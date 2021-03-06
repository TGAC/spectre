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

package uk.ac.uea.cmp.spectre.qtools.qnet;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import uk.ac.earlham.metaopt.Equality;
import uk.ac.earlham.metaopt.OptimiserException;
import uk.ac.earlham.metaopt.external.JOptimizer;
import uk.ac.uea.cmp.spectre.core.ds.IdentifierList;
import uk.ac.uea.cmp.spectre.core.ds.quad.quartet.GroupedQuartetSystem;
import uk.ac.uea.cmp.spectre.core.ds.quad.quartet.QuartetSystem;
import uk.ac.uea.cmp.spectre.core.ds.quad.quartet.QuartetSystemList;
import uk.ac.uea.cmp.spectre.core.ds.split.SplitSystem;
import uk.ac.uea.cmp.spectre.core.ds.tree.newick.NewickTree;
import uk.ac.uea.cmp.spectre.qtools.qmaker.QMaker;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class QNetITCase {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    private File simpleOutput;

    @Before
    public void setUp() throws Exception {
        simpleOutput = folder.newFolder("qnet-IT");

        // Uncomment this line if you want to see output.
        //BasicConfigurator.configure();
        //LogManager.getRootLogger().setLevel(Level.INFO);
    }

    protected GroupedQuartetSystem createFromScript() throws IOException {

        return new QMaker().execute(new File[]{FileUtils.toFile(QNetITCase.class.getResource("/simple/in.script"))});
    }

    protected GroupedQuartetSystem create2ConflictingTreesWithSameFiveTaxa() throws IOException {

        NewickTree tree1 = new NewickTree("(((A:1,B:1):1,C:1),(D:1,E:1):1);");

        NewickTree tree2 = new NewickTree("(((A:1,B:1):1,D:1),(C:1,E:1):1);");

        QuartetSystemList qsl = new QuartetSystemList();
        qsl.add(new QuartetSystem(tree1));
        qsl.add(new QuartetSystem(tree2));

        return new QMaker().execute(qsl);
    }

    @Test
    public void test5TaxaTree() throws OptimiserException, IOException, QNetException {

        NewickTree tree = new NewickTree("(((A:1,B:1):1,C:1),(D:1,E:1):1);");
        GroupedQuartetSystem qs = new GroupedQuartetSystem(tree);
        QNetResult result = new QNet().execute(qs, false, -1.0, null);

        // Check circular ordering
        assertTrue(result.getCircularOrdering().equals(new IdentifierList(new int[]{2, 1, 3, 4, 5})));

        // Check weights from solver
        assertTrue(Equality.approxEquals(result.getComputedWeights().getSolution(), new double[]{0.0, 0.0, 0.0, 1.0, 1.0}));

        // Generate splits
        SplitSystem ss = result.createSplitSystem(null, QNetResult.SplitLimiter.STANDARD);

        // Check splits
        assertTrue(ss.size() == 7);
        assertTrue(Equality.approxEquals(ss.getWeightAt(0), 1.0));
        assertTrue(Equality.approxEquals(ss.getWeightAt(1), 1.0));
    }

    //@Test
    public void test5TaxaTreeJOptimiser() throws OptimiserException, IOException, QNetException {

        NewickTree tree = new NewickTree("(((A:1,B:1):1,C:1),(D:1,E:1):1);");
        GroupedQuartetSystem qs = new GroupedQuartetSystem(tree);
        QNetResult result = new QNet().execute(qs, false, -1.0, new JOptimizer());

        // Check circular ordering
        assertTrue(result.getCircularOrdering().equals(new IdentifierList(new int[]{2, 1, 3, 4, 5})) ||
                result.getCircularOrdering().equals(new IdentifierList(new int[]{1, 2, 3, 4, 5})));

        // Check weights from solver
        assertTrue(Equality.approxEquals(result.getComputedWeights().getSolution(), new double[]{0.0, 0.0, 0.0, 1.0, 1.0}));

        // Generate splits
        SplitSystem ss = result.createSplitSystem(null, QNetResult.SplitLimiter.STANDARD);

        // Check splits
        /*assertTrue(ss.getNbSplits() == 7);
        assertTrue(ss.getWeightAt(0) == 2.0);
        assertTrue(ss.getWeightAt(1) == 2.0);*/
    }

    //@Test
    public void test2ConflictingTreesInternal() throws OptimiserException, IOException, QNetException {

        GroupedQuartetSystem qs = create2ConflictingTreesWithSameFiveTaxa();
        QNetResult result = new QNet().execute(qs, false, -1.0, null);

        // Check circular ordering
        assertTrue(result.getCircularOrdering().equals(new IdentifierList(new int[]{3, 1, 2, 4, 5})));

        SplitSystem ss = result.createSplitSystem(null, QNetResult.SplitLimiter.STANDARD);

        assertTrue(ss.size() == 8);
        assertTrue(ss.getWeightAt(0) == 4.0);
        assertTrue(ss.getWeightAt(1) == 1.75);
        assertTrue(ss.getWeightAt(2) == 1.75);
    }

    //@Test
    public void test2ConflictingTreesJOptimiser() throws OptimiserException, IOException, QNetException {

        GroupedQuartetSystem qs = create2ConflictingTreesWithSameFiveTaxa();
        QNetResult result = new QNet().execute(qs, false, -1.0, new JOptimizer());

        // Check circular ordering
        assertTrue(ArrayUtils.isEquals(result.getCircularOrdering().toArray(),
                new int[]{3, 1, 2, 4, 5}));

        SplitSystem ss = result.createSplitSystem(null, QNetResult.SplitLimiter.STANDARD);


        assertTrue(true);
    }

    /*@Test
    public void testSimpleScript() throws OptimiserException, IOException, QNetException {

        GroupedQuartetSystem qs = this.createFromScript();

        QNetResult result = new QNet().execute(qs, false, -1.0, new Gurobi());

        // Check circular ordering
        assertTrue(result.getCircularOrdering().equals(new CircularOrdering(new int[]{
                23,22,1,24,25,26,31,32,21,19,20,30,2,3,4,14,27,11,10,8,7,9,5,6,28,12,13,29,17,15,16,18})));

        CircularSplitSystem ss = result.createSplitSystem(null, QNetResult.SplitLimiter.STANDARD);

        // We expect only the trivial splits for this one.  Solver fails!
        assertTrue(ss.getNbSplits() == 32);
    } */
}
