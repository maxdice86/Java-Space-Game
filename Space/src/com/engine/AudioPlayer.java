package com.engine;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class AudioPlayer {

	private Clip clip;

	public AudioPlayer(String filename) {

		try {

			AudioInputStream fileclip = AudioSystem.getAudioInputStream(getClass().getResource(filename));
			AudioFormat baseFormat = fileclip.getFormat();
			AudioFormat decodeFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(), 16,
					baseFormat.getChannels(), baseFormat.getChannels() * 2, baseFormat.getSampleRate(), false);
			AudioInputStream decodedclip = AudioSystem.getAudioInputStream(decodeFormat, fileclip);
			clip = AudioSystem.getClip();
			clip.open(decodedclip);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void setVolume(float v) {

		FloatControl vol = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		// Converts input value to range values;
		float dB = (float) (Math.log(v) / Math.log(10.0) * 20.0);

		vol.setValue(dB);
	}

	public void mute(boolean m) {

		BooleanControl muteControl = (BooleanControl) clip.getControl(BooleanControl.Type.MUTE);

		if (m)
			muteControl.setValue(true);

		else
			muteControl.setValue(false);
	}

	public void play() {

		if (clip == null)
			return;
		stop();
		clip.setFramePosition(0);
		clip.start();
	}

	public void stop() {

		if (clip.isRunning())
			clip.stop();
	}

	public void close() {

		stop();
		clip.close();
	}

	public void setLoop() {
		clip.setLoopPoints(0, -1);
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}

}