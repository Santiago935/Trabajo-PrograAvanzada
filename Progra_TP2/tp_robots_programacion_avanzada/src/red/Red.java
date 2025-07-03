package red;

import cofres.Cofre;
import grafos.Grafo;
import grafos.Nodo;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Red {
	private String id;
	private Grafo grafo_red = new Grafo(false);
	private List<Robopuerto> robopuertos = new ArrayList<>();
	private List<Cofre> cofres = new ArrayList<>();
	private List<Robot> robots = new ArrayList<>();

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

	public String getId() {
		return id;
	}

	public void add_robot(Robot robot) {

        if (robot == null) {
            throw new IllegalArgumentException("El robot no puede ser nulo");
        }
        if(this.robots.contains(robot)) {
            throw new IllegalArgumentException("El robot ya está en la red");
        }
        this.robots.add(robot);
    }

    public void add_robopuerto(Robopuerto robopuerto) {
        if (robopuerto == null) {
            throw new IllegalArgumentException("El robopuerto no puede ser nulo");
        }
        if(this.robopuertos.contains(robopuerto)) {
            throw new IllegalArgumentException("El robopuerto ya está en la red");
        }
        this.robopuertos.add(robopuerto);
    }

    public void add_cofre(Cofre cofre) {
        if (cofre == null) {
            throw new IllegalArgumentException("El cofre no puede ser nulo");
        }
        if(this.cofres.contains(cofre)) {
            throw new IllegalArgumentException("El cofre ya está en la red");
        }
        this.cofres.add(cofre);
    }

	public List<ComponenteRed> getComponentes() {
		List<ComponenteRed> componentes = new ArrayList<>();
		componentes.addAll(robopuertos);
		componentes.addAll(cofres);
		return componentes;
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

	public void setGrafo_red(Grafo grafo_red) {
		this.grafo_red = grafo_red;
	}
	
}
