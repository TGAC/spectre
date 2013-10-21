/*
 * Phylogenetics Tool suite
 * Copyright (C) 2013  UEA CMP Phylogenetics Group
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with this program.  If not, see
 * <http://www.gnu.org/licenses/>.
 */
package uk.ac.uea.cmp.phygen.superq;

import org.apache.commons.cli.*;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.uea.cmp.phygen.core.math.optimise.Objective;
import uk.ac.uea.cmp.phygen.core.math.optimise.OptimiserException;
import uk.ac.uea.cmp.phygen.core.math.optimise.OptimiserFactory;
import uk.ac.uea.cmp.phygen.superq.objectives.SecondaryProblemFactory;

import java.io.File;
import java.io.OutputStream;
import java.io.PrintWriter;

public class SuperQCLI {

    private static Logger log = LoggerFactory.getLogger(SuperQCLI.class);

    private static String JAR_NAME = "SuperQ.jar";

    private static String OPT_INPUT = "input";
    private static String OPT_INPUT_FORMAT = "input_format";
    private static String OPT_OUTPUT = "output";
    private static String OPT_PRIMARY_SOLVER = "primary_solver";
    private static String OPT_SECONDARY_SOLVER = "secondary_solver";
    private static String OPT_SECONDARY_OBJECTIVE = "secondary_objective";
    private static String OPT_SCALE = "scale";
    private static String OPT_FILTER = "filter";
    private static String OPT_HELP = "help";
    private static String OPT_VERBOSE = "verbose";


    private static void configureLogging() {
        // Setup logging
        File propsFile = new File("logging.properties");

        if (!propsFile.exists()) {
            BasicConfigurator.configure();
        } else {
            PropertyConfigurator.configure(propsFile.getPath());
        }
    }

    public static void main(String args[]) {

        configureLogging();


        // If there are no args we assume that we're in GUI mode
        try {
            if (args.length == 0) {

                log.info("Running in GUI mode");

                java.awt.EventQueue.invokeLater(new Runnable() {

                    @Override
                    public void run() {
                        new SuperQGUI().setVisible(true);
                    }
                });
                return;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            System.exit(1);
        }

        // Else we're in command line mode so process the args
        Options cmdLineOptions = createOptions();
        SuperQOptions sqOpts = null;
        try {
            sqOpts = processArgs(args, cmdLineOptions);
        } catch (ParseException pe) {
            log.error("OPTION PARSING ERROR: " + pe.getMessage(), pe);
            printUsage(cmdLineOptions, System.err);
            System.exit(2);
        }

        try {
            sqOpts.createValidateConfig();
            SuperQ superQ = new SuperQ(sqOpts);
            superQ.run();
            if (superQ.failed()) {
                log.error(superQ.getErrorMessage());
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            System.exit(3);
        }

        log.info("Completed successfully");
    }

    private static Options createOptions() {

        Options options = new Options();
        options.addOption("i", OPT_INPUT, true, "REQUIRED: The input file containing trees, or paths to many trees if input format is script");
        options.addOption("m", OPT_INPUT_FORMAT, true, "REQUIRED: The input file format: SCRIPT, NEWICK or NEXUS");
        options.addOption("o", OPT_OUTPUT, true, "REQUIRED: The output file containing the split systems generated by SuperQ");
        options.addOption("x", OPT_PRIMARY_SOLVER, true, "The primary solver to use with Non-Negative Least Square (NNLS) objective.  " +
                "If no valid solver is specified SuperQ will use its own internal NNLS solver.  Available Solvers: " +
                OptimiserFactory.getInstance().getOperationalOptimisers(Objective.ObjectiveType.QUADRATIC));
        options.addOption("y", OPT_SECONDARY_SOLVER, true, "The secondary solver to use: BEST_AVAILABLE, GUROBI, GLPK.  " +
                "BEST_AVAILABLE will select GUROBI if available, otherwise GLPK.  Default: BEST_AVAILABLE");
        options.addOption("b", OPT_SECONDARY_OBJECTIVE, true, "The objective to use if the NNLS solution is non-unique.  " +
                "Available options: " + SecondaryProblemFactory.getInstance().listSecondaryObjectivesAsString());
        options.addOption("s", OPT_SCALE, false, "Whether to scale the input tree.  Default: off");
        options.addOption("f", OPT_FILTER, true, "The filter value to use.  Default: no filter");
        options.addOption("h", OPT_HELP, false, "Shows this help.");
        options.addOption("v", OPT_VERBOSE, false, "Whether to output extra information");
        return options;
    }

    private static SuperQOptions processArgs(String[] args, Options options) throws ParseException {
        final CommandLineParser cmdLineParser = new PosixParser();

        CommandLine commandLine = cmdLineParser.parse(options, args);

        SuperQOptions sqOpts = null;

        try {
            sqOpts = new SuperQOptions();
        } catch (OptimiserException oe) {
            throw new ParseException("Error occured configuring optimiser.   Check you have selected an operational optimiser and set an appropriate objective.");
        }

        // This is probably a bit dangerous to assume that no further processing 
        // should occur if this option is selected.  But we'll end here for simplicity.
        if (commandLine.hasOption(OPT_HELP)) {
            printHelp(options, System.out);
            System.exit(0);
        }


        if (commandLine.hasOption(OPT_INPUT)) {
            sqOpts.setInputFile(new File(commandLine.getOptionValue(OPT_INPUT)));
        } else {
            throw new ParseException("You must specify an input file");
        }

        if (commandLine.hasOption(OPT_OUTPUT)) {
            sqOpts.setOutputFile(new File(commandLine.getOptionValue(OPT_OUTPUT)));
        } else {
            throw new ParseException("You must specify an output file");
        }

        if (commandLine.hasOption(OPT_INPUT_FORMAT)) {
            sqOpts.setInputFileFormat(SuperQOptions.InputFormat.valueOf(commandLine.getOptionValue(OPT_INPUT_FORMAT).toUpperCase()));
        } else {
            throw new ParseException("You must specify an input file format");
        }


        if (commandLine.hasOption(OPT_SECONDARY_OBJECTIVE)) {

            sqOpts.setSecondaryProblem(
                    SecondaryProblemFactory.getInstance().createSecondaryObjective(
                            commandLine.getOptionValue(OPT_SECONDARY_OBJECTIVE).toUpperCase()));
        }

        try {

            if (commandLine.hasOption(OPT_PRIMARY_SOLVER)) {
                sqOpts.setPrimarySolver(
                        OptimiserFactory.getInstance().createOptimiserInstance(
                                commandLine.getOptionValue(OPT_PRIMARY_SOLVER), Objective.ObjectiveType.QUADRATIC));
            }

            if (commandLine.hasOption(OPT_SECONDARY_SOLVER)) {
                sqOpts.setSecondarySolver(
                        OptimiserFactory.getInstance().createOptimiserInstance(
                                commandLine.getOptionValue(OPT_SECONDARY_SOLVER), sqOpts.getSecondaryProblem().getObjectiveType()));
            }
        } catch (OptimiserException oe) {
            throw new ParseException(oe.getMessage());
        }

        if (commandLine.hasOption(OPT_SCALE)) {
            sqOpts.setScaleInputTree(true);
        }

        if (commandLine.hasOption(OPT_FILTER)) {
            sqOpts.setFilter(Double.parseDouble(commandLine.getOptionValue("filter")));
        }

        if (commandLine.hasOption(OPT_VERBOSE)) {
            sqOpts.setVerbose(true);
        }

        return sqOpts;
    }

    private static void printUsage(final Options options, final OutputStream out) {
        final String commandLineSyntax = "java -jar " + JAR_NAME;
        final PrintWriter writer = new PrintWriter(out);
        final HelpFormatter usageFormatter = new HelpFormatter();
        usageFormatter.printUsage(writer, 80, commandLineSyntax, options);
        writer.flush();
    }

    private static void printHelp(final Options options, final OutputStream out) {
        final String commandLineSyntax = "java -jar " + JAR_NAME;
        final PrintWriter writer = new PrintWriter(out);
        final HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.printHelp(
                writer,
                120,
                commandLineSyntax,
                "",
                options,
                3,
                3,
                "",
                true);
        writer.flush();
    }
}
