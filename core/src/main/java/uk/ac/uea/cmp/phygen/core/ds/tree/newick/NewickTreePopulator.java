/*
 * Phylogenetics Tool suite
 * Copyright (C) 2013  UEA CMP Phylogenetics Group
 *
 * This program is free software: you can redistribute it and/or modify it under the term of the GNU General Public
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

package uk.ac.uea.cmp.phygen.core.ds.tree.newick;

/**
 * Created with IntelliJ IDEA.
 * User: dan
 * Date: 08/11/13
 * Time: 16:17
 * To change this template use File | Settings | File Templates.
 */
public class NewickTreePopulator extends NewickTreeBaseListener {

    private NewickTree tree;

    /**
     * Provide the object to populate with findings from the parse context
     * @param tree
     */
    public NewickTreePopulator(NewickTree tree) {
        this.tree = tree;
    }


}
