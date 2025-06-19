package importacion;

import grafos.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import red.*;
import utiles.*;

public class ImportarArchivos {

    //------------------------------------<ROBOPUERTOS>------------------------------------
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
            System.err.println(e.getMessage());
        } finally {
            if (lector != null) {
                try {
                    lector.close();
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
        return listaRoboPuertos;
    }

    //------------------------------------<ITEMS>------------------------------------
    public ArrayList<Item> leerArchivoItems() {
        InputStream nombreArchivo = getClass().getResourceAsStream("/importacion/archivoItems.csv");
        //Ver como mejorar la ruta del archivo
        ArrayList<Item> listaItems = new ArrayList<>();
        BufferedReader lector = null;
        try {
            lector = new BufferedReader(new InputStreamReader(nombreArchivo));
            String linea;
            while ((linea = lector.readLine()) != null) {
                String[] valores = linea.split(";"); // Divide la línea por punto y coma
                if (valores.length < 3) {
                    System.out.println("Línea inválida: " + linea);
                    continue; // Saltar la línea
                }

                int id = Integer.parseInt(valores[0]);
                String descripcion = valores[1];
                String nombre = valores[2];
                Item item = new Item(id, nombre, descripcion);
                listaItems.add(item);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } finally {
            if (lector != null) {
                try {
                    lector.close();
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
        return listaItems;
    }

    //------------------------------------<ROBOT>------------------------------------
    public ArrayList<Robot> leerArchivoRobots(ArrayList<Robopuerto> listaRobopuertos) {
        InputStream nombreArchivo = getClass().getResourceAsStream("/importacion/archivoRobots.csv");
        //Ver como mejorar la ruta del archivo
        HashMap<String, Robopuerto> mapaRobopuertos = new HashMap<>();
        ArrayList<Robot> listaRobots = new ArrayList<>();
        for (Robopuerto rp : listaRobopuertos) {
            mapaRobopuertos.put(rp.getId(), rp);
        }

        BufferedReader lector = null;
        try {
            lector = new BufferedReader(new InputStreamReader(nombreArchivo));
            String linea;
            while ((linea = lector.readLine()) != null) {
                String[] valores = linea.split(";"); // Divide la línea por punto y coma
                if (valores.length < 2) {
                    System.out.println("Línea inválida: " + linea);
                    continue; // Saltar la línea
                }

                String id_robot = valores[0];
                String id_robopuerto = valores[1];
                //Buscar el robopuerto correspondiente (si existe)
                Robopuerto roboPuerto = mapaRobopuertos.get(id_robopuerto);
                if (roboPuerto != null) {
                    Nodo nodo_spawn = roboPuerto.getNodo(); 
                    Robot robot = new Robot(id_robot, nodo_spawn);
                    listaRobots.add(robot);
                } else {
                    System.out.println("Robopuerto " + id_robopuerto + "no encontrado");
                }

            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } finally {
            if (lector != null) {
                try {
                    lector.close();
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
        return listaRobots;
    }

}

