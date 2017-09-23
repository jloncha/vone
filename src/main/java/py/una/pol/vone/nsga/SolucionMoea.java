package py.una.pol.vone.nsga;

import java.io.Serializable;
import java.util.List;

import py.una.pol.vone.kshortestpath.Path;
import py.una.pol.vone.simulator.model.SustrateNetwork;
import py.una.pol.vone.simulator.model.VirtualEdge;

public class SolucionMoea implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double[] functions;
	private SustrateNetwork sustrateNetwork;
	private List<VirtualEdge> virtualEdge;
	List<Path> list;
	
	
	public SolucionMoea() {
		super();
		// TODO Auto-generated constructor stub
	}


	public double[] getFunctions() {
		return functions;
	}


	public void setFunctions(double[] resp) {
		this.functions = resp;
	}


	public SustrateNetwork getSustrateNetwork() {
		return sustrateNetwork;
	}


	public void setSustrateNetwork(SustrateNetwork sustrateNetwork) {
		this.sustrateNetwork = sustrateNetwork;
	}


	public List<Path> getList() {
		return list;
	}


	public void setList(List<Path> list) {
		this.list = list;
	}


	public List<VirtualEdge> getVirtualEdge() {
		return virtualEdge;
	}


	public void setVirtualEdge(List<VirtualEdge> virtualEdge) {
		this.virtualEdge = virtualEdge;
	}

}
