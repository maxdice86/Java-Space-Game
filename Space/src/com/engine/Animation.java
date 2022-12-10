package com.engine;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

// TODO: Auto-generated Javadoc
/**
 * The Class Animation.
 */
public class Animation {

	/** The image. */
	private BufferedImage[] image;

	/** The current. */
	private int current = 0;

	/** The duration. */
	private int duration;

	/** The delay. */
	private int delay;

	/** The played. */
	private boolean played = false;

	/**
	 * Instantiates a new animation.
	 *
	 * @param name     the name
	 * @param count    the count
	 * @param duration the duration
	 */
	public Animation(String name, int count, int duration) {
		image = new BufferedImage[count];

		for (int i = 0; i < count; i++) {

			// ImageIcon icon = new ImageIcon(getClass().getResource(name +"_" + i +
			// ".png"));
			// image[i] = icon.getImage();

			try {
				image[i] = ImageIO.read(getClass().getResource(name + "_" + i + ".png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		this.duration = duration;

		delay = duration;
	}

	/**
	 * Gets the current image.
	 *
	 * @return the current image
	 */
	public Image getCurrentImage() {
		if (delay == 0) {
			current++;

			if (current == image.length) {

				current = 0;
				played = true;
			}
			delay = duration;
		}

		delay--;

		return image[current];
	}

	/**
	 * Gets the still image.
	 *
	 * @return the still image
	 */
	public Image getStillImage() {
		return image[0];
	}

	/**
	 * Checks for played.
	 *
	 * @return true, if successful
	 */
	public boolean hasPlayed() {
		return played;
	}

}