package pruebasUnitarias;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import grafos.Grafo;
import grafos.Nodo;
import red.Robot;

public class robotTest {
	private Nodo nodo1, nodo2, nodo5;
	private Grafo grafo1;
	private Robot robot1;


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

	    robot1 = new Robot("B1", nodo5);

	}

	@Test
	public void getId() {
		assertEquals("Test1: El id del robot no es el insertado", robot1.getId(), "B1");}
	@Test
	public void getCargaMax() {
		assertEquals("Test2: La cargar Maxima del robot no es el correcto",robot1.getCarga_max(), 20.0, 0.001);}
	@Test
	public void getCargaActual() {
		assertEquals("Test3: La carga actual no es correcta",robot1.getCarga_actual(), 0);}
	@Test
	public void getNodoActual() {
		assertEquals("Test4: El nodo Actual del robot no es correcto",robot1.getNodo_actual(), nodo5);}
	@Test
	public void getIdRPInicial() {
		assertEquals("Test5: El id del nodo Actual del robot no es correcto",robot1.getIdRPInicial(), "Nodo Nabooeano");}
	@Test
	public void getBateria() {
		assertEquals("Test6: La bateria inicial del robot no es correcta",robot1.getBateria(), 100, 0.001);}
	@Test
	public void getPedidoActual() {
		assertEquals("Test7: No deberia tener pedido",robot1.getPedidoActual(), null);}
}

