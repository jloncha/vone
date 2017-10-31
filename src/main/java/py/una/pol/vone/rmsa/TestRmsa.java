package py.una.pol.vone.rmsa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.xml.bind.ValidationException;

import py.una.pol.vone.kshortestpath.Edge;
import py.una.pol.vone.kshortestpath.Graph;
import py.una.pol.vone.kshortestpath.Path;
import py.una.pol.vone.kshortestpath.Yen;
import py.una.pol.vone.simulator.model.SustrateEdge;
import py.una.pol.vone.simulator.model.SustrateNetwork;
import py.una.pol.vone.util.NetworkGenerator;

public class TestRmsa {

	  @SuppressWarnings("rawtypes")
	public static void main(String args[]) throws ValidationException {
		  Integer cantidadFS = 12;
		  Integer identificador = 0;
		  Random randomno = new Random();
		  SustrateNetwork sustrateNetwork = new SustrateNetwork();
		  NetworkGenerator.generarRedFisica(sustrateNetwork, "src/main/resources/grafos/tiny_graph_01.txt", 6);
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
			  		case 0:  	sustrateEdge.getFrequencySlot()[0] = false;
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
			  		case 1: 	sustrateEdge.getFrequencySlot()[0] = false;
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
			  		case 2: 	sustrateEdge.getFrequencySlot()[0] = false;
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
			  		case 3: 	sustrateEdge.getFrequencySlot()[0] = false;
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
			  		case 4: 	sustrateEdge.getFrequencySlot()[0] = false;
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
			  		case 5: 	sustrateEdge.getFrequencySlot()[0] = false;
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
			  		case 6: 	sustrateEdge.getFrequencySlot()[0] = false;
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
			  		case 7: 	sustrateEdge.getFrequencySlot()[0] = false;
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
			  }
			  
			  //System.out.println(sustrateEdge.getNombre());
			  identificador++;
		}
		
		Graph graph = new Graph();
		for (SustrateEdge sustrateEdge : sustrateNetwork.getEnlacesFisicos()) {
			graph.addEdge(String.valueOf(sustrateEdge.getNodoUno().getIdentificador()),
					String.valueOf(sustrateEdge.getNodoDos().getIdentificador()), calcularPeso(sustrateEdge));
			//se carga desde el nodo inverso por ser un grafo bidireccional
			graph.addEdge(String.valueOf(sustrateEdge.getNodoDos().getIdentificador()),
					String.valueOf(sustrateEdge.getNodoUno().getIdentificador()), calcularPeso(sustrateEdge));
		}
		/*Desde este punto se invoca al algoritmo de kshortestpath
		 * 
		 */
		
		//parametros a recibir de ant
		String sourceNode, targetNode;
        int K, slotRequerido;
        sourceNode = "4";
        targetNode = "5";
        K = 2;
        //cantidad de frecuency slots que se requieren
        slotRequerido = 1;  
		/* Compute the K shortest paths and record the completion time */
        System.out.print("Computing the " + K + " shortest paths from [" + sourceNode + "] to [" + targetNode + "] ");
        System.out.print("using Yen's algorithm... ");
        List<Path> ksp;
        long timeStart = System.currentTimeMillis();
        Yen yenAlgorithm = new Yen();
        ksp = yenAlgorithm.ksp(graph, sourceNode, targetNode, K);
        

        /* Output the K shortest paths */
        System.out.println("k) cost: [path]");
        int n = 0;
        //matriz filas y columnas 
        Map<String, List<Integer>> map = new HashMap<String, List<Integer>>();
        for (Path p : ksp) {
            System.out.println(++n + ") " + p.getNodes());
            //cada camino tiene su propia matriz
            boolean[][] matriz = new boolean[cantidadFS][p.getEdges().size()];
            //System.out.println(p.getEdges());
            Iterator<Edge> iter = p.getEdges().iterator();
            int columna = 0;
            while(iter.hasNext()){
            	Edge edge = iter.next();
            	edge.setCantidadFS(cantidadFS);
            	/*System.out.println("*********************");
            	System.out.println("FROM: " + edge.getFromNode());
            	System.out.println("TO: " + edge.getToNode());*/
            	if(CutRmsa.obtenerEdge(sustrateNetwork, Integer.valueOf(edge.getFromNode()),
            			 Integer.valueOf(edge.getToNode())) != null){
            		edge.setFrequencySlot(CutRmsa.obtenerEdge(sustrateNetwork, Integer.valueOf(edge.getFromNode()),
               			 Integer.valueOf(edge.getToNode())).getFrequencySlot());
            		for (int i = 0; i < cantidadFS; i++) {
            			matriz[i][columna] = edge.getFrequencySlot()[i];
					}
            		/*System.out.println(edge.toString());
            		System.out.println(CutRmsa.indicesDisponibles(edge, slotRequerido));*/
            	}
            	//System.out.println("-------------------------");
            	columna++;
            }
            p.setCaminos(matriz);
            //imprime los valores del fs
            /*for (int i = 0; i < cantidadFS; i++) {
            	System.out.print("FILA: " + i + " ");
            	for (int j = 0; j < p.getEdges().size(); j++) {
            		System.out.print(p.getCaminos()[i][j] + ", ");
				}
            	System.out.println("\n");
			}*/
            List<Integer> indiceCortes= CutRmsa.indicesDisponiblesPath(p.getCaminos(), slotRequerido);
            List<Integer> valoresCortes= CutRmsa.calcularValorCorte(matriz, indiceCortes);
            /*System.out.println("Indices disponibles para el corte"
            		+ CutRmsa.indicesDisponiblesPath(p.getCaminos(), slotRequerido));
            System.out.println("Valores de corte de acuerdo a los indices " + 
            		CutRmsa.calcularValorCorte(matriz, indiceCortes));*/
            /*los valores del map es una lista que contiene
             * pos 0 = indice del corte
             * pos 1 = valor del corte
             * pos 2 = sumatoria de desalianeacion
             * pos 3 = costo del path
             * pos 4 = size del path
             * pos 5 = elemento del ksp
             */
            for (int i = 0; i < indiceCortes.size(); i++) {
            	List<Integer> forMap = new ArrayList<>();
            	forMap.add(indiceCortes.get(i));
            	forMap.add(valoresCortes.get(i));
            	forMap.add(AlignmentRmsa.numeroDesaliancion(sustrateNetwork, p, indiceCortes.get(i)));
            	forMap.add(CutRmsa.calcularCosto(p));
            	forMap.add(p.size());
            	forMap.add(n - 1);
            	map.put("Path" + n + "." + i, forMap);
			}
            //System.out.println("Sumatoria: " + AlignmentRmsa.numeroDesaliancion(sustrateNetwork, p, 7));
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
           valorActual = (double) ((( map.get(mentry.getKey()).get(4) * slotRequerido * map.get(mentry.getKey()).get(4)) + map.get(mentry.getKey()).get(1) 
        		   +  map.get(mentry.getKey()).get(2))/(double) map.get(mentry.getKey()).get(3));
           System.out.println(valorActual);
           if(minValue > valorActual){
        	   seleccionado = (String) mentry.getKey();
        	   minValue = valorActual;
           }
        }
        System.out.println("Elemento del mapa seleccionado: " + seleccionado);
        List<Integer> listaSeleccionados = map.get(seleccionado);
        
        Integer identificadorEnlace;
        for (int i = 0; i < ksp.get(listaSeleccionados.get(5)).getEdges().size(); i++) {
        	
        	System.out.println(ksp.get(listaSeleccionados.get(5)).getEdges().get(i));
        	identificadorEnlace = obtenerEnlaceFisico(sustrateNetwork,ksp.get(listaSeleccionados.get(5)).getEdges().get(i));
        	if(!identificadorEnlace.equals(-1)){
        		for (SustrateEdge sustrateEdge : sustrateNetwork.getEnlacesFisicos()) {
					if(identificadorEnlace.equals(sustrateEdge.getIdentificador())){
						System.out.println("ENTRO if");
						for (int j = 0; j < slotRequerido; j++) {
							System.out.println("ENTRO");
							sustrateEdge.getFrequencySlot()[Integer.valueOf(listaSeleccionados.get(0)) + j] = true;
						}
						
					}
				}
        	}
		}
        for (SustrateEdge sustrateEdge : sustrateNetwork.getEnlacesFisicos()) {
        	System.out.println(sustrateEdge.getIdentificador() );
        	for (int i = 0; i < sustrateEdge.getFrequencySlot().length; i++) {
        		
        		System.out.println(sustrateEdge.getFrequencySlot()[i]);
			}
        }
        //System.out.println(ksp.get(listaSeleccionados.get(5)));
        long timeFinish = System.currentTimeMillis();
        System.out.println("complete.");
        System.out.println("Operation took " + (timeFinish - timeStart) / 1000.0 + " seconds.");
        //System.out.println(graph.getEdgeWeight("0", "1"));
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
