package cofres;

import utiles.Item;

public class CofreAlmacenamiento extends Cofre {

	public CofreAlmacenamiento(int x, int y, String id) {
		super(x, y, id);
	}

	@Override
	public int consultarItem(Item item) {
		return items.getOrDefault(item, 0);
	}

	@Override
	public void guardarItem(Item item, int cantidad) {
		items.put(item, items.getOrDefault(item, 0) + cantidad);
	}

}
