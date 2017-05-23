/*
 * Copyright 2014 Thiago Nascimento
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 *     
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package py.una.pol.vone.aco;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Random;

import py.una.pol.vone.simulator.model.VirtualNode;
import py.una.pol.vone.util.NodoProbabilidad;




/**
 * Clase que representa las hormigas
 * 
 * @author Jean
 */
public class Ant extends Observable implements Runnable {
	
	public static int ANT_ID = 1;
	
	/** El valor de alfa*/
	public static final int ALPHA = 2;
	
	/** Valor del componetne beta */
	public static final int BETA = 2;

	/** Identifier */
	public int id = ANT_ID++;
	
	public ACO aco;
	
	public List<Integer> tour;
	public List<Integer> virtualTour;
	
	/** The Current Node */
	public int currentNode;
	
	public int[][] path;
	
	public List<Integer> nodesToVisit;
	public List<Integer> nodesFToVisit;
	public VirtualNode currentVirtualNode;

	public double tourLength;
	
	public Ant(ACO aco) {
		this.aco = aco;
		reset();		
	}
	
	
	public void reset(){
		this.currentNode = -1;
		this.tourLength = 0;
		this.nodesToVisit = new ArrayList<Integer>();
		this.nodesFToVisit = new ArrayList<Integer>();
		this.tour = new ArrayList<Integer>();
		this.path = new int[aco.virtualNetwork.getNodosVirtuales().size()][aco.sustrateNetwork.getNodosFisicos().size()];
	}

	@Override
	public void run() {
		init();
		explore();		
		setChanged();
		notifyObservers(this);
	}
	
	public void init(){
		reset();
		//Random random = new Random();
		//this.currentNode = random.nextInt(aco.virtualNetwork.getNodosVirtuales().size());
		//this.tour.add(new Integer(currentNode));
		currentVirtualNode = new VirtualNode("AUX", -1, -1);
		
		this.virtualTour = new ArrayList<Integer>();
		initializeTheMandatoryNeighborhood(this);
		setCurrentVirtualNode();
	}
	
	@Override
	public String toString() {
		return "Ant " + id + " " + tour+" "+tourLength;
	}

	/***
	 * Funcion que inicializa el vecindario, que este caso todos los demas nodos
	 * @param ant
	 */
	public void initializeTheMandatoryNeighborhood(Ant ant) {
		//Agregamos todos los nodos virtuales
		for (int i = 0; i < aco.virtualNetwork.getNodosVirtuales().size(); i++) {
			if(i != ant.currentVirtualNode.getIdentificador()){
				ant.nodesToVisit.add(new Integer(i));
			}
		}
		//Agregamos todos los nodos fisicos disponibles
		for (int i = 0; i < aco.sustrateNetwork.getNodosFisicos().size(); i++) {
			if(i != ant.currentNode){
				ant.nodesFToVisit.add(new Integer(i));
			}
		}
	}
	
	
	/**
	 * Construct the solutions
	 */
	public void explore(){
		while (!this.nodesToVisit.isEmpty()) {
			//Seleccionamos el nodo fisico a mapear
			doExploration(this.currentVirtualNode.getIdentificador());

			//Tenemos que agregar el nodo fisico seleccionado
			this.tour.add(new Integer(this.currentNode));
			this.nodesFToVisit.remove(new Integer(this.currentNode));
			//seteamos la matriz solucion
			this.virtualTour.add(new Integer(this.currentVirtualNode.getIdentificador()));
			path[this.currentVirtualNode.getIdentificador()][currentNode] = 1;
			//quitamos el nodo que ya mapeamos
			this.nodesToVisit.remove(new Integer (this.currentVirtualNode.getIdentificador()));
			//seleccionamos el siguiente nodo a mapear
			setCurrentVirtualNode();
		
		}
	};
	
	protected void doExploration(int nodoAmapear) {
		Double[] probabilidad = new Double[this.nodesFToVisit.size()];
		List<NodoProbabilidad> listProb = new ArrayList<NodoProbabilidad>(); 
		Double totalsum = 0.0;
		Double sumAnterior = 0.0;
		Double probabilidadElegida = 0.0;
		Boolean parada = false;
		Integer k=0;
		//tenemos que calcular la suma de la tabla de feromonas
		for(Integer i: nodesFToVisit){
			 totalsum = totalsum +this.aco.tau[nodoAmapear][i];
		}
		//Primero seleccionaremos un nodo fisico al cual mapear,
		// de acuerdo a su tabla de feromonas
		for(Integer i : nodesFToVisit){
			NodoProbabilidad prob = new NodoProbabilidad(i, sumAnterior, sumAnterior + this.aco.tau[nodoAmapear][i]/totalsum);
			listProb.add(prob);
			sumAnterior = sumAnterior + this.aco.tau[nodoAmapear][i]/totalsum;
		}
		//Ya tenemos calculado las probabilidades de que cada nodo sea elegido,
		//entonces procedemos a elegir uno randomicamente
		Random random = new Random();
		probabilidadElegida = random.nextDouble();
		//buscamos a que nodo corresponde la probabilidad surgida
		for(NodoProbabilidad e: listProb){
			if(probabilidadElegida>= e.getMinProbabilidad() && probabilidadElegida< e.getMaxProbabilidad()){
				//hacemos que el nodo actual sea el 
				this.currentNode = e.getIdNodo();
				break;
			}
		}
		
	}

	/**
	 * Seleccionamos el siguiente nodo virtual a mapear, el que tenga mayor
	 * Capacidad de CPU
	 * 
	 */
	public void setCurrentVirtualNode(){
		VirtualNode mayor = null;
		for (VirtualNode nodo : this.aco.virtualNetwork.getNodosVirtuales()){
			if (!this.virtualTour.contains(nodo.getIdentificador())){
				if(mayor!= null){
					if(nodo.getCapacidadCPU()> mayor.getCapacidadCPU()){
						mayor = nodo;
					}
				}else{
					mayor = nodo;
				}
				
			}
		}
		this.currentVirtualNode = mayor;
	}

	/**
	 * Clone the ant
	 */
	//public abstract Ant clone();
}