/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ejercicio2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
/**
 *
 * @author Rocio
 */
public class TransformarLecturas {

    public static void main(String[] args) {

        if (args.length < 2) {
            System.out.println(
                "Uso: java TransformarLecturas entrada.csv salida.csv [--minAlert=30.0]");
            return;
        }

        String archivoEntrada = args[0];
        String archivoSalida = args[1];
        double umbral = 30.0;

        for (int i = 2; i < args.length; i++) {
            if (args[i].startsWith("--minAlert=")) {
                try {
                    umbral = Double.parseDouble(
                        args[i].substring("--minAlert=".length()));
                } catch (NumberFormatException e) {
                    System.out.println("El umbral no es válido.");
                    return;
                }
            }
        }

        Path entrada = Paths.get(archivoEntrada);
        Path salida = Paths.get(archivoSalida);

        try (
            BufferedReader lector = Files.newBufferedReader(entrada);
            BufferedWriter escritor = Files.newBufferedWriter(salida)
        ) {
            String linea;

            while ((linea = lector.readLine()) != null) {
                String[] partes = linea.split(";", -1);

                if (partes.length != 3) {
                    continue;
                }

                try {
                    String sensorId = partes[0].trim();
                    String timestamp = partes[1].trim();
                    double valor = Double.parseDouble(partes[2].trim());

                    if (!sensorId.matches("S\\d+")
                            || !Double.isFinite(valor)) {
                        continue;
                    }

                    String estado = valor >= umbral
                            ? "ALERTA" : "OK";

                    escritor.write(String.format(
                        Locale.US, "%s;%s;%.2f;%s",
                        sensorId, timestamp, valor, estado));

                    escritor.newLine();

                } catch (NumberFormatException e) {
                    // Ignoramos las lecturas cuyo valor no sea numérico.
                }
            }

            System.out.println("Fichero generado: " + salida);

        } catch (IOException e) {
            System.out.println(
                "Error al leer o escribir los ficheros: " + e.getMessage());
        }
    }
}
