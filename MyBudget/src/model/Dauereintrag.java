package model;

import java.time.LocalDate;

public class Dauereintrag {

	private int dauereintragId;
	private LocalDate naechsteFaelligkeit;
	private String titel;
	private double betrag;
	private LocalDate endedatum;
	private Intervall intervall;
	private Benutzer benutzer;
	private Kategorie kategorie;
	
	public Dauereintrag(int dauereintragId, LocalDate naechsteFaelligkeit, String titel, double betrag,
			LocalDate endedatum, Intervall intervall, Benutzer benutzer, Kategorie kategorie) {
		super();
		this.dauereintragId = dauereintragId;
		this.naechsteFaelligkeit = naechsteFaelligkeit;
		this.titel = titel;
		this.betrag = betrag;
		this.endedatum = endedatum;
		this.intervall = intervall;
		this.benutzer = benutzer;
		this.kategorie = kategorie;
	}

	public int getDauereintragId() {
		return dauereintragId;
	}

	public void setDauereintragId(int dauereintragId) {
		this.dauereintragId = dauereintragId;
	}

	public LocalDate getNaechsteFaelligkeit() {
		return naechsteFaelligkeit;
	}

	public void setNaechsteFaelligkeit(LocalDate naechsteFaelligkeit) {
		this.naechsteFaelligkeit = naechsteFaelligkeit;
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

	public LocalDate getEndedatum() {
		return endedatum;
	}

	public void setEndedatum(LocalDate endedatum) {
		this.endedatum = endedatum;
	}

	public Intervall getIntervall() {
		return intervall;
	}

	public void setIntervall(Intervall intervall) {
		this.intervall = intervall;
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
		return "Dauereintrag [dauereintragId=" + dauereintragId + ", naechsteFaelligkeit=" + naechsteFaelligkeit
				+ ", titel=" + titel + ", betrag=" + betrag + ", endedatum=" + endedatum + ", intervall=" + intervall
				+ ", benutzer=" + benutzer + ", kategorie=" + kategorie + "]";
	}
	
	
}
