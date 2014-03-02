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

package uk.ac.uea.cmp.phybre.core.io.nexus.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.uea.cmp.phybre.core.ds.IdentifierList;
import uk.ac.uea.cmp.phybre.core.ds.split.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dan
 * Date: 18/11/13
 * Time: 12:43
 * To change this template use File | Settings | File Templates.
 */
public class NexusSplitSystemBuilder {

    private static Logger log = LoggerFactory.getLogger(NexusSplitSystemBuilder.class);

    private double fit;
    private boolean isCyclic;
    private boolean isWeighted;
    private boolean hasLabels;
    private boolean hasConfidences;
    private boolean hasIntervals;
    private int expectedNbSplits;
    private int expectedNbTaxa;
    private List<SplitBlock> splitBlocks;
    private List<Double> weights;
    private List<Integer> cycle;
    private IdentifierList taxa;

    public NexusSplitSystemBuilder() {
        this.fit = -1.0;
        this.isCyclic = false;
        this.isWeighted = false;
        this.hasLabels = false;
        this.hasConfidences = false;
        this.hasIntervals = false;
        this.expectedNbSplits = 0;
        this.expectedNbTaxa = 0;
        this.splitBlocks = new ArrayList<>();
        this.weights = new ArrayList<>();
        this.cycle = new ArrayList<>();
        this.taxa = null;
    }


    public SplitSystem createSplitSystem() {

        if (!isWeighted) {
            throw new UnsupportedOperationException("Can't support unweighted split systems at the moment");
        }

        final int nbTaxa = taxa != null ? taxa.size() : expectedNbTaxa;

        if (expectedNbTaxa != 0 && taxa != null && nbTaxa != expectedNbTaxa) {
            log.warn("Expected number of taxa (" + expectedNbTaxa + ") is different from the number of found taxa (" + nbTaxa + ").");
        }

        SplitSystem ss;

        List<Split> splits = SplitUtils.createWeightedSplitList(this.splitBlocks, this.weights, nbTaxa);

        if (isCyclic) {

            ss = taxa != null ?
                    new CircularSplitSystem(taxa, splits, CircularOrdering.createFromList(cycle)) :
                    new CircularSplitSystem(IdentifierList.createSimpleIdentifiers(expectedNbTaxa), splits, CircularOrdering.createFromList(cycle));
        }
        else {
            ss = taxa != null ?
                    new SimpleSplitSystem(taxa, splits) :
                    new SimpleSplitSystem(nbTaxa, splits);
        }

        if (expectedNbSplits != 0 && ss.getNbSplits() != expectedNbSplits) {
            log.warn("Expected number of splits (" + expectedNbSplits + ") is different from the number of found splits (" + ss.getNbSplits() + ").");
        }

        return ss;
    }

    public double getFit() {
        return fit;
    }

    public void setFit(double fit) {
        this.fit = fit;
    }

    public boolean isWeighted() {
        return isWeighted;
    }

    public void setWeighted(boolean weighted) {
        isWeighted = weighted;
    }

    public boolean isHasLabels() {
        return hasLabels;
    }

    public void setHasLabels(boolean hasLabels) {
        this.hasLabels = hasLabels;
    }

    public boolean isHasConfidences() {
        return hasConfidences;
    }

    public void setHasConfidences(boolean hasConfidences) {
        this.hasConfidences = hasConfidences;
    }

    public boolean isHasIntervals() {
        return hasIntervals;
    }

    public void setHasIntervals(boolean hasIntervals) {
        this.hasIntervals = hasIntervals;
    }

    public int getExpectedNbSplits() {
        return expectedNbSplits;
    }

    public void setExpectedNbSplits(int expectedNbSplits) {
        this.expectedNbSplits = expectedNbSplits;
    }

    public int getExpectedNbTaxa() {
        return expectedNbTaxa;
    }

    public void setExpectedNbTaxa(int expectedNbTaxa) {
        this.expectedNbTaxa = expectedNbTaxa;
    }

    public boolean isCyclic() {
        return isCyclic;
    }

    public void setCyclic(boolean cyclic) {
        isCyclic = cyclic;
    }

    public void addSplit(SplitBlock splitBlock, double weight) {

        this.splitBlocks.add(splitBlock);
        this.weights.add(weight);
    }

    public void addCycleItem(int cycleItem) {
        this.cycle.add(cycleItem);
    }

    public void setTaxa(IdentifierList taxa) {
        this.taxa = taxa;
    }

    public IdentifierList getTaxa() {
        return taxa;
    }
}
