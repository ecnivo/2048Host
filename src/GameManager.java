import java.awt.Graphics;

import javax.swing.JPanel;

import org.python.core.PyInteger;
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

@SuppressWarnings("serial")
public class GameManager extends JPanel {

    PythonInterpreter interpreter;
    TwentyFortyEight twfe;

    private final static int UP = 0, RIGHT = 1, DOWN = 2, LEFT = 3;

    public GameManager(TwentyFortyEight twfe) {
	this.twfe = twfe;
	interpreter = new PythonInterpreter();
	setLayout(null);

	// TODO add two 2's or 4's anywhere

	boolean running = true;
	while (running) {
	    interpreter.exec("from MoveDecider import decideMove");
	    PyList pyGrid = getGrid();
	    interpreter.set("inputArray", pyGrid);
	    PyObject output = interpreter.eval("decideMove(inputArray)");
	    int direction = (int) output.__tojava__(int.class);
	    System.out.println("[INFO] " + direction + " was chosen");

	    // TODO collapse/combine tiles
	    switch (direction) {
	    case UP:
		for (int col = 0; col < twfe.getGrid().length; col++) {
		    for (int row = 0; row < twfe.getGrid()[0].length; row++) {
			NumberTile current = twfe.getGrid()[row][col];
			if (current != null) {
			    int nextRow = row + 1;
			    while (row < twfe.getGrid()[0].length && twfe.getGrid()[nextRow][col] == null) {
				nextRow++;
			    }
			    NumberTile next = twfe.getGrid()[nextRow][col];
			    if (next == null) {
				break;
			    } else if (current.getValue() == next.getValue()) {
				current.doubleValue();
				twfe.getGrid()[nextRow][col] = null;
				row = nextRow;
			    }
			}
		    }
		}
		break;

	    case RIGHT:
		for (NumberTile[] row : twfe.getGrid()) {
		    for (int col = row.length - 1; col >= 0; col--) {
			NumberTile current = row[col];
			if (current != null) {
			    int nextCol = col - 1;
			    while (nextCol >= 0 && row[nextCol] == null) {
				nextCol--;
			    }
			    NumberTile next = row[nextCol];
			    if (next == null) {
				break;
			    } else if (current.getValue() == next.getValue()) {
				current.doubleValue();
				row[nextCol] = null;
				col = nextCol;
			    }
			}
		    }
		}
		break;

	    case DOWN:
		for (int col = 0; col < twfe.getGrid().length; col++) {
		    for (int row = twfe.getGrid()[0].length - 1; row >= 0; row--) {
			NumberTile current = twfe.getGrid()[row][col];
			if (current != null) {
			    int nextRow = row - 1;
			    while (row >= 0 && twfe.getGrid()[nextRow][col] == null) {
				nextRow--;
			    }
			    NumberTile next = twfe.getGrid()[nextRow][col];
			    if (next == null) {
				break;
			    } else if (current.getValue() == next.getValue()) {
				current.doubleValue();
				twfe.getGrid()[nextRow][col] = null;
				row = nextRow;
			    }
			}
		    }
		}
		break;

	    case LEFT:
		for (NumberTile[] row : twfe.getGrid()) {
		    for (int col = 0; col < row.length - 1; col++) {
			NumberTile current = row[col];
			if (current != null) {
			    int nextCol = col + 1;
			    while (nextCol < row.length - 1 && row[nextCol] == null) {
				nextCol++;
			    }
			    NumberTile next = row[nextCol];
			    if (next == null) {
				break;
			    } else if (current.getValue() == next.getValue()) {
				current.doubleValue();
				row[nextCol] = null;
				col = nextCol;
			    }
			}
		    }
		}
		break;
	    }

	    NumberTile newTile = new NumberTile(twfe);

	    // TODO animate

	    // TODO check if 2048, and check if any moves possible
	}
    }

    public void paintComponent(Graphics g) {
	super.paintComponent(g);

    }

    private PyList getGrid() {
	PyList out = new PyList();
	for (int row = 0; row < twfe.getGrid().length; row++) {
	    PyList column = new PyList();
	    for (int col = 0; col < twfe.getGrid().length; col++) {
		if (twfe.getGrid()[row][col] == null) {
		    column.__add__(new PyInteger(0));
		} else
		    column.__add__(twfe.getGrid()[row][col].getPyInt());
	    }
	    out.__add__(column);
	}
	return out;
    }
}
