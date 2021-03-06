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
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class NetMakeCLIITCase {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void testTree1() throws IOException {

        File outputDir = temporaryFolder.getRoot();

        File testFile1 = FileUtils.toFile(NetMakeCLIITCase.class.getResource("/test.nex"));

        NetMakeCLI.main(new String[]{
                "--input", testFile1.getAbsolutePath(),
                "--network_out", new File(outputDir, "network.nex").getAbsolutePath(),
                "--tree_out", new File(outputDir, "tree.nex").getAbsolutePath(),
                "--weightings_1", "TREE"
        });

        assertTrue(outputDir.listFiles().length == 2);
    }

    //@Test
    public void testTreeBees() throws IOException {

        File outputDir = temporaryFolder.getRoot();

        File testFile2 = FileUtils.toFile(NetMakeCLIITCase.class.getResource("/bees.nex"));

        NetMakeCLI.main(new String[]{
                "--input", testFile2.getAbsolutePath(),
                "--network_out", new File(outputDir, "network.nex").getAbsolutePath(),
                "--tree_out", new File(outputDir, "tree.nex").getAbsolutePath(),
                "--weightings_1", "GREEDY_ME",
                "--weightings_2", "TSP"
        });

        assertTrue(outputDir.listFiles().length == 2);
    }
}
