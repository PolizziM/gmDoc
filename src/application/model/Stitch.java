package application.model;

public class Stitch {
	
	private String id1;
	private String id2;
	private String codeATC;
	private Sign s;
	
	public Stitch(String id1, String id2,Sign s) {
		super();
		this.id1 = id1;
		this.id2 = id2;
		this.s=s;
	}
	
	public Stitch(String id1, String id2) {
		super();
		this.id1 = id1;
		this.id2 = id2;
	}
	
	public Stitch(String id1, String id2, String codeATC) {
		this.id1 = id1;
		this.id2 = id2;
		this.codeATC = codeATC;
	}
	
	public String getId1() {
		return id1;
	}
	
	public void setId1(String id1) {
		this.id1 = id1;
	}
	
	public String getId2() {
		return id2;
	}
	
	public void setId2(String id2) {
		this.id2 = id2;
	}
	
	public String getCodeATC() {
		return codeATC;
	}

	public void setCodeATC(String codeATC) {
		this.codeATC = codeATC;
	}

	@Override
	public String toString() {
		return "Stitch [id1=" + id1 + ", id2=" + id2 + "]";
	}
	

}
