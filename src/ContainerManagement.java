import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.ItemSelectable;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class ContainerManagement {

    private HashMap<String, Container> containers;
    private Container currentContainer;
    private SQLConnection connection = new SQLConnection();

    public ContainerManagement() {
        this.containers = new HashMap<String, Container>();
        this.currentContainer = null;
    }

    public void add(String key, Container container) {
        this.containers.put(key, container);
    }

    public Container get(String key) {
        if(this.containers.containsKey(key)) {
            return this.containers.get(key);
        } else {
            return null;
        }
    }

    public void placeCurrentContainer(Container container) {
        this.currentContainer = container;
    }

    public Container grabCurrentContainer() {
        return this.currentContainer;
    }

    public void accounts() {
        Container accountContainer = new Container();

        ArrayList<Integer> accountIds = connection.getAccountId();
        JComboBox accountList = new JComboBox();
        for (Integer accountID : accountIds) {
            accountList.addItem(accountID);
        }

        ItemListener itemListener = new ItemListener() {
            public void itemStateChanged(ItemEvent itemEvent) {
                int state = itemEvent.getStateChange();
                System.out.println((state == ItemEvent.SELECTED) ? "Selected" : "Deselected");
                System.out.println("Item: " + itemEvent.getItem());
            }
        };
        accountList.addItemListener(itemListener);
        accountList.setMaximumSize(new Dimension(250,50));

        accountContainer.setLayout(new BoxLayout(accountContainer, BoxLayout.Y_AXIS));
        accountContainer.add(accountList);
        add("allAccounts", accountContainer);
    }

    public void filmUnderSixteenContainer() {
        Container filmUnderSixteenContainer = new Container();
        filmUnderSixteenContainer.setLayout(new BoxLayout(filmUnderSixteenContainer, BoxLayout.Y_AXIS));
        filmUnderSixteenContainer.add(new JLabel("Films"));
        add("filmUnderSixteen", filmUnderSixteenContainer);
    }

    public void singleProfileAccounts() {
        Container singleProfileAccounts = new Container();
        singleProfileAccounts.setLayout(new BoxLayout(singleProfileAccounts, BoxLayout.Y_AXIS));
        singleProfileAccounts.add(new JLabel("Single profile Accounts"));
        add("singleProfileAccounts", singleProfileAccounts);
    }

    public void getAllSeries() {
        Container allSeries = new Container();
        allSeries.setLayout(new BoxLayout(allSeries, BoxLayout.Y_AXIS));
        allSeries.add(new JLabel("All current series"));
        add("allSeries", allSeries);
    }

    public void getAllFilms() {
        Container allFilms = new Container();
        allFilms.setLayout(new BoxLayout(allFilms, BoxLayout.Y_AXIS));
        allFilms.add(new JLabel("All current films"));
        add("allFilms", allFilms);
    }
}
