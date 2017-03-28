package py.una.pol.vone.aco;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import py.una.pol.vone.simulator.model.SustrateNetwork;
import py.una.pol.vone.simulator.model.VirtualNetwork;

public abstract class ACO implements Observer{

	//Coeficiente de Evaporacion
	public static final double RHO= 0.5;
	//Hormiga Actual
	private List<Ant> ant;
	//Numero total de hormigas (cuantas soluciones se va a construir)
	private Integer numberOfAnts;
	//Matriz de feromonas
	List<List<Integer>> tau;
	//Numero de Iteraciones
	private Integer interations;
	//Id de la iteracion actual
	private Integer idInteracion;
	//Numero de hormigas que terminaron el tour
	private Integer finishedAnts;
	//Mejor Hormiga en realizar el tour
	private Ant bestAnt;
	//La red de enlaces virtuales sobre la que vamos a aplicar nuestra optimizacion
	private VirtualNetwork virtualNetwork;
	//Tambien necesitamos la red Fisica para Evaluar
	private SustrateNetwork sustrateNetwork;
	/**
	 * Constructor de nuestra clase
	 * @param numberOfAnts
	 * @param interactions
	 */
	public ACO(Integer numberOfAnts, Integer interactions) {
		super();
		if (numberOfAnts<=0){
			throw new IllegalArgumentException("Numero de hormigas no debe ser menor a 0");
		}
		if (interactions<=0){
			throw new IllegalArgumentException("Numero de iteraciones no puede ser menor a 0");
		}
		this.numberOfAnts = numberOfAnts;
		this.interations = interactions;
	}
	public Ant solve() {
		initializeData();
		while (!terminationCondition()) {
			constructAntsSolutions();
			updatePheromones();
			//daemonActions(); // optional
		}
		return bestAnt;
	}

	/***
	 * Metodo para inicializar todos los procedimientos
	 */
	private void initializeData() {
		initializePheromones();
		initializeAnts();		
	}
	
	/***
	 * Metodo para inicializar las feromonas
	 * Al principio todas las feromonas van a tener el mismo valor,
	 * lo unico que va a variar es la visibilidad
	 * La matriz es de mxn donde m es la cantidad de nodos virtuales y n la de fisicos
	 */
	private void initializePheromones() {
		this.tau = new ArrayList<List<Integer>>();
		for (Integer i=0; i<virtualNetwork.getNroNodos(); i++){
			List<Integer> redesFisicas = new ArrayList<Integer>();
			for(Integer j=0; j<sustrateNetwork.getNroNodos(); j++){
				redesFisicas.add(1);
			}
			tau.add(redesFisicas);
		}
	}
	/**
	 * Metodo que construye las soluciones
	 */
	private synchronized void constructAntsSolutions() {
		//Contruct Ant solutions
		for (int k = 0; k < numberOfAnts; k++) {
			Thread t = new Thread(ant.get(k), "Ant "+ ant.get(k).getIdAnt());
			t.start();
		}
		
		//Wait all ants finish your tour
		try{
			wait();
		}catch(InterruptedException ex){
			ex.printStackTrace();
		}	
	}
	
	private boolean terminationCondition() {
		return ++idInteracion > interations;
	}
	/***
	 * La parte de la evaluacion depende del problema que estemos
	 * abarcando, tenemos que adaptar para el VONE
	 */
	@Override
	public synchronized void update(Observable observable, Object obj) {
		Ant ant = (Ant) obj;

		ant.setTourLength(1); //Cambiar por la parte de evaluar que corresponde a nuestro problema
		//ant.tourLength = p.evaluate(ant);

		/*if (p.better(ant, bestAnt)) {
			bestAnt = ant.clone();
		}*/

		if (++finishedAnts == numberOfAnts) {
			// Continue all execution
			finishedAnts = 0;
			notify();
		}
	}

	public List<Ant> getAnt() {
		return ant;
	}
	public void setAnt(List<Ant> ant) {
		this.ant = ant;
	}
	public Integer getNumberOfAnts() {
		return numberOfAnts;
	}
	public void setNumberOfAnts(Integer numberOfAnts) {
		this.numberOfAnts = numberOfAnts;
	}
	public List<List<Integer>> getTau() {
		return tau;
	}
	public void setTau(List<List<Integer>> tau) {
		this.tau = tau;
	}
	public Integer getInteractions() {
		return interations;
	}
	public void setInteractions(Integer interactions) {
		this.interations = interactions;
	}
	public Integer getIdInteracion() {
		return idInteracion;
	}
	public void setIdInteracion(Integer idInteracion) {
		this.idInteracion = idInteracion;
	}
	public Integer getFinishedAnts() {
		return finishedAnts;
	}
	public void setFinishedAnts(Integer finishedAnts) {
		this.finishedAnts = finishedAnts;
	}
	public static double getRho() {
		return RHO;
	}
	public Ant getBestAnt() {
		return bestAnt;
	}
	public void setBestAnt(Ant bestAnt) {
		this.bestAnt = bestAnt;
	}
	public VirtualNetwork getVirtualNetwork() {
		return virtualNetwork;
	}
	public void setVirtualNetwork(VirtualNetwork virtualNetwork) {
		this.virtualNetwork = virtualNetwork;
	}
	public abstract void initializeAnts();
	
	private void updatePheromones() {
		globalUpdateRule();
	}

	public abstract void globalUpdateRule();
}
