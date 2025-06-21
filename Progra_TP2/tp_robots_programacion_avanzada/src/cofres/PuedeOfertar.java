package cofres;

import utiles.Item;

public interface PuedeOfertar {
	int cuantoOfrece(Item item); // devuelve cuánto stock tiene para ofrecer del item

	boolean reservarItem(Item item, int cantidad); // intenta reservar item, devuelve si pudo
}
