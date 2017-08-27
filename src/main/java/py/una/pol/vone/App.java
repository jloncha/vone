package py.una.pol.vone;

import org.moeaframework.Executor;
import org.moeaframework.analysis.plot.Plot;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.problem.AbstractProblem;


/**
 * Hello world!
 *
 */
public class App 
{
	
	public static class SchafferProblem extends AbstractProblem {

		public SchafferProblem() {
			super(1, 2);
		}

		@Override
		public void evaluate(Solution solution) {
			double x = EncodingUtils.getReal(solution.getVariable(0));
			
			solution.setObjective(0, Math.pow(x, 2.0));
			solution.setObjective(1, Math.pow(x - 2.0, 2.0));
		}
		
		@Override
		public Solution newSolution(){
			Solution solution = new Solution(1, 2);
			solution.setVariable(0, EncodingUtils.newReal(-3.0, 4.0));
			return solution;
		}
		
	}
	
	public static void main(String[] args) {
		//configure and run the DTLZ2 function
		NondominatedPopulation result = new Executor()
				.withProblemClass(SchafferProblem.class)
				.withAlgorithm("NSGAII")
				.withProperty("populationSize", 50)
				.withProperty("withReplacement", true)
				.withMaxEvaluations(10000)
				.run();
				
		//display the results
		System.out.format("Objective1  Objective2%n");
		
		 //new Plot().add("NSGAII", result).show(); 
		 
		for (Solution solution : result) {
			
			System.out.printf("%.5f => %.5f, %.5f\n", 
					EncodingUtils.getReal(solution.getVariable(0)), 
					solution.getObjective(0), 
					solution.getObjective(1));
			/*System.out.format("%.4f      %.4f%n",
					solution.getObjective(0),
					solution.getObjective(1));*/
		}
	}

    /*public static void main( String[] args )
    {
        //CargarRed red = new CargarRed();
        //Core problema = new Core(red.redVirtual, red.redFisica);
        NondominatedPopulation result = new Executor()
				.withProblem("UF1")
				.withAlgorithm("NSGAIII")
				.withMaxEvaluations(10000)
				 *       .withProperty("populationSize", 100)
 *       .withProperty("sbx.rate", 1.0)
 *       .withProperty("sbx.distributionIndex", 15.0)
 *       .withProperty("pm.rate", 0.05)
 *       .withProperty("pm.distributionIndex", 20.0)
				.run();
        	//display the results
      		System.out.format("Objective1  Objective2%n");
      		
      		for (Solution solution : result) {
      			System.out.format("%.4f      %.4f%n",
      					solution.getObjective(0),
      					solution.getObjective(1));
      		}


    }*/
}
