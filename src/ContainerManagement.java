import com.sun.deploy.panel.JavaPanel;

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

    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private int tableHeight = (int) Math.round(screenSize.getHeight()) / 64;
    private int bigFontSize = (int) Math.round(screenSize.getHeight()) / 64;
    private int smallFontSize = (int) Math.round(screenSize.getHeight()) / 96;

    //Variables for account tab
    private JScrollPane tablePane1 = null;
    private JScrollPane tablePane2 = null;
    private JLabel watchedFilms = new JLabel("Watched Movies:");

    //Variables for film tab
    private JScrollPane filmTablePane1 = null;
    private JLabel amountOfTimesFullyWatchedString = null;
    private JLabel amountOfTimesFullyWatched = null;

    //Variables for series tab
    private JScrollPane serieInformationPane = null;

    //Variables for create tab
    private JPanel createValues = null;

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
        accountList.setFont(new Font("Serif", Font.PLAIN, smallFontSize));
        watchedFilms.setFont(new Font("Serif", Font.BOLD, bigFontSize));
        accountList.setMaximumSize(new Dimension(8000,50));
        accountList.setBackground(Color.getHSBColor(167,0,10));


        accountContainer.add(accountList);

        ResultSet accountInformationRs = connection.getFirstAccountInformation();
        ResultSet accountWatchedFilmsRs = connection.getFilmsWatchedByAccount(connection.getFirstAccountId());
        try {
            if (!accountWatchedFilmsRs.isBeforeFirst() ) {
                this.tablePane2 = null;
            } else {
                JTable accountWatchedFilmsTable = new JTable(tableBuilder.buildTableModel(accountWatchedFilmsRs)){
                    public boolean isCellEditable(int row,int column){
                        return true;
                    }
                };
                accountWatchedFilmsTable.setRowHeight(tableHeight);
                tablePane2 = new JScrollPane(accountWatchedFilmsTable);
            }

            JTable table = new JTable(tableBuilder.buildTableModel(accountInformationRs)){
                public boolean isCellEditable(int row,int column){
                    return true;
                }
            };
            table.setRowHeight(tableHeight);
            tablePane1 = new JScrollPane(table);

            JLabel profileInformation = new JLabel("Profile information:");

            //Design settings
            tablePane1.setBackground(Color.WHITE);

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
                            JTable accountWatchedFilmsTable = new JTable(tableBuilder.buildTableModel(accountWatchedFilmsRs)){
                                public boolean isCellEditable(int row,int column){
                                    return true;
                                }
                            };
                            accountWatchedFilmsTable.setRowHeight(tableHeight);

                            if(tablePane2 != null) {
                                accountContainer.remove(tablePane2);
                            }

                            tablePane2 = new JScrollPane(accountWatchedFilmsTable);
                        }
                        JTable accountInformationTable = new JTable(tableBuilder.buildTableModel(accountInformationRs)){
                            public boolean isCellEditable(int row,int column){
                                return true;
                            }
                        };

                        accountInformationTable.setRowHeight(tableHeight);

                        accountContainer.remove(tablePane1);
                        tablePane1 = new JScrollPane(accountInformationTable);
                        accountContainer.add(tablePane1);

                        if(tablePane2 != null) {
                            accountContainer.add(watchedFilms);
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

    public void singleProfileAccounts() {
        Container singleProfileAccounts = new Container();
        JLabel singleProfileAccountString = new JLabel("Single profile Accounts: ");

        //Design settings
        singleProfileAccounts.setLayout(new BoxLayout(singleProfileAccounts, BoxLayout.Y_AXIS));

        singleProfileAccounts.add(singleProfileAccountString);
        ResultSet singleProfileAccountRs = connection.getSingleProfileAccounts();

        try {
            if (!singleProfileAccountRs.isBeforeFirst()) {
                JLabel noAccountsFoundString = new JLabel("No Accounts found in database with one profile. Go to the create page to add new accounts and profiles.");

                noAccountsFoundString.setFont(new Font("Serif", Font.PLAIN, bigFontSize));

                singleProfileAccounts.add(noAccountsFoundString);
            } else {
                JTable singleProfileAccountsTable = new JTable(tableBuilder.buildTableModel(singleProfileAccountRs)){
                    public boolean isCellEditable(int row,int column){
                        return true;
                    }
                };

                singleProfileAccountsTable.setRowHeight(tableHeight);
                JScrollPane singleProfileAccountsPane = new JScrollPane(singleProfileAccountsTable);
                singleProfileAccounts.add(singleProfileAccountsPane);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        add("singleProfileAccounts", singleProfileAccounts);
    }

    public void getAllSeriesContainer() {
        Container allSeries = new Container();

        JComboBox allSerieTitles = idGrabber.getAllSerieTitles();
        JComboBox allProfileNames = idGrabber.getAllProfileNames();
        JLabel seriesString = new JLabel("Select a show:");
        JLabel accountsString = new JLabel("Select a account:");
        JLabel serieInformationString = new JLabel("Show Information:");

        //Design
        allSeries.setLayout(new BoxLayout(allSeries, BoxLayout.Y_AXIS));
        allSerieTitles.setFont(new Font("Serif", Font.PLAIN, smallFontSize));
        allProfileNames.setFont(new Font("Serif", Font.PLAIN, smallFontSize));
        allSerieTitles.setMaximumSize(new Dimension(8000,50));
        allSerieTitles.setBackground(Color.getHSBColor(167,0,10));
        allProfileNames.setMaximumSize(new Dimension(8000,50));
        allProfileNames.setBackground(Color.getHSBColor(167,0,10));

        ItemListener serieListener = new ItemListener() {
            public void itemStateChanged(ItemEvent itemEvent) {
                int state = itemEvent.getStateChange();
                if (state == 1) {
                    ResultSet serieInformation = connection.getSerieInformation(itemEvent.getItem(), allProfileNames.getSelectedItem());
                    addShowInformationPanel(serieInformation, allSeries);
                }
            }
        };

        ItemListener accountListener = new ItemListener() {
            public void itemStateChanged(ItemEvent itemEvent) {
                int state = itemEvent.getStateChange();
                if (state == 1) {
                    ResultSet serieInformation = connection.getSerieInformation(allSerieTitles.getSelectedItem(), itemEvent.getItem());
                    addShowInformationPanel(serieInformation, allSeries);
                }
            }
        };

        allProfileNames.addItemListener(accountListener);
        allSerieTitles.addItemListener(serieListener);

        allSeries.add(accountsString);
        allSeries.add(allProfileNames);
        allSeries.add(seriesString);
        allSeries.add(allSerieTitles);
        allSeries.add(serieInformationString);

        ResultSet serieInformation = connection.getSerieInformation(allSerieTitles.getSelectedItem(), allProfileNames.getSelectedItem());
        try {
            JTable serieInformationTable = new JTable(tableBuilder.buildTableModel(serieInformation)){
                public boolean isCellEditable(int row,int column){
                    return true;
                }
            };
            serieInformationTable.setRowHeight(tableHeight);
            serieInformationPane = new JScrollPane(serieInformationTable);
            allSeries.add(serieInformationPane);
        } catch (Exception e) {
            e.printStackTrace();
        }

        add("allSeries", allSeries);
    }

    private void addShowInformationPanel(ResultSet serieInformation, Container allSeries) {
        try {
            if (serieInformationPane != null) {
                allSeries.remove(serieInformationPane);

                JTable serieInformationTable = new JTable(tableBuilder.buildTableModel(serieInformation)){
                    public boolean isCellEditable(int row,int column){
                        return true;
                    }
                };
                serieInformationTable.setRowHeight(tableHeight);

                serieInformationPane = new JScrollPane(serieInformationTable);
                allSeries.add(serieInformationPane);

                allSeries.revalidate();
                allSeries.repaint();
            } else {
                JTable serieInformationTable = new JTable(tableBuilder.buildTableModel(serieInformation)){
                    public boolean isCellEditable(int row,int column){
                        return true;
                    }
                };
                serieInformationTable.setRowHeight(tableHeight);

                serieInformationPane = new JScrollPane(serieInformationTable);
                allSeries.add(serieInformationPane);

                allSeries.revalidate();
                allSeries.repaint();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getAllFilmsContainer() {
        Container allFilms = new Container();

        JLabel longestFilmDurationUnderAgeSixteenString = new JLabel("Movie with longest duration for PG 16:");

        ResultSet longestFilmDurationUnderAgeSixteen = connection.getLongestFilmDurationUnderAgeSixteen();
        try {
            JTable longestFilmDurationUnderAgeSixteenTable = new JTable(tableBuilder.buildTableModel(longestFilmDurationUnderAgeSixteen)){
                public boolean isCellEditable(int row,int column){
                    return true;
                }
            };
            longestFilmDurationUnderAgeSixteenTable.setRowHeight(tableHeight);

            JScrollPane longestFilmDurationUnderAgeSixteenPane = new JScrollPane(longestFilmDurationUnderAgeSixteenTable);
            longestFilmDurationUnderAgeSixteenPane.setPreferredSize(new Dimension(3000,80));

            allFilms.add(longestFilmDurationUnderAgeSixteenString);
            allFilms.add(longestFilmDurationUnderAgeSixteenPane);
        } catch (Exception e) {
            System.out.println(e);
        }

        JSeparator seperator = new JSeparator(SwingConstants.HORIZONTAL);
        allFilms.add(seperator);

        JLabel allFilmTitlesString = new JLabel("Select a Movie title:");
        JComboBox allFilmTitles = idGrabber.getAllFilmTitles();

        //Design
        allFilms.setLayout(new BoxLayout(allFilms, BoxLayout.Y_AXIS));
        allFilmTitles.setFont(new Font("Serif", Font.PLAIN, smallFontSize));
        allFilmTitles.setMaximumSize(new Dimension(8000,50));
        allFilmTitles.setBackground(Color.getHSBColor(167,0,10));

        allFilms.add(allFilmTitlesString);
        allFilms.add(allFilmTitles);

        ResultSet firstFilmInformationRs = connection.getFirstFilmInformation();
        try {
            JTable filmTable = new JTable(tableBuilder.buildTableModel(firstFilmInformationRs)){
                public boolean isCellEditable(int row,int column){
                    return true;
                }
            };
            filmTable.setRowHeight(tableHeight);
            filmTablePane1 = new JScrollPane(filmTable);

            JLabel filmInformation = new JLabel("Movie information:");
            amountOfTimesFullyWatchedString = new JLabel("Amount of times fully watched:");
            amountOfTimesFullyWatched = new JLabel(String.valueOf(connection.getFirstFilmAmountOfFullWatches()));


            amountOfTimesFullyWatched.setFont(new Font("Serif", Font.BOLD, bigFontSize));
            filmTablePane1.setBackground(Color.WHITE);

            allFilms.add(filmInformation);
            allFilms.add(filmTablePane1);
            allFilms.add(amountOfTimesFullyWatchedString);
            allFilms.add(amountOfTimesFullyWatched);



            firstFilmInformationRs.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        ItemListener itemListener = new ItemListener() {
            public void itemStateChanged(ItemEvent itemEvent) {
                int state = itemEvent.getStateChange();
                if(state == 1) {
                    ResultSet filmInformationRs = connection.getFilmInformation(itemEvent.getItem());
                    try{
                        JTable filmInformationTable = new JTable(tableBuilder.buildTableModel(filmInformationRs)){
                            public boolean isCellEditable(int row,int column){
                                return true;
                            }
                        };
                        filmInformationTable.setRowHeight(tableHeight);

                        allFilms.remove(filmTablePane1);
                        allFilms.remove(amountOfTimesFullyWatchedString);
                        allFilms.remove(amountOfTimesFullyWatched);
                        filmTablePane1 = new JScrollPane(filmInformationTable);
                        amountOfTimesFullyWatched = new JLabel(String.valueOf(connection.getFilmAmountOfTimesFullWatches(itemEvent.getItem())));

                        amountOfTimesFullyWatched.setFont(new Font("Serif", Font.BOLD, bigFontSize));

                        allFilms.add(filmTablePane1);
                        allFilms.add(amountOfTimesFullyWatchedString);
                        allFilms.add(amountOfTimesFullyWatched);


                        allFilms.revalidate();
                        allFilms.repaint();

                        filmInformationRs.close();
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            }
        };
        allFilmTitles.addItemListener(itemListener);

        add("allFilms", allFilms);
    }

    public void createValuesContainer() {
        Container createValuesContainer = new Container();

        createValuesContainer.setLayout(new BoxLayout(createValuesContainer, BoxLayout.Y_AXIS));

        createValues = new JPanel();
        JLabel selectPageString = new JLabel("Select a table to edit");

        JComboBox pagesBox = new JComboBox();
        pagesBox.addItem("Users");
        pagesBox.addItem("Profiles");
        pagesBox.addItem("Watched");
        pagesBox.addItem("Library");
        pagesBox.addItem("Movies");
        pagesBox.addItem("Episodes");
        pagesBox.addItem("Shows");


        pagesBox.setMaximumSize(new Dimension(8000,50));
        pagesBox.setBackground(Color.getHSBColor(167,0,10));
        createValues.setBackground(Color.WHITE);

        JLabel UsersString = new JLabel("Add Users");
        createValues.add(UsersString);


        ItemListener itemListener = new ItemListener() {
            public void itemStateChanged(ItemEvent itemEvent) {
                int state = itemEvent.getStateChange();
                if(state == 1) {
                    if(itemEvent.getItem() == "Users") {
                        createValues.removeAll();
                        JLabel UsersString = new JLabel("Add Users");
                        createValues.add(UsersString);

                        updateCreateValuesContainer(createValuesContainer);
                    } else if (itemEvent.getItem() == "Profiles") {
                        createValues.removeAll();
                        JLabel UsersString = new JLabel("Add Profiles");
                        createValues.add(UsersString);

                        updateCreateValuesContainer(createValuesContainer);

                    } else if (itemEvent.getItem() == "Watched") {
                        createValues.removeAll();
                        JLabel UsersString = new JLabel("Add Watched");
                        createValues.add(UsersString);

                        updateCreateValuesContainer(createValuesContainer);

                    } else if (itemEvent.getItem() == "Library") {
                        createValues.removeAll();
                        JLabel UsersString = new JLabel("Add Library");
                        createValues.add(UsersString);

                        updateCreateValuesContainer(createValuesContainer);

                    } else if (itemEvent.getItem() == "Movies") {
                        createValues.removeAll();
                        JLabel UsersString = new JLabel("Add Movies");
                        createValues.add(UsersString);

                        updateCreateValuesContainer(createValuesContainer);

                    } else if (itemEvent.getItem() == "Episodes") {
                        createValues.removeAll();
                        JLabel UsersString = new JLabel("Add Episodes");
                        createValues.add(UsersString);

                        updateCreateValuesContainer(createValuesContainer);

                    } else if (itemEvent.getItem() == "Shows") {
                        createValues.removeAll();
                        JLabel UsersString = new JLabel("Add Shows");
                        createValues.add(UsersString);

                        updateCreateValuesContainer(createValuesContainer);
                    }
                }
            }
        };
        pagesBox.addItemListener(itemListener);


        createValuesContainer.add(selectPageString);
        createValuesContainer.add(pagesBox);

        createValuesContainer.add(createValues);

        add("creator", createValuesContainer);
    }

    public void updateCreateValuesContainer(Container container) {
        container.add(createValues);

        container.revalidate();
        container.repaint();
    }
}
