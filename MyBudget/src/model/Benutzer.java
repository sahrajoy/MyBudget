package model;

public class Benutzer {

	private int benutzerId;
	private String name;
	
	//Konstruktor um aus der Datenbank auszulesen
	public Benutzer(int benutzerId, String name) {
		super();
		this.benutzerId = benutzerId;
		this.name = name;
	}
	//Konstruktor um in die Datenbank einzulesen und 
	//HAUSHALT bei createTable() zu erstellen
	public Benutzer(String name) {
		super();
		this.name = name;
	}
	//Konstruktor um aus der Datenbank Dauereintraege auszulesen
	public Benutzer(int benutzerId) {
		super();
		this.benutzerId = benutzerId;
	}

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
