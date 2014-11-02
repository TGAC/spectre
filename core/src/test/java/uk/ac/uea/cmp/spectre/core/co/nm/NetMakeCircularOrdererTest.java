/*
 * Suite of PhylogEnetiC Tools for Reticulate Evolution (SPECTRE)
 * Copyright (C) 2014  UEA School of Computing Sciences
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

package uk.ac.uea.cmp.spectre.core.co.nm;

import org.junit.Before;
import org.junit.Test;
import uk.ac.uea.cmp.spectre.core.co.CircularOrderingCreator;
import uk.ac.uea.cmp.spectre.core.co.nm.weighting.GreedyMEWeighting;
import uk.ac.uea.cmp.spectre.core.co.nm.weighting.TSPWeighting;
import uk.ac.uea.cmp.spectre.core.co.nm.weighting.TreeWeighting;
import uk.ac.uea.cmp.spectre.core.ds.CircularOrdering;
import uk.ac.uea.cmp.spectre.core.ds.IdentifierList;
import uk.ac.uea.cmp.spectre.core.ds.distance.DistanceMatrix;
import uk.ac.uea.cmp.spectre.core.ds.distance.FlexibleDistanceMatrix;

import static org.junit.Assert.assertTrue;

public class NetMakeCircularOrdererTest {


    private DistanceMatrix dist1;
    private DistanceMatrix dist2;
    private DistanceMatrix dist3;

    private static final CircularOrdering orderDist1 = new CircularOrdering(new String[]{"B","A","C","D","E"});
    private static final CircularOrdering orderDist2 = new CircularOrdering(new String[]{"C","E","D","B","A"});
    private static final CircularOrdering orderDist3 = new CircularOrdering(new String[]{"C","D","B","A","E","F"});
    private static final CircularOrdering orderDist3b = new CircularOrdering(new String[]{"E","F","B","A","C","D"});

    @Before
    public void setup() {



        this.dist1 = new FlexibleDistanceMatrix(new double[][]{
                {0, 2, 3, 4, 4},
                {2, 0, 3, 4, 4},
                {3, 3, 0, 3, 3},
                {4, 4, 3, 0, 2},
                {4, 4, 3, 2, 0}
        });


        this.dist2 = new FlexibleDistanceMatrix(new double[][]{
                {0, 4, 7, 7, 8},
                {4, 0, 7, 7, 8},
                {7, 7, 0, 6, 5},
                {7, 7, 6, 0, 5},
                {8, 8, 5, 5, 0}
        });


        this.dist3 = new FlexibleDistanceMatrix(new double[][]{
                {0, 2, 7, 7, 6, 6},
                {2, 0, 7, 7, 6, 6},
                {7, 7, 0, 2, 7, 7},
                {7, 7, 2, 0, 7, 7},
                {6, 6, 7, 7, 0, 2},
                {6, 6, 7, 7, 2, 0}
        });

    }

    private void test(DistanceMatrix dm, CircularOrdering correctResult) {

        CircularOrderingCreator nm = new NetMakeCircularOrderer(new TreeWeighting(0.5), null);

        IdentifierList ssO = nm.createCircularOrdering(dm);

        CircularOrdering circularOrdering = new CircularOrdering(ssO);

        if (correctResult != null) {
            assertTrue(circularOrdering.equals(correctResult));
        }
        assertTrue(true);
    }

    private void testGreedy(DistanceMatrix dm, CircularOrdering correctResult) {

        CircularOrderingCreator nm = new NetMakeCircularOrderer(new GreedyMEWeighting(dm), new TSPWeighting());

        IdentifierList ssO = nm.createCircularOrdering(dm);

        CircularOrdering circularOrdering = new CircularOrdering(ssO);

        if (correctResult != null) {
            assertTrue(circularOrdering.equals(correctResult));
        }
        assertTrue(true);
    }

    @Test
    public void testDist1() {
        this.test(this.dist1, this.orderDist1);
    }

    @Test
    public void testDist2() {
        this.test(this.dist2, this.orderDist2);
    }

    @Test
    public void testDist3() {
        this.test(this.dist3, this.orderDist3);
    }

    @Test
    public void testGreedy1() {
        this.testGreedy(this.dist1, this.orderDist1);
    }
}