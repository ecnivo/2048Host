import java.awt.Point;

import javax.swing.JLabel;

import org.python.core.PyInteger;

@SuppressWarnings("serial")
public class NumberTile extends JLabel {

	private TwentyFortyEight	twfe;
	private int					value;
	private Point				position;

	public NumberTile(TwentyFortyEight twfe) {
		value = 2;
		this.twfe = twfe;
		init();
	}

	public NumberTile(TwentyFortyEight twfe, int value) {
		this.value = value;
		this.twfe = twfe;
		init();
	}

	private void init() {
		int row = (int) (Math.random() * twfe.getGrid().length);
		int col = (int) (Math.random() * twfe.getGrid().length);
		while (twfe.getGrid()[row][col] != null) {
			row = (int) (Math.random() * twfe.getGrid().length);
			col = (int) (Math.random() * twfe.getGrid().length);
		}
		twfe.getGrid()[row][col] = this;

		position = new Point();
		setPosition(new Point((int) ((col + 0.05) * twfe.getManager().tileSize), (int) ((row + 0.05) * twfe.getManager().tileSize)));
	}

	public boolean doubleValue() {
		value *= 2;
		if (value == 0) {
			System.err.println("zero!");
		}
		if (value >= twfe.getTarget()) {
			return true;
		}
		return false;
	}

	public int getValue() {
		return value;
	}

	public PyInteger getPyInt() {
		return new PyInteger(value);
	}

	public void incrementUp(double amount) {
		position.y -= amount;
	}

	public void incrementLeft(double amount) {
		position.x -= amount;
	}

	public void setPosition(Point newLocation) {
		position = newLocation;
	}

	public Point getPosition() {
		return position;
	}
}
