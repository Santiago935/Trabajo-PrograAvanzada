package red;

import utiles.*;
import grafos.*;

public class Robot {
	private String id;
	private final double carga_max = 20.0; // Es bateria
	public static final int cargaMaximaDeMochila = 40;
	private int carga_actual;
	private Nodo nodo_actual;
	private double bateria;
	private String idRPInicial;
	private Viaje viaje;

	public Robot(String id, Nodo nodo_inicial) {
		this.id = id;
		this.nodo_actual = nodo_inicial;
		this.bateria = 100;
		this.carga_actual = 0;
		this.idRPInicial = nodo_inicial.getAlias();
	}

	public void viajar(Nodo nodo_destino, double costo) {
		// Mi Arista está implementada con int para el peso
		// Para el algoritmo que vayamos a usar, debo cambiar eso y ver como fluctuan
		// los
		// resultados...
		try {
			if (this.bateria - costo > 0) {
				this.bateria -= costo;
				this.nodo_actual = nodo_destino;
			} else {
				throw new Exception("No hay suficiente batería para realizar la acción.");
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	public String getId() {
		return id;
	}

	public double getCarga_max() {
		return carga_max;
	}

	public int getCarga_actual() {
		return carga_actual;
	}

	public Nodo getNodo_actual() {
		return nodo_actual;
	}

	public double getBateria() {
		return bateria;
	}

	public String getIdRPInicial() {
		return idRPInicial;
	}

	@Override
	public String toString() {
		return String.format("Robot[id=%s, carga_actual=%d, robo_puerto_inicial=%s]", id, carga_actual,
				idRPInicial != null ? idRPInicial : "(no asignado)");
	}

	// METODOS DE VIAJE ---------------------------------------------------

	public Pedido getPedidoActual() {
		return viaje != null ? viaje.getPedido() : null;
	}

	public boolean estaLibre() {
		return viaje == null;
	}

	public void asignarViaje(Nodo destino, int pasos, double bateriaFinal, Pedido pedido, boolean ida) {
		this.viaje = new Viaje(destino, pasos, bateriaFinal, pedido, ida);
	}

	public void avanzarUnTurno(SimuladorRed sim) {
		if (viaje != null) {
			// AGREGO LOG
			System.out.printf("Robot %s avanzando - pasos restantes: %d\n", id, viaje.getPasosRestantes());
			viaje.avanzar();
		}
	}

	public boolean estaEnDestino() {
		if (viaje != null && viaje.getPasosRestantes() == 0) {
			// AGREGO LOG
			System.out.printf("Robot %s - llego? pasos restantes = %d\n", id, viaje.getPasosRestantes());
			return true;
		}
		return false;
	}

	public void finalizarViaje() {
		if (viaje != null) {
			this.nodo_actual = viaje.getDestino();
			this.bateria = viaje.getBateriaFinal();

			// INTERACTUAR CON EL COFRE

			this.viaje = null;
		}
	}

	public void guardarItemEnMochila(int cantidad) {
		if ( (this.cargaMaximaDeMochila - this.carga_actual) < cantidad) {
			System.out.println("Soy robot: " + this.id);
			System.out.println("No podes cargar esa cantidad:" + cantidad);
		} else {
			this.carga_actual += cantidad;
		}
	}

	public void sacarItemDeMochila(int cantidad) {
		if(this.carga_actual - cantidad < 0)
		{
			System.out.println("Soy robot: " + this.id);
			System.out.println("La carga actual no puede ser negativa.");
		}
		else
			this.carga_actual -= cantidad; 
	}

	public int getCargaMaximaDeMochila() {
		return cargaMaximaDeMochila;
	}

	

	
	

}
