import javax.swing.*;
import java.util.ArrayList;

public class idGrabber {
    private SQLConnection connection = new SQLConnection();

    public JComboBox getAccountId() {
        ArrayList<Integer> accountIds = connection.getAccountId();
        JComboBox accountIdBox = new JComboBox();
        for (Integer accountID : accountIds) {
            accountIdBox.addItem(accountID);
        }
        return accountIdBox;
    }

    public JComboBox getAllFilmTitles() {
        ArrayList<String> films = connection.getAllFilmTitles();
        JComboBox filmTitleBoxBox = new JComboBox();
        for (String accountID : films) {
            filmTitleBoxBox.addItem(accountID);
        }
        return filmTitleBoxBox;
    }

    public JComboBox getAllSerieTitles() {
        ArrayList<String> series = connection.getAllSerieTitles();
        JComboBox serieTitleBoxBox = new JComboBox();
        for (String accountID : series) {
            serieTitleBoxBox.addItem(accountID);
        }
        return serieTitleBoxBox;
    }

}
