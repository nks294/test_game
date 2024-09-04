package com.nks.testgame.core;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class AudioPlayer {
	
	public static Map<String, Sound> soundMap = new HashMap<String, Sound>();
	public static Map<String, Music> musicMap = new HashMap<String, Music>();
	
	public static void load() {
		
		try {
			//soundMap.put("Click", new Sound("res/click.ogg"));
      // BGM 경로
			musicMap.put("Menu_BGM", new Music("res/bgm/main_page.ogg"));
			musicMap.put("Help_BGM", new Music("res/bgm/help_page.ogg"));
			musicMap.put("Game_BGM", new Music("res/bgm/game_play.ogg"));

      // Sound 경로
			soundMap.put("Hit_SOUND", new Sound("res/sound/hit_sound.wav"));
			soundMap.put("Item_HEAL", new Sound("res/sound/item_heal.wav"));
		} catch (SlickException e) {
			
			e.printStackTrace();
			
		}
	}
	
	public static Music getMusic(String key) {
		return musicMap.get(key);
	}
	
	public static Sound getSound(String key) {
		return soundMap.get(key);
	}
}
