package com.entities;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import com.engine.Animation;
import com.engine.Camera3D;

public class Icons extends ImageIcon {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	double x, y, z, w, h;
	BufferedImage icon;
	Animation animation;
	public boolean selected = false;
	double d = 512;
	int sx, sy, sw, sh;
	public Rectangle bounds;

	public Icons(String file, double x, double y, double z, double w, double h) {

		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.z = z;

		try {
			icon = ImageIO.read(getClass().getResource(file));

		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	public Icons(String file, double x, double y, double z, double w, double h, int anim) {

		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.z = z;

		animation = new Animation(file, anim, 3);
	}

	public void updatePos(double x, double y, double z, double w, double h) {

	}

	////// Applies 3D NO Camera/////////
	private void addDepth() {

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

	/// Draws Icons in 3D space with relation to camera/////

	public void draw3D(Graphics2D g, boolean s) {

		cal3DCamera();

		if (!s)
			icon = (BufferedImage) animation.getStillImage();
		else
			icon = (BufferedImage) animation.getCurrentImage();

		Image iicon = icon.getScaledInstance(sw, sh, java.awt.Image.SCALE_SMOOTH);

		this.bounds = new Rectangle((int) (sx - sw / 2 + Camera3D.origin_x), (int) (sy - sh + Camera3D.origin_y), sw,
				sh);

		g.drawImage(iicon, (int) (sx - sw / 2 + Camera3D.origin_x), (int) (sy - sh + Camera3D.origin_y), sw, sh, null);

	}
	////////////////////////////////////////////

	/// Draws Icons on 2D space no Camera relation///////////

	public void draw2D(Graphics2D g) {

		addDepth();

		Image iicon = icon.getScaledInstance(sw, sh, java.awt.Image.SCALE_SMOOTH);

		g.drawImage(iicon, (int) (sx - sw / 2 + Camera3D.origin_x), (int) (sy - sh + Camera3D.origin_y), sw, sh, null);

		this.bounds = new Rectangle((int) (sx - sw / 2 + Camera3D.origin_x), (int) (sy - sh + Camera3D.origin_y), sw,
				sh);

	}

}
