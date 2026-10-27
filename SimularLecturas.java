/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
*/
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;
/**
 *
 * @author Rocio
 */
public class SimularLecturas  {

    public static void main(String[] args) {
        Random random = new Random();// Vamos a empezar generando el aletorio.
        DateTimeFormatter formato= DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); //Para poner el formato de la fecha y hora
        LocalDateTime inicio = LocalDateTime.of(2025, 10, 1, 0, 0, 0);
        for (int i=0; i<50; i++){
            int sensorId = random.nextInt(3) +1;//Para que eliga entre sensor 1,2 o 3
             LocalDateTime fecha =
             inicio.plusSeconds(random.nextInt(172800));

             String timestamp = fecha.format(formato);
        double valor=5 + random.nextDouble()*35;
        
        System.out.printf(//Imprimimos el CSV
        Locale.US,
                "S%d;%s;%.2f%n",
                sensorId,
                timestamp,
                valor
        );
        }
        
       
    }
}