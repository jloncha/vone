package py.una.pol.vone.rmsa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import py.una.pol.vone.kshortestpath.Edge;
import py.una.pol.vone.kshortestpath.Graph;
import py.una.pol.vone.kshortestpath.Path;
import py.una.pol.vone.kshortestpath.Yen;
import py.una.pol.vone.simulator.model.SustrateEdge;
import py.una.pol.vone.simulator.model.SustrateNetwork;

public class Rmsa {

	@SuppressWarnings("rawtypes")
	public SustrateNetwork realizarRmsa(SustrateNetwork sustrateNetwork, String sourceNode, 
			String targetNode, Integer k, Integer slotRequerido){
		
		Graph graph = new Graph();
		for (SustrateEdge sustrateEdge : sustrateNetwork.getEnlacesFisicos()) {
			graph.addEdge(String.valueOf(sustrateEdge.getNodoUno().getIdentificador()),
					String.valueOf(sustrateEdge.getNodoDos().getIdentificador()), calcularPeso(sustrateEdge));
			//se carga desde el nodo inverso por ser un grafo bidireccional
			graph.addEdge(String.valueOf(sustrateEdge.getNodoDos().getIdentificador()),
					String.valueOf(sustrateEdge.getNodoUno().getIdentificador()), calcularPeso(sustrateEdge));
		}
		
		List<Path> ksp;
		Yen yenAlgorithm = new Yen();
        ksp = yenAlgorithm.ksp(graph, sourceNode, targetNode, k);
        
        int n = 0;
        //matriz filas y columnas 
        Map<String, List<Integer>> map = new HashMap<String, List<Integer>>();
        for (Path p : ksp) {
            System.out.println(++n + ") " + p.getNodes());
            boolean[][] matriz = new boolean[sustrateNetwork.getCantidadFS()][p.getEdges().size()];
            Iterator<Edge> iter = p.getEdges().iterator();
            int columna = 0;
            while(iter.hasNext()){
            	Edge edge = iter.next();
            	edge.setCantidadFS(sustrateNetwork.getCantidadFS());
            	if(CutRmsa.obtenerEdge(sustrateNetwork, Integer.valueOf(edge.getFromNode()),
            			 Integer.valueOf(edge.getToNode())) != null){
            		edge.setFrequencySlot(CutRmsa.obtenerEdge(sustrateNetwork, Integer.valueOf(edge.getFromNode()),
               			 Integer.valueOf(edge.getToNode())).getFrequencySlot());
            		for (int i = 0; i < sustrateNetwork.getCantidadFS(); i++) {
            			matriz[i][columna] = edge.getFrequencySlot()[i];
					}

            	}
            	columna++;
            }
            p.setCaminos(matriz);
            List<Integer> indiceCortes= CutRmsa.indicesDisponiblesPath(p.getCaminos(), slotRequerido);
            List<Integer> valoresCortes= CutRmsa.calcularValorCorte(matriz, indiceCortes);
            /*los valores del map es una lista que contiene
             * pos 0 = indice del corte
             * pos 1 = valor del corte
             * pos 2 = sumatoria de desalianeacion
             * pos 3 = costo del path
             * pos 4 = size del path
             */
            for (int i = 0; i < indiceCortes.size(); i++) {
            	List<Integer> forMap = new ArrayList<>();
            	forMap.add(indiceCortes.get(i));
            	forMap.add(valoresCortes.get(i));
            	forMap.add(AlignmentRmsa.numeroDesaliancion(sustrateNetwork, p, indiceCortes.get(i)));
            	forMap.add(CutRmsa.calcularCosto(p));
            	forMap.add(p.size());
            	map.put("Path" + n + "." + i, forMap);
			}
        }
        Set<?> set = map.entrySet();
        Iterator<?> iterator = set.iterator();
        Double minValue = Double.MAX_VALUE;
        Double valorActual = 0.0;
        String seleccionado = "";
        while(iterator.hasNext()) {
           Map.Entry mentry = (Map.Entry)iterator.next();
           System.out.print("key is: "+ mentry.getKey() + " & Value is: ");
           System.out.println(mentry.getValue());
           valorActual = (double) ((( map.get(mentry.getKey()).get(4) * slotRequerido) + map.get(mentry.getKey()).get(1) 
        		   +  map.get(mentry.getKey()).get(2))/(double) map.get(mentry.getKey()).get(3));
           System.out.println(valorActual);
           if(minValue > valorActual){
        	   seleccionado = (String) mentry.getKey();
        	   minValue = valorActual;
           }
        }
        Integer identificadorEnlace;
        List<Integer> listaSeleccionados = map.get(seleccionado);
        for (int i = 0; i < ksp.get(listaSeleccionados.get(5)).getEdges().size(); i++) {
        	identificadorEnlace = obtenerEnlaceFisico(sustrateNetwork,ksp.get(listaSeleccionados.get(5)).getEdges().get(i));
        	if(!identificadorEnlace.equals(-1)){
        		for (SustrateEdge sustrateEdge : sustrateNetwork.getEnlacesFisicos()) {
					if(identificadorEnlace.equals(sustrateEdge.getIdentificador())){
						for (int j = 0; j < slotRequerido; j++) {
							sustrateEdge.getFrequencySlot()[Integer.valueOf(listaSeleccionados.get(0)) + j] = true;
						}
						
					}
				}
        	}
		}
        return sustrateNetwork;
	}
	
	public static Integer obtenerEnlaceFisico(SustrateNetwork network, Edge edge){
		  for (SustrateEdge sustrateEdge : network.getEnlacesFisicos()) {
			if((Integer.valueOf(edge.getFromNode()) == sustrateEdge.getNodoUno().getIdentificador()
					&& Integer.valueOf(edge.getToNode()) == sustrateEdge.getNodoDos().getIdentificador())
					|| (Integer.valueOf(edge.getFromNode()) == sustrateEdge.getNodoDos().getIdentificador()
							&& Integer.valueOf(edge.getToNode()) == sustrateEdge.getNodoUno().getIdentificador())){
				return sustrateEdge.getIdentificador();
			}
		}
		  return -1;
	  }
	
	public static Double calcularPeso(SustrateEdge sustrateEdge){
		  Double peso = 1.0;
		  for (int cont = 0; cont < sustrateEdge.getCantidadFS(); cont++) {
			  if(sustrateEdge.getFrequencySlot()[cont]){
				  peso++;
			  }
		  }
		  //System.out.println("Identificador sustrateEdge: " + sustrateEdge.getNombre() + " y su peso: " + peso);
		  return peso;
	  }
}