package com.entities;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import com.engine.Camera3D;

public class Paralax {
	BufferedImage image;

	private double x;
	private double y;
	private double z;

	double w;
	double h;

	double d = 512;

	double sx, sy, sw, sh;

	double origin_x = 632;
	double origin_y = 340;

	public Paralax(String name, double x, double y, double z, double w, double h) {
		// this.image = Toolkit.getDefaultToolkit().getImage(name);

		try {
			image = ImageIO.read(getClass().getResource(name));

		} catch (IOException e) {

			e.printStackTrace();
		}

		this.x = x;
		this.y = y;
		this.z = z;

		this.w = w;
		this.h = h;
	}

//////Applies 3D NO Camera/////////
	private void cal3D() {

		sx = (int) (d * x / z);
		sy = (int) (d * y / z);

		sw = (int) (d * w / z);
		sh = (int) (d * h / z);

	};

	////// Applies 3D with Camera/////////

	private void cal3DCamera() {

		sx = (int) (d * (x - Camera3D.x) / (z - Camera3D.z));
		sy = (int) (d * (y - Camera3D.y) / (z - Camera3D.z));

		sw = (int) (d * w / (z - Camera3D.z));
		sh = (int) (d * h / (z - Camera3D.z));

	};

	public void draw2D(Graphics2D g) {
		cal3D();

		if (z - Camera3D.z > 10)

			g.drawImage(image, (int) (sx - sw / 2 + Camera3D.origin_x), (int) (sy - sh + Camera3D.origin_y), (int) sw,
					(int) sh, null);

	}

	public void draw3D(Graphics2D g) {

		cal3DCamera();

		int px = (int) (sx - sw / 2 + Camera3D.origin_x);
		int py = (int) (sy - sh + Camera3D.origin_y);

		if (z - Camera3D.z > 10) {

			for (int i = 0; i < 3; i++) {

				g.drawImage(image, (int) (w * i + px), py, (int) sw, (int) sh, null);
				// g.drawImage(image, (int) (w*i + px), (int)(py + h * i), (int)sw, (int)sh,
				// null);
				// g.drawImage(image, (int) (w*i + px), (int)(py - h * i), (int)sw, (int)sh,
				// null);

			}
			// g.drawRect( (int)Camera3D.x, (int)Camera3D.y , 100, 100);

			if (px < -sw) {

				x = px + sw;

				// g.drawImage(image, (int) (w*(i+1) + px), py, (int)sw, (int)sh, null);

				System.out.println(
						"px: " + px + " py: " + py + " sw: " + sw + " sh: " + sh + " x: " + x + " Cam " + Camera3D.x);

			}
		}

	}

	public void updateX(double d) {

		x -= d;
	}

	public void updateY(double d) {

		z += d;
	}

	public void updateZ(double d) {
		if (z < 2560)
			z += d;

	}

}
