package py.una.pol.vone;

import org.moeaframework.Executor;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.problem.AbstractProblem;

public class VoneRun {
	public static class VoneProblem extends AbstractProblem {

		public static int m = 3;  //cant de nodos virtuales (filas)
		
		public static int n = 4;  //cant de nodos fisicos (columnas)
		
		public static int p = 3;  //cant de enlaces virtuales (filas)
		
		public static int q = 7;  //cant de enlaces fisicos (columnas)
		
		public static boolean[][] matEnlaces = {{false, false, true, true, false, true, false}, 
												{true, false, true, false, true, true, false}, 
												{false, false, true, false, false, false, true}};
		
		public static Integer[] requerimientoCPU = {9, 10, 7};
		
		public static Integer[] capacidadCPU = {20, 18, 25, 23};
		
		public static Integer[] requerimientoEnlace = {3, 5, 1};
		
		public static Integer[] capacidadEnlace = {10, 15, 18, 17, 12, 13, 9};
	 
		public VoneProblem() {
			super(1, 1, m + n);
		}

		@Override
		public void evaluate(Solution solution) {
			boolean[][] d = BitMatrix.getBinary(solution.getVariable(0));
			System.out.println("d1 \n" +solution.getVariable(0));
			double[] f = new double[1];
			double[] g = {2, 2, 2};
			double[] h = {2, 2, 2, 2};
			double sum = 0;
			double cons = 0.0;
			for (int i = 0; i < m; i++) {
				for (int j = 0; j < n; j++) {
					if(d[i][j]){
						sum = sum + capacidadCPU[j] + requerimientoEnlace[i];
						cons += 1.0;
					}
				}
				if(cons == 1){
					g[i] = 1.0;
				}
				cons = 0.0;
			}
			cons = 0.0;
			for(int j = 0; j < n; j++){
				for(int i = 0; i< m; i++){
					if(d[i][j]){
						cons += 1.0;
					}
				}
				if(cons == 1 || cons == 0){
					h[j] = 1.0;
				}
				cons = 0.0;
			}

			f[0] = sum;
			solution.setObjectives(f);
			for (int i = 0; i < m; i++) {
				//establece en 0 si la condicion se cumple, cualquier otro valor la condicion no se cumple
				solution.setConstraint(i, g[i] == 1.0 ? 0.0 : g[i]);
			}
			for (int j = 0; j < n; j++) {
				//establece en 0 si la condicion se cumple, cualquier otro valor la condicion no se cumple
				solution.setConstraint(m + j, h[j] <= 1.0 ? 0.0 : h[j]);
			}
			//solution.setConstraints(g);
		}
		
		@Override
		public Solution newSolution(){
			Solution solution = new Solution(1, 1, m + n);
			solution.setVariable(0, new BitMatrix(m, n));
			//System.out.println("instancia 1 " +solution.getVariable(0).toString());
			
			return solution;
		}
		
	}
	
	public static void main(String[] args) {
		
		NondominatedPopulation result = new Executor()
				.withProblemClass(VoneProblem.class)
				.withAlgorithm("NSGAII")
				.withProperty("populationSize", 50)
				.withProperty("withReplacement", false)
				.withProperty("operator", "hux+bf")
				/*.withProperty("sbx.rate", 0.9)
				.withProperty("sbx.distributionIndex", 25.0)
				.withProperty("pm.rate", 0.6)
				.withProperty("pm.distributionIndex", 15.0) */
				.withMaxEvaluations(100)
				.distributeOnAllCores()
				.run();
				
		//display the results
		System.out.format("Objective1%n");
		
		 //new Plot().add("NSGAII", result).show(); 
		 
		for (int i = 0; i < result.size(); i++) { 
			Solution solution = result.get(i);
			if(!solution.violatesConstraints()){
				double[] objectives = solution.getObjectives();
				// negate objectives to return them to their maximized form
				//objectives = Vector.negate(objectives);
				System.out.println("Valor Solution :");
				System.out.println(objectives[i]);
				System.out.println("Matriz solucion");
				System.out.println(solution.getVariable(i));
			}
			
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
