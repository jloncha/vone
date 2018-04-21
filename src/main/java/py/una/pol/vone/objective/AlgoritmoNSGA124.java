package py.una.pol.vone.objective;

import org.moeaframework.Executor;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;

import py.una.pol.vone.nsga.SolucionMoea;
import py.una.pol.vone.nsga.VoneNsgaII;
import py.una.pol.vone.simulator.model.SustrateEdge;
import py.una.pol.vone.simulator.model.SustrateNetwork;
import py.una.pol.vone.simulator.model.SustrateNode;
import py.una.pol.vone.simulator.model.VirtualEdge;
import py.una.pol.vone.simulator.model.VirtualNetwork;
import py.una.pol.vone.util.MoeaUtil;

public class AlgoritmoNSGA124 {

	public AlgoritmoNSGA124() {
		super();
	}
	
	/****
	 * Metodo principal que recibe la red virtual y la red fisica para resolver
	 * @param redFisica	red fisica sustrato
	 * @param redVirtual la red que se va a mapear
	 * @return 0 si fue mapeado, 1 caso de falta de cpu, 2 en caso de falta en enlaces
	 */
	public static int moeaDinamico(SustrateNetwork redFisica, VirtualNetwork redVirtual) {
		int resp = 2;
		VoneNsgaII nsga = new VoneNsgaII();
		nsga.cargarParametros(3, 4, 1, redFisica.getNroNodos(), redVirtual.getNroNodos(), redFisica, redVirtual, 2,
				true, true, false, true);

		try {
			double[] valSolFinal;

			Integer posicionFinal = 0;
			double[] objectives;
			double[] objNormalizados;
			double sum = 0.0;
			Integer cantFSUtilizado = 0;
			Integer maxCPU = Integer.MIN_VALUE;
			Double valSolucionOptima = Double.MAX_VALUE;
			boolean[][] matrizFinal;
			// List<Path> listPath = new ArrayList<>();

			MoeaUtil util = new MoeaUtil();

			// Cargamos los parametros de NSGA
			// 2 objetivos, 3 restricciones, 1 variable y 2 caminos de
			// kshortestpath - costo y fragmentacion

			// Seteamos los valores del framework
			NondominatedPopulation result = new Executor().withProblemClass(VoneNsgaII.class).withAlgorithm("NSGAII")
					.withProperty("populationSize", 50).withProperty("withReplacement", true)
					.withProperty("operator", "hux+bf").withProperty("m", redVirtual.getNroNodos())
					.withProperty("n", redFisica.getNroNodos())
					// valor por defecto hux.rate 1
					.withProperty("hux.rate", 1)
					// valor por defecto 0.01
					.withProperty("bf.rate", 0.01).withMaxEvaluations(200).distributeOnAllCores().run();
			// String solucionVariable = "";
			valSolFinal = new double[result.size()];

			for (int i = 0; i < result.size(); i++) {
				Solution solution = result.get(i);
				// Si la solucion cumple con todas las restricciones
				if (!solution.violatesConstraints()) {
					objectives = solution.getObjectives();
					objNormalizados = new double[solution.getObjectives().length];
					// Calculasmos la suma de cantidad de Requerimientos de la
					// VR
					SolucionMoea solucionMoea = (SolucionMoea) solution.getAttribute("solucionMoea");
					SustrateNetwork redFinal = solucionMoea.getSustrateNetwork();
					for (VirtualEdge vn : redVirtual.getEnlacesVirtuales()) {
						sum = sum + vn.getCantidadFS();
					}
					// Calculamos la cantidad maxima de CPU asignada
					for (SustrateNode nodo : redFinal.getNodosFisicos()) {
						if (nodo.getCapacidadCPU() > maxCPU) {
							maxCPU = nodo.getCapacidadCPU();
						}
					}
					// Calculamos la cantidad de FS utilizados
					for (SustrateEdge edge : redFinal.getEnlacesFisicos()) {
						for (Integer k = 0; k < redFinal.getCantidadFS(); k++) {
							if (edge.getFrequencySlot()[k]) {
								cantFSUtilizado++;
							}
						}
					}
					// Normalizacion de los valores de la funcion objetivo de
					// acuerdo al objetivo seleccionado
					// Normalizacion de la primera funcion objetivo (uso de
					// enlaces)
					objNormalizados[0] = (objectives[0] / (redFinal.getEnlacesFisicos().size() * sum));
					// Normalizacion del segundo objetivo (fragmentacion)
					objNormalizados[1] = (objectives[1]
							/ (redFinal.getCantidadFS() * ((redFinal.getEnlacesFisicos().size()) - 1)));
					// Normalizacion del tercer objetivo (balance de uso de CPU)
					//objNormalizados[2] = (objectives[2] / maxCPU);
					// Normalizamos el cuarto Objetivo (balance de enlaces)
					objNormalizados[2] = (objectives[2] /
					redFinal.getCantidadFS());
					// Pasamos a calcular su distancia al eje
					double finalVal = Math.sqrt(Math.pow(objNormalizados[0], 2) + Math.pow(objNormalizados[1], 2) + Math.pow(objNormalizados[2], 2));
					valSolFinal[i] = finalVal;
					// solucionVariable = solution.getVariable(0).toString();
				}
			}
			// Seleccionamos el menor valor, que sera nuestra solucion final
			// (mas cercano al origen)
			for (Integer cont = 0; cont < valSolFinal.length; cont++) {
				if (valSolucionOptima > valSolFinal[cont]) {
					valSolucionOptima = valSolFinal[cont];
					posicionFinal = cont;
				}
			}
			// si cumple esta condicion se verifica el motivo de los rechazos
			Solution solFinalElegida = result.get(posicionFinal);
			SolucionMoea solucionMoea = (SolucionMoea) solFinalElegida.getAttribute("solucionMoea");
			if (solucionMoea != null) {
				resp = 0;
				// Procedemos a asignar los valores en la red virtual
				matrizFinal = util.generateMat(EncodingUtils.getBinary(solFinalElegida.getVariable(0)),
						redVirtual.getNodosVirtuales().size(), redFisica.getNodosFisicos().size());
				// System.out.println("matriz final " +
				// solFinalElegida.getVariable(0));
				// System.out.println("Red Fisica" +
				// solucionMoea.getSustrateNetwork());
				// Mapeamos a los nodos fisicos
				for (int i = 0; i < redVirtual.getNodosVirtuales().size(); i++) {
					for (int j = 0; j < redFisica.getNodosFisicos().size(); j++) {
						if (matrizFinal[i][j]) {
							redVirtual.getNodosVirtuales().get(i).setNodoFisico(redFisica.getNodosFisicos().get(j));
							redVirtual.getNodosVirtuales().get(i).setMapeado(true);
						}
					}
				}
				// cargamos los enlaces
				for (Integer z = 0; z < redVirtual.getEnlacesVirtuales().size(); z++) {
					// recorremos todos los nodos y buscamos en los paths
					for (VirtualEdge enlaceSolucion : solucionMoea.getVirtualEdge()) {
						if (enlaceSolucion.getIdentificador() == redVirtual.getEnlacesVirtuales().get(z)
								.getIdentificador()) {
							redVirtual.getEnlacesVirtuales().get(z).setEnlaceFisico(enlaceSolucion.getEnlaceFisico());
							redVirtual.getEnlacesVirtuales().get(z).setMapeado(true);
							redVirtual.getEnlacesVirtuales().get(z)
									.setPosicionFisica(enlaceSolucion.getPosicionFisica());
						}
					}
				}
				redVirtual.setMapeado(true);
				// break;
			} else {
				if (solFinalElegida.getConstraint(2) == 2) {
					resp = 1;
				}
				if (solFinalElegida.getConstraint(3) == 3) {
					resp = 2;
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (resp == 1) {
			// System.out.println("RECHAZO");
		}
		return resp;
	
	}
}
