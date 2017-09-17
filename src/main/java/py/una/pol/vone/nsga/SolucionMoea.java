package py.una.pol.vone.nsga;

import java.io.Serializable;
import java.util.List;

import org.moeaframework.mymodel.SustrateNetwork;

import py.una.pol.vone.kshortestpath.Path;

public class SolucionMoea implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double[] functions;
	private SustrateNetwork sustrateNetwork;
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
}
