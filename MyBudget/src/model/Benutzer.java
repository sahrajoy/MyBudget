package model;

import java.util.ArrayList;

public class Benutzer {

	private int benutzerId;
	private String name;
	private ArrayList<Eintrag> eintraege;
	private ArrayList<Wiederholung> wiederholungen;
	
	public Benutzer() {}
	
	public Benutzer(int benutzerId, String name, ArrayList<Eintrag> eintraege, ArrayList<Wiederholung> wiederholungen) {
		super();
		this.benutzerId = benutzerId;
		this.name = name;
		this.eintraege = eintraege;
		this.wiederholungen = wiederholungen;
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

	public ArrayList<Eintrag> getEintraege() {
		return eintraege;
	}

	public void setEintraege(ArrayList<Eintrag> eintraege) {
		this.eintraege = eintraege;
	}

	public ArrayList<Wiederholung> getWiederholungen() {
		return wiederholungen;
	}

	public void setWiederholungen(ArrayList<Wiederholung> wiederholungen) {
		this.wiederholungen = wiederholungen;
	}

	@Override
	public String toString() {
		return "Benutzer [benutzerId=" + benutzerId + ", name=" + name + "]";
	}

}
