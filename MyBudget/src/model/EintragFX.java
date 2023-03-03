package model;

import java.time.LocalDate;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class EintragFX {
	
	private Eintrag modellEintrag;
	private SimpleObjectProperty<LocalDate> datum;
	private SimpleStringProperty titel;
	private SimpleDoubleProperty betrag;
	private SimpleObjectProperty<Benutzer> benutzer;
	private SimpleObjectProperty<Kategorie> kategorie;

	public EintragFX(Eintrag modellEintrag) {
		super();
		this.modellEintrag = modellEintrag;
		datum = new SimpleObjectProperty<>(modellEintrag.getDatum());
		titel = new SimpleStringProperty(modellEintrag.getTitel());
		betrag = new SimpleDoubleProperty(modellEintrag.getBetrag());
		benutzer = new SimpleObjectProperty<>(modellEintrag.getBenutzer());
		kategorie = new SimpleObjectProperty<>(modellEintrag.getKategorie());
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
	
	@Override
	public String toString() {
		return "EintragFX [modellEintrag=" + modellEintrag + ", datum=" + datum + ", titel=" + titel + ", betrag="
				+ betrag + ", benutzer=" + benutzer + ", kategorie=" + kategorie + "]";
	}

}
