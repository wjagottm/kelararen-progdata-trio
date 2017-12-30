import java.sql.*;
import java.util.ArrayList;

public class SQLConnection {

    private String connectionUrl = "jdbc:sqlserver://netflixstatistixsv1.database.windows.net:1433;database=NetflixStatistix;user=kelararen@netflixstatistixsv1;password=Welkom01!;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
    private Connection con = null;
    private Statement stmt = null;
    private ResultSet rs = null;

        public SQLConnection() {

        }

        public void testCon() {
            try {
                // 'Importeer' de driver die je gedownload hebt.
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                // Maak de verbinding met de database.
                con = DriverManager.getConnection(connectionUrl);

                // Stel een SQL query samen.
                String SQL = "SELECT * FROM account";
                stmt = con.createStatement();
                // Voer de query uit op de database.
                rs = stmt.executeQuery(SQL);

                //System.out.print(String.format("| %-10s | %-32s | %-24s |\n", " ", " ", " ").replace(" ", "-"));

                // Als de resultset waarden bevat dan lopen we hier door deze waarden en printen ze.
                while (rs.next()) {
                    // Vraag per row de kolommen in die row op.
                    int ISBN = rs.getInt("AbonneeNummer");
                    String title = rs.getString("Naam");
                    String author = rs.getString("WoonPlaats");

                    // Print de kolomwaarden.
                    System.out.println(ISBN + " " + title + " " + author);

                    // Met 'format' kun je de string die je print het juiste formaat geven, als je dat wilt.
                    // %d = decimal, %s = string, %-32s = string, links uitgelijnd, 32 characters breed.
                    //System.out.format("| %-10d | %-32s | %-24s | \n", ISBN, title, author);
                }
                //System.out.println(String.format("| %-10s | %-32s | %-24s |\n", " ", " ", " ").replace(" ", "-"));
            }
            // Handle any errors that may have occurred.
            catch (Exception e) {
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
            }
            catch (Exception e) {
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
}
