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

package uk.ac.uea.cmp.spectre.core.ds.network;

import uk.ac.uea.cmp.spectre.core.ds.IdentifierList;

import java.util.List;
import java.util.Set;

/**
 * Created by dan on 18/03/14.
 */
public interface Network {

    IdentifierList getTaxa();

    VertexList getAllVertices();

    VertexList getLabeledVertices();

    void removeVertices(VertexList toRemove);

    EdgeList getAllEdges();

    EdgeList getInternalEdges();

    EdgeList getExternalEdges();

    Set<Edge> getExternalEdges(Edge e1, Vertex a, Edge e2);

    EdgeList getTrivialEdges();

    void addTrivialEdges(VertexList toAdd);

    List<NetworkLabel> getLabels();

    int getNbTaxa();

    boolean veryLongTrivial();

}
