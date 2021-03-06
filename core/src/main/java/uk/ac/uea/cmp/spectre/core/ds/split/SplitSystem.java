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

import uk.ac.uea.cmp.spectre.core.ds.IdentifierList;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dan
 * Date: 18/11/13
 * Time: 13:11
 * To change this template use File | Settings | File Templates.
 */
public interface SplitSystem extends List<Split> {


    // **** Taxa methods ****

    /**
     * Gets the number of taxa associated with this split system
     *
     * @return The number of taxa
     */
    int getNbTaxa();

    /**
     * Gets the number of splits in the split system
     * @return The number of splits
     */
    int getNbSplits();

    int getNbTrivialSplits();
    int getNbNonTrivialSplits();
    int getNbActiveWeightedSplits();

    /**
     * Gets the actual taxa object stored in this split system.
     * If this split system is circular then this will represent the taxa in their cyclic ordering.
     *
     * @return The taxa
     */
    IdentifierList getOrderedTaxa();


    // **** Standard split methods ****

    /**
     * Removes the last split in the split system and returns it
     * @return Last split
     */
    Split removeLastSplit();

    /**
     * Merges the splits at the given indexes.  After merging Split i will contain the contents of both split i and split
     * j before merging.  This merged split is returned.
     *
     * @param i The split to merge
     * @param j The split which will be added to the split at i, and then deleted.
     * @return The merged split.
     */
    Split mergeSplits(final int i, final int j);

    /**
     * Whether or not this splitsystem contains the specified split.
     * @param s Split to test
     * @return True if this split system contains the split, otherwise false.
     */
    boolean contains(Split s);

    /**
     * Whether or not this splitsystem contains the specified splitblock.
     * @param sb Splitblock to test
     * @return True if this split system contains the splitblock, otherwise false.
     */
    //boolean contains(SplitBlock sb);

    // **** Methods related to split weights ****

    /**
     * Returns true if this split system is weighted.
     *
     * @return True if this split system is weighted, false otherwise.
     */
    boolean isWeighted();

    /**
     * Gets the weight of the split at the given index.
     *
     * @param i The index of the split weight
     * @return The weight of the split
     */
    double getWeightAt(final int i);

    /**
     * Filters this split system based on the given threshold.  Splits with weighting less than the threshold will be
     * discarded.
     *
     * @param percentThreshold The threshold to filter by
     * @return The filtered split system
     */
    SplitSystem filterByRelativeWeight(final double percentThreshold);

    /**
     * This filters the split system based on the weight threshold.  Splits with weights lower than this are discarded.
     * Note that unlike filterByRelativeWeight, this method creates and returns a filtered copy of the split system rather than modifying
     * this split system.
     * @param minWeight minimum weight allowed in returned split system
     * @return A new filtered copy of this split system
     */
    SplitSystem filterByAbsoluteWeight(final double minWeight);

    /**
     * Activates splits with weight greater than threshold, and deactivates those below.
     * @param threshold Cutoff value for activation
     */
    void activateByWeight(final double threshold);

    /**
     * Creates a copy of this split system with all splits in canonical form.  This means each split is arranged so the
     * a-side is smaller than the b-side.  Then the splits are sorted by a-side length, then by id.  This results in
     * trivial splits being first in the list.
     * @return Copy of this split system in an easily interpretable format.
     */
    SplitSystem makeCanonical();

    /**
     * Creates a copy of this split system with all splits organised by defined permutations based on the ordering of the
     * original taxa.  This ensures this split system, providing it is either circular or flat, can be drawn correctly
     * by our drawing algorithm.  This method will throw an IllegalStateException if this split system can#t be converted
     * into induced ordering.  Typically this will be because the split system is not flat.
     * @return Copy of this split system in an order suitable for drawing.
     */
    SplitSystem makeInducedOrdering();


    // **** Interrogation methods that try to detect split system properties ****

    /**
     * Whether or not this split system is a circular split system     *
     * @return True if this is a circular split system, false otherwise.
     */
    boolean isCircular();


    /**
     * Whether or not this split system is a compatible split system.
     * If this is true, then this split system can be represented as a tree.     *
     * @return True if this is a compatible split system, false otherwise
     */
    boolean isCompatible();

    /**
     * Whether or not the two specified splits in the system are compatible     *
     * @param i Split i
     * @param j Split j
     * @return True if these are compatible splits, false otherwise
     */
    boolean isCompatible(final int i, final int j);

    /**
     * Returns the compatibility pattern of split i and split j
     * @param i Split i
     * @param j Split j
     * @return The compatibility pattern
     */
    Split.Compatible getCompatible(final int i, final int j);

    /**
     * Returns true if this split system contains all possible splits for the given number of taxa
     * @return True if split system has the maximum complement of splits, false otherwise.
     */
    boolean isFull();

    /**
     * Tests to see if taxon from is on the same side in both splits
     * @param i Split i
     * @param j Split j
     * @param taxon Taxon
     * @return True if taxon is on the same side for both splits, false otherwise
     */
    boolean isTaxonOnSameSide(final int i, final int j, int taxon);

    /**
     * This method checks whether the quadruple split number nr is in the restriction of the split system to {a,b,c,d}.
     * It is assumed that a, b, c and d are pairwise distinct. The possible values for nr are:
     * 0 = a|bcd
     * 1 = b|acd
     * 2 = c|abd
     * 3 = d|abc
     * 4 = ab|cd
     * 5 = ac|bd
     * 6 = ad|bc
     * @param a Taxon a
     * @param b Taxon b
     * @param c Taxon c
     * @param d Taxon d
     * @param nr
     * @return True if restriction exists, false otherwise
     */
    boolean restrictionExists(final int a, final int b, final int c, final int d, final int nr);

    boolean[][] getAs2DBooleanArray();
    double[] getWeightsAsArray();

    /**
     * This is a hack to increase all taxa id by one.  Currently required to make compatible with FlatNJ.  Will remove
     * later so please don't use this method.
     */
    void incTaxId();
}
