package py.una.pol.vone.model;

import org.moeaframework.mymodel.VirtualEdge;

public class VirtualEdgeSA extends VirtualEdge{

	private VirtualNodeSA nodoUno;
    private VirtualNodeSA nodoDos;
    
	private boolean visitado;

	public VirtualEdgeSA() {
		super();
		// TODO Auto-generated constructor stub
	}

	public boolean isVisitado() {
		return visitado;
	}

	public void setVisitado(boolean visitado) {
		this.visitado = visitado;
	}

	public VirtualNodeSA getNodoUnoSA() {
		return nodoUno;
	}

	public void setNodoUnoSA(VirtualNodeSA nodoUno) {
		this.nodoUno = nodoUno;
	}

	public VirtualNodeSA getNodoDosSA() {
		return nodoDos;
	}

	public void setNodoDosSA(VirtualNodeSA nodoDos) {
		this.nodoDos = nodoDos;
	}
	
	
	
}
