package model;

import java.time.LocalDate;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;

public class EintragFX {
	
	private Eintrag modellEintrag;
	private SimpleIntegerProperty eintragId;
	private SimpleObjectProperty<LocalDate> datum;
	private SimpleStringProperty titel;
	private SimpleDoubleProperty betrag;
	private SimpleObjectProperty<Benutzer> benutzer;
	private SimpleStringProperty benutzerName;
	private SimpleObjectProperty<Kategorie> kategorie;
	private SimpleObjectProperty<Intervall> intervall;

	public EintragFX(Eintrag modellEintrag) {
		super();
		this.modellEintrag = modellEintrag;
		eintragId = new SimpleIntegerProperty(modellEintrag.getEintragId());
		datum = new SimpleObjectProperty<>(modellEintrag.getDatum());
		titel = new SimpleStringProperty(modellEintrag.getTitel());
		betrag = new SimpleDoubleProperty(modellEintrag.getBetrag());
		benutzer = new SimpleObjectProperty<>(modellEintrag.getBenutzer());
		benutzerName = new SimpleStringProperty(modellEintrag.getBenutzer().getName());
		kategorie = new SimpleObjectProperty<>(modellEintrag.getKategorie());
		intervall = new SimpleObjectProperty<>(modellEintrag.getIntervall());
	}

	public final SimpleObjectProperty<LocalDate> datumProperty() {
		return this.datum;
	}
	public final LocalDate getDatum() {
		return this.datumProperty().get();
	}
	public final void setDatum(final LocalDate datum) {
		this.datumProperty().set(datum);
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
	
	public final SimpleObjectProperty<Kategorie> kategorieProperty() {
		return this.kategorie;
	}
	public final Kategorie getKategorie() {
		return this.kategorieProperty().get();
	}
	public final void setKategorie(final Kategorie kategorie) {
		this.kategorieProperty().set(kategorie);
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

	public final SimpleIntegerProperty eintragIdProperty() {
		return this.eintragId;
	}
	public final int getEintragId() {
		return this.eintragIdProperty().get();
	}
	public final void setEintragId(final int eintragId) {
		this.eintragIdProperty().set(eintragId);
	}

	public final SimpleObjectProperty<Intervall> eintragProperty() {
		return this.intervall;
	}
	public final Intervall getIntervall() {
		return this.eintragProperty().get();
	}
	public final void setIntervall(final Intervall intervall) {
		this.eintragProperty().set(intervall);
	}
	
	
	
}
