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
        JComboBox filmTitleBox = new JComboBox();
        for (String filmTitle : films) {
            filmTitleBox.addItem(filmTitle);
        }
        return filmTitleBox;
    }

    public JComboBox getAllSerieTitles() {
        ArrayList<String> series = connection.getAllSerieTitles();
        JComboBox serieTitleBox = new JComboBox();
        for (String serieTitle : series) {
            serieTitleBox.addItem(serieTitle);
        }
        return serieTitleBox;
    }

    public JComboBox getAllProfileNames() {
        ArrayList<String> accounts = connection.getAllProfileNames();
        JComboBox accountNameBox = new JComboBox();
        for (String accountName : accounts) {
            accountNameBox.addItem(accountName);
        }
        return accountNameBox;
    }

    public JComboBox getAllProfileNames(Object subscriberId) {
        ArrayList<String> accounts = connection.getAllProfileNames(subscriberId);
        JComboBox profileNameBox = new JComboBox();
        for (String accountName : accounts) {
            profileNameBox.addItem(accountName);
        }
        return profileNameBox;
    }

}
