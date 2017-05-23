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

import java.util.Observable;
import java.util.Observer;

import py.una.pol.vone.simulator.model.SustrateNetwork;
import py.una.pol.vone.simulator.model.VirtualNetwork;


/**
 * The ACO Class
 * 
 * @author Thiago Nascimento
 * @since 2014-07-23
 * @version 1.0
 */
public /*abstract*/ class ACO implements Observer{

	/** Coeficiente de Evaporacion */
	public static final double RHO = 0.3; 
	public static final double feromonas = 1.2; 
	
	/** Hormigas **/
	protected Ant[] ant;

	/** Numero de hormigas */
	protected int numberOfAnts;

	/** Matriz de feromonas **/
	protected double[][] tau;
	
	/** Total Iteraciones que sirven como condicion de parada*/
	protected int interations;
	
	/** Iteracion Actual*/
	protected int it = 0;
	
	/** Numero total de Hormigas que terminaron el tour*/
	protected int finishedAnts = 0;
	
	/** Best Ant in tour */
	protected Ant bestAnt;
	
	/**Red Virtual que estamos tratando de mapear**/
	protected VirtualNetwork virtualNetwork;
	
	/**Red Virtual que estamos tratando de mapear**/
	protected SustrateNetwork sustrateNetwork;
	
	public ACO(int numberOfAnts, int interations, VirtualNetwork virtualNetwork, SustrateNetwork sustrateNetwork) {
		
		if (numberOfAnts <= 0) {
			throw new IllegalArgumentException("numberOfAnts shouldn't be less than 0");
		}
		if (interations <= 0) {
			throw new IllegalArgumentException("interations shouldn't be less than 0");
		}
		
		this.numberOfAnts = numberOfAnts;		
		this.interations = interations;
		this.virtualNetwork = virtualNetwork;
		this.sustrateNetwork = sustrateNetwork;
	}

	public Ant solve() {
		initializeData();
		while (!terminationCondition()) {
			constructAntsSolutions();
			updatePheromones();
		}
		return bestAnt;
	}

	private void initializeData() {
		initializePheromones();
		initializeAnts();		
	}

	/***
	 * Funcion que inicializa los datos
	 * al principio tienen todos feromonas 1
	 */
	private void initializePheromones() {
		this.tau = new double [virtualNetwork.getNodosVirtuales().size()][sustrateNetwork.getNodosFisicos().size()];
		for (Integer i=0; i<virtualNetwork.getNodosVirtuales().size(); i++){
			for (Integer j=0; j<sustrateNetwork.getNodosFisicos().size(); j++){
				this.tau[i][j] = 1;//virtualNetwork.getNodosVirtuales().get(j).getIdentificador();
			}
		}
	}
	
	private boolean terminationCondition() {
		return ++it > interations;
	}

	private void updatePheromones() {
		globalUpdateRule();
	}

	private synchronized void constructAntsSolutions() {
		//Contruct Ant solutions
		for (int k = 0; k < numberOfAnts; k++) {
			Thread t = new Thread(ant[k],"Ant "+ant[k].id);
			t.start();
		}
		
		//Wait all ants finish your tour
		try{
			wait();
		}catch(InterruptedException ex){
			ex.printStackTrace();
		}	
	}
	
	/**
	 * Call when a ant finished your tour
	 * 
	 * @Override 
	 */
	public synchronized void update(Observable observable, Object obj) {
		Ant ant = (Ant) obj;
		for(Integer i = 0; i<this.virtualNetwork.getNodosVirtuales().size();i++){
			for(Integer j = 0;j< this.sustrateNetwork.getNodosFisicos().size();j++){
				if(ant.path[i][j]!=0){
					this.tau[i][j] = feromonas*this.tau[i][j];
				}
			}
		}


		if (++finishedAnts == numberOfAnts) {
			// Continue all execution
			finishedAnts = 0;
			notify();
		}
	}

	public double[][] getTau() {
		return tau;
	}
	
	public synchronized void setTau(int j, int i, double value) {
		tau[i][j] = value;		
	}
	
	public double getTau(int i,int j){
		return tau[i][j];
	}
	
	
	public  void globalUpdateRule(){
		String finale = "";
		System.out.println("Matriz feromonas AS generico");
		for(Integer i = 0; i<this.virtualNetwork.getNodosVirtuales().size();i++){
			for(Integer j = 0;j< this.sustrateNetwork.getNodosFisicos().size();j++){
				finale = finale+ Math.round((this.tau[i][j])) + "\t";
			}
			System.out.println(finale);
			finale= "";
		}
		System.out.println("\n");
		
	}
	
	public void initializeAnts(){
		this.ant = new Ant[numberOfAnts];

		for (int k = 0; k < numberOfAnts; k++) {
			ant[k] = new Ant(this);
			ant[k].addObserver(this);
		}
	}
}
