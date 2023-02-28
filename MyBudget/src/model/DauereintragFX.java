package model;

import java.text.SimpleDateFormat;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class DauereintragFX {

	private Dauereintrag modellDauereintrag;
	private SimpleDateFormat datum;
	private SimpleStringProperty titel;
	private SimpleDoubleProperty betrag;
	private SimpleStringProperty dauereintrag;				
	private SimpleDateFormat dauereintragEnde;
	private SimpleDoubleProperty eintrege;		//Summe aller Einträge
	
	
	public DauereintragFX(Dauereintrag modellDauereintrag) {
		super();
		this.modellDauereintrag = modellDauereintrag;
	}

}
