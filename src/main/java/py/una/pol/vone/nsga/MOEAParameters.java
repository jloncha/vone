package py.una.pol.vone.nsga;

import org.moeaframework.mymodel.SustrateNetwork;

public class MOEAParameters {

	private Integer nroObjetivos;
	private Integer nroRestricciones;
	private Integer nroVariableDecision;
	private Integer nodosFisicos;
	private Integer nodosVirtuales;
	private SustrateNetwork redSustrato;
	
	public MOEAParameters() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MOEAParameters(Integer nroObjetivos, Integer nroRestricciones, Integer nroVariableDecision,
			Integer nodosFisicos, Integer nodosVirtuales) {
		super();
		this.nroObjetivos = nroObjetivos;
		this.nroRestricciones = nroRestricciones;
		this.nroVariableDecision = nroVariableDecision;
		this.nodosFisicos = nodosFisicos;
		this.nodosVirtuales = nodosVirtuales;
	}

	public Integer getNroObjetivos() {
		return nroObjetivos;
	}

	public void setNroObjetivos(Integer nroObjetivos) {
		this.nroObjetivos = nroObjetivos;
	}

	public Integer getNroRestricciones() {
		return nroRestricciones;
	}

	public void setNroRestricciones(Integer nroRestricciones) {
		this.nroRestricciones = nroRestricciones;
	}

	public Integer getNroVariableDecision() {
		return nroVariableDecision;
	}

	public void setNroVariableDecision(Integer nroVariableDecision) {
		this.nroVariableDecision = nroVariableDecision;
	}

	public Integer getNodosFisicos() {
		return nodosFisicos;
	}

	public void setNodosFisicos(Integer nodosFisicos) {
		this.nodosFisicos = nodosFisicos;
	}

	public Integer getNodosVirtuales() {
		return nodosVirtuales;
	}

	public void setNodosVirtuales(Integer nodosVirtuales) {
		this.nodosVirtuales = nodosVirtuales;
	}

	public SustrateNetwork getRedSustrato() {
		return redSustrato;
	}

	public void setRedSustrato(SustrateNetwork redSustrato) {
		this.redSustrato = redSustrato;
	}
	
}
