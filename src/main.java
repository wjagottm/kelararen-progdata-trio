import javax.swing.*;
import java.sql.*;

/**
 * Dit is een voorbeeld Java toepassing waarin je verbinding maakt met een SQLServer database.
 */
public class main {
    public static void main(String[] args) {
        //SQL Connection
        SQLConnection connection = new SQLConnection();

        //Interface
        SwingUtilities.invokeLater(new UserInterface());
    }
}