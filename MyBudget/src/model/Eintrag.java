package model;

import java.time.LocalDate;

public class Eintrag {

	private int eintragId;
	private LocalDate datum;
	private String titel;
	private double betrag;
	private Benutzer benutzer;
	private Kategorie kategorie;
	private String dauereintrag;			//wenn dauereintrag angelegt wird, wird hier das Intervall hinterlegt
	
	public Eintrag(int eintragId, LocalDate datum, String titel, double betrag,
			Benutzer benutzer, Kategorie kategorie) {
		super();
		this.eintragId = eintragId;
		this.datum = datum;
		this.titel = titel;
		this.betrag = betrag;
		this.benutzer = benutzer;
		this.kategorie = kategorie;
	}
	public Eintrag(LocalDate datum, String titel, double betrag, Benutzer benutzer, Kategorie kategorie) {
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
	public String getDauereintrag() {
		return dauereintrag;
	}
	public void setDauereintrag(String dauereintrag) {
		this.dauereintrag = dauereintrag;
	}
	
	
}
