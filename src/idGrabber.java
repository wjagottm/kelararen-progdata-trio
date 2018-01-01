import javax.swing.*;
import java.util.ArrayList;

public class idGrabber {
    private SQLConnection connection = new SQLConnection();

    public idGrabber() {

    }

    public JComboBox getAccountId() {
        ArrayList<Integer> accountIds = connection.getAccountId();
        JComboBox accountIdBox = new JComboBox();
        for (Integer accountID : accountIds) {
            accountIdBox.addItem(accountID);
        }
        return accountIdBox;
    }

}
