package py.una.pol.vone.nsga;

import org.moeaframework.mymodel.SustrateEdge;
import org.moeaframework.mymodel.SustrateNetwork;

public class Objetivo4 {

	private Double fitness;

	public Objetivo4() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Double getFitness() {
		return fitness;
	}

	public void setFitness(Double fitness) {
		this.fitness = fitness;
	}
	
	
	public void getEvaluacion(SustrateNetwork redSustrato){
		Double minSlot = Double.MAX_VALUE, maxSlot = Double.MIN_VALUE;
		//Double sum = 0.0;
		for (SustrateEdge edge : redSustrato.getEnlacesFisicos()) {
			Double sum = 0.0;
			for (int i = 0; i < edge.getFrequencySlot().length; i++) {
				if(edge.getFrequencySlot()[i]){
					sum += 1;
					
				}
			}
			if(sum > maxSlot){
				maxSlot = new Double(sum);
			}
			if(sum < minSlot){
				minSlot = new Double(sum);
			}
		}
		this.fitness =  maxSlot - minSlot;
	}
}
