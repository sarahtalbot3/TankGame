package tanksquad;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import java.awt.*;


public class Terrain {
	
	// starting x coordinate
	
	public int x;
	
	// starting y coordinate
	
	public int y;
	
	// image of one block of terrain
	
	private BufferedImage terrainImg;
	
	// height of one block
	
	public int blockHeight;
	
	// width of one block
	
	public int blockWidth;
	
	// length of terrain in x direction in repetition of blocks
	
	public int xLength;
	
	// length of terrain in y direction in repetition of blocks
	
	public int yLength;
	
	// area of terrain
	
	public Rectangle terrainArea;
	
	// width of terrain in pixels
	
	public int xRealLength;
	
	// height of terrain in pixels
	
	public int yRealLength;
	
	
	public Terrain(int n) {
		LoadContent();
		LoadTerrain(n);
		this.xRealLength = xLength * blockWidth;
		this.yRealLength = yLength * blockHeight;
		this.terrainArea = new Rectangle(this.x, this.y, this.xRealLength, this.yRealLength);
		while (Game.tank.tankArea.intersects(terrainArea) || Game.tank2.tankArea.intersects(terrainArea)) {
			LoadTerrain(n);
			this.xRealLength = xLength * blockWidth;
			this.yRealLength = yLength * blockHeight;
			this.terrainArea = new Rectangle(this.x, this.y, this.xRealLength, this.yRealLength);
		}
		
	}
	
	private void LoadContent() { 
		try
        {
            URL terrainImgUrl = this.getClass().getResource("/tanksquad/res/Terrain.png");
            terrainImg = ImageIO.read(terrainImgUrl);
            blockHeight = terrainImg.getHeight();
            blockWidth = terrainImg.getWidth();
            
            
            
           
        }
        catch (IOException ex) {
            Logger.getLogger(Tank.class.getName()).log(Level.SEVERE, null, ex);
        } 
	}
	
	private void LoadTerrain(int n) {
		if (n == 1) {
			this.xLength = (int)((Math.random() * (20)) + 1);
			if (this.xLength == 1) {
				this.yLength = (int)((Math.random() * (20)) + 1);
			}
			else {
				this.yLength = 1;
			}
		}
		else {
			this.yLength = (int)((Math.random() * (20)) + 1);
			if (this.yLength == 1) {
				this.xLength = (int)((Math.random() * (20)) + 1);
			}
			else {
				this.xLength = 1;
			}
		}
    	this.x = (int)((Math.random() * Framework.frameWidth / 2) + (Framework.frameWidth / 4));
    	this.y = (int)((Math.random() * Framework.frameHeight / 2) + (Framework.frameHeight / 4));
	}
	
	public void Reset(int n) {
		LoadTerrain(n);
		this.xRealLength = xLength * blockWidth;
		this.yRealLength = yLength * blockHeight;
		this.terrainArea = new Rectangle(this.x, this.y, this.xRealLength, this.yRealLength);
		while (Game.tank.tankArea.intersects(terrainArea) || Game.tank2.tankArea.intersects(terrainArea)) {
			LoadTerrain(n);
			this.xRealLength = xLength * blockWidth;
			this.yRealLength = yLength * blockHeight;
			this.terrainArea = new Rectangle(this.x, this.y, this.xRealLength, this.yRealLength);
		}
		
	}
	
	public void Draw(Graphics2D g2d) {
		 
		g2d.setColor(Color.black);
		 
		for (int i = 0; i < xLength; i++) {
			g2d.drawImage(terrainImg, (x + (i * blockWidth)), y, null);
		}
		
		for (int i = 0; i < yLength; i++) {
			g2d.drawImage(terrainImg, x , (y + (i * blockHeight)), null);
		}
		
	}

}
