import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.ItemSelectable;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;

public class ContainerManagement {

    private HashMap<String, Container> containers;
    private Container currentContainer;
    private SQLConnection connection = new SQLConnection();
    private JScrollPane tablePane1 = null;
    private JScrollPane tablePane2 = null;
    private JLabel watchedFilms = new JLabel("Watched Movies:");
    private Component emptySpace = null;

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
        emptySpace = Box.createRigidArea(new Dimension(0,accountContainer.getHeight() / 2 - 100));

        ArrayList<Integer> accountIds = connection.getAccountId();
        JComboBox accountList = new JComboBox();
        for (Integer accountID : accountIds) {
            accountList.addItem(accountID);
        }

        accountList.setFont(new Font("Serif", Font.PLAIN, 20));
        accountList.setMaximumSize(new Dimension(8000,50));
        accountList.setBackground(Color.getHSBColor(167,0,10));

        accountContainer.setLayout(new BoxLayout(accountContainer, BoxLayout.Y_AXIS));
        accountContainer.add(accountList);

        ResultSet rs = connection.getFirstAccountInformation();
        ResultSet accountWatchedFilmsRs = connection.getFilmsWatchedByAccount(connection.getFirstAccountId());
        try {
            if (!accountWatchedFilmsRs.isBeforeFirst() ) {
                System.out.println("No data");
            } else {
                System.out.println("found some data");
                JTable accountWatchedFilmsTable = new JTable(buildTableModel(accountWatchedFilmsRs));

                tablePane2 = new JScrollPane(accountWatchedFilmsTable);
            }

            JTable table = new JTable(buildTableModel(rs));
            tablePane1 = new JScrollPane(table);

            JLabel profileInformation = new JLabel("Profile information:");
            profileInformation.setFont(new Font("Serif", Font.BOLD, 30));
            tablePane1.setFont(new Font("Serif", Font.PLAIN, 30));
            tablePane1.setBackground(Color.WHITE);


            watchedFilms.setFont(new Font("Serif", Font.BOLD, 30));

            accountContainer.add(profileInformation);
            accountContainer.add(tablePane1);

            if(tablePane2 == null) {
                accountContainer.add(watchedFilms);
                accountContainer.add(emptySpace);
            }

            if(tablePane2 != null) {
                accountContainer.remove(emptySpace);
                accountContainer.add(tablePane2);
            }

            rs.close();
        } catch (Exception e) {
            System.out.println("not working");
            System.out.println(e);
        }

        ItemListener itemListener = new ItemListener() {
            public void itemStateChanged(ItemEvent itemEvent) {
                int state = itemEvent.getStateChange();
                System.out.println((state == ItemEvent.SELECTED) ? "Selected" : "Deselected");
                System.out.println("Item: " + itemEvent.getItem());
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
                            System.out.println("No data");
                        } else {
                            System.out.println("found data");
                            JTable accountWatchedFilmsTable = new JTable(buildTableModel(accountWatchedFilmsRs));

                            if(tablePane2 != null) {
                                accountContainer.remove(tablePane2);
                            }

                            tablePane2 = new JScrollPane(accountWatchedFilmsTable);
                        }
                        JTable accountInformationTable = new JTable(buildTableModel(accountInformationRs));

                        accountContainer.remove(tablePane1);
                        tablePane1 = new JScrollPane(accountInformationTable);
                        accountContainer.add(tablePane1);
                        accountContainer.add(watchedFilms);

                        if(tablePane2 == null) {
                            accountContainer.add(watchedFilms);
                            accountContainer.add(emptySpace);
                        }

                        if(tablePane2 != null) {
                            accountContainer.remove(emptySpace);
                            accountContainer.add(tablePane2);
                        }


                        accountContainer.revalidate();
                        accountContainer.repaint();

                        accountInformationRs.close();
                    } catch (Exception e) {
                        System.out.println("not working");
                        System.out.println(e);
                    }
                }
            }
        };

        accountList.addItemListener(itemListener);
        add("allAccounts", accountContainer);
    }

    public static DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();

        // names of columns
        Vector<String> columnNames = new Vector<String>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }

        // data of the table
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }

        return new DefaultTableModel(data, columnNames);

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
