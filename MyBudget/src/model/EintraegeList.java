package model;

import java.util.ArrayList;

public class EintraegeList {
	private ArrayList<Eintrag> eintraege;

	public EintraegeList(ArrayList<Eintrag> eintraege) {
		super();
		this.eintraege = eintraege;
	}
	public EintraegeList() {}

	public ArrayList<Eintrag> getEintraege() {
		return eintraege;
	}

	public void setEintraege(ArrayList<Eintrag> eintraege) {
		this.eintraege = eintraege;
	}
	
}
