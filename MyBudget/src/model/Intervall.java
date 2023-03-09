package model;

public enum Intervall {
	KEINE("keine"),
	TAEGLICH("taeglich"),
	WOECHENTLICH("woechentlich"),
	MONATLICH("monatlich"),
	QUARTAL("quartalsweise"),
	JAEHRLICH("jaehrlich");

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
		return iName.replace("ae", "ä")
					.replace("oe", "ö");
	}

}
	