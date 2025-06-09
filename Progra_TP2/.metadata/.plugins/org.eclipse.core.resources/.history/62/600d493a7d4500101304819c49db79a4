package red;

import grafos.*;

public class Robot {
	private String id;
	private final int carga_max = 20;
	private int carga_actual;
	private Nodo nodo_actual;
	private double bateria;

	public Robot(String id, Nodo nodo_inicial) {
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

	public int getCarga_max() {
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

}