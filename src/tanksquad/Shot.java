package tanksquad;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

public class Shot {
	
	// time between new shots
	
	public final static long timeBetweenShots = Framework.secInNanosec / 2;
	
	public static long timeOfLastShot;
	
	public static long timeOfLastShot2;
	
	// x coordinate of tank
	
	public int x;
	
	// y coordinate of tank
	
	public int y;
	
	// width of shot
	
	public int shotWidth;
	
	// height of shot
	
	public int shotHeight;
	
	// image of shot
	
	private BufferedImage shotImg;
	
	// rotation
	
	public double theta;
	
	// speed of bullet
	
	private static int shotSpeed = 10;
	
	// 
	
	public double xSpeed;
	
	public double ySpeed;
	
	public Shot(int xCoord, int yCoord , double t) {
		LoadContent();
		this.x = xCoord;
		this.y = yCoord;
		this.theta = t;
		
		setDirectionAndSpeed();
		
		
	}
	
	private void LoadContent() {
		
		try {
		URL shotImgUrl = this.getClass().getResource("/tanksquad/res/Shot.png");
		shotImg = ImageIO.read(shotImgUrl);
		shotWidth = shotImg.getWidth();
		shotHeight = shotImg.getHeight();
		}
		
		catch (IOException ex) {
            Logger.getLogger(Shot.class.getName()).log(Level.SEVERE, null, ex);
        }
	}
	
	private void setDirectionAndSpeed() {
		this.xSpeed = shotSpeed * Math.cos(this.theta);
		this.ySpeed = - (shotSpeed * Math.sin(this.theta));
	}
	
	public void Update() {
		x += xSpeed;
		y += ySpeed;
	}
	
	public void Draw(Graphics2D g2d) {
		AffineTransform affineTransform = new AffineTransform();
        affineTransform.translate(x, y);

        g2d.drawImage(shotImg, affineTransform, null);
	}
	
	public boolean isItLeftScreen()
    {
        if(x > 0 && x < Framework.frameWidth &&
           y > 0 && y < Framework.frameHeight)
            return false;
        else
            return true;
    }
	
	

}
