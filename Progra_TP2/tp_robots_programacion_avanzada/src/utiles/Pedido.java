package utiles;

import java.util.*;

import cofres.*;
import red.*;

public class Pedido implements Comparable<Pedido> {
	private Cofre cOrigen; // cambiar nombre
	private Cofre cDestino;
	private Item item;
	private int cantidad;
	private double distancia;

	public Pedido(Cofre cSolicito, Cofre cAtendio, Item item, int cantidad) {
		this.cOrigen = cSolicito;
		this.cDestino = cAtendio;
		this.item = item;
		this.cantidad = cantidad;
		// Agregar distancia
	}

	public void AtenderPedido(Cofre cofreQueAtendera) {
		if (this.cDestino != null) // si el pedido ya fue atendido
			return;

		this.cDestino = cofreQueAtendera;

		// calculo de la distancia
		this.distancia = Coordenada.distancia_eucladiana(this.cOrigen.getCoordenada(), this.cDestino.getCoordenada());
	}

	// LO TENGO QUE BORRAR
	public Cofre getcSolicito() {
		return cOrigen;
	}

	public Cofre getcOrigen() {
		return cOrigen;
	}

	public void setcOrigen(Cofre cOrigen) {
		this.cOrigen = cOrigen;
	}

	public Cofre getcDestino() {
		return cDestino;
	}

	public void setcDestino(Cofre cDestino) {
		this.cDestino = cDestino;
	}

	// LO TENGO QUE BORRAR
	public Cofre getcAtendio() {
		return cDestino;
	}

	public Item getItem() {
		return item;
	}

	public int getCantidad() {
		return cantidad;
	}

	public double getDistancia() {
		return distancia;
	}

	@Override
	public String toString() {
		return String.format("Pedido[origen=%s, destino=%s, item=%s, cantidad=%d, distancia=%.2f]", cOrigen.getId(),
				cDestino.getId(), item.getNombre(), cantidad, distancia);
	}

	@Override
	public int compareTo(Pedido otroPedido) {
		return Double.compare(this.distancia, otroPedido.distancia);
	}

	/* ---------- ARMADO DE PEDIDOS ---------- */
	public static ArrayList<Pedido> armado_pedidos3(ArrayList<Red> redes) {
		ArrayList<Pedido> pedidos = new ArrayList<>();

		for (Red red : redes) {
			ArrayList<Cofre> cofres = (ArrayList<Cofre>) red.getCofres();

			List<PuedeSolicitar> solicitantes = new ArrayList<>();
			List<PuedeOfertar> ofertantes = new ArrayList<>();

			for (Cofre c : cofres) {
				if (c instanceof PuedeSolicitar) {
					solicitantes.add((PuedeSolicitar) c);
				}
				if (c instanceof PuedeOfertar) {
					ofertantes.add((PuedeOfertar) c);
				}
			}

			// ordenamos ofertantes por prioridad: activa -> bufer -> pasiva
			ofertantes.sort((a, b) -> {
				if (a instanceof CofreProvisionActiva && !(b instanceof CofreProvisionActiva))
					return -1;
				if (b instanceof CofreProvisionActiva && !(a instanceof CofreProvisionActiva))
					return 1;
				if (a instanceof CofreBufer && !(b instanceof CofreBufer))
					return -1;
				if (b instanceof CofreBufer && !(a instanceof CofreBufer))
					return 1;
				return 0;
			});

			for (PuedeSolicitar solicitante : solicitantes) {
				Set<Item> itemsSolicitados = obtenerItemsSolicitados(solicitante);

				for (Item item : itemsSolicitados) {
					int cantidadNecesaria = solicitante.cuantoSolicita(item);
					if (cantidadNecesaria <= 0)
						continue;

					for (PuedeOfertar ofertante : ofertantes) {
						int stockDisponible = ofertante.cuantoOfrece(item);
						if (stockDisponible <= 0)
							continue;

						int cantidadAtendida = Math.min(cantidadNecesaria, stockDisponible);

						if (ofertante.reservarItem(item, cantidadAtendida)) {
							solicitante.aceptarPedido(item, cantidadAtendida);

							Cofre origen = (Cofre) solicitante;
							Cofre destino = (Cofre) ofertante;

							Pedido nuevoPedido = new Pedido(origen, destino, item, cantidadAtendida);
							pedidos.add(nuevoPedido);

							cantidadNecesaria -= cantidadAtendida;
							if (cantidadNecesaria <= 0)
								break;
						}
					}
				}
			}
		}

		return pedidos;
	}

	private static Set<Item> obtenerItemsSolicitados(PuedeSolicitar solicitante) {
		Set<Item> items = new HashSet<>();

		if (solicitante instanceof CofreSolicitud) {
			items.addAll(((CofreSolicitud) solicitante).getPedidos().keySet());
		} else if (solicitante instanceof CofreBufer) {
			items.addAll(((CofreBufer) solicitante).getPedidos().keySet());
		}

		return items;
	}

//	public static ArrayList<Pedido> armado_pedidos2(ArrayList<Red> redes) {
//		ArrayList<Pedido> pedidos = new ArrayList<>();
//
//		// 1) Recorrer cada red
//		for (Red red : redes) {
//			ArrayList<Cofre> cofres = (ArrayList<Cofre>) red.getCofres();
//
//			// 1.a) Filtrar cofres solicitantes
//			List<CofreSolicitud> solicitantes = new ArrayList<>();
//			List<Cofre> ofertantesOrdenados = new ArrayList<>();
//
//			for (Cofre c : cofres) {
//				if (c instanceof CofreSolicitud) {
//					solicitantes.add((CofreSolicitud) c);
//				} else if (c instanceof CofreProvisionActiva/*
//															 * || c instanceof CofreBuffer || c instanceof
//															 * CofreProvisionPasiva
//															 */) {
//					ofertantesOrdenados.add(c); // se agregan todos y se prioriza luego
//				}
//			}
//	
//
//			/*
//			 * // Ordenamos ofertantes según prioridad: activa > buffer > pasiva
//			 * ofertantesOrdenados.sort((a, b) -> { if (a instanceof CofreProvisionActiva)
//			 * return -1; if (b instanceof CofreProvisionActiva) return 1; if (a instanceof
//			 * CofreBuffer) return -1; if (b instanceof CofreBuffer) return 1; return 0; });
//			 */
//
//			// 2) Por cada cofre solicitante
//			for (CofreSolicitud solicitante : solicitantes) {
//				Map<Item, Integer> pedidosPendientes = new HashMap<>(solicitante.getPedidos());
//
//				// 3) Para cada ítem solicitado
//				for (Map.Entry<Item, Integer> entry : pedidosPendientes.entrySet()) {
//					Item item = entry.getKey();
//					int cantidadNecesaria = entry.getValue();
//
//					if (cantidadNecesaria <= 0)
//						continue; // Por si pide 0
//
//					// 4) Buscar cofres ofertantes con stock
//					for (Cofre ofertante : ofertantesOrdenados) {
//						int stockDisponible = ofertante.consultarStock(item);
//
//						if (stockDisponible <= 0)
//							continue;
//
//						// Tomo el minimo entre el que necesito y el que tengo
//						int cantidadAtendida = Math.min(cantidadNecesaria, stockDisponible);
//
//						// Reservo en el ofertante
//						boolean pudoReservar = ofertante.aceptarSolicitud(item, cantidadAtendida);
//						if (pudoReservar) {
//							solicitante.aceptarSolicitud(item, cantidadAtendida); // Acepto el pedido
//							Pedido nuevoPedido = new Pedido(solicitante, ofertante, item, cantidadAtendida); // Armo el
//																												// pedido
//							pedidos.add(nuevoPedido); // Lo agrego a la lista
//
//							cantidadNecesaria -= cantidadAtendida; // Resto lo otorgado
//
//							if (cantidadNecesaria <= 0)
//								break; // ítem completamente atendido
//							// Si no es 0, sigo buscando ofertantes
//						}
//					}
//				}
//			}
//		}
//
//		return pedidos;
//	}

//FIN
}