package py.una.pol.vone.model;

import java.util.ArrayList;

import org.moeaframework.mymodel.VirtualNode;

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
