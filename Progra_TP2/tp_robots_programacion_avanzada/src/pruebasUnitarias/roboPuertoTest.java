package pruebasUnitarias;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


import grafos.Grafo;
import grafos.Nodo;
import red.Robopuerto;


public class roboPuertoTest {
	private Nodo nodo1, nodo2, nodo3, nodo5;
	private Grafo grafo1;
	private Robopuerto puerto1;

	@Before
	public void setUp() {
	    nodo1 = new Nodo(01, "Nodo de la Muerte");
	    nodo2 = new Nodo(02, "Nodo Yaviniano");
	    nodo5 = new Nodo(05, "Nodo Nabooeano");
	    
	    
	    grafo1 = new Grafo(false);
	    
	    grafo1.addNodo(nodo1);
	    grafo1.addNodo(nodo2);
	    grafo1.addNodo(nodo5);

	    grafo1.addArista(nodo2, nodo1, 5);
	    grafo1.addArista(nodo5, nodo1, 70);
	    grafo1.addArista(nodo2, nodo5, 500);
	    puerto1 = new Robopuerto("Mordor", 3, 3, 5);
	    

	}
	@Test
	public void getId() {
		assertEquals("Test1: El id del nodo no es el insertado","Mordor", puerto1.getId());}
	
	@Test
	public void getNodo() {
		nodo3 = puerto1.getNodo();
        assertNotNull("Test1: El nodo del robopuerto no deberia ser nulo", nodo3); 
        assertEquals("Test2: El Id del nodo interno no se calculo correctamente", 12, nodo3.getId()); 
        assertEquals("Test3: El alias del nodo interno no es correcto", "Mordor", nodo3.getAlias());
        }
	@Test
	public void getCoordenada() {
		 assertNotNull("Test1: La coordenada del robopuerto no deberia ser nula", puerto1.getCoordenada()); 
	      assertEquals("Test2: La coordenada X no es correcta", 3, puerto1.getCoordenada().getX()); 
	        assertEquals("Test3: La coordenada Y no es correcta", 3, puerto1.getCoordenada().getY()); 
	        }
	@Test
	public void getRadio() {
		assertEquals("Test1: Radio incorrecto",puerto1.getRadio(), 5, 0.001);
		}

	
}


