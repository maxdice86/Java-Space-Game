package com.gamedesign;

import java.awt.Graphics2D;

import com.engine.Engine;
import com.engine.Fader;
import com.engine.GameState;
import com.entities.ID;
import com.entities.Paralax;
import com.entities.Player;
import com.entities.PowerUps;

public class LevelOne extends GameState {

	private Player playerone;
	PowerUps power;

	private Paralax bg1, bg2, bg3, bg4, bg5, bg6;

	private Fader fadeIn;
	private boolean fade;

	public LevelOne() {

		init();
	}

	@Override
	public void init() {

		Engine.obj_manager.setActive(true);
		Engine.obj_manager.startTimers();
		// fadeIn = new Fader(1);
		Engine.gui.setUP();

		Engine.audiomg.sfx.get("bgm1").setVolume(0.5f);
		Engine.audiomg.sfx.get("bgm1").setLoop();

		bg1 = new Paralax("/Background/spr_black.png", 0, 1280, 512, 2560, 2560);
		bg2 = new Paralax("/Background/spr_stars01.png", 0, 1280, 768, 2560, 2560);
		bg3 = new Paralax("/Background/spr_stars02.png", 0, 1280, 384, 2560, 2560);

		bg4 = new Paralax("/Background/1.png", 1280, 300, 2056, 512, 512);
		bg5 = new Paralax("/Background/2.png", 1980, 256, 3056, 512, 512);
		bg6 = new Paralax("/Background/3.png", 2560, -300, 4056, 512, 512);

		Engine.gui.playerLives = 3;

		playerone = new Player(-640, 0, 512, 64, 64, 0, ID.Player);

		Engine.gui.score = 0;

		Engine.obj_manager.allobjects.add(playerone);

		// fade = true;
	}

	@Override

	public void update() {

		if (Engine.gui.gameOver) {

			Engine.obj_manager.allobjects.remove(playerone);
			playerone = null;
			Engine.obj_manager.bossinGame = false;
		}

		// bg2.updateX(0.5);

		bg3.updateX(2.5);

		bg4.updateX(2.5);
		bg4.updateZ(2.0);

		bg5.updateX(1.5);
		bg5.updateZ(1);

		bg6.updateX(2);
		bg6.updateZ(1.5);

	}

	@Override
	public void render(Graphics2D g) {

		bg1.draw3D(g); // all-black

		bg4.draw2D(g); // earth
		bg5.draw2D(g); // planets
		bg6.draw2D(g); // panets
//		
		bg2.draw3D(g);
//	    bg3.draw3D(g);
		// power.render(g);
		Engine.obj_manager.render(g);
		// if(fade) fadeIn.render(g);
	}

}
