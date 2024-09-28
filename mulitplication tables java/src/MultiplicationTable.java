import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

public class MultiplicationTable extends JFrame {

    private final JPanel gridPanel;
    private final JLabel[] labels = new JLabel[100];
    private final JLabel centerLabel;
    private final Color black = Color.BLACK; // Set black as the initial background color

    public MultiplicationTable() {

        setTitle("Multiplication Table");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // Dispose the frame on close
        setSize(800, 600);
        setLayout(new BorderLayout());

        // Create a panel for the grid of labels
        gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(10, 10));

        // Initialize the labels for the grid
        for (int i = 0; i < 100; i++) {
            int labelValue = i + 1;
            JLabel label = new JLabel(String.valueOf(labelValue));
            label.setOpaque(true);
            label.setHorizontalAlignment(SwingConstants.CENTER);

            // Set all labels to black initially
            label.setBackground(black);
            label.setForeground(Color.WHITE); // Set text color to white

            labels[i] = label;
            gridPanel.add(label);

            // Add a mouse listener to handle clicks
            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    highlightMultiples(labelValue);
                }
            });
        }

        // Add the grid panel to the top of the window
        add(gridPanel, BorderLayout.NORTH);

        // Add a center label for displaying the clicked number
        centerLabel = new JLabel("", SwingConstants.CENTER);
        centerLabel.setFont(new Font("Arial", Font.BOLD, 48));
        centerLabel.setForeground(Color.WHITE); // Set text color to white for contrast
        add(centerLabel, BorderLayout.CENTER);
    }

    private void highlightMultiples(int number) {
        // Generate a random color that is not black
        Color randomColor = getRandomNonBlackColor();

        // Highlight multiples of the clicked number
        for (int i = 0; i < labels.length; i++) {
            int labelValue = i + 1;
            if (labelValue % number == 0) {
                labels[i].setBackground(randomColor); // Highlight multiples with a random color
            } else {
                // Reset to black for non-multiples
                labels[i].setBackground(black);
            }
        }

        // Update the center label to show the selected number
        centerLabel.setText(String.valueOf(number));
    }

    private Color getRandomNonBlackColor() {
        Random rand = new Random();
        Color randomColor;

        do {
            // Generate a random RGB color
            randomColor = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
        } while (isBlack(randomColor)); // Ensure the color is not black

        return randomColor;
    }

    private boolean isBlack(Color color) {
        // Black is (0, 0, 0) in RGB.
        // This method checks if the color is close to black.
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();

        // Check if the color is near black
        return red < 50 && green < 50 && blue < 50;
    }

    @Override
    public void dispose() {
        // Clean up event listeners and resources
        for (JLabel label : labels) {
            for (MouseListener listener : label.getMouseListeners()) { // Use MouseListener
                label.removeMouseListener(listener); // Remove all attached mouse listeners
            }
        }

        // Call the superclass dispose method to release the frame resources
        super.dispose();
    }

    public static void main(String[] args) {
        // Launch the application
        SwingUtilities.invokeLater(() -> {
            MultiplicationTable table = new MultiplicationTable();
            table.getContentPane().setBackground(Color.BLACK); // Set the background to black
            table.setVisible(true);

        });
    }
}
