import javax.swing.JLabel;

import org.python.core.PyInteger;

@SuppressWarnings("serial")
public class NumberTile extends JLabel {

    int amount;

    public NumberTile() {
	amount = 0;
    }

    public void doubleAmount() {
	amount *= 2;
    }

    public PyInteger getPyInt() {
	return new PyInteger(amount);
    }
}
