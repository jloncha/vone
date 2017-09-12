package py.una.pol.vone.nsga;

import org.moeaframework.core.Solution;
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
}
