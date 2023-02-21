package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class BenutzerFX {
	
	private Benutzer modellBenutzer;
	private SimpleIntegerProperty benutzerId;
	private SimpleStringProperty name;
	
	public BenutzerFX(Benutzer modellBenutzer) {
		super();
		this.modellBenutzer = modellBenutzer;
		benutzerId = new SimpleIntegerProperty(modellBenutzer.getBenutzerId());
		name = new SimpleStringProperty(modellBenutzer.getName());
	}

	public Benutzer getModellBenutzer() {
		return modellBenutzer;
	}

	public final SimpleIntegerProperty benutzerIdProperty() {
		return this.benutzerId;
	}
	public final int getBenutzerId() {
		return this.benutzerIdProperty().get();
	}
	public final void setBenutzerId(final int benutzerId) {
		this.benutzerIdProperty().set(benutzerId);
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
	
	
	
	
	
}
