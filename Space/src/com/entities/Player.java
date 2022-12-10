package com.entities;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import com.engine.Camera3D;
import com.engine.Engine;
import com.engine.InputManager;

/**
 * The Class Player.
 */
public class Player extends Sprite implements ActionListener {

	private int firerate = 25;

	private int movespeed = 4;

	private int guns = 1;

	private Timer respawn;

	private long delay;

	private boolean mov_up, mov_dn, damaged, firing, auto, dead;

	private Effects exh, flash, dmg;

	public double vx, vy;

	public Player(double x, double y, double z, double w, double h, int a, ID id) {

		super(x, y, z, w, h, a, id);

		exh = new Effects(x, y, z, w, h, a, ID.FX, "ex");

		flash = new Effects(x, y, z, 32, 32, a, ID.FX, "fl");

		dmg = new Effects(x, y, z, w, h, a, ID.FX, "dg");

		this.dead = false;

		Engine.gui.playerHealth = 200;
		Engine.gui.playerPower = 50;

		loadAnimation("moving", "/Player/player", 3, 20);

		// loadAnimation("moving", "/Effects/p_Sprite", 8, 3);

		loadAnimation("died", "/Player/hit2", 4, 5);
		loadAnimation("damaged", "/Player/hit", 8, 10);
		this.delay = 320;
		respawn = new Timer(3000, this);
		Engine.audiomg.sfx.get("shot").setVolume(1f);
		Engine.audiomg.sfx.get("powerup").setVolume(2f);
		Engine.audiomg.sfx.get("playerhit").setVolume(1f);

		// auto = true;

		// Engine.gui.removeBounds();

	}

	public void setHp(int hp) {
		Engine.gui.setPlayerHealth(hp);

		if (Engine.gui.playerHealth <= 0) {
			alive = false;
			enabled = false;

			Engine.gui.setPlayerLives(-1);
		}

	}

	public void setDead(boolean d) {

		this.dead = d;
	}

	public boolean isDead() {

		return this.dead;
	}

	public void setPower(int p) {
		Engine.gui.setPlayerPower(p);

		if (Engine.gui.playerPower >= 100) {
			Engine.gui.playerPower = 100;

		}

	}

	public void fire() {

		firerate--;

		if (firerate == 0) {

			switch (guns) {

			case 1:

				Engine.obj_manager.allobjects.add(new Bullets(px + 32, py - 12, 512, 24, 8, 0, 10 * Lookup.cos[A],
						10 * Lookup.sin[A], ID.Bullet, "pl"));

				firerate = 25;
				firing = true;

				break;

			case 2:

				Engine.obj_manager.allobjects.add(new Bullets(px + 32, py - 12, 512, 24, 8, 0, 10 * Lookup.cos[A],
						10 * Lookup.sin[A], ID.Bullet, "pl"));

				Engine.obj_manager.allobjects.add(new Bullets(px + 32, py - 12, 512, 32, 8, 0, 10 * Lookup.cos[180],
						10 * Lookup.sin[180], ID.Bullet, "pw"));

				firerate = 15;
				firing = true;

				break;

			case 3:

				Engine.obj_manager.allobjects.add(new Bullets(px + 32, py - 12, 512, 24, 8, 0, 10 * Lookup.cos[A],
						10 * Lookup.sin[A], ID.Bullet, "pl"));

				Engine.obj_manager.allobjects.add(new Bullets(px + 32, py - 12, 512, 32, 8, 0, 10 * Lookup.cos[180],
						10 * Lookup.sin[180], ID.Bullet, "pw"));

				Engine.obj_manager.allobjects.add(new Bullets(px + 32, py - 12, 512, 32, 8, 0, 10 * Lookup.cos[345],
						10 * Lookup.sin[345], ID.Bullet, "pw"));

				Engine.obj_manager.allobjects.add(new Bullets(px + 32, py - 12, 512, 32, 8, 0, 10 * Lookup.cos[25],
						10 * Lookup.sin[25], ID.Bullet, "pw"));

				firerate = 15;
				firing = true;
			}

			Engine.audiomg.sfx.get("shot").play();

		} // firing = false;
	}

	/*
	 * /** Check movements.
	 */
	public void checkMovements() {

		// MOVEMENT/

		mov_dn = false;
		mov_up = false;

		if (enabled) {

			if (InputManager.dn_pressed) {

				moveship(0, movespeed);

				collisionMap.moveBy(0, movespeed);

				mov_dn = true;
			}

			if (InputManager.up_pressed) {

				moveship(0, -movespeed);

				collisionMap.moveBy(0, -movespeed);

				mov_up = true;

			}

			if (InputManager.rt_pressed) {

				moveship(movespeed, 0);

				collisionMap.moveBy(movespeed, 0);
			}

			if (InputManager.lt_pressed) {
				moveship(-movespeed, 0);

				collisionMap.moveBy(-movespeed, 0);
			}

			if (InputManager.sp_pressed)
				fire();
		}
	}

	/**
	 * Check in bounds.
	 */
	public void checkInBounds() {
		// CHECK INGAME BOUNDS

		if (enabled) {
			// CHECK TOP//
			if (overlaps(Engine.gui.bottombounds)) {

				isPushedBackBy(Engine.gui.bottombounds);

				collisionMap.isPushedBackBy(Engine.gui.bottombounds);

				Camera3D.moveBy(0 * -0.25, movespeed * -0.25, 0);

				Camera3D.moveBy(0, 0, 0);

			}

			// CHECK BOTTOM//

			if (overlaps(Engine.gui.topbounds)) {

				isPushedBackBy(Engine.gui.topbounds);

				collisionMap.isPushedBackBy(Engine.gui.topbounds);

				Camera3D.moveBy(0 * -0.25, -movespeed * -0.25, 0);

				Camera3D.moveBy(0, 0, 0);
			}

			// CHECK RIGHT//

			if (overlaps(Engine.gui.rightbounds)) {

				isPushedBackBy(Engine.gui.rightbounds);

				collisionMap.isPushedBackBy(Engine.gui.rightbounds);

				Camera3D.moveBy(movespeed * -0.25, 0 * -0.25, 0);

				Camera3D.moveBy(0, 0, 0);
			}

			// CHECK LEFT//
			if (overlaps(Engine.gui.leftbounds)) {

				isPushedBackBy(Engine.gui.leftbounds);

				collisionMap.isPushedBackBy(Engine.gui.leftbounds);

				Camera3D.moveBy(-movespeed * -0.25, 0 * -0.25, 0);

				Camera3D.moveBy(0, 0, 0);
			}

		}

	}

	/**
	 * Update.
	 */
	@Override
	public void update() {

		if (auto)
			autoMove(2, 0, -200, 0);

		if (enabled) {
			collisionMap.updatePostions(px, py, pz, collisionMap.r, A);

			exh.updatePostions(px - 32, py, pz, sw / 2, A);

			flash.updatePostions(px + 42, py - 15, pz, 16, A);

			dmg.updatePostions(px, py, pz, sw / 2, A);

			checkMovements();
			checkCollison();
			checkInBounds();
			upgrade();
		}
	}

	public synchronized void checkCollison() {

		/// Check Collisions with Enemies////////////////
		for (int i = 0; i < Engine.obj_manager.allobjects.size(); i++) {

			boolean isHit = collisionMap.overlaps(Engine.obj_manager.allobjects.get(i).collisionMap);

			// System.out.println("collision hit is " + isHit);

			Sprite obj = Engine.obj_manager.allobjects.get(i);

			boolean Hit = overlaps(obj);

			// System.out.println("overlap hit is " + Hit);

			if (Hit) {
				switch (obj.id) {

				case Enemy:

					pushes(obj);
					collisionMap.pushes(obj);
					setHp(-1);
					damaged = true;

					break;

				case EBullet:

					Engine.obj_manager.allobjects.remove(obj);
					damaged = true;
					Engine.audiomg.sfx.get("playerhit").play();
					setHp(-10);
					System.out.println("getting hit");

					break;

				case PowerUP:

					Engine.obj_manager.allobjects.remove(obj);
					Engine.obj_manager.powerups -= 1;
					Engine.audiomg.sfx.get("powerup").play();
					setPower(10);
					break;

				case Boss:

					if (obj.pz < 700 && obj.pz >= 512) {
						pushes(obj);
						setHp(-1);
						damaged = true;
					}
					break;

				default:
					break;
				}
			}
		}

	}

	public void autoMove(double dx, double dy, double x, double y)

	{
		enabled = false;

		// System.out.println(px);

		if (px != x) {
			moveship(dx, dy);

		} else {

			enabled = true;
			auto = false;
			// Engine.gui.setBounds();
		}
	}

	private void moveship(double dx, double dy) {

		moveBy(dx, dy);

		Camera3D.moveBy(dx * 0.25, dy * 0.25, 0);
	}

	/**
	 * Upgrade.
	 */
	public void upgrade() {

		switch (Engine.gui.playerPower) {
		case 30:
			guns = 2;
			break;
		case 60:
			guns = 2;
			movespeed = 6;
			break;
		case 80:
			guns = 3;
			movespeed = 6;
			break;
		}
	}

	private void createPlayer() {

		respawn.stop();

		enabled = false;

		Engine.obj_manager.allobjects.add(new Player(-640, 0, 512, w, h, A, ID.Player));

		Engine.obj_manager.allobjects.remove(this);

	}

	@Override
	public void render(Graphics2D g) {

		cal3D();

		g.setColor(Color.blue);

		// collisionMap.draw3D(g);

		if (firing) {
			firing = false;
			flash.render(g);
		}

		if (alive && mov_dn || alive && mov_up) {
			exh.render(g);
			g.drawImage(animator.get("moving").getCurrentImage(), x, y, sw, sh, null);
		}

		else if (alive) {
			exh.render(g);
			g.drawImage(animator.get("moving").getStillImage(), x, y, sw, sh, null);
		}

		else if (!alive && !animator.get("died").hasPlayed()) {

			g.drawImage(animator.get("died").getCurrentImage(), x, y, sw, sh, null);

			respawn.start();
			enabled = false;
			respawn.setRepeats(false);
			g.setColor(Color.white);

		}

		if (damaged) {
			damaged = false;
			dmg.render(g);
		}
		// g.setColor(Color.blue);
		// g.setTransform(currentTrans);

		if (!alive && Engine.gui.playerLives > 0) {

			/////// COUNTDOWN TIMER ON NEW THREAD//////////
			new Thread(new Runnable() {
				@Override
				public void run() {
					while (delay > 0) {
						delay--;
						try {
							Thread.sleep(1000);
						} catch (Exception e) {
							// TODO: handle exception
						}
					}
				}
			}).start();
			/////////////////////////////////////////////
			Font old = g.getFont();

			Font myFont = new Font("Impact New", 1, 36);

			g.setFont(myFont);
			g.drawString("REPAWN IN:\n" + delay / 100, (int) (Camera3D.origin_x) - 128, (int) Camera3D.origin_y);
			g.setFont(old);

		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		createPlayer();
	}

}
