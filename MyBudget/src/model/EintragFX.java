package model;

import java.text.SimpleDateFormat;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class EintragFX {
	
	private Eintrag modellEintrag;
	private SimpleDateFormat datum;
	private SimpleStringProperty titel;
	private SimpleDoubleProperty betrag;
	private SimpleIntegerProperty benutzer;
	

	public EintragFX(Eintrag modellEintrag) {
		super();
		this.modellEintrag = modellEintrag;
	}
	
	
}
