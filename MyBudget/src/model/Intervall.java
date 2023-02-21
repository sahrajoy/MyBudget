package model;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public enum Intervall {
	TAEGLICH("täglich"),
	WOECHENTLICH("wöchentlich"),
	MONATLICH("monatlich"),
	QUARTAL("quartalsweise"),
	JAEHRLICH("jährlich");

	private String iName;

	private Intervall(String iName) {
		if(iName == null || iName.length() == 0)
			return;
		if(!iName.startsWith("<"))
			this.iName = iName;
		else {
			SAXParserFactory spf = SAXParserFactory.newInstance();
			try {
				SAXParser sp = spf.newSAXParser();
				XMLHandler xh = new XMLHandler();
				StringReader sr = new StringReader(iName);
				iName = xh.getIntervall().getIName();
				sp.parse(new InputSource(sr), xh);
			} catch (ParserConfigurationException | SAXException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	private Intervall() {}

	public String getIName() {
		return iName;
	}

	public void setName(String iName) {
		this.iName = iName;
	}
	
	@Override
	public String toString() {
		return "Intervall [iName=" + iName + "]"; 
	}

	public String toXML() {
		return "<intervall><iName>" + iName + "</iName></intervall>";
	}

}
	