package com.engine;

import java.awt.Graphics2D;

import com.gamedesign.Cutscene;
import com.gamedesign.LevelOne;
import com.gamedesign.StartScreen;

/**
 * The Class LevelManger.
 */
public class LevelManger {

	public int startScreen = 0;

	public int Levelone = 1;

	public int cutscene1 = 2;

	public int Leveltwo = 3;

	public int cutscene2 = 4;

	public int Levelthree = 5;

	public int restart = 10;

	/** The game states. */
	public GameState[] gameStates;

	/** The current state. */
	public int currentState;

	public LevelManger(int NUMGAMESTATES) {

		gameStates = new GameState[NUMGAMESTATES];
		currentState = 0;
		loadState(currentState);
	}

	public void setState(int state) {
		unloadState(currentState);
		currentState = state;
		loadState(currentState);
	}

	private void loadState(int state) {
		if (state == startScreen)
			gameStates[state] = new StartScreen();
		if (state == Levelone)
			gameStates[state] = new LevelOne();
		if (state == cutscene1)
			gameStates[state] = new Cutscene();

	}

	private void unloadState(int state) {
		gameStates[state] = null;
	}

	public void update() {
		try {
			gameStates[currentState].update();
		} catch (Exception e) {
		}
	}

	public void render(Graphics2D g) {
		try {
			gameStates[currentState].render(g);
		} catch (Exception e) {
		}
	}

}
