package py.una.pol.vone.sa;

import java.util.List;
/**
 * Clase que representa las soluciones del problema
 * @author Jean
 *
 */

public class Solution {
	private List<Mapping> map;
	private Integer frecuencyUsed;
	private Integer cpuUsed;
	
	public Solution() {
		super();
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
	
	
}
