package py.una.pol.vone.sa;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JTree;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import py.una.pol.vone.simulator.model.SustrateNetwork;
import py.una.pol.vone.simulator.model.SustrateNode;
import py.una.pol.vone.simulator.model.VirtualEdge;
import py.una.pol.vone.simulator.model.VirtualNetwork;
import py.una.pol.vone.simulator.model.VirtualNode;

public class Core {
	private VirtualNetwork virtualRequest;
	private SustrateNetwork sustrateNetwork;
	private List<VirtualNode> nodesToMap;
	private List<SustrateNode> phisicalNodes;
	private List<Solution> bestSolution;
	private Tree<VirtualNode> VNTree;
	private VirtualNode actualVN;
	private SustrateNode actualSN;
	private List<SustrateNode> sustratePath;
	
	public Core(){
		
	}
	
	
	
	public Core(VirtualNetwork virtualRequest, SustrateNetwork sustrateNetwork) {
		super();
		this.virtualRequest = virtualRequest;
		this.sustrateNetwork = sustrateNetwork;
		this.nodesToMap = this.virtualRequest.getNodosVirtuales();
		this.phisicalNodes = this.sustrateNetwork.getNodosFisicos();
		this.bestSolution = new ArrayList<Solution>();
		
	}



	/**
	 * Lo primero que debemos hacer es generar la primera solucion 
	 */
	public void generateFirstSolution(){
		//el primer paso es generar el arbol de nodos virtuales
		//Esto determina el orden de mapeo
		generateVNTree();
	}
	
	/*
	 * Este metodo genera el arbol de orden de los nodos virtuales
	 */
	public void generateVNTree() {
		VirtualNode maximun = null;
		try {
			// Lo primero que tenemos que hacer es ver cual es el nodo con mayor
			// req CPU
			for (VirtualNode node : this.virtualRequest.getNodosVirtuales()) {
				if (maximun != null) {
					if (node.getCapacidadCPU() > maximun.getCapacidadCPU()) {
						maximun = node;
					}
				} else {
					maximun = node;
				}
			}
			//Luego definimos el arbol donde el nodo raiz sera el de maximo CPU
			VNTree = new Tree<VirtualNode>(maximun);
			recursivePopulate(maximun, maximun.getAdyacentes(), true);
			processTree();
			
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println(ex.getMessage());
		}

	}
	/*
	 * Metodo para cargar el arbol recursivamente
	 */
	public void recursivePopulate(VirtualNode node, List<VirtualEdge> adjacent, Boolean itsHead) {
		VirtualNode nodoAux = new VirtualNode();
		// Controlamos que hayan hojas que se puedan agregar o que sea la raiz del arbol
		if ((VNTree.itContains(VNTree.getHead(), node) == false) || (itsHead == true)) {
			if (!adjacent.isEmpty()) {
				VirtualNode maximun = new VirtualNode();
				maximun = adjacent.get(0).getNodoUno().equals(node) ? adjacent.get(0).getNodoDos()
						: adjacent.get(0).getNodoUno();
				// Llamamos a nuestro ordenador de Listas
				// orderAdjacents();
				for (VirtualEdge edge : adjacent) {
					// Obtenemos el nodo con mayor capacidad de CPU, el
					// mismo pasa a ser nuestro nueva hoja a agregar
					nodoAux = node.equals(edge.getNodoUno()) ? edge.getNodoDos() : edge.getNodoUno();
					VNTree.addLeaf(node, nodoAux);
					recursivePopulate(nodoAux, nodoAux.getAdyacentes(), false);
				}

			}
		}
	}
	/**
	 * Metodo que ordena la lista de adjacentes para procesarla
	 * @param originalList
	 * @return
	 */
	/*public List<VirtualNode> orderAdjacents(List<VirtualNode> originalList){
		
	}*/
	
	public void processTree(){
		//Lo primero que hacemos va a ser comenzar por la raiz del arbol
		actualVN = new VirtualNode();
		actualVN = VNTree.getHead();
		//Una vez obtenida la raiz, recorremos los nodos virtuales
		recursiveGenerateSolution(actualVN);
	}
	/**
	 * Metodo recursivo de generacion de solucion
	 * @param nodoVirtual
	 */
	public void recursiveGenerateSolution(VirtualNode nodoVirtual){
		List<SustrateNode> preCandidates = new ArrayList<SustrateNode>();
		Random randonGenerator = new Random(); 
		try{
			//Comprobamos si es un nodo raiz, si es asi seleccionamos un nodo fisico al azar
			if(nodoVirtual.equals(VNTree.getHead())){
				//Significa que es la raiz, entonces obtenemos todos los nodos
				//Fisicos que tienen lo minimo de CPU requerido
				for(SustrateNode auxNode : phisicalNodes){
					if(nodoVirtual.getCapacidadCPU()>= auxNode.getCapacidadCPU()){
						//Significa que es un precandidato
						//entonces agregamos a la lista de precandidatos
						preCandidates.add(auxNode);
					}
				}
				//Una vez obtenidos los precandidatos se procede a elegir uno completamente al azar
				actualSN = preCandidates.get(randonGenerator.nextInt(preCandidates.size()));
				//Guardamos la solucion
				Solution solucion = new Solution(nodoVirtual, actualSN);
				bestSolution.add(solucion);		
			}else{
			//Si no corresponde a la Raiz se procede a selecionar vecinos
				
			}
			
		}catch (Exception ex){
			ex.printStackTrace();
		}
		
	}
}