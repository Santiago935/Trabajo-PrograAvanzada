package grafos;

public class Nodo {
	private final int id;
	private String alias = null;
	
	public Nodo(int id)
	{
		this.id = id;
	}
	
	public Nodo(int id, String alias)
	{
		this.id = id;
		this.alias = alias;
	}
	
    public int getId() {
        return id;
    }
    
    @Override
    public String toString() {
        if (alias == null) {
            return "Nodo[" + id + "]";
        }
        return "Nodo[" + id + ": " + alias + "]";
    }
}
