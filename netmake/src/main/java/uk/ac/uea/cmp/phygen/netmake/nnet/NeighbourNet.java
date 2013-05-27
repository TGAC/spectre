/*
 * Phylogenetics Tool suite
 * Copyright (C) 2013  UEA CMP Phylogenetics Group
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
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

package uk.ac.uea.cmp.phygen.netmake.nnet;

import uk.ac.uea.cmp.phygen.core.ds.distance.DistanceMatrix;
import uk.ac.uea.cmp.phygen.core.ds.split.CircularOrdering;
import uk.ac.uea.cmp.phygen.core.ds.split.CircularSplitSystem;
import uk.ac.uea.cmp.phygen.core.ds.split.CompatibleSplitSystem;
import uk.ac.uea.cmp.phygen.core.ds.split.SplitSystem;

import java.util.Arrays;
import java.util.Stack;

/**
 * Implements Neighbor Net method of Bryant and Moulton (2004).
 */
public class NeighbourNet {
    private double optionThreshold = 0.000001; // min weight of split that we consider
    private boolean makeSplits = true;
    private String optionVarianceName = "Ordinary_Least_Squares";
    private boolean optionConstrain = true;
    private CircularOrdering ordering = null; // the computed ordering
    public final static String DESCRIPTION = "Computes the Neighbor-Net network (Bryant and Moulton 2004)";


    /**
     * Applies the method to the given data
     *
     * @param taxa the taxa
     * @param dist the input distances
     * @return the computed set of splits
     */
    public SplitSystem apply(DistanceMatrix dist){

        /*ordering = runNeighborNet(dist);

        String var = selectVariance(this.optionVarianceName);

        return new CircularSplitSystem(); */


        /*Splits splits = CircularSplitWeights.getWeightedSplits(ordering, dist, var, optionConstrain, optionThreshold);

        if (SplitsUtilities.isCompatible(splits))
            splits.getProperties().setCompatibility(Splits.Properties.COMPATIBLE);
        else
            splits.getProperties().setCompatibility(Splits.Properties.CYCLIC);
        return splits; */

        return null;
    }

    /**
     * A scaled down version of NeighborNet that only returns the ordering, and does not
     * access the document or progress bar.
     *
     * @param dist Distance matrix
     */
    static public CircularOrdering computeNeighborNetOrdering(DistanceMatrix dist) {
        int ntax = dist.size();
        int[] ordering = null;
        if (ntax < 4) {
            int[] orderingArray = new int[ntax + 1];
            for (int i = 1; i <= ntax; i++) {
                orderingArray[i] = i;
            }
            ordering = orderingArray;
        } else {
            ordering = runNeighborNet(dist);
        }

        // Neighbour net produces 1 based array, we should change it to zero based for external use
        int[] zeroBasedOrdering = new int[ntax];
        for(int i = 0; i < ntax; i++) {
            zeroBasedOrdering[i] = ordering[i+1] - 1;
        }

        return new CircularOrdering(zeroBasedOrdering);
    }

    /**
     * gets a short description of the algorithm
     *
     * @return a description
     */
    public String getDescription() {
        return DESCRIPTION;
    }

    /**
     * gets a cyclic ordering computed by the algorithm
     *
     * @return a cyclic ordering
     */
    public CircularOrdering getOrdering() {
        return ordering;
    }


    /**
     * Sets the power for least squares
     *
     * @param varName 0, 1 or 2
     */
    public void setOptionVariance(String varName) {
        this.optionVarianceName = varName;
    }

    /**
     * Gets the power for least squares
     *
     * @return the power
     */
    public String getOptionVariance() {
        return optionVarianceName;
    }


    public String selectVariance(String varianceName) {
        if (varianceName.equalsIgnoreCase("OrdinaryLeastSquares"))
            return "ols";
        else if (varianceName.equalsIgnoreCase("FitchMargoliash1"))
            return "fm1";
        else if (varianceName.equalsIgnoreCase("FitchMargoliash2"))
            return "fm2";
        else if (varianceName.equalsIgnoreCase("Estimated"))
            return "user";
        else
            return "ols"; //In case of uncertainty, do OLS
    }

    /**
     * Sets the constrained option for least squares
     *
     * @param flag set the constrained option?
     */
    public void setConstrain(boolean flag) {
        this.optionConstrain = flag;
    }

    /**
     * Gets the constrained option for least squares
     *
     * @return true, if will use the constrained least squares
     */
    public boolean getConstrain() {
        return optionConstrain;
    }

    /*
    public  double getOptionThreshold() {
        return optionThreshold;
    }

    public  void setOptionThreshold(double optionThreshold) {
        this.optionThreshold = optionThreshold;
    }
    */


    /**
     * Sets up the working matrix. The original distance matrix is enlarged to
     * handle the maximum number of nodes
     *
     * @param dist Distance block
     * @return a working matrix of appropriate cardinality
     */
    private static double[][] setupMatrix(DistanceMatrix dist) {
        int ntax = dist.size();
        int max_num_nodes = 3 * ntax - 5;
        double[][] D = new double[max_num_nodes][max_num_nodes];
        /* Copy the distance matrix into a larger, scratch distance matrix */
        for (int i = 1; i <= ntax; i++) {
            for (int j = 1; j <= ntax; j++)
                D[i][j] = dist.getDistance(i-1, j-1);
            Arrays.fill(D[i], ntax + 1, max_num_nodes, 0.0);
        }
        for (int i = ntax + 1; i < max_num_nodes; i++)
            Arrays.fill(D[i], 0, max_num_nodes, 0.0);
        return D;
    }

    /**
     * Run the neighbor net algorithm
     */
    private static int[] runNeighborNet(DistanceMatrix dist) {

        int ntax = dist.size();

        //Special cases. When ntax<=3, the default circular ordering will work.
        if (ntax <= 3) // nnet can't do small data sets, so let's use split decomp
        {
            int[] ordering = new int[ntax + 1];
            for (int i = 0; i <= ntax; i++)
                ordering[i] = i;
            return ordering;
        }

        double[][] D = setupMatrix(dist);
        NetNode netNodes = new NetNode();

        /* Nodes are stored in a doubly linked list that we set up here */
        for (int i = ntax; i >= 1; i--) /* Initially, all singleton nodes are active */ {
            NetNode taxNode = new NetNode();
            taxNode.id = i;
            taxNode.next = netNodes.next;
            netNodes.next = taxNode;
        }

        /* Set up links in other direction */
        for (NetNode taxNode = netNodes; taxNode.next != null; taxNode = taxNode.next)
            taxNode.next.prev = taxNode;

        /* Perform the agglomeration step */
        Stack amalgs = new Stack();
        int num_nodes = ntax;
        num_nodes = agglomNodes(amalgs, D, netNodes, num_nodes);
        return expandNodes(num_nodes, ntax, amalgs, netNodes);
    }

    /**
     * Agglomerates the nodes
     */
    static private int agglomNodes(Stack amalgs, double D[][], NetNode netNodes, int num_nodes) {
        //System.err.println("agglomNodes");

        NetNode p, q, Cx, Cy, x, y;
        double Qpq, best;
        int num_active = num_nodes;
        int num_clusters = num_nodes;
        int m;
        double Dpq;

        while (num_active > 3) {

            /* Special case
            If we let this one go then we get a divide by zero when computing Qpq */
            if (num_active == 4 && num_clusters == 2) {
                p = netNodes.next;
                if (p.next != p.nbr)
                    q = p.next;
                else
                    q = p.next.next;
                if (D[p.id][q.id] + D[p.nbr.id][q.nbr.id] < D[p.id][q.nbr.id] + D[p.nbr.id][q.id]) {
                    agg3way(p, q, q.nbr, amalgs, D, netNodes, num_nodes);
                    num_nodes += 2;
                } else {
                    agg3way(p, q.nbr, q, amalgs, D, netNodes, num_nodes);
                    num_nodes += 2;
                }
                break;
            }

            /* Compute the "averaged" sums s_i from each cluster to every other cluster.

      To Do: 2x speedup by using symmetry*/

            for (p = netNodes.next; p != null; p = p.next)
                p.Sx = 0.0;
            for (p = netNodes.next; p != null; p = p.next) {
                if (p.nbr == null || p.nbr.id > p.id) {
                    for (q = p.next; q != null; q = q.next) {
                        if (q.nbr == null || (q.nbr.id > q.id) && (q.nbr != p)) {
                            Dpq = 0.0;
                            if ((p.nbr == null) && (q.nbr == null))
                                Dpq = D[p.id][q.id];
                            else if ((p.nbr != null) && (q.nbr == null))
                                Dpq = (D[p.id][q.id] + D[p.nbr.id][q.id]) / 2.0;
                            else if ((p.nbr == null) && (q.nbr != null))
                                Dpq = (D[p.id][q.id] + D[p.id][q.nbr.id]) / 2.0;
                            else
                                Dpq = (D[p.id][q.id] + D[p.id][q.nbr.id] + D[p.nbr.id][q.id] + D[p.nbr.id][q.nbr.id]) / 4.0;

                            p.Sx += Dpq;
                            if (p.nbr != null)
                                p.nbr.Sx += Dpq;
                            q.Sx += Dpq;
                            if (q.nbr != null)
                                q.nbr.Sx += Dpq;
                        }
                    }

                }
            }

            Cx = Cy = null;
            /* Now minimize (m-2) D[C_i,C_k] - Sx - Sy */
            best = 0;
            for (p = netNodes.next; p != null; p = p.next) {
                if ((p.nbr != null) && (p.nbr.id < p.id)) /* We only evaluate one node per cluster */
                    continue;
                for (q = netNodes.next; q != p; q = q.next) {
                    if ((q.nbr != null) && (q.nbr.id < q.id)) /* We only evaluate one node per cluster */
                        continue;
                    if (q.nbr == p) /* We only evaluate nodes in different clusters */
                        continue;
                    if ((p.nbr == null) && (q.nbr == null))
                        Dpq = D[p.id][q.id];
                    else if ((p.nbr != null) && (q.nbr == null))
                        Dpq = (D[p.id][q.id] + D[p.nbr.id][q.id]) / 2.0;
                    else if ((p.nbr == null) && (q.nbr != null))
                        Dpq = (D[p.id][q.id] + D[p.id][q.nbr.id]) / 2.0;
                    else
                        Dpq = (D[p.id][q.id] + D[p.id][q.nbr.id] + D[p.nbr.id][q.id] + D[p.nbr.id][q.nbr.id]) / 4.0;
                    Qpq = ((double) num_clusters - 2.0) * Dpq - p.Sx - q.Sx;
                    /* Check if this is the best so far */
                    if ((Cx == null || (Qpq < best)) && (p.nbr != q)) {
                        Cx = p;
                        Cy = q;
                        best = Qpq;
                    }
                }
            }

            /* Find the node in each cluster */
            x = Cx;
            y = Cy;

            if (Cx.nbr != null || Cy.nbr != null) {
                Cx.Rx = ComputeRx(Cx, Cx, Cy, D, netNodes);
                if (Cx.nbr != null)
                    Cx.nbr.Rx = ComputeRx(Cx.nbr, Cx, Cy, D, netNodes);
                Cy.Rx = ComputeRx(Cy, Cx, Cy, D, netNodes);
                if (Cy.nbr != null)
                    Cy.nbr.Rx = ComputeRx(Cy.nbr, Cx, Cy, D, netNodes);
            }

            m = num_clusters;
            if (Cx.nbr != null)
                m++;
            if (Cy.nbr != null)
                m++;

            best = ((double) m - 2.0) * D[Cx.id][Cy.id] - Cx.Rx - Cy.Rx;
            if (Cx.nbr != null) {
                Qpq = ((double) m - 2.0) * D[Cx.nbr.id][Cy.id] - Cx.nbr.Rx - Cy.Rx;
                if (Qpq < best) {
                    x = Cx.nbr;
                    y = Cy;
                    best = Qpq;
                }
            }
            if (Cy.nbr != null) {
                Qpq = ((double) m - 2.0) * D[Cx.id][Cy.nbr.id] - Cx.Rx - Cy.nbr.Rx;
                if (Qpq < best) {
                    x = Cx;
                    y = Cy.nbr;
                    best = Qpq;
                }
            }
            if ((Cx.nbr != null) && (Cy.nbr != null)) {
                Qpq = ((double) m - 2.0) * D[Cx.nbr.id][Cy.nbr.id] - Cx.nbr.Rx - Cy.nbr.Rx;
                if (Qpq < best) {
                    x = Cx.nbr;
                    y = Cy.nbr;
                    best = Qpq;
                }
            }

            /* We perform an agglomeration... one of three types */
            if ((null == x.nbr) && (null == y.nbr)) {   /* Both vertices are isolated...add edge {x,y} */
                agg2way(x, y);
                num_clusters--;
            } else if (null == x.nbr) {     /* X is isolated,  Y  is not isolated*/
                agg3way(x, y, y.nbr, amalgs, D, netNodes, num_nodes);
                num_nodes += 2;
                num_active--;
                num_clusters--;
            } else if ((null == y.nbr) || (num_active == 4)) { /* Y is isolated,  X is not isolated
                                                        OR theres only four active nodes and none are isolated */
                agg3way(y, x, x.nbr, amalgs, D, netNodes, num_nodes);
                num_nodes += 2;
                num_active--;
                num_clusters--;
            } else {  /* Both nodes are connected to others and there are more than 4 active nodes */
                num_nodes = agg4way(x.nbr, x, y, y.nbr, amalgs, D, netNodes, num_nodes);
                num_active -= 2;
                num_clusters--;
            }
        }
        return num_nodes;
    }

    /**
     * agglomerate 2 nodes
     *
     * @param x one node
     * @param y other node
     */
    static private void agg2way(NetNode x, NetNode y) {
        x.nbr = y;
        y.nbr = x;
    }

    /**
     * agglomerate 3 nodes.
     * Note that this version doesn't update num_nodes, you need to
     * num_nodes+=2 after calling this!
     *
     * @param x one node
     * @param y other node
     * @param z other node
     * @return one of the new nodes
     */
    static private NetNode agg3way(NetNode x, NetNode y, NetNode z,
                                   Stack amalgs, double[][] D, NetNode netNodes, int num_nodes) {
/* Agglomerate x,y, and z to give TWO new nodes, u and v */
/* In terms of the linked list: we replace x and z
  	 by u and v and remove y from the linked list.
  	 and replace y with the new node z
    Returns a pointer to the node u */
//printf("Three way: %d, %d, and %d\n",x.id,y.id,z.id);

        NetNode u = new NetNode();
        u.id = num_nodes + 1;
        u.ch1 = x;
        u.ch2 = y;

        NetNode v = new NetNode();
        v.id = num_nodes + 2;
        v.ch1 = y;
        v.ch2 = z;

/* Replace x by u in the linked list */
        u.next = x.next;
        u.prev = x.prev;
        if (u.next != null)
            u.next.prev = u;
        if (u.prev != null)
            u.prev.next = u;

/* Replace z by v in the linked list */
        v.next = z.next;
        v.prev = z.prev;
        if (v.next != null)
            v.next.prev = v;
        if (v.prev != null)
            v.prev.next = v;

/* Remove y from the linked list */
        if (y.next != null)
            y.next.prev = y.prev;
        if (y.prev != null)
            y.prev.next = y.next;

/* Add an edge between u and v, and add u into the list of amalgamations */
        u.nbr = v;
        v.nbr = u;

/* Update distance matrix */

        for (NetNode p = netNodes.next; p != null; p = p.next) {
            D[u.id][p.id] = D[p.id][u.id] = (2.0 / 3.0) * D[x.id][p.id] + D[y.id][p.id] / 3.0;
            D[v.id][p.id] = D[p.id][v.id] = (2.0 / 3.0) * D[z.id][p.id] + D[y.id][p.id] / 3.0;
        }
        D[u.id][u.id] = D[v.id][v.id] = 0.0;

        amalgs.push(u);

        return u;
    }

    /**
     * Agglomerate four nodes
     *
     * @param x2 a node
     * @param x  a node
     * @param y  a node
     * @param y2 a node
     * @return the new number of nodes
     */
    static private int agg4way(NetNode x2, NetNode x, NetNode y, NetNode y2,
                               Stack amalgs, double[][] D, NetNode netNodes, int num_nodes) {
/* Replace x2,x,y,y2 by with two vertices... performed using two
  	 3 way amalgamations */

        NetNode u;

        u = agg3way(x2, x, y, amalgs, D, netNodes, num_nodes); /* Replace x2,x,y by two nodes, equal to x2_prev.next and y_prev.next. */
        num_nodes += 2;
        agg3way(u, u.nbr, y2, amalgs, D, netNodes, num_nodes); /* z = y_prev . next */
        num_nodes += 2;
        return num_nodes;
    }

    /**
     * Computes the Rx
     *
     * @param z        a node
     * @param Cx       a node
     * @param Cy       a node
     * @param D        the distances
     * @param netNodes the net nodes
     * @return the Rx value
     */
    static private double ComputeRx(NetNode z, NetNode Cx, NetNode Cy, double[][] D,
                                    NetNode netNodes) {
        double Rx = 0.0;

        for (NetNode p = netNodes.next; p != null; p = p.next) {
            if (p == Cx || p == Cx.nbr || p == Cy || p == Cy.nbr || p.nbr == null)
                Rx += D[z.id][p.id];
            else /* p.nbr != null */
                Rx += D[z.id][p.id] / 2.0; /* We take the average of the distances */
        }
        return Rx;
    }

    /**
     * Expands the net nodes to obtain the ordering, quickly
     *
     * @param num_nodes number of nodes
     * @param ntax      number of taxa
     * @param amalgs    stack of amalagations
     * @param netNodes  the net nodes
     */
    static private int[] expandNodes(int num_nodes, int ntax, Stack amalgs, NetNode netNodes) {

        int[] ordering = new int[ntax + 1];
        //System.err.println("expandNodes");
        NetNode x, y, z, u, v, a;

/* Set up the circular order for the first three nodes */
        x = netNodes.next;
        y = x.next;
        z = y.next;
        z.next = x;
        x.prev = z;

/* Now do the rest of the expansions */
        while (!amalgs.empty()) {
/* Find the three elements replacing u and v. Swap u and v around if v comes before u in the
          circular ordering being built up */
            u = (NetNode) (amalgs.pop());
            // System.err.println("POP: u="+u);
            v = u.nbr;
            x = u.ch1;
            y = u.ch2;
            z = v.ch2;
            if (v != u.next) {
                NetNode tmp = u;
                u = v;
                v = tmp;
                tmp = x;
                x = z;
                z = tmp;
            }

/* Insert x,y,z into the circular order */
            x.prev = u.prev;
            x.prev.next = x;
            x.next = y;
            y.prev = x;
            y.next = z;
            z.prev = y;
            z.next = v.next;
            z.next.prev = z;
        }

/* When we exit, we know that the point x points to a node in the circular order */
/* We loop through until we find the node after taxa zero */
        while (x.id != 1) {
            x = x.next;
        }

/* extract the ordering */
        a = x;
        int t = 0;
        do {
            // System.err.println("a="+a);
            ordering[++t] = a.id;
            a = a.next;
        } while (a != x);
        return ordering;
    }


}
