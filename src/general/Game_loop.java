//imgs courtesy of http://en.wikipedia.org/wiki/Walk_cycle
package general;

import java.applet.Applet;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Game_loop extends Applet implements Runnable, KeyListener {
	
	// public
	public BufferedImage state; // animation state
	public static int score=0, lvl=1, goal=lvl*150;
	// private
	private static int guyX, guyY;
	private static int guyHeight, guyWidth;
	private int timer;
	private int rightorleft; // '1' if facing right; '2' if facing left; never '0'
	private int rightJumporleftJump; // '1' if its a right jump; '2' if its a left jump; '0' if its neither
	private double dCounter = 4;
	private boolean bJump, bLeft, bRight;
	private BufferedImage w0, w0r, w1, w1r, w2, w2r, w3, w3r, w4, w4r;
	
	public static int guyX() {
		return guyX;
	}
	public static int guyY() {
		return guyY;
	}
	public static int guyHeight() {
		return guyHeight;
	}
	public static int guyWidth() {
		return guyWidth;
	}
	public boolean guygroundCollision() {
		//System.out.println(guyX() + " " + guyY());
		for (Rectangle block:Main.groundList) {
			if (guyX+guyWidth >= block.x && guyX <= block.x + block.width) {
	    		if (guyY+guyHeight >= block.y && guyY <= block.y + block.height) {
					return true;
	    		} 
	    	}		
		}
		return false;
    }
	@Override
	public void run() {
		try {
			w0 = ImageIO.read(new File("w0.png"));
			w0r = ImageIO.read(new File("w0r.png"));
			w1 = ImageIO.read(new File("w1.png"));
			w1r = ImageIO.read(new File("w1r.png"));
			w2 = ImageIO.read(new File("w2.png"));
			w2r = ImageIO.read(new File("w2r.png"));
			w3 = ImageIO.read(new File("w3.png"));
			w3r = ImageIO.read(new File("w3r.png"));
			w4 = ImageIO.read(new File("w4.png"));
			w4r = ImageIO.read(new File("w4r.png"));	
		} catch (IOException e1) {
			e1.printStackTrace();
		} 
		guyWidth=w0.getWidth();
		guyHeight=w0.getHeight();
		rightorleft=1;
		rightJumporleftJump=0;
		state = w0;
		guyX=50;
		guyY=196;
		while (true) {	
			//System.out.println(X+ " " + Y);
			
			/* START: STATE CONTROLLER */
			timer++;
			if (timer >= 25) {
				timer=0;
			}
			if (bLeft == true && bRight == true && bJump!=true) {
				if (rightorleft == 1) {
					switch(timer) {
						case 0: state = w0;
						timer=0;
						break;
					}
				} else {
					switch(timer) {
						case 0: state = w0r;
						timer=0;
						break;
					}
				}
			} else {
				
				if (bRight ==true) {
					switch(timer) {
						case 0: state = w0;break;
						case 5: state = w1;break;
						case 10: state = w2; break;
						case 15: state = w3;break;
						case 20: state = w4;break;
					}
				} 
				if (bLeft == true) {
					switch(timer) {
						case 0: state = w0r;break;
						case 5: state = w1r;break;
						case 10: state = w2r;break;
						case 15: state = w3r;break;
						case 20: state = w4r;break;
					}
				}
			}
			/* END: STATE CONTROLLER */
			
			/* START: HORIZONTAL MOVEMENT */
			if (bJump == true && bRight == true && bLeft == true) {
				if (rightJumporleftJump == 1) {
					if (guyX<=516) {
						guyX+=4;	
					} else {
						guyX=-20;
						guyX+=4;
					}
						
				} else if (rightJumporleftJump==2) {
					if (guyX>=-11) {
						guyX-=4;	
					} else {
						guyX=520;
						guyX-=4;
					}
				}
			} else {
				if (bLeft == true && guyY>=196 || rightJumporleftJump == 2 && bJump==true) {
					if (guyX>=-10) {
						guyX-=5;
					} else {
						guyX=520;
						guyX-=5;
					}
					//System.out.println(X);
				}
				if (bRight == true && guyY>=196|| rightJumporleftJump == 1 && bJump==true) {
					if (guyX<=515) {
						guyX+=5;
					} else {
						guyX=-20;
						guyX+=5;
					}	
					//System.out.println(X);
				}
			}	
			/* END: HORIZONTAL MOVEMENT */
			
			if (bJump == true) {
				dCounter +=0.05;
				guyY = guyY + (int) ((Math.sin(dCounter) + Math.cos(dCounter)) * 4);
				//*4 -> amplitude of jump
				if(dCounter>=7){	
					bJump=false;
					rightJumporleftJump=0;
					dCounter=4;//bottom of jump		
					if (rightorleft == 1) {		
						state = w0;//position to which he snaps back
					} else {
						state = w0r;//position to which he snaps back
					}
				} 
				
				if (rightorleft==1 && guyY!=196) {
					if (bJump == true && bRight == true && bLeft == true) {
						state = w0;
					} else {
						if (rightJumporleftJump==0 && bRight==false) {
							state=w0;
						} else {
							state = w3;
						}					
					}						
				}
				
				if (rightorleft==2 && guyY!=196) {				
					if (bJump == true && bRight == true && bLeft == true) {
						state = w0r;
					} else {
						if (rightJumporleftJump==0 && bLeft == false) {
							state=w0r;
						} else {
							state = w3r;
						}	
					}
				}
			}	
			
			if (guyY >= 196) {
				guyY=196;
			}
			
			/* *** */
			repaint();
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			/* *** */
		}	
	}
	@Override
	public void keyPressed(KeyEvent arg0) { //inherited
		
	 if (arg0.getKeyCode() == 37) { //left arrow
		 bLeft = true;
		 if (bRight != true) {
			 rightorleft = 2;
		 }		
	 }
	 if (arg0.getKeyCode() == 39) { //right arrow
		 bRight = true;
		 if (bLeft!=true) {
			 rightorleft = 1;
		 }	 	 
	 }
	 if (arg0.getKeyCode() == 38) { //upward arrow
		 bJump = true;
		 if (bRight==true && rightorleft == 1 && guyY==196) {
			 rightJumporleftJump=1; // right jump
		 } else if (bLeft==true && rightorleft==2 && guyY==196){
			 rightJumporleftJump=2; // left jump
		 }
		
	 }

	}
	@Override
	public void keyReleased(KeyEvent arg0) { //inherited
		
		if (arg0.getKeyCode() == 37) {
			 bLeft = false;
			 if (rightorleft==2 && bRight==false) {
				 state = w0r; //position to which he snaps back 
			 } else {
				 rightorleft=1;
				 state=w0;
			 }
		}
			if (arg0.getKeyCode() == 39) {
				 bRight = false;
				 if (rightorleft==1 && bLeft==false) {
					 state = w0;//position to which he snaps back
				 } else {
					 rightorleft=2;
					 state=w0r;
				 }
			 }
	}
	
	@Override
	public void keyTyped(KeyEvent arg0) {}//inherited abstract method
	
}
