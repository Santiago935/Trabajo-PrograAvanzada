package resolucion;

import cofres.Cofre;
import cofres.PuedeOfertar;
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

	public static void main(String[] args) {
		ejemploClase();
	}
	
	
	//---FUNCION DE MOSTRAR EL ENTORNO DONDE DE SIMULACION---
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

	/////////////// SIMULACION DE LA RED ///////////////
	public static void simulacionCompletaDeRed(Red red, ArrayList<Item> listaItems) {
		SimuladorRed simulador = new SimuladorRed(red);

		// 1) Armar pedidos
		System.out.println("\n---COMENZANDO SIMULACIÓN---");
		System.out.println("\n\nRED SIMULADA: \n" + red);
		ArrayList<Pedido> pedidos = Pedido.armado_pedidos3(red);

		if (pedidos.isEmpty()) {
			System.out.println("ERROR, No se pudieron generar pedidos. Verificar stock y solicitudes.");
			return; // en vez de retornar poner un else
		}

		// 2) Cargar pedidos al simulador
		System.out.println("PAQUETES DE LA RED -> " + red.getId());
		for (Pedido pedido : pedidos) {
			System.out.println(pedido);
			simulador.agregarPedido(pedido);
		}

		
		//3) Comenzamos los turnos
		int turno = 0;

		while (!simulador.estaTodoListo()) {
			System.out.println("----- Turno " + turno + " -----");
			simulador.simularTurno();

			// Dormir el hilo por 1 segundo (1000 ms)
			try {
				Thread.sleep(1000); // puedes ajustar el valor
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				System.out.println("Simulación interrumpida.");
				break;
			}

			turno++;
		}

		//4) Mostramos los pedidos cancelados
		simulador.mostrarPedidosCancelados();
		
		//5) Mostramos cofres PA que no tienen destino
		
		for (Cofre cofre : red.getCofres()) {
            if (cofre instanceof PuedeOfertar) {
                PuedeOfertar ofertante = (PuedeOfertar) cofre;

                for (Item item : listaItems) {
                    int ofrecido = ofertante.cuantoOfrece(item);
                    int enStock = cofre.consultarItem(item);

                    if (ofrecido > 0 && enStock >= ofrecido) {
                        System.out.printf("- El cofre %s sigue ofreciendo %d unidades de %s (stock: %d)%n",
                                cofre.getId(), ofrecido, item.getNombre(), enStock);
                    }
                }
            }
        }

		System.out.println("\nSimulacion finalizada en " + turno + " turnos.");
	}

	//----- EJEMPLO PARA LA CLASE ------
	public static void ejemploClase() {
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

		for(Red red : redes)
		{
			simulacionCompletaDeRed(red, listaItems);
			System.out.println(red);
			
		}		
	}
	
	
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

	
	
//Fin
}
