package red;

import cofres.Cofre;
import grafos.Grafo;
import java.util.*;

public class ArmadoRed {
	// -------------------------------------------------------------------------------------------
	// Metodos para la configuración inicial de la red
	// -------------------------------------------------------------------------------------------

	public static ArrayList<Red> armado_redes(ArrayList<Robopuerto> robopuertos, ArrayList<Cofre> cofres,
			ArrayList<Robot> robots) {
		ArrayList<Red> lista_redes = new ArrayList<>();
		int i = 1;

		for (Robopuerto rb : robopuertos) {
			boolean flag_solapado = false;
			for (Red red_actual : lista_redes) {
				for (Robopuerto rb_red : red_actual.getRobopuertos()) {
					if (radios_solapados(rb, rb_red) == true) {
						red_actual.add_robopuerto(rb);
						flag_solapado = true;
						break;
					}
				}
				if (flag_solapado) // Si le encontre red, no sigo buscando en otras redes
					break;
			}
			if (!flag_solapado) // Si no le encontré red o es primero->nueva red
			{
				Red nueva_red = new Red("Red: " + (i++));
				nueva_red.add_robopuerto(rb);
				lista_redes.add(nueva_red);
			}
		}

		armado_cofres(lista_redes, cofres);

		armado_robots(lista_redes, robots);

		generarGrafos(lista_redes);
		
		return lista_redes;
	}

	public static boolean radios_solapados(Robopuerto rb1, Robopuerto rb2) {
		Coordenada coord1 = rb1.getCoordenada();
		Coordenada coord2 = rb2.getCoordenada();

		double radio1 = rb1.getRadio();
		double radio2 = rb2.getRadio();

		return Coordenada.distancia_eucladiana(coord1, coord2) < radio1 + radio2;
	}

	public static void armado_cofres(ArrayList<Red> redes, ArrayList<Cofre> cofres) {
		Set<Cofre> visitados = new HashSet<>();

		for (Red red : redes) {
			for (Robopuerto rb : red.getRobopuertos()) {
				for (Cofre cofre : cofres) {
					// Si el cofre todavía no forma parte de red y
					// La distancia entre cofre y rb es menor al radio del rb
					if (!visitados.contains(cofre) && Coordenada.distancia_eucladiana(cofre.getCoordenada(),
							rb.getCoordenada()) < rb.getRadio()) {
						red.add_cofre(cofre);
						visitados.add(cofre);
					}
				}
			}
		}
	}

	public static void armado_robots(ArrayList<Red> redes, ArrayList<Robot> robots) {

		for (Robot robot : robots) {
			String idRP = robot.getIdRPInicial();
			boolean encontrado = false;

			for (Red red : redes) {
				for (Robopuerto rp : red.getRobopuertos()) {
					if (rp.getId().equals(idRP)) {
						red.add_robot(robot); // agrego robot a la red
						encontrado = true; // si ya encontre la red a la que pertence no sigo buscando
						break;
					}
				}
				if (encontrado)
					break;
			}
		}

	}

	public static void generarGrafos(ArrayList<Red> redes) {

		for (Red red : redes) {
			Grafo grafoDeLaRed = red.getGrafo_red();

			// Unificar todos los "componentes" (nodos) de la red en una sola lista.
			// También creamos un mapa para acceder fácilmente a las coordenadas de cada
			// nodo.
			List<Red.ComponenteRed> componentes = new ArrayList<>();
			componentes.addAll(red.getRobopuertos());
			componentes.addAll(red.getCofres());

			// Añadimos todos los nodos al grafo.
			for (Red.ComponenteRed comp : componentes) {
				grafoDeLaRed.addNodo(comp.getNodo());
			}

			// Creamos las aristas. Conectamos cada nodo con todos los demás
			for (int i = 0; i < componentes.size(); i++) {
				for (int j = i + 1; j < componentes.size(); j++) {
					Red.ComponenteRed compA = componentes.get(i);
					Red.ComponenteRed compB = componentes.get(j);

					// Calculamos la distancia euclidiana para usarla como peso de la arista.
					double distancia = Coordenada.distancia_eucladiana(compA.getCoordenada(), compB.getCoordenada());

					// Añadimos la arista con su peso al grafo.
					grafoDeLaRed.addArista(compA.getNodo(), compB.getNodo(), distancia);
				}
			}
		}
	}
}
