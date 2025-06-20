package cofres;

import utiles.Item;

public interface PuedeSolicitar {
	int cuantoSolicita(Item item); // devuelve la cantidad solicitada de un item

	void aceptarPedido(Item item, int cantidad); // notifica que se le entrego parte del pedido
}
