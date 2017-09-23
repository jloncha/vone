package py.una.pol.vone.sa;

import java.util.ArrayList;
import java.util.List;

import py.una.pol.vone.simulator.model.SustrateNetwork;
/**
 * Clase que representa las soluciones del problema
 * @author Jean
 *
 */

public class Solution {
	private List<Mapping> map;
	private Integer frecuencyUsed;
	private Integer cpuUsed;
	private SustrateNetwork sustrateNetwork;
	
	public Solution() {
		super();
		this.map = new ArrayList<Mapping>();
	}
	public List<Mapping> getMap() {
		return map;
	}
	public void setMap(List<Mapping> map) {
		this.map = map;
	}
	public Integer getFrecuencyUsed() {
		return frecuencyUsed;
	}
	public void setFrecuencyUsed(Integer frecuencyUsed) {
		this.frecuencyUsed = frecuencyUsed;
	}
	public Integer getCpuUsed() {
		return cpuUsed;
	}
	public void setCpuUsed(Integer cpuUsed) {
		this.cpuUsed = cpuUsed;
	}
	public SustrateNetwork getSustrateNetwork() {
		return sustrateNetwork;
	}
	public void setSustrateNetwork(SustrateNetwork sustrateNetwork) {
		this.sustrateNetwork = sustrateNetwork;
	}
	
}
