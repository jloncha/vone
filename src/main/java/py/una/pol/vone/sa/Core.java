package py.una.pol.vone.sa;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.moeaframework.mymodel.SustrateEdge;
import org.moeaframework.mymodel.SustrateNetwork;
import org.moeaframework.mymodel.SustrateNode;
import org.moeaframework.mymodel.VirtualEdge;
import org.moeaframework.mymodel.VirtualNetwork;
import org.moeaframework.mymodel.VirtualNode;

import py.una.pol.vone.nsga.SolucionMoea;
import py.una.pol.vone.rmsa.Rmsa;

public class Core {
	private VirtualNetwork virtualRequest;
	private SustrateNetwork sustrateNetwork;
	private List<VirtualNode> nodesToMap;
	private List<SustrateNode> phisicalNodes;
	private Solution bestSolution;
	private Tree<VirtualNode> VNTree;
	private VirtualNode actualVN;
	private SustrateNode actualSN;
	private List<SustrateNode> sustratePath;
	private List<VirtualNode> virtualPath;
	
	public Core(){
		
	}
	
	
	
	public Core(VirtualNetwork virtualRequest, SustrateNetwork sustrateNetwork) {
		super();
		this.virtualRequest = virtualRequest;
		this.sustrateNetwork = sustrateNetwork;
		this.nodesToMap = this.virtualRequest.getNodosVirtuales();
		this.phisicalNodes = this.sustrateNetwork.getNodosFisicos();
		this.bestSolution = new Solution();
		this.bestSolution.setSustrateNetwork(sustrateNetwork);
		this.sustratePath = new ArrayList<SustrateNode>();
		this.virtualPath = new ArrayList<VirtualNode>();
		
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
		System.out.println("Nodo " + node.getNombre());
		// Controlamos que hayan hojas que se puedan agregar o que sea la raiz del arbol
		//if ((VNTree.itContains(VNTree.getHead(), node) == false) || (itsHead == true)) {
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
					System.out.println("Adjacente " + nodoAux.getNombre());
					if (VNTree.itContains(VNTree.getHead(), nodoAux) == false){
						System.out.println("Se inserta " + nodoAux.getNombre());
						VNTree.addLeaf(node, nodoAux);
						//recursivePopulate(nodoAux, nodoAux.getAdyacentes(), false);
					}
					
				}
				VirtualNode nodoAux2 = new VirtualNode();
				for (VirtualEdge edge : adjacent) {					
					// Obtenemos el nodo con mayor capacidad de CPU, el
					// mismo pasa a ser nuestro nueva hoja a agregar
					nodoAux = node.equals(edge.getNodoUno()) ? edge.getNodoDos() : edge.getNodoUno();
					System.out.println("Llamada Recursiva " + nodoAux.getNombre());
					for (VirtualEdge edge2 : nodoAux.getAdyacentes()){
						nodoAux2 = nodoAux.equals(edge2.getNodoUno()) ? edge2.getNodoDos() : edge2.getNodoUno();
						System.out.println("Adj de Adj " + nodoAux2.getNombre());
						if (VNTree.itContains(VNTree.getHead(), nodoAux2) == false){
							recursivePopulate(nodoAux, nodoAux.getAdyacentes(), false);
						}
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
		List<VirtualNode> listOrdenada =Collections.sort(originalList, ());
	}*/
	
	public void processTree(){
		//Lo primero que hacemos va a ser comenzar por la raiz del arbol
		actualVN = new VirtualNode();
		actualVN = VNTree.getHead();
		//Una vez obtenida la raiz, recorremos los nodos virtuales
		recursiveGenerateSolution(actualVN);
		System.out.println("FIN");
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
					if(nodoVirtual.getCapacidadCPU()<= auxNode.getCapacidadCPU()){
						//Significa que es un precandidato
						//entonces agregamos a la lista de precandidatos
						preCandidates.add(auxNode);
					}
				}
				//Una vez obtenidos los precandidatos se procede a elegir uno completamente al azar
				actualSN = preCandidates.get(randonGenerator.nextInt(preCandidates.size()));
				//Guardamos la solucion
				Mapping firstMap = new Mapping(nodoVirtual, actualSN);
				bestSolution.getMap().add(firstMap);
			}else{
			//Si no corresponde a la Raiz se procede a selecionar vecinos
				SustrateNode beforeNode = new SustrateNode();
				//Obtenemos el ultimo nodo mapeado
				beforeNode = sustratePath.get(sustratePath.size()-1);
				//Obtenemos los vecinos precandidatos
				preCandidates = getPrecandidatesNotRoot(beforeNode, nodoVirtual.getCapacidadCPU());
				//seleccionamos un nodo al azar que va a ser nuestro destino de mapeo
				actualSN = preCandidates.get(randonGenerator.nextInt(preCandidates.size()));
				Mapping mappingAfterRoot = new Mapping(nodoVirtual, actualSN);
				bestSolution.getMap().add(mappingAfterRoot);
				//Agregamos en el path, en donde se muestra los que fueron mapeados
				//Pasamos a mapear su enlace
				Rmsa calculateEdge= new Rmsa();
				//Necesitamos saber cuanto requiere de Frecuency Slots se requieren
				Integer qantSolts = 0;
				//primero recuperamos el nodo anterior
				VirtualNode beforeVN = new VirtualNode();
				beforeVN = virtualPath.get(virtualPath.size()-1);
				for(VirtualEdge auxEdge : beforeVN.getAdyacentes()){
					if((auxEdge.getNodoUno().equals(beforeVN)&& auxEdge.getNodoDos().equals(nodoVirtual))||
					    auxEdge.getNodoDos().equals(beforeVN)&& auxEdge.getNodoUno().equals(nodoVirtual)){
						qantSolts = auxEdge.getCantidadFS();
					}
				}
				bestSolution.setSustrateNetwork(calculateEdge.realizarRmsa(bestSolution.getSustrateNetwork(), String.valueOf(beforeNode.getIdentificador()), String.valueOf(actualSN.getIdentificador()), 5, qantSolts
						, new SolucionMoea(), null));
			}
			virtualPath.add(nodoVirtual);
			sustratePath.add(actualSN);
			//Ahora debemos mapear todos los demas nodos del arbol
			for (VirtualNode obj : VNTree.getSuccessors(nodoVirtual)){
				recursiveGenerateSolution(obj);
			}
		}catch (Exception ex){
			ex.printStackTrace();
		}
		
	}
	
	/**
	 * Metodo que devuelve los precandidatos del nodo
	 * @param center
	 * @return
	 */
	public List<SustrateNode> getPrecandidatesNotRoot(SustrateNode center, Integer requiremnt) {
		List<SustrateNode> resp = new ArrayList<SustrateNode>();
		SustrateNode auxNode = new SustrateNode();
		try {
			for (SustrateEdge node : center.getAdyacentes()) {
				auxNode = center.equals(node.getNodoUno()) ? node.getNodoDos() : node.getNodoUno();
				if (auxNode.getCapacidadCPU() >= requiremnt) {
					resp.add(auxNode);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return resp;
	}
	
}
