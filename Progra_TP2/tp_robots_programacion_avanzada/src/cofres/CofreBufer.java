package cofres;

import java.util.HashMap;

import utiles.Item;

/*
 * El cofre se comporta como un CofreSolicitud, pero tambien puede ofertar items
 */

public class CofreBufer extends Cofre implements PuedeOfertar, PuedeSolicitar {
	private HashMap<Item, Integer> itemsSolicitados; // separados del contenido real
	private HashMap<Item, Integer> itemsOfrecidos; // marcados como items en oferta

	public CofreBufer(int x, int y, String id) {
		super(x, y, id);
		itemsSolicitados = new HashMap<Item, Integer>();
		itemsOfrecidos = new HashMap<Item, Integer>();
	}

	public void solicitarItem(Item item, int cantidad) {
		itemsSolicitados.put(item, itemsSolicitados.getOrDefault(item, 0) + cantidad);
	}

	public HashMap<Item, Integer> getPedidos() {
		return itemsSolicitados;
	}

	// Interfaz de solicitar
	@Override
	public int cuantoSolicita(Item item) {
		return itemsSolicitados.getOrDefault(item, 0);
	}

	@Override
	public void aceptarPedido(Item item, int cantidad) {
		int solicitado = itemsSolicitados.getOrDefault(item, 0);
		if (cantidad >= solicitado) {
			itemsSolicitados.remove(item);
		} else {
			itemsSolicitados.put(item, solicitado - cantidad);
		}
	}

	// Interfaz de ofertar
	@Override
	public int cuantoOfrece(Item item) {
		return itemsOfrecidos.getOrDefault(item, 0);
	}

	@Override
	public boolean reservarItem(Item item, int cantidad) {
		int stock = itemsOfrecidos.getOrDefault(item, 0);
		if (cantidad > stock)
			return false;
		itemsOfrecidos.put(item, stock - cantidad);
		return true;
	}

	public boolean agregarOferta(Item item, int cantidad) {
		int stock = items.getOrDefault(item, 0);
		int cantidadNecesaria = cantidad + itemsOfrecidos.getOrDefault(item, 0);

		if (stock < cantidadNecesaria) {
			return false;
		}

		itemsOfrecidos.put(item, cantidadNecesaria);
		return true;
	}

	public int cantidadOfertada(Item item) {
		return itemsOfrecidos.getOrDefault(item, 0);
	}


}
