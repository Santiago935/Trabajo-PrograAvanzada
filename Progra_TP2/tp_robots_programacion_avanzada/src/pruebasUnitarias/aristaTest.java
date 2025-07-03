package pruebasUnitarias;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import grafos.Arista;
import grafos.Nodo;

public class aristaTest {

	private Nodo  nodo2;
	private Arista arista1;

	@Before
	public void setUp() {

	    nodo2 = new Nodo(02, "Nodo Yaviniano");


	    arista1 = new Arista(nodo2, -4);}

		@Test
		public void getObjetivo() {
			assertEquals("Test1: No esta devolviendo el Nodo Objetivo",arista1.getObjetivo(), nodo2);
		}
		@Test
		public void getPeso() {
			assertEquals("Test2: No esta devolviendo el peso inicial",arista1.getPeso(), -4, 0.001);

		}
		@Test
		public void setPeso() {

			arista1.setPeso(7.3);
			assertEquals("Test3: no funciona el metodo setPeso()",arista1.getPeso(), 7.3, 0.001);

		}

	}

