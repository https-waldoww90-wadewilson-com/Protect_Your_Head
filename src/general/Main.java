package general;

import java.awt.Graphics;
import java.awt.Image;

public class Main extends Game_loop {
	// public
	public static int FRAME_WIDTH = 600;
    public static int FRAME_HEIGHT = 420;
    public int NUM_OBSTACLES=20;
    public Obstacles[] squareArray = new Obstacles[NUM_OBSTACLES];
    // private
    private Image offscreen; // offscreen img
	private Graphics dbG; // double buffer graphic
    
	@Override
	public void init() {
		setSize(FRAME_WIDTH,FRAME_HEIGHT);
		Thread th = new Thread(this);
		th.start();
		addKeyListener(this);
		offscreen = createImage(FRAME_WIDTH,FRAME_HEIGHT); //this.getSize().width, this.getSisze().height
		dbG = offscreen.getGraphics();
		//initializes square objects
        for (int i = 0; i < squareArray.length; i++) {
        	squareArray[i] = new Obstacles();
        }
	}
	
	@Override
	public void paint(Graphics g) {	
		for (Obstacles square : squareArray) {
			square.paint(g);
	    }
	}
	
	@Override
	public void update(Graphics g) { //called by repaint()	
		dbG.clearRect(0, 0, FRAME_WIDTH,FRAME_HEIGHT);
		dbG.drawImage(background, 0, 0, this);
		dbG.drawImage(chrctr, X, Y, this);
		dbG.drawImage(foreground, 0, 0, this);
		paint(dbG);
		g.drawImage(offscreen, 0, 0, this);
	}
}
