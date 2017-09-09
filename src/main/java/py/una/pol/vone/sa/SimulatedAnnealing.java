package py.una.pol.vone.sa;

import java.util.List;

import org.moeaframework.mymodel.SustrateNetwork;
import org.moeaframework.mymodel.VirtualNetwork;

public class SimulatedAnnealing {

	private Double tempAct;
	private Double tempMin;
	private Double tempMax;
	private Integer coolingMechanism;   //alfa
	private Solution solActual;
	private Solution solCand;
	private List<Solution> listSolucion;
	
	
	public SimulatedAnnealing(){
		
	}
	
	public SustrateNetwork simulated(SustrateNetwork sustrateNetwork, VirtualNetwork virtualNetwork){
		
		Core core = new Core(virtualNetwork, sustrateNetwork);
		core.generateFirstSolution();
		//solActual = core en esta parte voy a setear mi solucion actual
		//y luego agrego esta solucion actual a mi lista de listSolucion
		while (tempAct >= tempMin){
			for (int i = 0; i < virtualNetwork.getNroNodos(); i++) {
				//del primer nodo que esta mapeado el vsn selecciono algun adyacente de el
				//y con eso construyo mi solCand, esto se puede obtener de sustrateNode.getadyacentes
				//con esto adyacente nuevo debo calcular de vuelta el fsused y el maxcpu de la clase solucion
				
				//Math.exp(dif/temp)
			}
		}
		
		return sustrateNetwork;
	}

	public Double getTempAct() {
		return tempAct;
	}

	public void setTempAct(Double tempAct) {
		this.tempAct = tempAct;
	}

	public Double getTempMin() {
		return tempMin;
	}

	public void setTempMin(Double tempMin) {
		this.tempMin = tempMin;
	}

	public Double getTempMax() {
		return tempMax;
	}

	public void setTempMax(Double tempMax) {
		this.tempMax = tempMax;
	}

	public Integer getCoolingMechanism() {
		return coolingMechanism;
	}

	public void setCoolingMechanism(Integer coolingMechanism) {
		this.coolingMechanism = coolingMechanism;
	}

	public Solution getSolActual() {
		return solActual;
	}

	public void setSolActual(Solution solActual) {
		this.solActual = solActual;
	}

	public Solution getSolCand() {
		return solCand;
	}

	public void setSolCand(Solution solCand) {
		this.solCand = solCand;
	}

	public List<Solution> getListSolucion() {
		return listSolucion;
	}

	public void setListSolucion(List<Solution> listSolucion) {
		this.listSolucion = listSolucion;
	}
}
