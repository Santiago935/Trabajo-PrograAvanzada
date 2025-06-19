package red;

import grafos.*;
import java.util.*;

import cofres.Cofre;

public class Red {
	private String id;
	private Grafo grafo_red = new Grafo(false);;
	private List<Robopuerto> robopuertos = new ArrayList<Robopuerto>();
	private List<Cofre> cofres = new ArrayList<Cofre>();
	private List<Robot> robots = new ArrayList<Robot>();

	public Red(String id) {
		this.id = id;
	}
	
	public Set<Integer> getIdsNodosRobopuertos() {
	    Set<Integer> ids = new HashSet<>();
	    for (Robopuerto rp : robopuertos) {
	        ids.add(rp.getNodo().getId());
	    }
	    return ids;
	}

	public Grafo getGrafo_red() {
		return grafo_red;
	}

	public List<Robopuerto> getRobopuertos() {
		return robopuertos;
	}

	public List<Cofre> getCofres() {
		return cofres;
	}

	public List<Robot> getRobots() {
		return robots;
	}

	public void add_robot(Robot robot) {
		this.robots.add(robot);
	}

	public void add_robopuerto(Robopuerto robopuerto) {
		this.robopuertos.add(robopuerto);
	}

	public void add_cofre(Cofre cofre) {
		this.cofres.add(cofre);
	}

	@Override
	public String toString() {
		return "Red [id=" + id + ", grafo_red=" + grafo_red + ", robopuertos=\n" + robopuertos + ", cofres=" + cofres
				+ ", robots=" + robots + "]";
	}
	
	public interface ComponenteRed {
	    Nodo getNodo();
	    Coordenada getCoordenada();
	}
	

	
//fin
}
