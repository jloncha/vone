package py.una.pol.vone;

import java.io.Serializable;
import java.util.Random;

public class Prueba implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args){
		double a = 6; 
		double b = 12;
		
		double fitnes = 48;
		
		double result = new Double(fitnes / (a*b));
		Random r = new Random();
		int value = r.nextInt(2);	
		int m = 4/2;
		System.out.println(2+2*2);
	}
}
