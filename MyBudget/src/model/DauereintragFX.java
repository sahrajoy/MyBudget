package model;

import java.time.LocalDate;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class DauereintragFX {

	private Dauereintrag modellDauereintrag;
	private SimpleObjectProperty<LocalDate> naechsteFaelligkeit;
	private SimpleObjectProperty<Kategorie> kategorie;	
	private SimpleStringProperty kategorieName;	
	private SimpleStringProperty titel;
	private SimpleDoubleProperty betrag;
	private SimpleObjectProperty<Benutzer> benutzer;	
	private SimpleStringProperty benutzerName;
	private SimpleObjectProperty<Intervall> intervall;				
	private SimpleObjectProperty<LocalDate> dauereintragEnde;
	
	public DauereintragFX(Dauereintrag modellDauereintrag) {
		super();
		this.modellDauereintrag = modellDauereintrag;
		naechsteFaelligkeit = new SimpleObjectProperty<>(modellDauereintrag.getNaechsteFaelligkeit());
		kategorie = new SimpleObjectProperty<>(modellDauereintrag.getDeKategorie());
		kategorieName = new SimpleStringProperty(modellDauereintrag.getDeKategorie().getName());
		titel = new SimpleStringProperty(modellDauereintrag.getDeTitel());
		betrag = new SimpleDoubleProperty(modellDauereintrag.getDeBetrag());
		benutzer = new SimpleObjectProperty<>(modellDauereintrag.getDeBenutzer());
		benutzerName = new SimpleStringProperty(modellDauereintrag.getDeBenutzer().getName());
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

	public final SimpleStringProperty benutzerNameProperty() {
		return this.benutzerName;
	}
	public final String getBenutzerName() {
		return this.benutzerNameProperty().get();
	}
	public final void setBenutzerName(final String benutzerName) {
		this.benutzerNameProperty().set(benutzerName);
	}

	public final SimpleObjectProperty<Kategorie> kategorieProperty() {
		return this.kategorie;
	}
	public final Kategorie getKategorie() {
		return this.kategorieProperty().get();
	}
	public final void setKategorie(final Kategorie kategorie) {
		this.kategorieProperty().set(kategorie);
	}
	
	public final SimpleStringProperty kategorieNameProperty() {
		return this.kategorieName;
	}
	public final String getKategorieName() {
		return this.kategorieNameProperty().get();
	}
	public final void setKategorieName(final String kategorieName) {
		this.kategorieNameProperty().set(kategorieName);
	}
	
}
