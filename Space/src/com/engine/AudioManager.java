package com.engine;

import java.util.Hashtable;

public class AudioManager {

	public Hashtable<String, AudioPlayer> sfx = new Hashtable<String, AudioPlayer>();

	public AudioManager() {

		loadSfx("shot", "/Audio/photon.ogg");
		loadSfx("hit", "/Audio/explosion.ogg");

		loadSfx("bgm1", "/Audio/Brightness-soft-techno.ogg");
		loadSfx("bgm2", "/Audio/Space_BGM3.ogg");

		loadSfx("playerhit", "/Audio/hit.ogg");
		loadSfx("enemyshot", "/Audio/laserray.ogg");

		loadSfx("bossshot", "/Audio/laser2.ogg");
		loadSfx("lose", "/Audio/losemusic.ogg");

		loadSfx("select", "/Audio/select2.ogg");
		loadSfx("powerup", "/Audio/Powerup6.ogg");

		loadSfx("boss", "/Audio/Space_BGM1.ogg");
		loadSfx("bossshot", "/Audio/laser2.ogg");

		loadSfx("pause", "/Audio/pause.ogg");
		loadSfx("start", "/Audio/loading.ogg");

		loadSfx("intro", "/Audio/intro.ogg");
		loadSfx("play", "/Audio/start-level.ogg");

		loadSfx("incoming", "/Audio/Arpeggiated-bassline.ogg");

	}

	public void loadSfx(String name, String filename) {

		sfx.put(name, new AudioPlayer(filename));
	}
}
