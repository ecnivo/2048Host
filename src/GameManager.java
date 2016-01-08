import java.awt.Graphics;

import javax.swing.JPanel;

import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

@SuppressWarnings("serial")
public class GameManager extends JPanel {

    PythonInterpreter interpreter;
    TwentyFortyEight twfe;

    public GameManager(TwentyFortyEight twfe) {
	this.twfe = twfe;
	interpreter = new PythonInterpreter();

	setLayout(null);

	boolean running = true;
	while (running) {
	    interpreter.exec("from MoveDecider import decideMove");
	    PyList pyGrid = getGrid();
	    interpreter.set("inputArray", pyGrid);
	    PyObject output = interpreter.eval("decideMove(inputArray)");
	    int direction = (int) output.__tojava__(int.class);
	    System.out.println(direction + " was chosen");

	    NumberTile newTile = new NumberTile(twfe, (int) (Math.random() * twfe.getGrid().length), (int) (Math.random() * twfe.getGrid().length));
	    // TODO add new tile

	    // TODO calculate future position, and prep to combine

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
		column.__add__(twfe.getGrid()[row][col].getPyInt());
	    }
	    out.__add__(column);
	}
	return out;
    }
}
