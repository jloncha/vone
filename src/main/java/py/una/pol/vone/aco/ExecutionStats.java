package py.una.pol.vone.aco;

import java.util.Date;

/**
 * Clase que ejecuta los algoritmos
 * @author Jean
 *
 */
public class ExecutionStats {
	
	public double executionTime;
	
	public ACO aco;
	
	public Ant bestAnt;
	
	public static ExecutionStats execute(ACO aco) {
		ExecutionStats ets = new ExecutionStats();
		double startTime = (new Date()).getTime();

		ets.aco = aco;
		ets.bestAnt = aco.solve();
		ets.executionTime = (new Date()).getTime() - startTime;
		return ets;
	}
	
	public void printStats(){
		System.out.println("ACO Framework Version 1.0");
		System.out.println("Algorithm: "+bestAnt.getAco().getClass().getSimpleName());
		//System.out.println("Problem: "+bestAnt.aco.p.getClass().getSimpleName());
		//System.out.println("Instance Name: "+bestAnt.aco.p.getFilename());
		System.out.println("Execution time (ms): "+executionTime);
		System.out.println("Best Solution Found: " + bestAnt.getTour());
		System.out.println("Tour Length: "+ bestAnt.getTourLength());	
	}	
	
	/*public void printDotFormat() {
		System.out.println(Convert.toDot(bestAnt.getAco().getTau()));
	}*/

}
