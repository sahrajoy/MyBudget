package model;

public class Benutzer {

	private int benutzerId;
	private String name;
	
	public Benutzer(int benutzerId, String name) {
		super();
		this.benutzerId = benutzerId;
		this.name = name;
	}
	public Benutzer(String name) {
		super();
		this.name = name;
	}
	public Benutzer(int benutzerId) {
		super();
		this.benutzerId = benutzerId;
	}
	public Benutzer() {}

	public int getBenutzerId() {
		return benutzerId;
	}

	public void setBenutzerId(int benutzerId) {
		this.benutzerId = benutzerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}

}
