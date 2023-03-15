package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;

public class Datenbank {
	private static final String DB_LOCATION = "C:\\Users\\sahra\\git\\MyBudget\\Temp\\Datenbank";
	private static final String CONNECTION_URL = "jdbc:derby:" + DB_LOCATION + ";create=true";
	
	//Benutzer Tabelle
	private static final String BENUTZER_TABLE = "Benutzer";
	private static final String BENUTZER_ID = "BenutzerId";
	private static final String BENUTZER_NAME = "BenutzerName";
	
	//Kategorie Tabelle
	private static final String KATEGORIE_TABLE = "Kategorie";
	private static final String KATEGORIE_ID = "KategorieId";
	private static final String KATEGORIE_EINNAHMEODERAUSGABE = "KategorieEinnahmeOderAusgabe";
	private static final String KATEGORIE_NAME = "KategorieName";
	private static final String KATEGORIE_FAVORITE = "KategorieFavorite";
	//Kategorie berechnete Werte
	private static final String KATEGORIE_SUMMEEINTRAEGE = "KategorieSummeEintraege";
	
	//Eintrag Tabelle
	private static final String EINTRAG_TABLE = "Eintrag";
	private static final String EINTRAG_ID = "EintragId";
	private static final String EINTRAG_DATUM = "EintragDatum";
	private static final String EINTRAG_TITEL = "EintragTitel";
	private static final String EINTRAG_BETRAG = "EintragBetrag";
	private static final String EINTRAG_BENUTZERID = "EintragBenutzerId";
	private static final String EINTRAG_KATEGORIEID = "EintragKategorieId";
	private static final String EINTRAG_DAUEREINTRAGINTERVALL = "EintragDauereintragIntervall";
	
	//Dauereinträge Tabelle
	private static final String DAUEREINTRAG_TABLE = "Dauereintrag";
	private static final String DAUEREINTRAG_ID = "DauereintragId";
	private static final String DAUEREINTRAG_NAECHSTEFAELLIGKEIT = "DauereintragNaechsteFaelligkeit";
	private static final String DAUEREINTRAG_TITEL = "DauereintragTitel";
	private static final String DAUEREINTRAG_BETRAG = "DauereintragBetrag";
	private static final String DAUEREINTRAG_BENUTZERID = "DauereintragBenutzerId";
	private static final String DAUEREINTRAG_INTERVALL = "DauereintragIntervall";
	private static final String DAUEREINTRAG_ENDEDATUM = "DauereintragEndedatum";
	private static final String DAUEREINTRAG_KATEGORIEID = "DauereintragKategorieId";

	//TABELLENERSTELLEN
	public static void createBenutzerTable() throws SQLException{		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(CONNECTION_URL);
			stmt = conn.createStatement();
			rs = conn.getMetaData().getTables(null, null, BENUTZER_TABLE.toUpperCase(), new String[] {"TABLE"}); 	//um zu prüfen ob die Tabelle schon exestiert	
			if(rs.next()) {	
				return;		//wenn existiert
			}
			String ct = "CREATE TABLE " + BENUTZER_TABLE + " (" + 
					BENUTZER_ID + " INTEGER GENERATED ALWAYS AS IDENTITY," +
					BENUTZER_NAME + " VARCHAR(200)," +
					"PRIMARY KEY(" + BENUTZER_ID + "))";
			stmt.executeUpdate(ct);
			
		}
		catch(SQLException e) {
			throw e;
		}
		finally {
			try {
				if(stmt != null) 
					stmt.close();
				if(conn != null)
					conn.close();
			}
			catch(SQLException e) {
				throw e;
			}
		}
		insertBenutzer(new Benutzer("HAUSHALT"));
	}
	public static void createKategorieTable() throws SQLException{		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(CONNECTION_URL);
			stmt = conn.createStatement();
			rs = conn.getMetaData().getTables(null, null, KATEGORIE_TABLE.toUpperCase(), new String[] {"TABLE"}); 		
			if(rs.next()) {			
				return;
			}
			String ct = "CREATE TABLE " + KATEGORIE_TABLE + " (" + 
					KATEGORIE_ID + " INTEGER GENERATED ALWAYS AS IDENTITY," +
					KATEGORIE_EINNAHMEODERAUSGABE + " BOOLEAN," +
					KATEGORIE_NAME + " VARCHAR(200)," +
					KATEGORIE_FAVORITE + " BOOLEAN," +
					"PRIMARY KEY(" + KATEGORIE_ID + "))";
			stmt.executeUpdate(ct);
		}
		catch(SQLException e) {
			throw e;
		}
		finally {
			try {
				if(stmt != null) 
					stmt.close();
				if(conn != null)
					conn.close();
			}
			catch(SQLException e) {
				throw e;
			}
		}
	}
	public static void createEintragTable() throws SQLException{
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(CONNECTION_URL);
			stmt = conn.createStatement();
			rs = conn.getMetaData().getTables(null, null, EINTRAG_TABLE.toUpperCase(), new String[] {"TABLE"}); 		
			if(rs.next()) {			
				return;
			}
			String ct = "CREATE TABLE " + EINTRAG_TABLE + " (" + 
					EINTRAG_ID + " INTEGER GENERATED ALWAYS AS IDENTITY," +
					EINTRAG_DATUM + " DATE," +
					EINTRAG_TITEL + " VARCHAR(200)," +
					EINTRAG_BETRAG +  " DECIMAL," +
					EINTRAG_BENUTZERID + " INTEGER," +
					EINTRAG_KATEGORIEID + " INTEGER," +
					EINTRAG_DAUEREINTRAGINTERVALL + " VARCHAR(200)," +
					"PRIMARY KEY(" + EINTRAG_ID + ")," +
					"FOREIGN KEY(" + EINTRAG_BENUTZERID + ") REFERENCES " + BENUTZER_TABLE + "(" + BENUTZER_ID + ")," +
					"FOREIGN KEY(" + EINTRAG_KATEGORIEID + ") REFERENCES " + KATEGORIE_TABLE + "(" + KATEGORIE_ID + "))";
			stmt.executeUpdate(ct);
		}
		catch(SQLException e) {
			throw e;
		}
		finally {
			try {
				if(stmt != null) 
					stmt.close();
				if(conn != null)
					conn.close();
			}
			catch(SQLException e) {
				throw e;
			}
		}
	}
	public static void createDauereintragTable() throws SQLException{ 
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(CONNECTION_URL);
			stmt = conn.createStatement();
			rs = conn.getMetaData().getTables(null, null, DAUEREINTRAG_TABLE.toUpperCase(), new String[] {"TABLE"}); 		
			if(rs.next()) {			
				return;
			}
			String ct = "CREATE TABLE " + DAUEREINTRAG_TABLE + " (" + 
					DAUEREINTRAG_ID + " INTEGER GENERATED ALWAYS AS IDENTITY," +
					DAUEREINTRAG_NAECHSTEFAELLIGKEIT + " DATE," +
					DAUEREINTRAG_TITEL + " VARCHAR(200)," +
					DAUEREINTRAG_BETRAG +  " DECIMAL," +
					DAUEREINTRAG_BENUTZERID + " INTEGER," +
					DAUEREINTRAG_INTERVALL + " VARCHAR(200)," +				
					DAUEREINTRAG_ENDEDATUM + " DATE," +
					DAUEREINTRAG_KATEGORIEID + " INTEGER," +
					"PRIMARY KEY(" + DAUEREINTRAG_ID + ")," +
					"FOREIGN KEY(" + DAUEREINTRAG_BENUTZERID + ") REFERENCES " + BENUTZER_TABLE + "(" + BENUTZER_ID + ")," +
					"FOREIGN KEY(" + DAUEREINTRAG_KATEGORIEID + ") REFERENCES " + KATEGORIE_TABLE + "(" + KATEGORIE_ID + "))";
			stmt.executeUpdate(ct);
		}
		catch(SQLException e) {
			throw e;
		}
		finally {
			try {
				if(stmt != null) 
					stmt.close();
				if(conn != null)
					conn.close();
			}
			catch(SQLException e) {
				throw e;
			}
		}
	}
	
	//DATEN AUSLESEN
	//Benutzer auslesen
	public static ArrayList<Benutzer> readBenutzer() throws SQLException{
			return readBenutzer(null);
	}
	public static ArrayList<Benutzer> readBenutzer(String benutzerName) throws SQLException{				
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<Benutzer> alBenutzer = new ArrayList<>();
		String select = "SELECT * FROM " + BENUTZER_TABLE;
		if(benutzerName != null)
			select += " WHERE " + BENUTZER_NAME + "=?";
		try {
			conn = DriverManager.getConnection(CONNECTION_URL);
			stmt = conn.prepareStatement(select);
			if(benutzerName != null)
				stmt.setString(1, benutzerName);
			rs = stmt.executeQuery();
			while(rs.next())
				alBenutzer.add(new Benutzer(rs.getInt(BENUTZER_ID), rs.getString(BENUTZER_NAME)));
			rs.close();
		}
		catch(SQLException e) {
			throw e;
		}
		finally {
			try {
				if(stmt != null) 
					stmt.close();
				if(conn != null)
					conn.close();
			}
			catch(SQLException e) {
				throw e;
			}
		}
		return alBenutzer;
	}
	//ID von HAUSHALT zurückgeben
	public static int getHaushaltId() throws SQLException {
		// Hier eine Abfrage auf die Benutzertabelle machen
		// um die Id des Eintrags "Haushalt" zu bekommen und die ID zurückgeben
		// (SELECT BENUTZER_ID FROM BENUTZER_TABLE WHERE BENUTZER_NAME = "Haushalt") 
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int haushaltId = 0;
		String select = "SELECT * FROM " + BENUTZER_TABLE + " WHERE " + BENUTZER_NAME + "=?";
		try {
			conn = DriverManager.getConnection(CONNECTION_URL);
			stmt = conn.prepareStatement(select);
			stmt.setString(1, "HAUSHALT");
			rs = stmt.executeQuery();
			while(rs.next())
				haushaltId = rs.getInt(BENUTZER_ID);
			rs.close();
		}
		catch(SQLException e) {
			throw e;
		}
		finally {
			try {
				if(stmt != null) 
					stmt.close();
				if(conn != null)
					conn.close();
			}
			catch(SQLException e) {
				throw e;
			}
		}
		return haushaltId;
	}

	//Kategorien auslesen
	public static ArrayList<Kategorie> readKategorie(String einnahmeOderAusgabe) throws SQLException {
		return readKategorie(null, einnahmeOderAusgabe);
	}
	public static ArrayList<Kategorie> readKategorie(String kategorieName, String einnahmeOderAusgabe) throws SQLException{  	
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<Kategorie> alKategorien = new ArrayList<>();
		boolean isEinnahmenParameter = einnahmeOderAusgabe.equals("Einnahmen");
		String select = "SELECT * FROM " + KATEGORIE_TABLE;
		if(kategorieName != null)
			select += " WHERE " + KATEGORIE_NAME + "=?" + KATEGORIE_EINNAHMEODERAUSGABE + "=?";
		else
			select += " WHERE " + KATEGORIE_EINNAHMEODERAUSGABE + "=?";
		try {
			conn = DriverManager.getConnection(CONNECTION_URL);
			stmt = conn.prepareStatement(select);
			if(kategorieName != null) {
				stmt.setString(1, kategorieName);
				stmt.setBoolean(2, isEinnahmenParameter);
			}
			else
				stmt.setBoolean(1, isEinnahmenParameter);
			rs = stmt.executeQuery();
			while(rs.next())
				alKategorien.add(new Kategorie(
						rs.getInt(KATEGORIE_ID), 
						rs.getBoolean(KATEGORIE_EINNAHMEODERAUSGABE), 
						rs.getString(KATEGORIE_NAME), 
						rs.getBoolean(KATEGORIE_FAVORITE)));
			rs.close();
		}
		catch(SQLException e) {
			throw e;
		}
		finally {
			try {
				if(stmt != null) 
					stmt.close();
				if(conn != null)
					conn.close();
			}
			catch(SQLException e) {
				throw e;
			}
		}
		return alKategorien;
	}
	//Kategorien nach Datum auslesen
	public static ArrayList<Kategorie> readKategorieNachDatum(String einnahmeOderAusgabe, LocalDate anfangDatum, LocalDate endeDatum) throws SQLException {
		return readKategorieNachDatum(null, einnahmeOderAusgabe, anfangDatum, endeDatum);
	}
	public static ArrayList<Kategorie> readKategorieNachDatum(String kategorieName, String einnahmeOderAusgabe, LocalDate anfangDatum, LocalDate endeDatum) throws SQLException{  	
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<Kategorie> alKategorien = new ArrayList<>();
		boolean isEinnahmenParameter = einnahmeOderAusgabe.equals("Einnahmen");
		String select = "SELECT * FROM " + KATEGORIE_TABLE
						+ " INNER JOIN " + EINTRAG_TABLE 
						+ " ON " + 	KATEGORIE_TABLE + "." + KATEGORIE_ID + "=" + 
									EINTRAG_TABLE + "." + EINTRAG_KATEGORIEID;
		if(kategorieName != null)
			select += " WHERE " + 	KATEGORIE_NAME + "=? AND " + 
									KATEGORIE_EINNAHMEODERAUSGABE + "=? AND " +
									EINTRAG_DATUM + " >=? AND " + 
									EINTRAG_DATUM + " <=?";
		else
			select += " WHERE " + 	KATEGORIE_EINNAHMEODERAUSGABE + "=? AND " +
									EINTRAG_DATUM + " >=? AND " + 
									EINTRAG_DATUM + " <=?";
		
		try {
			conn = DriverManager.getConnection(CONNECTION_URL);
			stmt = conn.prepareStatement(select);
			if(kategorieName != null) {
				stmt.setString(1, kategorieName);
				stmt.setBoolean(2, isEinnahmenParameter);
				LocalDateTime ldt1 = LocalDateTime.of(anfangDatum, LocalTime.of(0, 0, 0));
				java.sql.Date dateBeginn = new java.sql.Date(ldt1.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()); 
				stmt.setDate(3, dateBeginn);
				LocalDateTime ldt2 = LocalDateTime.of(endeDatum, LocalTime.of(0, 0, 0));
				java.sql.Date dateEnd = new java.sql.Date(ldt2.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()); 
				stmt.setDate(4, dateEnd);
			}
			else {
				stmt.setBoolean(1, isEinnahmenParameter);
				LocalDateTime ldt1 = LocalDateTime.of(anfangDatum, LocalTime.of(0, 0, 0));
				java.sql.Date dateBeginn = new java.sql.Date(ldt1.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()); 
				stmt.setDate(2, dateBeginn);
				LocalDateTime ldt2 = LocalDateTime.of(endeDatum, LocalTime.of(0, 0, 0));
				java.sql.Date dateEnd = new java.sql.Date(ldt2.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()); 
				stmt.setDate(3, dateEnd);
			}
			rs = stmt.executeQuery();
			while(rs.next())
				alKategorien.add(new Kategorie(
						rs.getInt(KATEGORIE_ID), 
						rs.getBoolean(KATEGORIE_EINNAHMEODERAUSGABE), 
						rs.getString(KATEGORIE_NAME), 
						rs.getBoolean(KATEGORIE_FAVORITE)));
			rs.close();
		}
		catch(SQLException e) {
			throw e;
		}
		finally {
			try {
				if(stmt != null) 
					stmt.close();
				if(conn != null)
					conn.close();
			}
			catch(SQLException e) {
				throw e;
			}
		}
		return alKategorien;
	}
	//Kategorie Summe aller Eintrage auslesen
	public static double readKategorieSummeEintraege(String kategorieName, String einnahmeOderAusgabe, int benutzerId) throws SQLException{  		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		double summeEintraege = 0;
		boolean isEinnahmenParameter = einnahmeOderAusgabe.equals("Einnahmen");
		String select =  "SELECT SUM(" + EINTRAG_TABLE + "." + EINTRAG_BETRAG + ") AS " + KATEGORIE_SUMMEEINTRAEGE + " FROM " + EINTRAG_TABLE 
				+ " INNER JOIN " + KATEGORIE_TABLE 
				+ " ON " + 	EINTRAG_TABLE + "." + EINTRAG_KATEGORIEID + "=" + 
							KATEGORIE_TABLE + "." + KATEGORIE_ID;
							
		if(benutzerId != getHaushaltId()) {
		select += " WHERE " + 	EINTRAG_BENUTZERID + "=? AND " + 
					KATEGORIE_NAME + "=? AND " + 
					KATEGORIE_EINNAHMEODERAUSGABE + "=?";
		}
		else {
			select += " WHERE " + 	KATEGORIE_NAME + "=? AND " + 
					KATEGORIE_EINNAHMEODERAUSGABE + "=?";
		}
							
		try {
			conn = DriverManager.getConnection(CONNECTION_URL);
			stmt = conn.prepareStatement(select);
			if(benutzerId != getHaushaltId()) {
				stmt.setInt(1, benutzerId);
				stmt.setString(2, kategorieName);
				stmt.setBoolean(3, isEinnahmenParameter);
			}
			else {
				stmt.setString(1, kategorieName);
				stmt.setBoolean(2, isEinnahmenParameter);
			}
			rs = stmt.executeQuery();
			while(rs.next())
				summeEintraege = rs.getDouble(KATEGORIE_SUMMEEINTRAEGE);
			rs.close();
		}
		catch(SQLException e) {
			throw e;
		}
		finally {
			try {
				if(stmt != null) 
					stmt.close();
				if(conn != null)
					conn.close();
			}
			catch(SQLException e) {
				throw e;
			}
		}
		return summeEintraege;
	}
	//Favorisierte Einnahmen-/Ausgaben Kategorien für einen bestimmten Benutzer auslesen
	public static ArrayList<Kategorie> readFavoritenKategorien(int benutzerId, String einnahmeOderAusgabe) throws SQLException{ 		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<Kategorie> alFavoritenKategorien = new ArrayList<>();
		//INNER JOIN Kategorie-Tabelle mit WHERE Bedingung auf KATEGORIE_EINNAHMEODERAUSGABE, damit nur Dauereinträge ausgegeben werden, welche eine Ausgabe oder Einnahme sind
		boolean isEinnahmenParameter = einnahmeOderAusgabe.equals("Einnahmen");
		String select = "SELECT DISTINCT " + 	KATEGORIE_ID + ", " + 
				KATEGORIE_EINNAHMEODERAUSGABE + ", " +
				KATEGORIE_NAME + ", " +
				KATEGORIE_FAVORITE +
				" FROM " + KATEGORIE_TABLE;
		if(benutzerId != getHaushaltId()) {
			select += 		" INNER JOIN " + EINTRAG_TABLE 
					+ 	" ON " + 	EINTRAG_TABLE + "." + EINTRAG_KATEGORIEID + "=" + 
					KATEGORIE_TABLE + "." + KATEGORIE_ID;
		}
		if(benutzerId != getHaushaltId())
			select += " WHERE " + 	EINTRAG_BENUTZERID + "=? AND " + 
					KATEGORIE_EINNAHMEODERAUSGABE + "=? AND " +
					KATEGORIE_FAVORITE + "=?";
		else
			select += " WHERE " + 	KATEGORIE_EINNAHMEODERAUSGABE + "=? AND " +
					KATEGORIE_FAVORITE + "=?";
		try {
			conn = DriverManager.getConnection(CONNECTION_URL);
			stmt = conn.prepareStatement(select);
			if(benutzerId != getHaushaltId()) {
				stmt.setInt(1, benutzerId);
				stmt.setBoolean(2, isEinnahmenParameter);
				stmt.setBoolean(3, true);
			}
			else {
				stmt.setBoolean(1, isEinnahmenParameter);
				stmt.setBoolean(2, true);
			}
			rs = stmt.executeQuery();
			while(rs.next())
				alFavoritenKategorien.add(new Kategorie(
						rs.getInt(KATEGORIE_ID), 
						rs.getBoolean(KATEGORIE_EINNAHMEODERAUSGABE), 
						rs.getString(KATEGORIE_NAME), 
						rs.getBoolean(KATEGORIE_FAVORITE)));
			rs.close();
		}
		catch(SQLException e) {
			throw e;
		}
		finally {
			try {
				if(stmt != null) 
					stmt.close();
				if(conn != null)
					conn.close();
			}
			catch(SQLException e) {
				throw e;
			}
		}
		return alFavoritenKategorien;
	}
	
	//Einträge auslesen
	//Einnahmen-/Ausgaben-Einträge für einen bestimmten Benutzer auslesen
	public static ArrayList<Eintrag> readEintraege(int benutzerId, String einnahmeOderAusgabe) throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<Eintrag> alEintraege = new ArrayList<>();
		boolean isEinnahmenParameter = einnahmeOderAusgabe.equals("Einnahmen");
		// Join mit Tabelle KATEGORIE_TABLE um Column KATEGORIE_EINNAHMEODERAUSGABE abfragen zu können 
		String select = "SELECT * FROM " + EINTRAG_TABLE 
				+ " INNER JOIN " + KATEGORIE_TABLE 
				+ " ON " + 	EINTRAG_TABLE + "." + EINTRAG_KATEGORIEID + "=" + 
							KATEGORIE_TABLE + "." + KATEGORIE_ID
							
				+ " INNER JOIN " + BENUTZER_TABLE  				
				+ " ON " + 	EINTRAG_TABLE + "." + EINTRAG_BENUTZERID + "=" + 
							BENUTZER_TABLE + "." + BENUTZER_ID;
		
		if(benutzerId != getHaushaltId())
			select += " WHERE " + EINTRAG_BENUTZERID + "=? AND " + KATEGORIE_EINNAHMEODERAUSGABE + "=?";
		else
			select += " WHERE " + KATEGORIE_EINNAHMEODERAUSGABE + "=?";
		try {
			conn = DriverManager.getConnection(CONNECTION_URL);
			stmt = conn.prepareStatement(select);
			// Wenn BenutzerId != "HAUSHALT", dann 2 Parameter definieren, sonst nur einen
			if(benutzerId != getHaushaltId())
			{
				stmt.setInt(1, benutzerId);
				stmt.setBoolean(2, isEinnahmenParameter);
			}
			else
			{
				stmt.setBoolean(1, isEinnahmenParameter);
			}
			rs = stmt.executeQuery();
			while(rs.next()) {			
				alEintraege.add(
						new Eintrag(
								rs.getInt(EINTRAG_ID), 
								rs.getDate(EINTRAG_DATUM).toLocalDate(), 
								rs.getString(EINTRAG_TITEL), 
								rs.getDouble(EINTRAG_BETRAG), 
								new Benutzer(
										rs.getInt(BENUTZER_ID),
										rs.getString(BENUTZER_NAME)), 
								new Kategorie(
										rs.getInt(KATEGORIE_ID), 
										rs.getBoolean(KATEGORIE_EINNAHMEODERAUSGABE), 
										rs.getString(KATEGORIE_NAME), 
										rs.getBoolean(KATEGORIE_FAVORITE)),
								Enum.valueOf(Intervall.class, rs.getString(EINTRAG_DAUEREINTRAGINTERVALL).toUpperCase())
								));
			}
			rs.close();
			}
		catch(SQLException e) {
			throw e;
		}
		finally {
			try {
				if(stmt != null) 
					stmt.close();
				if(conn != null)
					conn.close();
			}
			catch(SQLException e) {
				throw e;
			}
		}
		return alEintraege;
	}
	//Einnahmen-/Ausgaben-Einträge für eine bestimmte Kategorie und einen bestimmten Benutzer auslesen				
	public static ArrayList<Eintrag> readEintraegeNachKategorie(int benutzerId, String einnahmeOderAusgabe, int kategorieId) throws SQLException{
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<Eintrag> alEintraege = new ArrayList<>();
		//INNER JOIN Kategorie-Tabelle mit WHERE Bedingung auf KATEGORIE_EINNAHMEODERAUSGABE, damit nur Dauereinträge ausgegeben werden, welche eine Ausgabe oder Einnahme sind
		boolean isEinnahmenParameter = einnahmeOderAusgabe.equals("Einnahmen");
		String select = "SELECT * FROM " + EINTRAG_TABLE 
				+ " INNER JOIN " + KATEGORIE_TABLE 
				+ " ON " + 	EINTRAG_TABLE + "." + EINTRAG_KATEGORIEID + "=" + 
							KATEGORIE_TABLE + "." + KATEGORIE_ID
							
				+ " INNER JOIN " + BENUTZER_TABLE  				
				+ " ON " + 	EINTRAG_TABLE + "." + EINTRAG_BENUTZERID + "=" + 
							BENUTZER_TABLE + "." + BENUTZER_ID;
				
		
		if(benutzerId != getHaushaltId())
			select += " WHERE " + 	EINTRAG_BENUTZERID + "=? AND " + 
									EINTRAG_KATEGORIEID  + "=? AND " + 
									KATEGORIE_EINNAHMEODERAUSGABE + "=?";
		else
			select += " WHERE " + 	EINTRAG_KATEGORIEID  + "=? AND " + 
									KATEGORIE_EINNAHMEODERAUSGABE + "=?";
		try {
			conn = DriverManager.getConnection(CONNECTION_URL);
			stmt = conn.prepareStatement(select);
			// Wenn BenutzerId != "HAUSHALT", dann 2 Parameter definieren, sonst nur einen
			if(benutzerId != getHaushaltId()) {
				stmt.setInt(1, benutzerId);
				stmt.setInt(2, kategorieId);
				stmt.setBoolean(3, isEinnahmenParameter);
			}
			else {
				stmt.setInt(1, kategorieId);
				stmt.setBoolean(2, isEinnahmenParameter);
			}
			rs = stmt.executeQuery();
			while(rs.next()) {			
				alEintraege.add(
						new Eintrag(
								rs.getInt(EINTRAG_ID), 
								rs.getDate(EINTRAG_DATUM).toLocalDate(), 
								rs.getString(EINTRAG_TITEL), 
								rs.getDouble(EINTRAG_BETRAG), 
								new Benutzer(
										rs.getInt(BENUTZER_ID),
										rs.getString(BENUTZER_NAME)), 
								new Kategorie(
										rs.getInt(KATEGORIE_ID), 
										rs.getBoolean(KATEGORIE_EINNAHMEODERAUSGABE), 
										rs.getString(KATEGORIE_NAME), 
										rs.getBoolean(KATEGORIE_FAVORITE)),
								Enum.valueOf(Intervall.class, rs.getString(EINTRAG_DAUEREINTRAGINTERVALL).toUpperCase())
								));
			}
			rs.close();
			}
		catch(SQLException e) {
			throw e;
		}
		finally {
			try {
				if(stmt != null) 
					stmt.close();
				if(conn != null)
					conn.close();
			}
			catch(SQLException e) {
				throw e;
			}
		}
		return alEintraege;
	}
	
	//Dauereinträge auslesen
	//Einnahmen-/Ausgaben-Dauereinträge für einen bestimmten Benutzer auslesen
	public static ArrayList<Dauereintrag> readDauereintraege(int benutzerId, String einnahmeOderAusgabe) throws SQLException{
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<Dauereintrag> alDauereintraege = new ArrayList<>();
		//INNER JOIN Kategorie-Tabelle mit WHERE Bedingung auf KATEGORIE_EINNAHMEODERAUSGABE, damit nur Dauereinträge ausgegeben werden, welche eine Ausgabe oder Einnahme sind
		boolean isEinnahmenParameter = einnahmeOderAusgabe.equals("Einnahmen");
		String select = "SELECT * FROM " + DAUEREINTRAG_TABLE 
				+ " INNER JOIN " + KATEGORIE_TABLE  				
				+ " ON " + 	DAUEREINTRAG_TABLE + "." + DAUEREINTRAG_KATEGORIEID + "=" + 
							KATEGORIE_TABLE + "." + KATEGORIE_ID 
				+ " INNER JOIN " + BENUTZER_TABLE  				
				+ " ON " + 	DAUEREINTRAG_TABLE + "." + DAUEREINTRAG_BENUTZERID + "=" + 
							BENUTZER_TABLE + "." + BENUTZER_ID;
		
		if(benutzerId != getHaushaltId())
			select += " WHERE " + 	DAUEREINTRAG_BENUTZERID + "=? AND " + 
									KATEGORIE_EINNAHMEODERAUSGABE + "=?";
		else 
			select += " WHERE " +	KATEGORIE_EINNAHMEODERAUSGABE + "=?";
		
		try {
			conn = DriverManager.getConnection(CONNECTION_URL);
			stmt = conn.prepareStatement(select);
			// Wenn BenutzerId != "HAUSHALT", dann 2 Parameter definieren, sonst nur einen
			if(benutzerId != getHaushaltId()) {
				stmt.setInt(1, benutzerId);
				stmt.setBoolean(2, isEinnahmenParameter);
			}
			else {
				stmt.setBoolean(1, isEinnahmenParameter);
			}
			
			rs = stmt.executeQuery();
			while(rs.next()) {			
				alDauereintraege.add(
						new Dauereintrag(
								rs.getInt(DAUEREINTRAG_ID), 
								rs.getDate(DAUEREINTRAG_NAECHSTEFAELLIGKEIT).toLocalDate(), 
								rs.getString(DAUEREINTRAG_TITEL), 
								rs.getDouble(DAUEREINTRAG_BETRAG), 
								new Benutzer(
										rs.getInt(BENUTZER_ID),
										rs.getString(BENUTZER_NAME)), 
								Enum.valueOf(Intervall.class, rs.getString(DAUEREINTRAG_INTERVALL).toUpperCase()), 
								rs.getDate(DAUEREINTRAG_ENDEDATUM).toLocalDate(), 
								new Kategorie(
										rs.getInt(KATEGORIE_ID), 
										rs.getBoolean(KATEGORIE_EINNAHMEODERAUSGABE), 
										rs.getString(KATEGORIE_NAME), 
										rs.getBoolean(KATEGORIE_FAVORITE))
								));
			}
			rs.close();
			}
		catch(SQLException e) {
			throw e;
		}
		finally {
			try {
				if(stmt != null) 
					stmt.close();
				if(conn != null)
					conn.close();
			}
			catch(SQLException e) {
				throw e;
			}
		}
		return alDauereintraege;
	}
	public static ArrayList<Dauereintrag> readDauereintraegeAlle() throws SQLException{
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<Dauereintrag> alDauereintraege = new ArrayList<>();
		String select = "SELECT * FROM " + DAUEREINTRAG_TABLE; 
		try {
			conn = DriverManager.getConnection(CONNECTION_URL);
			stmt = conn.prepareStatement(select);
			rs = stmt.executeQuery();
			while(rs.next()) {			
				alDauereintraege.add(
						new Dauereintrag(
								rs.getInt(DAUEREINTRAG_ID), 
								rs.getDate(DAUEREINTRAG_NAECHSTEFAELLIGKEIT).toLocalDate(), 
								rs.getString(DAUEREINTRAG_TITEL), 
								rs.getDouble(DAUEREINTRAG_BETRAG), 
								new Benutzer(
										rs.getInt(DAUEREINTRAG_BENUTZERID)), 
								Enum.valueOf(Intervall.class, rs.getString(DAUEREINTRAG_INTERVALL).toUpperCase()), 
								rs.getDate(DAUEREINTRAG_ENDEDATUM).toLocalDate(), 
								new Kategorie(
										rs.getInt(DAUEREINTRAG_KATEGORIEID))
								));
			}
			rs.close();
			}
		catch(SQLException e) {
			throw e;
		}
		finally {
			try {
				if(stmt != null) 
					stmt.close();
				if(conn != null)
					conn.close();
			}
			catch(SQLException e) {
				throw e;
			}
		}
		return alDauereintraege;
	}
	//Einnahmen-/Ausgaben-Einträge für eine bestimmte Kategorie und einen bestimmten Benutzer auslesen	
	public static ArrayList<Dauereintrag> readDauereintraegeNachKategorie(int benutzerId, String einnahmeOderAusgabe, int kategorieId) throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<Dauereintrag> alDauereintraege = new ArrayList<>();
		//INNER JOIN Kategorie-Tabelle mit WHERE Bedingung auf KATEGORIE_EINNAHMEODERAUSGABE, damit nur Dauereinträge ausgegeben werden, welche eine Ausgabe oder Einnahme sind
		boolean isEinnahmenParameter = einnahmeOderAusgabe.equals("Einnahmen");
		String select = "SELECT * FROM " + DAUEREINTRAG_TABLE 
				+ " INNER JOIN " + KATEGORIE_TABLE  				
				+ " ON " + 	DAUEREINTRAG_TABLE + "." + DAUEREINTRAG_KATEGORIEID + "=" + 
							KATEGORIE_TABLE + "." + KATEGORIE_ID 
				+ " INNER JOIN " + BENUTZER_TABLE  				
				+ " ON " + 	DAUEREINTRAG_TABLE + "." + DAUEREINTRAG_BENUTZERID + "=" + 
							BENUTZER_TABLE + "." + BENUTZER_ID;
		
		if(benutzerId != getHaushaltId())
			select += " WHERE " + 	DAUEREINTRAG_BENUTZERID + "=? AND " + 
									DAUEREINTRAG_KATEGORIEID + "=? AND " +
									KATEGORIE_EINNAHMEODERAUSGABE + "=?";
		else
			select += " WHERE " + 	DAUEREINTRAG_KATEGORIEID  + "=? AND " + 
									KATEGORIE_EINNAHMEODERAUSGABE + "=?";
		try {
			conn = DriverManager.getConnection(CONNECTION_URL);
			stmt = conn.prepareStatement(select);
			// Wenn BenutzerId != "HAUSHALT", dann 2 Parameter definieren, sonst nur einen
			if(benutzerId != getHaushaltId()) {
				stmt.setInt(1, benutzerId);
				stmt.setInt(2, kategorieId);
				stmt.setBoolean(3, isEinnahmenParameter);
			}
			else {
				stmt.setInt(1, kategorieId);
				stmt.setBoolean(2, isEinnahmenParameter);
			}
			
			rs = stmt.executeQuery();
			while(rs.next()) {			
				alDauereintraege.add(
						new Dauereintrag(
								rs.getInt(DAUEREINTRAG_ID), 
								rs.getDate(DAUEREINTRAG_NAECHSTEFAELLIGKEIT).toLocalDate(), 
								rs.getString(DAUEREINTRAG_TITEL), 
								rs.getDouble(DAUEREINTRAG_BETRAG), 
								new Benutzer(
										rs.getInt(BENUTZER_ID),
										rs.getString(BENUTZER_NAME)), 
								Enum.valueOf(Intervall.class, rs.getString(DAUEREINTRAG_INTERVALL).toUpperCase()), 
								rs.getDate(DAUEREINTRAG_ENDEDATUM).toLocalDate(), 
								new Kategorie(
										rs.getInt(KATEGORIE_ID), 
										rs.getBoolean(KATEGORIE_EINNAHMEODERAUSGABE), 
										rs.getString(KATEGORIE_NAME), 
										rs.getBoolean(KATEGORIE_FAVORITE))
								));
			}
			rs.close();
			}
		catch(SQLException e) {
			throw e;
		}
		finally {
			try {
				if(stmt != null) 
					stmt.close();
				if(conn != null)
					conn.close();
			}
			catch(SQLException e) {
				throw e;
			}
		}
		return alDauereintraege;
	}
			
	//DATEN EINTRAGEN
	public static void insertBenutzer(Benutzer benutzer) throws SQLException{
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = DriverManager.getConnection(CONNECTION_URL);
			String insert = "INSERT INTO " + BENUTZER_TABLE + " (" +
                    BENUTZER_NAME +
                    ") VALUES (?)"; 	//BENUTZER_NAME
			stmt = conn.prepareStatement(insert);
			stmt.setString(1, benutzer.getName());		
			stmt.executeUpdate();		
		}
		catch(SQLException e) {
			throw e;
		}
		finally {
			try {
				if(stmt != null) 
					stmt.close();
				if(conn != null)
					conn.close();
			}
			catch(SQLException e) {
				throw e;
			}
		}
	}
	public static void insertKategorie(Kategorie kategorie) throws SQLException{
		Connection conn = null;
		PreparedStatement stmt = null;
		Statement stmti = null;
		try {
			conn = DriverManager.getConnection(CONNECTION_URL);
			String insert = "INSERT INTO " + KATEGORIE_TABLE + 
					"(" + KATEGORIE_EINNAHMEODERAUSGABE + "," + KATEGORIE_NAME + "," + KATEGORIE_FAVORITE + ") VALUES(" +
					"?," +	//KATEGORIE_EINNAHMEODERAUSGABE
					"?," +	//KATEGORIE_NAME
					"?)"; 	//KATEGORIE_FAVORITE
			stmt = conn.prepareStatement(insert);
			stmt.setBoolean(1, kategorie.isEinnahmeOderAusgabe());
			stmt.setString(2, kategorie.getName());	
			stmt.setBoolean(3, kategorie.isFavorite());
			stmt.executeUpdate();	
			stmti = conn.createStatement();
			ResultSet rs = stmti.executeQuery("SELECT IDENTITY_VAL_LOCAL() FROM " + KATEGORIE_TABLE);
			if(rs.next())
				kategorie.setKategorieId(rs.getInt("1"));
		}
		catch(SQLException e) {
			throw e;
		}
		finally {
			try {
				if(stmt != null) 
					stmt.close();
				if(stmti != null) 
					stmti.close();
				if(conn != null)
					conn.close();
			}
			catch(SQLException e) {
				throw e;
			}
		}
	}
	public static void insertEintrag(Eintrag eintrag) throws SQLException{
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = DriverManager.getConnection(CONNECTION_URL);
			String insert = "INSERT INTO " + EINTRAG_TABLE + 
					"(" + EINTRAG_DATUM + "," + EINTRAG_TITEL + "," + EINTRAG_BETRAG + "," + EINTRAG_BENUTZERID + "," + EINTRAG_KATEGORIEID + "," + EINTRAG_DAUEREINTRAGINTERVALL + ") VALUES(" +
					"?," +	//EINTRAG_DATUM
					"?," +	//EINTRAG_TITEL
					"?," +	//EINTRAG_BETRAG
					"?," +	//EINTRAG_BENUTZERID
					"?," +	//EINTRAG_KATEGORIEID
					"?)"; 	//EINTRAG_DAUEREINTRAG
			stmt = conn.prepareStatement(insert);
			LocalDateTime ldt = LocalDateTime.of(eintrag.getDatum(), LocalTime.of(0, 0, 0));
			java.sql.Date date = new java.sql.Date(ldt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()); //in altes Date Objekt für JDBC umwandeln - ldt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
			stmt.setDate(1, date);
			stmt.setString(2, eintrag.getTitel());	
			stmt.setDouble(3, eintrag.getBetrag());	
			stmt.setInt(4, eintrag.getBenutzer().getBenutzerId());
			stmt.setInt(5, eintrag.getKategorie().getKategorieId());
			stmt.setString(6, eintrag.getIntervall().getIName());
			stmt.executeUpdate();		
		}
		catch(SQLException e) {
			throw e;
		}
		finally {
			try {
				if(stmt != null) 
					stmt.close();
				if(conn != null)
					conn.close();
			}
			catch(SQLException e) {
				throw e;
			}
		}
	}
	public static void insertDauereintrag(Dauereintrag dauereintrag) throws SQLException{
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = DriverManager.getConnection(CONNECTION_URL);
			String insert = "INSERT INTO " + DAUEREINTRAG_TABLE + 
					"(" + DAUEREINTRAG_NAECHSTEFAELLIGKEIT + "," + DAUEREINTRAG_TITEL + "," + DAUEREINTRAG_BETRAG + "," + DAUEREINTRAG_BENUTZERID + "," + DAUEREINTRAG_INTERVALL + ","+ DAUEREINTRAG_ENDEDATUM + "," + DAUEREINTRAG_KATEGORIEID + ") VALUES(" +
					"?," +	//DAUEREINTRAG_NAECHSTEFAELLIGKEIT
					"?," +	//DAUEREINTRAG_TITEL
					"?," +	//DAUEREINTRAG_BETRAG
					"?," +	//DAUEREINTRAG_BENUTZERID
					"?," +	//DAUEREINTRAG_INTERVALL
					"?," +	//DAUEREINTRAG_ENDEDATUM
					"?)"; 	//DAUEREINTRAG_KATEGORIEID
			stmt = conn.prepareStatement(insert);
			LocalDateTime ldt1 = LocalDateTime.of(dauereintrag.getNaechsteFaelligkeit(), LocalTime.of(0, 0, 0));
			java.sql.Date naechsteFaelligkeit = new java.sql.Date(ldt1.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()); //in altes Date Objekt für JDBC umwandeln - ldt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
			stmt.setDate(1, naechsteFaelligkeit);
			stmt.setString(2, dauereintrag.getDeTitel());	
			stmt.setDouble(3, dauereintrag.getDeBetrag());
			stmt.setInt(4, dauereintrag.getDeBenutzer().getBenutzerId());
			stmt.setString(5, dauereintrag.getIntervall().getIName()); 		
			LocalDateTime ldt2 = LocalDateTime.of(dauereintrag.getEnddatum(), LocalTime.of(0, 0, 0));
			java.sql.Date endedatum = new java.sql.Date(ldt2.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()); //in altes Date Objekt für JDBC umwandeln - ldt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
			stmt.setDate(6, endedatum);
			stmt.setInt(7, dauereintrag.getDeKategorie().getKategorieId());
			stmt.executeUpdate();		
		}
		catch(SQLException e) {
			throw e;
		}
		finally {
			try {
				if(stmt != null) 
					stmt.close();
				if(conn != null)
					conn.close();
			}
			catch(SQLException e) {
				throw e;
			}
		}
	}
	
	//DATEN UPDATEN		
	//Benutzer updaten
	public static void updateBenutzer(Benutzer benutzer, String neuerName) throws SQLException{
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = DriverManager.getConnection(CONNECTION_URL);
			String update = "UPDATE " + BENUTZER_TABLE + " SET " +
					BENUTZER_NAME + "=? WHERE " + BENUTZER_ID + "=?";
			stmt = conn.prepareStatement(update);
			stmt.setString(1, neuerName);
			stmt.setInt(2, benutzer.getBenutzerId());
			stmt.executeUpdate();
		}
		catch (SQLException e) {
			throw e;
		}
		finally {
			try {
				if(stmt != null) 
					stmt.close();
				if(conn != null)
					conn.close();
			}
			catch(SQLException e) {
				throw e;
			}
		}
	}
	
	//Dauereinträge updaten
	public static void updateDauereintrag(Dauereintrag dauereintrag) throws SQLException{
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = DriverManager.getConnection(CONNECTION_URL);
			String update = "UPDATE " + DAUEREINTRAG_TABLE + " SET " +
					DAUEREINTRAG_NAECHSTEFAELLIGKEIT + "=?," +
					DAUEREINTRAG_TITEL + "=?," +
					DAUEREINTRAG_BETRAG + "=?," +
					DAUEREINTRAG_BENUTZERID + "=?," +
					DAUEREINTRAG_INTERVALL + "=?," +
					DAUEREINTRAG_ENDEDATUM + "=?," +
					DAUEREINTRAG_KATEGORIEID + "=? WHERE " + DAUEREINTRAG_ID + "=?";
			stmt = conn.prepareStatement(update);
			LocalDateTime ldt1 = LocalDateTime.of(dauereintrag.getNaechsteFaelligkeit(), LocalTime.of(0, 0, 0));
			java.sql.Date naechsteFaelligkeit = new java.sql.Date(ldt1.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()); //in altes Date Objekt für JDBC umwandeln - ldt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
			stmt.setDate(1, naechsteFaelligkeit);
			stmt.setString(2, dauereintrag.getDeTitel());	
			stmt.setDouble(3, dauereintrag.getDeBetrag());
			stmt.setInt(4, dauereintrag.getDeBenutzer().getBenutzerId());
			stmt.setString(5, dauereintrag.getIntervall().getIName()); 		
			LocalDateTime ldt2 = LocalDateTime.of(dauereintrag.getEnddatum(), LocalTime.of(0, 0, 0));
			java.sql.Date endedatum = new java.sql.Date(ldt2.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()); //in altes Date Objekt für JDBC umwandeln - ldt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
			stmt.setDate(6, endedatum);
			stmt.setInt(7, dauereintrag.getDeKategorie().getKategorieId());
			stmt.setInt(8, dauereintrag.getDauereintragId());
			stmt.executeUpdate();
		}
		catch (SQLException e) {
			throw e;
		}
		finally {
			try {
				if(stmt != null) 
					stmt.close();
				if(conn != null)
					conn.close();
			}
			catch(SQLException e) {
				throw e;
			}
		}
	}
	//Update BenutzerId Fremdschlüssel: übgeben der Dauereinträge von Benutzer an Haushalt durch austauschen des Fremdschlüssels auf BenutzerId Haushalt
	private static void updateDauereintraegeAufBenutzerHaushalt(int benutzerId) throws SQLException{
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = DriverManager.getConnection(CONNECTION_URL);
			String update = "UPDATE " + DAUEREINTRAG_TABLE + " SET " +
					DAUEREINTRAG_BENUTZERID + "=? WHERE " + DAUEREINTRAG_BENUTZERID + "=?";
			stmt = conn.prepareStatement(update);
			stmt.setInt(1, getHaushaltId());
			stmt.setInt(2, benutzerId);	
			stmt.executeUpdate();
		}
		catch (SQLException e) {
			throw e;
		}
		finally {
			try {
				if(stmt != null) 
					stmt.close();
				if(conn != null)
					conn.close();
			}
			catch(SQLException e) {
				throw e;
			}
		}
	}
	public static void updateDauereintragNaechsteFaelligkeit(LocalDate naechsteFaelligkeit, int dauereintragId) throws SQLException{
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = DriverManager.getConnection(CONNECTION_URL);
			String update = "UPDATE " + DAUEREINTRAG_TABLE + " SET " +
					DAUEREINTRAG_NAECHSTEFAELLIGKEIT + "=? WHERE " + DAUEREINTRAG_ID + "=?";
			stmt = conn.prepareStatement(update);
			LocalDateTime ldt1 = LocalDateTime.of(naechsteFaelligkeit, LocalTime.of(0, 0, 0));
			java.sql.Date nF = new java.sql.Date(ldt1.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()); //in altes Date Objekt für JDBC umwandeln - ldt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
			stmt.setDate(1, nF);
			stmt.setInt(2, dauereintragId);
			stmt.executeUpdate();
		}
		catch (SQLException e) {
			throw e;
		}
		finally {
			try {
				if(stmt != null) 
					stmt.close();
				if(conn != null)
					conn.close();
			}
			catch(SQLException e) {
				throw e;
			}
		}
		
	}
	
	//Einträge updaten
	public static void updateEintrag(Eintrag eintrag) throws SQLException{
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = DriverManager.getConnection(CONNECTION_URL);
			String update = "UPDATE " + EINTRAG_TABLE + " SET " +
					EINTRAG_DATUM + "=?," +
					EINTRAG_TITEL + "=?," +
					EINTRAG_BETRAG + "=?," +
					EINTRAG_BENUTZERID + "=?," +
					EINTRAG_KATEGORIEID + "=? WHERE " + EINTRAG_ID + "=?";
			stmt = conn.prepareStatement(update);
			LocalDateTime ldt = LocalDateTime.of(eintrag.getDatum(), LocalTime.of(0, 0, 0));
			java.sql.Date date = new java.sql.Date(ldt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()); //in altes Date Objekt für JDBC umwandeln - ldt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
			stmt.setDate(1, date);
			stmt.setString(2, eintrag.getTitel());	
			stmt.setDouble(3, eintrag.getBetrag());	
			stmt.setInt(4, eintrag.getBenutzer().getBenutzerId());
			stmt.setInt(5, eintrag.getKategorie().getKategorieId());
			stmt.setInt(6, eintrag.getEintragId());
			stmt.executeUpdate();
		}
		catch (SQLException e) {
			throw e;
		}
		finally {
			try {
				if(stmt != null) 
					stmt.close();
				if(conn != null)
					conn.close();
			}
			catch(SQLException e) {
				throw e;
			}
		}
	}
	//Update BenutzerId Fremdschlüssel: übgeben der Einträge von Benutzer an Haushalt durch austauschen des Fremdschlüssels auf BenutzerId Haushalt
	private static void updateEintraegeAufBenutzerHaushalt(int benutzerId) throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = DriverManager.getConnection(CONNECTION_URL);
			String update = "UPDATE " + EINTRAG_TABLE + " SET " +
					EINTRAG_BENUTZERID + "=? WHERE " + EINTRAG_BENUTZERID + "=?";
			stmt = conn.prepareStatement(update);
			stmt.setInt(1, getHaushaltId());
			stmt.setInt(2, benutzerId);	
			stmt.executeUpdate();
		}
		catch (SQLException e) {
			throw e;
		}
		finally {
			try {
				if(stmt != null) 
					stmt.close();
				if(conn != null)
					conn.close();
			}
			catch(SQLException e) {
				throw e;
			}
		}
	}	
	
	//Kategorienname updaten
	public static void updateKategorieName(Kategorie kategorie, String neuerName) throws SQLException{
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = DriverManager.getConnection(CONNECTION_URL);
			String update = "UPDATE " + KATEGORIE_TABLE + " SET " +
					KATEGORIE_NAME + "=? WHERE " + KATEGORIE_ID + "=?";
			stmt = conn.prepareStatement(update);
			stmt.setString(1, neuerName);
			stmt.setInt(2, kategorie.getKategorieId());
			stmt.executeUpdate();
		}
		catch (SQLException e) {
			throw e;
		}
		finally {
			try {
				if(stmt != null) 
					stmt.close();
				if(conn != null)
					conn.close();
			}
			catch(SQLException e) {
				throw e;
			}
		}
	}
	//Kategorie favorisieren
	public static void setKategorieFavorit(int kategorieId, Boolean isFavorit) throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = DriverManager.getConnection(CONNECTION_URL);
			String update = "UPDATE " + KATEGORIE_TABLE + " SET " +
					KATEGORIE_FAVORITE + "=? WHERE " + KATEGORIE_ID + "=?";
			stmt = conn.prepareStatement(update);
			stmt.setBoolean(1, isFavorit);
			stmt.setInt(2, kategorieId);
			stmt.executeUpdate();
		}
		catch (SQLException e) {
			throw e;
		}
		finally {
			try {
				if(stmt != null) 
					stmt.close();
				if(conn != null)
					conn.close();
			}
			catch(SQLException e) {
				throw e;
			}
		}
	}
	
	//DATEN LÖSCHEN
	//Benutzer Löschen und alle Einträge und Dauereinträge mit der BenutzerId werden auf HAUSHALT überschrieben
	public static void deleteBenutzer(int benutzerId) throws SQLException{
		//Fremdschlüssel Referenz austauschen
		updateDauereintraegeAufBenutzerHaushalt(benutzerId);
		updateEintraegeAufBenutzerHaushalt(benutzerId);
		//Benutzer löschen
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = DriverManager.getConnection(CONNECTION_URL);
			String delete = "DELETE FROM " + BENUTZER_TABLE + 
					" WHERE " + BENUTZER_ID + "=" + benutzerId;
			stmt = conn.prepareStatement(delete);
			stmt.executeUpdate();		
		}
		catch(SQLException e) {
			throw e;
		}
		finally {
			try {
				if(stmt != null) 
					stmt.close();
				if(conn != null)
					conn.close();
			}
			catch(SQLException e) {
				throw e;
			}
		}
	} 
	//Löschen einer Kategorie inkl. aller Einträge und Dauereinträge
	public static void deleteKategorie(int kategorieId) throws SQLException{	
		//Zuerst löschen der Einträge und Dauerenträge
		deleteEintraegeEinerKategorie(kategorieId);
		deleteDauereintraegeEinerKategorie(kategorieId);
		//Jetzt Löschen der Kategorie
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = DriverManager.getConnection(CONNECTION_URL);
			String delete = "DELETE FROM " + KATEGORIE_TABLE + 
					" WHERE " + KATEGORIE_ID + "=?";
			stmt = conn.prepareStatement(delete);
			stmt.setInt(1, kategorieId);
			stmt.executeUpdate();		
		}
		catch(SQLException e) {
			throw e;
		}
		finally {
			try {
				if(stmt != null) 
					stmt.close();
				if(conn != null)
					conn.close();
			}
			catch(SQLException e) {
				throw e;
			}
		}
	} 
	private static void deleteDauereintraegeEinerKategorie(int kategorieId) throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = DriverManager.getConnection(CONNECTION_URL);
			String delete = "DELETE FROM " + EINTRAG_TABLE + 
					" WHERE " + EINTRAG_KATEGORIEID + "=?";
			stmt = conn.prepareStatement(delete);
			stmt.setInt(1, kategorieId);
			stmt.executeUpdate();		
		}
		catch(SQLException e) {
			throw e;
		}
		finally {
			try {
				if(stmt != null) 
					stmt.close();
				if(conn != null)
					conn.close();
			}
			catch(SQLException e) {
				throw e;
			}
		}
	}
	private static void deleteEintraegeEinerKategorie(int kategorieId) throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = DriverManager.getConnection(CONNECTION_URL);
			String delete = "DELETE FROM " + DAUEREINTRAG_TABLE + 
					" WHERE " + DAUEREINTRAG_KATEGORIEID + "=?";
			stmt = conn.prepareStatement(delete);
			stmt.setInt(1, kategorieId);
			stmt.executeUpdate();		
		}
		catch(SQLException e) {
			throw e;
		}
		finally {
			try {
				if(stmt != null) 
					stmt.close();
				if(conn != null)
					conn.close();
			}
			catch(SQLException e) {
				throw e;
			}
		}
	}
	//Eintrag löschen
	public static void deleteEintrag(int eintragId) throws SQLException{
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = DriverManager.getConnection(CONNECTION_URL);
			String delete = "DELETE FROM " + EINTRAG_TABLE + 
					" WHERE " + EINTRAG_ID + "=" + eintragId;
			stmt = conn.prepareStatement(delete);
			stmt.executeUpdate();		
		}
		catch(SQLException e) {
			throw e;
		}
		finally {
			try {
				if(stmt != null) 
					stmt.close();
				if(conn != null)
					conn.close();
			}
			catch(SQLException e) {
				throw e;
			}
		}
	} 
	public static void deleteDauereintrag(int dauereintragId) throws SQLException{
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = DriverManager.getConnection(CONNECTION_URL);
			String delete = "DELETE FROM " + DAUEREINTRAG_TABLE + 
					" WHERE " + DAUEREINTRAG_ID + "=" + dauereintragId;
			stmt = conn.prepareStatement(delete);
			stmt.executeUpdate();		
		}
		catch(SQLException e) {
			throw e;
		}
		finally {
			try {
				if(stmt != null) 
					stmt.close();
				if(conn != null)
					conn.close();
			}
			catch(SQLException e) {
				throw e;
			}
		}
	}


}
