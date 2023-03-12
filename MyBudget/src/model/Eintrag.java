package model;

import java.time.LocalDate;

public class Eintrag {

	private int eintragId;
	private LocalDate datum;
	private String titel;
	private double betrag;
	private Benutzer benutzer;
	private Kategorie kategorie;
	private Intervall intervall;			
	
	//Konstruktor um aus der Datenbank auszulesen
	public Eintrag(int eintragId, LocalDate datum, String titel, double betrag,
			Benutzer benutzer, Kategorie kategorie, Intervall intervall) {
		super();
		this.eintragId = eintragId;
		this.datum = datum;
		this.titel = titel;
		this.betrag = betrag;
		this.benutzer = benutzer;
		this.kategorie = kategorie;
		this.intervall = intervall;
	}
	//Konstruktor um Eintraege über die dauereintraegeAusfuehren() Methode zu speichern
	public Eintrag(LocalDate datum, String titel, double betrag,
			Benutzer benutzer, Kategorie kategorie, Intervall intervall) {
		super();
		this.datum = datum;
		this.titel = titel;
		this.betrag = betrag;
		this.benutzer = benutzer;
		this.kategorie = kategorie;
		this.intervall = intervall;
	}
	//Konstruktor um Einträge über die Eingabezeilen zu speichern
	public Eintrag(LocalDate datum, String titel, double betrag,
			Benutzer benutzer, Kategorie kategorie) {
		super();
		this.datum = datum;
		this.titel = titel;
		this.betrag = betrag;
		this.benutzer = benutzer;
		this.kategorie = kategorie;
	}
	public Eintrag() {}

	public int getEintragId() {
		return eintragId;
	}
	public void setEintragId(int eintragId) {
		this.eintragId = eintragId;
	}
	public LocalDate getDatum() {
		return datum;
	}
	public void setDatum(LocalDate datum) {
		this.datum = datum;
	}
	public String getTitel() {
		return titel;
	}
	public void setTitel(String titel) {
		this.titel = titel;
	}
	public double getBetrag() {
		return betrag;
	}
	public void setBetrag(double betrag) {
		this.betrag = betrag;
	}
	public Benutzer getBenutzer() {
		return benutzer;
	}
	public void setBenutzer(Benutzer benutzer) {
		this.benutzer = benutzer;
	}
	public Kategorie getKategorie() {
		return kategorie;
	}
	public void setKategorie(Kategorie kategorie) {
		this.kategorie = kategorie;
	}
	public Intervall getIntervall() {
		return intervall;
	}
	public void setIntervall(Intervall intervall) {
		this.intervall = intervall;
	}

	
	
}
