package py.una.pol.vone;

import org.moeaframework.Executor;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.problem.AbstractProblem;
import org.moeaframework.util.Vector;


/**
 * Hello world!
 *
 */
public class KnapshackRun 
{
	
	public static class KnapshackProblem extends AbstractProblem {

		public static int nsacks = 2;
		
		public static int nitems = 5;
		
		public static int[][] profit = {{2,1,6,5,3}, {3, 4, 2, 1, 3}};
		 
		public static int[][] weight = {{3,4, 1, 5, 5}, {3, 2, 5, 3, 2}};
		 
		public static int[] capacity = { 10, 8 };

		 
		public KnapshackProblem() {
			super(1, nsacks, nsacks);
		}

		@Override
		public void evaluate(Solution solution) {
			boolean[] d = EncodingUtils.getBinary(solution.getVariable(0));
			System.out.println("d 1 " + solution.getVariable(0));
			double[] f = new double[nsacks];
			double[] g = new double[nsacks];
			
			f[0] = 0.0;
			f[1] = 0.0;
			g[0] = 0.0;
			g[1] = 0.0;
			//calculate the profits and the weights for the knapsacks
			for (int i = 0; i < nitems; i++) {
				if(d[i]){
					for (int j = 0; j < nsacks; j++) {
						f[j] += profit[j][i];
						g[j] += weight[j][i];
					}
				}
			}
			
			for (int j = 0; j < nsacks; j++) {
				if(g[j] <= capacity[j]){
					g[j] = 0.0;
				} else {
					g[j] = g[j] - capacity[j];
				}
			}
			
			// negate the objectives since Knapsack is maximization 
			solution.setObjectives(Vector.negate(f)); 
			solution.setConstraints(g); 
		}
		
		@Override
		public Solution newSolution(){
			Solution solution = new Solution(1, nsacks, nsacks);
			
			solution.setVariable(0, EncodingUtils.newBinary(nitems));
			System.out.println("instancia 1 " +solution.getVariable(0).toString());
			return solution;
		}
		
	}
	
	public static void main(String[] args) {
		//configure and run the DTLZ2 function
		NondominatedPopulation result = new Executor()
				.withProblemClass(KnapshackProblem.class)
				.withAlgorithm("NSGAII")
				.withProperty("populationSize", 50)
				.withProperty("withReplacement", true)
				.withMaxEvaluations(10000)
				.distributeOnAllCores()
				.run();
				
		//display the results
		System.out.format("Objective1  Objective2%n");
		
		 //new Plot().add("NSGAII", result).show(); 
		 
		for (int i = 0; i < result.size(); i++) { 
			Solution solution = result.get(i);
			double[] objectives = solution.getObjectives();
			
			// negate objectives to return them to their maximized form
			objectives = Vector.negate(objectives);
			System.out.println("Solution " + (i+1) + ":");
			System.out.println(" Sack 1 Profit: " + objectives[0]);
			System.out.println(" Sack 2 Profit: " + objectives[1]);
			System.out.println(" Binary String: " + solution.getVariable(0));	
		}
	}
}
