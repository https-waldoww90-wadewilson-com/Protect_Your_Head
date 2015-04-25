//imgs courtesy of http://en.wikipedia.org/wiki/Walk_cycle
package general;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Game_loop extends Applet implements Runnable, KeyListener {

//game is divided into levels of increasing difficulty, where shit falls out of the sky
//and dave has to either manoevre s.t. his head doesn't get hit or position himself under a
//temporary block of concrete (destroyed after 3 hits), dirt (2 hits), or grass (destroyed after 1 hit)
//as you can see there's a strategy to it since multiple blocks will pop up at once
//with every shot to the head, character flashes then gets bigger
//game over once jumping causes head to disappear - go to cutscene
//where his head is in the clouds and a ton of shit hits him in the head til he dies
//occassionally power ups will pop on blocks that will create a temporary shield above his head
//made of either concrete, dirt, or grass
	
	// public
	public int X, Y;
	public BufferedImage background, foreground;
	public BufferedImage chrctr; // animation state
	// private
	private int timer;
	private int rightorleft; // '1' if facing right; '2' if facing left; never '0'
	private int rightJumporleftJump; // '1' if its a right jump; '2' if its a left jump; '0' if its neither
	private int lvl=0;
	private double dCounter = 4;
	private boolean bJump, bLeft, bRight;
	private BufferedImage w0, w0r, w1, w1r, w2, w2r, w3, w3r, w4, w4r;
	
	@Override
	public void run() {
		X=50;
		Y=100;
		try {
			background = ImageIO.read(new File("background.png"));
			foreground = ImageIO.read(new File("foreground.png"));
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
		rightorleft=1;
		rightJumporleftJump=0;
		chrctr = w0;
		Y=196; // measured from top
		while (true) {	
			if (Y <= 196 && bJump !=true){
				Y+=5;//downward speed
			}
			timer++;
			if (timer >= 20) {
				timer=0;
			}
			if (bLeft == true && bRight == true && bJump!=true) {
				if (rightorleft == 1) {
					switch(timer) {
						case 0: chrctr = w0;
						timer=0;
						break;
					}
				} else {
					switch(timer) {
						case 0: chrctr = w0r;
						timer=0;
						break;
					}
				}
			} else {
				
				if (bRight ==true) {
					switch(timer) {
						case 0: chrctr = w0;break;
						case 5:chrctr = w1;break;
						case 10: chrctr = w3;break;
						case 15: chrctr = w4;break;
					}
				} 
				if (bLeft == true) {
					switch(timer) {
						case 0: chrctr = w0r;break;
						case 5:chrctr = w1r;break;
						case 10: chrctr = w3r;break;
						case 15: chrctr = w4r;break;
					}
				}
			}
			if (bRight != true && bLeft != true) {
				if (timer <=5 && bRight == true) {
					chrctr = w1;
				}
				if (timer >= 5 && timer <=10 && bRight == true) {
					chrctr = w2;
				}
				if (timer >= 10 && timer <=5 && bRight == true) {
					chrctr = w3;
				}
				if (timer >= 10 && timer <=15 && bRight == true) {
					chrctr = w4;
				}
			}		
			if (bJump == true && bRight == true && bLeft == true) {
				if (rightJumporleftJump == 1) {
					if (X<=516) {
						X+=4;	
					} else {
						X=-20;
						X+=4;
						lvl++;
					}
						
				} else if (rightJumporleftJump==2) {
					if (X>=-11) {
						X-=4;	
					}			
				}
			} else {
				if (bLeft == true && Y>=196 || rightJumporleftJump == 2 && bJump==true) {
					if (X>=-10) {
						X-=5;
					}
					//System.out.println(X);
				}
				if (bRight == true && Y>=196|| rightJumporleftJump == 1 && bJump==true) {
					if (X<=515) {
						X+=5;
					} else {
						X=-20;
						X+=5;
						lvl++;
					}	
					//System.out.println(X);
				}
			}			
			if (bJump == true) {
				dCounter +=0.05;
				Y = Y + (int) ((Math.sin(dCounter) + Math.cos(dCounter)) * 4);
				//*4 -> amplitude of jump
				if(dCounter>=7){	
					Y=196;
					bJump=false;
					rightJumporleftJump=0;
					dCounter=4;//bottom of jump		
					if (rightorleft == 1) {		
						chrctr = w0;//position to which he snaps back
					} else {
						chrctr = w0r;//position to which he snaps back
					}
				} 
				
				if (rightorleft==1 && Y!=196) {
					if (bJump == true && bRight == true && bLeft == true) {
						chrctr = w0;
					} else {
						if (rightJumporleftJump==0 && bRight==false) {
							chrctr=w0;
						} else {
							chrctr = w3;
						}					
					}						
				}
				
				if (rightorleft==2 && Y!=196) {				
					if (bJump == true && bRight == true && bLeft == true) {
						chrctr = w0r;
					} else {
						if (rightJumporleftJump==0 && bLeft == false) {
							chrctr=w0r;
						} else {
							chrctr = w3r;
						}	
					}
				}
			}	
			if (Y >= 196) {
				Y=196;
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
		 if (bRight==true && rightorleft == 1 && Y==196) {
			 rightJumporleftJump=1; // right jump
		 } else if (bLeft==true && rightorleft==2 && Y==196){
			 rightJumporleftJump=2; // left jump
		 }
		
	 }

	}
	@Override
	public void keyReleased(KeyEvent arg0) { //inherited
		
		if (arg0.getKeyCode() == 37) {
			 bLeft = false;
			 if (rightorleft==2 && bRight==false) {
				 chrctr = w0r; //position to which he snaps back 
			 } else {
				 rightorleft=1;
				 chrctr=w0;
			 }
		}
			if (arg0.getKeyCode() == 39) {
				 bRight = false;
				 if (rightorleft==1 && bLeft==false) {
					 chrctr = w0;//position to which he snaps back
				 } else {
					 rightorleft=2;
					 chrctr=w0r;
				 }
			 }
	}
	
	@Override
	public void keyTyped(KeyEvent arg0) {}//inherited abstract method

}
