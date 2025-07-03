package pruebasUnitarias;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import cofres.Cofre;
import cofres.CofreAlmacenamiento;
import red.ArmadoRed;
import red.Red;
import red.Robot;
import red.Robopuerto;

public class ArmadoRedTest {

    private ArrayList<Robopuerto> robopuertos;
    private ArrayList<Cofre> cofres;
    private ArrayList<Robot> robots;

    @Before
    public void setUp() {
        robopuertos = new ArrayList<>();
        cofres = new ArrayList<>();
        robots = new ArrayList<>();
    }

    @Test
    public void radiosSolapados() {
        Robopuerto rpA = new Robopuerto("A", 0, 0,  10); // radio 10
        Robopuerto rpB = new Robopuerto("B", 15, 0,  6); // radio 6
        // Distancia es 15. Suma de radios es 16. Se solapan.
        assertTrue("Los radios deberían solaparse.", ArmadoRed.radios_solapados(rpA, rpB));
    }

    @Test
    public void radiosNoSolapados() {
        Robopuerto rpA = new Robopuerto("A", 0, 0,  5); // radio 5
        Robopuerto rpB = new Robopuerto("B", 15, 0,  5); // radio 5
        // Distancia es 15. Suma de radios es 10. No se solapan.
        assertFalse("Los radios no deberían solaparse.", ArmadoRed.radios_solapados(rpA, rpB));
    }

    @Test
    public void armadoRedesSeparadas() {
        robopuertos.add(new Robopuerto("RP1", 0, 0,  10)); // Red 1
        robopuertos.add(new Robopuerto("RP2", 100, 100,  10)); // Red 2
        
        ArrayList<Red> redes = ArmadoRed.armado_redes(robopuertos, cofres, robots);
        assertEquals("Deberían crearse 2 redes separadas.", 2, redes.size());
    }

    @Test
    public void armadoRedesUnidas() {
        robopuertos.add(new Robopuerto("RP1", 0, 0,  20));
        robopuertos.add(new Robopuerto("RP2", 10, 0,  20)); // Se solapa con RP1
        robopuertos.add(new Robopuerto("RP3", 100, 100,  10)); // Red separada
        
        ArrayList<Red> redes = ArmadoRed.armado_redes(robopuertos, cofres, robots);
        assertEquals("Deberían crearse 2 redes (una de ellas con 2 RPs).", 2, redes.size());
        
        Red redUnida = redes.get(0).getRobopuertos().size() == 2 ? redes.get(0) : redes.get(1);
        assertEquals("La red unida debe tener 2 robopuertos.", 2, redUnida.getRobopuertos().size());
    }

    @Test
    public void armadoCofres() {
        Robopuerto rp = new Robopuerto("RP-Centro", 50, 50,  20); // radio 20
        Red red = new Red("Red-1");
        red.add_robopuerto(rp);
        ArrayList<Red> redes = new ArrayList<>();
        redes.add(red);
        
        Cofre cofreDentro = new CofreAlmacenamiento(55, 55, "C1"); // Distancia < 20
        Cofre cofreFuera = new CofreAlmacenamiento(80, 80, "C2"); // Distancia > 20
        cofres.add(cofreDentro);
        cofres.add(cofreFuera);
        
        ArmadoRed.armado_cofres(redes, cofres);
        
        assertEquals("La red debe tener solo 1 cofre.", 1, red.getCofres().size());
        assertTrue("La red debe contener el cofre de adentro.", red.getCofres().contains(cofreDentro));
    }

    @Test
    public void armadoRobots() {
        Red redA = new Red("Red-A");
        redA.add_robopuerto(new Robopuerto("RP-A", 0,0,10));
        Red redB = new Red("Red-B");
        redB.add_robopuerto(new Robopuerto("RP-B", 100,100,10));
        ArrayList<Red> redes = new ArrayList<>();
        redes.add(redA);
        redes.add(redB);
        
        Robot robotA = new Robot("R1", redA.getRobopuertos().getFirst().getNodo());
        Robot robotB = new Robot("R2", redB.getRobopuertos().getFirst().getNodo());
        robots.add(robotA);
        robots.add(robotB);
        
        ArmadoRed.armado_robots(redes, robots);
        
        assertEquals("La Red A debe tener 1 robot.", 1, redA.getRobots().size());
        assertTrue("La Red A debe contener al robot R1.", redA.getRobots().contains(robotA));
        assertEquals("La Red B debe tener 1 robot.", 1, redB.getRobots().size());
        assertTrue("La Red B debe contener al robot R2.", redB.getRobots().contains(robotB));
        assertTrue("La Red A no debe contener al robot R2.", !redA.getRobots().contains(robotB));
    }

}