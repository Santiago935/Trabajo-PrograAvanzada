package pruebasUnitarias;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import red.Coordenada;


public class coordenadaTest {

	private Coordenada punto1, punto2;

	@Before
	public void setUp() {

	    punto1 = new Coordenada(9,3);
	    punto2 = new Coordenada(-2,-3);



	}

	@Test
	public void getX() {
		assertEquals("Test1: X no es correcto", punto1.getX(), 9);
		}

	@Test
	public void getY() {
		assertEquals("Test1: Y no es correcto",punto1.getY(), 3);
		}
	@Test
	public void calcularDistanciaEucladiana() {
		assertEquals("Test1: Distancia eucloriana no correcta", Coordenada.distancia_eucladiana(punto1, punto2), 12.529 , 0.001);
		}
}


