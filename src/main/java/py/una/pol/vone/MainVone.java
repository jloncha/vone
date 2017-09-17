package py.una.pol.vone;


import java.util.List;

import org.moeaframework.Executor;
import org.moeaframework.analysis.plot.Plot;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Solution;
import org.moeaframework.mymodel.SustrateNetwork;
import org.moeaframework.mymodel.VirtualNetwork;

import py.una.pol.vone.kshortestpath.Path;
import py.una.pol.vone.nsga.VoneNsgaII;
import py.una.pol.vone.util.CargarRed;

public class MainVone {

	public static void main(String[] args){
		CargarRed cargarRed = new CargarRed();
		SustrateNetwork network = cargarRed.redFisica;
		VirtualNetwork virtualNetwork = cargarRed.redVirtual;
		
		VoneNsgaII nsga = new VoneNsgaII();
		nsga.cargarParametros(4, 3, 1, network.getNroNodos(), virtualNetwork.getNroNodos(), network, virtualNetwork,
				2);
		
		NondominatedPopulation result = new Executor()
				.withProblemClass(VoneNsgaII.class)
				.withAlgorithm("NSGAII")
				.withProperty("populationSize", 50)
				.withProperty("withReplacement", true)
				.withProperty("operator", "hux+bf")
				.withProperty("atributoNuevo", "jean")
				//valor por defecto hux.rate 1
				.withProperty("hux.rate", 1)
				//valor por defecto 0.01
				.withProperty("bf.rate", 0.01)
				/*.withProperty("sbx.distributionIndex", 25.0)
				.withProperty("pm.rate", 0.6)
				.withProperty("pm.distributionIndex", 15.0) */
				.withMaxEvaluations(30000)
				.distributeOnAllCores()
				.run();
				
		//display the results
		String solucionVariable = "";
		
		 //new Plot().add("NSGAII", result).show(); 
		System.out.println("cantidad del result " + result.size());
		for (int i = 0; i < result.size(); i++) { 
			System.out.format("Solucion %s%n", i);
			Solution solution = result.get(i);
			
			if(!solution.violatesConstraints()){
				double[] objectives = solution.getObjectives();
				// negate objectives to return them to their maximized form
				//objectives = Vector.negate(objectives);
				System.out.println("Valor Funcion Obj 1:");
				System.out.println(objectives[0]);
				System.out.println("Valor Funcion Obj 2:");
				System.out.println(objectives[1]);
				System.out.println("Valor Funcion Obj 3:");
				System.out.println(objectives[2]);
				System.out.println("Valor Funcion Obj 4:");
				System.out.println(objectives[3]);
				System.out.println("Matriz solucion");
				solucionVariable = solution.getVariable(0).toString();
				System.out.println(solucionVariable);
				System.out.println("RED FISICA Inicial");
				System.out.println(solution.getAttribute("sustrateOriginal"));
				System.out.println("RED FISICA FINAL");
				System.out.println(solution.getAttribute("sustrateMapeada"));
				System.out.println("LISTA DE PATH");
				@SuppressWarnings("unchecked")
				List<Path> listPath = (List<Path>) solution.getAttribute("listPath");
				for (int j = 0; j < listPath.size(); j++) {
					System.out.println(listPath.get(j));	
				}
				//imprimir valores
				int k = 0;
				/*System.out.println("[");
				for (int h = 0; h < 3; h++) {
					for (int j = 0; j < 10; j++) {
						System.out.print(solucionVariable.charAt(k) + " ");
						k++;
					}
					System.out.println("");
				}
				System.out.println("]");*/
				//System.out.println(solution.getVariable(i));
				
			}
			
		}
		new Plot().add("NSGAII", result).show();
		
	}
}
