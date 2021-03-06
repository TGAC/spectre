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

package uk.ac.uea.cmp.spectre.core.io;

import uk.ac.uea.cmp.spectre.core.ds.Locations;
import uk.ac.uea.cmp.spectre.core.ds.Sequences;
import uk.ac.uea.cmp.spectre.core.ds.distance.DistanceMatrix;
import uk.ac.uea.cmp.spectre.core.ds.quad.quartet.QuartetSystem;
import uk.ac.uea.cmp.spectre.core.ds.split.SplitSystem;
import uk.ac.uea.cmp.spectre.core.ds.tree.newick.NewickTree;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dan
 * Date: 06/11/13
 * Time: 20:02
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractSpectreReader implements SpectreReader {


    @Override
    public List<NewickTree> readTrees(File input) throws IOException {
        return this.readTrees(input, 1.0);
    }

    @Override
    public List<NewickTree> readTrees(File input, double weight) throws IOException {
        throw new UnsupportedOperationException(this.getClass().getCanonicalName() + " does not support reading of trees.");
    }

    @Override
    public DistanceMatrix readDistanceMatrix(File input) throws IOException {
        throw new UnsupportedOperationException(this.getClass().getCanonicalName() + " does not support reading of distance matrices.");
    }

    @Override
    public SplitSystem readSplitSystem(File file) throws IOException {
        throw new UnsupportedOperationException(this.getClass().getCanonicalName() + " does not support reading of split systems.");
    }

    @Override
    public Sequences readAlignment(File file) throws IOException {
        throw new UnsupportedOperationException(this.getClass().getCanonicalName() + " does not support reading of alignments.");
    }

    @Override
    public QuartetSystem readQuartets(File file) throws IOException {
        throw new UnsupportedOperationException(this.getClass().getCanonicalName() + " does not support reading of quartets.");
    }

    @Override
    public Locations readLocations(File file) throws IOException {
        throw new UnsupportedOperationException(this.getClass().getCanonicalName() + " does not support reading of locations.");
    }

    @Override
    public boolean acceptsIdentifier(String id) {

        if (id.equalsIgnoreCase(this.getIdentifier())) {
            return true;
        }

        for (String ext : this.commonFileExtensions()) {
            if (ext.equalsIgnoreCase(id))
                return true;
        }

        return id.equalsIgnoreCase(this.getClass().getName());
    }

    @Override
    public boolean acceptsDataTypes(List<SpectreDataType> spectreDataTypeList) {

        for (SpectreDataType spectreDataType : spectreDataTypeList) {
            if (!this.acceptsDataType(spectreDataType)) {
                return false;
            }
        }
        return true;
    }
}
