package general;

import java.awt.Graphics;
import java.util.Random;
import java.awt.Color;


public class Obstacles {
	 	private int squareXLocation;
	    private int squareSize;
	    private int squareYLocation;
	    private int fallSpeed;
	    private Random rand = new Random();
	    
	    private int generateRandomXLocation(){
	        return squareXLocation = rand.nextInt(Main.FRAME_WIDTH - squareSize);
	    }

	    private int generateRandomSquareSize(){
	        return squareSize = rand.nextInt(50);
	    }

	    private int generateRandomFallSpeed(){
	        return fallSpeed = 1+rand.nextInt(9);
	    }
	    
	    public void paint(Graphics g){
	    	g.setColor(Color.RED);
	    	g.fillRect(squareXLocation,squareYLocation,squareSize,squareSize); 
	    	if(squareYLocation >= Main.FRAME_HEIGHT){
	        	reset();
	        }
	        if(squareYLocation <= Main.FRAME_HEIGHT){
	            squareYLocation += fallSpeed;
	        }
	    }
	    
	    public void reset() {
            generateRandomXLocation();
            generateRandomFallSpeed();
            generateRandomSquareSize();
            squareYLocation = -squareSize;
	    }

	    public Obstacles(){
	    	reset();
	    }	    
}
