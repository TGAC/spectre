/*
 * Suite of PhylogEnetiC Tools for Reticulate Evolution (SPECTRE)
 * Copyright (C) 2015  UEA School of Computing Sciences
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

package uk.ac.uea.cmp.spectre.core.ds.tree;

import java.util.List;

/**
 * A node is either a tree or a leaf
 *
 * @author IntelliJ IDEA
 * @since Date: 2004-jul-11 Time: 20:04:56
 */
public interface Node {

    void index(List<String> taxonNames);

    void harvestNames(List<String> taxonNames);

    void harvest(List<Integer> taxa);

    boolean internalNode();

    void rename(List<String> oldTaxa, List<String> newTaxa);
}
