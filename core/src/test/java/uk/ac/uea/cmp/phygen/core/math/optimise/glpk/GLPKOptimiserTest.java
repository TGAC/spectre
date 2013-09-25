package uk.ac.uea.cmp.phygen.core.math.optimise.glpk;

import org.junit.Test;
import uk.ac.uea.cmp.phygen.core.math.optimise.*;
import uk.ac.uea.cmp.phygen.core.math.optimise.apache.ApacheOptimiser;

import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: dan
 * Date: 22/09/13
 * Time: 21:31
 * To change this template use File | Settings | File Templates.
 */
public class GLPKOptimiserTest {


    @Test
    public void testAcceptsIdentifier() throws OptimiserException {

        Optimiser glpk = new GLPKOptimiser();

        assertTrue(glpk.acceptsIdentifier("glpk"));
        assertTrue(glpk.acceptsIdentifier("Glpk"));
        assertTrue(glpk.acceptsIdentifier("GLPK"));
        assertTrue(glpk.acceptsIdentifier("uk.ac.uea.cmp.phygen.core.math.optimise.glpk.GLPKOptimiser"));
    }


    @Test
    public void testNullOptimise() throws OptimiserException {

        Optimiser glpk = OptimiserFactory.getInstance().createOptimiserInstance("glpk", Objective.LINEAR);

        Problem problem = new Problem();

        //double[] solution = glpk.optimise(problem);

        assertTrue(true);
    }
}