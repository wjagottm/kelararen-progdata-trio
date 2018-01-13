import javax.swing.*;
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
            String SQL = "SELECT * FROM Users";
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);
            while (rs.next()) {
                accounts.add(rs.getInt("SubscriberId"));
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
        String SubscriberId = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(connectionUrl);
            String SQL = "SELECT TOP 1 * FROM Users";
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);
            while (rs.next()) {
                SubscriberId = String.valueOf(rs.getInt("SubscriberId"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return SubscriberId;
    }

    public ResultSet getAccountInformation(Object account) {
        ResultSet rs = null;
        Connection con = null;
        Statement stmt = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(connectionUrl);
            String SQL = "SELECT * FROM Users WHERE SubscriberId = '" + String.valueOf(account) + "'";
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
            String SQL = "SELECT TOP 1* FROM Users";
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet getSingleProfileAccounts() {
        ResultSet rs = null;
        Connection con = null;
        Statement stmt = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(connectionUrl);
            String SQL = "SELECT Users.* FROM Users JOIN ( SELECT Users.SubscriberId FROM Users, Profiles WHERE Users.SubscriberId = Profiles.SubscriberId GROUP BY Users.SubscriberId HAVING Count(Profiles.SubscriberId) = 1) as singleAccountUsers ON Users.SubscriberId = singleAccountUsers.SubscriberId";
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
            String SQL = "SELECT Users.SubscriberId, Movies.Title, Movies.Id FROM Movies, Users, Profiles, Watched, Library WHERE Users.SubscriberId = '" + account + "' AND Users.SubscriberId = Profiles.SubscriberId AND Profiles.SubscriberId = Watched.SubscriberId AND Watched.Watched = Library.Id AND Library.Id = Movies.Id GROUP BY Users.SubscriberId, Movies.Title, Movies.Id ORDER BY Users.SubscriberId";
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
            String SQL = "SELECT * FROM Movies";
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);
            while (rs.next()) {
                films.add(rs.getString("Title"));
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

    public ArrayList<String> getAllAccountNames() {
        ArrayList<String> accounts = new ArrayList<>();
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(connectionUrl);
            String SQL = "SELECT * FROM Profiles";
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);
            while (rs.next()) {
                accounts.add(rs.getString("ProfileName"));
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

    public ArrayList<String> getAllSerieTitles() {
        ArrayList<String> series = new ArrayList<String>();
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(connectionUrl);
            String SQL = "SELECT * FROM Shows";
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);
            while (rs.next()) {
                series.add(rs.getString("Show"));
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
            String SQL = "SELECT TOP 1 * FROM Movies";
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
            String SQL = "SELECT * FROM Movies WHERE Title = '"+ newFilmString +"'";
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet getSerieInformation(Object film, Object account) {
        ResultSet rs = null;
        Connection con = null;
        Statement stmt = null;
        String newFilmString = String.valueOf(film).replaceAll("'","''");
        String newAccountString = String.valueOf(account).replaceAll("'","''");
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(connectionUrl);
            String SQL = "SELECT Episodes.*, ISNULL(WatchedId.AvgPercentage,0) AS avgAmountWatched, ISNULL(Profilename, '" + newAccountString + "') AS AccountName FROM Episodes LEFT JOIN (SELECT Watched.Watched, AVG(ISNULL(Percentage, 0)) AS AvgPercentage, Profilename FROM Watched WHERE ProfileName = '" + newAccountString + "' GROUP BY Watched.Watched, Profilename) AS WatchedId ON Episodes.Id = WatchedId.Watched WHERE Episodes.Show = '" + newFilmString + "' ORDER BY Id";
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }



    public String getFirstFilmTitle() {
        ResultSet rs = null;
        Connection con = null;
        Statement stmt = null;
        String filmTitle = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(connectionUrl);
            String SQL = "SELECT TOP 1 * FROM Movies";
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);
            while(rs.next()) {
                filmTitle = rs.getString("Title").replaceAll("'", "''");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return filmTitle;
    }

    public int getFirstFilmAmountOfFullWatches() {
        ResultSet rs = null;
        Connection con = null;
        Statement stmt = null;
        int amountOfTimes = 0;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(connectionUrl);
            String SQL = "SELECT Watched.SubscriberId FROM Movies, Library, Watched WHERE Watched.Percentage = '100' and Movies.Id = Library.Id and Library.Id = Watched.Watched and Movies.Title = '" + getFirstFilmTitle() + "'";
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);
            while(rs.next()) {
                amountOfTimes++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return amountOfTimes;
    }

    public int getFilmAmountOfTimesFullWatches(Object film) {
        ResultSet rs = null;
        Connection con = null;
        Statement stmt = null;
        String newFilmString = String.valueOf(film).replaceAll("'","''");
        int amountOfTimes = 0;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(connectionUrl);
            String SQL = "SELECT Watched.SubscriberId FROM Movies, Library, Watched WHERE Watched.Percentage = '100' and Movies.Id = Library.Id and Library.Id = Watched.Watched and Movies.Title = '" + newFilmString + "'";
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);
            while(rs.next()) {
                amountOfTimes++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return amountOfTimes;
    }

    public ResultSet getLongestFilmDurationUnderAgeSixteen() {
        ResultSet rs = null;
        Connection con = null;
        Statement stmt = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(connectionUrl);
            String SQL = "SELECT * FROM Movies WHERE (Length) IN (SELECT MAX(Length) FROM Movies);";
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    public void createNewAccount(String subscriberId, String name, String street, String postalCode, String houseNumber, String city, JFrame frame) {
        ResultSet rs = null;
        Connection con = null;
        Statement stmt = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(connectionUrl);
            String SQL = "INSERT INTO Users VALUES ("+ subscriberId +", '"+ name +"', '"+ street +"', '"+ postalCode +"', "+ houseNumber +", '"+ city +"')";
            stmt = con.createStatement();
            stmt.executeUpdate(SQL);


            JOptionPane.showMessageDialog(frame, "Account added succesfully!");

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "The inserted values where not correct.");
        }
    }

    public boolean removeAccount(JFrame frame, Object accountId){
        ResultSet rs = null;
        Connection con = null;
        Statement stmt = null;
        Boolean result;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(connectionUrl);
            String SQL = "DELETE FROM Users WHERE SubscriberId = "+accountId+"";
            stmt = con.createStatement();
            stmt.executeUpdate(SQL);

            JOptionPane.showMessageDialog(frame, "The user has been successfully removed!");
            result = true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "The user could not be removed!");
            e.printStackTrace();
            result = false;
        } finally {
            try {
                stmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}

