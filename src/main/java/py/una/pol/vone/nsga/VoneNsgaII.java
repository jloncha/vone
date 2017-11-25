package py.una.pol.vone.nsga;

import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.problem.AbstractProblem;

import py.una.pol.vone.simulator.model.SustrateNetwork;
import py.una.pol.vone.simulator.model.VirtualNetwork;
import py.una.pol.vone.util.MoeaUtil;

public class VoneNsgaII extends AbstractProblem{

	static MOEAParameters parameters;

	/*public VoneNsgaII(int numberOfVariables, int numberOfObjectives, int numberOfConstraints) {
		super(numberOfVariables, numberOfObjectives, numberOfConstraints);
	}*/
	
	public VoneNsgaII() {
		super(1, 4, 4);
	}

	public void cargarParametros(Integer nroObjetivos, Integer nroRestricciones, Integer nroVariable,
			Integer nodosFisicos, Integer nodosVirtuales, SustrateNetwork network, VirtualNetwork virtualNetwork, 
			Integer kshort, Boolean obj1, Boolean obj2, Boolean obj3, Boolean obj4){
		parameters = new MOEAParameters();
		parameters.setNodosFisicos(nodosFisicos);
		parameters.setNodosVirtuales(nodosVirtuales);
		parameters.setNroObjetivos(nroObjetivos);
		parameters.setNroRestricciones(nroRestricciones);
		parameters.setNroVariableDecision(nroVariable);
		parameters.setRedSustrato(network);
		parameters.setRedVirtual(virtualNetwork);
		parameters.setKshort(kshort);
		parameters.setObj1(obj1);
		parameters.setObj2(obj2);
		parameters.setObj3(obj3);
		parameters.setObj4(obj4);
	}
	
	@Override
	public void evaluate(Solution solution) {
		//double[] funciones = new double[parameters.getNroObjetivos()];
		double[] restricciones = new double[parameters.getNroRestricciones()];
		SolucionMoea solucion = null;
		SustrateNetwork sustrate = null;
		VirtualNetwork virtualNetwork = null;
		MoeaUtil util = new MoeaUtil();
		boolean[] d = EncodingUtils.getBinary(solution.getVariable(0));
		//boolean[] d =  {false,false,false,true,false,false,false,false,false,false,true,false,false,false,true,false,false,false,false,true,false,false,false,false};
		//System.out.println("red al inicio: " + parameters.getRedSustrato());
		//System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% " + solution.getVariable(0) );
		
		boolean[][] mat = util.generateMat(d, parameters.getNodosVirtuales(), parameters.getNodosFisicos());
		
		/*for (int i = 0; i < parameters.getNodosVirtuales(); i++) {
			for (int j = 0; j < parameters.getNodosFisicos(); j++) {
				System.out.print(mat[i][j] + " ");
			}
			System.out.println();
		}*/
		try{
			sustrate = (SustrateNetwork) parameters.getRedSustrato().clone();
			virtualNetwork = (VirtualNetwork) parameters.getRedVirtual().clone();
		} catch(CloneNotSupportedException ex){
			ex.printStackTrace();
		}
		
		//obtener las restricciones
		boolean band = false;
		restricciones = util.getContrains(mat, parameters);
		for (int i = 0; i < restricciones.length - 2; i++) {
			if(restricciones[i] != 0 ){
				band = true;
			}
		}
		//en caso de que cumpla las restricciones de un uno solo por fila y
		//un uno o cero por columna invoca al getfuncions
		if(!band){
			
			solucion = util.getFuncions(mat, parameters, sustrate, virtualNetwork);
			//System.out.println(parameters.getRedSustrato());
			
		} /*else {
			//solucion = null;
		}*/
		//no cumple las 2 primeras restricciones
		if(solucion == null){
			double[] notValue = new double[parameters.getNroObjetivos()];
			for (int i = 0; i < parameters.getNroObjetivos(); i++) {
				notValue[i] = Double.MAX_VALUE;
			}
			solution.setObjectives(notValue);
		}
		//rechazo de cpu
		else if(solucion.getRechazo() == 1){
			double[] notValue = new double[parameters.getNroObjetivos()];
			for (int i = 0; i < parameters.getNroObjetivos(); i++) {
				notValue[i] = Double.MAX_VALUE -1.0;
			}
			solution.setObjectives(notValue);
			restricciones[2] = 2;
			restricciones[3] = 0;
			//System.out.println("cpu");
		//rechazo por enlace
		} else if(solucion.getRechazo() == 2){
			double[] notValue = new double[parameters.getNroObjetivos()];
			for (int i = 0; i < parameters.getNroObjetivos(); i++) {
				notValue[i] = Double.MAX_VALUE - 500.00;
			}
			solution.setObjectives(notValue);
			restricciones[3] = 3;
			restricciones[2] = 0;
			//System.out.println("enlace");
		}
		else{
			solution.setObjectives(solucion.getFunctions());
			restricciones[parameters.getNroRestricciones() - 2] = 0;
			restricciones[parameters.getNroRestricciones() - 1] = 0;
			solution.setAttribute("sustrateOriginal", parameters.getRedSustrato());
			/*solution.setAttribute("sustrateMapeada", solucion.getSustrateNetwork());
			solution.setAttribute("listPath", (Serializable) solucion.getList());*/
			solution.setAttribute("solucionMoea", solucion);
			/*System.out.println("*****************************");
			System.out.println("d: " + solution.getVariable(0));
			for (int i = 0; i < restricciones.length; i++) {
				System.out.print(restricciones[i] + " ");
			}
			System.out.println();
			for (int i = 0; i < funciones.length; i++) {
				System.out.print(funciones[i] + " ");
			}
			System.out.println();
			System.out.println("#############################");*/
			
			//System.out.println("****************************************************** " + solution.getVariable(0));
		}
		
		solution.setConstraints(restricciones);
		
	}
	
	@Override
	public Solution newSolution(){

		Integer nroObjetivos = parameters.getNroObjetivos();
		Integer nroRestricciones = parameters.getNroRestricciones();
		Integer nroVariableDecision = parameters.getNroVariableDecision();
		Integer nodosFisicos = parameters.getNodosFisicos();
		Integer nodosVirtuales = parameters.getNodosVirtuales();

		Solution solution = new Solution(nroVariableDecision, nroObjetivos, nroRestricciones);
		solution.setVariable(0, EncodingUtils.newBinary(nodosFisicos * nodosVirtuales));
		
		return solution;
	}	
}
