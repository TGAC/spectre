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

package uk.ac.uea.cmp.spectre.core.io.nexus;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import uk.ac.uea.cmp.spectre.core.ds.IdentifierList;
import uk.ac.uea.cmp.spectre.core.ds.distance.DistanceMatrix;
import uk.ac.uea.cmp.spectre.core.ds.network.Edge;
import uk.ac.uea.cmp.spectre.core.ds.network.Network;
import uk.ac.uea.cmp.spectre.core.ds.network.NetworkLabel;
import uk.ac.uea.cmp.spectre.core.ds.network.Vertex;
import uk.ac.uea.cmp.spectre.core.ds.split.Split;
import uk.ac.uea.cmp.spectre.core.ds.split.SplitSystem;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: Dan
 * Date: 28/04/13
 * Time: 20:40
 * To change this template use File | Settings | File Templates.
 */
public class NexusReaderTest {

    @Test
    public void testNexusReader() throws IOException {

        File testFile = FileUtils.toFile(NexusReaderTest.class.getResource("/bees.nex"));

        DistanceMatrix distanceMatrix = new NexusReader().readDistanceMatrix(testFile);

        assertTrue(distanceMatrix.size() == 6);
    }

    @Test
    public void testTriangularNexusReader() throws IOException {

        File testFile = FileUtils.toFile(NexusReaderTest.class.getResource("/triangular.nex"));

        DistanceMatrix distanceMatrix = new NexusReader().readDistanceMatrix(testFile);

        assertTrue(distanceMatrix.size() == 5);
    }

    @Test
    public void testTaxaParser() throws IOException {

        File testFile = FileUtils.toFile(NexusReaderTest.class.getResource("/triangular.nex"));

        Nexus nexus = new NexusReader().parse(testFile);

        assertTrue(nexus.getTaxa().size() == 5);
        assertTrue(nexus.getDistanceMatrix().size() == 5);
        assertTrue(nexus.getDistanceMatrix().getDistance(3, 1) == 2.0);
        assertTrue(nexus.getDistanceMatrix().getDistance(1, 3) == 2.0);
    }

    @Test
    public void testTaxaColorsWithoutComments() throws IOException {

        File testFile = FileUtils.toFile(NexusReaderTest.class.getResource("/colors-nocomments.nex"));

        Nexus nexus = new NexusReader().parse(testFile);

        assertTrue(nexus.getTaxa().size() == 10);
    }

    @Test
    public void testTaxaColorsWithComments() throws IOException {

        File testFile = FileUtils.toFile(NexusReaderTest.class.getResource("/colors-withcomments.nex"));

        Nexus nexus = new NexusReader().parse(testFile);

        assertTrue(nexus.getTaxa().size() == 10);
    }

    @Test
    public void testTaxaColorsNetwork() throws IOException {

        File testFile = FileUtils.toFile(NexusReaderTest.class.getResource("/colors-network.nex"));

        Nexus nexus = new NexusReader().parse(testFile);

        assertTrue(nexus.getTaxa().size() == 10);
    }

    @Test
    public void testDistMatrix() throws IOException {

        File testFile = FileUtils.toFile(NexusReaderTest.class.getResource("/distmtx.nex"));

        Nexus nexus = new NexusReader().parse(testFile);

        DistanceMatrix dm = nexus.getDistanceMatrix();

        assertNotNull(dm);
        assertTrue(dm.size() == 4);

        double[][] mtx = dm.getMatrix();

        assertTrue(mtx[2][3] == 3.0);
    }

    @Test
    public void testBeesSplits() throws IOException {

        File testFile = FileUtils.toFile(NexusReaderTest.class.getResource("/bees-splits.nex"));

        Nexus nexus = new NexusReader().parse(testFile);

        assertNotNull(nexus);

        IdentifierList taxa = nexus.getTaxa();

        assertNotNull(taxa);
        assertTrue(taxa.size() == 6);

        SplitSystem ss = nexus.getSplitSystem();

        assertNotNull(ss);

        IdentifierList co = ss.getOrderedTaxa();

        assertNotNull(co);
        assertTrue(co.size() == 6);
        assertTrue(co.get(2).getId() == 6);


        assertTrue(ss.size() == 9);
        Split s = ss.get(7);

        assertNotNull(s);
        assertTrue(s.getWeight() == 6.951241628977932E-4);
    }

    @Test
    public void testBeesSplitsPlusId() throws IOException {

        File testFile = FileUtils.toFile(NexusReaderTest.class.getResource("/bees-splits-plusId.nex"));

        Nexus nexus = new NexusReader().parse(testFile);

        assertNotNull(nexus);

        IdentifierList taxa = nexus.getTaxa();

        assertNotNull(taxa);
        assertTrue(taxa.size() == 6);

        SplitSystem ss = nexus.getSplitSystem();

        assertNotNull(ss);

        IdentifierList co = ss.getOrderedTaxa();

        assertNotNull(co);
        assertTrue(co.size() == 6);
        assertTrue(co.get(2).getId() == 6);


        assertTrue(ss.size() == 9);
        Split s = ss.get(7);

        assertNotNull(s);
        assertTrue(s.getWeight() == 6.951241628977932E-4);
    }

    @Test
    public void testNetworkAssumptions() throws IOException {

        File testFile = FileUtils.toFile(NexusReaderTest.class.getResource("/network-assumptions.nex"));

        Nexus nexus = new NexusReader().parse(testFile);

        assertNotNull(nexus);

        Network network = nexus.getNetwork();

        assertNotNull(network);

        List<Vertex> vertices = network.getAllVertices();

        assertNotNull(vertices);
        assertFalse(vertices.isEmpty());

        List<NetworkLabel> labels = network.getLabels();

        assertNotNull(labels);
        assertFalse(labels.isEmpty());

        List<Edge> edges = network.getAllEdges();

        assertNotNull(edges);
        assertFalse(edges.isEmpty());

    }

    @Test
    public void testSPR32() throws IOException {

        File testFile = FileUtils.toFile(NexusReaderTest.class.getResource("/SPR32.nex"));

        Nexus nexus = new NexusReader().parse(testFile);

        assertTrue(nexus.getTaxa().size() == 32);
    }

    @Test
    public void testCharBlock() throws IOException {

        File testFile = FileUtils.toFile(NexusReaderTest.class.getResource("/gallwasps_aln.nex"));

        Nexus nexus = new NexusReader().parse(testFile);

        assertTrue(nexus.getAlignments().size() == 80);
    }

    @Test
    public void testCharBlockAsh() throws IOException {

        File testFile = FileUtils.toFile(NexusReaderTest.class.getResource("/ash.aln.nxs"));

        Nexus nexus = new NexusReader().parse(testFile);

        assertTrue(nexus.getAlignments().size() == 43);
    }

    // Can't do this one yet.
    //@Test
    public void testFunghi1() throws IOException {

        File testFile = FileUtils.toFile(NexusReaderTest.class.getResource("/funghi1.nex"));

        Nexus nexus = new NexusReader().parse(testFile);

        // Just check that we get here
        assertTrue(nexus.getAlignments() != null);
    }
}
