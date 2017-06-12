package py.una.pol.vone;

import py.una.pol.vone.sa.Core;
import py.una.pol.vone.util.CargarRed;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        CargarRed red = new CargarRed();
        Core problema = new Core(red.redVirtual, red.redFisica);
		problema.generateFirstSolution();
    }
}
