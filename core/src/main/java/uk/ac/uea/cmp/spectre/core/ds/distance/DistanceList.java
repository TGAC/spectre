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

package uk.ac.uea.cmp.spectre.core.ds.distance;

import uk.ac.uea.cmp.spectre.core.ds.Identifier;
import uk.ac.uea.cmp.spectre.core.ds.IdentifierList;

/**
 * Created by dan on 27/02/14.
 */
public interface DistanceList {

    double getDistance(Identifier taxon);

    double getDistance(int taxonId);

    double getDistance(String taxonName);


    /**
     * Sets the distance to the specified taxon
     *
     * @param taxon The taxon which will have its distance modified
     * @param value The new distance value
     * @return The previous distance set between the two taxa, or 0.0 if the value has never been set before
     */
    double setDistance(final Identifier taxon, final double value);

    /**
     * Sets the distance to the specified taxon using its name
     *
     * @param taxonName Name of the taxon to modify
     * @param value The new distance value
     * @return The previous distance set between the two taxa, or 0.0 if the value has never been set before
     */
    double setDistance(final String taxonName, final double value);

    /**
     * Sets the distance to the specified taxa using its id
     *
     * @param taxonId ID of the taxon to modify
     * @param value The new distance value
     * @return The previous distance set between the two taxa, or 0.0 if the value has never been set before
     */
    double setDistance(final int taxonId, final double value);


    /**
     * Increments the distance to the specified taxon by incValue.  Returns the new distance to the specified taxon.
     *
     * @param taxon The taxon to increment
     * @param increment Amount to increment the distance by
     * @return The value after incrementation.
     */
    double incrementDistance(final Identifier taxon, final double increment);

    /**
     * Increments the distance to the specified taxon by incValue.  Returns the new distance to the specified taxon.
     *
     * @param taxonId ID of the taxon to modify
     * @param increment Amount to increment the distance by
     * @return The value after incrementation.
     */
    double incrementDistance(final int taxonId, final double increment);

    /**
     * Increments the distance to the specified taxon by incValue.  Returns the new distance to the specified taxon.
     *
     * @param taxonName Name of the taxon to increment
     * @param increment Amount to increment the distance by
     * @return The value after incrementation.
     */
    double incrementDistance(final String taxonName, final double increment);


    /**
     * Returns the taxon represented by this DistanceList
     *
     * @return The taxon master taxon to which all distances in this list relate to
     */
    Identifier getTaxon();


    /**
     * Gets all the other taxa targeted by this DistanceList
     *
     * @return The list of all taxa that this taxon relates to
     */
    IdentifierList getOtherTaxa();


    /**
     * Sums up all the distances in this DistanceList.
     *
     * @return The sum of distances from this element to all the others.
     */
    double sum();


}
