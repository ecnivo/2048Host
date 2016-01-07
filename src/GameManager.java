import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

public class GameManager {

    NumberTile[][] grid;
    PythonInterpreter interpreter;

    public GameManager(int size) {
	interpreter = new PythonInterpreter();
	grid = new NumberTile[size][size];

	boolean running = true;
	while (running) {
	    // TODO ask for move
	    PyObject ask = interpreter.get("funcName");
	    PyList pyGrid = getGrid();
	    PyObject reply = ask.__call__(pyGrid);
	    byte direction = (byte) reply.__tojava__(byte.class);

	    NumberTile newTile = new NumberTile();
	    // TODO add new tile

	    // TODO calculate future position

	    // TODO animate

	    // TODO check if 2048, and check if any moves possible
	}
    }

    private PyList getGrid() {
	PyList out = new PyList();
	for (int row = 0; row < grid.length; row++) {
	    PyList column = new PyList();
	    for (int col = 0; col < grid.length; col++) {
		column.__add__(grid[row][col].getPyInt());
	    }
	    out.__add__(column);
	}
	return out;
    }
}
