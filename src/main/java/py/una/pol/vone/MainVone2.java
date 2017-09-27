package py.una.pol.vone;

import py.una.pol.vone.simulator.model.SustrateNetwork;
import py.una.pol.vone.simulator.model.VirtualNetwork;
import py.una.pol.vone.util.CargarRed;

public class MainVone2 {
	
	public static void main(String[] args){
		double[] valSolFinal;
		Integer cont = 0;
		AlgoritmoNSGA nsga = new AlgoritmoNSGA();
		CargarRed cargarRed = new CargarRed();
		SustrateNetwork network = cargarRed.redFisica;
		VirtualNetwork virtualNetwork = cargarRed.redVirtual;
		int resp = nsga.moeaDinamico(network, virtualNetwork);
		System.out.println(virtualNetwork);
		System.out.println("Respuesta " + resp);
	}

}
