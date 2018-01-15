//The idGrabber class is to get all unique identifiers and put them into a JCombobox to keep the containerManagement class as clean as possible.

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
        for (String profileName : accounts) {
            profileNameBox.addItem(profileName);
        }
        return profileNameBox;
    }

    public JComboBox getAllFilmAndShowIds() {
        ArrayList<Integer> ids = connection.getAllFilmAndShowIds();
        JComboBox filmAndShowBox = new JComboBox();
        for (int id : ids) {
            filmAndShowBox.addItem(id);
        }
        return filmAndShowBox;
    }
    public JComboBox getAllFilmAndShowIds(Object subscriberId, Object profileName) {
        ArrayList<Integer> ids = connection.getAllFilmAndShowIds(subscriberId, profileName);
        JComboBox filmAndShowBox = new JComboBox();
        for (int id : ids) {
            filmAndShowBox.addItem(id);
        }
        return filmAndShowBox;
    }

}
