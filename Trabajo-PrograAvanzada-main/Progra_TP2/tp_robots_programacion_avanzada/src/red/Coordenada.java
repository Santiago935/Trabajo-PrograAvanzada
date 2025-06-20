package red;

public class Coordenada {
	private final int x;
	private final int y;

	public Coordenada(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public static double distancia_eucladiana(Coordenada a, Coordenada b) {
		int lx = a.getX() - b.getX(); // Longitud X
		int ly = a.getY() - b.getY(); // Longitud Y
		return Math.sqrt(lx * lx + ly * ly); // raiz cuadrada de suma de cuadrados
		// Se resuelve con pitágoras.
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public String toString() {
		return "[x=" + x + ", y=" + y + "]";
	}

	
	
}