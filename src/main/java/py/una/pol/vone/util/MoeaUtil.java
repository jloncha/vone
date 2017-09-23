package py.una.pol.vone.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.moeaframework.mymodel.SustrateNetwork;
import org.moeaframework.mymodel.VirtualEdge;

import py.una.pol.vone.kshortestpath.Path;
import py.una.pol.vone.nsga.MOEAParameters;
import py.una.pol.vone.nsga.Objetivo2;
import py.una.pol.vone.nsga.Objetivo3;
import py.una.pol.vone.nsga.Objetivo4;
import py.una.pol.vone.nsga.SolucionMoea;
import py.una.pol.vone.rmsa.Rmsa;

public class MoeaUtil implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public boolean[][] generateMat(boolean[] d, Integer m, Integer n){
		boolean mat[][] = new boolean[m][n]; 
		int cont = 0;
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				mat[i][j] = d[cont];
				cont++;
			}
		}
		return mat;
	}
	
	public SolucionMoea getFuncions(boolean[][] mat, MOEAParameters parameters, SustrateNetwork networkOrigin) {
		/** para la funcion objetivo 1: desde la fila 1 de la matriz, buscar todos los enlaces de la red virtual que 
		 * parten de ese punto, evitar ciclos(esto es por ejemplo si ya se obtuvo enlace de 1 a 2, no obtener enlace de 2 a 1)
		 * una vez que se obtiene un enlace invocar al algoritmo del rsa, con este valor del path calcular la funcion
		 * objetivo 1 en donde es saltos por cantidad de frecuency slots utilizado
		 *
		 */
		
		/***
		 * El segundo objetivo consiste en la reduccion de fragmentacion
		 * La misma se controla el minimizar el indice de FS utilizado/cantEnlaces
		 */
		
		/*double min = 100;
		double max = 101;
		double[] obj = new double[parameters.getNroRestricciones()];
		Random r = new Random();
		for (int i = 0; i < parameters.getNroRestricciones(); i++) {
			obj[i] =  min + (max - min) * r.nextDouble();
		}
		return obj;*/
		
		int identificador1, identificador2, nodoFisicoId1 = 0, nodoFisicoId2 = 0;
		SolucionMoea solucion = new SolucionMoea();
		List<VirtualEdge> virtualList = new ArrayList<>();
		List<Path> listPath = new ArrayList<>();
		solucion.setList(listPath);
		solucion.setVirtualEdge(virtualList);
		Rmsa rmsa = new Rmsa();
		SustrateNetwork network = null;
		for (VirtualEdge virtualEdge : parameters.getRedVirtual().getEnlacesVirtuales()) {
			identificador1 = virtualEdge.getNodoUno().getIdentificador();
			identificador2 = virtualEdge.getNodoDos().getIdentificador();
			for (int i = 0; i < mat[identificador1].length; i++) {
				if(mat[identificador1][i]){
					nodoFisicoId1 = i;
				}
			}
			for (int i = 0; i < mat[identificador2].length; i++) {
				if(mat[identificador2][i]){
					nodoFisicoId2 = i;
				}
			}
			try{
				//si no existe path o no se pudo realizar el rmsa devolver con constrains
				network = rmsa.realizarRmsa(networkOrigin, String.valueOf(nodoFisicoId1), 
						String.valueOf(nodoFisicoId2), parameters.getKshort(), virtualEdge.getCantidadFS(),
						solucion, virtualEdge);
				if(network == null){
					//System.out.println("red de sustrato null despues de invocar al rsa");
					return null;
				} else {
					networkOrigin = network;
					network = null;
					//parameters.setRedSustrato(network);
				}
				
			} catch(Exception e){
				e.printStackTrace();
			}
			
		}
		
		double[] resp = new double[parameters.getNroObjetivos()];
		Objetivo2 obj2 = new Objetivo2();
		Objetivo3 obj3 = new Objetivo3();
		Objetivo4 obj4 = new Objetivo4();
		try {
			obj2.getEvaluacion(networkOrigin);
			if(mapearNodos(mat, parameters, networkOrigin)!=1){
				//System.out.println("retorne null en mapearNodos objetivo3");
				return null;
			}
			obj3.getEvaluacion(networkOrigin);
			obj4.getEvaluacion(networkOrigin);
			//System.out.println("red al mapear todos los enlaces: " + parameters.getRedSustrato());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		resp[0] = rmsa.getEvaluatefuntion1();
		resp[1] = obj2.getFitness();
		resp[2] = obj3.getFitness();
		resp[3] = obj4.getFitness();
		
		solucion.setFunctions(resp);
		solucion.setSustrateNetwork(networkOrigin);
		return solucion;
	}
	
	public double[] getContrains(boolean[][] mat, MOEAParameters parameters){
		double[] resp = null;
		try {
			resp = new double[parameters.getNroRestricciones()];
			Integer m = parameters.getNodosVirtuales();
			Integer n = parameters.getNodosFisicos();
			// Inicializamos todas las restricciones a valor <>0
			for (Integer k = 0; k < parameters.getNroRestricciones(); k++) {
				resp[k] = 1;
			}
			// Primera restriccion, que implica que existe solo un uno en una
			// fila
			for (Integer i = 0; i < m; i++) {
				Integer sum = 0;
				for (Integer j = 0; j < n; j++) {
					sum = sum + (mat[i][j] ? 1 : 0);
				}
				// Significa que hay mas de un 1, se debe cortar el ciclo
				if (sum != 1) {
					return resp;
				}
			}
			// Si paso todo el ciclo significa que cumplio la primera
			// restriccion
			resp[0] = 0;
			// pasamos a la segunda restriccion
			for (Integer i = 0; i < n; i++) {
				Integer sum = 0;
				for (Integer j = 0; j < m; j++) {
					sum = sum + (mat[j][i] ? 1 : 0);
				}
				// Significa que hay mas de un 1, se debe cortar el ciclo
				if (sum > 1) {
					return resp;
				}
			}
			// Si paso, significa que tambien se cumplio la segunda restriccion
			resp[1] = 0;
			/*if(validar){
				// pasamos a la tercera restriccion, evaluar si los nodos fisicos
				// tienen suficiente CPU
				for (Integer i = 0; i < m; i++) {
					for (Integer j = 0; j < n; j++) {
						// significa que es un mapeado
						if (mat[i][j]) {
							cpuV = parameters.getRedVirtual().getNodosVirtuales().get(i).getCapacidadCPU();
							cpuF = parameters.getRedSustrato().getNodosFisicos().get(j).capacidadActual();
							if (cpuV > cpuF) {
								return resp;
							}
						}
					}
				}
				// Si paso, significa que cumple la tercera validacion tambien
				resp[2] = 0;
			}*/
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return resp;
	}
	
	public Integer mapearNodos(boolean[][] mat, MOEAParameters parameters, SustrateNetwork network){
		Integer cpuV = 0;
		Integer cpuF = 0;
		try{
			for (int i = 0; i < parameters.getNodosVirtuales(); i++) {
				for (int j = 0; j < parameters.getNodosFisicos(); j++) {
					//Significa que exta mapeado
					if(mat[i][j]){
						cpuV = parameters.getRedVirtual().getNodosVirtuales().get(i).getCapacidadCPU();
						cpuF = network.getNodosFisicos().get(j).capacidadActual();
						if (cpuV <=cpuF) {
							network.getNodosFisicos().get(j).asignarRecursoCPU(cpuV);;
							
						}
						else{
							return -1;
						}
					}
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return 1;
	}
}
