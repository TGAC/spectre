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

package uk.ac.uea.cmp.spectre.qtools.qmaker;

import org.junit.Test;
import uk.ac.uea.cmp.spectre.core.ds.quad.Quad;
import uk.ac.uea.cmp.spectre.core.ds.quad.SpectreQuad;
import uk.ac.uea.cmp.spectre.core.ds.quad.quartet.GroupedQuartetSystem;
import uk.ac.uea.cmp.spectre.core.ds.quad.quartet.QuartetSystem;
import uk.ac.uea.cmp.spectre.core.ds.quad.quartet.QuartetSystemList;
import uk.ac.uea.cmp.spectre.core.ds.quad.quartet.QuartetWeights;
import uk.ac.uea.cmp.spectre.core.ds.tree.newick.NewickTree;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class QMakerTest {

    @Test
    public void testFiveTaxaTree() throws IOException {

        NewickTree tree = new NewickTree("(((A:1,B:1):1,C:1),(D:1,E:1):1);");

        QuartetSystemList qsl = new QuartetSystemList(new QuartetSystem(tree));

        GroupedQuartetSystem qsc = new QMaker().execute(qsl);

        assertTrue(true);
    }

    @Test
    public void testSevenTaxaTree() throws IOException {

        NewickTree tree = new NewickTree("(((A:1,B:1):1,((C:1,D:1):1,E:1):1),(F:1,G:1):1);");

        QuartetSystemList qsl = new QuartetSystemList(new QuartetSystem(tree));

        GroupedQuartetSystem qsc = new QMaker().execute(qsl);

        assertTrue(true);
    }

    @Test
    public void testConflict() throws IOException {

        NewickTree tree1 = new NewickTree("(((A:1,B:1):1,C:1),(D:1,E:1):1);");

        NewickTree tree2 = new NewickTree("(((A:1,B:1):1,D:1),(C:1,E:1):1);");

        QuartetSystemList qsl = new QuartetSystemList();
        qsl.add(new QuartetSystem(tree1));
        qsl.add(new QuartetSystem(tree2));

        GroupedQuartetSystem gqs = new QMaker().execute(qsl);
        List<Quad> quads = gqs.sortedQuartets();

        assertNotNull(quads);
        assertTrue(quads.size() == 5);

        Quad q1 = new SpectreQuad(1, 2, 3, 4);
        assertTrue(quads.get(0).equals(q1));
        assertTrue(gqs.getQuartets().get(q1).equals(new QuartetWeights(1.0, 0.0, 0.0)));

        Quad q2 = new SpectreQuad(1, 2, 3, 5);
        assertTrue(quads.get(1).equals(q2));
        assertTrue(gqs.getQuartets().get(q2).equals(new QuartetWeights(1.5, 0.0, 0.0)));

        Quad q4 = new SpectreQuad(1, 3, 4, 5);
        assertTrue(quads.get(3).equals(q4));
        assertTrue(gqs.getQuartets().get(q4).equals(new QuartetWeights(1.0, 1.0, 0.0)));
    }


    @Test
    public void testCombine() throws IOException {

        NewickTree tree1 = new NewickTree("(((A:1,B:1):1,C:1),(D:1,E:1):1);");

        NewickTree tree2 = new NewickTree("(((A:1,B:1):1,((H:1,D:1):1,E:1):1),(F:1,G:1):1);");

        QuartetSystemList qsl = new QuartetSystemList();
        qsl.add(new QuartetSystem(tree1));
        qsl.add(new QuartetSystem(tree2));

        GroupedQuartetSystem qsc = new QMaker().execute(qsl);

        assertTrue(true);
    }
}
