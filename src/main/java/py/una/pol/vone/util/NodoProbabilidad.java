package py.una.pol.vone.util;

public class NodoProbabilidad {
	private Integer idNodo;
	private Double minProbabilidad;
	private Double maxProbabilidad;
	public NodoProbabilidad(Integer idNodo, Double minProbabilidad, Double maxProbabilidad) {
		super();
		this.idNodo = idNodo;
		this.minProbabilidad = minProbabilidad;
		this.maxProbabilidad = maxProbabilidad;
	}
	public Integer getIdNodo() {
		return idNodo;
	}
	public void setIdNodo(Integer idNodo) {
		this.idNodo = idNodo;
	}
	public Double getMinProbabilidad() {
		return minProbabilidad;
	}
	public void setMinProbabilidad(Double minProbabilidad) {
		this.minProbabilidad = minProbabilidad;
	}
	public Double getMaxProbabilidad() {
		return maxProbabilidad;
	}
	public void setMaxProbabilidad(Double maxProbabilidad) {
		this.maxProbabilidad = maxProbabilidad;
	}
	
	
}
