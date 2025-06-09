package grafos;

import java.util.*;

public class Grafo {
	private final boolean esDirigido;
	private final Map<Nodo, List<Arista>> listaAdy;

	public Grafo(boolean esDirigido) {
		this.esDirigido = esDirigido;
		this.listaAdy = new HashMap<>();
	}

	public void addNodo(Nodo nodo) {
		if (this.listaAdy.containsKey(nodo)) {
			throw new IllegalArgumentException("El nodo " + nodo + " ya existe en el grafo.");
		}
		this.listaAdy.put(nodo, new ArrayList<Arista>()); // Usamos ArrayList porque es más rapido
	}

	public void addArista(Nodo nInicio, Nodo nFin, double peso) {
		this.listaAdy.putIfAbsent(nInicio, new ArrayList<Arista>());
		this.listaAdy.putIfAbsent(nFin, new ArrayList<Arista>());

		(this.listaAdy.get(nInicio)).add(new Arista(nFin, peso));

		if (!this.esDirigido) {
			(this.listaAdy.get(nFin)).add(new Arista(nInicio, peso));
		}
	}

	public List<Arista> getAristas_deNodo(Nodo nodo) {
		return this.listaAdy.getOrDefault(nodo, new ArrayList<Arista>());
	}
	
	public Set<Nodo> getVecinos(Nodo nodo)
	{
		List<Arista> aristas = getAristas_deNodo(nodo);
		
		Set<Nodo> vecinos = new HashSet<Nodo>();
		for(Arista arista : aristas)
		{
			Nodo destino = arista.getObjetivo();
			vecinos.add(destino);
		}
		
		return vecinos; //Si no tiene vecinos devuelvo un Set vacío, no un null
	}

	// Devuelvo un SET de nodos por la naturaleza no repetible de los mismos en el
	// HashMap
	public Set<Nodo> getNodos() {
		return this.listaAdy.keySet();
	}
	
	public void imprimirGrafo()
	{
		for(Nodo nodo : this.listaAdy.keySet())
		{
			System.out.print(nodo + " -> ");
			for(Arista arista : this.listaAdy.get(nodo)) {
				System.out.print(arista.getObjetivo() + "(Peso: " + arista.getPeso()+ ") ");
			}
			System.out.println();
		}
	}

}
