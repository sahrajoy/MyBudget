package model;

import java.time.LocalDate;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class DauereintragFX {

	private Dauereintrag modellDauereintrag;
	private SimpleObjectProperty<LocalDate> naechsteFaelligkeit;
	private SimpleStringProperty titel;
	private SimpleDoubleProperty betrag;
	private SimpleObjectProperty<Benutzer> benutzer;	
	private SimpleObjectProperty<Intervall> intervall;				
	private SimpleObjectProperty<LocalDate> dauereintragEnde;
	
	public DauereintragFX(Dauereintrag modellDauereintrag) {
		super();
		this.modellDauereintrag = modellDauereintrag;
		naechsteFaelligkeit = new SimpleObjectProperty<>(modellDauereintrag.getNaechsteFaelligkeit());
		titel = new SimpleStringProperty(modellDauereintrag.getDeTitel());
		betrag = new SimpleDoubleProperty(modellDauereintrag.getDeBetrag());
		benutzer = new SimpleObjectProperty<>(modellDauereintrag.getDeBenutzer());
		intervall = new SimpleObjectProperty<>(modellDauereintrag.getIntervall());
		dauereintragEnde = new SimpleObjectProperty<>(modellDauereintrag.getEnddatum());
	}

	public final SimpleObjectProperty<LocalDate> naechsteFaelligkeitProperty() {
		return this.naechsteFaelligkeit;
	}
	public final LocalDate getNaechsteFaelligkeit() {
		return this.naechsteFaelligkeitProperty().get();
	}
	public final void setNaechsteFaelligkeit(final LocalDate naechsteFaelligkeit) {
		this.naechsteFaelligkeitProperty().set(naechsteFaelligkeit);
	}
	
	public final SimpleStringProperty titelProperty() {
		return this.titel;
	}
	public final String getTitel() {
		return this.titelProperty().get();
	}
	public final void setTitel(final String titel) {
		this.titelProperty().set(titel);
	}
	
	public final SimpleDoubleProperty betragProperty() {
		return this.betrag;
	}
	public final double getBetrag() {
		return this.betragProperty().get();
	}
	public final void setBetrag(final double betrag) {
		this.betragProperty().set(betrag);
	}
	
	public final SimpleObjectProperty<Benutzer> benutzerProperty() {
		return this.benutzer;
	}
	public final Benutzer getBenutzer() {
		return this.benutzerProperty().get();
	}
	public final void setBenutzer(final Benutzer benutzer) {
		this.benutzerProperty().set(benutzer);
	}
	
	public final SimpleObjectProperty<Intervall> intervallProperty() {
		return this.intervall;
	}
	public final Intervall getIntervall() {
		return this.intervallProperty().get();
	}
	public final void setIntervall(final Intervall intervall) {
		this.intervallProperty().set(intervall);
	}
	
	public final SimpleObjectProperty<LocalDate> dauereintragEndeProperty() {
		return this.dauereintragEnde;
	}
	public final LocalDate getDauereintragEnde() {
		return this.dauereintragEndeProperty().get();
	}
	public final void setDauereintragEnde(final LocalDate dauereintragEnde) {
		this.dauereintragEndeProperty().set(dauereintragEnde);
	}

	@Override
	public String toString() {
		return "DauereintragFX [modellDauereintrag=" + modellDauereintrag + ", naechsteFaelligkeit="
				+ naechsteFaelligkeit + ", titel=" + titel + ", betrag=" + betrag + ", benutzer=" + benutzer
				+ ", intervall=" + intervall + ", dauereintragEnde=" + dauereintragEnde + "]";
	}

}
