package model;

import java.util.ArrayList;

public class Kategorie {

	private int kategorieId;
	private boolean einnahmeOderAusgabe;
	private String name;
	private boolean favorite = false;
	private ArrayList<Eintrag> eintraege = new ArrayList<>();
	private ArrayList<Dauereintrag> dauereintraege = new ArrayList<>();
	
	public Kategorie(int kategorieId, boolean einnahmeOderAusgabe, String name, boolean favorite,
			ArrayList<Eintrag> eintraege, ArrayList<Dauereintrag> dauereintraege) {
		super();
		this.kategorieId = kategorieId;
		this.einnahmeOderAusgabe = einnahmeOderAusgabe;
		this.name = name;
		this.favorite = favorite;
		this.eintraege = eintraege;
		this.dauereintraege = dauereintraege;
	}
	public Kategorie(int kategorieId, boolean einnahmeOderAusgabe, String name, boolean favorite) {
		super();
		this.kategorieId = kategorieId;
		this.einnahmeOderAusgabe = einnahmeOderAusgabe;
		this.name = name;
		this.favorite = favorite;
	}
	public Kategorie(boolean einnahmeOderAusgabe, String name, boolean favorite) {
		super();
		this.einnahmeOderAusgabe = einnahmeOderAusgabe;
		this.name = name;
		this.favorite = favorite;
	}
	public Kategorie() {}
	
	public double getSummeEintraege() {
		double summe = 0;
		for(Eintrag einEintrag : eintraege)
			summe += einEintrag.getBetrag();
		return summe;
	}
	
	public int getKategorieId() {
		return kategorieId;
	}
	public void setKategorieId(int kategorieId) {
		this.kategorieId = kategorieId;
	}
	public boolean isEinnahmeOderAusgabe() {
		return einnahmeOderAusgabe;
	}
	public void setEinnahmeOderAusgabe(boolean einnahmeOderAusgabe) {
		this.einnahmeOderAusgabe = einnahmeOderAusgabe;
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
	public ArrayList<Dauereintrag> getDauereintraege() {
		return dauereintraege;
	}
	public void setDauereintraege(ArrayList<Dauereintrag> dauereintraege) {
		this.dauereintraege = dauereintraege;
	}
	@Override
	public String toString() {
		return "Kategorie [kategorieId=" + kategorieId + ", einnahmeOderAusgabe=" + einnahmeOderAusgabe + ", name="
				+ name + ", favorite=" + favorite + ", eintraege=" + eintraege + ", dauereintraege=" + dauereintraege
				+ "]";
	}
	
}
