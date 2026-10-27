/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ejercicio2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;
/**
 *
 * @author Rocio
 */
public class CoordinarProcesos {
    
  public static void main(String[] args) {

            if (args.length < 3) {
            System.out.println("Uso: java CoordinarProcesos <entrada.csv> <numProcesos> <salida_final.csv> [--minAlert=30.0]");
            return;
        }

        String ficheroEntrada = args[0];
        int numProcesos = Integer.parseInt(args[1]);
        String ficheroSalidaFinal = args[2];
        String parametroMinAlert = null;

            if (args.length >= 4) {
            parametroMinAlert = args[3];
        }

        BufferedReader br = null;
        BufferedWriter bw = null;
        BufferedWriter bwFinal = null;

        try {
            List<String> lineas = new ArrayList<String>();
            List<String> archivosEntradaParcial = new ArrayList<String>();
            List<String> archivosSalidaParcial = new ArrayList<String>();
            List<Process> procesos = new ArrayList<Process>();

            br = new BufferedReader(new FileReader(ficheroEntrada));
            String linea;

            while ((linea = br.readLine()) != null) {
                if (!linea.trim().isEmpty()) {
                    lineas.add(linea);
                }
            }
            br.close();
            if (lineas.isEmpty()) {
                System.out.println("El fichero de entrada no contiene nada.");
                return;
            }
            if (numProcesos <= 0) {
                System.out.println("Debe tener un número de procesos mayo a 0.");
                return;
            }

          if (numProcesos > lineas.size()) {
                numProcesos = lineas.size();
            }
            int totalLineas = lineas.size();
            int base = totalLineas / numProcesos;
            int resto = totalLineas % numProcesos;

            int inicio = 0;

            for (int i = 0; i < numProcesos; i++) {
                int cantidadLineas = base;

                if (i < resto) {
                    cantidadLineas++;
                }

                int fin = inicio + cantidadLineas;

             String entradaParcial = "ejercicio2/pruebas/entrada_parcial_" + i + ".csv";
             String salidaParcial = "ejercicio2/pruebas/salida_parcial_" + i + ".csv";

                archivosEntradaParcial.add(entradaParcial);
                archivosSalidaParcial.add(salidaParcial);

                bw = new BufferedWriter(new FileWriter(entradaParcial));

                for (int j = inicio; j < fin; j++) {
                    bw.write(lineas.get(j));
                    bw.newLine();
                }
                bw.close();
                List<String> comando = new ArrayList<String>();
                comando.add("java");
                comando.add("-cp");
                comando.add(".");
                comando.add("ejercicio2.TransformarLecturas");
                comando.add(entradaParcial);
                comando.add(salidaParcial);

                if (parametroMinAlert != null) {
                    comando.add(parametroMinAlert);
                }

                ProcessBuilder pb = new ProcessBuilder(comando);
                pb.inheritIO();

                Process proceso = pb.start();
                procesos.add(proceso);

                inicio = fin;
            }

        boolean error = false;

        for (int i = 0; i < procesos.size(); i++) {
            int codigoSalida = procesos.get(i).waitFor();

        if (codigoSalida != 0) {
        System.out.println("El proceso " + i
                + " ha fallado. Código: " + codigoSalida);
        error = true;
    }
}

if (error) {
    System.out.println("No se puede generar el fichero final.");
    return;

            }
            bwFinal = new BufferedWriter(new FileWriter(ficheroSalidaFinal));
            for (int i = 0; i < archivosSalidaParcial.size(); i++) {
                br = new BufferedReader(new FileReader(archivosSalidaParcial.get(i)));

                while ((linea = br.readLine()) != null) {
                    bwFinal.write(linea);
                    bwFinal.newLine();
                }
                br.close();
            }
            bwFinal.close();
            System.out.println("Proceso correcto.");
            System.out.println("Fichero final generado: " + ficheroSalidaFinal);

        } catch (Exception e) {
            e.printStackTrace();
        }
  }
}