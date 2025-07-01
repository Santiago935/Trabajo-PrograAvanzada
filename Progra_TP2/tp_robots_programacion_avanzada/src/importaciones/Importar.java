
package importaciones;

import cofres.*;
import grafos.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import red.*;
import utiles.*;

public class Importar {

	// ------------------------------------<ROBOPUERTOS>------------------------------------
	public ArrayList<Robopuerto> leerArchivoRobopuertos() {
		InputStream nombreArchivo = getClass().getResourceAsStream("/importaciones/archivoRobopuertos.csv"); // Importo
		ArrayList<Robopuerto> listaRoboPuertos = new ArrayList<>();
		BufferedReader lector = null;
		try {

			lector = new BufferedReader(new InputStreamReader(nombreArchivo, StandardCharsets.UTF_8));
//			lector = new BufferedReader(new InputStreamReader(nombreArchivo));
			String linea;
			while ((linea = lector.readLine()) != null) {

				// Cada linea que leo, guardo en el arrayList el contenido

				String[] valores = linea.split(";");

				if (valores.length < 3) {
					System.out.println("Línea inválida: " + linea);
					continue; // Saltar la línea si es invalida
				}
				int x = Integer.parseInt(valores[1]);
				int y = Integer.parseInt(valores[2]);
				String id = valores[0];
				double radio = Double.parseDouble(valores[3]);
				Robopuerto roboPuerto = new Robopuerto(id, x, y, radio);
				listaRoboPuertos.add(roboPuerto);
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
		} finally {
			if (lector != null) {
				try {
					lector.close();
				} catch (IOException e) {
					System.err.println(e.getMessage());
				}
			}
		}
		return listaRoboPuertos;
	}

	// ------------------------------------<ITEMS>------------------------------------
	public ArrayList<Item> leerArchivoItems() {
		InputStream nombreArchivo = getClass().getResourceAsStream("/importaciones/archivoItems.csv");
		// Ver como mejorar la ruta del archivo
		ArrayList<Item> listaItems = new ArrayList<>();
		BufferedReader lector = null;
		try {
			// lector = new BufferedReader(new InputStreamReader(nombreArchivo));
			lector = new BufferedReader(new InputStreamReader(nombreArchivo, StandardCharsets.UTF_8));
			String linea;
			while ((linea = lector.readLine()) != null) {
				String[] valores = linea.split(";");
				if (valores.length < 3) {
					System.out.println("Línea inválida: " + linea);
					continue; // Saltar la línea si es invalida
				}

				int id = Integer.parseInt(valores[0]);
				String descripcion = valores[1];
				String nombre = valores[2];
				Item item = new Item(id, nombre, descripcion);
				listaItems.add(item);
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
		} finally {
			if (lector != null) {
				try {
					lector.close();
				} catch (IOException e) {
					System.err.println(e.getMessage());
				}
			}
		}
		return listaItems;
	}

	// ------------------------------------<ROBOT>------------------------------------
	public ArrayList<Robot> leerArchivoRobots(ArrayList<Robopuerto> listaRobopuertos) {
		InputStream nombreArchivo = getClass().getResourceAsStream("/importaciones/archivoRobots.csv");

		// En este caso creo un HashMap de Robopuertos para poder buscar facilmente el
		// id. De esta manera solo hay un for, en vez de varios whiles...
		HashMap<String, Robopuerto> mapaRobopuertos = new HashMap<>();
		ArrayList<Robot> listaRobots = new ArrayList<>();
		for (Robopuerto rp : listaRobopuertos) {
			mapaRobopuertos.put(rp.getId(), rp);
		}

		BufferedReader lector = null;
		try {
			lector = new BufferedReader(new InputStreamReader(nombreArchivo, StandardCharsets.UTF_8));
//			lector = new BufferedReader(new InputStreamReader(nombreArchivo));
			String linea;
			while ((linea = lector.readLine()) != null) {
				String[] valores = linea.split(";");
				if (valores.length < 2) {
					System.out.println("Línea inválida: " + linea);
					continue; // Saltar la línea si es invalida
				}

				String id_robot = valores[0];
				String id_robopuerto = valores[1];

				// Buscar el robopuerto correspondiente (si existe)
				Robopuerto roboPuerto = mapaRobopuertos.get(id_robopuerto);
				if (roboPuerto != null) {
					Nodo nodo_spawn = roboPuerto.getNodo();
					Robot robot = new Robot(id_robot, nodo_spawn);
					listaRobots.add(robot);
				} else {
					// Reporto que no existe.
					System.out.println("Robopuerto " + id_robopuerto + "no encontrado");
				}

			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
		} finally {
			if (lector != null) {
				try {
					lector.close();
				} catch (IOException e) {
					System.err.println(e.getMessage());
				}
			}
		}
		return listaRobots;
	}

	// ------------------------------------<COFRES>------------------------------------
	public ArrayList<Cofre> leerArchivoCofres() {
		InputStream nombreArchivo = getClass().getResourceAsStream("/importaciones/archivoCofres.csv");
		ArrayList<Cofre> listaCofres = new ArrayList<>();
		BufferedReader lector = null;
		try {
			lector = new BufferedReader(new InputStreamReader(nombreArchivo, StandardCharsets.UTF_8));
//			lector = new BufferedReader(new InputStreamReader(nombreArchivo));
			String linea;
			while ((linea = lector.readLine()) != null) {
				String[] valores = linea.split(";"); // Divide la línea por punto y coma
				if (valores.length < 4) {
					System.out.println("Línea inválida: " + linea);
					continue; // Saltar la línea
				}

				String id_cofre = valores[0];
				int cordX = Integer.parseInt(valores[1]);
				int cordY = Integer.parseInt(valores[2]);
				String tipoCofre = valores[3]; // S = CofreSolicitud, B = CofreBuffer, PA = CofreProvisionActivo, A =
												// CofreAlmacenamiento
				// PP = CofrePasivoPasivo
				switch (tipoCofre) {
				case "S" -> {
					CofreSolicitud cofreAuxS = new CofreSolicitud(cordX, cordY, id_cofre);
					listaCofres.add(cofreAuxS);
				}
				case "B" -> {
					CofreBufer cofreAuxB = new CofreBufer(cordX, cordY, id_cofre);
					listaCofres.add(cofreAuxB);
				}
				case "PA" -> {
					CofreProvisionActiva cofreAuxPA = new CofreProvisionActiva(cordX, cordY, id_cofre);
					listaCofres.add(cofreAuxPA);
				}
				case "A" -> {
					CofreAlmacenamiento cofreAuxA = new CofreAlmacenamiento(cordX, cordY, id_cofre);
					listaCofres.add(cofreAuxA);
				}
				case "PP" -> {
					CofreProvisionPasiva cofreAuxPP = new CofreProvisionPasiva(cordX, cordY, id_cofre);
					listaCofres.add(cofreAuxPP);
				}

				default -> {
					System.out.println("Tipo de cofre no reconocido: " + tipoCofre);
				}
				}

			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
		} finally {
			if (lector != null) {
				try {
					lector.close();
				} catch (IOException e) {
					System.err.println(e.getMessage());
				}
			}
		}
		return listaCofres;
	}

	// ------------------------------------<STOCK>------------------------------------
	public void leerArchivoStock(ArrayList<Item> listaItems, ArrayList<Cofre> listaCofres) {
		InputStream nombreArchivo = getClass().getResourceAsStream("/importaciones/archivoStock.csv");

		// En este caso creo un HashMap de Items y Cofre para poder buscar facilmente el
		// id. De esta manera solo hay un for, en vez de varios whiles...
		HashMap<Integer, Item> mapaItem = new HashMap<>();
		HashMap<String, Cofre> mapaCofre = new HashMap<>();
		for (Item it : listaItems) {
			mapaItem.put(it.getId(), it);
		}
		for (Cofre cof : listaCofres) {

			mapaCofre.put(cof.getId(), cof);

		}

		BufferedReader lector = null;
		try {
			lector = new BufferedReader(new InputStreamReader(nombreArchivo, StandardCharsets.UTF_8));
			// lector = new BufferedReader(new InputStreamReader(nombreArchivo));
			String linea;
			while ((linea = lector.readLine()) != null) {
				String[] valores = linea.split(";");
				if (valores.length < 3) {
					System.out.println("Línea inválida: " + linea);
					continue; // Saltar la línea si es invalida
				}

				String id_cofreSolicita = valores[0];
				int id_Item = Integer.parseInt(valores[1]);
				int cantidadSolicitada = Integer.parseInt(valores[2]);

				Cofre auxCofre = mapaCofre.get(id_cofreSolicita); // Busca el cofre en la lista
				Item itemAux = mapaItem.get(id_Item); // y el item correspondiente (si existen las dos cosas)
				if (auxCofre != null && itemAux != null)
					auxCofre.guardarItem(itemAux, cantidadSolicitada);
				else {
					// Reporto que no existe.
					System.out.println("Item o Cofre no encontrado: " + id_cofreSolicita + " o " + id_Item);
				}

			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
		} finally {
			if (lector != null) {
				try {
					lector.close();
				} catch (IOException e) {
					System.err.println(e.getMessage());
				}
			}
		}
	}

	// -------------------------------<OFERTAR>-------------------------------

	public void leerArchivoOferta(ArrayList<Item> listaItems, ArrayList<Cofre> listaCofres) {
		InputStream nombreArchivo = getClass().getResourceAsStream("/importaciones/archivoOferta.csv");

		HashMap<Integer, Item> mapaItem = new HashMap<>();
		HashMap<String, Cofre> mapaCofre = new HashMap<>();
		for (Item it : listaItems) {
			mapaItem.put(it.getId(), it);
		}
		for (Cofre cof : listaCofres) {
			mapaCofre.put(cof.getId(), cof);
		}

		BufferedReader lector = null;
		try {
			lector = new BufferedReader(new InputStreamReader(nombreArchivo, StandardCharsets.UTF_8));
			// lector = new BufferedReader(new InputStreamReader(nombreArchivo));
			String linea;
			while ((linea = lector.readLine()) != null) {
				String[] valores = linea.split(";");
				if (valores.length < 3) {
					System.out.println("Línea inválida: " + linea);
					continue;
				}

				String id_cofreSolicita = valores[0];
				int id_Item = Integer.parseInt(valores[1]);
				int cantidadSolicitada = Integer.parseInt(valores[2]);

				Cofre auxCofre = mapaCofre.get(id_cofreSolicita);
				Item itemAux = mapaItem.get(id_Item);
				if (auxCofre != null && itemAux != null) {
					if (auxCofre instanceof CofreBufer) {
						((CofreBufer) auxCofre).agregarOferta(itemAux, cantidadSolicitada);
					} else if (auxCofre instanceof CofreProvisionActiva) {
						((CofreProvisionActiva) auxCofre).agregarOferta(itemAux, cantidadSolicitada);
					} else {
						System.out.println("Tipo de cofre no reconocido: " + auxCofre.getId());
					}
				} else {
					System.out.println("Item o Cofre no encontrado: " + id_cofreSolicita + " o " + id_Item);
				}
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
		} finally {
			if (lector != null) {
				try {
					lector.close();
				} catch (IOException e) {
					System.err.println(e.getMessage());
				}
			}
		}
	}

	// -------------------------------<SOLICITUD>-------------------------------

	public void leerArchivoSolicitud(ArrayList<Item> listaItems, ArrayList<Cofre> listaCofres) {
		InputStream nombreArchivo = getClass().getResourceAsStream("/importaciones/archivoPedidos.csv");

		HashMap<Integer, Item> mapaItem = new HashMap<>();
		HashMap<String, Cofre> mapaCofre = new HashMap<>();
		for (Item it : listaItems) {
			mapaItem.put(it.getId(), it);
		}
		for (Cofre cof : listaCofres) {
			mapaCofre.put(cof.getId(), cof);
		}

		BufferedReader lector = null;
		try {
			lector = new BufferedReader(new InputStreamReader(nombreArchivo, StandardCharsets.UTF_8));
			// lector = new BufferedReader(new InputStreamReader(nombreArchivo));
			String linea;
			while ((linea = lector.readLine()) != null) {
				String[] valores = linea.split(";");
				if (valores.length < 3) {
					System.out.println("Línea inválida: " + linea);
					continue;
				}

				String id_cofreSolicita = valores[0];
				int id_Item = Integer.parseInt(valores[1]);
				int cantidadSolicitada = Integer.parseInt(valores[2]);

				Cofre auxCofre = mapaCofre.get(id_cofreSolicita);
				Item itemAux = mapaItem.get(id_Item);
				if (auxCofre != null && itemAux != null) {
					if (auxCofre instanceof CofreBufer) {
						((CofreBufer) auxCofre).solicitarItem(itemAux, cantidadSolicitada);
					} else if (auxCofre instanceof CofreSolicitud) {
						((CofreSolicitud) auxCofre).solicitarItem(itemAux, cantidadSolicitada);
					} else {
						System.out.println("Tipo de cofre no reconocido: " + auxCofre.getId());
					}
				} else {
					System.out.println("Item o Cofre no encontrado: " + id_cofreSolicita + " o " + id_Item);
				}
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
		} finally {
			if (lector != null) {
				try {
					lector.close();
				} catch (IOException e) {
					System.err.println(e.getMessage());
				}
			}
		}
	}

}