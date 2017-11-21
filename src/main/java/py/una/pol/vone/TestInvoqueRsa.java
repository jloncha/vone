package py.una.pol.vone;

import java.util.ArrayList;
import java.util.List;

import py.una.pol.vone.excepcions.ValidationsExceptions;
import py.una.pol.vone.kshortestpath.Path;
import py.una.pol.vone.nsga.SolucionMoea;
import py.una.pol.vone.rmsa.Rmsa;
import py.una.pol.vone.util.CargarRed;

public class TestInvoqueRsa {

	public static void main(String[] args) throws ValidationsExceptions{
		CargarRed cargarRed = new CargarRed();
		Rmsa rmsa = new Rmsa();
		SolucionMoea solucion = new SolucionMoea();
		List<Path> listPath = new ArrayList<Path>();
		solucion.setList(listPath);
		rmsa.realizarRmsa(cargarRed.redFisica, String.valueOf(0), 
				String.valueOf(4), 2, 2, solucion, null);
	}
}
