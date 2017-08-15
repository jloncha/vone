package py.una.pol.vone;

import org.moeaframework.Executor;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.problem.AbstractProblem;


/**
 * Hello world!
 *
 */
public class SrinivasRun 
{
	
	public static class SrinivasProblem extends AbstractProblem {

		public SrinivasProblem() {
			super(2, 2, 2);
		}

		@Override
		public void evaluate(Solution solution) {
			double x = EncodingUtils.getReal(solution.getVariable(0));
			double y = EncodingUtils.getReal(solution.getVariable(1));
			double f1 = Math.pow(x - 2.0, 2.0) + Math.pow(y - 1.0, 2.0) + 2.0;
			double f2 = 9.0*x - Math.pow(y - 1.0, 2.0);
			double c1 = Math.pow(x, 2.0) + Math.pow(y, 2.0) - 225.0;
			double c2 = x - 3.0*y + 10.0;
			
			
			solution.setObjective(0, f1);
			solution.setObjective(1, f2);
			solution.setConstraint(0, c1 <= 0.0 ? 0.0 : c1);
			solution.setConstraint(1, c2 <= 0.0 ? 0.0 : c2);
		}
		
		@Override
		public Solution newSolution(){
			Solution solution = new Solution(2, 2, 2);
			
			solution.setVariable(0, EncodingUtils.newReal(-20.0, 20.0));
			solution.setVariable(1, EncodingUtils.newReal(-20.0, 20.0));
			return solution;
		}
		
	}
	
	public static void main(String[] args) {
		//configure and run the DTLZ2 function
		NondominatedPopulation result = new Executor()
				.withProblemClass(SrinivasProblem.class)
				.withAlgorithm("NSGAII")
				.withProperty("populationSize", 50)
				.withProperty("withReplacement", true)
				.withMaxEvaluations(10000)
				.run();
				
		//display the results
		System.out.format("Objective1  Objective2%n");
		
		 //new Plot().add("NSGAII", result).show(); 
		 
		for (Solution solution : result) {
			if(!solution.violatesConstraints()){
				System.out.printf("%10.3f      %10.3f%n", 
						//EncodingUtils.getReal(solution.getVariable(0)), 
						solution.getObjective(0), 
						solution.getObjective(1));
				/*System.out.format("%.4f      %.4f%n",
						solution.getObjective(0),
						solution.getObjective(1));*/
			}
			
		}
	}
}
