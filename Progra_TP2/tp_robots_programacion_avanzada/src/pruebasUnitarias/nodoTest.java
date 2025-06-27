package pruebasUnitarias;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


import grafos.Nodo;

public class nodoTest {

	private Nodo nodo1;
	@Before
	public void setUp() {
	    nodo1 = new Nodo(01, "Nodo de la Muerte");
	}


	@Test
	public void getId() {
		assertEquals("Test1: El id del nodo no es el insertado",nodo1.getId(), 01);
		
	}
	@Test 
	public void getAlias() {
		assertEquals("Test2: El Alias del nodo no es el insertado",nodo1.getAlias(), "Nodo de la Muerte");
	}
	
	@Test
	public void getString() {
		assertEquals("Test3: toString", "Nodo[1: Nodo de la Muerte]", nodo1.toString());
	}
	

}
