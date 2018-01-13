
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class ContainerManagement {

    private HashMap<String, Container> containers;
    private Container currentContainer;

    private SQLConnection connection = new SQLConnection();
    private idGrabber idGrabber = new idGrabber();
    private tableBuilder tableBuilder = new tableBuilder();
    private JFrame frame = null;

    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private int tableHeight = (int) Math.round(screenSize.getHeight()) / 64;
    private int bigFontSize = (int) Math.round(screenSize.getHeight()) / 64;
    private int smallFontSize = (int) Math.round(screenSize.getHeight()) / 96;

    //Variables for account tab
    private JScrollPane tablePane1 = null;
    private JScrollPane tablePane2 = null;
    private JLabel watchedFilms = new JLabel("Watched Movies:");

    //Variables for profiles tab
    private JComboBox profileList = null;
    private JButton deleteProfileButton = null;
    private JScrollPane profileInformationPane = null;
    private ItemListener profileListener = null;

    //Variables for film tab
    private JScrollPane filmTablePane1 = null;
    private JLabel amountOfTimesFullyWatchedString = null;
    private JLabel amountOfTimesFullyWatched = null;

    //Variables for series tab
    private JScrollPane serieInformationPane = null;

    //Variables for create tab
    private JPanel createValues = null;

    public ContainerManagement(JFrame frame) {
        this.containers = new HashMap<String, Container>();
        this.currentContainer = null;
        this.frame = frame;
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
        JLabel accountsString = new JLabel("Select a user:");

        final JPopupMenu popup = new JPopupMenu();
        popup.add(new JMenuItem(new AbstractAction("Edit User") {
            public void actionPerformed(ActionEvent e) {
                JPanel panel = new JPanel();
                JLabel labelName = new JLabel("Name ");
                JLabel labelStreet = new JLabel("Street");
                JLabel labelPostalCode = new JLabel("Postal Code");
                JLabel labelHouseNumber = new JLabel("House Number");
                JLabel labelCity = new JLabel("City");

                JTextField textName = new JTextField();
                JTextField textStreet = new JTextField();
                JTextField textPostalCode = new JTextField();
                JTextField textHouseNumber = new JTextField();
                JTextField textCity = new JTextField();

                try {
                    ResultSet rs = connection.getAccountInformation(accountList.getSelectedItem());
                    while (rs.next()) {
                        textName.setText(rs.getString("Name"));
                        textStreet.setText(rs.getString("Street"));
                        textPostalCode.setText(rs.getString("PostalCode"));
                        textHouseNumber.setText(rs.getString("HouseNumber"));
                        textCity.setText(rs.getString("City"));
                    }
                } catch (Exception a) {
                    a.printStackTrace();
                }

                panel.add(labelName);
                panel.add(textName);
                panel.add(labelStreet);
                panel.add(textStreet);
                panel.add(labelPostalCode);
                panel.add(textPostalCode);
                panel.add(labelHouseNumber);
                panel.add(textHouseNumber);
                panel.add(labelCity);
                panel.add(textCity);

                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

                Object[] options = {"Edit",
                        "Cancel"};



                JOptionPane.showOptionDialog(frame, panel, "Edit User",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
            }
        }));
        popup.add(new JMenuItem(new AbstractAction("Delete User") {
            public void actionPerformed(ActionEvent e) {
                if(connection.removeAccount(frame, accountList.getSelectedItem())) {
                    accountsContainer();
                    Container newContainer = get("allAccounts");
                    if (grabCurrentContainer() != null) {
                        frame.getContentPane().remove(grabCurrentContainer());
                    }
                    frame.getContentPane().add(newContainer, BorderLayout.CENTER);

                    placeCurrentContainer(newContainer);

                    frame.invalidate();
                    frame.validate();
                    frame.repaint();
                }
            }
        }));

        final JButton button = new JButton("Options");
        button.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                popup.show(e.getComponent(), e.getX(), e.getY());
            }
        });

        //Design settings
        accountContainer.setLayout(new BoxLayout(accountContainer, BoxLayout.Y_AXIS));
        accountList.setFont(new Font("Serif", Font.PLAIN, smallFontSize));
        watchedFilms.setFont(new Font("Serif", Font.BOLD, bigFontSize));
        accountList.setMaximumSize(new Dimension(8000,50));
        accountList.setBackground(Color.getHSBColor(167,0,10));

        accountContainer.add(accountsString);
        accountContainer.add(accountList);
        accountContainer.add(button);

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

    public void allProfilesContainer() {
        Container profilesContainer = new Container();


        JComboBox accountList = idGrabber.getAccountId();
        JLabel accountsString = new JLabel("Select a subcriberId:");

        profileList = idGrabber.getAllProfileNames(accountList.getSelectedItem());
        JLabel profilesString = new JLabel("Select a profile:");

        profilesContainer.setLayout(new BoxLayout(profilesContainer, BoxLayout.Y_AXIS));
        profileList.setMaximumSize(new Dimension(8000,50));
        profileList.setBackground(Color.getHSBColor(167,0,10));
        accountList.setMaximumSize(new Dimension(8000,50));
        accountList.setBackground(Color.getHSBColor(167,0,10));

        final JPopupMenu popup = new JPopupMenu();
        popup.add(new JMenuItem(new AbstractAction("Edit User") {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Option 1 selected");
            }
        }));
        popup.add(new JMenuItem(new AbstractAction("Delete User") {
            public void actionPerformed(ActionEvent e) {
                if(connection.removeProfile(frame, accountList.getSelectedItem(), profileList.getSelectedItem())) {
                    accountsContainer();
                    Container newContainer = get("allProfiles");
                    if (grabCurrentContainer() != null) {
                        frame.getContentPane().remove(grabCurrentContainer());
                    }
                    frame.getContentPane().add(newContainer, BorderLayout.CENTER);

                    placeCurrentContainer(newContainer);

                    frame.invalidate();
                    frame.validate();
                    frame.repaint();
                }
            }
        }));

        deleteProfileButton = new JButton("Options");
        deleteProfileButton.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                popup.show(e.getComponent(), e.getX(), e.getY());
            }
        });

        ItemListener accountListener = new ItemListener() {
            public void itemStateChanged(ItemEvent itemEvent) {
                int state = itemEvent.getStateChange();
                if (state == 1) {
                    profilesContainer.remove(profileList);
                    profilesContainer.remove(deleteProfileButton);
                    profilesContainer.remove(profileInformationPane);
                    profileList = idGrabber.getAllProfileNames(itemEvent.getItem());

                    profileList.setMaximumSize(new Dimension(8000,50));
                    profileList.setBackground(Color.getHSBColor(167,0,10));

                    ResultSet profileInformationRs = connection.getProfileInformation(itemEvent.getItem(), profileList.getSelectedItem());
                    try {
                        JTable profileInformationTable = new JTable(tableBuilder.buildTableModel(profileInformationRs)){
                            public boolean isCellEditable(int row,int column){
                                return false;
                            }
                        };

                        profileInformationTable.setRowHeight(tableHeight);

                        profileInformationPane = new JScrollPane(profileInformationTable);


                        profileList.addItemListener(profileListener);

                        profilesContainer.add(profileList);
                        profilesContainer.add(deleteProfileButton);
                        profilesContainer.add(profileInformationPane);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    profilesContainer.revalidate();
                    profilesContainer.repaint();
                }
            }
        };

        profileListener = new ItemListener() {
            public void itemStateChanged(ItemEvent itemEvent) {
                int state = itemEvent.getStateChange();
                if (state == 1) {
                    profilesContainer.remove(profileInformationPane);

                    ResultSet profileInformationRs = connection.getProfileInformation(accountList.getSelectedItem(), itemEvent.getItem());
                    try {
                        JTable profileInformationTable = new JTable(tableBuilder.buildTableModel(profileInformationRs)){
                            public boolean isCellEditable(int row,int column){
                                return false;
                            }
                        };

                        profileInformationTable.setRowHeight(tableHeight);

                        profileInformationPane = new JScrollPane(profileInformationTable);

                        profilesContainer.add(profileInformationPane);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    profilesContainer.revalidate();
                    profilesContainer.repaint();
                }
            }
        };

        accountList.addItemListener(accountListener);
        profileList.addItemListener(profileListener);

        profilesContainer.add(accountsString);
        profilesContainer.add(accountList);
        profilesContainer.add(profilesString);

        ResultSet profileInformationRs = connection.getProfileInformation(accountList.getSelectedItem(), profileList.getSelectedItem());
        try {
            JTable profileInformationTable = new JTable(tableBuilder.buildTableModel(profileInformationRs)){
                public boolean isCellEditable(int row,int column){
                    return false;
                }
            };

            profileInformationTable.setRowHeight(tableHeight);

            profileInformationPane = new JScrollPane(profileInformationTable);


            profilesContainer.add(profileList);
            profilesContainer.add(deleteProfileButton);
            profilesContainer.add(profileInformationPane);
        } catch (Exception e) {
            e.printStackTrace();
        }

        add("allProfiles", profilesContainer);
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
                        return false;
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
                    return false;
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
                        return false;
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
                        return false;
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
                    return false;
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
                    return false;
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
                                return false;
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


        pagesBox.setMaximumSize(new Dimension(8000,50));
        pagesBox.setBackground(Color.getHSBColor(167,0,10));
        createValues.setBackground(Color.WHITE);
        createValues.setLayout(new GridLayout(14,1));

        createAccountsContainer();

        ItemListener itemListener = new ItemListener() {
            public void itemStateChanged(ItemEvent itemEvent) {
                int state = itemEvent.getStateChange();
                if(state == 1) {
                    if(itemEvent.getItem() == "Users") {
                        createValues.removeAll();

                        createAccountsContainer();

                        updateCreateValuesContainer(createValuesContainer);
                    } else if (itemEvent.getItem() == "Profiles") {
                        createValues.removeAll();
                        JLabel UsersString = new JLabel("Fill the values below: ");

                        JComboBox accountList = idGrabber.getAccountId();
                        JLabel accountsString = new JLabel("Select a subscriber ID:");

                        JLabel profileNameString = new JLabel("Profile name:");
                        JTextArea profileName = new JTextArea(1,30);
                        JScrollPane profileNamePane = new JScrollPane(profileName);


                        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        Date date = new Date();

                        JLabel dateOfBirthString = new JLabel("Date of Birth:");
                        JLabel exampleText = new JLabel("For example: " + dateFormat.format(date));
                        JTextArea dateOfBirth = new JTextArea(1,30);
                        JScrollPane dateOfBirthPane = new JScrollPane(dateOfBirth);

                        accountList.setMaximumSize(new Dimension(8000,50));
                        accountList.setBackground(Color.getHSBColor(167,0,10));
                        exampleText.setFont(new Font("Serif", Font.PLAIN, smallFontSize));

                        JButton addProfile = new JButton("Add new profile");

                        addProfile.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                connection.createNewProfile(String.valueOf(accountList.getSelectedItem()), profileName.getText(), dateOfBirth.getText(), frame);
                                profileName.setText("");
                                dateOfBirth.setText("");
                            }
                        });

                        createValues.add(UsersString);

                        createValues.add(accountsString);
                        createValues.add(accountList);

                        createValues.add(profileNameString);
                        createValues.add(profileNamePane);

                        createValues.add(dateOfBirthString);
                        createValues.add(exampleText);
                        createValues.add(dateOfBirthPane);

                        createValues.add(addProfile);

                        updateCreateValuesContainer(createValuesContainer);

                    } else if (itemEvent.getItem() == "Watched") {
                        createValues.removeAll();
                        JLabel UsersString = new JLabel("Add Watched shows/movies");
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

    public void createAccountsContainer() {
        JLabel UsersString = new JLabel("Fill the values below: ");

        JLabel subscriberIdString = new JLabel("SubscriberId:");
        JTextArea subscriberId = new JTextArea(1,30);
        JScrollPane subscriberIdPane = new JScrollPane(subscriberId);

        JLabel nameString = new JLabel("Name:");
        JTextArea name = new JTextArea(1,30);
        JScrollPane namePane = new JScrollPane(name);

        JLabel streetString = new JLabel("Street:");
        JTextArea street = new JTextArea(1,30);
        JScrollPane streetPane = new JScrollPane(street);

        JLabel postalCodeString = new JLabel("Postalcode:");
        JTextArea postalCode = new JTextArea(1,30);
        JScrollPane postalCodePane = new JScrollPane(postalCode);

        JLabel houseNumberString = new JLabel("House Number:");
        JTextArea houseNumber = new JTextArea(1,30);
        JScrollPane houseNumberPane = new JScrollPane(houseNumber);

        JLabel cityString = new JLabel("City:");
        JTextArea city = new JTextArea(1,30);
        JScrollPane cityPane = new JScrollPane(city);

        JButton addAccount = new JButton("Add Account");

        createValues.add(UsersString);

        createValues.add(subscriberIdString);
        createValues.add(subscriberIdPane);

        createValues.add(nameString);
        createValues.add(namePane);

        createValues.add(streetString);
        createValues.add(streetPane);

        createValues.add(postalCodeString);
        createValues.add(postalCodePane);

        createValues.add(houseNumberString);
        createValues.add(houseNumberPane);

        createValues.add(cityString);
        createValues.add(cityPane);

        addAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connection.createNewAccount(subscriberId.getText(), name.getText(), street.getText(), postalCode.getText(), houseNumber.getText(), city.getText(), frame);
                subscriberId.setText("");
                name.setText("");
                street.setText("");
                postalCode.setText("");
                houseNumber.setText("");
                city.setText("");
            }
        });
        createValues.add(addAccount);
    }
}

