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

package uk.ac.uea.cmp.spectre.net.netmake;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import uk.ac.uea.cmp.spectre.core.ds.IdentifierList;
import uk.ac.uea.cmp.spectre.core.ds.distance.DistanceMatrix;
import uk.ac.uea.cmp.spectre.core.ds.distance.FlexibleDistanceMatrix;
import uk.ac.uea.cmp.spectre.core.ds.split.SplitSystem;
import uk.ac.uea.cmp.spectre.core.ds.split.circular.ordering.CircularOrderingCreator;
import uk.ac.uea.cmp.spectre.core.ds.split.circular.ordering.nm.NetMakeCircularOrderer;
import uk.ac.uea.cmp.spectre.core.ds.split.circular.ordering.nm.weighting.GreedyMEWeighting;
import uk.ac.uea.cmp.spectre.core.ds.split.circular.ordering.nm.weighting.TSPWeighting;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertTrue;


public class NetMakeTest {

    @Before
    public void setup() {

        BasicConfigurator.configure();
        LogManager.getRootLogger().setLevel(Level.WARN);
    }

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    //@Test
    public void simpleTest() {

        String[] taxa = new String[]{"1", "2", "3", "4", "5", "6", "7"};

        double[][] distances = new double[][]{
                {0, 2, 5, 5, 5, 5, 3},
                {2, 0, 5, 5, 5, 5, 3},
                {5, 5, 0, 2, 4, 4, 4},
                {5, 5, 2, 0, 4, 4, 4},
                {5, 5, 4, 4, 0, 2, 4},
                {5, 5, 4, 4, 2, 0, 4},
                {3, 3, 4, 4, 4, 4, 0}
        };


        DistanceMatrix distanceMatrix = new FlexibleDistanceMatrix(new IdentifierList(taxa), distances);

        CircularOrderingCreator circularOrderingCreator = new NetMakeCircularOrderer(new GreedyMEWeighting(distanceMatrix), new TSPWeighting());
        NetMakeResult result = new NetMake().execute(distanceMatrix, circularOrderingCreator);

        SplitSystem tree = result.getTreeSS();

       /* assertTrue(tree.getNbSplits() == 11);

        for(Split s : tree.getSplits()) {
            assertTrue(Equality.approxEquals(s.getWeight(), 1.0, 0.01));
        }

        assertTrue(result.getNetworkSS().getNbSplits() == 11);

        for(Split s : result.getNetworkSS().getSplits()) {
            assertTrue(s.getWeight() == 1.0);
        }   */
    }

    @Test
    public void testDist1() throws IOException {

        File dist1Nex = FileUtils.toFile(NetMakeTest.class.getResource("/dist1.nex"));

        File netout = new File(folder.getRoot(), "network.nex");
        File treeout = new File(folder.getRoot(), "tree.nex");

        NetMakeOptions options = new NetMakeOptions();
        options.setInput(dist1Nex);
        options.setOutputNetwork(netout);
        options.setOutputTree(treeout);
        options.setWeighting1("TREE");

        NetMake nm = new NetMake(options);

        nm.run();

        assertTrue(netout.exists());
        //assertTrue(treeout.exists());

        List<String> netlines = FileUtils.readLines(netout, "UTF-8");
        //List<String> treelines = FileUtils.readLines(treeout, "UTF-8");

        assertTrue(!netlines.isEmpty());
        //assertTrue(!treelines.isEmpty());

    }

    @Test
    public void testDist2() throws IOException {

        File dist1Nex = FileUtils.toFile(NetMakeTest.class.getResource("/dist2.nex"));

        File netout = new File(folder.getRoot(), "network.nex");
        File treeout = new File(folder.getRoot(), "tree.nex");

        NetMakeOptions options = new NetMakeOptions();
        options.setInput(dist1Nex);
        options.setOutputNetwork(netout);
        options.setOutputTree(treeout);
        options.setWeighting1("TREE");
        options.setCoAlg("NETMAKE");

        NetMake nm = new NetMake(options);

        nm.run();

        assertTrue(netout.exists());
        assertTrue(treeout.exists());

        List<String> netlines = FileUtils.readLines(netout, "UTF-8");
        List<String> treelines = FileUtils.readLines(treeout, "UTF-8");

        assertTrue(!netlines.isEmpty());
        assertTrue(!treelines.isEmpty());
    }

    @Test
    public void testColors() throws IOException {

        File dist1Nex = FileUtils.toFile(NetMakeTest.class.getResource("/colors.nex"));

        File netout = new File(folder.getRoot(), "network.nex");
        File treeout = new File(folder.getRoot(), "tree.nex");

        NetMakeOptions options = new NetMakeOptions();
        options.setInput(dist1Nex);
        options.setOutputNetwork(netout);
        options.setOutputTree(treeout);
        options.setWeighting1("TREE");

        NetMake nm = new NetMake(options);

        nm.run();

        assertTrue(netout.exists());
        //assertTrue(treeout.exists());

        List<String> netlines = FileUtils.readLines(netout, "UTF-8");
        //List<String> treelines = FileUtils.readLines(treeout, "UTF-8");

        assertTrue(!netlines.isEmpty());
        //assertTrue(!treelines.isEmpty());
    }
}
