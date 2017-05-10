package model;

import java.util.ArrayList;

public class Disease {

	private int id;
	private String nom;
	private ArrayList<String> synonymes;
	private ArrayList<String> signes;
	public Disease(int id, String nom, ArrayList<String> synonymes, ArrayList<String> signes) {
		super();
		this.id = id;
		this.nom = nom;
		this.synonymes = synonymes;
		this.signes = signes;
	}
	public Disease(int id, String nom) {
		super();
		this.id = id;
		this.nom = nom;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
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
		return "Disease [id=" + id + ", nom=" + nom + ", synonymes=" + synonymes + ", signes=" + signes + "]";
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
