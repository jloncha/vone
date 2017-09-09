package py.una.pol.vone.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.moeaframework.mymodel.SustrateEdge;
import org.moeaframework.mymodel.SustrateNetwork;
import org.moeaframework.mymodel.SustrateNode;

/**
 * Clase utilizada para generar las topologias de redes virtuales.
 *
 * @author <a href="mailto: carlifer.fernando@gmail.com">Fernando Saucedo</a>
 * @version 1.0
 * @since 2017-03-04
 */
public class NetworkGenerator {

    /**
     * Metodo para generar el esqueleto de la red fisica.
     *
     * @param redFisica objeto que va a almacenar la red fisica.
     * @param pathRed direccion de donde obtener los enlaces de la red fisica.
     * @param cantidadNodos numero de nodos que va a contener la red fisica.
     */
    public static void generarRedFisica(SustrateNetwork redFisica,
            String pathRed, int cantidadNodos) {
        ArrayList<SustrateNode> nodosFisicos = new ArrayList<>();
        ArrayList<SustrateEdge> enlacesFisicos = new ArrayList<>();
        //Esta parte es para generar los nodos
        SustrateNode nodoNuevo;
        for (int i = 0; i < cantidadNodos; i++) {
            //Se hace el random entre 10 y 20 unidades de CPU.
            nodoNuevo = new SustrateNode();
            nodoNuevo.setIdentificador(i);
            nodoNuevo.setNombre("Nodo" + nodoNuevo.getIdentificador());
            nodosFisicos.add(nodoNuevo);
            nodoNuevo = null;
        }
        Collections.sort(nodosFisicos, new Comparator<SustrateNode>() {
            @Override
            public int compare(SustrateNode o1, SustrateNode o2) {
                return new Integer(o1.getIdentificador()).
                        compareTo(o2.getIdentificador());
            }
        });

        //Metodo para generar los enlaces fisicos
        try {
            @SuppressWarnings("resource")
            BufferedReader bf = new BufferedReader(new FileReader(pathRed));
            String linea = bf.readLine();
            SustrateNode nodoOrigen = null, nodoDestino = null;
            SustrateEdge enlaceNuevo;
            boolean origenEncontrado = false, destinoEncontrado = false;
            int distancia;
            while (linea != null) {
                String[] descripcionEnlace = linea.split("\\s");
                if (descripcionEnlace.length == 3) {
                    for (SustrateNode nodo : nodosFisicos) {
                        if (nodo.getIdentificador()
                                == Integer.valueOf(descripcionEnlace[0])) {
                            nodoOrigen = nodo;
                            origenEncontrado = true;
                        } else if (nodo.getIdentificador()
                                == Integer.valueOf(descripcionEnlace[1])) {
                            nodoDestino = nodo;
                            destinoEncontrado = true;
                        }
                        if (origenEncontrado && destinoEncontrado) {
                            break;
                        }
                    }

                    /*La distancia es un valor que agregue, 
                    entre 1 y 5 km, por si los lleguemos a utilizar.*/
                    distancia = Integer.valueOf(descripcionEnlace[2]);
                    /*Las dos siguientes lineas es para verificar que no exista 
                     *el enlace, ya que el archivo tiene dos enlaces por el 
                     *tema de convertir en no dirigido dicho grafo
                     */

                    //Se registra el nuevo enlace con los nodos hallados.
                    enlaceNuevo = new SustrateEdge();
                    enlaceNuevo.setDistancia(distancia);
                    enlaceNuevo.setNombre("ENLACE"
                            + nodoOrigen.getIdentificador() + "A"
                            + nodoDestino.getIdentificador());
                    enlaceNuevo.setNodoUno(nodoOrigen);
                    enlaceNuevo.setNodoDos(nodoDestino);
                    ArrayList<SustrateEdge> adyacentesOrigen
                            = nodoOrigen.getAdyacentes();
                    adyacentesOrigen.add(enlaceNuevo);
                    nodoOrigen.setAdyacentes(adyacentesOrigen);
                    ArrayList<SustrateEdge> adyacentesDestino
                            = nodoDestino.getAdyacentes();
                    adyacentesDestino.add(enlaceNuevo);
                    nodoDestino.setAdyacentes(adyacentesDestino);
                    enlacesFisicos.add(enlaceNuevo);
                    origenEncontrado = false;
                    destinoEncontrado = false;
                    nodoOrigen = null;
                    nodoDestino = null;
                    enlaceNuevo = null;

                }
                linea = bf.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        redFisica.setEnlacesFisicos(enlacesFisicos);
        redFisica.setNodosFisicos(nodosFisicos);
    }


}
