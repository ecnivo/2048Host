import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

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
    GameManager manager;

    public TwentyFortyEight() {
	super("2048");
	JSpinner rowSelector = new JSpinner(new SpinnerNumberModel(4, 3, 16, 1));
	JPanel joptionPane = new JPanel(new GridLayout(2, 1, 1, 2));
	joptionPane.add(new JLabel("Select the number of rows/columns"));
	joptionPane.add(rowSelector);
	JOptionPane.showMessageDialog(null, joptionPane);
	int size = (int) rowSelector.getValue();
	if (size < 3) {
	    size = 3;
	}
	grid = new NumberTile[size][size];
	target = (int) Math.pow(2, 7 + size);
	manager = new GameManager(this);
	manager.addStartingTiles();
	manager.repaint();
	add(manager);
	setVisible(true);
	setResizable(false);
	setLocation(200, 200);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setResizable(true);
	addComponentListener(new ComponentListener() {

	    @Override
	    public void componentShown(ComponentEvent e) {
		// nothing
	    }

	    @Override
	    public void componentResized(ComponentEvent e) {
		if (TwentyFortyEight.this.getWidth() == TwentyFortyEight.this.getHeight())
		    return;
		TwentyFortyEight.this.setSize(new Dimension(TwentyFortyEight.this.getWidth(), TwentyFortyEight.this.getWidth() + 23));
		manager.updateTileSize();
		manager.repaint();
	    }

	    @Override
	    public void componentMoved(ComponentEvent e) {
		// nothing
	    }

	    @Override
	    public void componentHidden(ComponentEvent e) {
		// nothing
	    }
	});
	setSize(320, 343);
	// pack();
    }

    public GameManager getManager() {
	return manager;
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