package py.una.pol.vone.nsga;

import java.util.HashMap;
import java.util.Map;

import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.mymodel.SustrateNetwork;
import org.moeaframework.mymodel.VirtualNetwork;
import org.moeaframework.problem.AbstractProblem;

import py.una.pol.vone.util.MoeaUtil;

public class VoneNsgaII extends AbstractProblem{

	static MOEAParameters parameters;

	/*public VoneNsgaII(int numberOfVariables, int numberOfObjectives, int numberOfConstraints) {
		super(numberOfVariables, numberOfObjectives, numberOfConstraints);
	}*/
	
	public VoneNsgaII() {
		super(1, 3, 4);
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
		//boolean[] d =  {false,true,false,false,false,false,true,false,false,false,false,false,false,false,false,false,true,false,false,false,false,true,false,false};
		//System.out.println("red al inicio: " + parameters.getRedSustrato());
		
		
		boolean[][] mat = util.generateMat(d, parameters.getNodosVirtuales(), parameters.getNodosFisicos());
		/*for (int i = 0; i < parameters.getNodosVirtuales(); i++) {
			for (int j = 0; j < parameters.getNodosFisicos(); j++) {
				System.out.print(mat[i][j] + " ");
			}
			System.out.println();
		}*/
		//obtener las funciones
		boolean band = false;
		restricciones = util.getContrains(mat, parameters);
		for (int i = 0; i < restricciones.length - 1; i++) {
			if(restricciones[i] != 0 ){
				band = true;
			}
		}
		
		if(!band){
			System.out.println();
			System.out.println("d: " + solution.getVariable(0));
			for (int i = 0; i < restricciones.length; i++) {
				System.out.print(restricciones[i] + " ");
			}
			System.out.println("*****************************");
			System.out.println(parameters.getRedSustrato());
			funciones = util.getFuncions(mat, parameters);
			System.out.println("***************************** funtions " + funciones);
		} else {
			funciones = null;
		}
		if(funciones == null){
			double[] notValue = new double[parameters.getNroObjetivos()];
			for (int i = 0; i < parameters.getNroObjetivos(); i++) {
				notValue[i] = Double.MAX_VALUE;
			}
			solution.setObjectives(notValue);
		}else{
			solution.setObjectives(funciones);
			System.out.println("****************************************************** " + solution.getVariable(0));
		}
		
		//obtener las restricciones
		//restricciones = util.getContrains(mat, parameters, true);
		if(funciones != null){
			restricciones[parameters.getNroRestricciones() - 1] = 0;
		}
		solution.setConstraints(restricciones);
		solution.setAttribute("jean", util);
	}
	
	@Override
	public Solution newSolution(){

		Integer nroObjetivos = parameters.getNroObjetivos();
		Integer nroRestricciones = parameters.getNroRestricciones();
		Integer nroVariableDecision = parameters.getNroVariableDecision();
		Integer nodosFisicos = parameters.getNodosFisicos();
		Integer nodosVirtuales = parameters.getNodosVirtuales();
		
		Map<String, Object> nombreMap = new HashMap<String, Object>();
		nombreMap.put("redSustrato", "est es una prueba");
		Solution solution = new Solution(nroVariableDecision, nroObjetivos, nroRestricciones);
		solution.setVariable(0, EncodingUtils.newBinary(nodosFisicos * nodosVirtuales));
		
		return solution;
	}	
}
