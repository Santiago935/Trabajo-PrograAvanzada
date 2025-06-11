package utiles;

import cofres.Cofre;
import red.Coordenada;
import red.Robot;

public class Pedido {
	private Cofre cSolicito;
	private Cofre cAtendio;
	private Item item;
	private int cantidad;
	private double distancia; 
	private Robot robotAsignado;

	public Pedido(Cofre cSolicito, Item item, int cantidad) {
		this.cSolicito = cSolicito;
		this.item = item;
		this.cantidad = cantidad;
	}
	
	public void AtenderPedido(Cofre cofreQueAtendera) {
		if(this.cAtendio != null) // si el pedido ya fue atendido
			return;
		
		this.cAtendio = cofreQueAtendera;
		
		//calculo de la distancia
		this.distancia = Coordenada.distancia_eucladiana(this.cSolicito.getCoordenada() ,  this.cAtendio.getCoordenada());
	}

	public Cofre getcSolicito() {
		return cSolicito;
	}

	public Cofre getcAtendio() {
		return cAtendio;
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
		return "Pedido [cSolicito=" + cSolicito + ", cAtendio=" + cAtendio + ", item=" + item + ", cantidad=" + cantidad
				+ ", distancia=" + distancia + ", robotAsignadoMasCercano=" + robotAsignado + "]";
	}
	
	
	//Asignacion de robots

	public void asignarRobot(Robot robot) {
	    this.robotAsignado = robot;
	}

	public Robot getRobotAsignado() {
	    return robotAsignado;
	}


}
