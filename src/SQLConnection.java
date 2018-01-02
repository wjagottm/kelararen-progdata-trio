import java.sql.*;
import java.util.ArrayList;

public class SQLConnection {

    private String connectionUrl = "jdbc:sqlserver://netflixstatistixsv1.database.windows.net:1433;database=NetflixStatistix;user=kelararen@netflixstatistixsv1;password=Welkom01!;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
    private Connection con = null;
    private Statement stmt = null;
    private ResultSet rs = null;

    public SQLConnection() {

    }

    public ArrayList<Integer> getAccountId() {
        ArrayList<Integer> accounts = new ArrayList<Integer>();
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(connectionUrl);
            String SQL = "SELECT * FROM account";
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);
            while (rs.next()) {
                accounts.add(rs.getInt("AbonneeNummer"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try {
                rs.close();
            } catch (Exception e) {
            }
            if (stmt != null) try {
                stmt.close();
            } catch (Exception e) {
            }
            if (con != null) try {
                con.close();
            } catch (Exception e) {
            }
        }
        return accounts;
    }

    public String getFirstAccountId() {
        ResultSet rs = null;
        Connection con = null;
        Statement stmt = null;
        String abonneeNummer = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(connectionUrl);
            String SQL = "SELECT TOP 1 * FROM account";
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);
            while (rs.next()) {
                abonneeNummer = String.valueOf(rs.getInt("AbonneeNummer"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return abonneeNummer;
    }

    public ResultSet getAccountInformation(Object account) {
        ResultSet rs = null;
        Connection con = null;
        Statement stmt = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(connectionUrl);
            String SQL = "SELECT * FROM account WHERE Abonneenummer = '" + String.valueOf(account) + "'";
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet getFirstAccountInformation() {
        ResultSet rs = null;
        Connection con = null;
        Statement stmt = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(connectionUrl);
            String SQL = "SELECT TOP 1* FROM account";
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet getFilmsWatchedByAccount(String account) {
        ResultSet rs = null;
        Connection con = null;
        Statement stmt = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(connectionUrl);
            String SQL = "SELECT Account.AbonneeNummer, Film.Titel, Film.Id FROM Film, Account, Profiel, Bekeken, Aanbod WHERE Account.AbonneeNummer = '" + account + "' AND Account.AbonneeNummer = Profiel.AbonneeNummer AND Profiel.AbonneeNummer = Bekeken.AbonneeNummer AND Bekeken.Gezien = Aanbod.Id AND Aanbod.Id = Film.Id GROUP BY Account.AbonneeNummer, Film.Titel, Film.Id ORDER BY Account.AbonneeNummer";
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    public ArrayList<String> getAllFilmTitles() {
        ArrayList<String> films = new ArrayList<String>();
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(connectionUrl);
            String SQL = "SELECT * FROM Film";
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);
            while (rs.next()) {
                films.add(rs.getString("Titel"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try {
                rs.close();
            } catch (Exception e) {
            }
            if (stmt != null) try {
                stmt.close();
            } catch (Exception e) {
            }
            if (con != null) try {
                con.close();
            } catch (Exception e) {
            }
        }
        return films;
    }

    public ArrayList<String> getAllSerieTitles() {
        ArrayList<String> series = new ArrayList<String>();
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(connectionUrl);
            String SQL = "SELECT * FROM Serie";
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);
            while (rs.next()) {
                series.add(rs.getString("Serie"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try {
                rs.close();
            } catch (Exception e) {
            }
            if (stmt != null) try {
                stmt.close();
            } catch (Exception e) {
            }
            if (con != null) try {
                con.close();
            } catch (Exception e) {
            }
        }
        return series;
    }

    public ResultSet getFirstFilmInformation() {
        ResultSet rs = null;
        Connection con = null;
        Statement stmt = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(connectionUrl);
            String SQL = "SELECT TOP 1 * FROM Film";
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet getFilmInformation(Object film) {
        ResultSet rs = null;
        Connection con = null;
        Statement stmt = null;
        String newFilmString = String.valueOf(film).replaceAll("'","''");
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(connectionUrl);
            String SQL = "SELECT * FROM Film WHERE Titel = '"+ newFilmString +"'";
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }
}

