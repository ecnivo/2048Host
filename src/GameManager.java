import java.awt.Color;
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

	new NumberTile(twfe, 2);
	new NumberTile(twfe, ((int) (Math.random() * 2)) * 2);

	boolean running = true;
	Thread loop = new Thread(new Runnable() {
	    @Override
	    public void run() {
		while (running) {
		    interpreter.exec("from MoveDecider import decideMove");
		    PyList pyGrid = getGrid();
		    interpreter.set("inputArray", pyGrid);
		    PyObject output = interpreter.eval("decideMove(inputArray)");
		    int direction = (int) output.__tojava__(int.class);
		    System.out.println("[INFO] " + direction + " was chosen");

		    switch (direction) {
		    case UP:
			for (int col = 0; col < twfe.getGrid().length; col++) {
			    for (int row = 0; row < twfe.getGrid()[0].length - 1; row++) {
				NumberTile current = twfe.getGrid()[row][col];
				if (current != null) {
				    int nextRow = row + 1;
				    while (nextRow < twfe.getGrid()[0].length - 1 && twfe.getGrid()[nextRow][col] == null) {
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
			for (int col = 0; col < twfe.getGrid().length; col++) {
			    for (int row = 0; row < twfe.getGrid()[0].length - 1; row++) {
				if (twfe.getGrid()[row][col] == null) {
				    int nextRow = row + 1;
				    while (nextRow < twfe.getGrid().length - 1 && twfe.getGrid()[nextRow][col] == null) {
					nextRow++;
				    }
				    NumberTile nextItem = twfe.getGrid()[nextRow][col];
				    if (nextItem != null) {
					twfe.getGrid()[row][col] = nextItem;
					twfe.getGrid()[nextRow][col] = null;
				    } else {
					break;
				    }
				}
			    }
			}
			break;

		    case RIGHT:
			for (NumberTile[] row : twfe.getGrid()) {
			    for (int col = row.length - 1; col > 0; col--) {
				NumberTile current = row[col];
				if (current != null) {
				    int nextCol = col - 1;
				    while (nextCol > 0 && row[nextCol] == null) {
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
			for (NumberTile[] row : twfe.getGrid()) {
			    for (int col = row.length - 1; col > 0; col--) {
				if (row[col] == null) {
				    int nextCol = col - 1;
				    while (nextCol > 0 && row[nextCol] == null) {
					nextCol--;
				    }
				    NumberTile nextItem = row[nextCol];
				    if (nextItem != null) {
					row[col] = nextItem;
					row[nextCol] = null;
				    } else {
					break;
				    }
				}
			    }
			}
			break;

		    case DOWN:
			for (int col = 0; col < twfe.getGrid().length; col++) {
			    for (int row = twfe.getGrid()[0].length - 1; row > 0; row--) {
				NumberTile current = twfe.getGrid()[row][col];
				if (current != null) {
				    int nextRow = row - 1;
				    while (nextRow > 0 && twfe.getGrid()[nextRow][col] == null) {
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
			for (int col = 0; col < twfe.getGrid().length; col++) {
			    for (int row = twfe.getGrid().length; row > 0; row--) {
				if (twfe.getGrid()[row][col] == null) {
				    int nextRow = row - 1;
				    while (nextRow > 0 && twfe.getGrid()[nextRow][col] == null) {
					nextRow--;
				    }
				    NumberTile nextItem = twfe.getGrid()[nextRow][col];
				    if (nextItem != null) {
					twfe.getGrid()[row][col] = nextItem;
					twfe.getGrid()[nextRow][col] = null;
				    } else {
					break;
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
			for (NumberTile[] row : twfe.getGrid()) {
			    for (int col = 0; col < row.length - 1; col++) {
				if (row[col] == null) {
				    int nextCol = col + 1;
				    while (nextCol < row.length - 1 && row[nextCol] == null) {
					nextCol++;
				    }
				    NumberTile nextItem = row[nextCol];
				    if (nextItem != null) {
					row[col] = nextItem;
					row[nextCol] = null;
				    } else {
					break;
				    }
				}
			    }
			}
			break;
		    }

		    new NumberTile(twfe);

		    revalidate();
		    repaint();

		    // TODO check if 2048, and check if any moves possible
		    try {
			Thread.sleep(500);
		    } catch (InterruptedException e) {
			e.printStackTrace();
		    }
		}
	    }
	});
	loop.start();
    }

    public void paintComponent(Graphics g) {
	super.paintComponent(g);

	int width = (int) twfe.getWidth() - 16;
	int tileSize = (int) Math.round(width * 1.0 / twfe.getGrid().length);

	Color background = new Color(0x90C3D4);
	Color lines = new Color(0x9CB5B5);
	g.setColor(background);
	g.fillRect(0, 0, width, width);

	for (int row = 0; row < twfe.getGrid().length; row++) {
	    for (int col = 0; col < twfe.getGrid()[row].length; col++) {
		g.setColor(lines);
		g.fillRect(col * tileSize, row * tileSize, 2, tileSize);
		g.fillRect(col * tileSize, row * tileSize, tileSize, 2);

		if (twfe.getGrid()[row][col] != null) {
		    NumberTile tile = twfe.getGrid()[row][col];
		    g.setColor(new Color(128 + tile.getValue(), 64, 64));
		    int x = (int) ((col + 0.05) * tileSize);
		    int y = (int) ((row + 0.05) * tileSize);
		    g.fillRect(x, y, (int) (tileSize * 0.9), (int) (tileSize * 0.9));
		    g.drawChars(String.valueOf(tile.getValue()).toCharArray(), 0, 1, x, y);
		}
	    }
	}
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

    private class MoveInfo {

	private int start, end;
	private NumberTile tile;
	private static final int VERTICAL = 0, HORIZONTAL = 1;

	private MoveInfo(NumberTile tile, int axis, int start, int end) {

	}
    }
}
