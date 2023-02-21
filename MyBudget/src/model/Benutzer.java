package model;

import java.util.ArrayList;

public class Benutzer {

	private int benutzerId;
	private String name;
	private ArrayList<Eintrag> eintraege;
	private ArrayList<Dauereintrag> dauereintraege;
	
	public Benutzer(int benutzerId, String name, ArrayList<Eintrag> eintraege, ArrayList<Dauereintrag> dauereintraege) {
		super();
		this.benutzerId = benutzerId;
		this.name = name;
		this.eintraege = eintraege;
		this.dauereintraege = dauereintraege;
	}
	public Benutzer(int benutzerId, String name) {
		super();
		this.benutzerId = benutzerId;
		this.name = name;
	}
	public Benutzer(String name) {
		super();
		this.name = name;
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

	public ArrayList<Eintrag> getEintraege() {
		return eintraege;
	}

	public void setEintraege(ArrayList<Eintrag> eintraege) {
		this.eintraege = eintraege;
	}

	public ArrayList<Dauereintrag> getDauereintrag() {
		return dauereintraege;
	}

	public void setWiederholungen(ArrayList<Dauereintrag> dauereintraege) {
		this.dauereintraege = dauereintraege;
	}

	@Override
	public String toString() {
		return name;
	}

}
