package main.java.com.nks.testgame.core;

import java.awt.Graphics;
import java.util.ArrayList;

import main.java.com.nks.testgame.game1.GameObject;
import main.java.com.nks.testgame.game1.Player;

public class Handler {
	
	private Game game;

	public ArrayList<GameObject> object = new ArrayList<GameObject>(100);
	
	public Handler (Game game) {
		this.game = game;
	}
	
	public void tick() {
		for (int i = 0; i < object.size(); i++) {
			GameObject tempObject = object.get(i);
			
			tempObject.tick();
		}
	}
	
	public void render(Graphics g) {
		for (int i = 0; i < object.size(); i++) {
			GameObject tempObject = object.get(i);
			
			tempObject.render(g);
		}
	}
	
	public void clearPlayer() {
		for (int i = 0; i < object.size(); i++) {
			GameObject tempObject = object.get(i);
			if (tempObject.getId() == ID.Player) {
				object.remove(tempObject);
			}
		}
	}
	
	public void clearObjects() {
		for (int i = 0; i < object.size(); i++) {
			GameObject tempObject = object.get(i);
			
			if(tempObject.getId() != ID.Player) {
				object.clear();
				if(Game.gameState != Game.STATE.End) {
					addObject(new Player((int)tempObject.getX(), (int)tempObject.getY(), ID.Player, game, this));
				}
			}
		}
	}

	public void addObject(GameObject object) {
		this.object.add(object);
	}
	
	public void removeObject(GameObject object) {
		this.object.remove(object);
	}
	
}
