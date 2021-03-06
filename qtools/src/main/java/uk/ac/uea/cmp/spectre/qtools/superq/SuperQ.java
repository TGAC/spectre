/*
 * Suite of PhylogEnetiC Tools for Reticulate Evolution (SPECTRE)
 * Copyright (C) 2017  UEA School of Computing Sciences
 *
 * This program is free software: you can redistribute it and/or modify it under the term of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program.  If not, see
 * <http://www.gnu.org/licenses/>.
 */

package uk.ac.uea.cmp.spectre.qtools.superq;

import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.earlham.metaopt.Optimiser;
import uk.ac.earlham.metaopt.OptimiserException;
import uk.ac.earlham.metaopt.Problem;
import uk.ac.uea.cmp.spectre.core.ds.quad.quartet.GroupedQuartetSystem;
import uk.ac.uea.cmp.spectre.core.ds.split.SplitSystem;
import uk.ac.uea.cmp.spectre.core.io.nexus.NexusWriter;
import uk.ac.uea.cmp.spectre.core.ui.gui.RunnableTool;
import uk.ac.uea.cmp.spectre.core.ui.gui.StatusTrackerWithView;
import uk.ac.uea.cmp.spectre.qtools.qmaker.QMaker;
import uk.ac.uea.cmp.spectre.qtools.qnet.QNet;
import uk.ac.uea.cmp.spectre.qtools.qnet.QNetResult;

import java.io.File;
import java.io.IOException;

public class SuperQ extends RunnableTool {

    private static Logger log = LoggerFactory.getLogger(SuperQ.class);
    private SuperQOptions options;

    public SuperQ(SuperQOptions options) {
        this(options, null);
    }

    public SuperQ(SuperQOptions options, StatusTrackerWithView tracker) {

        super(tracker);
        this.options = options;
    }


    @Override
    public void run() {

        try {

            // Check we have something sensible to work with
            if (this.options == null) {
                throw new IOException("Must specify a valid set of parameters to control superQ.");
            }

            if (this.options.getInputFiles() == null || this.options.getInputFiles().length == 0) {
                throw new IOException("Must specify at least one valid input file.");
            }

            if (this.options.getOutputFile() == null || this.options.getOutputFile().isDirectory()) {
                throw new IOException("Must specify a valid path where to create the output file.");
            }

            // Print the validated options
            log.info("Recognised these options:\n\n" +
                    this.options.toString());

            // Get a shortcut to runtime object for checking memory usage
            Runtime rt = Runtime.getRuntime();

            // Start timing
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();

            log.info("Starting job");
            log.debug("FREE MEM - at start: " + rt.freeMemory());

            this.continueRun();

            notifyUser("Converting input trees into a combined quartet system.  " +
                    (this.options.getScalingSolver() != null ? "(Scaling input (optimiser: " + this.options.getScalingSolver().getIdentifier() + ")" : ""));

            GroupedQuartetSystem combinedQuartetSystem = new QMaker().execute(
                    this.options.getInputFiles(),
                    this.options.getScalingSolver());

            rt.gc();
            log.debug("FREE MEM - after running QMaker: " + rt.freeMemory());

            this.continueRun();

            String primarySolverName = this.options.getPrimarySolver() == null ?
                    "INTERNAL" :
                    this.options.getPrimarySolver().getIdentifier();

            notifyUser("Running QNet (optimiser: " + primarySolverName + ")");
            QNetResult qnetResult = new QNet().execute(combinedQuartetSystem, false, -1.0, this.options.getPrimarySolver());

            rt.gc();
            log.debug("FREE MEM - after running Q-Net: " + rt.freeMemory());

            this.continueRun();

            double[] solution = qnetResult.getComputedWeights().getSolution();

            if (this.options.getSecondaryProblem() == null || this.options.getSecondarySolver() == null) {
                log.info("Secondary optimisation - Not requested");
            } else {

                Optimiser secondarySolver = this.options.getSecondarySolver();
                notifyUser("Secondary optimisation (optimiser: " + secondarySolver.getIdentifier() + "; objective: " +
                        this.options.getSecondaryProblem().getName() + ")");

                try {

                    // Create problem from the computed weights
                    Problem problem = this.options.getSecondaryProblem().compileProblem(qnetResult.getCircularOrdering().size(),
                            solution, qnetResult.getComputedWeights().getEtE().toArray());

                    // Run the secondary optimisation step
                    double[] solution2 = this.options.getSecondaryProblem().getName().equalsIgnoreCase("MINIMA") ?
                            this.minimaOptimise(secondarySolver, problem, solution) :     // Special handling of MINIMA objective
                            secondarySolver.optimise(problem).getVariableValues();        // Normally just call child's optimisation method

                    // Sum the solutions (modifies the qnet result)
                    for (int i = 0; i < solution.length; i++) {
                        solution[i] = solution[i] + solution2[i];
                    }
                } catch (OptimiserException use) {
                    log.warn("Secondary optimisation - Invalid solution.  Keeping original solution from first optimisation step.");
                }
            }

            SplitSystem ss = qnetResult.createSplitSystem(null, QNetResult.SplitLimiter.STANDARD).makeCanonical();

            rt.gc();
            log.debug("FREE MEM - after computing weights: " + rt.freeMemory());

            // Filter split system if required
            if (this.options.getFilter() != null) {

                notifyUser("Filtering out bottom " + this.options.getFilter() * 100.0 + " % of splits");
                ss.filterByRelativeWeight(this.options.getFilter());
            }

            // Save split system
            File outputFile = this.options.getOutputFile();
            File outputDir = this.options.getOutputFile().getParentFile();

            // Create output dir if required
            if (outputDir != null && !outputDir.exists()) {
                outputDir.mkdirs();
            }

            notifyUser("Saving to file: " + outputFile.getAbsolutePath());
            NexusWriter nw = new NexusWriter();
            // Construct file content
            nw.appendHeader()
                    .appendLine()
                    .append(ss.getOrderedTaxa().sortById())
                    .appendLine()
                    .append(ss);

            nw.write(outputFile);

            this.trackerFinished(true);

            // Print run time on screen
            stopWatch.stop();
            log.info("Completed Successfully - Total run time: " + stopWatch.toString());

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            this.setError(e);
            this.trackerFinished(false);
        } finally {
            this.notifyListener();
        }
    }


    /**
     * To be used only in conjunction with the MINIMA objective
     *
     * NOTE: This might be broken... probably need to modify the coefficients of the problem internally for each run
     *
     * @param optimiser The optimiser to use
     * @param problem The problem to solve
     * @param data The variables
     * @return The solution The result
     * @throws OptimiserException Occurs if there was a problem while optimising the problem
     */
    protected double[] minimaOptimise(Optimiser optimiser, Problem problem, double[] data) throws OptimiserException {

        double[] coefficients = problem.getObjective().getExpression().getLinearCoefficients(problem.getVariables());

        double[] solution = new double[data.length];

        // This is a bit messy, but essentially what is happening is that we run the solver for each coefficient, and if
        // the non-negativity constraint at each variable is > 0, then we run the solver but we only take the result from
        // this variable, otherwise the solution at this position is 0.
        for (int k = 0; k < data.length; k++) {
            if (data[k] > 0.0) {
                coefficients[k] = 1.0;
                double[] help = optimiser.optimise(problem).getVariableValues();
                solution[k] = help[k];
                coefficients[k] = 0.0;
            } else {
                solution[k] = 0;
            }
        }

        return solution;
    }

    private void notifyUser(String message) {
        log.info(message);
        this.trackerInitUnknownRuntime(message);
    }
}
