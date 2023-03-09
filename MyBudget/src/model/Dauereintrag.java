package model;

import java.time.LocalDate;

public class Dauereintrag {

	private int dauereintragId;
	private LocalDate naechsteFaelligkeit;
	private String deTitel;
	private double deBetrag;
	private Benutzer deBenutzer;
	private Intervall intervall;
	private LocalDate enddatum;
	private Kategorie deKategorie;
	
	public Dauereintrag(int dauereintragId, LocalDate naechsteFaelligkeit, String deTitel, double deBetrag,
			Benutzer deBenutzer, Intervall intervall, LocalDate enddatum, Kategorie deKategorie) {
		super();
		this.dauereintragId = dauereintragId;
		this.naechsteFaelligkeit = naechsteFaelligkeit;
		this.deTitel = deTitel;
		this.deBetrag = deBetrag;
		this.deBenutzer = deBenutzer;
		this.intervall = intervall;
		this.enddatum = enddatum;
		this.deKategorie = deKategorie;
	}
	public Dauereintrag(LocalDate naechsteFaelligkeit, String deTitel, double deBetrag, Benutzer deBenutzer,
			Intervall intervall, LocalDate enddatum, Kategorie deKategorie) {
		super();
		this.naechsteFaelligkeit = naechsteFaelligkeit;
		this.deTitel = deTitel;
		this.deBetrag = deBetrag;
		this.deBenutzer = deBenutzer;
		this.intervall = intervall;
		this.enddatum = enddatum;
		this.deKategorie = deKategorie;
	}
	public Dauereintrag() {}
	
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
	public Benutzer getDeBenutzer() {
		return deBenutzer;
	}
	public void setDeBenutzer(Benutzer deBenutzer) {
		this.deBenutzer = deBenutzer;
	}
	public Intervall getIntervall() {
		return intervall;
	}
	public void setIntervall(Intervall intervall) {
		this.intervall = intervall;
	}
	public LocalDate getEnddatum() {
		return enddatum;
	}
	public void setEnddatum(LocalDate enddatum) {
		this.enddatum = enddatum;
	}
	public Kategorie getDeKategorie() {
		return deKategorie;
	}
	public void setDeKategorie(Kategorie deKategorie) {
		this.deKategorie = deKategorie;
	}
	@Override
	public String toString() {
		return "Dauereintrag [dauereintragId=" + dauereintragId + ", naechsteFaelligkeit=" + naechsteFaelligkeit
				+ ", deTitel=" + deTitel + ", deBetrag=" + deBetrag + ", deBenutzer=" + deBenutzer + ", intervall="
				+ intervall + ", enddatum=" + enddatum + ", deKategorie=" + deKategorie + "]";
	}


	
}
