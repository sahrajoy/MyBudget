package model;

public class Kategorie {

	private int kategorieId;
	private boolean einnahmeOderAusgabe;
	private String name;
	private boolean favorite = false;
	private double summeEintraege;
	
	//Konstruktor um aus Datenbank auszulesen
	public Kategorie(int kategorieId, boolean einnahmeOderAusgabe, String name, boolean favorite) {
		super();
		this.kategorieId = kategorieId;
		this.einnahmeOderAusgabe = einnahmeOderAusgabe;
		this.name = name;
		this.favorite = favorite;
	}
	//Konstruktor zum Kategorie speichern
	public Kategorie(boolean einnahmeOderAusgabe, String name, boolean favorite) {
		super();
		this.einnahmeOderAusgabe = einnahmeOderAusgabe;
		this.name = name;
		this.favorite = favorite;
	}
	//Konstruktor für die Datenbank Methode readDauereintraege(), um nur die ID mitzugeben
	public Kategorie(int kategorieId) {
		super();
		this.kategorieId = kategorieId;
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
	public double getSummeEintraege() {
		return summeEintraege;
	}
	public void setSummeEintraege(double summeEintraege) {
		this.summeEintraege = summeEintraege;
	}




	
}
