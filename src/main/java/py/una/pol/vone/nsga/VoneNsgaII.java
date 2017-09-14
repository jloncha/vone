package py.una.pol.vone.nsga;

import java.util.ArrayList;
import java.util.List;

import org.moeaframework.core.Solution;
import org.moeaframework.core.Variable;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.mymodel.SustrateNetwork;
import org.moeaframework.mymodel.VirtualNetwork;
import org.moeaframework.problem.AbstractProblem;

import py.una.pol.vone.util.MoeaUtil;

public class VoneNsgaII extends AbstractProblem{

	MOEAParameters parameters;

	public VoneNsgaII(int numberOfVariables, int numberOfObjectives, int numberOfConstraints) {
		super(numberOfVariables, numberOfObjectives, numberOfConstraints);
	}

	public void cargarParametros(Integer nroObjetivos, Integer nroRestricciones, Integer nroVariable,
			Integer nodosFisicos, Integer nodosVirtuales, SustrateNetwork network, VirtualNetwork virtualNetwork, 
			Integer kshort){
		parameters = new MOEAParameters();
		parameters.setNodosFisicos(nodosFisicos);
		parameters.setNodosVirtuales(nodosVirtuales);
		parameters.setNroObjetivos(nroObjetivos);
		parameters.setNroRestricciones(nroRestricciones);
		parameters.setNroVariableDecision(nroVariable);
		parameters.setRedSustrato(network);
		parameters.setRedVirtual(virtualNetwork);
		parameters.setKshort(kshort);
	}
	
	@Override
	public void evaluate(Solution solution) {
		double[] funciones = new double[parameters.getNroObjetivos()];
		double[] restricciones = new double[parameters.getNroRestricciones()];
		MoeaUtil util = new MoeaUtil();
		boolean[] d = EncodingUtils.getBinary(solution.getVariable(0));
		boolean[][] mat = util.generateMat(d, parameters.getNodosFisicos(), parameters.getNodosVirtuales());
		//obtener las funciones
		funciones = util.getFuncions(mat, parameters);
		solution.setObjectives(funciones);
		//obtener las restricciones
		restricciones = util.getContrains();
		solution.setConstraints(restricciones);
		restricciones = evaluarRestricciones(solution);
		solution.setConstraints(restricciones);
	}
	
	@Override
	public Solution newSolution(){

		Integer nroObjetivos = this.parameters.getNroObjetivos();
		Integer nroRestricciones = this.parameters.getNroRestricciones();
		Integer nroVariableDecision = this.parameters.getNroVariableDecision();
		Integer nodosFisicos = this.parameters.getNodosFisicos();
		Integer nodosVirtuales = this.parameters.getNodosVirtuales();
		
		Solution solution = new Solution(nroVariableDecision, nroObjetivos, nroRestricciones);
		solution.setVariable(0, EncodingUtils.newBinary(nodosFisicos * nodosVirtuales));
		
		return solution;
	}
	
	public double[] evaluarRestricciones(Solution solucion) {
		double[] resp = null;
		Integer cpuV = 0;
		Integer cpuF = 0;
		try {
			resp = new double[this.parameters.getNroRestricciones()];
			Integer m = this.parameters.getNodosVirtuales();
			Integer n = this.parameters.getNodosFisicos();
			// Casteamos la variable a una matriz de boolean para trabajar mejor
			boolean[] d = EncodingUtils.getBinary(solucion.getVariable(0));
			boolean[][] individuo = new boolean[m][n];
			individuo = convertArray2Mat(m, n, d);
			// Inicializamos todas las restricciones a valor <>0
			for (Integer k = 0; k < this.parameters.getNroRestricciones(); k++) {
				resp[k] = 1;
			}
			// Primera restriccion, que implica que existe solo un uno en una
			// fila
			for (Integer i = 0; i < m; i++) {
				Integer sum = 0;
				for (Integer j = 0; j < n; j++) {
					sum = sum + (individuo[i][j] ? 1 : 0);
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
					sum = sum + (individuo[j][i] ? 1 : 0);
				}
				// Significa que hay mas de un 1, se debe cortar el ciclo
				if (sum > 1) {
					return resp;
				}
			}
			// Si paso, significa que tambien se cumplio la segunda restriccion
			resp[1] = 0;
			// pasamos a la tercera restriccion, evaluar si los nodos fisicos
			// tienen suficiente CPU
			for (Integer i = 0; i < m; i++) {
				for (Integer j = 0; j < n; j++) {
					// significa que es un mapeado
					if (individuo[i][j]) {
						cpuV = this.parameters.getRedVirtual().getNodosVirtuales().get(i).getCapacidadCPU();
						cpuF = this.parameters.getRedSustrato().getNodosFisicos().get(j).getCapacidadCPU();
						if (cpuV > cpuF) {
							return resp;
						}
					}
				}
			}
			// Si paso, significa que cumple la tercera validacion tambien
			resp[2] = 0;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return resp;
	}

	/***
	 * Funcion que convierte un array de boolean en matriz
	 * @throws Exception 
	 */
	public boolean[][]convertArray2Mat(Integer fil, Integer col, boolean[] vector) 
			throws Exception{
		boolean[][] resp = new boolean[fil][col];
		Integer cont = 0;
		try{
			for(Integer i=0;i<fil;i++){
				for(Integer j=0;j<col; j++){
					resp[i][j] = vector[cont];
					cont++;
				}
			}
		}catch(Exception ex){
			throw new Exception(ex.getMessage());
		}
		return resp;
	}
		
}
