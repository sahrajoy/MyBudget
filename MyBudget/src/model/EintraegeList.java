package model;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class EintraegeList {
	private ArrayList<Eintrag> eintraege;

	public EintraegeList(ArrayList<Eintrag> eintraege) {
		super();
		this.eintraege = eintraege;
	}
	public EintraegeList() {}
	public EintraegeList(String xmlString) {
		if(xmlString == null || xmlString.length() == 0)
			return;
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {
			SAXParser sp = spf.newSAXParser();
			XMLHandler xh = new XMLHandler();
			StringReader sr = new StringReader(xmlString);
			sp.parse(new InputSource(sr), xh);
			eintraege = xh.getEintraege();
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Eintrag> getEintraege() {
		return eintraege;
	}

	public void setEintraege(ArrayList<Eintrag> eintraege) {
		this.eintraege = eintraege;
	}
	
	public String toXML() {
		StringBuilder sb = new StringBuilder();
		sb.append("<eintraegeliste>");
		for(Eintrag einEintrag : eintraege) 
			sb.append(einEintrag.toXML());
		sb.append("</eintraegeliste>");
		return sb.toString();
	}
}
