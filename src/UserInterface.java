import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class UserInterface implements Runnable {

    private JFrame frame;
    private ContainerManagement containerManager = new ContainerManagement();

    @Override
    public void run() {
        frame = new JFrame("Netflix Statistix");
        frame.setPreferredSize(new Dimension(1000, 800));

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        createComponents(frame.getContentPane());

        frame.pack();
        frame.setVisible(true);
    }

    private void createComponents(Container container) {
        Container account = new Container();
        account.setLayout(new BoxLayout(account, BoxLayout.Y_AXIS));
        account.add(new JLabel("Test"));
        containerManager.add("accounts", account);

        Container newtest = new Container();
        newtest.setLayout(new BoxLayout(newtest, BoxLayout.Y_AXIS));
        newtest.add(new JLabel("newTest"));
        containerManager.add("test", newtest);

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
        JButton filmUnderSixteen = new JButton("Films onder de 16 jaar");
        JButton accountWithOneProfile = new JButton("Accounts met 1 profiel");
        JButton series = new JButton("Series");
        JButton films = new JButton("Films");

        //Add Action event listeners
        accounts.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                Container test = containerManager.grabCurrentContainer();
                if (test != null) {
                    frame.getContentPane().remove(test);
                }
                frame.getContentPane().add(containerManager.get("accounts"), BorderLayout.CENTER);

                containerManager.placeCurrentContainer(containerManager.get("accounts"));

                frame.invalidate();
                frame.validate();
                frame.repaint();
            }
        });

        filmUnderSixteen.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                Container test = containerManager.grabCurrentContainer();
                if (test != null) {
                    frame.getContentPane().remove(test);
                }
                frame.getContentPane().add(containerManager.get("test"), BorderLayout.CENTER);

                containerManager.placeCurrentContainer(containerManager.get("test"));

                frame.invalidate();
                frame.validate();
                frame.repaint();
            }
        });

        accountWithOneProfile.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                // display/center the jdialog when the button is pressed
                JDialog d = new JDialog(frame, "Hello", true);
                d.setLocationRelativeTo(frame);
                d.setVisible(true);
            }
        });

        series.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                // display/center the jdialog when the button is pressed
                JDialog d = new JDialog(frame, "Hello", true);
                d.setLocationRelativeTo(frame);
                d.setVisible(true);
            }
        });

        films.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                // display/center the jdialog when the button is pressed
                JDialog d = new JDialog(frame, "Hello", true);
                d.setLocationRelativeTo(frame);
                d.setVisible(true);
            }
        });

        //Add left buttons to JPanel inside 1
        leftPanelInside1.add(accounts);
        leftPanelInside1.add(filmUnderSixteen);
        leftPanelInside1.add(accountWithOneProfile);

        //Add left buttons to JPanel inside 2
        leftPanelInside2.add(series);
        leftPanelInside2.add(films);

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

        //Return JPanel to caller
        return footerContent;
    }
}