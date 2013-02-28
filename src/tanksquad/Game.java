package tanksquad;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.awt.*;

/**
 * Actual game.
 * 
 * @author www.gametutorial.net
 */

public class Game {
	
	public static Tank tank;
	
	public static Tank2 tank2;
	
	private ArrayList<Shot> shotList;
	
	private ArrayList<Shot> shotList2;
	
	public Terrain terrain1;
	
	public Terrain terrain2;
	
	public int scoreTank;
	
	public int scoreTank2;
	
	public static String winner;
	
	private int prevX1;
	
	private int prevY1;
	
	private double theta1;
	
	private int prevX2;
	
	private int prevY2;
	
	private double theta2;


    public Game()
    {
        Framework.gameState = Framework.GameState.GAME_CONTENT_LOADING;
        
        Thread threadForInitGame = new Thread() {
            @Override
            public void run(){
                // Sets variables and objects for the game.
                Initialize();
                // Load game files (images, sounds, ...)
                LoadContent();
                
                Framework.gameState = Framework.GameState.PLAYING;
            }
        };
        threadForInitGame.start();
    }
    
    
   /**
     * Set variables and objects for the game.
     */
    private void Initialize()
    {
    	tank = new Tank();
    	tank2 = new Tank2();
    	
    	terrain1 = new Terrain(1);
    	
    	System.out.println("initialized");
    	
    	terrain2 = new Terrain(0);
    	
    	

    	
    	shotList = new ArrayList<Shot>();
    	shotList2 = new ArrayList<Shot>();
    	
    	scoreTank = 0;
    	scoreTank2 = 0;
    	
    
    }
    
    /**
     * Load game files - images, sounds, ...
     */
    private void LoadContent()
    {
    	System.out.println("CONTENTLOADED");
    
    }    
    
    
    /**
     * Restart game - reset some variables.
     */
    public void RestartGame()
    {
        
    }
    
    
    /**
     * Update game logic.
     * 
     * @param gameTime gameTime of the game.
     * @param mousePosition current mouse position.
     */
    public void UpdateGame(long gameTime)
    {
    	savePreviousCoord();
    	tank.Update();
    	tank2.Update();
    	isTankCrashed();
    	isTank2Crashed();
    	isTankShooting(gameTime);
    	updateShots();
    	isTank2Shooting(gameTime);
    	updateShots2();
    	
    	if (scoreTank == 20) {
    		winner = "PLAYER 1";
    		Framework.gameState = Framework.GameState.GAMEOVER;
    	}
    	
    	if (scoreTank2 == 20) {
    		winner = "PLAYER 2";
    		Framework.gameState = Framework.GameState.GAMEOVER;
    	}
    }
    
    /**
     * Draw the game to the screen.
     * 
     * @param g2d Graphics2D
     * @param mousePosition current mouse position.
     */
    public void Draw(Graphics2D g2d, Point mousePosition)
    {
    	tank.Draw(g2d);
    	tank2.Draw(g2d);
    	for(int i = 0; i < shotList.size(); i++)
         {
             shotList.get(i).Draw(g2d);
         }
    	for(int i = 0; i < shotList2.size(); i++)
        {
            shotList2.get(i).Draw(g2d);
        }
    	terrain1.Draw(g2d);
    	terrain2.Draw(g2d);
    	g2d.drawString("Tank 1 : " + scoreTank, 5, 15);
    	g2d.drawString("Tank 2 : " + scoreTank2, 5, 30);
    }
    
    private void savePreviousCoord() {
    	prevX1 = tank.x;
    	prevY1 = tank.y;
    	theta1 = tank.theta;
    	prevX2 = tank2.x;
    	prevY2 = tank2.y;
    	theta2 = tank2.theta;
    }
    
    private void isTankCrashed() {
    	
    	if (tank.tankArea.intersects(terrain1.terrainArea) || tank.tankArea.intersects(terrain2.terrainArea)) {
    		tank.x = prevX1;
    		tank.y = prevY1;
    		tank.theta = theta1;
    	}
    }
    
    private void isTank2Crashed() {
    	
    	if (tank2.tankArea.intersects(terrain1.terrainArea) || tank2.tankArea.intersects(terrain2.terrainArea)) {
    		tank2.x = prevX2;
    		tank2.y = prevY2;
    		tank2.theta = theta2;
    	}
    }
    
    private void isTankShooting(long gameTime) {
    	if (tank.isShooting(gameTime)) {
    		Shot.timeOfLastShot = gameTime;
    		
    		Shot s = new Shot(tank.cannonX, tank.cannonY, - tank.theta);
    		shotList.add(s);
    	}
    }
    
    private void isTank2Shooting(long gameTime) {
    	if (tank2.isShooting(gameTime)) {
    		Shot.timeOfLastShot2 = gameTime;
    		
    		Shot s = new Shot(tank2.cannonX, tank2.cannonY, - tank2.theta );
    		shotList2.add(s);
    	}
    }
    private void updateShots() {
    	for (int i = 0; i < shotList.size(); i++ ) {
    		
    		Shot shot = shotList.get(i);
    		
    		shot.Update();
    		
    		Rectangle shotRectangle = new Rectangle(shot.x, shot.y, shot.shotWidth, shot.shotHeight);
    		
    		if (tank2.tankArea.intersects(shotRectangle)) {
    			scoreTank++;
    			shotList.remove(i);
    			tank2.resetTank();
    			terrain1.Reset(1);
    			terrain2.Reset(0);
    		}
    		else {
    			if (shotRectangle.intersects(terrain1.terrainArea) || shotRectangle.intersects(terrain2.terrainArea)) {
    			shotList.remove(i);
    			}
    			else {
    				if (shot.isItLeftScreen())
    					shotList.remove(i);
    		
    			}
    		}
    		
    		
    	}
    }
    private void updateShots2() {
    	for (int i = 0; i < shotList2.size(); i++ ) {
    		
    		Shot shot = shotList2.get(i);
    		
    		shot.Update();
    		
    		Rectangle shotRectangle = new Rectangle(shot.x, shot.y, shot.shotWidth, shot.shotHeight);
    		
    		if (tank.tankArea.intersects(shotRectangle)) {
    			scoreTank2++;
    			shotList2.remove(i);
    			tank.resetTank();
    			terrain1.Reset(1);
    			terrain2.Reset(0);
    		}
    		else {
    		
    				if (shotRectangle.intersects(terrain1.terrainArea) || shotRectangle.intersects(terrain2.terrainArea)) {
    					shotList2.remove(i);
    				}
    				else {
    					if (shot.isItLeftScreen())
    						shotList2.remove(i);
    				}
    		}
    		
    		
    		
    	}
    }
    
}
