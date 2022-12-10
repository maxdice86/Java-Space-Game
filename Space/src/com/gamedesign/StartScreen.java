package com.gamedesign;

import java.awt.*;
import com.engine.*;
import com.entities.*;

// TODO: Auto-generated Javadoc
/**
 * The Class StartScreen.
 */
public class StartScreen extends GameState {

	private Paralax bg1, bg2, bg3;

	private int selected = 2;

	private String[] choices;

	private Icons playButton, optionButton, exitButton, Title, menu;

	public int mx, my;

	boolean fade = false;

	public Fader faderIn, faderOut;

	private long timeOfLastPressed;

	private boolean optionScreen;

	public StartScreen() {
		init();
	}

	@Override
	public void init() {

		faderIn = new Fader(1);
		faderOut = new Fader(0);
		optionScreen = false;

		Engine.audiomg.sfx.get("play").setVolume(1.5f);
		Engine.audiomg.sfx.get("start").setVolume(1.25f);
		Engine.audiomg.sfx.get("select").setVolume(1.5f);

		Engine.audiomg.sfx.get("start").setLoop();

		choices = new String[] { "Ext", "Opt", " Ply" };

		bg1 = new Paralax("/Background/spr_black.png", 0, 1280, 512, 2560, 2560);
		bg2 = new Paralax("/Background/spr_stars01.png", 0, 1280, 768, 2560, 2560);
		bg3 = new Paralax("/Background/spr_stars02.png", 0, 1280, 384, 2560, 2560);

		createButtons();

	}

	private void createMenu() {
		optionScreen = true;
		selected = 3;
		menu = new Icons("/Gui/Window.png", 0, 200, 512, 640, 320);
		Title = null;
		playButton = null;
		optionButton = null;
		exitButton = null;
	}

	private void goBack() {
		System.out.println("going back");
		selected = 2;
		menu = null;
		createButtons();

	}

	private void createButtons() {

		playButton = new Icons("/Menu/Play", 0, 0, 512, 178, 64, 2);
		optionButton = new Icons("/Menu/Options", 0, 96, 512, 178, 64, 2);
		exitButton = new Icons("/Menu/Exit", 0, 192, 512, 178, 64, 2);

		Title = new Icons("/Background/Title", 0, -(int) Camera3D.origin_y + 192, 512, 632, 64 * 2, 1);

	}

	public void padNavigate()

	{

		long timeNow = System.currentTimeMillis();
		long idelay = timeNow - timeOfLastPressed;

		// Fixes multiple inputs from Game pad//

		if (selected != 3) {

			if (InputManager.dn_pressed) {

				if (idelay < 0 || idelay > 250) {
					timeOfLastPressed = timeNow;

					Engine.audiomg.sfx.get("select").play();

					selected--;

					if (selected <= -1) {
						selected = choices.length - 1;
					}

				}

			}

			if (InputManager.up_pressed) {

				if (idelay < 0 || idelay > 250) {
					timeOfLastPressed = timeNow;

					Engine.audiomg.sfx.get("select").play();

					selected++;

					if (selected >= choices.length) {
						selected = 0;

					}

				}

			}
		}

	}

	public void checkMouse() {

		if (playButton.bounds.contains(InputManager.mx, InputManager.my)) {
			// Engine.audiomg.sfx.get("select").play();
			selected = 2;

		} else if (optionButton.bounds.contains(InputManager.mx, InputManager.my)) {
			// Engine.audiomg.sfx.get("select").play();
			selected = 1;

		} else if (exitButton.bounds.contains(InputManager.mx, InputManager.my)) {
			// Engine.audiomg.sfx.get("select").play();
			selected = 0;
		}
	}

	public void checkSelected() {

		if (selected == 2 && InputManager.en_pressed) {
			fade = true;
			Engine.audiomg.sfx.get("start").stop();
			Engine.audiomg.sfx.get("play").play();

			Engine.level_manger.setState(2);
		}

		else if (selected == 1 && InputManager.en_pressed) {

			Engine.audiomg.sfx.get("play").play();

			createMenu();

		}

		else if (selected == 0 && InputManager.m_pressed || selected == 0 && InputManager.en_pressed) {
			System.exit(0);
		}

		else if (selected == 3 && InputManager.m_pressed || selected == 3 && InputManager.en_pressed) {
			goBack();
		}

	}

	@Override
	public void update() {

		padNavigate();
		// checkMouse();
		checkSelected();
		bg2.updateX(0.5);
		bg3.updateX(2.5);

	}

	@Override
	public void render(Graphics2D g) {

		bg1.draw3D(g);
		bg2.draw3D(g);
		bg3.draw3D(g);

		if (menu != null)
			menu.draw2D(g);

		if (Title != null)

		{
			Title.draw3D(g, false);

			if (selected == 2) {

				playButton.draw3D(g, true);

			} else
				playButton.draw3D(g, false);

			if (selected == 1) {

				optionButton.draw3D(g, true);

			} else
				optionButton.draw3D(g, false);

			if (selected == 0) {

				exitButton.draw3D(g, true);

			} else
				exitButton.draw3D(g, false);

		}

		if (fade)
			faderOut.render(g);
		g.setColor(Color.green);

		g.drawString(" Created By : Maurice Blake CMP428", 940, 640);

	}

}
