import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.plaf.FontUIResource;

public class UserInterface implements Runnable {

    private JFrame frame;
    private ContainerManagement containerManagement = new ContainerManagement();
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private int bigFontSize = (int) Math.round(screenSize.getHeight()) / 64;
    private int smallFontSize = (int) Math.round(screenSize.getHeight()) / 96;

    @Override
    public void run() {
        frame = new JFrame("Netflix Statistix");

        //Design settings
        frame.setPreferredSize(new Dimension((int) Math.round(screenSize.getWidth()) / 2, (int) Math.round(screenSize.getHeight()) / 2));
        setUIFont (new javax.swing.plaf.FontUIResource("Serif",Font.BOLD,bigFontSize));
        UIManager.put("Button.font", new FontUIResource(new Font("Sans-serif", Font.PLAIN, smallFontSize)));
        UIManager.put("Table.font", new FontUIResource(new Font("Sans-serif", Font.PLAIN, smallFontSize)));

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        createComponents(frame.getContentPane());

        frame.getContentPane().setBackground(Color.WHITE);

        frame.pack();
        frame.setVisible(true);
    }

    private static void setUIFont (javax.swing.plaf.FontUIResource f){
        java.util.Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get (key);
            if (value instanceof javax.swing.plaf.FontUIResource)
                UIManager.put (key, f);
        }
    }

    private void createComponents(Container container) {
        containerManagement.accountsContainer();
        containerManagement.singleProfileAccounts();
        containerManagement.getAllSeriesContainer();
        containerManagement.getAllFilmsContainer();
        containerManagement.createValuesContainer();

        container.add(createLeftButtons(), BorderLayout.WEST);
        container.add(footer(), BorderLayout.SOUTH);
    }

    private JPanel createLeftButtons() {
        //Left buttons layout
        JPanel leftPanel = new JPanel(new GridLayout(3, 1));
        JPanel leftPanelInside1 = new JPanel(new GridLayout(3, 1));
        JPanel leftPanelInside2 = new JPanel(new GridLayout(3, 1));
        JPanel leftPanelInside3 = new JPanel(new GridLayout(3, 1));
        leftPanel.add(leftPanelInside1);
        leftPanel.add(leftPanelInside2);
        leftPanel.add(leftPanelInside3);


        //Left buttons creation
        JButton accounts = new JButton("Accounts");
        JButton accountWithOneProfile = new JButton("Accounts with 1 profile");
        JButton series = new JButton("Users average view time per show");
        JButton films = new JButton("Movies");
        JButton create = new JButton("Add database values");

        //Add Action event listeners
        accounts.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                Container testCurrentContainer = containerManagement.grabCurrentContainer();
                Container newContainer = containerManagement.get("allAccounts");
                if (testCurrentContainer != null) {
                    frame.getContentPane().remove(testCurrentContainer);
                }
                frame.getContentPane().add(newContainer, BorderLayout.CENTER);

                addNewContainer(newContainer);
            }
        });

        //accountWithOneProfileButton Actionlistener
        accountWithOneProfile.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                Container testCurrentContainer = containerManagement.grabCurrentContainer();
                containerManagement.singleProfileAccounts();
                Container newContainer = containerManagement.get("singleProfileAccounts");
                if (testCurrentContainer != null) {
                    frame.getContentPane().remove(testCurrentContainer);
                }
                frame.getContentPane().add(newContainer, BorderLayout.CENTER);

                addNewContainer(newContainer);
            }
        });

        //seriesButton Actionlistener
        series.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                Container testCurrentContainer = containerManagement.grabCurrentContainer();
                Container newContainer = containerManagement.get("allSeries");
                if (testCurrentContainer != null) {
                    frame.getContentPane().remove(testCurrentContainer);
                }
                frame.getContentPane().add(newContainer, BorderLayout.CENTER);

                addNewContainer(newContainer);
            }
        });

        //filmsButton Actionlistener
        films.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                Container testCurrentContainer = containerManagement.grabCurrentContainer();
                Container newContainer = containerManagement.get("allFilms");
                if (testCurrentContainer != null) {
                    frame.getContentPane().remove(testCurrentContainer);
                }
                frame.getContentPane().add(newContainer, BorderLayout.CENTER);

                addNewContainer(newContainer);
            }
        });

        //filmsButton Actionlistener
        create.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                Container testCurrentContainer = containerManagement.grabCurrentContainer();
                Container newContainer = containerManagement.get("creator");
                if (testCurrentContainer != null) {
                    frame.getContentPane().remove(testCurrentContainer);
                }
                frame.getContentPane().add(newContainer, BorderLayout.CENTER);

                addNewContainer(newContainer);
            }
        });

        //Add left buttons to JPanel inside 1
        leftPanelInside1.add(accounts);
        leftPanelInside1.add(accountWithOneProfile);

        //Add left buttons to JPanel inside 2
        leftPanelInside1.add(series);
        leftPanelInside2.add(films);

        //Add left buttons to JPanel inside 3
        leftPanelInside3.add(new JLabel(""));
        leftPanelInside3.add(new JLabel(""));
        leftPanelInside3.add(create);

        leftPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        leftPanel.setBackground(Color.WHITE);
        leftPanelInside2.setBackground(Color.WHITE);
        leftPanelInside3.setBackground(Color.WHITE);

        //Return JPanels to caller
        return leftPanel;
    }

    private JPanel footer() {
        //Footer layout
        JPanel footerContent = new JPanel(new GridLayout(1,2));

        //JLabel creation
        JLabel projectName = new JLabel("Netflix Statistix");
        JLabel creatorNames = new JLabel("Informatica 2017, 23IVT1C1, Kelly, Rene, Arantxio");

        //Set JLabel text size
        projectName.setFont(new Font("Serif", Font.BOLD, bigFontSize));
        creatorNames.setFont(new Font("Serif", Font.BOLD, smallFontSize));

        //Add JLabel to JPanel
        footerContent.add(projectName);
        footerContent.add(creatorNames);

        footerContent.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        footerContent.setBackground(Color.WHITE);

        //Return JPanel to caller
        return footerContent;
    }

    //Container repainter
    public void addNewContainer(Container container) {
        containerManagement.placeCurrentContainer(container);

        frame.invalidate();
        frame.validate();
        frame.repaint();
    }
}