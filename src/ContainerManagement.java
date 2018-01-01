import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.util.HashMap;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class ContainerManagement {

    private HashMap<String, Container> containers;
    private Container currentContainer;

    private SQLConnection connection = new SQLConnection();
    private idGrabber idGrabber = new idGrabber();
    private tableBuilder tableBuilder = new tableBuilder();

    private JScrollPane tablePane1 = null;
    private JScrollPane tablePane2 = null;
    private JLabel watchedFilms = new JLabel("Watched Movies:");

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

    public void accountsContainer() {
        Container accountContainer = new Container();

        JComboBox accountList = idGrabber.getAccountId();

        //Design settings
        accountContainer.setLayout(new BoxLayout(accountContainer, BoxLayout.Y_AXIS));
        accountList.setFont(new Font("Serif", Font.PLAIN, 20));
        accountList.setMaximumSize(new Dimension(8000,50));
        accountList.setBackground(Color.getHSBColor(167,0,10));


        accountContainer.add(accountList);

        ResultSet accountInformationRs = connection.getFirstAccountInformation();
        ResultSet accountWatchedFilmsRs = connection.getFilmsWatchedByAccount(connection.getFirstAccountId());
        try {
            if (!accountWatchedFilmsRs.isBeforeFirst() ) {
                this.tablePane2 = null;
            } else {
                JTable accountWatchedFilmsTable = new JTable(tableBuilder.buildTableModel(accountWatchedFilmsRs));
                tablePane2 = new JScrollPane(accountWatchedFilmsTable);
            }

            JTable table = new JTable(tableBuilder.buildTableModel(accountInformationRs));
            tablePane1 = new JScrollPane(table);

            JLabel profileInformation = new JLabel("Profile information:");

            //Design settings
            profileInformation.setFont(new Font("Serif", Font.BOLD, 30));
            tablePane1.setFont(new Font("Serif", Font.PLAIN, 30));
            tablePane1.setBackground(Color.WHITE);
            watchedFilms.setFont(new Font("Serif", Font.BOLD, 30));

            accountContainer.add(profileInformation);
            accountContainer.add(tablePane1);

            if(tablePane2 != null) {
                accountContainer.add(tablePane2);
            }

            accountInformationRs.close();
            accountWatchedFilmsRs.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        ItemListener itemListener = new ItemListener() {
            public void itemStateChanged(ItemEvent itemEvent) {
                int state = itemEvent.getStateChange();
                if( state == 1) {
                    ResultSet accountInformationRs = connection.getAccountInformation(itemEvent.getItem());
                    ResultSet accountWatchedFilmsRs = connection.getFilmsWatchedByAccount(String.valueOf(itemEvent.getItem()));
                    try {
                        if (!accountWatchedFilmsRs.isBeforeFirst() ) {
                            if(tablePane2 != null) {
                                accountContainer.remove(watchedFilms);
                                accountContainer.remove(tablePane2);
                                tablePane2 = null;
                            }
                        } else {
                            JTable accountWatchedFilmsTable = new JTable(tableBuilder.buildTableModel(accountWatchedFilmsRs));

                            if(tablePane2 != null) {
                                accountContainer.remove(tablePane2);
                            }

                            tablePane2 = new JScrollPane(accountWatchedFilmsTable);
                        }
                        JTable accountInformationTable = new JTable(tableBuilder.buildTableModel(accountInformationRs));

                        accountContainer.remove(tablePane1);
                        tablePane1 = new JScrollPane(accountInformationTable);
                        accountContainer.add(tablePane1);

                        if(tablePane2 != null) {
                            accountContainer.add(tablePane2);
                        }


                        accountContainer.revalidate();
                        accountContainer.repaint();

                        accountInformationRs.close();
                        accountWatchedFilmsRs.close();
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            }
        };
        accountList.addItemListener(itemListener);

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

    public void getAllSeriesContainer() {
        Container allSeries = new Container();

        JComboBox allSerieTitles = idGrabber.getAllSerieTitles();

        //Design
        allSeries.setLayout(new BoxLayout(allSeries, BoxLayout.Y_AXIS));
        allSerieTitles.setFont(new Font("Serif", Font.PLAIN, 20));
        allSerieTitles.setMaximumSize(new Dimension(8000,50));
        allSerieTitles.setBackground(Color.getHSBColor(167,0,10));

        allSeries.add(allSerieTitles);
        add("allSeries", allSeries);
    }

    public void getAllFilmsContainer() {
        Container allFilms = new Container();

        JComboBox allFilmTitles = idGrabber.getAllFilmTitles();

        //Design
        allFilms.setLayout(new BoxLayout(allFilms, BoxLayout.Y_AXIS));
        allFilmTitles.setFont(new Font("Serif", Font.PLAIN, 20));
        allFilmTitles.setMaximumSize(new Dimension(8000,50));
        allFilmTitles.setBackground(Color.getHSBColor(167,0,10));

        allFilms.add(allFilmTitles);

        add("allFilms", allFilms);
    }
}
