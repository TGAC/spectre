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
package uk.ac.uea.cmp.spectre.core.ds.tree;

import java.util.List;

public class Leaf implements BinaryTree {

    private int identity;

    public Leaf(int anIdentity) {
        identity = anIdentity;
    }

    @Override
    public void fillList(List<Integer> aList) {
        aList.add(Integer.valueOf(identity));
    }

    public int getIdentity() {
        return identity;
    }

    @Override
    public boolean hasChildren() {
        return false;
    }

}