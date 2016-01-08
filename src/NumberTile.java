import javax.swing.JLabel;

import org.python.core.PyInteger;

@SuppressWarnings("serial")
public class NumberTile extends JLabel {

    TwentyFortyEight twfe;
    int amount;
    int row, col;
    int x, y;

    private static final double INCREMENT_AMOUNT = 2;

    public NumberTile(TwentyFortyEight twfe, int row, int column) {
	amount = 0;
	this.twfe = twfe;
	this.row = row;
	this.col = column;

    }

    public void doubleAmount() {
	amount *= 2;
    }

    public int getAmount() {
	return amount;
    }

    public PyInteger getPyInt() {
	return new PyInteger(amount);
    }

    public void incrementUp() {
	y -= INCREMENT_AMOUNT;
    }

    public void incrementDown() {
	y += INCREMENT_AMOUNT;
    }

    public void incrementLeft() {
	x -= INCREMENT_AMOUNT;
    }

    public void incrementRight() {
	x += INCREMENT_AMOUNT;
    }

    public void setNewPos(int row, int col) {
	this.row = row;
	this.col = col;
    }
}
