package py.una.pol.vone.util;

import org.moeaframework.mymodel.SustrateNetwork;

import py.una.pol.vone.nsga.Objetivo2;
import py.una.pol.vone.nsga.Objetivo3;

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
	
	public double[] getFuncions(SustrateNetwork redSustrato){
		/* para la funcion objetivo 1: desde la fila 1 de la matriz, buscar todos los enlaces de la red virtual que 
		 * parten de ese punto, evitar ciclos(esto es por ejemplo si ya se obtuvo enlace de 1 a 2, no obtener enlace de 2 a 1)
		 * una vez que se obtiene un enlace invocar al algoritmo del rsa, con este valor del path calcular la funcion
		 * objetivo 1 en donde es saltos por cantidad de frecuency slots utilizado
		 *
		 * para la funcion objetivo 2: trabajar sobre red fisica y con la formula de balance de carga, calcular el balanceo
		 * de la red luego de asignar todos los enlaces que ocuparan los vnr
		 */
		
		/***
		 * El segundo objetivo consiste en la reduccion de fragmentacion
		 * La misma se controla el minimizar el indice de FS utilizado/cantEnlaces
		 */
		double[] resp = new double[3];
		Objetivo2 obj2 = new Objetivo2();
		Objetivo3 obj3 = new Objetivo3();
		try {
			obj2.getEvaluacion(redSustrato);
			obj3.getEvaluacion(redSustrato);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		resp[1] = obj2.getFitness();
		resp[2] = obj3.getFitness();
		return resp;
	}
	
	public double[] getContrains(){
		return new double[2];
	}
}
