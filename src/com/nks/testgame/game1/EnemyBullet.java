package com.nks.testgame.game1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import com.nks.testgame.core.Game;
import com.nks.testgame.core.Handler;
import com.nks.testgame.core.ID;

public class EnemyBullet extends GameObject {
	
	Game game;
	private Handler handler;
	//private BufferedImage enemy_image;
	// enemy color setting
	private Color col;
	// enemy size setting
	private final int bulletSize = 12;

	//private Random r = new Random();
	
	public EnemyBullet(int x, int y, ID id, Handler handler, Game game, int velX, int velY) {
		super(x, y, id);
		this.handler = handler;
		col = game.getDifficulty() == 0 ? Color.green : Color.blue;
		this.velX = game.getDifficulty() == 0 ? velX*1 : velX*1.7f;
		this.velY = game.getDifficulty() == 0 ? velY*1 : velY*1.7f;
	}

	public void tick() {
		x += velX;
		y += velY;
		
		if(x >= Game.WIDTH || y >= Game.HEIGHT) handler.removeObject(this);
		handler.addObject(new BulletTrail((int)x, (int)y, ID.Trail, col, bulletSize, bulletSize, 0.1f, handler));
	}

	public void render(Graphics g) {
		g.setColor(col);
		g.fillRect((int)x, (int)y, bulletSize, bulletSize);
	}

	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, bulletSize, bulletSize);
	}
}
