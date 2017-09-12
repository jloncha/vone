package py.una.pol.vone.util;

import org.moeaframework.mymodel.SustrateNetwork;
import org.moeaframework.mymodel.VirtualEdge;

import py.una.pol.vone.nsga.MOEAParameters;
import py.una.pol.vone.nsga.Objetivo2;
import py.una.pol.vone.nsga.Objetivo3;
import py.una.pol.vone.rmsa.Rmsa;

public class MoeaUtil {

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
	
	public double[] getFuncions(boolean[][] mat, MOEAParameters parameters) {
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
		int identificador1, identificador2, nodoFisicoId1 = 0, nodoFisicoId2 = 0;
		
		Rmsa rmsa = new Rmsa();
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
				SustrateNetwork network = rmsa.realizarRmsa(parameters.getRedSustrato(), String.valueOf(nodoFisicoId1), 
						String.valueOf(nodoFisicoId2), parameters.getKshort(), virtualEdge.getCantidadFS());
				
				parameters.setRedSustrato(network);
			} catch(Exception e){
				e.printStackTrace();
			}
			
		}
		
		double[] resp = new double[parameters.getNroObjetivos()];
		Objetivo2 obj2 = new Objetivo2();
		Objetivo3 obj3 = new Objetivo3();
		try {
			obj2.getEvaluacion(parameters.getRedSustrato());
			obj3.getEvaluacion(parameters.getRedSustrato());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		resp[0] = rmsa.getEvaluatefuntion1();
		resp[1] = obj2.getFitness();
		resp[2] = obj3.getFitness();
		
		return resp;
	}
	
	public double[] getContrains(){
		return new double[2];
	}
}
