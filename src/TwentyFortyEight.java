import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

@SuppressWarnings("serial")
class TwentyFortyEight extends JFrame {

    private NumberTile[][] grid;
    private int target;

    public TwentyFortyEight() {
	super("2048");
	JSpinner rowSelector = new JSpinner(new SpinnerNumberModel(4, 0, 16, 1));
	JPanel joptionPane = new JPanel(new GridLayout(2, 1, 1, 2));
	joptionPane.add(new JLabel("Select the number of rows/columns"));
	joptionPane.add(rowSelector);
	JOptionPane.showMessageDialog(null, joptionPane);
	int size = (int) rowSelector.getValue();
	grid = new NumberTile[size][size];
	target = (int) Math.pow(2, 7 + size);
	add(new GameManager(this));
	setVisible(true);
	setResizable(false);
	setLocation(200, 200);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setResizable(true);
	pack();
    }

    public static void main(String[] args) {
	new TwentyFortyEight();
    }

    public NumberTile[][] getGrid() {
	return grid;
    }

    public int getTarget() {
	return target;
    }
}