package cofres;

import java.util.HashMap;

import grafos.*;
import red.Coordenada;
import red.Red;
import red.Robopuerto;
import utiles.Item;

//Cofre clase abstracta que podra ser un cofre de un tipo y comportamiento especifico
public abstract class Cofre implements Red.ComponenteRed {
	private final String id;
	private final Coordenada coordenada;
	private final Nodo nodo;
	protected HashMap<Item, Integer> items; // listado de items que tendra el cofre internamente

	private Robopuerto rp_mas_cercano;
	private double distancia_rp;

	public Cofre(int x, int y, String id) {
		this.id = id;
		this.coordenada = new Coordenada(x, y);
		this.nodo = new Nodo((x+1)*1000 + (x+1) * y, id); // Para tener id único
		this.items = new HashMap<>(); // Inicialmente cofre vacio
	}

	public boolean tieneItem(Item item) {
		return items.containsKey(item) && items.get(item) > 0;
	}

	public Nodo getNodo() {
		return nodo;
	}

	public Coordenada getCoordenada() {
		return coordenada;
	}

	public Robopuerto getRp_mas_cercano() {
		return rp_mas_cercano;
	}

	public void setRp_mas_cercano(Robopuerto rp_mas_cercano, double distancia) {
		this.rp_mas_cercano = rp_mas_cercano;
		setDistancia_rp(distancia);
	}

	public double getDistanciaRP_minima() {
		return distancia_rp;
	}

	private void setDistancia_rp(double distancia_rp) {
		this.distancia_rp = distancia_rp;
	}

	public String getId() {
		return id;
	}

	@Override
	public String toString() {
		return String.format("Cofre[id=%s, coord=(x=%d, y=%d), nodo=%s, tipo=%s]", id, coordenada.getX(),
				coordenada.getY(), nodo.getAlias(), this.getClass().getSimpleName());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Cofre cofre = (Cofre) o;
		return id.equals(cofre.id);
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	public void guardarItem(Item item, int cantidad) {
		items.put(item, items.getOrDefault(item, 0) + cantidad);
	}

	public int consultarItem(Item item) {
		return items.getOrDefault(item, 0);
	}

	public void descontarItem(Item item, int cantidad) {
		int cant = items.getOrDefault(item, 0);

		if (cant >= cantidad)
			items.put(item, cant - cantidad);

	}

}