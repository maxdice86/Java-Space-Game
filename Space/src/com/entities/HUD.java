package com.entities;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import com.engine.Camera3D;
import com.engine.Engine;
import com.engine.GameState;
import com.engine.InputManager;

public class HUD {
	
	public  int x;
	public  int y;
	public  int score;
	public  int playerHealth;
	public  int playerPower;
	public  int playerLives;
	public  double bossLife;
	public  Line topbounds, bottombounds, rightbounds, leftbounds;
	public  Icons scoreDisplay;
	public  Icons powerDisplay;
	public  Icons overDisplay;
	public  Icons livesDisplay;
	public  Icons weaponDisplay;
	public  Icons pauseDisplay;
	public  boolean pause, active, gameOver, playIncoming, playLose;
	public  long pausetime;
	public  long timeOfLastPressed = 0;
	public GameState restart;
	 
	public  HUD()
	{
	
		 active = false;
		 pause = false;
		 gameOver = false;
		 playIncoming = false;
		 playLose = false;
		 
		 bossLife = 720;
		 playerLives = 3;

		 x = 0;
		 y = 0;
		 score = 0;
		 
		 scoreDisplay = new Icons("/GUI/Score_0.png", 400, -250,  512, 178, 32);	
		 livesDisplay = new Icons("/Player/player_0.png", -32,-250, 512, 32, 32);
		 overDisplay =  new Icons("/GUI/lose.png", 0, 0, 512, 300, 64);
		 
		 setBounds();
		 
			 
	}
	 
	public void setBounds() 
   {
		
//        topbounds    = new Line(0, 64, 1280, 64);
//		
//		bottombounds = new Line(1200, 680, 0, 680);
//		
//		rightbounds  = new Line(1232, 64, 1232, 640);
//		
//		leftbounds   = new Line(32, 640, 32, 64);
		
		
		    topbounds    = new Line(-Camera3D.origin_x + 32, -Camera3D.origin_y + 64, 1280, -Camera3D.origin_y + 64);
			
			bottombounds = new Line(Camera3D.origin_x + 284 , Camera3D.origin_y , -Camera3D.origin_x + 32, Camera3D.origin_y);
			
			rightbounds  = new Line(Camera3D.origin_x - 32 , -Camera3D.origin_y + 64, Camera3D.origin_x -32 , Camera3D.origin_y );
			
			leftbounds   = new Line(-Camera3D.origin_x + 32, Camera3D.origin_y + 320, -Camera3D.origin_x + 32, -Camera3D.origin_y + 64); 
	}
	
	
	public void removeBounds()
	{
		topbounds    = null;
		
		bottombounds = null;
		
		rightbounds  = null;
		
		leftbounds   = null;
	}
	public void setActive(boolean a) 
	{
		active = a;
	}
	
	public  void setScore(int sc) 
	{
		score += sc;
	}
	
	public  void setPlayerLives(int lives)
	{
		playerLives += lives;
	}

	public  void setPlayerHealth(int hp) 
	{
		playerHealth += hp;
	}
	
	public  void setBossLife(double hp) 
	{
		bossLife += hp;
	}


	public  void  setPlayerPower(int p)
	{
		playerPower += p;
	}
	
	public  void createMenu() 
	{
		
	}
	
	public void setPause()
	{
		pause = !pause;
	}
	
	public void setUP() 
	{
		setBounds();
		active = true;
	}
	
	public  void pauseGame() 
	{
	
		if(pause && active && !gameOver)
				{
			
			        pauseDisplay = new Icons("/GUI/Pause_0.png", 0, 0, 512, 300, 64);
			        
					pausetime = Engine.uptime;
															
					for(int i = 0 ; i < Engine.obj_manager.allobjects.size(); i++)
						
					Engine.obj_manager.allobjects.get(i).setEnabled(false);
					
					createMenu();
				}
		else if (!pause && active && !gameOver)
		{		
			  Engine.uptime = pausetime;
			 
			  pauseDisplay = null;
			  			 
			  for(int i = 0 ; i < Engine.obj_manager.allobjects.size(); i++)
					
				 Engine.obj_manager.allobjects.get(i).setEnabled(true);
		}
	  
	
  }
	
	
	public void playLose() 
	{
		if (playerLives <= 0 && !playLose) 
		{
			playLose = true;
			gameOver = true;
			playerLives = 3;

			Engine.audiomg.sfx.get("bgm1").stop();
			Engine.audiomg.sfx.get("incoming").stop();
			Engine.audiomg.sfx.get("lose").setLoop();
		} 
	}
	
	public void pauseButtonPress() 
	{
		long timeNow = System.currentTimeMillis();
		long idelay = timeNow - timeOfLastPressed;
		
		if (InputManager.en_pressed)
		{
	
		if (idelay < 0 || idelay > 250)
			{
				
				timeOfLastPressed = timeNow;
				
			    // Trigger associated action
				setPause();
				pauseGame();
			}
			
		}
	}
	
	public void checkRestart() 
	{
		if (gameOver)
		{
			
			 for(int i = 0 ; i < Engine.obj_manager.allobjects.size(); i++)
			 {
				 
				Engine.obj_manager.allobjects.remove(i);

			 }
			 
				 Engine.obj_manager.setActive(false);
				 Engine.obj_manager.stopTimers();

								
			 if (InputManager.en_pressed) 
			 {
				 
				 gameOver = false;
				 active = false;
				 Camera3D.setup(0, 0, 0);
				 Engine.level_manger.setState(0);

			 }
				 		 
		}
		
	}
	
	public void playIncoming() 
	{
		if(!playIncoming && Engine.obj_manager.bossinGame)
		{
			playIncoming = true;
			
			Engine.audiomg.sfx.get("bgm1").stop();
			Engine.audiomg.sfx.get("incoming").setLoop();
		}
	}
	
	public synchronized void update()
	{
		pauseButtonPress();	
		playLose();
		checkRestart();
		playIncoming();
		
	}

	public void render(Graphics2D g) 
	{
		g.setColor(new Color(0, 0, 0, 0));
		topbounds.drawDepth(g);
		bottombounds.drawDepth(g);
		rightbounds.drawDepth(g);
		leftbounds.drawDepth(g);
		
		if (active && !gameOver)
		{
			///Draw health bar///
			g.setColor(Color.red);
			g.fillRect( 32, 56, playerHealth, 32);
			g.setColor(Color.white);
			g.draw3DRect( 32, 56, 200, 32, false);
			
			//Draw Power bar///
			
			if(playerPower == 100)
				g.setColor(Color.green);
			else g.setColor(Color.blue);
			
			g.fillRect( 200, 56, playerPower, 32);
			g.setColor(Color.white);
			g.draw3DRect( 200, 56, 100, 32, false);
			
			Font myFont = new Font ("Impact New", 1, 38);
			g.setFont(myFont);

			g.drawString(" "+score, 1128, 88);

			g.drawString(""+playerLives, 632, 88);
			
			g.drawString("P", 200, 86);
			
			g.drawString("H", 32, 86);
			
			scoreDisplay.draw2D(g);
			livesDisplay.draw2D(g);
						
			if (pause && pauseDisplay != null) 
			{   
				pauseDisplay.draw2D(g);
			}
			
			if (Engine.obj_manager.bossinGame == true) 
			{   
				
					g.setColor(Color.red);
					g.fillRect( 320, 640, (int) bossLife, 32);
					g.setColor(Color.white);
					g.draw3DRect( 320, 640,720, 32, false);
				
			}
		   
		} else if (gameOver) overDisplay.draw2D(g);
		
	}
}
