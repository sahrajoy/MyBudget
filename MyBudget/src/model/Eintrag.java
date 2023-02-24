package model;

import java.time.LocalDate;

public class Eintrag {

	private int eintragId;
	private boolean einnahmeOderAusgabe;
	private LocalDate datum;
	private String titel;
	private double betrag;
	private Benutzer benutzer;
	private Kategorie kategorie;
	
	public Eintrag(int eintragId, boolean einnahmeOderAusgabe, LocalDate datum, String titel, double betrag,
			Benutzer benutzer, Kategorie kategorie) {
		super();
		this.eintragId = eintragId;
		this.einnahmeOderAusgabe = einnahmeOderAusgabe;
		this.datum = datum;
		this.titel = titel;
		this.betrag = betrag;
		this.benutzer = benutzer;
		this.kategorie = kategorie;
	}
	public Eintrag(boolean einnahmeOderAusgabe, LocalDate datum, String titel, double betrag, BenutzerFX benutzerFX) {
		super();
		this.einnahmeOderAusgabe = einnahmeOderAusgabe;
		this.datum = datum;
		this.titel = titel;
		this.betrag = betrag;
		this.benutzer = benutzer;
//		this.kategorie = kategorie;
	}
	public Eintrag() {}

	public int getEintragId() {
		return eintragId;
	}
	public void setEintragId(int eintragId) {
		this.eintragId = eintragId;
	}
	public boolean isEinnahmeOderAusgabe() {
		return einnahmeOderAusgabe;
	}
	public void setEinnahmeOderAusgabe(boolean einnahmeOderAusgabe) {
		this.einnahmeOderAusgabe = einnahmeOderAusgabe;
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

	@Override
	public String toString() {
		return "Eintrag [eintragId=" + eintragId + ", einnahmeOderAusgabe=" + einnahmeOderAusgabe + ", datum=" + datum
				+ ", titel=" + titel + ", betrag=" + betrag + ", benutzer=" + benutzer + ", kategorie=" + kategorie
				+ "]";
	}
	
}
