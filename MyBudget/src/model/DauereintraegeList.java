package model;

import java.util.ArrayList;

public class DauereintraegeList {

	private ArrayList<Dauereintrag> dauereintraege;

	public DauereintraegeList(ArrayList<Dauereintrag> dauereintraege) {
		super();
		this.dauereintraege = dauereintraege;
	}
	public DauereintraegeList() {}

	public ArrayList<Dauereintrag> getDauereintraege() {
		return dauereintraege;
	}

	public void setDauereintraege(ArrayList<Dauereintrag> dauereintraege) {
		this.dauereintraege = dauereintraege;
	}
	
}
