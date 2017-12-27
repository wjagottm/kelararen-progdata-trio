import java.awt.*;
import javax.swing.*;

public class UserInterface implements Runnable {

    private JFrame frame;

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
        container.add(createLeftButtons(), BorderLayout.WEST);
        container.add(footer(), BorderLayout.SOUTH);
        container.add(new JTextArea());
    }

    private JPanel createLeftButtons() {
        JPanel leftPanel = new JPanel(new GridLayout(3, 1));
        JPanel leftPanelInside1 = new JPanel(new GridLayout(3, 1));
        JPanel leftPanelInside2 = new JPanel(new GridLayout(3, 1));
        leftPanel.add(leftPanelInside1);
        leftPanel.add(leftPanelInside2);
        leftPanelInside1.add(new JButton("Accounts"));
        leftPanelInside1.add(new JButton("Films onder de 16 jaar"));
        leftPanelInside1.add(new JButton("Accounts met 1 profiel"));
        leftPanelInside2.add(new JButton("Series"));
        leftPanelInside2.add(new JButton("Films"));
        return leftPanel;
    }

    private JPanel footer() {
        JPanel footerContent = new JPanel(new GridLayout(1,2));
        JLabel projectName = new JLabel("Netflix Statistix");
        JLabel creatorNames = new JLabel("Informatica 2017, 23IVT1C1, Kelly, Rene, Arantxio");
        projectName.setFont(new Font("Serif", Font.BOLD, 30));
        creatorNames.setFont(new Font("Serif", Font.BOLD, 20));
        footerContent.add(projectName);
        footerContent.add(creatorNames);
        return footerContent;
    }
}