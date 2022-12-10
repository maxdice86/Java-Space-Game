package com.entities;

import java.awt.*;

import com.engine.Camera3D;

public class Line3D {
	// ------------------------------------------------------------------------//

	double x1;
	double z1;

	double x2;
	double z2;

	double yT;
	double yB;

	double Nx;
	double Nz;

	double ux;
	double uz;

	double mag_v;

	double c;

	double d = 512;
	double origin_x = 960;
	double origin_y = 520;
	double zClip = 1;

	// ------------------------------------------------------------------------//

	public Line3D(double x1, double z1, double x2, double z2) {
		this.x1 = x1;
		this.z1 = z1;
		this.x2 = x2;
		this.z2 = z2;

		computeNormal();

		c = x1 * Nx + z1 * Nz;
	}

	// ------------------------------------------------------------------------//

	public Line3D(double x1, double z1, double x2, double z2, double yT, double yB) {
		this.x1 = x1;
		this.z1 = z1;
		this.x2 = x2;
		this.z2 = z2;
		this.yT = yT;
		this.yB = yB;

		computeNormal();

		c = x1 * Nx + z1 * Nz;
	}

	// ------------------------------------------------------------------------//

	public void computeNormal() {
		double vx = x2 - x1; // v
		double vz = z2 - z1;

		mag_v = Math.sqrt(vx * vx + vz * vz); // |v|

		ux = vx / mag_v; // v
		uz = vz / mag_v; // u = ---
							// |v|

		Nx = -uz;
		Nz = ux;
	}

	// ------------------------------------------------------------------------//

	public double distanceTo(double x, double z) {
		return Nx * (x - x1) + Nz * (z - z1);

		// return x * Nx + z * Nz - c;
	}

	// ------------------------------------------------------------------------//

	public void draw(Graphics g) {
		int x_origin = 200;
		int y_origin = 200;
		int scale = 5;

		g.drawLine((int) (x1 - Camera3D.x) / scale + x_origin, (int) (z1 - Camera3D.z) / scale + y_origin,
				(int) (x2 - Camera3D.x) / scale + x_origin, (int) (z2 - Camera3D.z) / scale + y_origin);
	}

	// ------------------------------------------------------------------------//

	public void draw3D(Graphics g) {
		double cosA = Camera3D.cosA;
		double sinA = Camera3D.sinA;

		double x1c = (x1 - Camera3D.x) * cosA + (z1 - Camera3D.z) * sinA;
		double z1c = (z1 - Camera3D.z) * cosA - (x1 - Camera3D.x) * sinA;

		double x2c = (x2 - Camera3D.x) * cosA + (z2 - Camera3D.z) * sinA;
		double z2c = (z2 - Camera3D.z) * cosA - (x2 - Camera3D.x) * sinA;

		double yTc = yT - Camera3D.y;
		double yBc = yB - Camera3D.y;

		if ((z1c >= zClip) && (z2c < zClip)) {
			double dx = x2c - x1c;
			double dz = z2c - z1c;
			double m = dx / dz;

			double zwalk = z1c - zClip;
			double xwalk = m * zwalk;

			x2c = x1c - xwalk;
			z2c = zClip;
		}
		if ((z2c >= zClip) && (z1c < zClip)) {
			double dx = x2c - x1c;
			double dz = z2c - z1c;
			double m = dx / dz;

			double zwalk = z2c - zClip;
			double xwalk = m * zwalk;

			x1c = x2c - xwalk;
			z1c = zClip;
		}
		if ((z1c >= zClip) && (z2c >= zClip)) {
			// 3D Perspective Transformation
			int xs1 = (int) (d * x1c / z1c + origin_x);
			int xs2 = (int) (d * x2c / z2c + origin_x);

			int ys1 = (int) (d * yTc / z1c + origin_y);
			int ys2 = (int) (d * yBc / z1c + origin_y);
			int ys3 = (int) (d * yBc / z2c + origin_y);
			int ys4 = (int) (d * yTc / z2c + origin_y);

			int[] xp = { xs1, xs1, xs2, xs2 };
			int[] yp = { ys1, ys2, ys3, ys4 };

			g.setColor(Color.GRAY);
			g.fillPolygon(xp, yp, 4);

// COMMENT THIS OUT TO GET RID OF THE BLACK LINES         
//*         
			g.setColor(Color.BLACK);
			g.drawPolygon(xp, yp, 4);
//*/
		}
	}

	// ------------------------------------------------------------------------//
	// ------------------------------------------------------------------------//

	int held_at = 0;

	public boolean isHeld() {
		return held_at != 0;
	}

	// ------------------------------------------------------------------------//

	public void grabbedAt(int mx, int mz) {
		double dx;
		double dz;

		dx = x1 - mx;
		dz = z1 - mz;

		if (dx * dx + dz * dz < 49)
			held_at = 1;

		dx = x2 - mx;
		dz = z2 - mz;

		if (dx * dx + dz * dz < 49)
			held_at = 2;
	}

	// ------------------------------------------------------------------------//

	public void draggedBy(int dx, int dz) {
		if (held_at == 1) {
			x1 += dx;
			z1 += dz;
		}

		if (held_at == 2) {
			x2 += dx;
			z2 += dz;
		}

		computeNormal();
	}

	// ------------------------------------------------------------------------//

	public void released() {
		held_at = 0;
	}

	// ------------------------------------------------------------------------//

}