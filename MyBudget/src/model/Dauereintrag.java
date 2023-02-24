package model;

import java.time.LocalDate;

public class Dauereintrag {

	private int dauereintragId;
	private boolean einnahmeOderAusgabe;
	private LocalDate naechsteFaelligkeit;
	private String deTitel;
	private double deBetrag;
	private Benutzer deBenutzer;
	private Intervall intervall;
	private LocalDate enddatum;
	private Kategorie deKategorie;
	
	public Dauereintrag(int dauereintragId, boolean einnahmeOderAusgabe, LocalDate naechsteFaelligkeit, String deTitel,
			double deBetrag, LocalDate enddatum, Intervall intervall, Benutzer deBenutzer, Kategorie deKategorie) {
		super();
		this.dauereintragId = dauereintragId;
		this.einnahmeOderAusgabe = einnahmeOderAusgabe;
		this.naechsteFaelligkeit = naechsteFaelligkeit;
		this.deTitel = deTitel;
		this.deBetrag = deBetrag;
		this.enddatum = enddatum;
		this.intervall = intervall;
		this.deBenutzer = deBenutzer;
		this.deKategorie = deKategorie;
	}
	public Dauereintrag(boolean einnahmeOderAusgabe, LocalDate naechsteFaelligkeit, String deTitel, double deBetrag,
			BenutzerFX deBenutzerFX, Intervall intervall, LocalDate enddatum) {
		super();
		this.einnahmeOderAusgabe = einnahmeOderAusgabe;
		this.naechsteFaelligkeit = naechsteFaelligkeit;
		this.deTitel = deTitel;
		this.deBetrag = deBetrag;
		this.deBenutzer = deBenutzerFX;
		this.intervall = intervall;
		this.enddatum = enddatum;
	}
	public Dauereintrag() {}
	
	public int getDauereintragId() {
		return dauereintragId;
	}
	public void setDauereintragId(int dauereintragId) {
		this.dauereintragId = dauereintragId;
	}
	public boolean isEinnahmeOderAusgabe() {
		return einnahmeOderAusgabe;
	}
	public void setEinnahmeOderAusgabe(boolean einnahmeOderAusgabe) {
		this.einnahmeOderAusgabe = einnahmeOderAusgabe;
	}
	public LocalDate getNaechsteFaelligkeit() {
		return naechsteFaelligkeit;
	}
	public void setNaechsteFaelligkeit(LocalDate naechsteFaelligkeit) {
		this.naechsteFaelligkeit = naechsteFaelligkeit;
	}
	public String getDeTitel() {
		return deTitel;
	}
	public void setDeTitel(String deTitel) {
		this.deTitel = deTitel;
	}
	public double getDeBetrag() {
		return deBetrag;
	}
	public void setDeBetrag(double deBetrag) {
		this.deBetrag = deBetrag;
	}
	public LocalDate getEnddatum() {
		return enddatum;
	}
	public void setEnddatum(LocalDate enddatum) {
		this.enddatum = enddatum;
	}
	public Intervall getIntervall() {
		return intervall;
	}
	public void setIntervall(Intervall intervall) {
		this.intervall = intervall;
	}
	public Benutzer getDeBenutzer() {
		return deBenutzer;
	}
	public void setDeBenutzer(Benutzer deBenutzer) {
		this.deBenutzer = deBenutzer;
	}
	public Kategorie getDeKategorie() {
		return deKategorie;
	}
	public void setDeKategorie(Kategorie deKategorie) {
		this.deKategorie = deKategorie;
	}
	@Override
	public String toString() {
		return "Dauereintrag [dauereintragId=" + dauereintragId + ", einnahmeOderAusgabe=" + einnahmeOderAusgabe
				+ ", naechsteFaelligkeit=" + naechsteFaelligkeit + ", deTitel=" + deTitel + ", deBetrag=" + deBetrag
				+ ", enddatum=" + enddatum + ", intervall=" + intervall + ", deBenutzer=" + deBenutzer
				+ ", deKategorie=" + deKategorie + "]";
	}
	
	
	
}
