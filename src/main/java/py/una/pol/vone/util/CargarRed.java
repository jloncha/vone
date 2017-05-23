package py.una.pol.vone.util;

import java.util.ArrayList;
import py.una.pol.vone.simulator.model.SustrateEdge;
import py.una.pol.vone.simulator.model.SustrateNetwork;
import py.una.pol.vone.simulator.model.SustrateNode;
import py.una.pol.vone.simulator.model.VirtualEdge;
import py.una.pol.vone.simulator.model.VirtualNetwork;
import py.una.pol.vone.simulator.model.VirtualNode;

public class CargarRed {
	
	public VirtualNetwork redVirtual = null;
	public SustrateNetwork redFisica = null;
	
	public CargarRed() {
		VirtualNode nodo1 = new VirtualNode("A",0, 10);
		VirtualNode nodo2 = new VirtualNode("B",1, 10);
		VirtualNode nodo3 = new VirtualNode("C",2, 10);
		VirtualEdge enlace1 = new VirtualEdge(4, "A-B", 50);
		VirtualEdge enlace2 = new VirtualEdge(5, "B-C", 50);
		VirtualEdge enlace3 = new VirtualEdge(6, "C-A", 50);
		SustrateNode nodof1 = new SustrateNode(0, "1", 50);
		SustrateNode nodof2 = new SustrateNode(1, "2", 50);
		SustrateNode nodof3 = new SustrateNode(2, "3", 50);
		SustrateNode nodof4 = new SustrateNode(3, "4", 50);
		SustrateEdge enlacef1 = new SustrateEdge();
		SustrateEdge enlacef2 = new SustrateEdge();
		SustrateEdge enlacef3 = new SustrateEdge();
		SustrateEdge enlacef4 = new SustrateEdge();
		SustrateEdge enlacef5 = new SustrateEdge();
		ArrayList<VirtualEdge> enlaces = new ArrayList<VirtualEdge>();
		ArrayList<SustrateEdge> enlacesF = new ArrayList<SustrateEdge>();
		ArrayList<SustrateNode> nodosF = new ArrayList<SustrateNode>();
		ArrayList<VirtualNode> nodos = new ArrayList<VirtualNode>();
		enlacef1.setNodoUno(nodof1);
		enlacef1.setNodoDos(nodof2);
		enlacef2.setNodoUno(nodof2);
		enlacef2.setNodoDos(nodof4);
		enlacef3.setNodoUno(nodof4);
		enlacef3.setNodoDos(nodof3);
		enlacef4.setNodoUno(nodof3);
		enlacef4.setNodoDos(nodof1);
		enlacef5.setNodoUno(nodof3);
		enlacef5.setNodoDos(nodof2);
		enlace1.setNodoUno(nodo1);
		enlace1.setNodoDos(nodo2);
		enlace2.setNodoUno(nodo2);
		enlace2.setNodoDos(nodo3);
		enlace3.setNodoUno(nodo1);
		enlace3.setNodoDos(nodo3);
		enlaces.add(enlace1);
		enlaces.add(enlace2);
		enlaces.add(enlace3);
		nodos.add(nodo1);
		nodos.add(nodo2);
		nodos.add(nodo3);
		
		nodosF.add(nodof1);
		nodosF.add(nodof2);
		nodosF.add(nodof3);
		nodosF.add(nodof4);
		
		enlacesF.add(enlacef1);
		enlacesF.add(enlacef2);
		enlacesF.add(enlacef3);
		enlacesF.add(enlacef4);
		enlacesF.add(enlacef5);
		
		this.redVirtual = new VirtualNetwork(1, "VN1");
		this.redVirtual.setMapeado(false);
		this.redVirtual.setEnlacesVirtuales(enlaces);
		this.redVirtual.setNodosVirtuales(nodos);
		
		this.redFisica = new SustrateNetwork(1, "SN");
		this.redFisica.setNodosFisicos(nodosF);
		this.redFisica.setEnlacesFisicos(enlacesF);
	}
	
	
}
