package py.una.pol.vone.model;

import java.util.ArrayList;

import org.moeaframework.mymodel.VirtualNetwork;

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
