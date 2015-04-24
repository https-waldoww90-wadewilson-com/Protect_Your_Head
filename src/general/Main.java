package general;

import java.applet.Applet;
import java.awt.Graphics;

public class Main extends Game_loop {
	public void init() {
		setSize(600,420);
		Thread th = new Thread(this);
		th.start();
		offscreen = createImage(600,420);
		d = offscreen.getGraphics();
		addKeyListener(this);
	}
	public void paint(Graphics g) {
		d.clearRect(0, 0, 600,420);
		d.drawImage(background, 0, 0, this);
		d.drawImage(chrctr, X, Y, this);
		d.drawImage(foreground, 0, 0, this);
		g.drawImage(offscreen, 0, 0, this);
	}
	public void update(Graphics g) {
		paint(g);
	}
}
