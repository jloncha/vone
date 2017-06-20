package py.una.pol.vone.model;

import java.util.ArrayList;

import py.una.pol.vone.simulator.model.VirtualNetwork;

public class VirtualNetworkSA extends VirtualNetwork{

	private ArrayList<VirtualNodeSA> nodosVirtuales = new ArrayList<>();
    private ArrayList<VirtualEdgeSA> enlacesVirtuales = new ArrayList<>();
    
	public VirtualNetworkSA(){
		super();
	}

	public ArrayList<VirtualNodeSA> getNodosVirtualesSA() {
		return nodosVirtuales;
	}

	public void setNodosVirtualesSA(ArrayList<VirtualNodeSA> nodosVirtuales) {
		this.nodosVirtuales = nodosVirtuales;
	}

	public ArrayList<VirtualEdgeSA> getEnlacesVirtualesSA() {
		return enlacesVirtuales;
	}

	public void setEnlacesVirtualesSA(ArrayList<VirtualEdgeSA> enlacesVirtuales) {
		this.enlacesVirtuales = enlacesVirtuales;
	}
	
	
	
}
