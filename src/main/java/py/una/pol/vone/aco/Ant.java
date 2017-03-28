package py.una.pol.vone.aco;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Random;

import py.una.pol.vone.simulator.model.VirtualNode;
/***
 * Clase que se utiliza para representar una hormiga
 * @author Jean
 *
 */
public abstract class Ant extends Observable implements Runnable{

	//Identificador de la hormiga
	private Integer idAnt;
	//Parametro alpha del problema(peso de las feromons)
	private Integer alpha;
	//Parametro beta del problema(peso de la visibilidad)
	private Integer beta;
	//La optimizacion que vamos a aplicar
	private ACO aco;
	//El tour que va a hacer una hormiga
	private List<Integer> tour = new ArrayList<Integer>();
	//Datos del nodo actual
	private VirtualNode currentNode;
	//Path del recorrido
	List<List<Integer>> path;
	//Lista de Nodos a visitar
	private List<VirtualNode> nodesToVisit = new ArrayList<VirtualNode>();
	//Cantidad de nodos visitados por la hormiga
	private Integer tourLength;
	
	public Ant(ACO aco) {
		this.aco = aco;
		reset();		
	}
	
	public void reset(){
		this.currentNode = new VirtualNode();
		this.tourLength = 0;
		this.nodesToVisit = new ArrayList<VirtualNode>();
		this.tour = new ArrayList<Integer>();
		this.path = new ArrayList<List<Integer>>();
	}
	
	@Override
	public void run() {
		init();
		explore();		
		setChanged();
		notifyObservers(this);
	}
	
	public void init(){
		Random randomGenerator = new Random();
		reset();
		this.currentNode = this.aco.getVirtualNetwork().getNodosVirtuales().get(randomGenerator.nextInt(this.aco.getVirtualNetwork().getNodosVirtuales().size()));
		this.currentNode = new VirtualNode();
		this.tour.add(new Integer(0));
		//this.aco.p.initializeTheMandatoryNeighborhood(this);
	}
	
	/**
	 * Construct the solutions
	 */
	public abstract void explore();

	/**
	 * Clone the ant
	 */
	public Ant clone() {
		return null;
	}

	public Integer getIdAnt() {
		return idAnt;
	}

	public void setIdAnt(Integer idAnt) {
		this.idAnt = idAnt;
	}

	public Integer getAlpha() {
		return alpha;
	}

	public void setAlpha(Integer alpha) {
		this.alpha = alpha;
	}

	public Integer getBeta() {
		return beta;
	}

	public void setBeta(Integer beta) {
		this.beta = beta;
	}

	public ACO getAco() {
		return aco;
	}

	public void setAco(ACO aco) {
		this.aco = aco;
	}

	public List<Integer> getTour() {
		return tour;
	}

	public void setTour(List<Integer> tour) {
		this.tour = tour;
	}

	public VirtualNode getCurrentNode() {
		return currentNode;
	}

	public void setCurrentNode(VirtualNode currentNode) {
		this.currentNode = currentNode;
	}

	public List<VirtualNode> getNodesToVisit() {
		return nodesToVisit;
	}

	public void setNodesToVisit(List<VirtualNode> nodesToVisit) {
		this.nodesToVisit = nodesToVisit;
	}

	public Integer getTourLength() {
		return tourLength;
	}

	public void setTourLength(Integer tourLength) {
		this.tourLength = tourLength;
	}

	public List<List<Integer>> getPath() {
		return path;
	}

	public void setPath(List<List<Integer>> path) {
		this.path = path;
	}
	
}
