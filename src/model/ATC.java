package model;

public class ATC {
	private String codeATC;
	private String drug;
	
	public ATC() {
		this.codeATC = null;
		this.drug = null;
	}
	
	@Override
	public String toString() {
		return "ATC [codeATC=" + codeATC + ", drug=" + drug + "]";
	}

	public ATC(String codeATC, String drug) {
		this.codeATC = codeATC;
		this.drug = drug;
	}

	public String getCodeATC() {
		return codeATC;
	}

	public void setCodeATC(String codeATC) {
		this.codeATC = codeATC;
	}

	public String getDrug() {
		return drug;
	}

	public void setDrug(String drug) {
		this.drug = drug;
	}
	
	

}
