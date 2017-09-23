package py.una.pol.vone.nsga;

import py.una.pol.vone.simulator.model.SustrateEdge;
import py.una.pol.vone.simulator.model.SustrateNetwork;

public class Objetivo2 {
	
	private Double fitness;

	public Objetivo2() {
		super();
	}

	/***
	 * metodo que se encarga de evaluar el fitness de una solucion
	 * @param redSustrato la red sobre la que se va a trabajar
	 * @throws Exception 
	 */
	public void getEvaluacion(SustrateNetwork redSustrato) throws Exception {
		/**
		 * Lo que evaluamos aqui es la formula de SUM (fsi/n) donde el FSI es el
		 * indice maximo de slot utilizado, y n es la cantidad de enlaces
		 */
		Double sumFSI = 0.0;
		// Recorremos todos los enlaces
		try {
			for (SustrateEdge enlace : redSustrato.getEnlacesFisicos()) {
				Double fsi = 0.0;
				for (Integer i = 0; i < enlace.getCantidadFS(); i++) {
					// Si es valor del FS es true, significa que esta ocupado
					// Entonces es un candidato a ser el FSI
					if (enlace.getFrequencySlot()[i]) {
						fsi = new Double(i) + 1;
					}
				}
				// Una vez recorrido todos los slots, ya tenemos el FSI
				// Pasamos a sumarle a nuestro contador de FSI
				sumFSI = sumFSI + fsi;
			}
			// Una vez recorrida toda la red, procedemos a dividir entre la
			// cantidad de enlaces
			this.fitness = new Double(sumFSI/(redSustrato.getEnlacesFisicos().size()));
		} catch (Exception ex) {
			throw new Exception("Error en evaluacion de objetivo de Fragmentacion");
		}
	}
	
	public Double getFitness() {
		return fitness;
	}

	public void setFitness(Double fitness) {
		this.fitness = fitness;
	}
	
	
}
