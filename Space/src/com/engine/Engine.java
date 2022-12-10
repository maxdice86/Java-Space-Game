package com.engine;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;
import com.entities.HUD;

public class Engine extends JPanel implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6073474009552191126L;

	public static boolean running;

	public Thread loop;

	public Screen screen;

	public static AudioManager audiomg;

	public static LevelManger level_manger;

	public static ObjectManager obj_manager;

	public static HUD gui;

	// public static Gamepad pad;

	public static long startTime, elasped;

	public static long uptime;

	public Engine() {
		super();

		screen = new Screen(this);

		init();
	}

	public static void main(String[] args) {
		new Engine();
	}

	public void init() {

		requestFocusInWindow();

		this.addKeyListener(new InputManager());
		this.addMouseMotionListener(new InputManager());
		this.addMouseListener(new InputManager());

		gui = new HUD();
		// gui.setActive(true);
		audiomg = new AudioManager();
		obj_manager = new ObjectManager();
		obj_manager.setName("obj");
		level_manger = new LevelManger(7);

		startTime = System.currentTimeMillis();

		start();
		obj_manager.start();
	}

	public void start() {

		running = true;
		loop = new Thread(this, "loop");
		loop.start();
	}

	public void stop() {

		running = false;

		try {

			loop.join();

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	/**
	 * Update.
	 */
	public void update() {
		// pad.update();
		level_manger.update();
		gui.update();

	}

	/**
	 * Render.
	 */
	public void render() {
		// Render single frame
		do {
			// The following loop ensures that the contents of the drawing buffer
			// are consistent in case the underlying surface was recreated
			do {
				// Get a new graphics context every time through the loop
				// to make sure the strategy is validated
				Graphics2D g2d = (Graphics2D) screen.render.getDrawGraphics();

				////////// DRAW ONSCREEN///////////
				g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

				g2d.clearRect(0, 0, 1280, 720);

				level_manger.render(g2d);
				gui.render(g2d);
				obj_manager.render(g2d);

				// Dispose the graphics
				g2d.dispose();

				// Repeat the rendering if the drawing buffer contents
				// were restored
			} while (screen.render.contentsRestored());

			// Display the buffer
			screen.render.show();

			// Repeat the rendering if the drawing buffer was lost
		} while (screen.render.contentsLost());

	}

	@Override
	public void run() {

		while (running) {

			elasped = System.currentTimeMillis();

			uptime = ((elasped - startTime) / 1000);

			update();
			render();

			try

			{
				Thread.sleep(16);

			} catch (Exception x) {
				x.printStackTrace();
			}
			;
		}

		stop();
	}

}
