package com.entities;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import com.engine.Engine;

// TODO: Auto-generated Javadoc
/**
 * The Class Enemy.
 */
public class Enemy extends Sprite {

	private double hp;

	private int firerate;

	private boolean dmg;

	private String type;

	public Enemy(double x, double y, double z, double w, double h, int angle, ID id, String t)

	{
		super(x, y, z, w, h, angle, id);

		this.type = t;

		switch (type) {
		case "en1":
			hp = 65;
			firerate = 80;
			loadAnimation("moving", "/Enemies/enemy1", 5, 5);
			loadAnimation("destroyed", "/Player/explosion1", 5, 3);
			break;
		case "en2":
			hp = 100;
			firerate = 80;
			loadAnimation("2moving", "/Enemies/Ship1", 1, 1);
			loadAnimation("2destroyed", "/Enemies/Ship1_Explosion", 7, 3);
			break;
		case "en3":
			hp = 100;
			firerate = 6;
			break;

		case "met":
			hp = 10;
			firerate = 0;
			loadAnimation("met", "/Enemies/Stones", 29, 3);
			loadAnimation("metdes", "/Player/hit2", 4, 3);
			break;
		case "bom":
			hp = 10;
			firerate = 0;
			break;

		default:
			break;
		}

		Engine.audiomg.sfx.get("hit").setVolume(0.9f);
		Engine.audiomg.sfx.get("enemyshot").setVolume(0.08f);

	}

	public void setHp(double h) {
		hp += h;

		if (hp <= 0) {
			alive = false;
			Engine.gui.setScore(100);
		}
	}

	public void fire() {
		firerate--;

		if (firerate == 0 && type != "met") {
			Bullets b = new Bullets(px, py, 512, 16, 16, A,

					4 * -cosA, 4 * -sinA, ID.EBullet, type);

			Engine.audiomg.sfx.get("enemyshot").play();
			firerate = 100;
			Engine.obj_manager.allobjects.add(b);

		}
	}

	public synchronized void checkCollision() {
		for (int i = 0; i < Engine.obj_manager.allobjects.size(); i++) {
			Sprite obj = Engine.obj_manager.allobjects.get(i);

			boolean Hit = overlaps(obj);

			switch (obj.id) {
			case Player:

				if (obj.enabled) {
					turnToward(obj);

					if (type != "met") {
						if (getDis(obj) < -5 || getDis(obj) > 5)

							goBackward(3);

					} else
						moveBy(-3, 0);

				} else
					goForward(2);

				break;

			case Bullet:

				if (Hit) {
					Engine.obj_manager.allobjects.remove(obj);
					dmg = true;
					Engine.audiomg.sfx.get("hit").play();
					setHp(-10);
				}

				break;

			case Enemy:

				if (Hit) {
					pushes(obj);

					goForward(5);
				}

				break;

			default:
				break;
			}

		}

	}

	public void zClip() {
		pz -= 5;

		if (pz <= 512)
			pz = 512;
	}

	@Override
	public void update() {

		collisionMap.updatePostions(px, py, pz, collisionMap.r, A);

		if (alive && enabled) {
			zClip();

			checkCollision();

			fire();

			if (type == "met" && x < -1980)

				Engine.obj_manager.allobjects.remove(this);

		}

	}

	public void checkBounds() {
	}

	@Override
	public void render(Graphics2D g) {

		// super.render(g);

		cal3D();

		// collisionMap.draw3D(g);

		currentTrans = g.getTransform();

		AffineTransform rotate = AffineTransform.getRotateInstance(Math.toRadians(A), x + sw / 2, y + sh / 2);
		g.setTransform(rotate);

		if (alive) {

			switch (type) {
			case "en1":
				g.drawImage(animator.get("moving").getCurrentImage(), x, y, sw, sh, null);
				if (!enabled)
					g.drawImage(animator.get("moving").getStillImage(), x, y, sw, sh, null);
				break;
			case "en2":
				g.drawImage(animator.get("2moving").getCurrentImage(), x, y, sw, sh, null);
				if (!enabled)
					g.drawImage(animator.get("2moving").getStillImage(), x, y, sw, sh, null);
				break;
			case "met":
				g.drawImage(animator.get("met").getCurrentImage(), x, y, sw, sh, null);
				if (!enabled)
					g.drawImage(animator.get("met").getStillImage(), x, y, sw, sh, null);
				break;
			default:
				break;
			}

		}

		else
			switch (type) {
			case "en1":
				if (!animator.get("destroyed").hasPlayed()) {
					g.drawImage(animator.get("destroyed").getCurrentImage(), x, y, sw, sh, null);

				} else {

					Engine.obj_manager.allobjects.remove(this);
					Engine.obj_manager.enemycount -= 1;

				}
				break;
			case "en2":
				if (!animator.get("2destroyed").hasPlayed()) {
					g.drawImage(animator.get("2destroyed").getCurrentImage(), x, y, sw, sh, null);

				} else {

					Engine.obj_manager.allobjects.remove(this);
					Engine.obj_manager.enemycount -= 1;

				}
				break;
			case "met":
				if (!animator.get("metdes").hasPlayed()) {
					g.drawImage(animator.get("metdes").getCurrentImage(), x, y, sw, sh, null);

				} else {

					Engine.obj_manager.allobjects.remove(this);
					Engine.obj_manager.enemycount -= 1;

				}
				break;
			default:
				break;
			}

		g.setTransform(currentTrans);

	}
}
