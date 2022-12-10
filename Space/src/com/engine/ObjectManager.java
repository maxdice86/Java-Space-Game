package com.engine;

import com.entities.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.Timer;

public class ObjectManager extends Thread implements ActionListener {

	public ArrayList<Sprite> allobjects;

	public Timer BossSpawn;

	public Timer Power;

	public Timer EnemnySpawn;

	public int delay;

	public int en_delay;

	public boolean bossinGame, active;

	public int enemycount, powerups;

	public Random generator;

	public Bosses bossone;

	/**
	 * Instantiates a new object manager.
	 */
	public ObjectManager() {

		allobjects = new ArrayList<Sprite>();
		delay = 10000;
		en_delay = 2000;
		bossinGame = false;
		enemycount = 0;
		powerups = 0;
		active = false;
		bossone = new Bosses(1280, Camera3D.origin_y, 5048, 512, 512, 0, ID.Boss);

	}

	public void setActive(boolean a) {
		active = true;
	}

	public void startTimers() {

		generator = new Random();

		BossSpawn = new Timer(60000, this);
		BossSpawn.setActionCommand("boss");
		BossSpawn.start();

		Power = new Timer(delay, this);
		Power.setActionCommand("Pup");
		Power.start();

		EnemnySpawn = new Timer(en_delay, this);
		EnemnySpawn.setActionCommand("enemny");
		EnemnySpawn.start();
	}

	public void stopTimers() {
		BossSpawn.stop();
		Power.stop();
		EnemnySpawn.stop();
	}

	public void spawnBoss() {

		bossinGame = true;

		for (int i = 0; i < allobjects.size(); i++) {

			if (allobjects.get(i).getId() == ID.Player) {

				allobjects.add(bossone);

				BossSpawn.stop();

				break;

			}
		}
	}

	/**
	 * Creates the enemies.
	 */
	public void createEnemies() {

		en_delay = (generator.nextInt(1000) + 2000);
		String[] types = { "en1", "en2", "met" };

		if (!bossinGame && enemycount < 6) {

			int x = generator.nextInt(640) + 960;
			int y = generator.nextInt(320) + 160;
			double z = generator.nextInt(1024) + 512;
			int i = generator.nextInt(3);

			double w = 64;
			double h = 64;

			if (i == 1) {
				w = 128;
				h = 128;
			}

			Enemy e = new Enemy(x, y, z, w, h, 0, ID.Enemy, types[i]);

			// System.out.println("enemy created");

			allobjects.add(e);

			enemycount += 1;

		}
	}

	/**
	 * Spawn power.
	 */
	public void spawnPower() {

		delay = (generator.nextInt(10000) + 10000);
		int pow = generator.nextInt(20);

		double x = generator.nextInt(640) + 640;
		double y = generator.nextInt(320);
		double z = generator.nextInt(256) + 512;

		if (pow >= 10 && powerups <= 3) {

			PowerUps pw = new PowerUps(x, y, z, 64, 64, 0, ID.PowerUP);
			System.out.println(powerups);
			powerups += 1;

			allobjects.add(pw);
		}

	}

	public void update() {

		/// UPDATE OBJECTS IN GAME////
		// createEnemies();

		// System.out.println(allobjects.size() + " in game");

		for (int i = 0; i < allobjects.size(); i++) {

			allobjects.get(i).update();
		}

	}

	public void render(Graphics2D g2d) {
		//// RENDERS ALL OBJECTS IN GAME///
		if (active) {
			for (int i = 0; i < allobjects.size(); i++)

				allobjects.get(i).render(g2d);
		}

	}

	@Override
	public void run() {

		while (Engine.running) {

			if (active)
				update();

			try {

				Thread.sleep(8);

			} catch (Exception x) {
				x.printStackTrace();

			}
			;
		}

	}

	public void disabled() {

		try {

			this.join();

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals("Pup"))
			spawnPower();
		if (e.getActionCommand().equals("boss"))
			spawnBoss();
		if (e.getActionCommand().equals("enemny"))
			createEnemies();
	}

}
