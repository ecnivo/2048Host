import java.awt.Point;

import javax.swing.JLabel;

import org.python.core.PyInteger;

@SuppressWarnings("serial")
public class NumberTile extends JLabel {

    TwentyFortyEight twfe;
    int value;
//    int row, col;
    Point location;

    public NumberTile(TwentyFortyEight twfe) {
	value = 0;
	this.twfe = twfe;
	init();
    }

    public NumberTile(TwentyFortyEight twfe, int value) {
	this.value = value;
	this.twfe = twfe;
	init();
    }

    private void init() {
	int tempRow = (int) (Math.random() * twfe.getGrid().length);
	int tempCol = (int) (Math.random() * twfe.getGrid().length);
	while (twfe.getGrid()[tempRow][tempCol] != null) {
	    tempRow = (int) (Math.random() * twfe.getGrid().length);
	    tempCol = (int) (Math.random() * twfe.getGrid().length);
	}
//	row = tempRow;
//	col = tempCol;
	twfe.getGrid()[tempRow][tempCol] = this;
    }

    public boolean doubleValue() {
	value *= 2;
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

    public void incrementUp(int amount) {
	location.y -= amount;
    }

    public void incrementDown(int amount) {
	location.y += amount;
    }

    public void incrementLeft(int amount) {
	location.x -= amount;
    }

    public void incrementRight(int amount) {
	location.x += amount;
    }

//    public void setNewPos(int row, int col) {
//	this.row = row;
//	this.col = col;
//    }
}
