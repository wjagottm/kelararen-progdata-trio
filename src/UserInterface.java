import java.awt.*;
import javax.swing.*;

public class UserInterface implements Runnable {

    private JFrame frame;

    @Override
    public void run() {
        frame = new JFrame("Netflix Statistix");
        frame.setPreferredSize(new Dimension(800, 600));

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        createComponents(frame.getContentPane());

        frame.pack();
        frame.setVisible(true);
    }

    private void createComponents(Container container) {
        container.add(createLeftButtons(), BorderLayout.WEST);
        container.add(new JTextArea());
    }

    private JPanel createLeftButtons() {
        JPanel panel = new JPanel(new GridLayout(3, 1));
        panel.add(new JButton("Execute"));
        panel.add(new JButton("Test"));
        panel.add(new JButton("Send"));
        return panel;
    }
}