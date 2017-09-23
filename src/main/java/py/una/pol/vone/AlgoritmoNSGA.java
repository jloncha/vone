package py.una.pol.vone;

import java.util.ArrayList;
import java.util.List;

import org.moeaframework.Executor;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;

import py.una.pol.vone.kshortestpath.Path;
import py.una.pol.vone.nsga.SolucionMoea;
import py.una.pol.vone.nsga.VoneNsgaII;
import py.una.pol.vone.simulator.model.SustrateEdge;
import py.una.pol.vone.simulator.model.SustrateNetwork;
import py.una.pol.vone.simulator.model.SustrateNode;
import py.una.pol.vone.simulator.model.VirtualEdge;
import py.una.pol.vone.simulator.model.VirtualNetwork;
import py.una.pol.vone.util.CargarRed;
import py.una.pol.vone.util.MoeaUtil;

/*****
 * Clase que contiene los metodo principal del algoritmo
 * @author Jean
 *
 */
public class AlgoritmoNSGA {

	
	CargarRed cargarRed = new CargarRed();
	
	public AlgoritmoNSGA(){
		
	}
	
	/****
	 * Metodo principal que recibe la red virtual y la red fisica para resolver
	 * @param redFisica	red fisica sustrato
	 * @param redVirtual la red que se va a mapear
	 * @return 0 si fue mapeado, 1 caso contrario
	 */
	public static int moeaDinamico(SustrateNetwork redFisica, VirtualNetwork redVirtual) {
		
		double[] valSolFinal;
		int resp = 1;
		Integer posicionFinal = 0;
		double[] objectives;
		double[] objNormalizados;
		double sum = 0.0;
		Integer cantFSUtilizado = 0;
		Integer maxCPU = Integer.MIN_VALUE;
		Double valSolucionOptima = new Double(String.valueOf(Integer.MAX_VALUE));
		boolean[][] matrizFinal;
		List<Path> listPath = new ArrayList<>();
		VoneNsgaII nsga = new VoneNsgaII();
		MoeaUtil util = new MoeaUtil();
		try {
			// Cargamos los parametros de NSGA
			// 4 objetivos, 3 restricciones, 1 variable y 2 caminos de kshortestpath 
			nsga.cargarParametros(4, 3, 1, redFisica.getNroNodos(), redVirtual.getNroNodos(), redFisica, redVirtual, 2);
			// Seteamos los valores del framework
			NondominatedPopulation result = new Executor().withProblemClass(VoneNsgaII.class).withAlgorithm("NSGAII")
					.withProperty("populationSize", 50).withProperty("withReplacement", true)
					.withProperty("operator", "hux+bf")
					// valor por defecto hux.rate 1
					.withProperty("hux.rate", 1)
					// valor por defecto 0.01
					.withProperty("bf.rate", 0.01).withMaxEvaluations(100000).distributeOnAllCores().run();
			String solucionVariable = "";
			valSolFinal = new double[result.size()];
			for (int i = 0; i < result.size(); i++) {
				Solution solution = result.get(i);
				//Si la solucion cumple con todas las restricciones
				if (!solution.violatesConstraints()) {
					objectives = solution.getObjectives();
					objNormalizados = new double[solution.getObjectives().length];
					//Calculasmos la suma de cantidad de Requerimientos de la VR
					SolucionMoea solucionMoea = (SolucionMoea)solution.getAttribute("solucionMoea");
					SustrateNetwork redFinal = solucionMoea.getSustrateNetwork();
					for (VirtualEdge vn : redVirtual.getEnlacesVirtuales()) {
						sum = sum + vn.getCantidadFS();
					}
					//Calculamos la cantidad maxima de CPU asignada
					for (SustrateNode nodo : redFinal.getNodosFisicos()) {
						if (nodo.getCapacidadCPU() > maxCPU) {
							maxCPU = nodo.getCapacidadCPU();
						}
					}
					//Calculamos la cantidad de FS utilizados
					for (SustrateEdge edge : redFinal.getEnlacesFisicos()) {
						for (Integer k = 0; k < redFinal.getCantidadFS(); k++) {
							if (edge.getFrequencySlot()[k]) {
								cantFSUtilizado++;
							}
						}
					}
					// Normalizacion de los valores de la funcion objetivo 
					// Normalizacion de la primera funcion objetivo (uso de enlaces)
					objNormalizados[0] = (objectives[0] / (redFinal.getEnlacesFisicos().size() * sum));
					// Normalizacion del segundo objetivo (fragmentacion)
					objNormalizados[1] = (objectives[1]
							/ (redFinal.getCantidadFS() * ((redFinal.getEnlacesFisicos().size()) - 1)));
					// Normalizacion del tercer objetivo (balance de uso de CPU)
					objNormalizados[2] = (objectives[2] / maxCPU);
					// Normalizamos el cuarto Objetivo (balance de enlaces)
					objNormalizados[3] = (objectives[3] / redFinal.getCantidadFS());
					// Pasamos a calcular su distancia al eje
					double finalVal = Math.sqrt(Math.pow(objNormalizados[0], 2) + Math.pow(objNormalizados[1], 2)
							+ Math.pow(objNormalizados[2], 2) + Math.pow(objNormalizados[3], 2));
					valSolFinal[i] = finalVal;
					solucionVariable = solution.getVariable(0).toString();
				}
			}
			//Seleccionamos el menor valor, que sera nuestra solucion final (mas cercano al origen)
			for (Integer cont = 0; cont < valSolFinal.length; cont++) {
				if (valSolucionOptima > valSolFinal[cont]) {
					valSolucionOptima = valSolFinal[cont];
					posicionFinal = cont;
				}
			}
			Solution solFinalElegida = result.get(posicionFinal);
			SolucionMoea solucionMoea = (SolucionMoea)solFinalElegida.getAttribute("solucionMoea");
			if (solucionMoea != null) {
				SustrateNetwork redFinalEleg = solucionMoea.getSustrateNetwork();
				resp = 0;
				//Procedemos a asignar los valores en la red virtual
				matrizFinal =util.generateMat(EncodingUtils.getBinary(solFinalElegida.getVariable(0)), redVirtual.getEnlacesVirtuales().size(), redFisica.getNodosFisicos().size());
				//Mapeamos a los nodos fisicos
				for(int i= 0; i<redVirtual.getEnlacesVirtuales().size(); i++){
					for (int j = 0; j < redFisica.getNodosFisicos().size(); j++) {
						if(matrizFinal[i][j]){
							redVirtual.getNodosVirtuales().get(i).setNodoFisico(redFisica.getNodosFisicos().get(j));
						}
					}
				}
				//cargamos los enlaces
				for(Integer z=0;z<redVirtual.getEnlacesVirtuales().size(); z++ ){
					//recorremos todos los nodos y buscamos en los paths
					for(VirtualEdge enlaceSolucion : solucionMoea.getVirtualEdge()){
						if(enlaceSolucion.getIdentificador() == redVirtual.getEnlacesVirtuales().get(z).getIdentificador()){
							redVirtual.getEnlacesVirtuales().get(z).setEnlaceFisico(enlaceSolucion.getEnlaceFisico());
						}
					}
				}
				redVirtual.setMapeado(true);
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return resp;
	}
	
}
