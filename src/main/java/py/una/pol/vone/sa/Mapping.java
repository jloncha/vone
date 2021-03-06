package py.una.pol.vone.sa;

import py.una.pol.vone.simulator.model.SustrateNode;
import py.una.pol.vone.simulator.model.VirtualNode;

/**
 * Clase que representa una solucion
 * Nodo Virtual a Fisico
 * @author Jean
 *
 */
public class Mapping {
	private VirtualNode virtualNode;
	private SustrateNode sustrateNode;
	
	public Mapping(VirtualNode virtualNode, SustrateNode sustrateNode) {
		super();
		this.virtualNode = virtualNode;
		this.sustrateNode = sustrateNode;
	}
	public VirtualNode getVirtualNode() {
		return virtualNode;
	}
	public void setVirtualNode(VirtualNode virtualNode) {
		this.virtualNode = virtualNode;
	}
	public SustrateNode getSustrateNode() {
		return sustrateNode;
	}
	public void setSustrateNode(SustrateNode sustrateNode) {
		this.sustrateNode = sustrateNode;
	}
	
}
