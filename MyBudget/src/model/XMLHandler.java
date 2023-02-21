package model;

import java.util.ArrayList;
import java.util.Date;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLHandler extends DefaultHandler{

	private String text;
	private ArrayList<Eintrag> eintraege;
	private Eintrag eintrag;
	private ArrayList<Dauereintrag> dauereintraege;
	private Dauereintrag dauereintrag;
	
	private Intervall intervall;
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		switch(qName.toUpperCase()) {
		case "EINTRAEGELISTE" :			//richtet sich nach den Tags in toXML, muss gleich geschrieben sein
			eintraege = new ArrayList<>();
			break;
		case "EINTRAG" :
			eintrag = new Eintrag();
			break;
		case "DAUEREINTRAEGELISTE" :			
			dauereintraege = new ArrayList<>();
			break;
		case "DAUEREINTRAG" :
			dauereintrag = new Dauereintrag();
			break;
			
			
			
		case "INTERVALL" :
			intervall = new Intervall();
			break;
		}
	}
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		switch(qName.toUpperCase()) {
		case "EINTRAGID" :			
			eintrag.setEintragId(Integer.parseInt(text));
			break;
		case "DATUM" :
			eintrag.setDatum(null);		//LocalDate to String?
			break;
		case "TITEL" :			
			eintrag.setTitel(text);
			break;
		case "BETRAG" :			
			eintrag.setBetrag(Double.parseDouble(text));
			break;
		case "EINTRAG" :
			if(eintraege != null)
				eintraege.add(eintrag);
		case "DAUEREINTRAGID" :			
			dauereintrag.setDauereintragId(Integer.parseInt(text));
			break;
		case "NAECHSTEFAELLIGKEIT" :
			dauereintrag.setNaechsteFaelligkeit(null);		//LocalDate to String?
			break;
		case "DETITEL" :			
			dauereintrag.setTitel(text);
			break;
		case "DEBETRAG" :			
			dauereintrag.setBetrag(Double.parseDouble(text));
			break;
		case "ENDDATUM" :			
			dauereintrag.setEnddatum(null); 		//LocalDate to String?
			break;
			
			
			
		case "INAME" :			
			intervall.setName(text);
			break;		
		}
	}
	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		text = new String(ch, start, length);
	}

	public String getText() {
		return text;
	}

	public ArrayList<Eintrag> getEintraege() {
		return eintraege;
	}

	public Eintrag getEintrag() {
		return eintrag;
	}

	public ArrayList<Dauereintrag> getDauereintraege() {
		return dauereintraege;
	}

	public Dauereintrag getDauereintrag() {
		return dauereintrag;
	}

	public Intervall getIntervall() {
		return intervall;
	}
	
	
	
	
}
