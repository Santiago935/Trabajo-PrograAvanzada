package red;

//import java.util.*;
import grafos.*;

public class Robopuerto implements Red.ComponenteRed{
	private String id;
	private final Coordenada coordenada;
	private final Nodo nodo;
	private final double radio;

	public Robopuerto(String id, int x, int y, double radio) {
		this.id = id;
		this.coordenada = new Coordenada(x, y);
		this.nodo = new Nodo((x+1)*1000 + (x+1) * y, id);// Para tener id único
		this.radio = radio;
	}

	public Nodo getNodo() {
		return nodo;
	}

	public Coordenada getCoordenada() {
		return coordenada;
	}

	public double getRadio() {
		return radio;
	}

	public String getId() {
		return id;
	}

	@Override
	public String toString() {
	    return String.format(
	        "Robopuerto[id=%s, coord=(x=%d, y=%d), nodo=%s, radio=%.1f]",
	        id,
	        coordenada.getX(),
	        coordenada.getY(),
	        nodo.getAlias(), // o nodo.toString() si preferís, pero legible
	        radio
	    );
	}

}
