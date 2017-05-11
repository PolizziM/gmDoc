package model;

public class Omim {
	
	private String id;
	private String[] sign;
	private String[] synonym;
	private String cui;

	public Omim(String id, String[] sign, String[] synonym, String cui) {
		this.id = id;
		this.sign = sign;
		this.synonym = synonym;
		this.cui = cui;
	}
	
	public Omim() {
		this.id = null;
		this.sign = null;
		this.synonym = null;
		this.cui = null;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String[] getSign() {
		return sign;
	}

	public void setSign(String[] sign) {
		this.sign = sign;
	}

	public String[] getSynonym() {
		return synonym;
	}

	public void setSynonym(String[] synonym) {
		this.synonym = synonym;
	}

	public String getCui() {
		return cui;
	}

	public void setCui(String cui) {
		this.cui = cui;
	}
	
	

	@Override
	public String toString() {
		return "Omim [id=" + id + ", sign=" + sign + ", synonym=" + synonym + ", cui=" + cui + "]";
	}
	
	
}
