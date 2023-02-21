package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

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
	private static final String KATEGORIE_NAME = "KategorieName";
	private static final String KATEGORIE_FAVORITE = "KategorieFavorite";
	
	//Eintrag Tabelle
	private static final String EINTRAG_TABLE = "Eintrag";
	private static final String EINTRAG_ID = "EintragId";
	private static final String EINTRAG_EINNAHMEODERAUSGABE = "EintragEinnahmeOderAusgabe";
	private static final String EINTRAG_DATUM = "EintragDatum";
	private static final String EINTRAG_TITEL = "EintragTitel";
	private static final String EINTRAG_BETRAG = "EintragBetrag";
	private static final String EINTRAG_BENUTZERID = "EintragBenutzerId";
	private static final String EINTRAG_KATEGORIEID = "EintragKategorieId";
	
	//Dauereinträge Tabelle
	private static final String DAUEREINTRAG_TABLE = "Dauereintrag";
	private static final String DAUEREINTRAG_ID = "DauereintragId";
	private static final String DAUEREINTRAG_EINNAHMEODERAUSGABE = "DauereintragEinnahmeOderAusgabe";
	private static final String DAUEREINTRAG_NAECHSTEFAELLIGKEIT = "DauereintragNaechsteFaelligkeit";
	private static final String DAUEREINTRAG_TITEL = "DauereintragTitel";
	private static final String DAUEREINTRAG_BETRAG = "DauereintragBetrag";
	private static final String DAUEREINTRAG_ENDEDATUM = "DauereintragEndedatum";
	private static final String DAUEREINTRAG_INTERVALL = "DauereintragIntervall";
	private static final String DAUEREINTRAG_BENUTZERID = "DauereintragBenutzerId";
	private static final String DAUEREINTRAG_KATEGORIEID = "DauereintragKategorieId";

	//Tabellen erstellen
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
					EINTRAG_EINNAHMEODERAUSGABE + " BOOLEAN," +
					EINTRAG_DATUM + " DATE," +
					EINTRAG_TITEL + " VARCHAR(200)," +
					EINTRAG_BETRAG +  " DECIMAL," +
					EINTRAG_BENUTZERID + " BIGINT," +
					EINTRAG_KATEGORIEID + " BIGINT," +
					"PRIMARY KEY(" + EINTRAG_ID + ")" +
					"FOREIGN KEY(" + EINTRAG_BENUTZERID + ") REFERENCES " + BENUTZER_TABLE + "(" + BENUTZER_ID + ")" +
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
					DAUEREINTRAG_EINNAHMEODERAUSGABE + " BOOLEAN," +
					DAUEREINTRAG_NAECHSTEFAELLIGKEIT + " DATE," +
					DAUEREINTRAG_TITEL + " VARCHAR(200)," +
					DAUEREINTRAG_BETRAG +  " DECIMAL," +
					DAUEREINTRAG_ENDEDATUM + " DATE," +
					DAUEREINTRAG_INTERVALL + " SMALLINT," +				
					DAUEREINTRAG_BENUTZERID + " BIGINT," +
					DAUEREINTRAG_KATEGORIEID + " BIGINT," +
					"PRIMARY KEY(" + DAUEREINTRAG_ID + "))" +
					"FOREIGN KEY(" + DAUEREINTRAG_BENUTZERID + ") REFERENCES " + BENUTZER_TABLE + "(" + BENUTZER_ID + ")" +
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
	
	//Daten auslesen
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
	
	public static EintraegeList readEintraege(String Kategorie, String EinnahmeOdAusgabe, String Benutzer) throws SQLException{
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<Eintrag> alEintraege = new ArrayList<>();
		String select = "SELECT * FROM " + EINTRAG_TABLE;
		try {
			conn = DriverManager.getConnection(CONNECTION_URL);
			stmt = conn.prepareStatement(select);
			rs = stmt.executeQuery();
			while(rs.next()) {			
				alEintraege.add(new Eintrag(rs.getInt(EINTRAG_ID),rs.getBoolean(EINTRAG_EINNAHMEODERAUSGABE), rs.getDate(EINTRAG_DATUM).toLocalDate(), rs.getString(EINTRAG_TITEL), rs.getDouble(EINTRAG_BETRAG), new Benutzer(rs.getInt(BENUTZER_ID), rs.getString(BENUTZER_NAME)), new Kategorie(rs.getInt(KATEGORIE_ID))));
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
		return new EintraegeList(alEintraege);
	}
	
	public static DauereintraegeList readDauereintraege(String Kategorie, String EinnahmeOdAusgabe, String Benutzer) throws SQLException{
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<Eintrag> alDauereintraege = new ArrayList<>();
		String select = "SELECT * FROM " + DAUEREINTRAG_TABLE;
		try {
			conn = DriverManager.getConnection(CONNECTION_URL);
			stmt = conn.prepareStatement(select);
			rs = stmt.executeQuery();
			while(rs.next()) {			
				alDauereintraege.add(new Dauereintrag(rs.getInt(DAUEREINTRAG_ID), rs.getDate(DAUEREINTRAG_NAECHSTEFAELLIGKEIT), rs.getString(DAUEREINTRAG_TITEL), rs.getDouble(DAUEREINTRAG_BETRAG), rs.getDate(DAUEREINTRAG_ENDEDATUM), rs.getString(DAUEREINTRAG_INTERVALL), new Benutzer(rs.getInt(BENUTZER_ID), rs.getString(BENUTZER_NAME)), new Kategorie(rs.getInt(KATEGORIE_ID))));
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
		return new DauereintraegeList(alDauereintraege);
	}
	
	//Daten überprüfen
	public static boolean benutzerExist(String benutzerName) throws SQLException{
		try {
			List<Benutzer> alBenutzer = Datenbank.readBenutzer();
			return alBenutzer.stream().anyMatch(b -> b.equals(benutzerName));
		} catch (SQLException e) {
			throw e;
		}	
	}
	
	//Daten adaptieren
	public static void insertBenutzer(Benutzer b) throws SQLException{
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = DriverManager.getConnection(CONNECTION_URL);
			String insert = "INSERT INTO " + BENUTZER_TABLE + " VALUES(" +
					"?," + 	//BENUTZER_ID
					"?)"; 	//BENUTZER_NAME
			stmt = conn.prepareStatement(insert);
			stmt.setString(1, b.getName());		//Fragezeichen 1
			stmt.setString(2, b.getName());		//Fragezeichen 2
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
	public static void insertKategorie() throws SQLException{
		
	}
	public static void insertEintrag() throws SQLException{
		
	}
	public static void insertDauereintrag() throws SQLException{
	
	}
	
	//Daten ändern
	public static void updateBenutzer(Benutzer benutzer) throws SQLException{
		
	}
	public static void updateKategorie(Kategorie kategorie) throws SQLException{
		
	}
	public static void updateEintrag(Eintrag eintrag) throws SQLException{
		
	}
	public static void updateDauereintrag(Dauereintrag dauereintrag) throws SQLException{
		
	}
	
	//Daten löschen
	public static void deleteBenutzer(int benutzerId) throws SQLException{
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
	
	public static void deleteKategorie(int kategorieId) throws SQLException{
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = DriverManager.getConnection(CONNECTION_URL);
			String delete = "DELETE FROM " + KATEGORIE_TABLE + 
					" WHERE " + KATEGORIE_ID + "=" + kategorieId;
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
