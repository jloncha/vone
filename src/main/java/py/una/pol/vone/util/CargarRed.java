package py.una.pol.vone.util;

import java.util.ArrayList;
import java.util.Random;

import org.moeaframework.mymodel.SustrateEdge;
import org.moeaframework.mymodel.SustrateNetwork;
import org.moeaframework.mymodel.SustrateNode;
import org.moeaframework.mymodel.VirtualEdge;
import org.moeaframework.mymodel.VirtualNetwork;
import org.moeaframework.mymodel.VirtualNode;

public class CargarRed {
	
	public VirtualNetwork redVirtual = null;
	public SustrateNetwork redFisica = null;
	
	public CargarRed() {
		
		Integer cantidadFS = 12;
		Integer identificador = 0;
		//Random r = new Random();
		Integer valorCpu = 0;
		SustrateNetwork sustrateNetwork = new SustrateNetwork();
		sustrateNetwork.setNombre("Paper rsa");
		NetworkGenerator.generarRedFisica(sustrateNetwork, "src/main/resources/grafos/tiny_graph_01.txt", 6);
		sustrateNetwork.setCantidadFS(12);
		
		for(SustrateNode nodo : sustrateNetwork.getNodosFisicos()){
			nodo.setCapacidadCPU(valorCpu + 10);
			valorCpu++;
		}
		
		VirtualNode nodo1 = new VirtualNode("A",0, 3);
		VirtualNode nodo2 = new VirtualNode("B",1, 2);
		VirtualNode nodo3 = new VirtualNode("C",2, 3);
		VirtualNode nodo4 = new VirtualNode("D",3, 2);
		
		VirtualEdge enlace1 = new VirtualEdge(4, "A-B", 2);
		VirtualEdge enlace2 = new VirtualEdge(5, "B-C", 2);
		VirtualEdge enlace3 = new VirtualEdge(6, "C-A", 2);
		VirtualEdge enlace4 = new VirtualEdge(7, "D-C", 2);
		/*SustrateNode nodof1 = new SustrateNode(0, "1", 50);
		SustrateNode nodof2 = new SustrateNode(1, "2", 50);
		SustrateNode nodof3 = new SustrateNode(2, "3", 50);
		SustrateNode nodof4 = new SustrateNode(3, "4", 50);
		SustrateEdge enlacef1 = new SustrateEdge(10, 12);
		SustrateEdge enlacef2 = new SustrateEdge(10, 12);
		SustrateEdge enlacef3 = new SustrateEdge(10, 12);
		SustrateEdge enlacef4 = new SustrateEdge(10, 12);
		SustrateEdge enlacef5 = new SustrateEdge(10, 12);*/
		ArrayList<VirtualEdge> enlaces = new ArrayList<VirtualEdge>();
		//ArrayList<SustrateEdge> enlacesF = new ArrayList<SustrateEdge>();
	//	ArrayList<SustrateNode> nodosF = new ArrayList<SustrateNode>();
		ArrayList<VirtualNode> nodos = new ArrayList<VirtualNode>();
		/*enlacef1.setNodoUno(nodof1);
		enlacef1.setNodoDos(nodof2);
		enlacef2.setNodoUno(nodof2);
		enlacef2.setNodoDos(nodof4);
		enlacef3.setNodoUno(nodof4);
		enlacef3.setNodoDos(nodof3);
		enlacef4.setNodoUno(nodof3);
		enlacef4.setNodoDos(nodof1);
		enlacef5.setNodoUno(nodof3);
		enlacef5.setNodoDos(nodof2);*/
		enlace1.setNodoUno(nodo1);
		enlace1.setNodoDos(nodo2);
		enlace2.setNodoUno(nodo2);
		enlace2.setNodoDos(nodo3);
		enlace3.setNodoUno(nodo1);
		enlace3.setNodoDos(nodo3);
		enlace4.setNodoDos(nodo3);
		enlace4.setNodoUno(nodo4);
		
		nodo1.getAdyacentes().add(enlace1);
		nodo1.getAdyacentes().add(enlace3);
		nodo2.getAdyacentes().add(enlace1);
		nodo2.getAdyacentes().add(enlace2);
		nodo3.getAdyacentes().add(enlace2);
		nodo3.getAdyacentes().add(enlace3);
		nodo3.getAdyacentes().add(enlace4);
		nodo4.getAdyacentes().add(enlace4);
		
		enlaces.add(enlace1);
		enlaces.add(enlace2);
		enlaces.add(enlace3);
		enlaces.add(enlace4);
		nodos.add(nodo1);
		nodos.add(nodo2);
		nodos.add(nodo3);
		nodos.add(nodo4);
		//Adjacentes
		/*nodof1.getAdyacentes().add(enlacef1); 
		nodof1.getAdyacentes().add(enlacef4);
		nodof2.getAdyacentes().add(enlacef1);
		nodof2.getAdyacentes().add(enlacef2); 
		nodof2.getAdyacentes().add(enlacef5);
		nodof3.getAdyacentes().add(enlacef3);
		nodof3.getAdyacentes().add(enlacef4);
		nodof3.getAdyacentes().add(enlacef5);
		nodof4.getAdyacentes().add(enlacef2);
		nodof4.getAdyacentes().add(enlacef3);
		
		nodosF.add(nodof1);
		nodosF.add(nodof2);
		nodosF.add(nodof3);
		nodosF.add(nodof4);
		
		//Poblamo los FS
		//1)
		enlacef1.getFrequencySlot()[0] = true;
		enlacef1.getFrequencySlot()[1] = true;	
		enlacef1.getFrequencySlot()[2] = false;
		enlacef1.getFrequencySlot()[3] = false;
		enlacef1.getFrequencySlot()[4] = true;
		enlacef1.getFrequencySlot()[5] = true;
		enlacef1.getFrequencySlot()[6] = false;
		enlacef1.getFrequencySlot()[7] = false;
		enlacef1.getFrequencySlot()[8] = true;
		enlacef1.getFrequencySlot()[9] = true;
		enlacef1.getFrequencySlot()[10] = false;
		enlacef1.getFrequencySlot()[11] = false;
		//2
		
		enlacef2.getFrequencySlot()[0] = true;
		enlacef2.getFrequencySlot()[1] = false;	
		enlacef2.getFrequencySlot()[2] = false;
		enlacef2.getFrequencySlot()[3] = false;
		enlacef2.getFrequencySlot()[4] = true;
		enlacef2.getFrequencySlot()[5] = true;
		enlacef2.getFrequencySlot()[6] = true;
		enlacef2.getFrequencySlot()[7] = false;
		enlacef2.getFrequencySlot()[8] = false;
		enlacef2.getFrequencySlot()[9] = false;
		enlacef2.getFrequencySlot()[10] = false;
		enlacef2.getFrequencySlot()[11] = false;
		//3
		
		enlacef3.getFrequencySlot()[0] = true;
		enlacef3.getFrequencySlot()[1] = true;	
		enlacef3.getFrequencySlot()[2] = false;
		enlacef3.getFrequencySlot()[3] = false;
		enlacef3.getFrequencySlot()[4] = true;
		enlacef3.getFrequencySlot()[5] = true;
		enlacef3.getFrequencySlot()[6] = false;
		enlacef3.getFrequencySlot()[7] = false;
		enlacef3.getFrequencySlot()[8] = false;
		enlacef3.getFrequencySlot()[9] = false;
		enlacef3.getFrequencySlot()[10] = false;
		enlacef3.getFrequencySlot()[11] = false;
		
		//4
		
		enlacef4.getFrequencySlot()[0] = false;
		enlacef4.getFrequencySlot()[1] = false;	
		enlacef4.getFrequencySlot()[2] = false;
		enlacef4.getFrequencySlot()[3] = false;
		enlacef4.getFrequencySlot()[4] = true;
		enlacef4.getFrequencySlot()[5] = true;
		enlacef4.getFrequencySlot()[6] = true;
		enlacef4.getFrequencySlot()[7] = false;
		enlacef4.getFrequencySlot()[8] = false;
		enlacef4.getFrequencySlot()[9] = false;
		enlacef4.getFrequencySlot()[10] = false;
		enlacef4.getFrequencySlot()[11] = false;
		
		//5
		
		enlacef5.getFrequencySlot()[0] = true;
		enlacef5.getFrequencySlot()[1] = false;	
		enlacef5.getFrequencySlot()[2] = false;
		enlacef5.getFrequencySlot()[3] = false;
		enlacef5.getFrequencySlot()[4] = false;
		enlacef5.getFrequencySlot()[5] = false;
		enlacef5.getFrequencySlot()[6] = false;
		enlacef5.getFrequencySlot()[7] = false;
		enlacef5.getFrequencySlot()[8] = false;
		enlacef5.getFrequencySlot()[9] = false;
		enlacef5.getFrequencySlot()[10] = false;
		enlacef5.getFrequencySlot()[11] = false;
		
		enlacesF.add(enlacef1);
		enlacesF.add(enlacef2);
		enlacesF.add(enlacef3);
		enlacesF.add(enlacef4);
		enlacesF.add(enlacef5);*/
		
		
		for (SustrateEdge sustrateEdge : sustrateNetwork.getEnlacesFisicos()) {
			  sustrateEdge.setCantidadFS(cantidadFS);
			  sustrateEdge.setFrequencySlot(new boolean[cantidadFS]);
			  sustrateEdge.setIdentificador(identificador);
			 /*Si se descomenta el for se genera de manera randomica 
			  * los slocks ocupados, y habria que comentar 
			  * el switch
			  */
			  /*for (int i = 0; i < cantidadFS; i++) {
				  sustrateEdge.getFrequencySlot()[i] = randomno.nextBoolean();
			  }*/
			  switch (identificador){
			  		case 0:  	sustrateEdge.getFrequencySlot()[0] = true;
			  					sustrateEdge.getFrequencySlot()[1] = true;	
						  		sustrateEdge.getFrequencySlot()[2] = false;
						  		sustrateEdge.getFrequencySlot()[3] = false;
						  		sustrateEdge.getFrequencySlot()[4] = true;
						  		sustrateEdge.getFrequencySlot()[5] = true;
						  		sustrateEdge.getFrequencySlot()[6] = false;
						  		sustrateEdge.getFrequencySlot()[7] = false;
						  		sustrateEdge.getFrequencySlot()[8] = true;
						  		sustrateEdge.getFrequencySlot()[9] = true;
						  		sustrateEdge.getFrequencySlot()[10] = false;
						  		sustrateEdge.getFrequencySlot()[11] = false;
			  				break;
			  		case 1: 	sustrateEdge.getFrequencySlot()[0] = true;
			  					sustrateEdge.getFrequencySlot()[1] = false;	
						  		sustrateEdge.getFrequencySlot()[2] = false;
						  		sustrateEdge.getFrequencySlot()[3] = false;
						  		sustrateEdge.getFrequencySlot()[4] = true;
						  		sustrateEdge.getFrequencySlot()[5] = true;
						  		sustrateEdge.getFrequencySlot()[6] = true;
						  		sustrateEdge.getFrequencySlot()[7] = false;
						  		sustrateEdge.getFrequencySlot()[8] = false;
						  		sustrateEdge.getFrequencySlot()[9] = false;
						  		sustrateEdge.getFrequencySlot()[10] = false;
						  		sustrateEdge.getFrequencySlot()[11] = false;
						  	break;
			  		case 2: 	sustrateEdge.getFrequencySlot()[0] = true;
			  					sustrateEdge.getFrequencySlot()[1] = true;	
						  		sustrateEdge.getFrequencySlot()[2] = false;
						  		sustrateEdge.getFrequencySlot()[3] = false;
						  		sustrateEdge.getFrequencySlot()[4] = true;
						  		sustrateEdge.getFrequencySlot()[5] = true;
						  		sustrateEdge.getFrequencySlot()[6] = false;
						  		sustrateEdge.getFrequencySlot()[7] = false;
						  		sustrateEdge.getFrequencySlot()[8] = false;
						  		sustrateEdge.getFrequencySlot()[9] = false;
						  		sustrateEdge.getFrequencySlot()[10] = false;
						  		sustrateEdge.getFrequencySlot()[11] = false;
			  				break;
			  		case 3: 	sustrateEdge.getFrequencySlot()[0] = false;
			  					sustrateEdge.getFrequencySlot()[1] = true;	
						  		sustrateEdge.getFrequencySlot()[2] = false;
						  		sustrateEdge.getFrequencySlot()[3] = false;
						  		sustrateEdge.getFrequencySlot()[4] = true;
						  		sustrateEdge.getFrequencySlot()[5] = false;
						  		sustrateEdge.getFrequencySlot()[6] = false;
						  		sustrateEdge.getFrequencySlot()[7] = false;
						  		sustrateEdge.getFrequencySlot()[8] = true;
						  		sustrateEdge.getFrequencySlot()[9] = true;
						  		sustrateEdge.getFrequencySlot()[10] = true;
						  		sustrateEdge.getFrequencySlot()[11] = true;
			  				break;
			  		case 4: 	sustrateEdge.getFrequencySlot()[0] = false;
			  					sustrateEdge.getFrequencySlot()[1] = false;	
						  		sustrateEdge.getFrequencySlot()[2] = false;
						  		sustrateEdge.getFrequencySlot()[3] = false;
						  		sustrateEdge.getFrequencySlot()[4] = true;
						  		sustrateEdge.getFrequencySlot()[5] = true;
						  		sustrateEdge.getFrequencySlot()[6] = true;
						  		sustrateEdge.getFrequencySlot()[7] = false;
						  		sustrateEdge.getFrequencySlot()[8] = false;
						  		sustrateEdge.getFrequencySlot()[9] = false;
						  		sustrateEdge.getFrequencySlot()[10] = false;
						  		sustrateEdge.getFrequencySlot()[11] = false;
			  				break;
			  		case 5: 	sustrateEdge.getFrequencySlot()[0] = true;
			  					sustrateEdge.getFrequencySlot()[1] = false;	
						  		sustrateEdge.getFrequencySlot()[2] = false;
						  		sustrateEdge.getFrequencySlot()[3] = false;
						  		sustrateEdge.getFrequencySlot()[4] = false;
						  		sustrateEdge.getFrequencySlot()[5] = false;
						  		sustrateEdge.getFrequencySlot()[6] = false;
						  		sustrateEdge.getFrequencySlot()[7] = false;
						  		sustrateEdge.getFrequencySlot()[8] = false;
						  		sustrateEdge.getFrequencySlot()[9] = false;
						  		sustrateEdge.getFrequencySlot()[10] = false;
						  		sustrateEdge.getFrequencySlot()[11] = false;
			  				break;
			  		case 6: 	sustrateEdge.getFrequencySlot()[0] = true;
			  					sustrateEdge.getFrequencySlot()[1] = false;	
						  		sustrateEdge.getFrequencySlot()[2] = false;
						  		sustrateEdge.getFrequencySlot()[3] = false;
						  		sustrateEdge.getFrequencySlot()[4] = true;
						  		sustrateEdge.getFrequencySlot()[5] = false;
						  		sustrateEdge.getFrequencySlot()[6] = false;
						  		sustrateEdge.getFrequencySlot()[7] = false;
						  		sustrateEdge.getFrequencySlot()[8] = false;
						  		sustrateEdge.getFrequencySlot()[9] = false;
						  		sustrateEdge.getFrequencySlot()[10] = false;
						  		sustrateEdge.getFrequencySlot()[11] = false;
			  				break;
			  		case 7: 	sustrateEdge.getFrequencySlot()[0] = true;
			  					sustrateEdge.getFrequencySlot()[1] = true;	
						  		sustrateEdge.getFrequencySlot()[2] = false;
						  		sustrateEdge.getFrequencySlot()[3] = false;
						  		sustrateEdge.getFrequencySlot()[4] = false;
						  		sustrateEdge.getFrequencySlot()[5] = true;
						  		sustrateEdge.getFrequencySlot()[6] = true;
						  		sustrateEdge.getFrequencySlot()[7] = true;
						  		sustrateEdge.getFrequencySlot()[8] = false;
						  		sustrateEdge.getFrequencySlot()[9] = false;
						  		sustrateEdge.getFrequencySlot()[10] = false;
						  		sustrateEdge.getFrequencySlot()[11] = false;
			  				break;
			  }
			  
			  //System.out.println(sustrateEdge.getNombre());
			  identificador++;
		}
		
		
		
		this.redVirtual = new VirtualNetwork(1, "VN1");
		this.redVirtual.setMapeado(false);
		this.redVirtual.setEnlacesVirtuales(enlaces);
		this.redVirtual.setNodosVirtuales(nodos);
		
		this.redFisica = new SustrateNetwork();
		this.redFisica = sustrateNetwork;
	//	this.redFisica.setNodosFisicos(nodosF);
	//	this.redFisica.setEnlacesFisicos(enlacesF);
	
	}
	
	
}
