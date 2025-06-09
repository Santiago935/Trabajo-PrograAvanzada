package resolucion;

import grafos.*;
import red.*;
import java.util.*;

public class Resolucion {

	
	public static void main(String[] args) {
		// Red 1
		Robopuerto rp1 = new Robopuerto(0, 0, "RP1");
		Robopuerto rp2 = new Robopuerto(15, 0, "RP2"); 
		Robopuerto rp3 = new Robopuerto(5, 8, "RP3");
		
		//Red 2
		Robopuerto rp4 = new Robopuerto(40, 0, "RP4"); 
		Robopuerto rp5 = new Robopuerto(55, 0, "RP5");
		
		Robopuerto rp6 = new Robopuerto(100, 100, "RP6"); //-> Alejado, red independiente
		
		
		// Cofres
		Cofre c1 = new Cofre(5, 0, "C1"); // cerca de RP1
		Cofre c2 = new Cofre(0, 7, "C2"); // cerca de RP1
		Cofre c3 = new Cofre(20, 0, "C3"); // cerca de RP2
		Cofre c4 = new Cofre(15, 7, "C4"); // cerca de RP2
		Cofre c5 = new Cofre(8, 0, "C5"); // en medio, dentro de ambos (sin estar en límite)
		Cofre c6 = new Cofre(50, 50, "C6"); // fuera de ambos

		Robopuerto[] robopuertos = { rp1, rp2, rp3, rp4, rp5, rp6};
		Cofre[] cofres = { c1, c2, c3, c4, c5, c6 };

		// Paso 1: Armar las redes
		ArrayList<Red> redes = ArmadoRed.armado_redes(robopuertos, cofres);

		// Paso 2: Asignar cofres a las redes
		ArmadoRed.armado_cofres(redes, cofres);

		// Paso 3: Generar grafos para cada red
		ArrayList<Grafo> grafos = ArmadoRed.generarGrafos(redes);

		
		
	}
	
	
	public static void pruebas_red() {
	    // Robopuertos con coordenadas fáciles
	    Robopuerto rp1 = new Robopuerto(0, 0, "RP1");
	    Robopuerto rp2 = new Robopuerto(3, 4, "RP2");
	    Robopuerto rp3 = new Robopuerto(50, 0, "RP3");
	    Robopuerto rp4 = new Robopuerto(53, 4, "RP4");
	    
	    
	    // Cofres con coordenadas simples
	    Cofre c1 = new Cofre(6, 0, "C1");
	    Cofre c2 = new Cofre(0, 5, "C2");
	    Cofre c3 = new Cofre(56, 0, "C3");
	    Cofre c4 = new Cofre(50, 5, "C4");
	    
	    
	    Robopuerto[] robopuertos = { rp1, rp2, rp3, rp4 };
	    Cofre[] cofres = { c1, c2 , c3, c4};

	    // Crear redes y agregar cofres
	    ArrayList<Red> redes = ArmadoRed.armado_redes(robopuertos, cofres);
	    ArmadoRed.armado_cofres(redes, cofres);

	    // Generar grafos para cada red
	    ArrayList<Grafo> grafos = ArmadoRed.generarGrafos(redes);

	    // Imprimir redes y grafos para verificar distancias
	    for (int i = 0; i < redes.size(); i++) {
	        Red red = redes.get(i);
	        System.out.println("=== Red " + (i + 1) + " ===");
	        System.out.println("Robopuertos:");
	        for (Robopuerto rp : red.getRobopuertos()) {
	            System.out.println(" - " + rp);
	        }
	        System.out.println("Cofres:");
	        for (Cofre cofre : red.getCofres()) {
	            System.out.println(" - " + cofre);
	        }
	        System.out.println("Grafo:");
	        grafos.get(i).imprimirGrafo();
	        
	    }
	}



	
//Fin
}
