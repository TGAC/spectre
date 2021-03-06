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

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.kohsuke.MetaInfServices;
import uk.ac.uea.cmp.spectre.core.ds.IdentifierList;
import uk.ac.uea.cmp.spectre.core.ds.Locations;
import uk.ac.uea.cmp.spectre.core.ds.Sequences;
import uk.ac.uea.cmp.spectre.core.ds.distance.DistanceMatrix;
import uk.ac.uea.cmp.spectre.core.ds.split.SplitSystem;
import uk.ac.uea.cmp.spectre.core.ds.tree.newick.NewickTree;
import uk.ac.uea.cmp.spectre.core.io.AbstractSpectreReader;
import uk.ac.uea.cmp.spectre.core.io.SpectreDataType;
import uk.ac.uea.cmp.spectre.core.io.SpectreReader;
import uk.ac.uea.cmp.spectre.core.io.nexus.parser.NexusFileLexer;
import uk.ac.uea.cmp.spectre.core.io.nexus.parser.NexusFileParser;
import uk.ac.uea.cmp.spectre.core.io.nexus.parser.NexusFilePopulator;
import uk.ac.uea.cmp.spectre.core.util.DefaultParsingErrorListener;
import uk.ac.uea.cmp.spectre.core.util.DefaultParsingErrorStrategy;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Used to handle streaming of Nexus format files into memory, and convertion of
 * the data into a SplitSystem object.
 *
 * @author Dan
 */
@MetaInfServices(SpectreReader.class)
public class NexusReader extends AbstractSpectreReader {


    public Nexus parse(File file) throws IOException {

        // Convert loader into a character stream
        CharStream in = new ANTLRInputStream(new FileInputStream(file));

        // Setup lexer
        NexusFileLexer lexer = new NexusFileLexer(in);
        lexer.removeErrorListeners();
        lexer.addErrorListener(new DefaultParsingErrorListener());

        // Do the lexing
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // The results of parsing go in here
        Nexus nexus = new Nexus();

        // Setup parser
        NexusFileParser parser = new NexusFileParser(tokens);
        parser.removeParseListeners();
        parser.removeErrorListeners();
        parser.addParseListener(new NexusFilePopulator(nexus, true));
        parser.addErrorListener(new DefaultParsingErrorListener());
        parser.setErrorHandler(new DefaultParsingErrorStrategy());

        // Do the parsing
        try {
            parser.parse();
        } catch (RuntimeException e) {
            throw new IOException("Error parsing: " + file.getAbsolutePath() + "; " + e.getMessage(), e);
        }

        // Return the populated Nexus object
        return nexus;
    }

    /**
     * Reads the file specified by this reader and converts the data into a set
     * of taxa and the distances between taxa.
     *
     * @return The distance matrix, with associated taxa set.
     * @throws IOException    Thrown if there were any problems accessing the file.
     */
    @Override
    public DistanceMatrix readDistanceMatrix(File file) throws IOException {
        return this.parse(file).getDistanceMatrix();
    }

    @Override
    public Sequences readAlignment(File file) throws IOException {
        return this.parse(file).getAlignments();
    }

    @Override
    public Locations readLocations(File file) throws IOException {
        return this.parse(file).getLocations();
    }


    @Override
    public List<NewickTree> readTrees(File input, double weight) throws IOException {
        throw new UnsupportedOperationException("Haven't got around to implementing this yet");
    }

    @Override
    public SplitSystem readSplitSystem(File file) throws IOException {
        return this.parse(file).getSplitSystem();
    }

    @Override
    public String[] commonFileExtensions() {
        return new NexusFileFilter().commonFileExtensions();
    }

    @Override
    public String getIdentifier() {
        return "NEXUS";
    }

    @Override
    public boolean acceptsDataType(SpectreDataType spectreDataType) {

        if (spectreDataType == SpectreDataType.DISTANCE_MATRIX)
            return true;
        else if (spectreDataType == SpectreDataType.TREE)
            return true;
        else if (spectreDataType == SpectreDataType.CIRCULAR_ORDERING)
            return true;
        else if (spectreDataType == SpectreDataType.SPLITS)
            return true;
        else if (spectreDataType == SpectreDataType.QUARTETS)
            return true;
        else if (spectreDataType == SpectreDataType.NETWORK)
            return true;
        else if (spectreDataType == SpectreDataType.ALIGNMENT)
            return true;
        else if (spectreDataType == SpectreDataType.LOCATIONS)
            return true;

        return false;
    }


    /**
     * Pulls out the circular ordering that should be present in the split block of the nexus file (assuming this represents
     * a circular split system)
     *
     * @param file Nexus file containing split block with circular ordering
     * @return A circular ordering
     * @throws IOException Thrown if any disk related issues occur (e.g. file does not exist or inaccessible)
     */
    public IdentifierList extractCircOrdering(File file) throws IOException {

        SplitSystem splitSystem = this.parse(file).getSplitSystem();

        return splitSystem.isCircular() ?
                splitSystem.getOrderedTaxa() :
                null;
    }

    public List<String> extractBlock(File file, String blockName) throws IOException {

        List<String> lines = new ArrayList<>();

        BufferedReader br = new BufferedReader(new FileReader(file));

        String line;
        boolean foundStart = false;
        while ((line = br.readLine()) != null) {

            line = line.trim();

            String[] words = line.split(" ");
            if (words != null && words.length >= 2 && words[0].equalsIgnoreCase("begin") && words[1].equalsIgnoreCase(blockName)) {
                foundStart = true;
            }

            if (foundStart) {
                lines.add(line);
            }

            if (foundStart && words != null && words.length >= 1 && words[0].equalsIgnoreCase("end")) {
                break;
            }
        }

        br.close();

        return lines;
    }


    /**
     * Just returns everything that was found in the nexus file
     *
     * @param inFile Nexus file
     * @return An object representing the contents of a nexus file.
     * @throws IOException Thrown if any disk related issues occur (e.g. file does not exist or inaccessible)
     */
    public Nexus readNexusData(File inFile) throws IOException {

        return this.parse(inFile);
    }

}
