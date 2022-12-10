package com.entities;

import java.awt.Graphics2D;

public class Effects extends Sprite {

	private String type;

	public Effects(double x, double y, double z, double w, double h, int angle, ID id, String t) {
		super(x, y, z, w, h, angle, id);

		this.type = t;

		loadAnimation("ex", "/Player/exhaust1", 4, 2);
		loadAnimation("fl", "/Player/flash", 1, 1);
		loadAnimation("dg", "/Player/hit2", 4, 2);
		loadAnimation("dfx", "/Effects/expl_09", 16, 1);

	}

	@Override
	public void update() {
	}

	@Override
	public void render(Graphics2D g) {

		cal3D();

		switch (type) {
		case "ex":
			g.drawImage(animator.get("ex").getCurrentImage(), x, y, sw, sh, null);
			break;
		case "fl":
			g.drawImage(animator.get("fl").getCurrentImage(), x, y, sw, sh, null);
			break;
		case "dg":
			g.drawImage(animator.get("dg").getCurrentImage(), x, y, sw, sh, null);
			break;
		case "dfx":
			g.drawImage(animator.get("dfx").getCurrentImage(), x, y, sw, sh, null);
			break;

		}

	}
}
