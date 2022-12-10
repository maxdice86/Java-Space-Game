package com.gamedesign;

import java.awt.Graphics2D;

import com.engine.Engine;
import com.engine.Fader;
import com.engine.GameState;
import com.engine.InputManager;
import com.engine.TextBox;
import com.entities.Paralax;

public class Cutscene extends GameState {

	private String story;
	private Paralax bg1, bg2, bg3;
	private TextBox plot;
	private Fader fadeIn, fadeOut;
	private boolean toFade;

	public Cutscene() {
		init();
	}

	@Override
	public void init() {

		story = "The Imperial Forces are under orders from cruel Darth Vader\n "
				+ "and hold Princess Leia hostage in their efforts to quell the\n"
				+ "rebellion against the Galactic Empire.Luke Skywalker and Han Solo\n"
				+ "captain of the Millennium Falcon, work together with the companionable\n"
				+ "droid duo R2-D2 and C-3PO to rescue the beautiful princess,\n"
				+ "help the Rebel Alliance, and restore freedom and justice to the Galaxy.\n";

		plot = new TextBox(story, 0, 340, 512);
		toFade = true;
		fadeIn = new Fader(1);
		fadeOut = new Fader(0);

		bg1 = new Paralax("/Background/spr_black.png", 0, 1280, 512, 2560, 2560);
		bg2 = new Paralax("/Background/spr_stars01.png", 0, 1280, 768, 2560, 2560);
		bg3 = new Paralax("/Background/spr_stars02.png", 0, 1280, 384, 2560, 2560);

		Engine.audiomg.sfx.get("intro").setVolume(1.0f);
		Engine.audiomg.sfx.get("intro").setLoop();

	}

	@Override
	public void update() {
		bg3.updateX(1.5);
		plot.moveBox(0, -1);

		if (InputManager.en_pressed || InputManager.m_pressed) {
			Engine.audiomg.sfx.get("intro").stop();
			Engine.level_manger.setState(1);
		}
	}

	@Override
	public void render(Graphics2D g) {
		bg1.draw3D(g);
		bg2.draw3D(g);
		bg3.draw3D(g);

		plot.render(g);
		fadeIn.render(g);

	}

}
