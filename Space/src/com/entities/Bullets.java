package com.entities;

import java.awt.Graphics2D;
import com.engine.Engine;

public class Bullets extends Sprite {

	private double spx, spy;

	private String type;

	public Bullets(double x, double y, double z, double w, double h, int angle, double vx, double vy, ID id, String t) {

		super(x, y, z, w, h, angle, id);

		this.spx = vx;
		this.spy = vy;

		this.type = t;

		switch (type) {
		case "en1":
			loadAnimation("shot1", "/Enemies/Eshot", 1, 1);
			break;
		case "en2":
			loadAnimation("shot2", "/Enemies/bulletsG", 3, 2);
			break;
		case "en3":
			break;
		case "pl":
			loadAnimation("fired", "/Player/shoot", 2, 4);
			break;
		case "pw":
			loadAnimation("pw", "/Player/b2", 10, 10);
			break;
		case "bos":
			loadAnimation("boss", "/Enemies/bossShot", 2, 1);
			break;
		}

	}

	/**
	 * Removes the.
	 */
	public void remove() {

		if (x < -1980 || x > 1980) {

			Engine.obj_manager.allobjects.remove(this);
		}

		if (y > 1980 || y < -1980) {

			Engine.obj_manager.allobjects.remove(this);
		}

	}

	public void zClip() {
		pz -= 1;

		if (pz <= 512)
			pz = 512;
	}

	@Override
	public void update() {

		if (enabled) {
			// double test = Camera3D.x + 2560;
			// System.out.println("removed: " + x + " @ " + test);
			// x += vx ;
			// y += vy ;

			moveBy(spx, spy);

			// zClip();

		} else {
			px += 0;
			py += 0;
			pz += 0;

		}

		remove();

	}

	/**
	 * Render.
	 *
	 * @param g the g
	 */
	@Override
	public void render(Graphics2D g) {
		// super.render(g);
//		
		cal3D();
//		
		currentTrans = g.getTransform();
//    	
//       AffineTransform rotate = AffineTransform.getRotateInstance(Math.toRadians(A), x + sw/2, y + sh/2);
//         g.setTransform(rotate);
//		
//	   collisionMap.draw3D(g);

		switch (id) {

		case Bullet:

			if (type == "pw") {
				g.drawImage(animator.get("pw").getCurrentImage(), x, y, sw, sh, null);
			}

			else
				g.drawImage(animator.get("fired").getCurrentImage(), x, y, sw, sh, null);

			break;

		case EBullet:

			if (type == "en1") {
				g.drawImage(animator.get("shot1").getCurrentImage(), x, y, sw, sh, null);
			} else if (type == "bos") {
				g.drawImage(animator.get("boss").getCurrentImage(), x, y, sw, sh, null);
			} else {

				g.drawImage(animator.get("shot2").getCurrentImage(), x, y, sw, sh, null);

			}

			break;
		default:
			break;
		}

		g.setTransform(currentTrans);
	}

}
