package py.una.pol.vone;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

public class LeerFicheros {

	public static void main(String[] args) {
		/*
		 * String so = System.getProperty("os.name"); String separador =
		 * System.getProperty("file.separator"); String directorio =
		 * System.getProperty("user.dir"); System.out.println(so);
		 * System.out.println(separador); System.out.println(directorio);
		 */
		FileReader fr = null;
		BufferedReader br = null;
		FileWriter fichero = null;
		PrintWriter pw = null;
		int i;
		try {
			File folder = new File(
					"C:\\Users\\acer\\workspace\\VONE_Simulator\\vone_results\\RedFisica1Nsfnet\\Voraz\\Conjunto11-15");
			File[] listOfFiles = folder.listFiles();
			fichero = new FileWriter(
					"C:\\Users\\acer\\workspace\\VONE_Simulator\\vone_results\\RedFisica1Nsfnet\\Voraz\\filas11-15.csv", true);
			pw = new PrintWriter(fichero);
			for (File file : listOfFiles) {
				if (file.isFile()) {
					String hora = "";
					String rechazo = "";
					String utilizacion = "";
					String costo = "";
					String revenue = "";
					String revCosto = "";
					String voneNP = "";
					String fila;
					// System.out.println("Inicio Archivo: " + file.getName());

					fr = new FileReader(file);
					br = new BufferedReader(fr);
					
					String linea;
					i = 0;
					while ((linea = br.readLine()) != null && i < 18) {
						if (i == 3) {
							// System.out.println(linea);
							String[] values = linea.split(" ");
							hora = values[8];
						}
						if (i == 9) {
							// System.out.println(linea);
							String[] values = linea.split(" ");
							// System.out.println(values[12].replace(",", "."));
							rechazo = values[12].replace(",", ".");
						}
						if (i == 12) {
							// System.out.println(linea);
							String[] values = linea.split(" ");
							// System.out.println(values[10]);
							utilizacion = values[10];
						}
						if (i == 13) {
							// System.out.println(linea);
							String[] values = linea.split(" ");
							// System.out.println(values[14]);
							costo = values[14];
						}
						if (i == 14) {
							// System.out.println(linea);
							String[] values = linea.split(" ");
							// System.out.println(values[14]);
							revenue = values[14];
						}
						if (i == 15) {
							// System.out.println(linea);
							String[] values = linea.split(" ");
							// System.out.println(values[9].replace(",", "."));
							revCosto = values[9].replace(",", ".");
						}
						if (i == 16) {
							// System.out.println(linea);
							String[] values = linea.split(" ");
							// System.out.println(values[12].replace(",", "."));
							voneNP = values[12].replace(",", ".");
						}

						i++;
					}
					if (null != fr) {
						fr.close();
					}
					fila = rechazo.concat(";").concat(utilizacion).concat(";").concat(costo).concat(";").concat(revenue)
							.concat(";").concat(revCosto).concat(";").concat(voneNP).concat("; ; ;").concat(hora)
							.concat(";").concat(file.getName()).concat(";");
					// System.out.println("Fila: " + fila);
					pw.println(fila);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != fr) {
					fr.close();
				}
				if (null != fichero)
					fichero.close();

			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

	}
}
