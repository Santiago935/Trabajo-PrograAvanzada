package cofres;

import java.util.HashMap;
import utiles.Item;

public class CofreSolicitud extends Cofre implements PuedeSolicitar {

	private HashMap<Item, Integer> itemsSolicitados; // separados del contenido real

	public CofreSolicitud(int x, int y, String id) {
		super(x, y, id);
		itemsSolicitados = new HashMap<Item, Integer>();
	}

	public void solicitarItem(Item item, int cantidad) {
		itemsSolicitados.put(item, itemsSolicitados.getOrDefault(item, 0) + cantidad);
	}

	// Aplicamos interfaz
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

	public HashMap<Item, Integer> getPedidos() {
		return itemsSolicitados;
	}

	public boolean necesita(Item item) {
		return itemsSolicitados.containsKey(item);
	}

}