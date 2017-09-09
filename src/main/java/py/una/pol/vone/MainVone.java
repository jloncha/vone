package py.una.pol.vone;


import org.moeaframework.mymodel.SustrateNetwork;
import org.moeaframework.mymodel.VirtualNetwork;

import py.una.pol.vone.nsga.MOEAParameters;
import py.una.pol.vone.util.CargarRed;

public class MainVone {

	public static void main(String[] args){
		CargarRed cargarRed = new CargarRed();
		SustrateNetwork network = cargarRed.redFisica;
		VirtualNetwork virtualNetwork = cargarRed.redVirtual;
		
		org.moeaframework.mymodel.SustrateNetwork framework = new org.moeaframework.mymodel.SustrateNetwork();
		framework = network;
		
		MOEAParameters parameters = new MOEAParameters();
		parameters.setNroObjetivos(3);
		parameters.setNroRestricciones(3);
		parameters.setNroVariableDecision(1);
		parameters.setRedSustrato(network);
	}
}
