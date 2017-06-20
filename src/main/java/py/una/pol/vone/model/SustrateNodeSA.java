package py.una.pol.vone.model;

import java.util.ArrayList;

import py.una.pol.vone.simulator.model.SustrateNode;

public class SustrateNodeSA extends SustrateNode {

	private ArrayList<SustrateEdgeSA> adyacentes = new ArrayList<>();
	
	public SustrateNodeSA(){
		super();
	}
	
	public ArrayList<SustrateEdgeSA> getAdyacentesSA() {
		return adyacentes;
	}

	public void setAdyacentesSA(ArrayList<SustrateEdgeSA> adyacentes) {
		this.adyacentes = adyacentes;
	}
	
}
