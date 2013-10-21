package uk.ac.uea.cmp.phygen.core.math.optimise.apache;

import org.junit.Test;
import uk.ac.uea.cmp.phygen.core.math.optimise.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: dan
 * Date: 14/09/13
 * Time: 21:53
 * To change this template use File | Settings | File Templates.
 */
public class ApacheOptimiserTest {

    @Test
    public void testAcceptsIdentifier() throws OptimiserException {

        Optimiser apache = new ApacheOptimiser();

        assertTrue(apache.acceptsIdentifier("apache"));
        assertTrue(apache.acceptsIdentifier("Apache"));
        assertTrue(apache.acceptsIdentifier("APACHE"));
        assertTrue(apache.acceptsIdentifier("uk.ac.uea.cmp.phygen.core.math.optimise.apache.ApacheOptimiser"));
    }


    @Test
    public void testSimpleProblem() throws OptimiserException {

        Problem problem = new Problem("simple", new ArrayList<Variable>(), new ArrayList<Constraint>(),
                new Objective("z", Objective.ObjectiveDirection.MINIMISE, new Expression()));

        Solution solution = new ApacheOptimiser().optimise(problem);

        assertTrue(true);
    }


    /**
     * This example formulates and solves the following simple MIP model:
     *  maximize    x +   y + 2 z
     *  subject to  x + 2 y + 3 z <= 4
     *  x + y       >= 1
     *  x, y, z binary
     * @throws OptimiserException
     */
    @Test
    public void testMip1() throws OptimiserException {

        // Create the variables

        Variable x = new Variable("x", 0.0, new Bounds(0.0, 1.0, Bounds.BoundType.DOUBLE), Variable.VariableType.BINARY);
        Variable y = new Variable("y", 0.0, new Bounds(0.0, 1.0, Bounds.BoundType.DOUBLE), Variable.VariableType.BINARY);
        Variable z = new Variable("z", 0.0, new Bounds(0.0, 1.0, Bounds.BoundType.DOUBLE), Variable.VariableType.BINARY);

        List<Variable> variables = new ArrayList<>();
        variables.add(x);
        variables.add(y);
        variables.add(z);

        // Set objective: maximize x + y + 2 z
        Expression objExpr = new Expression().addTerm(1.0, x).addTerm(1.0, y).addTerm(2.0, z);
        Objective objective = new Objective("z", Objective.ObjectiveDirection.MAXIMISE, objExpr);

        // Setup constraints
        List<Constraint> constraints = new ArrayList<>();

        // Add constraint: x + 2 y + 3 z <= 4
        Expression c0 = new Expression().addTerm(1.0, x).addTerm(2.0, y).addTerm(3.0, z);
        constraints.add(new Constraint("c0", c0, Constraint.Relation.LTE, 4.0));

        // Add constraint: x + y >= 1
        Expression c1 = new Expression().addTerm(1.0, x).addTerm(1.0, y);
        constraints.add(new Constraint("c1", c1, Constraint.Relation.GTE, 1.0));

        // Create problem
        Problem problem = new Problem("mip1", variables, constraints, objective);

        // Solve
        Solution solution = new ApacheOptimiser().optimise(problem);

        // Check result
        assertTrue(solution.getSolution() == 3.0);

        double[] vals = solution.getVariableValues();

        assertTrue(vals[0] == 1.0);
        assertTrue(vals[1] == 0.0);
        assertTrue(vals[2] == 1.0);
    }
}
