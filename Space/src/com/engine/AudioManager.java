package com.engine;
import java.util.Hashtable;

public class AudioManager {

	  public Hashtable<String, AudioPlayer> sfx = new Hashtable<String, AudioPlayer>();

	  public AudioManager() {
		  
		  loadSfx("shot", "/Audio/photon.wav");
		  loadSfx("hit", "/Audio/explosion.wav");
		  
		  loadSfx("bgm1","/Audio/Brightness-soft-techno.wav");
		  loadSfx("bgm2", "/Audio/Space_BGM3.wav");

		  loadSfx("playerhit","/Audio/hit.wav");
		  loadSfx("enemyshot","/Audio/laserray.wav");

		  loadSfx("bossshot","/Audio/laser2.wav");
		  loadSfx("lose","/Audio/losemusic.wav");

		  loadSfx("select","/Audio/select2.wav");
		  loadSfx("powerup", "/Audio/Powerup6.wav");
		  
		  loadSfx("boss", "/Audio/Space_BGM1.wav");
		  loadSfx("bossshot", "/Audio/laser2.wav");
		  
		  loadSfx("pause", "/Audio/pause.wav");
		  loadSfx("start", "/Audio/loading.wav");
		  
		  loadSfx("intro", "/Audio/intro.wav");
		  loadSfx("play", "/Audio/start-level.wav");
		  
		  loadSfx("incoming", "/Audio/Arpeggiated-bassline.wav");
		 
	  }
	  
		
	  public void loadSfx(String name, String filename) {

			sfx.put(name, new AudioPlayer(filename));
		}
	}
