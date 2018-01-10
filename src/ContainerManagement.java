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
        singleProfileAccountString.setFont(new Font("Serif", Font.BOLD, 30));

        singleProfileAccounts.add(singleProfileAccountString);
        ResultSet singleProfileAccountRs = connection.getSingleProfileAccounts();

        try {
            if (!singleProfileAccountRs.isBeforeFirst()) {
                JLabel noAccountsFoundString = new JLabel("No Accounts found in database with one profile.");

                noAccountsFoundString.setFont(new Font("Serif", Font.PLAIN, 30));

                singleProfileAccounts.add(noAccountsFoundString);
            } else {
                JScrollPane singleProfileAccountsPane = new JScrollPane(new JTable(tableBuilder.buildTableModel(singleProfileAccountRs)));
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
        allSerieTitles.setFont(new Font("Serif", Font.PLAIN, 20));
        allProfileNames.setFont(new Font("Serif", Font.PLAIN, 20));
        seriesString.setFont(new Font("Serif", Font.BOLD, 30));
        accountsString.setFont(new Font("Serif", Font.BOLD, 30));
        serieInformationString.setFont(new Font("Serif", Font.BOLD, 30));
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
            serieInformationPane = new JScrollPane(new JTable(tableBuilder.buildTableModel(serieInformation)));

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

                serieInformationPane = new JScrollPane(new JTable(tableBuilder.buildTableModel(serieInformation)));

                allSeries.add(serieInformationPane);

                allSeries.revalidate();
                allSeries.repaint();
            } else {
                serieInformationPane = new JScrollPane(new JTable(tableBuilder.buildTableModel(serieInformation)));

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

        JLabel longestFilmDurationUnderAgeSixteenString = new JLabel("Movie with longest duration for PEGI 16:");
        longestFilmDurationUnderAgeSixteenString.setFont(new Font("Serif", Font.BOLD, 30));

        ResultSet longestFilmDurationUnderAgeSixteen = connection.getLongestFilmDurationUnderAgeSixteen();
        try {
            JScrollPane longestFilmDurationUnderAgeSixteenPane = new JScrollPane(new JTable(tableBuilder.buildTableModel(longestFilmDurationUnderAgeSixteen)));
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
        allFilmTitles.setFont(new Font("Serif", Font.PLAIN, 20));
        allFilmTitles.setMaximumSize(new Dimension(8000,50));
        allFilmTitles.setBackground(Color.getHSBColor(167,0,10));
        allFilmTitlesString.setFont(new Font("Serif", Font.BOLD, 30));

        allFilms.add(allFilmTitlesString);
        allFilms.add(allFilmTitles);

        ResultSet firstFilmInformationRs = connection.getFirstFilmInformation();
        try {
            JTable filmTable = new JTable(tableBuilder.buildTableModel(firstFilmInformationRs));
            filmTablePane1 = new JScrollPane(filmTable);

            JLabel filmInformation = new JLabel("Movie information:");
            amountOfTimesFullyWatchedString = new JLabel("Amount of times fully watched:");
            amountOfTimesFullyWatched = new JLabel(String.valueOf(connection.getFirstFilmAmountOfFullWatches()));


            filmInformation.setFont(new Font("Serif", Font.BOLD, 30));
            filmTablePane1.setFont(new Font("Serif", Font.PLAIN, 30));
            amountOfTimesFullyWatchedString.setFont(new Font("Serif", Font.BOLD, 30));
            amountOfTimesFullyWatched.setFont(new Font("Serif", Font.BOLD, 40));
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
                        JTable filmInformationTable = new JTable(tableBuilder.buildTableModel(filmInformationRs));

                        allFilms.remove(filmTablePane1);
                        allFilms.remove(amountOfTimesFullyWatchedString);
                        allFilms.remove(amountOfTimesFullyWatched);
                        filmTablePane1 = new JScrollPane(filmInformationTable);
                        amountOfTimesFullyWatched = new JLabel(String.valueOf(connection.getFilmAmountOfTimesFullWatches(itemEvent.getItem())));

                        amountOfTimesFullyWatched.setFont(new Font("Serif", Font.BOLD, 40));

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
}
