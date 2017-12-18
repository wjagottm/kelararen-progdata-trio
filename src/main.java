import javax.swing.*;

public class main {
    public static void main(String[] args) {
        //SQL Connection
        SQLConnection connection = new SQLConnection();

        //Interface
        SwingUtilities.invokeLater(new UserInterface());
    }
}