package utiles;

import grafos.*;
import grafos.AlgoritmosGrafos.Dijkstra_resultado;
import red.*;
import cola_de_prioridad.TDA.Cola_prioridad_heap;
import java.util.*;
import cofres.*;

public class SimuladorRed {
	private Red red; // Red específica a simular
	private Cola_prioridad_heap<Pedido> pedidos = new Cola_prioridad_heap<>();
	private ArrayList<Robot> disponibles = new ArrayList<>();
	private ArrayList<Robot> buscandoItem = new ArrayList<>();
	private ArrayList<Robot> llevandoItem = new ArrayList<>();
	private Set<Integer> robopuertos = new HashSet<Integer>();
	private ArrayList<Pedido> sinAtender = new ArrayList<>();

	public SimuladorRed(Red red) {
		this.red = red;
		this.disponibles.addAll(red.getRobots());
		this.robopuertos = red.getIdsNodosRobopuertos();
	}

	public void agregarPedido(Pedido p) {
		pedidos.encolar(p);
	}

	public boolean estaTodoListo() {
		return pedidos.estaVacia() && buscandoItem.isEmpty() && llevandoItem.isEmpty();
	}

	public void simularTurno() {
		// Asignar pedidos
		while (!pedidos.estaVacia() && !disponibles.isEmpty()) {
			Pedido pedido = pedidos.desencolar();
			RobotElegido eleccion = obtenerRobotMasCercano(disponibles, pedido.getcOrigen(), red.getGrafo_red());

			// Si no se pudo atender, se vuelve a encolar
			if (eleccion == null || eleccion.robot == null) {
				sinAtender.add(pedido);
			}
			else //Si encontré robot:
			{								
				// Crear camino y viaje
				eleccion.robot.asignarViaje(pedido.getcOrigen().getNodo(), (int) Math.ceil(eleccion.getDistanciaMinima()),
						eleccion.getBateriaLlegada(), pedido, true);
				
				// RECORDAR QUE SI NO SE TOMO TODO EL CONTENIDO, HAY QUE CREAR OTRO PEDIDO
				
				disponibles.remove(eleccion.robot);
				buscandoItem.add(eleccion.robot);
			}
		}

		// Avanzar robots que buscan item
		List<Robot> listosParaLlevar = new ArrayList<>();
		for (Robot r : buscandoItem) {
			if (r.estaEnDestino()) {
				Pedido pedidoActual = r.getPedidoActual();
				
				// Sacarle items al cofre y guardarlo en el robot
				Cofre cofreOrigen = pedidoActual.getcOrigen();
				cofreOrigen.descontarItem(pedidoActual.getItem(), pedidoActual.getCantidad());
				r.guardarItemEnMochila(pedidoActual.getCantidad());
				
				r.finalizarViaje();

				Cofre cofreDestino = pedidoActual.getcDestino();
				RobotElegido infoViaje = calcularRuta(r, cofreDestino, red.getGrafo_red());


				// Comenzar a llevar el item, asignando ese viaje
				r.asignarViaje(cofreDestino.getNodo(), (int) Math.ceil(infoViaje.getDistanciaMinima()),
						infoViaje.getBateriaLlegada(), pedidoActual, false);
				System.out.println("El robot " + infoViaje.robot.getId() + " agarro el item "
						+ pedidoActual.getItem().getId() + " del cofre " + cofreOrigen.getId());

				listosParaLlevar.add(r); // Buffer de los que van a cambiar de estado
			} else {
				// Si no está en destino, avanzar un turno
				r.avanzarUnTurno(this);
			}
		}

		// Los paso a llevando
		buscandoItem.removeAll(listosParaLlevar);
		llevandoItem.addAll(listosParaLlevar);

		// Avanzar robots que llevan item
		List<Robot> listosParaDescargar = new ArrayList<>();

		for (Robot r : llevandoItem) {
			if (r.estaEnDestino()) {
				
				//Saco el item de la mochila y lo guardo en cofre de destino
				Pedido pedidoActual = r.getPedidoActual();
				r.sacarItemDeMochila(pedidoActual.getCantidad());
				Cofre cofreDestino = pedidoActual.getcDestino();
				cofreDestino.guardarItem(pedidoActual.getItem(), pedidoActual.getCantidad());
				
				System.out.println("El robot " + r.getId() + " deja el item "
						+ pedidoActual.getItem().getId() + " en el cofre " + cofreDestino.getId());

				
				listosParaDescargar.add(r);
				r.finalizarViaje();
			} else {
				r.avanzarUnTurno(this);
			}
		}

		//Disponibilizo los robots
		llevandoItem.removeAll(listosParaDescargar);
		disponibles.addAll(listosParaDescargar);

		// Volvemos a encolar los que no se pudieron atender
		for (Pedido pedido : sinAtender) {
			System.out.println("No se pudo atender al pedido: " + pedido.getcOrigen().getId() + "-"
					+ pedido.getcDestino().getId());
			pedidos.encolar(pedido);
		}

		System.out.println("---Turno finalizado---\n");
	}

	public void reencolarPedido(Pedido nuevo) {
		pedidos.encolar(nuevo);
	}

	public RobotElegido obtenerRobotMasCercano(ArrayList<Robot> robots, Cofre cofre, Grafo grafo) {
		Robot robotMinimo = null;
		Double distanciaMinima = Double.MAX_VALUE;
		Double bateriaLlegada = 0.0;

		Nodo destino = cofre.getNodo();

		for (Robot robotActual : robots) {
			Nodo origen = robotActual.getNodo_actual();
			Double bateria = robotActual.getBateria();
			Double cargaMax = robotActual.getCarga_max();

			// Hacemos un dijkstra del robot al cofre de llegada
			Dijkstra_resultado resul = AlgoritmosGrafos.dijkstraConBateria(grafo, origen, bateria, cargaMax,
					this.robopuertos);

			Map<Integer, Double> costosMinimos = resul.getCostosMinimos();
			Map<Integer, Double> bateriasMinimas = resul.getMejorBateriaConCostoMinimoDouble();

			Double distanciaActual = costosMinimos.get(destino.getId());
			Double bateriaActual = bateriasMinimas.get(destino.getId());

			// Si tengo nuevo minimo
			if (Double.compare(distanciaActual, distanciaMinima) < 0) {
				distanciaMinima = distanciaActual;
				robotMinimo = robotActual;
				bateriaLlegada = bateriaActual;
			}
		}
		
		return new RobotElegido(robotMinimo, distanciaMinima, bateriaLlegada);
	}

	private class RobotElegido {
		private Robot robot;
		private Double distanciaMinima;
		private Double bateriaLlegada;

		public RobotElegido(Robot robot, Double distanciaMinima, Double bateriaLlegada) {
			this.robot = robot;
			this.distanciaMinima = distanciaMinima;
			this.bateriaLlegada = bateriaLlegada;
		}

//		public Robot getRobot() {
//			return robot;
//		}

//		public void setRobot(Robot robot) {
//			this.robot = robot;
//		}

		public Double getDistanciaMinima() {
			return distanciaMinima;
		}

//		public void setDistanciaMinima(Double distanciaMinima) {
//			this.distanciaMinima = distanciaMinima;
//		}

		public Double getBateriaLlegada() {
			return bateriaLlegada;
		}

//		public void setBateriaLlegada(Double bateriaLlegada) {
//			this.bateriaLlegada = bateriaLlegada;
//		}
	}

	public RobotElegido calcularRuta(Robot robot, Cofre cofre, Grafo grafo) {
		Double distanciaMinima;
		Double bateriaLlegada;

		// Hacemos un dijkstra del robot al cofre de llegada
		Dijkstra_resultado resul = AlgoritmosGrafos.dijkstraConBateria(grafo, robot.getNodo_actual(),
				robot.getBateria(), robot.getCarga_max(), this.robopuertos);

		Map<Integer, Double> costosMinimos = resul.getCostosMinimos();
		Map<Integer, Double> bateriasMinimas = resul.getMejorBateriaConCostoMinimoDouble();

		distanciaMinima = costosMinimos.get(cofre.getNodo().getId());
		bateriaLlegada = bateriasMinimas.get(cofre.getNodo().getId());

		return new RobotElegido(robot, distanciaMinima, bateriaLlegada);
	}

}
