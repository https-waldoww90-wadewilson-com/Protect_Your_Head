package general;

import java.awt.Graphics;
import java.util.Random;
import java.awt.Color;

public class Obstacles extends Main {
	 	private int squareX, squareY;
	    private int squareSize, squareVal;
	    private int fallSpeed;
	    private Random rand = new Random();
	    
	    private void generateRandomXLocation(){
	        squareX = rand.nextInt(frameWidth() - squareSize);
	    }

	    private void generateRandomSquareSize(){
	        squareSize = rand.nextInt(50);
	    }

	    private void generateRandomFallSpeed(){
	        fallSpeed = 2+rand.nextInt(9);
	    }
	    
	    private void generateSquareValue(int squareSize) {
	    	if (squareSize<=19) {
	        	squareVal=5;
	        } else if (squareSize <= 38) {
	        	squareVal=3;
	        } else {
	        	squareVal=1;
	        }		
		}
	    
	    private void reset() {
            generateRandomXLocation();
            generateRandomFallSpeed();
            generateRandomSquareSize();
            generateSquareValue(squareSize);
            squareY = -squareSize;
	    }

		public Obstacles(){
	    	reset();
	    }	
	    
	    private void animate(Graphics g) {
	    	if (squareY >= frameHeight()){
	        	reset();
	        }
	        if(squareY <= frameHeight()){
	        	if (squareguyCollision(squareX, squareY, squareSize, squareVal)|| squaregroundCollision(squareX, squareY, squareSize)) {
	        		reset();
	        		//System.out.println("OUCH!");
	        	} else {
	        		squareY += fallSpeed;
	        	}         
	        }
	    }
	    
	    public void paint(Graphics g){
	    	g.setColor(Color.RED);
	    	g.fillRect(squareX,squareY,squareSize,squareSize); 
	    	animate(g);
	    }    
}
