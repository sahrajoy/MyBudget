package model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class KategorieFX {

	private Kategorie modellKategorie;
	private SimpleBooleanProperty einnahmeOderAusgabe;
	private SimpleIntegerProperty kategorieId;
	private SimpleStringProperty name;
	private SimpleBooleanProperty favorite;
	private SimpleDoubleProperty summeEintraege;
	
	public KategorieFX(Kategorie modellKategorie) {
		super();
		this.modellKategorie = modellKategorie;
		einnahmeOderAusgabe = new SimpleBooleanProperty(modellKategorie.isEinnahmeOderAusgabe());
		kategorieId = new SimpleIntegerProperty(modellKategorie.getKategorieId());
		name = new SimpleStringProperty(modellKategorie.getName());
		favorite = new SimpleBooleanProperty(modellKategorie.isFavorite());
		summeEintraege = new SimpleDoubleProperty(modellKategorie.getSummeEintraege());
	}

	public final SimpleBooleanProperty einnahmeOderAusgabeProperty() {
		return this.einnahmeOderAusgabe;
	}
	public final boolean isEinnahmeOderAusgabe() {
		return this.einnahmeOderAusgabeProperty().get();
	}
	public final void setEinnahmeOderAusgabe(final boolean einnahmeOderAusgabe) {
		this.einnahmeOderAusgabeProperty().set(einnahmeOderAusgabe);
	}
	
	public final SimpleIntegerProperty kategorieIdProperty() {
		return this.kategorieId;
	}
	public final int getKategorieId() {
		return this.kategorieIdProperty().get();
	}
	public final void setKategorieId(final int kategorieId) {
		this.kategorieIdProperty().set(kategorieId);
	}
	
	public final SimpleStringProperty nameProperty() {
		return this.name;
	}
	public final String getName() {
		return this.nameProperty().get();
	}
	public final void setName(final String name) {
		this.nameProperty().set(name);
	}
	
	public final SimpleBooleanProperty favoriteProperty() {
		return this.favorite;
	}
	public final boolean isFavorite() {
		return this.favoriteProperty().get();
	}
	public final void setFavorite(final boolean favorite) {
		this.favoriteProperty().set(favorite);
	}

	public final SimpleDoubleProperty summeEintraegeProperty() {
		return this.summeEintraege;
	}
	public final double getSummeEintraege() {
		return this.summeEintraegeProperty().get();
	}
	public final void setSummeEintraege(final double summeEintraege) {
		this.summeEintraegeProperty().set(summeEintraege);
	}
	
	@Override
	public String toString() {
		return name.get();
	}

}
