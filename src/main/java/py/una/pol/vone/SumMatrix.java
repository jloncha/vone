package py.una.pol.vone;

import org.moeaframework.Executor;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Solution;
import org.moeaframework.problem.AbstractProblem;
import org.moeaframework.util.Vector;

public class SumMatrix {
	public static class VoneProblem extends AbstractProblem {

		public static int matNodeRow = 3;
		
		public static int matNodeColumns = 5;
	 
		public VoneProblem() {
			super(1, 1);
		}

		@Override
		public void evaluate(Solution solution) {
			boolean[][] d = BitMatrix.getBinary(solution.getVariable(0));
			System.out.println("d1 \n" +solution.getVariable(0));
			double[] f = new double[1];
			//double[] g = new double[nsacks];
			double sum = 0;
			for (int i = 0; i < d.length; i++) {
				for (int j = 0; j < d[0].length; j++) {
					if(d[i][j]){
						sum+=1.0;
					}
				}
			}
			f[0] = sum;
			// negate the objectives since Knapsack is maximization
			solution.setObjectives(Vector.negate(f)); 
			//solution.setObjective(0, sum);
		}
		
		@Override
		public Solution newSolution(){
			Solution solution = new Solution(1, 1);
			solution.setVariable(0, new BitMatrix(matNodeRow, matNodeColumns));
			//System.out.println("instancia 1 " +solution.getVariable(0).toString());
			
			return solution;
		}
		
	}
	
	public static void main(String[] args) {
		
		NondominatedPopulation result = new Executor()
				.withProblemClass(VoneProblem.class)
				.withAlgorithm("NSGAII")
				.withProperty("populationSize", 50)
				.withProperty("withReplacement", true)
				.withProperty("operator", "ssx+replace")
				.withMaxEvaluations(10)
				.distributeOnAllCores()
				.run();
				
		//display the results
		System.out.format("Objective1%n");
		
		 //new Plot().add("NSGAII", result).show(); 
		 
		for (int i = 0; i < result.size(); i++) { 
			Solution solution = result.get(i);
			double[] objectives = solution.getObjectives();
			
			// negate objectives to return them to their maximized form
			objectives = Vector.negate(objectives);
			System.out.println("Solution " + (i+1) + ":");
			System.out.println(objectives[0]);
		}
		
		
		/*for (int i = 0; i < result.size(); i++) { 
			Solution solution = result.get(i);
			//double[] objectives = solution.getObjectives();
			objectives = Vector.negate(objectives);
			// negate objectives to return them to their maximized form
			//objectives = Vector.negate(objectives);
			System.out.println("Solution " + (i+1) + ":");
			System.out.println(" Sack 1 Profit: " + objectives[0]);
			System.out.printf("get_variable", 
					solution.getObjectives());
			///System.out.println(" Sack 2 Profit: " + objectives[1]);
			//System.out.println(" Binary String: " + solution.getVariable(0));	
		}*/
	}
}
