import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class ContainerManagement {

    private HashMap<String, Container> containers;
    private Container currentContainer;

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
        accountContainer.setLayout(new BoxLayout(accountContainer, BoxLayout.Y_AXIS));
        accountContainer.add(new JLabel("Test"));
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
