package py.una.pol.vone.nsga;

import org.moeaframework.mymodel.SustrateNetwork;
import org.moeaframework.mymodel.SustrateNode;

/***
 * Clase que se usa para representar el Objetivo enfocado en
 * la minimizacion de recursos usados en el nodo
 * @author Jean
 *
 */
public class Objetivo3 {
	private double fitness;

	public Objetivo3() {
		super();
	}

	/**
	 * Metodo que obtendra la evaluacion
	 * @param redSustrato
	 * @throws Exception 
	 */
	public void getEvaluacion(SustrateNetwork redSustrato) throws Exception {
		/**
		 * El objetivo aqui es balancear el uso la cantidad de recursos de Nodo
		 * utilizados Se describe basicamente por la formula maxCPU red - minCPU
		 * Red
		 */
		Integer maxCPU = 0;
		Integer minCPU = Integer.MAX_VALUE;
		try {
			// Recorremos todos los nodos de la red para ver cuales son los
			// maximos y minimos de utilizacion
			for (SustrateNode nodo : redSustrato.getNodosFisicos()) {
				if (nodo.capacidadActual() > maxCPU) {
					maxCPU = nodo.capacidadActual();
				}
				if (minCPU > nodo.capacidadActual()) {
					minCPU = nodo.capacidadActual();
				}
			}
			// Restamos los valores max y minimo de CPU, si esta balanceado esto
			// tenderá a cero
			fitness = new Double (maxCPU - minCPU);
		} catch (Exception ex) {
			throw new Exception("Error en evaluacion de objetivo balancear nodos");
		}
	}
	
	public double getFitness() {
		return fitness;
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
	}
	
	
	
}
