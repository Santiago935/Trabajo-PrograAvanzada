package utiles;

public class Item {
	private int id;
	private String nombre;
	private String desc;

	public Item(int id, String nombre, String desc) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.desc = desc;
	}

	public int getId() {
		return id;
	}

	public String getDesc() {
		return desc;
	}

	public String getNombre() {
		return nombre;
	}

	@Override
	public String toString() {
		return String.format("Item[id=%s, nombre=%s]", id, nombre);
	}

}