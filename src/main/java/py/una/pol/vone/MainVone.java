package py.una.pol.vone;


import org.moeaframework.mymodel.SustrateNetwork;
import org.moeaframework.mymodel.VirtualNetwork;

import py.una.pol.vone.nsga.VoneNsgaII;
import py.una.pol.vone.util.CargarRed;

public class MainVone {

	public static void main(String[] args){
		CargarRed cargarRed = new CargarRed();
		SustrateNetwork network = cargarRed.redFisica;
		VirtualNetwork virtualNetwork = cargarRed.redVirtual;
		
		VoneNsgaII nsga = new VoneNsgaII(1, 3, 3);
		nsga.cargarParametros(3, 3, 1, network.getNroNodos(), virtualNetwork.getNroNodos(), network, virtualNetwork,
				2);
		
		
	}
}
