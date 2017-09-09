package py.una.pol.vone.model;

import org.moeaframework.mymodel.SustrateEdge;

public class SustrateEdgeSA extends SustrateEdge {

	private SustrateNodeSA nodoUno;
    private SustrateNodeSA nodoDos;

	
	
	public SustrateEdgeSA() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SustrateNodeSA getNodoUnoSA() {
		return nodoUno;
	}

	public void setNodoUnoSA(SustrateNodeSA nodoUno) {
		this.nodoUno = nodoUno;
	}

	public SustrateNodeSA getNodoDosSA() {
		return nodoDos;
	}

	public void setNodoDosSA(SustrateNodeSA nodoDos) {
		this.nodoDos = nodoDos;
	}
	
	
}
