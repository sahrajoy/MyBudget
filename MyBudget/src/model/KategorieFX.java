package model;

import java.util.ArrayList;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class KategorieFX {

	private Kategorie modellKategorie;
	private SimpleIntegerProperty kategorieId;
	private SimpleStringProperty name;
	private SimpleBooleanProperty favorite;
	
	public KategorieFX(Kategorie modellKategorie) {
		super();
		this.modellKategorie = modellKategorie;
		kategorieId = new SimpleIntegerProperty(modellKategorie.getKategorieId());
		name = new SimpleStringProperty(modellKategorie.getName());
		favorite = new SimpleBooleanProperty(modellKategorie.isFavorite());
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

	@Override
	public String toString() {
		return name.get();
	}

}
