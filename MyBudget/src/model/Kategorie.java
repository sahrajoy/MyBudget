package model;

import java.util.ArrayList;

public class Kategorie {

	private int kategorieId;
	private String name;
	private boolean favorite = false;
	private ArrayList<Eintrag> eintraege;
	private ArrayList<Wiederholung> wiederholungen;
	
	public Kategorie(int kategorieId, String name, boolean favorite, ArrayList<Eintrag> eintraege,
			ArrayList<Wiederholung> wiederholungen) {
		super();
		this.kategorieId = kategorieId;
		this.name = name;
		this.favorite = favorite;
		this.eintraege = eintraege;
		this.wiederholungen = wiederholungen;
	}

	public int getKategorieId() {
		return kategorieId;
	}

	public void setKategorieId(int kategorieId) {
		this.kategorieId = kategorieId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isFavorite() {
		return favorite;
	}

	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
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
		return "Kategorie [kategorieId=" + kategorieId + ", name=" + name + ", favorite=" + favorite + "]";
	}
	
	
	
}
