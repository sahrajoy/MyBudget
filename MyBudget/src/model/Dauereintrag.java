package model;

import java.time.LocalDate;

public class Dauereintrag {

	private int dauereintragId;
	private LocalDate naechsteFaelligkeit;
	private String deTitel;
	private double deBetrag;
	private LocalDate enddatum;
	private Intervall intervall;
	private Benutzer deBenutzer;
	private Kategorie deKategorie;
	
	public Dauereintrag(int dauereintragId, LocalDate naechsteFaelligkeit, String deTitel, double deBetrag,
			LocalDate enddatum, Intervall intervall, Benutzer deBenutzer, Kategorie deKategorie) {
		super();
		this.dauereintragId = dauereintragId;
		this.naechsteFaelligkeit = naechsteFaelligkeit;
		this.deTitel = deTitel;
		this.deBetrag = deBetrag;
		this.enddatum = enddatum;
		this.intervall = intervall;
		this.deBenutzer = deBenutzer;
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

	public String getTitel() {
		return deTitel;
	}

	public void setTitel(String deTitel) {
		this.deTitel = deTitel;
	}

	public double getBetrag() {
		return deBetrag;
	}

	public void setBetrag(double deBetrag) {
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

	public Benutzer getBenutzer() {
		return deBenutzer;
	}

	public void setBenutzer(Benutzer deBenutzer) {
		this.deBenutzer = deBenutzer;
	}

	public Kategorie getKategorie() {
		return deKategorie;
	}

	public void setKategorie(Kategorie deKategorie) {
		this.deKategorie = deKategorie;
	}
	
	@Override
	public String toString() {
		return "Dauereintrag [dauereintragId=" + dauereintragId + ", naechsteFaelligkeit=" + naechsteFaelligkeit
				+ ", deTitel=" + deTitel + ", deBetrag=" + deBetrag + ", enddatum=" + enddatum + ", intervall="
				+ intervall + ", deBenutzer=" + deBenutzer + ", deKategorie=" + deKategorie + "]";
	}
	public String toXML() {
		StringBuilder sb = new StringBuilder();
		sb.append("<dauereintrag>");
		sb.append("<dauereintragId>" + dauereintragId + "</dauereintragId>");
		sb.append("<naechsteFaelligkeit>" + naechsteFaelligkeit + "</naechsteFaelligkeit>");
		sb.append("<deTitel>" + deTitel + "</deTitel>");
		sb.append("<deBetrag>" + deBetrag + "</deBetrag>");
		sb.append("<enddatum>" + enddatum + "</enddatum>");
		sb.append(intervall.toXML());
		sb.append(deBenutzer.toXML());
		sb.append(deKategorie.toXML());
		sb.append("</dauereintrag>");
		return sb.toString();
	}
}
