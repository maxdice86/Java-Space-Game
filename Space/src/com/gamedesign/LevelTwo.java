package com.gamedesign;

import java.awt.Graphics2D;

import com.engine.Engine;
import com.engine.GameState;
import com.entities.ID;
import com.entities.Paralax;
import com.entities.Player;

public class LevelTwo extends GameState
{
	private Player playerone;
	
	private Paralax bg1,bg2,bg3, bg4,bg5, bg6;
	
	public  LevelTwo()
	{
				
		init();
	}

	@Override
	
	public void update() {

		Engine.obj_manager.update();
		
		//bg2.updateX(0.5);
		bg3.updateX(2.5);
	
		bg4.updateX(1);
		bg4.updateZ(1);
		
		bg5.updateX(1);
		bg5.updateZ(1);
		
		bg6.updateX(1);
		bg6.updateZ(1);
						
	}
	
	@Override
	public void render(Graphics2D g) {

		bg1.draw3D(g);
	    
		bg4.draw2D(g);
		bg5.draw2D(g);
		bg6.draw2D(g);
		
		bg2.draw3D(g);
	    bg3.draw3D(g);
		 
		Engine.obj_manager.render(g);
	}

	
	@Override
	public void init() {
		
		Engine.obj_manager.setActive(true);
		Engine.obj_manager.startTimers();
		
		Engine.gui.setUP();
		Engine.audiomg.sfx.get("bgm1").setVolume(0.5f);
		Engine.audiomg.sfx.get("bgm1").setLoop();
		
		 bg1 = new Paralax("/Background/spr_black.png", 0,1280, 512,2560,2560);
		 bg2 = new Paralax("/Background/spr_stars01.png",0,1280, 768 ,2560 , 2560 );
		 bg3 = new Paralax("/Background/spr_stars02.png",0, 1280, 384, 2560 , 2560 );
		 
		 bg4 = new Paralax("/Background/1.png",1280, 300, 2056 , 512 , 512 );
		 bg5 = new Paralax("/Background/2.png",1980, 256, 3056 , 512 , 512 );
		 bg6 = new Paralax("/Background/3.png",2560,-300, 4056 , 512 , 512 );

		 playerone = new Player(-200, 0, 512, 48, 48, 0, ID.Player);
		 
		 Engine.obj_manager.allobjects.add(playerone);
	}
	
}