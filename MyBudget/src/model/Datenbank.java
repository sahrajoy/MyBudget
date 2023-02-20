package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
	private static final String EINTRAG_DATUM = "EintragDatum";
	private static final String EINTRAG_TITEL = "EintragTitel";
	private static final String EINTRAG_BETRAG = "EintragBetrag";
	private static final String EINTRAG_BENUTZERID = "EintragBenutzerId";
	private static final String EINTRAG_KATEGORIEID = "EintragKategorieId";
	
	//Dauereinträge Tabelle
	private static final String DAUEREINTRAG_TABLE = "Dauereintrag";
	private static final String DAUEREINTRAG_ID = "DauereintragId";
	private static final String DAUEREINTRAG_NAECHSTEFAELLIGKEIT = "DauereintragNaechsteFaelligkeit";
	private static final String DAUEREINTRAG_TITEL = "DauereintragTitel";
	private static final String DAUEREINTRAG_BETRAG = "DauereintragBetrag";
	private static final String DAUEREINTRAG_ENDEDATUM = "DauereintragEndedatum";
	private static final String DAUEREINTRAG_INTERVALL = "DauereintragIntervall";
	private static final String DAUEREINTRAG_BENUTZERID = "DauereintragBenutzerId";
	private static final String DAUEREINTRAG_KATEGORIEID = "DauereintragKategorieId";

	public static void createBenutzerTable() throws SQLException{
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(CONNECTION_URL);
			stmt = conn.createStatement();
			rs = conn.getMetaData().getTables(null, null, BENUTZER_TABLE.toUpperCase(), new String[] {"TABLE"}); 		
			if(rs.next()) {			
				return;
			}
			String ct = "CREATE TABLE " + BENUTZER_TABLE + " (" + 
					BENUTZER_ID + " BIGINT," +
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
					KATEGORIE_ID + " BIGINT," +
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
					EINTRAG_ID + " BIGINT," +
					EINTRAG_DATUM + " DATE," +
					EINTRAG_TITEL + " VARCHAR(200)," +
					EINTRAG_BETRAG +  " DECIMAL," +
					EINTRAG_BENUTZERID + " BIGINT," +
					EINTRAG_KATEGORIEID + " BIGINT," +
					"PRIMARY KEY(" + EINTRAG_ID + "))" +
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
	
	public static void createDauereintragTable() throws SQLException{ //Enum wie Objektklassen behandeln, auch Tabelle erzeugen?
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
					DAUEREINTRAG_ID + " BIGINT," +
					DAUEREINTRAG_NAECHSTEFAELLIGKEIT + " DATE," +
					DAUEREINTRAG_TITEL + " VARCHAR(200)," +
					DAUEREINTRAG_BETRAG +  " DECIMAL," +
					DAUEREINTRAG_ENDEDATUM + " DATE," +
					DAUEREINTRAG_INTERVALL + " ," +				//??
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
