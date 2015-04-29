/* TODO
 * reset position once score is reached or guy dies
 */
package general;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Main extends Game_loop {
    // private
	private static final int FRAME_WIDTH = 600;
    private static final int FRAME_HEIGHT = 400;
    public static final int GROUND_HEIGHT=100;
    private static Color background= new Color(135,206,250);
    private static Color foreground= new Color(35,139,34);
    private static final int NUM_BLOCKS = FRAME_WIDTH*GROUND_HEIGHT;
    private static final int NUM_OBSTACLES=lvl*10;
    private static Obstacles[] obstacleArray = new Obstacles[NUM_OBSTACLES];
	private static Rectangle[] groundArray = new Rectangle[NUM_BLOCKS/400];  
    private static Image offscreen; // offscreen img
	private static Graphics dbG; // double buffer graphic
	public static List<Rectangle> groundList; 
    
	public static final int frameHeight() {
		return FRAME_HEIGHT;
	}
	public static final int frameWidth() {
		return FRAME_WIDTH;
	}
	@Override
	public void init() {
		setSize(FRAME_WIDTH,FRAME_HEIGHT);
		setBackground(background);
		setForeground(foreground);
		Thread th = new Thread(this);
		th.start();
		addKeyListener(this);
		offscreen = createImage(FRAME_WIDTH,FRAME_HEIGHT); //this.getSize().width, this.getSisze().height
		dbG = offscreen.getGraphics();
		//initializes square objects
        for (int i = 0; i < obstacleArray.length; i++) {
        	obstacleArray[i] = new Obstacles();
        }
        //initialize ground blocks
        for (int i=0, k=0;i<FRAME_WIDTH;i+=20) {
        	for (int j=0;j<GROUND_HEIGHT;j+=20) {
        		Rectangle block = new Rectangle();
        		block.setSize(19, 19);
        		block.setLocation(i, FRAME_HEIGHT-block.height-j);
        		groundArray[k]=block;
        		k++;
        	}   	
        }
        groundList= new LinkedList<Rectangle>(Arrays.asList(groundArray));
        /*
        for (Rectangle block : groundArray) {
        	if (block==null) {
        		System.out.println("FUCK");
        	}
        }*/
	}
	
	private void paintGround(Rectangle block, Graphics g) {
		g.setColor(getForeground());
		g.fillRect(block.x, block.y, block.width, block.height);
	}
	
	@Override
	public void paint(Graphics g) {	
		for (Rectangle block : groundList) {
				paintGround(block, g);				
		}
		for (Obstacles obstacle : obstacleArray) {
			obstacle.paint(g);
	    }
	}
	
	@Override
	public void update(Graphics g) { //called by repaint()
		dbG.clearRect(0, 0, FRAME_WIDTH,FRAME_HEIGHT);		
		dbG.setColor(getBackground());
		dbG.fillRect(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
		//System.out.println(guyX() + " " + guyY());
		dbG.drawImage(state, guyX(), guyY(), this);	
		paint(dbG);
		g.drawImage(offscreen, 0, 0, this);
	}
	
	public boolean squareguyCollision(int squareX, int squareY, int squareSize, int squareVal) {
		//System.out.println(guyX() + " " + guyY());
    	if (squareX+squareSize >= guyX() && squareX <= guyX() + guyWidth()) {
    		if (squareY+squareSize >= guyY() && squareY <= guyY() + guyHeight()) {
    			score+=squareVal;
    			//System.out.println(score);
    		    return true;
    		} 
    	}
    	return false;
    }
	
	public boolean squaregroundCollision(int squareX, int squareY, int squareSize) {
		//System.out.println(guyX() + " " + guyY());
		int collisions=0;
		List<Rectangle> removeList = new LinkedList<Rectangle>(); 
		for (Rectangle block:groundList) {
			if (squareX+squareSize >= block.x && squareX <= block.x + block.width) {
	    		if (squareY+squareSize >= block.y && squareY <= block.y + block.height) {
	    			removeList.add(block);
					collisions++;
	    		} 
	    	}		
		}
		if (collisions>0) {
			for (Rectangle candidate:removeList) {
				groundList.remove(candidate);
			}
			return true;
		} 
		return false;
    }
}
