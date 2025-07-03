package pruebasUnitarias;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.Set;
import cofres.CofreAlmacenamiento;
import red.Red;
import red.Robot;
import red.Robopuerto;

public class RedTest {

    private Red red;
    private Robopuerto rp1, rp2;
    private CofreAlmacenamiento cofre1;
    private Robot robot1;

    @Before
    public void setUp() {
        red = new Red("Red-Test");
        rp1 = new Robopuerto("RP-A", 10, 10, 100);
        rp2 = new Robopuerto("RP-B", 20, 20,  100);
        cofre1 = new CofreAlmacenamiento(12, 12, "Cofre-1");
        robot1 = new Robot("R2D2", rp1.getNodo());
    }

    @Test
    public void getters() {
        assertNotNull("El grafo de la red no debe ser null.", red.getGrafo_red());
        assertTrue("La lista de robopuertos debe estar vacía inicialmente.", red.getRobopuertos().isEmpty());
        assertTrue("La lista de cofres debe estar vacía inicialmente.", red.getCofres().isEmpty());
        assertTrue("La lista de robots debe estar vacía inicialmente.", red.getRobots().isEmpty());
    }

    @Test
    public void addRobopuerto() {
    	red.add_robopuerto(rp1);
        assertEquals("La lista de robopuertos debe tener 1 elemento.", 1, red.getRobopuertos().size());
        assertTrue("La lista debe contener el robopuerto añadido.", red.getRobopuertos().contains(rp1));
        red.add_robopuerto(rp2);
        assertEquals("La lista de robopuertos debe tener 2 elemento.", 2, red.getRobopuertos().size());
        assertTrue("La lista debe contener el robopuerto añadido.", red.getRobopuertos().contains(rp1) &&  red.getRobopuertos().contains(rp2));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void addRobopuertoRepetido() {
    	red.add_robopuerto(rp1);
    	red.add_robopuerto(rp1);

    }

    @Test
    public void addCofre() {
        red.add_cofre(cofre1);
        assertEquals("La lista de cofres debe tener 1 elemento.", 1, red.getCofres().size());
        assertTrue("La lista debe contener el cofre añadido.", red.getCofres().contains(cofre1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void addCofreRepetido() {
        red.add_cofre(cofre1);
        red.add_cofre(cofre1);
    }
    
    @Test
    public void addRobot() {
        red.add_robot(robot1);
        assertEquals("La lista de robots debe tener 1 elemento.", 1, red.getRobots().size());
        assertTrue("La lista debe contener el robot añadido.", red.getRobots().contains(robot1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void addRobotRepetido() {
        red.add_robot(robot1);
        red.add_robot(robot1);
    }

    @Test
    public void getIdsNodosRobopuertos() {
        red.add_robopuerto(rp1);
        red.add_robopuerto(rp2);
        
        Set<Integer> ids = red.getIdsNodosRobopuertos();
        
        assertEquals("El conjunto de IDs debe tener 2 elementos.", 2, ids.size());
        assertTrue("El conjunto debe contener el ID del nodo de RP-A.", ids.contains(rp1.getNodo().getId()));
        assertTrue("El conjunto debe contener el ID del nodo de RP-B.", ids.contains(rp2.getNodo().getId()));
    }
    
}