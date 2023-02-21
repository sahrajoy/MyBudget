package model;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class DauereintraegeList {

	private ArrayList<Dauereintrag> dauereintraege;

	public DauereintraegeList(ArrayList<Dauereintrag> dauereintraege) {
		super();
		this.dauereintraege = dauereintraege;
	}
	public DauereintraegeList() {}
	public DauereintraegeList(String xmlString) {
		if(xmlString == null || xmlString.length() == 0)
			return;
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {
			SAXParser sp = spf.newSAXParser();
			XMLHandler xh = new XMLHandler();
			StringReader sr = new StringReader(xmlString);
			sp.parse(new InputSource(sr), xh);
			dauereintraege = xh.getDauereintraege();
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Dauereintrag> getDauereintraege() {
		return dauereintraege;
	}

	public void setDauereintraege(ArrayList<Dauereintrag> dauereintraege) {
		this.dauereintraege = dauereintraege;
	}
	
	public String toXML() {
		StringBuilder sb = new StringBuilder();
		sb.append("<dauereintraegeliste>");
		for(Dauereintrag einDauereintrag : dauereintraege) 
			sb.append(einDauereintrag.toXML());
		sb.append("</dauereintraegeliste>");
		return sb.toString();
	}
}
