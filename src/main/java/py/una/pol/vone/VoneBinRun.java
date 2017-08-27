package py.una.pol.vone;

import org.moeaframework.Executor;
import org.moeaframework.analysis.plot.Plot;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.problem.AbstractProblem;

public class VoneBinRun {
	public static class VoneProblem extends AbstractProblem {

		public static int m = 3;  //cant de nodos virtuales (filas)
		
		public static int n = 10;  //cant de nodos fisicos (columnas)
		
		public static int p = 3;  //cant de enlaces virtuales (filas)
		
		public static int q = 7;  //cant de enlaces fisicos (columnas)
		
		public static boolean[][] matEnlaces = {{false, false, true, true, false, true, false}, 
												{true, false, true, false, true, true, false}, 
												{false, false, true, false, false, false, true}};
		
		public static Double[] requerimientoCPU = {9.0, 10.0, 7.0};
		
		public static Integer[] capacidadCPU = {20, 18, 25, 23, 19, 21, 17, 16, 24, 15};
		
		public static Integer[] requerimientoEnlace = {3, 5, 1};
		
		public static Integer[] capacidadEnlace = {10, 15, 18, 17, 12, 13, 9};
	 
		public VoneProblem() {
			super(1, 2, m+n);
		}

		@Override
		public void evaluate(Solution solution) {
			
			double[] f = new double[2];
			double[] g = {2.0, 2.0, 2.0};
			double[] h = {2.0, 2.0, 2.0, 2.0, 2.0, 2.0, 2.0, 2.0, 2.0, 2.0};
			//funcion objetivo 1
			double sum = 0;
			//funcion objetivo 2
			double capCpu = 0.0;
			double cons = 0.0;
			
			
			boolean[] d = EncodingUtils.getBinary(solution.getVariable(0));
			System.out.println("d: " + solution.getVariable(0));
			//convertir de array a matriz
			boolean[][] mat = new boolean[m][n]; 
			int cont = 0;
			for (int i = 0; i < m; i++) {
				for (int j = 0; j < n; j++) {
					mat[i][j] = d[cont];
					cont++;
				}
			}
			//imprimir valores
			/*int k = 0;
			System.out.println("d1 " +solution.getVariable(0));
			System.out.println("[");
			for (int i = 0; i < m; i++) {
				for (int j = 0; j < n; j++) {
					k = mat[i][j] ? 1:0;
					System.out.print(k + " ");
				}
				System.out.println("");
			}
			System.out.println("]");*/
			
			
			
			for (int i = 0; i < m; i++) {
				for (int j = 0; j < n; j++) {
					if(mat[i][j]){
						sum = Math.sqrt(sum + capacidadCPU[j] + requerimientoEnlace[i]);
						//capCpu += requerimientoCPU[i];
						cons += 1.0;
					}else {
						capCpu = (capCpu + requerimientoCPU[i] + capacidadCPU[j] + requerimientoEnlace[i]);
					}
				}
				if(cons == 1){
					g[i] = 1.0;
				}
				cons = 0.0;
			}
			//System.out.println("Funcion1 " + sum);
			//System.out.println("Funcion2 " + capCpu);
			cons = 0.0;
			for(int j = 0; j < n; j++){
				for(int i = 0; i< m; i++){
					if(mat[i][j]){
						cons += 1.0;
					}
				}
				if(cons == 1 || cons == 0){
					h[j] = 1.0;
				}
				cons = 0.0;
			}

			f[0] = sum;
			f[1] = capCpu;
			solution.setObjectives(f);
			for (int i = 0; i < m; i++) {
				//establece en 0 si la condicion se cumple, cualquier otro valor la condicion no se cumple
				solution.setConstraint(i, g[i] == 1.0 ? 0.0 : g[i]);
			}
			for (int j = 0; j < n; j++) {
				//establece en 0 si la condicion se cumple, cualquier otro valor la condicion no se cumple
				solution.setConstraint(m + j, h[j] <= 1.0 ? 0.0 : h[j]);
			}
			/*int c = 0;
			for (int i = 0; i < m; i++) {
				if(g[i] == 1){
					c = c + 1;
				}
			}
			if(c == m){
				
				System.out.println("d1 " +solution.getVariable(0));
				System.out.println("Funcion1 " + sum);
				System.out.println("Funcion2 " + capCpu);
				/*System.out.println("[");
				for (int i = 0; i < m; i++) {
					for (int j = 0; j < n; j++) {
						k = mat[i][j] ? 1:0;
						System.out.print(k + " ");
					}
					System.out.println("");
				}
				System.out.println("]");*/
			//}
			//solution.setConstraints(g);
		}
		
		@Override
		public Solution newSolution(){
			Solution solution = new Solution(1, 2, m + n);
			solution.setVariable(0, EncodingUtils.newBinary(m*n));
			//System.out.println("instancia 1 " +solution.getVariable(0).toString());
			
			return solution;
		}
		
	}
	
	public static void main(String[] args) {
		
		NondominatedPopulation result = new Executor()
				.withProblemClass(VoneProblem.class)
				.withAlgorithm("NSGAII")
				.withProperty("populationSize", 100)
				.withProperty("withReplacement", true)
				.withProperty("operator", "hux+bf")
				.withProperty("hux.rate", 0.9)
				.withProperty("bf.rate", 0.02)
				/*.withProperty("sbx.distributionIndex", 25.0)
				.withProperty("pm.rate", 0.6)
				.withProperty("pm.distributionIndex", 15.0) */
				.withMaxEvaluations(10000)
				.distributeOnAllCores()
				.run();
				
		//display the results
		String solucionVariable = "";
		
		 //new Plot().add("NSGAII", result).show(); 
		System.out.println("cantidad del result " + result.size());
		for (int i = 0; i < result.size(); i++) { 
			System.out.format("Objective %s%n", i);
			Solution solution = result.get(i);
			
			//if(!solution.violatesConstraints()){
				double[] objectives = solution.getObjectives();
				// negate objectives to return them to their maximized form
				//objectives = Vector.negate(objectives);
				System.out.println("Valor Funcion Obj 1:");
				System.out.println(objectives[0]);
				System.out.println("Valor Funcion Obj 2:");
				System.out.println(objectives[1]);
				System.out.println("Matriz solucion");
				solucionVariable = solution.getVariable(0).toString();
				System.out.println(solucionVariable);
				//imprimir valores
				int k = 0;
				System.out.println("[");
				for (int h = 0; h < 3; h++) {
					for (int j = 0; j < 10; j++) {
						System.out.print(solucionVariable.charAt(k) + " ");
						k++;
					}
					System.out.println("");
				}
				System.out.println("]");
				//System.out.println(solution.getVariable(i));
				
			//}
			
		}
		new Plot().add("NSGAII", result).show(); 
	}
}
