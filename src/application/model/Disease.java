package application.model;

import java.util.ArrayList;

public class Disease {

	private int id;
	private String name;
	private ArrayList<String> synonymes;
	private ArrayList<String> signes;
	
	public Disease(int id, String name, ArrayList<String> synonymes, ArrayList<String> signes) {
		super();
		this.id = id;
		this.name = name;
		this.synonymes = synonymes;
		this.signes = signes;
	}
	
	public Disease() {
		this.id = 0;
		this.name = null;
		this.synonymes = new ArrayList<String>();
		this.signes = new ArrayList<String>();
	}
	
	public Disease(int id, String name) {
		super();
		this.id = id;
		this.name = name;
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
	
	public ArrayList<String> getSynonymes() {
		return synonymes;
	}
	
	public void setSynonymes(ArrayList<String> synonymes) {
		this.synonymes = synonymes;
	}
	
	public void addSynonyme(String synonyme) {
		this.synonymes.add(synonyme);
	}
	
	public ArrayList<String> getSignes() {
		return signes;
	}
	
	public void setSignes(ArrayList<String> signes) {
		this.signes = signes;
	}
	
	public void addSigne(String signe) {
		this.signes.add(signe);
	}
	
	@Override
	public String toString() {
		return "Disease [id=" + id + ", name=" + name + ", synonymes=" + synonymes + ", signes=" + signes + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Disease other = (Disease) obj;
		if (id != other.id)
			return false;
		return true;
	}	
}
