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
package uk.ac.uea.cmp.phygen.qnet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.uea.cmp.phygen.core.ds.quartet.QuartetWeights;
import uk.ac.uea.cmp.phygen.core.ds.tree.BinaryTree;
import uk.ac.uea.cmp.phygen.core.ds.tree.InnerNode;
import uk.ac.uea.cmp.phygen.core.ds.tree.Leaf;

import java.util.ArrayList;

class QJoiner {

    private static Logger log = LoggerFactory.getLogger(QJoiner.class);

    /**
     * QJoin method
     */
    public static ArrayList join(QNet qnet) {

        QuartetWeights theQuartetWeights = qnet.getWeights();
        int N = qnet.getN();
        boolean useMax = qnet.useMax();

        ArrayList<BinaryTree> cN = new ArrayList<>();

        for (int n = 0; n < N; n++) {

            cN.add(new Leaf(n + 1));
        }

        /**
         *
         * Each real step requires:
         *
         * 1. a sum matrix 2. a cN
         *
         * Find maximum in sum matrix, create conversion matrix while new cN is
         * being made Create new sum matrix Repeat
         *
         */
        int[][] qC = new int[N][N];
        double[][] sC = new double[N][N];

        /**
         *
         * Create the first cN
         *
         */
        for (int a = 0; a < N - 1; a++) {

            for (int b = a + 1; b < N; b++) {

                /**
                 *
                 * Split pair (a, b)
                 *
                 */
                int quartets = 0;
                double score = 0;

                for (int c = 0; c < N - 1; c++) {

                    if (c != a && c != b) {

                        for (int d = c + 1; d < N; d++) {

                            if (d != a && d != b) {

                                /**
                                 *
                                 * Unique pair combo (a, b) - (c, d)
                                 *
                                 */
                                /**
                                 *
                                 * Here, retrieve the lists and so on. Take
                                 * every combo of those four lists, retrieve the
                                 * weights, add to score, increment quartets
                                 *
                                 */
                                ArrayList<Integer> aL = new ArrayList<>();
                                ArrayList<Integer> bL = new ArrayList<>();
                                ArrayList<Integer> cL = new ArrayList<>();
                                ArrayList<Integer> dL = new ArrayList<>();

                                cN.get(a).fillList(aL);
                                cN.get(b).fillList(bL);
                                cN.get(c).fillList(cL);
                                cN.get(d).fillList(dL);

                                QuartetWeights.MeanSumResult meanSumResult = theQuartetWeights.meanSum(aL, bL, cL, dL);

                                quartets += meanSumResult.getCount();
                                score += meanSumResult.getScore();
                            }
                        }
                    }
                }

                /**
                 *
                 * The score and the quartets have been established. Now they
                 * are stored.
                 *
                 */
                qC[a][b] = quartets;
                sC[a][b] = score;
            }
        }

        /**
         *
         * The first cN, qC and sC has been established. Now for the loop.
         *
         */
        /**
         *
         * This should be exactly long enough
         *
         */
        for (int n = N; n > 3; n--) {

            /**
             *
             * Course through the sC/qC for maximum
             *
             */
            double quote = sC[0][1] / ((double) qC[0][1]);
            int bestA = 0;
            int bestB = 1;

            for (int a = 0; a < n - 1; a++) {

                for (int b = a + 1; b < n; b++) {

                    double aQuote = sC[a][b] / ((double) qC[a][b]);

                    if (useMax) {

                        if (aQuote > quote) {

                            quote = aQuote;
                            bestA = a;
                            bestB = b;

                        }

                    } else {

                        if (aQuote < quote) {

                            quote = aQuote;
                            bestA = a;
                            bestB = b;
                        }
                    }
                }
            }

            /**
             *
             * We now know which to join
             *
             * Print out.
             *
             */
            ArrayList<Integer> aJ = new ArrayList<>();
            ArrayList<Integer> bJ = new ArrayList<>();

            cN.get(bestA).fillList(aJ);
            cN.get(bestB).fillList(bJ);

            String aS = new String();

            for (int e = 0; e < aJ.size(); e++) {

                aS += (" " + aJ.get(e));
            }

            String bS = new String();

            for (int e = 0; e < bJ.size(); e++) {

                bS += (" " + bJ.get(e));
            }

            log.debug("Iteration step " + (N - n + 1) + ": joining" + aS + " and" + bS + ", average length: " + quote);

            /**
             *
             * Make new qN and conversion list (cL)
             *
             */
            ArrayList<BinaryTree> aCN = new ArrayList<>();

            ArrayList<Integer> convert = new ArrayList<>();

            int aC = 0;

            for (int a = 0; a < n; a++) {

                if (a != bestA && a != bestB) {

                    aCN.add(cN.get(a));

                    convert.add(new Integer(aC));

                    aC++;

                } else {

                    convert.add(new Integer(n - 2));
                }
            }

            aCN.add(new InnerNode((BinaryTree) cN.get(bestA), (BinaryTree) cN.get(bestB)));

            /**
             *
             * So, conversion list and new cN ready
             *
             */
            /**
             *
             * Then, we must face the wrath of Khan by coursing through the old
             * qC-sC complex and creating a new one
             *
             */
            int[][] aQC = new int[n][n];
            double[][] aSC = new double[n][n];

            for (int a = 0; a < n; a++) {

                for (int b = 0; b < n; b++) {

                    aQC[a][b] = 0;
                    aSC[a][b] = 0;

                }

            }

            for (int a = 0; a < n - 1; a++) {

                for (int b = a + 1; b < n; b++) {

                    /**
                     *
                     * If none of these are bestA or bestB, assign to the
                     * converted index the current value minus the calculation
                     * of the four specific
                     *
                     * Else, do a new calculation and add
                     *
                     */
                    if ((a == bestA || a == bestB) && (b == bestA || b == bestB)) {
                        /**
                         *
                         * Both are joining, ignore
                         *
                         */
                    } else if (a == bestA || a == bestB) {

                        /**
                         *
                         * a is bestA, special case
                         *
                         * Add to position last, convert (b) the values from
                         * doing the loop of all (except b, bestA, bestB) unique
                         * paired with b, bestA
                         *
                         */
                        int count = 0;
                        double score = 0;

                        for (int c = 0; c < n - 1; c++) {

                            if (c != b && c != bestA && c != bestB) {

                                for (int d = c + 1; d < n; d++) {

                                    if (d != b && d != bestA && d != bestB) {

                                        ArrayList<Integer> aL = new ArrayList<>();
                                        ArrayList<Integer> bL = new ArrayList<>();
                                        ArrayList<Integer> cL = new ArrayList<>();
                                        ArrayList<Integer> dL = new ArrayList<>();

                                        cN.get(a).fillList(aL);
                                        cN.get(b).fillList(bL);
                                        cN.get(c).fillList(cL);
                                        cN.get(d).fillList(dL);

                                        QuartetWeights.MeanSumResult meanSumResult = theQuartetWeights.meanSum(aL, bL, cL, dL);

                                        count += meanSumResult.getCount();
                                        score += meanSumResult.getScore();
                                    }
                                }
                            }
                        }

                        aSC[convert.get(b)][n - 2] += score;
                        aQC[convert.get(b)][n - 2] += count;

                    } else if (b == bestA || b == bestB) {

                        /**
                         *
                         * b is bestA, special case
                         *
                         * Add to position last, convert (a) the values from
                         * doing the loop of all (except a, bestA, bestB) unique
                         * paired with a, bestA
                         *
                         */
                        int count = 0;
                        double score = 0;

                        for (int c = 0; c < n - 1; c++) {

                            if (c != a && c != bestA && c != bestB) {

                                for (int d = c + 1; d < n; d++) {

                                    if (d != a && d != bestA && d != bestB) {

                                        ArrayList<Integer> aL = new ArrayList<>();
                                        ArrayList<Integer> bL = new ArrayList<>();
                                        ArrayList<Integer> cL = new ArrayList<>();
                                        ArrayList<Integer> dL = new ArrayList<>();

                                        cN.get(a).fillList(aL);
                                        cN.get(b).fillList(bL);
                                        cN.get(c).fillList(cL);
                                        cN.get(d).fillList(dL);

                                        QuartetWeights.MeanSumResult meanSumResult = theQuartetWeights.meanSum(aL, bL, cL, dL);

                                        count += meanSumResult.getCount();
                                        score += meanSumResult.getScore();
                                    }

                                }

                            }

                        }

                        aSC[convert.get(a)][n - 2] += score;
                        aQC[convert.get(a)][n - 2] += count;

                    } else {

                        /**
                         *
                         * Happy, smiling standard case
                         *
                         * Here, subtract the specific four, then print to the
                         * new indices in the new matrices. That is, print to
                         * position convert (a), convert (b) the value of old
                         * (a, b) - calc of (a, b, bestA, bestB)
                         *
                         */
                        ArrayList<Integer> aL = new ArrayList<>();
                        ArrayList<Integer> bL = new ArrayList<>();
                        ArrayList<Integer> cL = new ArrayList<>();
                        ArrayList<Integer> dL = new ArrayList<>();

                        cN.get(a).fillList(aL);
                        cN.get(b).fillList(bL);
                        cN.get(bestA).fillList(cL);
                        cN.get(bestB).fillList(dL);

                        QuartetWeights.MeanSumResult meanSumResult = theQuartetWeights.meanSum(aL, bL, cL, dL);

                        aSC[convert.get(a)][convert.get(b)] = sC[a][b] - meanSumResult.getScore();
                        aQC[convert.get(a)][convert.get(b)] = qC[a][b] - meanSumResult.getCount();
                    }
                }
            }

            /**
             *
             * Having done this, all the places have been given what they need
             * and we may proceed until all is well
             *
             */
            cN = aCN;
            sC = aSC;
            qC = aQC;

        }

        /**
         *
         * Now, the cN is a binary tree
         *
         */
        return cN;

    }
}
