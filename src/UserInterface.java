import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class UserInterface implements Runnable {

    private JFrame frame;
    private ContainerManagement containerManagement = new ContainerManagement();

    @Override
    public void run() {
        frame = new JFrame("Netflix Statistix");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setPreferredSize(new Dimension((int) Math.round(screenSize.getWidth()) / 4, (int) Math.round(screenSize.getHeight()) / 3));

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        createComponents(frame.getContentPane());

        frame.getContentPane().setBackground(Color.WHITE);

        frame.pack();
        frame.setVisible(true);
    }

    private void createComponents(Container container) {
        containerManagement.accountsContainer();
        containerManagement.singleProfileAccounts();
        containerManagement.getAllSeriesContainer();
        containerManagement.getAllFilmsContainer();

        container.add(createLeftButtons(), BorderLayout.WEST);
        container.add(footer(), BorderLayout.SOUTH);
    }

    private JPanel createLeftButtons() {
        //Left buttons layout
        JPanel leftPanel = new JPanel(new GridLayout(3, 1));
        JPanel leftPanelInside1 = new JPanel(new GridLayout(3, 1));
        JPanel leftPanelInside2 = new JPanel(new GridLayout(3, 1));
        leftPanel.add(leftPanelInside1);
        leftPanel.add(leftPanelInside2);


        //Left buttons creation
        JButton accounts = new JButton("Accounts");
        JButton accountWithOneProfile = new JButton("Accounts with 1 profile");
        JButton series = new JButton("Users average view time per show");
        JButton films = new JButton("Movies");

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

        //Add left buttons to JPanel inside 1
        leftPanelInside1.add(accounts);
        leftPanelInside1.add(accountWithOneProfile);

        //Add left buttons to JPanel inside 2
        leftPanelInside1.add(series);
        leftPanelInside2.add(films);

        leftPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        leftPanel.setBackground(Color.WHITE);
        leftPanelInside2.setBackground(Color.WHITE);

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
        projectName.setFont(new Font("Serif", Font.BOLD, 30));
        creatorNames.setFont(new Font("Serif", Font.BOLD, 20));

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