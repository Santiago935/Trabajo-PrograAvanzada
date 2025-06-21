package cofres;

import java.util.HashMap;

import utiles.Item;

public class CofreProvisionActiva extends Cofre implements PuedeOfertar {
	private HashMap<Item, Integer> itemsOfrecidos; // Diferentes de los que realmente tiene

	public CofreProvisionActiva(int x, int y, String id) {
		super(x, y, id);
		itemsOfrecidos = new HashMap<Item, Integer>();
	}

	public HashMap<Item, Integer> ofertaDeItems() {
		return this.items;
	}

	// Implementamos interfaz
	@Override
	public int cuantoOfrece(Item item) {
		return itemsOfrecidos.getOrDefault(item, 0);
	}

	@Override
	public boolean reservarItem(Item item, int cantidad) {
		int cantEnStock = itemsOfrecidos.getOrDefault(item, 0);
		if (cantidad > cantEnStock)
			return false;

		itemsOfrecidos.put(item, cantEnStock - cantidad); // Descontamos lo ofrecido
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

}