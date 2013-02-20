package tanksquad;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;

import javax.imageio.ImageIO;

public class Tank2 {
	
	// x coordinate of the tank
	
	public int x;
	
	// y coordinate at the tank
	
	public int y;
	
	// image of tank
	
	private BufferedImage tankImg;
	
	// rotation
	
	public double theta;
	
	// height of tank
	
	public int tankHeight;
	
	// width of tank
	
	public int tankWidth;
	
	// position of cannon
	
	public int cannonX;
	
	public int cannonY;
	
	public Rectangle tankRec;
	
	public Polygon tankArea;
	
	public Tank2() {
		LoadContent();
		x = (int) (Math.random() * (Framework.frameWidth - tankWidth));
		y = (int) (Math.random() * (Framework.frameHeight - tankHeight));
		theta = (Math.PI);
		cannonX = x + tankWidth;
		cannonY = y + (tankHeight / 2);
		tankRec = new Rectangle(x, y, tankWidth, tankHeight);
		AffineTransform affineTransform = new AffineTransform();
        affineTransform.rotate(theta, x + (tankWidth / 2) , y + (tankHeight / 2));
        
        tankArea = new Polygon(); 
        
        PathIterator i = tankRec.getPathIterator(affineTransform);
        while (!i.isDone()) {
            double[] xy = new double[2];
            i.currentSegment(xy);
            tankArea.addPoint((int) xy[0], (int) xy[1]);

            i.next();
        }
	}
	
	private void LoadContent() { 
		try
        {
            URL rocketImgUrl = this.getClass().getResource("/tanksquad/res/TankGrey.png");
            tankImg = ImageIO.read(rocketImgUrl);
            tankHeight = tankImg.getHeight();
            tankWidth = tankImg.getWidth();
           
        }
        catch (IOException ex) {
            Logger.getLogger(Tank.class.getName()).log(Level.SEVERE, null, ex);
        } 
	}
	
	public void Update() {
		
		if(Canvas.keyboardKeyState(KeyEvent.VK_J))
        	theta -= 0.05;
        
        if(Canvas.keyboardKeyState(KeyEvent.VK_L))
        	theta += 0.05;
        
        if(Canvas.keyboardKeyState(KeyEvent.VK_I)) {
            y = y + (int) (5 * (Math.sin(theta)));
        	x = x + (int) (5 * (Math.cos(theta)));}
        	
        if(Canvas.keyboardKeyState(KeyEvent.VK_K)) {
        	y = y + (int)-(5 * (Math.sin(theta)));
    		x = x + (int)-(5 * (Math.cos(theta)));}
        
        if (x > Framework.frameWidth)
        	x = (0 - tankWidth);
        
        if (y > Framework.frameHeight)
        	y = (0 - tankHeight);
        
        if (x < (0 - tankWidth))
        	x = Framework.frameWidth;
        
        if (y < (0 - tankHeight))
        	y = Framework.frameHeight;
        
        cannonX = (int) ((x - 5) + ((0.5 *(Math.cos(theta)) + 0.5) * tankWidth));
        cannonY = (int) ((y - (25)) + ((0.5 *(Math.sin(theta)) + 0.5) * tankWidth));
        
        tankArea.reset();
		AffineTransform affineTransform = new AffineTransform();
		tankRec = new Rectangle(x, y, tankWidth, tankHeight);
		affineTransform.rotate(theta, x + (tankWidth / 2) , y + (tankHeight / 2));
        PathIterator i = tankRec.getPathIterator(affineTransform);
        int j = 0;
        while (!i.isDone()) {
            double[] xy = new double[2];
            i.currentSegment(xy);
            if (j < 4) {
            tankArea.addPoint((int) xy[0], (int) xy[1]);}
            j++;
            i.next();
        }
	}
	
	public void Draw(Graphics2D g2d)
    {
        g2d.setColor(Color.black);
        
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.rotate(theta, x + (tankWidth / 2) , y + (tankHeight / 2));
        affineTransform.translate(x, y);

        g2d.drawImage(tankImg, affineTransform, null);
        
        }
	
	public boolean isShooting(long gameTime) {
		if (Canvas.keyboardKeyState(KeyEvent.VK_SHIFT) && ((gameTime - Shot.timeOfLastShot2) >= Shot.timeBetweenShots)) {
			return true;
		}
		else {
			return false;
		}
		
	}
	
	public void resetTank() {
		x = (int) (Math.random() * (Framework.frameWidth - tankWidth));
		y = (int) (Math.random() * (Framework.frameHeight - tankHeight));
		theta = (int) (Math.random() * 2 * Math.PI);
	}

}
