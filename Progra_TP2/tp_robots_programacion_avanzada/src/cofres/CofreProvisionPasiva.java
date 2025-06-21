package cofres;

import java.util.HashMap;
import utiles.Item;

public class CofreProvisionPasiva extends Cofre implements PuedeOfertar {

	private HashMap<Item, Integer> almacenamientoItems;

	public CofreProvisionPasiva(int x, int y, String id) {
		super(x, y, id);
		almacenamientoItems = new HashMap<>();
	}

	// Implementamos interfaz PuedeOfertar
	@Override
	public int cuantoOfrece(Item item) {
		return almacenamientoItems.getOrDefault(item, 0);
	}

	@Override
	public boolean reservarItem(Item item, int cantidad) {
		int stock = almacenamientoItems.getOrDefault(item, 0);
		if (cantidad > stock)
			return false;
		almacenamientoItems.put(item, stock - cantidad);
		return true;
	}

	// Método para cargar stock pasivo
	public void almacenar(Item item, int cantidad) {
		almacenamientoItems.put(item, almacenamientoItems.getOrDefault(item, 0) + cantidad);
	}
}
