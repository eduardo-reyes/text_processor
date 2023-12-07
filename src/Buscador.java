import java.io.*;

/*
 * @author: Eduardo Alfonso Reyes López.
 */

public class Buscador {

    /*
     * Método que lee el contenido de un archivo y 
     * regresa su contenido como una cadena de texto.
     * @returns String cadena con el texto del archivo. 
     */
    private static String readFile() {
        String file = "";
        try {
            BufferedReader in = new BufferedReader(new FileReader("LoR.txt"));
            String line;
            while((line = in.readLine()) != null) {
                  file = file + line + "\n";
            }
            in.close();
        } catch (FileNotFoundException e) {
            System.err.println("No se encontró el archivo en el path dado.");
        } catch (IOException ioe) {
            System.err.println("Error al leer el contenido del path.");
        }
      return file;
    }

        /*
         * Método que aplica la estrategia de búsqueda binaria
         * para encontrar los erores dentro del texto.
         * @param arreglo un arreglo de cadenas que contiene las palabras del texto.
         * @param izq es la cota izquierda del intervalo de búsqueda.
         * @param der es la cota derecha del intervalo de búsqueda.
         * @param p es un objeto de tipo Procesador.
         * @param numProceso es el método del procesador que vamos a usar.
         * @param etapa es la etapa del proceso en la que estamos.
         */
    private static String busquedaBinaria(String[] arreglo, int izq, int der, Procesador p, int numProceso, int etapa) {
        System.out.println("\nETAPA " + Integer.toString(etapa));
        if (der > izq) {
            int mitad = izq + (der - izq) / 2;
            StringBuffer sb = new StringBuffer();
            for(int i = izq; i <= mitad; i++) {
                sb.append(arreglo[i] + " ");
            }
            String texto = sb.toString();
            Boolean presente = verificar(p, numProceso, texto);
            if(presente) {
                System.out.println(texto);
                return busquedaBinaria(arreglo, izq, mitad, p, numProceso, etapa + 1);
            }
            else {
                sb = new StringBuffer();
                for(int i = mitad + 1; i <= der; i++) {
                    sb.append(arreglo[i] + " ");
                }
                System.out.println(sb.toString());
                return busquedaBinaria(arreglo, mitad + 1, der, p, numProceso, etapa + 1);
            }
        }
        else {
            if(verificar(p, numProceso, arreglo[izq])) {
                System.out.println("ERROR EN EL TEXTO: " + arreglo[izq]);
                return arreglo[izq];
            }
            else if(verificar(p, numProceso, arreglo[der])){
                System.out.println("ERROR EN EL TEXTO: " + arreglo[der]);
                return arreglo[der];
            }
            else {
                System.out.println("No hay error.");
            }
        }
        return "";
    }

    /*
     * Método que verifica según el número de proceso si
     * la palabra se encuentra dentro del texto pasado.
     * @param p es un objeto de tipo Procesador.
     * @param numProceso es el método del procesador que vamos a usar.
     * @param texto es la cadena con el texto a revisar.
     * @returns Boolean true si se encuentra la palabra, false e.o.c.
     */
    private static Boolean verificar(Procesador p, int numProceso, String texto) {
        Boolean presente;
        switch(numProceso) {
            case 1: presente = p.procesado1(texto);
                    break;
            case 2: presente = p.procesado2(texto);
                    break;
            default: presente = p.procesado3(texto);
                    break;
        } 
        return presente;
    }

    /*
     * Método que convierte una cadena de texto a
     * un arreglo de cadenas de palabras.
     * @param texto es la cadena a convertir.
     * @param String[] arreglo de palabras.
     */
    private static String[] cadenaArreglo(String texto) {
        String[] s = null;
        s = texto.split(" ");
        return s;
    }

public static void main(String[] args){
    String texto = readFile();
    String[] arregloTexto = cadenaArreglo(texto);
    Procesador p = new Procesador();
    String listaErrores = "";
    for(int i = 1; i <= 3; i++) {
        System.out.println("\n===== Procesado " + Integer.toString(i) + " =====");
        listaErrores = listaErrores + " " + busquedaBinaria(arregloTexto, 0, arregloTexto.length - 1, p, i, 1);
    }
    System.out.println("LISTA DE ERRORES: " + listaErrores);
}
} 