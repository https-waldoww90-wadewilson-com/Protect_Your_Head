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
//with every shot to the head, character flashes then gets bigger
//game over once jumping causes head to disappear - go to cutscene
//where his head is in the clouds and he gets shot in the end and dies
	
	public int X, Y, nCounter, nRightLeftChck, nRightLeftJump;
	public double dCounter = 4;
	public Image offscreen;
	public Graphics d;
	public boolean bJump, bDown, bLeft, bRight;
	public BufferedImage background, foreground, w0, w0r, w1, w1r, w2, w2r, w3, w3r, w4, w4r, chrctr;
	//@Override
	public void run() {
		X=100;
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
		nRightLeftChck=1;
		nRightLeftJump=0;
		chrctr = w0;//start position
		Y=196;
		while (true) {	
			if (Y <= 196 && bJump !=true){
				Y+=5;//downward speed
			}
			nCounter++;
			if (nCounter >= 20) {
				nCounter=0;
			}
			if (bLeft == true && bRight == true && bJump!=true) {
				if (nRightLeftChck == 1) {
					switch(nCounter) {
						case 0: chrctr = w0;
						nCounter=0;
						break;
					}
				} else {
					switch(nCounter) {
						case 0: chrctr = w0r;
						nCounter=0;
						break;
					}
				}
			} else {
				
				if (bRight ==true) {
					switch(nCounter) {
						case 0: chrctr = w0;break;
						case 5:chrctr = w1;break;
						case 10: chrctr = w3;break;
						case 15: chrctr = w4;break;
					}
				} 
				if (bLeft == true) {
					switch(nCounter) {
						case 0: chrctr = w0r;break;
						case 5:chrctr = w1r;break;
						case 10: chrctr = w3r;break;
						case 15: chrctr = w4r;break;
					}
				}
			}
			if (bRight != true && bLeft != true) {
				if (nCounter <=5 && bRight == true) {
					chrctr = w1;
				}
				if (nCounter >= 5 && nCounter <=10 && bRight == true) {
					chrctr = w2;
				}
				if (nCounter >= 10 && nCounter <=5 && bRight == true) {
					chrctr = w3;
				}
				if (nCounter >= 10 && nCounter <=15 && bRight == true) {
					chrctr = w4;
				}
			}		
			if (bJump == true && bRight == true && bLeft == true) {
				if (nRightLeftJump == 1) {
					X+=2;		
				} else if (nRightLeftJump==2) {
					X-=2;			
				}
			} else {
				if (bLeft == true && Y>=196 || nRightLeftJump == 2 && bJump==true) {
					X-=2;
				}
				if (bRight == true && Y>=196|| nRightLeftJump == 1 && bJump==true) {
					X+=2;	
				}
			}			
			if (bJump == true) {
				dCounter +=0.05;
				Y = Y + (int) ((Math.sin(dCounter) + Math.cos(dCounter)) * 4);
				//*4 -> amplitude of jump
				if(dCounter>=7){	
					Y=196;
					bJump=false;
					nRightLeftJump=0;
					dCounter=4;//bottom of jump		
					if (nRightLeftChck == 1) {		
						chrctr = w0;//position to which he snaps back
					} else {
						chrctr = w0r;//position to which he snaps back
					}
				} 
				
				if (nRightLeftChck==1 && Y!=196) {
					if (bJump == true && bRight == true && bLeft == true) {
						chrctr = w0;
					} else {
						chrctr = w3;		
					}						
				}
				
				if (nRightLeftChck==2 && Y!=196) {				
					if (bJump == true && bRight == true && bLeft == true) {
						chrctr = w0r;
					} else {
						chrctr = w3r;
					}
				}
				//System.out.println(dCounter);
			}	
			//if (bDown == true) {
				//nY+=2;
			//}
			if (Y >= 196) {
				Y=196;
			}
			repaint();
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}	
	}
	@Override
	public void keyPressed(KeyEvent arg0) { //inherited
		
	 if (arg0.getKeyCode() == 37) {
		 bLeft = true;
		 if (bRight != true) {
			 nRightLeftChck = 2;
		 }		
	 }
	 if (arg0.getKeyCode() == 39) {
		 bRight = true;
		 if (bLeft!=true) {
			 nRightLeftChck = 1;
		 }	 	 
	 }
	 if (arg0.getKeyCode() == 38) {
		 bJump = true;
		 if (nRightLeftChck==1 && Y==196) {
			 nRightLeftJump=1;
		 } else if (nRightLeftChck==2 && Y==196){
			 nRightLeftJump=2;
		 }
		
	 }
	// if (arg0.getKeyCode() == 40) {
	//	 bDown = true;
	 //}
	//System.out.println(nRightLeftChck);
	}
	@Override
	public void keyReleased(KeyEvent arg0) { //inherited
		
		if (arg0.getKeyCode() == 37) {
			 bLeft = false;
			 if (nRightLeftChck==2 && bRight==false) {
				 chrctr = w0r; //position to which he snaps back 
			 } else {
				 nRightLeftChck=1;
				 chrctr=w0;
			 }
		}
			// if (arg0.getKeyCode() == 38) {
				//bJump = false;
				//dCounter = 4;
				 
			 //}
			if (arg0.getKeyCode() == 39) {
				 bRight = false;
				 if (nRightLeftChck==1 && bLeft==false) {
					 chrctr = w0;//position to which he snaps back
				 } else {
					 nRightLeftChck=2;
					 chrctr=w0r;
				 }
			 }
			//if (arg0.getKeyCode() == 40) {
				// bDown = false;
			 //} 
	}
	
	@Override
	public void keyTyped(KeyEvent arg0) {}//inherited abstract method

}
