package importacion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import red.Robopuerto;


public class ImportarArchivos {

    public ArrayList<Robopuerto> leerArchivoRobopuertos() {
        InputStream nombreArchivo = getClass().getResourceAsStream("/importacion/archivoRoboPuertos.csv");
        //Ver como mejorar la ruta del archivo
        ArrayList<Robopuerto> listaRoboPuertos = new ArrayList<>();
        BufferedReader lector = null;
        try {
            lector = new BufferedReader(new InputStreamReader(nombreArchivo));
            String linea;
            while ((linea = lector.readLine()) != null) {
                String[] valores = linea.split(";"); // Divide la línea por punto y coma
                int x = Integer.parseInt(valores[1]);
                int y = Integer.parseInt(valores[2]);
                String id = valores[0];
                double radio = Double.parseDouble(valores[3]);
                Robopuerto roboPuerto = new Robopuerto(id, x, y, radio);
                listaRoboPuertos.add(roboPuerto);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (lector != null) {
                try {
                    lector.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return listaRoboPuertos;
    }

}
