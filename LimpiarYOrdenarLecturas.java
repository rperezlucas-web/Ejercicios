
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Rocio
 */
public class LimpiarYOrdenarLecturas {
     static class Lectura {
         String sensorId;
         LocalDateTime timestamp;
         double valor;
         
         Lectura (String sensorId, LocalDateTime timestamp, double valor) {
             this.sensorId = sensorId;
             this.timestamp = timestamp;
             this.valor = valor;
         }
       }
     public static void main (String[] args) throws Exception{
         BufferedReader entrada = new BufferedReader(
         new InputStreamReader (System.in));
         List <Lectura>lecturas= new ArrayList<>();
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss");
                 String linea;
            while ((linea = entrada.readLine()) != null) {
    try {
        String[] partes = linea.split(";"); 
        if (partes.length != 3) continue;
        
        String sensorId = partes[0];
        LocalDateTime timestamp = LocalDateTime.parse(partes[1], formatter); // 
        double valor = Double.parseDouble(partes[2]);
        
        if (valor >= -20 && valor <= 60) { // vamos a descartar las que estan fuera de rango
            lecturas.add(new Lectura(sensorId, timestamp, valor)); 
        }
    } catch (NumberFormatException e) {
    }}
        lecturas.sort(Comparator.comparing((Lectura l) -> l.sensorId)
                                .thenComparing(l -> l.timestamp));

        // 3. Imprimir
        for (Lectura l : lecturas) {
            System.out.printf(Locale.US, "%s;%s;%.2f%n", 
                              l.sensorId, l.timestamp.format(formatter), l.valor);
        }
    }
            } 
   
