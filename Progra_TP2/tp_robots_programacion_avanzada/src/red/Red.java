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
		StringBuilder sb = new StringBuilder();
		sb.append("Red ").append(id).append(" {\n");

		sb.append("  Robopuertos:\n");
		for (Robopuerto rp : robopuertos) {
			sb.append("    ").append(rp).append("\n");
		}

		sb.append("  Cofres:\n");
		if (cofres.isEmpty()) {
			sb.append("    (ninguno)\n");
		} else {
			for (Cofre c : cofres) {
				sb.append("    ").append(c).append("\n");
			}
		}

		sb.append("  Robots:\n");
		if (robots.isEmpty()) {
			sb.append("    (ninguno)\n");
		} else {
			for (Robot r : robots) {
				sb.append("    ").append(r).append("\n");
			}
		}

		sb.append("}\n");
		return sb.toString();
	}

	public interface ComponenteRed {
		Nodo getNodo();

		Coordenada getCoordenada();
	}

//fin
}
