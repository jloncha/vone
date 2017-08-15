package py.una.pol.vone;

import java.util.BitSet;

public class RunBitMatrix {

	public static void main(String[] args) {
		BitMatrix bitMatrix = new BitMatrix(3, 1);
		//System.out.println(bitMatrix.toString());
		BitSet prueba = new BitSet(5);
		prueba.set(0);
		//System.out.println(prueba.length());
		int value = -1;
		int i = 0;
		while (i<5) {
			i++;
			System.out.print(prueba.nextSetBit(value+1) + "\t");
		    value +=1;
		}
		System.out.println("FINAL");
	
		
		BitSet bs = new BitSet(5);

		// Número de elementos que tiene "activos" (bits a 1)
		assert 0 == bs.cardinality();

		// Añadimos 3 elementos: 0, 14, 2400 y el 80000
		bs.set(0, true);
		bs.set(14, true);
		bs.set(2400, true);
		bs.set(80000);  // Si no pasamos parametro, por defecto es true

		// Número de elementos que tiene "activos" (bits a 1)
		assert 4 == bs.cardinality();

		// Borrar un elemento es simplemente asignarlo a false
		bs.set(14, false);

		// Para saber si hay un elemento activo
		assert bs.get(0) == true;
		assert bs.get(14) == false;
		assert bs.get(2400) == true;
		value = -1;
		i = 0;
		while (i<1000) {
			i++;
		    System.out.println(bs.nextSetBit(value+1));
		    value +=1;
		}
		/*bitMatrix.set(1, 1, true);
		System.out.println(bitMatrix.toString());
		System.out.println(bitMatrix.get(0, 1));*/
	}
}
