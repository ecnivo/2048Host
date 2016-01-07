import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

class Background extends JPanel {

    Image background;
    int sides;

    public Background(int sides) {
	this.sides = sides;
	try {
	    background = ImageIO.read(new File("background.png")).getScaledInstance((int) getSize().getWidth(), (int) getSize().getHeight(), Image.SCALE_SMOOTH);
	} catch (IOException e) {
	    JOptionPane.showConfirmDialog(null, "Background not found", "Image missing", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
	    e.printStackTrace();
	    return;
	}
    }

    @Override
    public void paintComponent(Graphics g) {
	super.paintComponent(g);
	g.drawImage(background, 0, 0, null);
    }
}