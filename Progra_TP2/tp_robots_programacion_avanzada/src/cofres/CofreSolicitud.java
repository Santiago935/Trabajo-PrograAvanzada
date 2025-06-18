package cofres;

import java.util.HashMap;
import utiles.Item;

public class CofreSolicitud extends Cofre {

	private HashMap<Item, Integer> itemsSolicitados; // separados del contenido real

	public CofreSolicitud(int x, int y, String id) {
		super(x, y, id);
		itemsSolicitados = new HashMap<Item, Integer>();
	}

	public void solicitarItem(Item item, int cantidad) {
		itemsSolicitados.put(item, itemsSolicitados.getOrDefault(item, 0) + cantidad);
	}

	@Override
	public boolean aceptarSolicitud(Item item, int cantidad) {
		int solicitado = itemsSolicitados.getOrDefault(item, 0);
		if (cantidad >= solicitado) {
			itemsSolicitados.remove(item);
		} else {
			itemsSolicitados.put(item, solicitado - cantidad);
		}
		return true;
	}

	public int consultarCantidadSolicitada(Item item) {
		return itemsSolicitados.getOrDefault(item, 0);
	}

	public HashMap<Item, Integer> getPedidos() {
		return itemsSolicitados;
	}

	public boolean necesita(Item item) {
		return itemsSolicitados.containsKey(item);
	}

    //Pisamos el toString super y hacemos el especifico
	@Override
	public String toString() {
		return "Cofre [id=" + getId() + 
				", coordenada=" + getCoordenada() + 
				", nodo=" + getNodo() + 
				", tipo= CofreSolicitud" + 
				"]";
	}
	
	@Override
	public  int consultarStock(Item item) {
		return items.getOrDefault(item, 0);
	}
}