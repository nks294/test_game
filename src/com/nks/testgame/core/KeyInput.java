package com.nks.testgame.core;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.nks.testgame.core.Game.STATE;
import com.nks.testgame.game1.GameObject;

public class KeyInput extends KeyAdapter {

	private Handler handler;
	private boolean[] keyDown = new boolean[4];
	private Game game;
	
	public KeyInput(Handler handler, Game game) {
		this.handler = handler;
		this.game = game;
		
		keyDown[0] = false;
		keyDown[1] = false;
		keyDown[2] = false;
		keyDown[3] = false;
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		for(int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			
			if (tempObject.getId() == ID.Player) {
				// key event for player 1
				
				if (key == KeyEvent.VK_UP) { tempObject.setVelY(-5); keyDown[0] = true; }
				if (key == KeyEvent.VK_DOWN) { tempObject.setVelY(5); keyDown[1] = true; }
				if (key == KeyEvent.VK_RIGHT) { tempObject.setVelX(5); keyDown[2] = true; }
				if (key == KeyEvent.VK_LEFT) { tempObject.setVelX(-5); keyDown[3] = true; }
			}
		}
		if (key == KeyEvent.VK_P) {
			if (Game.gameState == STATE.Menu) {
				handler.clearObjects();
				handler.clearPlayer();
				game.menuParticle(35);
			}
			if (Game.gameState == STATE.Game) {
				Game.gameState = STATE.Pause;
			} else if (Game.gameState == STATE.Pause) {
				Game.gameState = STATE.Ready;
			}
		}
		if (key == KeyEvent.VK_ESCAPE) {
			if (Game.gameState == STATE.Pause) {
				game.gameOver(1);
			}
		}
	}
	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		for(int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			
			if (tempObject.getId() == ID.Player) {
				// key event for player 1
				
				if (key == KeyEvent.VK_UP) keyDown[0] = false;
				if (key == KeyEvent.VK_DOWN) keyDown[1] = false;
				if (key == KeyEvent.VK_RIGHT) keyDown[2] = false;
				if (key == KeyEvent.VK_LEFT) keyDown[3] = false;
				
				// vertical movement
				if (!keyDown[0] && !keyDown[1]) tempObject.setVelY(0);
				// horizontal movement
				if (!keyDown[2] && !keyDown[3]) tempObject.setVelX(0);
			}
		}
		
	}
	
}
