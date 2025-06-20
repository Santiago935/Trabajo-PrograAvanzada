package cola_de_prioridad;

import java.util.ArrayList;
import java.util.NoSuchElementException;

/*
#define COLA_LLENA 0
#define COLA_VACIA 0
#define TODO_BIEN 1
#define MINIMO(X,Y) (X<=Y?X:Y)

void crearCola(tCola *pCola);
int colaVacia(const tCola *pCola);
int colaLlena(const tCola *pCola, unsigned int tamD);
void vaciarCola(tCola *pCola);

int ponerEnCola(tCola *pCola, const void *dato, unsigned int cBytes);
int verTopeDeCola(const tCola *pCola, void *dato, unsigned int cBytes);
int sacarDeCola(tCola *pCola, void *dato, unsigned int cBytes);

 */

public class TDA {

	public interface Cola_prioridad<T> {
		void encolar(T elemento); // Insertar elemento al final

		T desencolar(); // Eliminar y retornar el primero

		T primerElemento(); // Consultar el primer elemento sin eliminarlo

		boolean estaVacia(); // Verificar si está vacía
	}

	 public static class Cola_prioridad_heap<T extends Comparable<T>> implements Cola_prioridad<T> {
		 //Atributos
		 private Heap_min<T> heap; //El heap que va a manejar la prioridad
		 
		//Constructor
		 public Cola_prioridad_heap() { //Constructur
			 heap = new Heap_min<T>();
		 }
		 
		 @Override
		 public void encolar(T elemento)
		 {
			 heap.agregar(elemento);
		 }
		 
		 @Override
		 public T desencolar()
		 {
				if (estaVacia()) {
					throw new NoSuchElementException("Heap vacío");
				}
				return heap.remover();
		 }
		 
		 @Override
		 public boolean estaVacia()
		 {
			 return heap.estaVacio();
		 }
		 
		 @Override
		 public T primerElemento()
		 {
			 if (estaVacia()) {
					throw new NoSuchElementException("Heap vacío");
				}
				return heap.primerElemento();
		 }
		 
		 
	 }

	public interface Heap<T> {
		void agregar(T elemento);
		T remover();
		T primerElemento();
	}

	public static class Heap_min<T extends Comparable<T>> implements Heap<T> {
		private ArrayList<T> datos; // Acá guardo los datos

		public Heap_min() {
			datos = new ArrayList<T>(); // Poner la T es redundante porque ya está en el dato de arriba
			datos.add(null); // Reservamos la posición 0 para mejorar los índices
		}

		public boolean estaVacio() {
			return (datos.size() == 1); // Si queda solo el null está vacia.
		}

		@Override
		public void agregar(T elemento) {
			datos.add(elemento);
			subir(datos.size() - 1); // No usamos el indice 0, si hay un elemento aparte del null, size devuelve 2.
		}

		// Métodos privados para mantener el heap -------------------------------------

		// Método subir para balancear el árbol mínimo (RECURSIVO)
		private void subir(int i) {
			if (i <= 1)
				return;

			int padre = (int) i / 2;

			// Si el hijo es más chico, debe subir (intercambio con el padre).
			if (datos.get(i).compareTo(datos.get(padre)) <= 0) {
				intercambiar(i, padre);
				subir(padre); // Ahora mi insertado quedó como padre, debo seguir evaluando
			}
		}

		// Método genérico para intercambio de datos en el Vector
		private void intercambiar(int i, int j) {
			T aux = datos.get(i);
			datos.set(i, datos.get(j));
			datos.set(j, aux);
		}

		// Método para agregar un nuevo elemento
		@Override
		public T remover() {
			if (estaVacio()) {
				throw new NoSuchElementException("Heap vacío");
			}

			T minimo = datos.get(1);
			T ultimo = datos.remove(datos.size() - 1); // Sacamos el ultimo hijo

			if (!estaVacio()) {
				datos.set(1, ultimo);
				bajar(1);
			}

			return minimo;
		}
		
		@Override
		public T primerElemento()
		{
			if (estaVacio()) {
				throw new NoSuchElementException("Heap vacío");
			}
			
			return datos.get(1);
		}

		private void bajar(int i) {
			int ultimo_elem = datos.size() - 1;

			if (i > ultimo_elem)
				return;

			int pos_hijoDer = i * 2 + 1;
			int pos_hijoIzq = i * 2;
			int hijo_minimo;

			// Valido posiciones validas
			// Primero pregunto por el mayor indice entre los hijos (el derecho)
			if (pos_hijoDer > ultimo_elem) // Si el hijo derecho es invalido...
			{
				if (pos_hijoIzq <= ultimo_elem) // Si izquierdo es valido...
				{
					hijo_minimo = pos_hijoIzq;
				} else
					return; // Llegamos al límite, ninguno era valido
			} else // Ambos hijos son válidos, si el der es valido, el izquierdo también
			{
				// Guardo como mínimo al mas chico (ES UN HEAP DE MINIMO)
				if (datos.get(pos_hijoIzq).compareTo(datos.get(pos_hijoDer)) < 0)
					hijo_minimo = pos_hijoIzq;
				else
					hijo_minimo = pos_hijoDer;

				// Si el hijo es más chico, debe subir
				if (datos.get(hijo_minimo).compareTo(datos.get(i)) < 0) {
					intercambiar(hijo_minimo, i);
					bajar(hijo_minimo); // Ahora mi elemento insertado quedó como hijo, debo seguir evaluando
				}
			}

		}
		
		
//------------------------------------------------------------------------------------------------------//

		public static void main(String[] args) {
			Heap_min<Double> heap = new Heap_min<>();

			Cola_prioridad_heap<Double> colaPrio = new Cola_prioridad_heap<>();
			
			Double[] valores = { 15.0, 3.0, 8.0, 21.0, 1.0, 10.0 };

			// Agregar valores al heap
			for (Double v : valores) {
				heap.agregar(v);
				System.out.println("Agregado: " + v);
				
				colaPrio.encolar(v);
			}

			// Remover y mostrar en orden creciente
			System.out.println("\nRemoviendo elementos:");
			while (!heap.estaVacio()) {
				System.out.println(heap.remover());
			}
			
			// Desencolar
			System.out.println("\nRemoviendo elementos:");
			while (!colaPrio.estaVacia()) {
				System.out.println(colaPrio.desencolar());
			}
			
		}
	}
}
