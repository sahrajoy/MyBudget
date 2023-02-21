package model;

public enum Intervall {
	TAEGLICH("täglich"),
	WOECHENTLICH("wöchentlich"),
	MONATLICH("monatlich"),
	QUARTAL("quartalsweise"),
	JAEHRLICH("jährlich");

	private String iName;

	private Intervall(String iName) {
		this.iName = iName;
	}
	private Intervall() {}

	public String getIName() {
		return iName;
	}

	public void setName(String iName) {
		this.iName = iName;
	}
	
	@Override
	public String toString() {
		return "Intervall [iName=" + iName + "]"; 
	}


}
	