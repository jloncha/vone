package py.una.pol.vone.model;

import java.util.ArrayList;

import py.una.pol.vone.simulator.model.VirtualNode;

public class VirtualNodeSA extends VirtualNode{
	
	private ArrayList<VirtualEdgeSA> adyacentes = new ArrayList<>();
	
	public VirtualNodeSA(){
		super();
	}

	public ArrayList<VirtualEdgeSA> getAdyacentesSA() {
		return adyacentes;
	}

	public void setAdyacentesSA(ArrayList<VirtualEdgeSA> adyacentes) {
		this.adyacentes = adyacentes;
	}
	
	
}
