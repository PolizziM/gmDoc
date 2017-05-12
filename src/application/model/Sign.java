package application.model;

public class Sign {
	
	private int id=0;
	private String name="";
	
	public Sign(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public Sign(){
		
	}
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "Sign [id=" + id + ", name=" + name + "]";
	}
}
