package model;

public enum Intervall {
	TAEGLICH("täglich"),
	WOECHENTLICH("wöchentlich"),
	MONATLICH("monatlich"),
	QUARTAL("quartalsweise"),
	JAEHRLICH("jährlich");

	private String name;

	private Intervall(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String toString() {
		return name; 
	}

}
	