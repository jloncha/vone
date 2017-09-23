package py.una.pol.vone;


import org.moeaframework.Executor;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Solution;

import py.una.pol.vone.nsga.SolucionMoea;
import py.una.pol.vone.nsga.VoneNsgaII;
import py.una.pol.vone.simulator.model.SustrateEdge;
import py.una.pol.vone.simulator.model.SustrateNetwork;
import py.una.pol.vone.simulator.model.SustrateNode;
import py.una.pol.vone.simulator.model.VirtualEdge;
import py.una.pol.vone.simulator.model.VirtualNetwork;
import py.una.pol.vone.util.CargarRed;

public class MainVone {

	public static void main(String[] args){
		double[] valSolFinal;
		Integer cont = 0;
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
				//valor por defecto hux.rate 1
				.withProperty("hux.rate", 1)
				//valor por defecto 0.01
				.withProperty("bf.rate", 0.01)
				/*.withProperty("sbx.distributionIndex", 25.0)
				.withProperty("pm.rate", 0.6)
				.withProperty("pm.distributionIndex", 15.0) */
				.withMaxEvaluations(100000)
				.distributeOnAllCores()
				.run();
				
		//display the results
		String solucionVariable = "";
		
		 //new Plot().add("NSGAII", result).show(); 
		System.out.println("cantidad del result " + result.size());
		valSolFinal = new double [result.size()];
		for (int i = 0; i < result.size(); i++) { 
			System.out.format("Solucion %s%n", i);
			Solution solution = result.get(i);
			SolucionMoea solucionMoea = (SolucionMoea)solution.getAttribute("solucionMoea");
			
			if(!solution.violatesConstraints()){
				double[] objectives = solution.getObjectives();
				double[] objNormalizados = new double[solution.getObjectives().length];
				double sum = 0.0;
				Integer cantFSUtilizado = 0;
				Integer maxCPU = Integer.MIN_VALUE;
				for(VirtualEdge vn : virtualNetwork.getEnlacesVirtuales()){
					sum = sum + vn.getCantidadFS();
				}
				
				SustrateNetwork redFinal = solucionMoea.getSustrateNetwork();
				for(SustrateNode nodo : redFinal.getNodosFisicos()){
					if(nodo.getCapacidadCPU()>maxCPU){
						maxCPU = nodo.getCapacidadCPU();
					}
				}
				for(SustrateEdge edge : redFinal.getEnlacesFisicos()){
					for(Integer k= 0; k<redFinal.getCantidadFS(); k++){
						if(edge.getFrequencySlot()[k]){
							cantFSUtilizado++;
						}
					}
				}
				
				// negate objectives to return them to their maximized form
				//objectives = Vector.negate(objectives);
				//Normalizacion de los valores de la funcion objetivo
				//Normalizacion de la primera funcion objetivo
				objNormalizados[0] = (objectives[0]/(redFinal.getEnlacesFisicos().size()*sum));
				//Normalizacion del segundo objetivo
				objNormalizados[1]= (objectives[1]/(redFinal.getCantidadFS()*((redFinal.getEnlacesFisicos().size())-1)));
				//Normalizacion del tercer objetivo
				objNormalizados[2]= (objectives[2]/maxCPU);
				//Normalizamos el 4to Objetivo
				objNormalizados[3]= (objectives[3]/redFinal.getCantidadFS());
				//Pasamos a calcular su distancia al eje
				double finalVal= Math.sqrt(Math.pow(objNormalizados[0], 2) + Math.pow(objNormalizados[1],2) 
				+ Math.pow(objNormalizados[2], 2) + Math.pow(objNormalizados[3],2) );
				valSolFinal[cont] = finalVal;
				cont++;
				/*System.out.println("Valor Funcion Obj 1: Val Normalizado");
				System.out.println(objectives[0] + " : " +objNormalizados[0]);
				System.out.println("Valor Funcion Obj 2: Val Normalizado");
				System.out.println(objectives[1] + " : " +objNormalizados[1]);
				System.out.println("Valor Funcion Obj 3: Val Normalizado");
				System.out.println(objectives[2] + " : " +objNormalizados[2]);
				System.out.println("Valor Funcion Obj 4: Val Normalizado");
				System.out.println(objectives[3]+ " : " +objNormalizados[3]);
				System.out.println("Valor Final " + finalVal);
				System.out.println("Matriz solucion");
				solucionVariable = solution.getVariable(0).toString();
				System.out.println(solucionVariable);
				System.out.println("RED FISICA Inicial");
				System.out.println(solution.getAttribute("sustrateOriginal"));
				System.out.println("RED FISICA FINAL");
				System.out.println(solucionMoea.getSustrateNetwork());
				System.out.println("LISTA DE PATH");
				List<Path> listPath = solucionMoea.getList();
				for (int j = 0; j < listPath.size(); j++) {
					System.out.println(listPath.get(j));	
				}*/
				
			}
			
		}
		Integer posicionFinal = 0;
		Double valSolucionOptima= new Double(String.valueOf(Integer.MAX_VALUE));
		for(cont = 0; cont<valSolFinal.length; cont++){
			if(valSolucionOptima>valSolFinal[cont]){
				valSolucionOptima= valSolFinal[cont];
				posicionFinal = cont;
			}
		}
		System.out.println("Valor Solucion Elegida: " + valSolucionOptima);
		Solution solFinalElegida = result.get(posicionFinal);
		SolucionMoea solucionMoea = (SolucionMoea)solFinalElegida.getAttribute("solucionMoea");
		if(solucionMoea != null){
			SustrateNetwork redFinalEleg = solucionMoea.getSustrateNetwork();
			//System.out.println("################### " + solucionMoea.getVirtualEdge());
			System.out.println("Solucion Elegida: " + redFinalEleg.toString());
		}else{
			System.out.println("No se encontro solucion");
		}
		//new Plot().add("NSGAII", result).show();
		
	}
}
