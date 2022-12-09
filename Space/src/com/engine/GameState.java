package com.engine;
import java.awt.Graphics2D;

// TODO: Auto-generated Javadoc
/**
 * The Class GameState.
 */
public abstract class GameState {

	/** The lv manger. */
	//protected LevelManger lv_manger;
		
	/**
	 * Update.
	 */
	public abstract void update();
	
	/**
	 * Inits the.
	 */
	public abstract void init();
	
	/**
	 * Render.
	 *
	 * @param g the g
	 */
	public abstract void render(Graphics2D g);

}
