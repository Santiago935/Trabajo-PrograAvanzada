package grafos;

import java.util.*;
import cola_de_prioridad.TDA;


public class AlgoritmosGrafos {

    private static final int FACTOR_PRECISION = 100; // Para 2 decimales

    // Clase auxiliar para la cola de prioridad, usando enteros escalados
    private static class EstadoNodo implements Comparable<EstadoNodo> {
        private final Nodo nodo;
        private final int costoAcumulado; // Costo escalado (multiplicado por FACTOR_PRECISION)
        private final int bateriaAlLlegar; // Batería escalada

        EstadoNodo(Nodo nodo, int costoAcumulado, int bateriaAlLlegar) {
            this.nodo = nodo;
            this.costoAcumulado = costoAcumulado;
            this.bateriaAlLlegar = bateriaAlLlegar;
        }

        public Nodo getNodo() {
            return nodo;
        }

        public int getCostoAcumulado() {
            return costoAcumulado;
        }

        public int getBateriaAlLlegar() {
            return bateriaAlLlegar;
        }

        @Override
        public int compareTo(EstadoNodo otro) {
            return Integer.compare(this.costoAcumulado, otro.getCostoAcumulado());
        }
    }

    public static class Dijkstra_resultado {
        private final Map<Integer, Double> costos_minimos_double; // Se almacenan como double para el usuario
        private final Map<Integer, Integer> predecesores;
        private final Map<Integer, Double> mejorBateriaConCostoMinimoDouble;
        
        // El constructor ahora toma el mapa de costos mínimos con enteros escalados
        public Dijkstra_resultado(Map<Integer, Integer> costos_minimos_int, Map<Integer, Integer> predecesores, Map<Integer, Integer> mejorBateriaConCostoMinimoInt) {
            this.costos_minimos_double = new HashMap<>();
            for (Map.Entry<Integer, Integer> entry : costos_minimos_int.entrySet()) {
                if (entry.getValue() == Integer.MAX_VALUE) {
                    this.costos_minimos_double.put(entry.getKey(), Double.MAX_VALUE);
                } else {
                    // Convertir de nuevo a double para el resultado final
                    this.costos_minimos_double.put(entry.getKey(), (double) entry.getValue() / FACTOR_PRECISION);
                }
            }
            this.predecesores = predecesores;
            
            // Convertimos la batería también a double
            this.mejorBateriaConCostoMinimoDouble = new HashMap<>();
            for (Map.Entry<Integer, Integer> entry : mejorBateriaConCostoMinimoInt.entrySet()) {
                if (entry.getValue() == Integer.MAX_VALUE) {
                    this.mejorBateriaConCostoMinimoDouble.put(entry.getKey(), Double.MAX_VALUE);
                } else {
                    this.mejorBateriaConCostoMinimoDouble.put(entry.getKey(), (double) entry.getValue() / FACTOR_PRECISION);
                }
            }
        }

        public Map<Integer, Double> getCostosMinimos() {
            return costos_minimos_double;
        }

        public Map<Integer, Integer> getPredecesores() {
            return predecesores;
        }

		public Map<Integer, Double> getMejorBateriaConCostoMinimoDouble() {
			return mejorBateriaConCostoMinimoDouble;
		}

		// Mantén tu método imprimirDijkstraResultado o ajusta si es necesario
        public static void imprimirDijkstraResultado(Dijkstra_resultado resultado) {
            if (resultado == null || resultado.costos_minimos_double == null || resultado.predecesores == null) {
                System.out.println("Resultado de Dijkstra no válido o vacío.");
                return;
            }
            System.out.println("Costos mínimos desde el nodo origen (precisión 2 decimales):");
            for (Map.Entry<Integer, Double> entry : resultado.costos_minimos_double.entrySet()) {
                if (entry.getValue() < Double.MAX_VALUE) {
                    System.out.println("Nodo " + entry.getKey() + ": " + String.format("%.2f", entry.getValue()));
                } else {
                    System.out.println("Nodo " + entry.getKey() + ": Inalcanzable");
                }
            }

            System.out.println("\nPredecesores para reconstruir caminos:");
            for (Map.Entry<Integer, Integer> entry : resultado.predecesores.entrySet()) {
                if (entry.getValue() != null) {
                    System.out.println("Nodo " + entry.getKey() + " <- Nodo " + entry.getValue());
                }
            }
        }
        
         public List<Integer> reconstruirCamino(int idDestino) {
            LinkedList<Integer> camino = new LinkedList<>();
            Integer actual = idDestino;
            if (!costos_minimos_double.containsKey(actual) || costos_minimos_double.get(actual) == Double.MAX_VALUE) {
                return camino; 
            }
            while (actual != null) {
                camino.addFirst(actual);
                actual = predecesores.get(actual);
                if (camino.size() > predecesores.size() && predecesores.size() > 0) { 
                    System.err.println("Error: Bucle detectado en la reconstrucción del camino para el nodo " + idDestino);
                    return new LinkedList<>();
                }
            }
            return camino;
        }
    }


    public static Dijkstra_resultado dijkstraConBateria(
            Grafo grafo,
            Nodo inicio,
            double bateriaInicialDouble, // Parámetro original
            double bateriaMaximaDouble,  // Parámetro original
            Set<Integer> robopuertos) {

        // Mapas internos usarán enteros escalados
        Map<Integer, Integer> costosMinimosInt = new HashMap<>();
        Map<Integer, Integer> predecesores = new HashMap<>(); // Se mantiene igual
        Map<Integer, Integer> mejorBateriaConCostoMinimoInt = new HashMap<>();

        TDA.Cola_prioridad_heap<EstadoNodo> colaPrioridad = new TDA.Cola_prioridad_heap<>();

        // Convertir parámetros double a enteros escalados una vez
        int bateriaInicialInt = (int) Math.round(bateriaInicialDouble * FACTOR_PRECISION);
        int bateriaMaximaInt = (int) Math.round(bateriaMaximaDouble * FACTOR_PRECISION);


        //Inicializo para cada nodo del grafo, costo de llegada máximo, predecesor nulo y batería -1
        for (Nodo nodo : grafo.getNodos()) {
            costosMinimosInt.put(nodo.getId(), Integer.MAX_VALUE);
            predecesores.put(nodo.getId(), null);
            mejorBateriaConCostoMinimoInt.put(nodo.getId(), -1); // Un valor que indique "sin batería válida"
        }

        /*
        if (inicio == null) {
            System.err.println("Error: El nodo de inicio no puede ser null.");
            // Devolver un resultado vacío pero inicializado para evitar NullPointerExceptions posteriores
            Map<Integer, Double> costosVacios = new HashMap<>();
            for (Nodo nodo : grafo.getNodos()) {
                 costosVacios.put(nodo.getId(), Double.MAX_VALUE);
            }
            return new Dijkstra_resultado(new HashMap<>(), new HashMap<>()); // O como manejes resultados vacíos
        }*/
        
        costosMinimosInt.put(inicio.getId(), 0); // Costo inicial es 0 (0 * 100 = 0)
        mejorBateriaConCostoMinimoInt.put(inicio.getId(), bateriaInicialInt);
        colaPrioridad.encolar(new EstadoNodo(inicio, 0, bateriaInicialInt)); //Arranco por el inicial

        //Empiezo a recorrer
        while (!colaPrioridad.estaVacia()) {
            EstadoNodo estadoActual = colaPrioridad.desencolar();
            Nodo nodoActual = estadoActual.getNodo();
            int idActual = nodoActual.getId();
            int costoActualInt = estadoActual.getCostoAcumulado();
            int bateriaAlLlegarANodoActualInt = estadoActual.getBateriaAlLlegar();

            // Si el costo es mayor o igual con peor estado de batería, no hago nada.
            if (costoActualInt > costosMinimosInt.get(idActual) ||
                (costoActualInt == costosMinimosInt.get(idActual) 
                && bateriaAlLlegarANodoActualInt < mejorBateriaConCostoMinimoInt.get(idActual))) {
                continue;
            }

            int bateriaParaSalirDeActualInt = bateriaAlLlegarANodoActualInt;
            if (robopuertos.contains(idActual)) {
                bateriaParaSalirDeActualInt = bateriaMaximaInt;
            }

            //Para cada arista que tenga el nodo W
            for (Arista arista : grafo.getAristas_deNodo(nodoActual)) {
                Nodo vecino = arista.getObjetivo();
                int idVecino = vecino.getId();
                // Convertir peso de arista (double) a entero escalado
                int pesoAristaInt = (int) Math.round(arista.getPeso() * FACTOR_PRECISION);

                //Si me alcanza la batería
                if (bateriaParaSalirDeActualInt >= pesoAristaInt) {
                    int bateriaAlLlegarAVecinoInt = bateriaParaSalirDeActualInt - pesoAristaInt;
                    int nuevoCostoAVecinoInt = costoActualInt + pesoAristaInt;
                    
                    //Si mejora el costo o iguala el costo pero mejora la batería
                    if (nuevoCostoAVecinoInt < costosMinimosInt.get(idVecino) ||
                        (nuevoCostoAVecinoInt == costosMinimosInt.get(idVecino) && bateriaAlLlegarAVecinoInt > mejorBateriaConCostoMinimoInt.get(idVecino))) {
                        
                    	//Actualizo costos, predecesores y batería
                        costosMinimosInt.put(idVecino, nuevoCostoAVecinoInt);
                        predecesores.put(idVecino, idActual); // Predecesor sigue siendo ID
                        mejorBateriaConCostoMinimoInt.put(idVecino, bateriaAlLlegarAVecinoInt);
                        
                        //nueva ruta a evaluar
                        colaPrioridad.encolar(new EstadoNodo(vecino, nuevoCostoAVecinoInt, bateriaAlLlegarAVecinoInt));
                    }
                }
            }
        }
        // Al final, se crea el Dijkstra_resultado, que internamente convertirá los costos a double
        return new Dijkstra_resultado(costosMinimosInt, predecesores, mejorBateriaConCostoMinimoInt);
    }
}