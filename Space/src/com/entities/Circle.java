package com.entities;

import java.awt.*;
import com.engine.Camera3D;

public class Circle {
	// ------------------------------------------------------------------------//

	double px;
	double py;
	double pz;

	double w, h;

	double vx = 0;
	double vy = 0;
	double vz = 0;

	double ax = 0;
	double ay = 0;

	double dis;

	protected int sx, sy, sw, sh, per_x, per_y;

	protected int x, y;

	double r;

	public int A;

	double cosA;
	double sinA;

	double launch_delay = 0;
	double launch_countdown = 20;

	static int bulletNum = 0;

	static final double GRAVITY = 0.4;

	boolean held = false;

	// ------------------------------------------------------------------------//

	public Circle(double px, double py, double pz, double pw, double ph, int A) {
		this.px = px;
		this.py = py;
		this.pz = pz;

		this.r = pw / 2;

		this.w = pw;
		this.h = ph;

		this.A = A;

		cosA = Lookup.cos[A];
		sinA = Lookup.sin[A];

	}

	// ------------------------------------------------------------------------//

	public Circle(double x, double y, int r, int A) {
		this.px = x;
		this.py = y;

		this.w = r * 2;
		this.h = r * 2;
		this.r = r;

		this.A = A;

	}

	public void updatePostions(double px, double py, double pz, double r, int A) {

		this.px = px;
		this.py = py;
		this.pz = pz;

		this.r = r;

		this.w = r * 2;
		this.h = r * 2;

		this.A = A;

		cosA = Lookup.cos[A];
		sinA = Lookup.sin[A];
	}

	public void launch(Circle[] bullet) {
		if (launch_delay == 0) {
			double speed = 20;

			bullet[bulletNum].px = px + (r + 6) * cosA;
			bullet[bulletNum].py = py + (r + 6) * sinA;

			bullet[bulletNum].vx = speed * cosA;
			bullet[bulletNum].vy = speed * sinA;

			bulletNum++;

			if (bulletNum == bullet.length)
				bulletNum = 0;

			launch_delay = launch_countdown;
		}

		launch_delay--;
	}

	public double getDis(Circle c) {
		return -sinA * (c.px - px) + cosA * (c.py - py);
	}

	// ------------------------------------------------------------------------//

	public boolean toLeftOf(Circle c) {

		// sinA * (c.px - px) - cosA * (c.py - py) > 0;

		return -sinA * (c.px - px) + cosA * (c.py - py) > 0;
	}

	// ------------------------------------------------------------------------//

	public boolean inFrontOf(Circle c) {
		return cosA * (c.px - px) + sinA * (c.py - py) > 0;
	}

	// ------------------------------------------------------------------------//

	public boolean within(double distance, Circle c) {
		double dx = px - c.px;
		double dy = py - c.py;

		return dx * dx + dy * dy < distance * distance;
	}

	// ------------------------------------------------------------------------//

	public void draw3D(Graphics2D g) {
		cal3D();

		g.drawOval(x, y, sw, sw);

		g.drawLine((int) (sx + Camera3D.origin_x), (int) (sy + Camera3D.origin_y),
				(int) ((sx + Camera3D.origin_x) + (sw / 2 * cosA)), (int) ((sy + Camera3D.origin_y) + (sw / 2 * sinA)));

		// g.drawImage(test, (int)(x), (int)(y), sw, sw, null);

	}

	public void draw2D(Graphics2D g) {
		g.drawOval((int) (px - r), (int) (py - r), (int) (2.0 * r), (int) (2.0 * r));

		g.drawLine((int) px, (int) py, (int) (px + r * cosA), (int) (py + r * sinA));
	}

	public void cal3D() {
		int d = 512;

		sx = (int) (d * (px - Camera3D.x) / (pz - Camera3D.z));
		sy = (int) (d * (py - Camera3D.y) / (pz - Camera3D.z));

		sw = (int) (d * w / (pz - Camera3D.z));
		sh = (int) (d * h / (pz - Camera3D.z));

		x = (int) (sx - sw / 2 + Camera3D.origin_x);
		y = (int) (sy - sh / 2 + Camera3D.origin_y);
	}

	// ------------------------------------------------------------------------//

	public void move() {
		vx += ax; // Accelerate
		vy += ay;

		px += vx; // Move
		py += vy;
	}

	// ------------------------------------------------------------------------//

	public void setVelocity(double vx, double vy) {
		this.vx = vx;
		this.vy = vy;
	}

	// ------------------------------------------------------------------------//

	public void setAcceleration(double ax, double ay) {
		this.ax = ax;
		this.ay = ay;
	}

	// ------------------------------------------------------------------------//

	public void jump(double d) {
		setVelocity(0, -d);
	}

	// ------------------------------------------------------------------------//

	public void toss(double vx, double vy) {
		setVelocity(vx, vy);
	}
	// ------------------------------------------------------------------------//

	public void moveBy(double dx, double dy) {
		vx += dx; // Accelerate
		vy += dy;

		px += dx;
		py += dy;
	}

	// ------------------------------------------------------------------------//

	public void goForward(double d) {
		double dx = d * cosA;
		double dy = d * sinA;

		moveBy(dx, dy);
	}

	// ------------------------------------------------------------------------//

	public void goBackward(double d) {
		double dx = -d * cosA;
		double dy = -d * sinA;

		moveBy(dx, dy);
	}

	// ------------------------------------------------------------------------//

	public void setAngle(int angle) {
		A = angle;

		if (A < 0)
			A += 360;
		if (A > 359)
			A -= 360;

		cosA = Lookup.cos[A];
		sinA = Lookup.sin[A];

	}
	// ------------------------------------------------------------------------//

	public void turnLeft(int dA) {
		A -= dA;

		if (A < 0)
			A += 360;

		cosA = Lookup.cos[A];
		sinA = Lookup.sin[A];

//		A -= dA;
//				
//		if(A < 0)   A += 360;
	}

	// ------------------------------------------------------------------------//

	public void turnRight(int dA) {
		A += dA;

		if (A > 359)
			A -= 360;

		cosA = Lookup.cos[A];
		sinA = Lookup.sin[A];
	}

	// ------------------------------------------------------------------------//

	public boolean overlaps(Line L) {
		double d = L.distanceTo(px, py);

		return d < r;
	}

	// ------------------------------------------------------------------------//

	public void isPushedBackBy(Line L) {
		double d = L.distanceTo(px, py);

		double p = r - d;

		vx = p * L.Nx;
		vy = p * L.Ny;

		moveBy(p * L.Nx, p * L.Ny);

	}

	// ------------------------------------------------------------------------//

	public boolean overlaps(Circle c) {
		if (c.equals(this))
			return false;

		double dx = px - c.px;
		double dy = py - c.py;

		double d2 = dx * dx + dy * dy;

		double ri = r + c.r;

		return d2 <= ri * ri;
	}

	// ------------------------------------------------------------------------//

	public void pushes(Circle c) {
		double dx = px - c.px; // <dx, dy> // vector in the direction from center of one circle to the other
		double dy = py - c.py; // with a magnitude equal to the distance between the two circles

		double d = Math.sqrt(dx * dx + dy * dy);

		double ux = dx / d; // <dx/d, dy/d> // unit vector in the direction from center of one circle to the
							// other
		double uy = dy / d;

		double pen = ((r + c.r) - d) / 2; // Half of the Penetration Distance

		double penx = ux * pen;
		double peny = uy * pen;

		px += penx;
		py += peny;

		c.px -= penx;
		c.py -= peny;

		System.out.println("pushing");

	}

	// ------------------------------------------------------------------------//

	public void bounceOff2(Circle c) // 11+, 22*, 2/, 1sqrt
	{
		double dx = c.px - px;
		double dy = c.py - py;

		double mag = Math.sqrt(dx * dx + dy * dy);

		double ux = dx / mag;
		double uy = dy / mag;

		double tx = -uy;
		double ty = ux;

		double u = vx * ux + vy * uy;
		double t = vx * tx + vy * ty;

		double cu = c.vx * ux + c.vy * uy;
		double ct = c.vx * tx + c.vy * ty;

		vx = .9 * (t * tx + cu * ux);
		vy = .9 * (t * ty + cu * uy);

		c.vx = .9 * (ct * tx + u * ux);
		c.vy = .9 * (ct * ty + u * uy);
	}

	// ------------------------------------------------------------------------//

	public void bounceOff(Circle c) // 13+, 12*, 2/
	{
		double mag = r + c.r; // Magnitude in Direction of Impact, double mag = Math.sqrt(dx*dx + dy*dy);

		double ix = (c.px - px) / mag; // Unit Vector in
		double iy = (c.py - py) / mag; // Direction of Impact

		double iv = vx * ix + vy * iy; // Impact Velocity Scalar

		double ivx = iv * ix; // Impact Velocity x component
		double ivy = iv * iy; // Impact Velocity x component

		double tvx = vx - ivx; // Tangent Velocity x component
		double tvy = vy - ivy; // Tangent Velocity y component

		double civ = c.vx * ix + c.vy * iy; // Impact Velocity Scalar

		double civx = civ * ix; // Impact Velocity x component
		double civy = civ * iy; // Impact Velocity x component

		double ctvx = c.vx - civx; // Tangent Velocity x component
		double ctvy = c.vy - civy; // Tangent Velocity y component

		vx = .9 * (tvx + civx);
		vy = .9 * (tvy + civy);

		c.vx = .9 * (ctvx + ivx);
		c.vy = .9 * (ctvy + ivy);
	}

	// ------------------------------------------------------------------------//

	public void bounceOffLine(Line L) {
		double d = L.distanceTo(px, py);

		double p = r - d;

		px += 1.9 * (p * L.Nx);
		py += 1.9 * (p * L.Ny);

		double mag = 1.9 * (vx * L.Nx + vy * L.Ny);

		double tx = mag * L.Nx;
		double ty = mag * L.Ny;

		vx -= tx;
		vy -= ty;
	}

	// ------------------------------------------------------------------------//

	public void grabbedAt(int x, int y) {
		double dx = px - x;
		double dy = py - y;

		double d2 = dx * dx + dy * dy;

		double r2 = r * r;

		held = d2 < r2;
	}

	// ------------------------------------------------------------------------//
	public void turnToward(Circle c) {
		if (toLeftOf(c))
			turnLeft(2);

		else
			turnRight(2);
	}

	public void chase(Circle c, double d) {
		turnToward(c);

		goForward(d);
	}
	// ------------------------------------------------------------------------//

}
