package red;

import utiles.*;
import grafos.*;

public class Robot {
	private String id;
	private final double carga_max = 20.0;
	private int carga_actual;
	private Nodo nodo_actual;
	private double bateria;

	private Viaje viaje;
	
	
	public Robot(String id, Nodo nodo_inicial) {
		this.id = id;
		this.nodo_actual = nodo_inicial;
		this.bateria = 100;
		this.carga_actual = 0;
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

	@Override
	public String toString() {
		return "Robot [id=" + id + ", carga_actual=" + carga_actual + "]";
	}

	
	
	//METODOS DE VIAJE ---------------------------------------------------

	public Pedido getPedidoActual() {
		return viaje != null ? viaje.getPedido() : null;
	}
	

<<<<<<< Updated upstream
}
=======
	public boolean estaLibre() {
		return viaje == null;
	}

	public void asignarViaje(Nodo destino, int pasos, double bateriaFinal, Pedido pedido, boolean ida) {
		this.viaje = new Viaje(destino, pasos, bateriaFinal, pedido, ida);
	}

	public void avanzarUnTurno(SimuladorRed sim) {
		if (viaje != null) {
			viaje.avanzar();
		}
	}

	public boolean estaEnDestino()
	{
		if(viaje != null && viaje.getPasosRestantes()==0)
			return true;
		return false;
	}

	public void finalizarViaje() {
		if (viaje != null) {
			this.nodo_actual = viaje.getDestino();
			this.bateria = viaje.getBateriaFinal();
			
			//INTERACTUAR CON EL COFRE
			
			this.viaje = null;
		}
	}
	
	
}
>>>>>>> Stashed changes
