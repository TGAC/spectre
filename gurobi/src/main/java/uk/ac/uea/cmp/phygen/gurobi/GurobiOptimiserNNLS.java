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
package uk.ac.uea.cmp.phygen.gurobi;

import gurobi.*;
import org.kohsuke.MetaInfServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.uea.cmp.phygen.core.math.optimise.OptimiserException;
import uk.ac.uea.cmp.phygen.core.math.optimise.Problem;

/**
 *
 */
@MetaInfServices(uk.ac.uea.cmp.phygen.core.math.optimise.Optimiser.class)
public class GurobiOptimiserNNLS extends GurobiOptimiser {

    private static Logger log = LoggerFactory.getLogger(GurobiOptimiserNNLS.class);

    public GurobiOptimiserNNLS() throws OptimiserException {
        super();
    }

    @Override
    public GRBConstr[] addConstraints(Problem problem, GRBModel model, GRBVar[] grbVars) throws GRBException {

        GRBConstr[] constraints = new GRBConstr[grbVars.length];

        for (int i = 0; i < grbVars.length; i++) {

            GRBLinExpr expr = new GRBLinExpr();
            expr.addTerm(1.0, grbVars[i]);
            constraints[i] = model.addConstr(expr, GRB.EQUAL, 0.0, "c0");
        }

        return constraints;
    }

    @Override
    public GRBExpr addObjective(Problem problem, GRBModel model, GRBVar[] grbVars) throws GRBException {

        double[] nncs = problem.getNonNegativityConstraint();
        double[][] ssc = problem.getSolutionSpaceConstraint();

        GRBQuadExpr obj = new GRBQuadExpr();
        for (int i = 0; i < nncs.length; i++) {
            for (int j = 0; j < nncs.length; j++) {
                obj.addTerm(ssc[j][i], grbVars[j], grbVars[i]);
            }
            obj.addTerm(-2 * nncs[i], grbVars[i]);
        }
        model.setObjective(obj);

        return obj;
    }

}
