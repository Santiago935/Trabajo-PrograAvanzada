package grafos;

public class Arista {
	private final Nodo objetivo;
	private double peso;
	
	public Arista(Nodo objetivo, double peso)
	{
		this.objetivo=objetivo;
		this.peso=peso;
	}

	/**
	 * @return the peso
	 */
	public double getPeso() {
		return peso;
	}

	/**
	 * @param peso the peso to set
	 */
	public void setPeso(double peso) {
		this.peso = peso;
	}

	/**
	 * @return the objetivo
	 */
	public Nodo getObjetivo() {
		return objetivo;
	}
	
	
}

