package py.una.pol.vone.model;

import java.util.ArrayList;

import org.moeaframework.mymodel.SustrateNetwork;

public class SustrateNetworkSA extends SustrateNetwork {

	private ArrayList<SustrateNodeSA> nodosFisicos;
    private ArrayList<SustrateEdgeSA> enlacesFisicos;
	
	public SustrateNetworkSA(){
		super();
	}
	
	public ArrayList<SustrateNodeSA> getNodosFisicosSA() {
		return nodosFisicos;
	}

	public void setNodosFisicosSA(ArrayList<SustrateNodeSA> nodosFisicos) {
		this.nodosFisicos = nodosFisicos;
	}

	public ArrayList<SustrateEdgeSA> getEnlacesFisicosSA() {
		return enlacesFisicos;
	}

	public void setEnlacesFisicosSA(ArrayList<SustrateEdgeSA> enlacesFisicos) {
		this.enlacesFisicos = enlacesFisicos;
	}
	
	
}
