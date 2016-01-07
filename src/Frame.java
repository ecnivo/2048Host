import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

class Frame extends JFrame {

    private GameManager man;

    public Frame() {
	super("2048");
	JSpinner rowSelector = new JSpinner(new SpinnerNumberModel(4, 0, 16, 1));
	JPanel joptionPane = new JPanel(new GridLayout(2, 1, 1, 2));
	joptionPane.add(new JLabel("Select the number of rows/columns"));
	joptionPane.add(rowSelector);
	JOptionPane.showMessageDialog(null, joptionPane);
	int size = (int) rowSelector.getValue();
	add(new Background(size));
	man = new GameManager(size);
	setVisible(true);
	setLocation(200, 200);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	pack();
    }

    public static void main(String[] args) {
	new Frame();
    }
}