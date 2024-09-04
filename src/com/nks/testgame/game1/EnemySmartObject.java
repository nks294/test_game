package com.nks.testgame.game1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import com.nks.testgame.core.Game;
import com.nks.testgame.core.Handler;
import com.nks.testgame.core.ID;

public class EnemySmartObject extends GameObject {
	
	Game game;
	private Handler handler;
	private GameObject player;
	private int timer = 0;
	private Random r = new Random();

	private float smartSpeed = 0;
	// enemy color setting
	private Color col = Color.yellow;
	// enemy size setting
	private final int enemySize = 16;

	public EnemySmartObject(int x, int y, ID id, Handler handler, Game game) {
		super(x, y, id);
		
		this.handler = handler;

		col = Color.yellow;
		smartSpeed = game.getDifficulty() == 0 ? -2.0f : -3.0f;
		
		for (int i = 0; i < handler.object.size(); i++) {
			if (handler.object.get(i).getId() == ID.Player) player = handler.object.get(i);
		}
		timer = 0;
	}

	public void tick() {
		x += velX;
		y += velY;
		timer++;
		
		float diffX = x - player.getX() - 8;
		float diffY = y - player.getY() - 8;
		float distance = (float) Math.sqrt((x-player.getX())*(x-player.getX()) + (y-player.getY())*(y-player.getY()));
		
		velX = (float) ((smartSpeed/distance) * diffX);
		velY = (float) ((smartSpeed/distance) * diffY);
		
		for (int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			if(tempObject.getId() == ID.Hostile && timer > r.nextInt(350)+800) {
				timer = 0;
				handler.removeObject(tempObject);
			}
		}
		handler.addObject(new Trail(x, y, ID.Trail, col, enemySize, enemySize, 0.05f, handler));
	}

	public void render(Graphics g) {
		g.setColor(col);
		g.fillRect((int)x, (int)y, enemySize, enemySize);
	}

	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, enemySize, enemySize);
	}
}
