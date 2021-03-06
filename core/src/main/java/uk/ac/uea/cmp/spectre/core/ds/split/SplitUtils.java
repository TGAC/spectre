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

package uk.ac.uea.cmp.spectre.core.ds.split;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import uk.ac.uea.cmp.spectre.core.ds.Identifier;
import uk.ac.uea.cmp.spectre.core.ds.IdentifierList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dan
 * Date: 18/11/13
 * Time: 13:39
 * To change this template use File | Settings | File Templates.
 */
public class SplitUtils {

    public static List<Split> createWeightedSplitList(List<SplitBlock> splitBlocks, List<Double> weights, final int nbTaxa) {

        if (splitBlocks.size() != weights.size())
            throw new IllegalArgumentException("Split blocks and weights are of different lengths.");

        List<Split> splits = new ArrayList<>();

        int i = 0;
        for (SplitBlock splitBlock : splitBlocks) {
            splits.add(new SpectreSplit(splitBlock, nbTaxa, weights.get(i++)));
        }

        return splits;
    }


    public static double sumOfWeights(List<Split> splits) {

        double sum = 0.0;

        for (Split split : splits) {
            sum += split.getWeight();
        }

        return sum;
    }

    public static double meanOfWeights(List<Split> splits) {

        return sumOfWeights(splits) / (double) splits.size();
    }

    public static int calcMaxSplits(final int nbTaxa) {
        return nbTaxa * (nbTaxa - 1) / 2 - nbTaxa;
    }


    /**
     * Create a list of possible split indices from a given number of taxa
     *
     * @param N The number of taxa
     * @return An array of split indices
     */
    public static List<Pair<Integer, Integer>> createSplitIndices(final int N) {

        List<Pair<Integer, Integer>> splitIndices = new ArrayList<>(calcMaxSplits(N));

        int n = 0;

        for (int i = 1; i <= N; i++) {

            for (int j = i + 2; j <= N; j++) {

                if (i != 1 || j != N) {
                    // valid split
                    splitIndices.add(n++, new ImmutablePair<>(i, j));
                }
            }
        }

        return splitIndices;
    }

    public static List<Split> createTrivialSplits(IdentifierList taxa, final double weight) {
        List<Split> splits = new ArrayList<>();

        for (Identifier taxon : taxa) {

            splits.add(new SpectreSplit(new SpectreSplitBlock(new int[]{taxon.getId()}), taxa.size(), weight));
        }

        return splits;
    }

    public static List<Split> createTrivialSplits(final int nbTaxa, final double weight) {

        List<Split> splits = new ArrayList<>();

        for (int i = 1; i <= nbTaxa; i++) {

            splits.add(new SpectreSplit(new SpectreSplitBlock(new int[]{i}), nbTaxa, weight));
        }

        return splits;
    }
}
