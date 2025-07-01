package resolucion;

import cofres.Cofre;
import cola_de_prioridad.TDA.Cola_prioridad_heap;
import grafos.AlgoritmosGrafos;
import grafos.Grafo;
import grafos.Nodo;
import importaciones.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import red.ArmadoRed;
import red.Red;
import red.Robopuerto;
import red.Robot;
import utiles.Item;
import utiles.Pedido;
import utiles.SimuladorRed;

public class Resolucion {

	// ------------------- Obtener pedidos ORDENADOS ----- //

	public static Cola_prioridad_heap<Pedido> obtenerPedidosOrdenados(ArrayList<Pedido> listaDesordenada) {
		// Uso heap de mínimo para priorizar pedidos mas cortos
		Cola_prioridad_heap<Pedido> pedidos = new Cola_prioridad_heap<>();

		for (Pedido pedido : listaDesordenada) {
			pedidos.encolar(pedido);
		}

		return pedidos;
	}

	// -------------------------------------------------//

	public static void main(String[] args) {

		Importar importador = new Importar();

		// PASO 1: cargar robopuertos
		ArrayList<Robopuerto> robopuertos = importador.leerArchivoRobopuertos();

		// PASO 2: cargar robots
		ArrayList<Robot> robots = importador.leerArchivoRobots(robopuertos);

		// reemplazar por importado de cofres
		// PASO 3: cargar cofres
		ArrayList<Cofre> cofres = importador.leerArchivoCofres();

		// Paso 4: cargar items en el sistema
		ArrayList<Item> listaItems = importador.leerArchivoItems();

		// mostrar el entorno
		mostrarEntorno(robopuertos, robots, cofres, listaItems);

		ArrayList<Red> redes = ArmadoRed.armado_redes(robopuertos, cofres, robots);

		System.out.println("\nLAS REDES SON:");
		for (Red red : redes) {
			System.out.println(red);
		}

		// Paso 5: importar stock de cofres
		importador.leerArchivoStock(listaItems, cofres);

		// Paso 6: importar stock ofertado
		importador.leerArchivoOferta(listaItems, cofres);

		// Paso 7: importar las solicitudes de items:
		importador.leerArchivoSolicitud(listaItems, cofres);

		/////////////// SIMULACION DE LA RED ///////////////
		SimuladorRed simulador = new SimuladorRed(redes.get(0));

		// 1) Armar pedidos
		System.out.println("\n\nRED SIMULADA: \n" + redes.get(0));
		ArrayList<Pedido> pedidos = Pedido.armado_pedidos3(redes.get(0));

		if (pedidos.isEmpty()) {
			System.out.println("ERROR, No se pudieron generar pedidos. Verificar stock y solicitudes.");
			return; // en vez de retornar poner un else
		}

		// 2) Cargar pedidos al simulador
		System.out.println("PAQUETES DE LA RED -> " + redes.get(0).getId());
		for (Pedido pedido : pedidos) {
			System.out.println(pedido);
			simulador.agregarPedido(pedido);
		}

		// 3) Armar grafo de la red con costo unitario por arista de 1.0
		//redes.get(0).construirGrafo(1.0);
			
		
		if (redes.get(0).getGrafo_red() == null) {
			System.out.println("El grafo está vacío.");
		} else {
			redes.get(0).getGrafo_red().imprimirGrafo();
		}

		// 4) Ejecutar turnos hasta que no haya robots trabajando ni pedidos
		int turno = 0;
		while (!simulador.estaTodoListo()) {
			System.out.println("----- Turno " + turno + " -----");
			simulador.simularTurno();

			// Dormir el hilo por 1 segundo (1000 ms)
			try {
				Thread.sleep(2000); // puedes ajustar el valor
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				System.out.println("Simulación interrumpida.");
				break;
			}

			turno++;
		}

		System.out.println("Simulacion finalizada en " + turno + " turnos.");
		System.out.println("\nHOLA MUNDO DE LA NUEVA VERSION FINAL, FIN DEL TP...");
	}

	public static void mostrarEntorno(ArrayList<Robopuerto> robopuertos, ArrayList<Robot> robots,
			ArrayList<Cofre> cofres, ArrayList<Item> listaItems) {

		System.out.println("LOS ROBOPUERTOS SON:");
		for (Robopuerto aux : robopuertos) {
			System.out.println(aux);
		}
		System.out.println("--------------------");

		System.out.println("\nLOS ROBOTS SON:");
		for (Robot aux : robots) {
			System.out.println(aux);
		}
		System.out.println("--------------------");

		System.out.println("\nLOS COFRES SON:");
		for (Cofre aux : cofres) {
			System.out.println(aux);
		}
		System.out.println("--------------------");
		System.out.println("\nLOS ITEMS SON:");
		for (Item aux : listaItems) {
			System.out.println(aux);
		}
		System.out.println("--------------------");

	}

	/*
	 *
	 *
	 *
	 *
	 * ArrayList<Robopuerto> robopuertos = new ArrayList<>(Arrays.asList(rp1, rp2,
	 * rp3, rp4)); Cofre[] cofres = { c1, c2 , c3, c4};
	 *
	 * // Crear redes y agregar cofres ArrayList<Red> redes =
	 * ArmadoRed.armado_redes(robopuertos, cofres); ArmadoRed.armado_cofres(redes,
	 * cofres);
	 *
	 * // Generar grafos para cada red ArrayList<Grafo> grafos =
	 * ArmadoRed.generarGrafos(redes);
	 *
	 * // Imprimir redes y grafos para verificar distancias for (int i = 0; i <
	 * redes.size(); i++) { Red red = redes.get(i); System.out.println("=== Red " +
	 * (i + 1) + " ==="); System.out.println("Robopuertos:"); for (Robopuerto rp :
	 * red.getRobopuertos()) { System.out.println(" - " + rp); }
	 * System.out.println("Cofres:"); for (Cofre cofre : red.getCofres()) {
	 * System.out.println(" - " + cofre); } System.out.println("Grafo:");
	 * grafos.get(i).imprimirGrafo();
	 *
	 * } }
	 */



//Fin
}
