package com.nks.testgame.game1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import com.nks.testgame.core.Game;
import com.nks.testgame.core.Handler;
import com.nks.testgame.core.ID;

public class EnemyBoss extends GameObject {
	
	private Handler handler;
	private Random r = new Random();
	
	private int timer = 60;
	private int timer2 = 50;
	
	// enemy color setting
	private final Color col = Color.green;
	// enemy size setting
	private final int enemySize = 64;

	public EnemyBoss(int x, int y, ID id, Handler handler) {
		super(x, y, id);
		
		this.handler = handler;
		
		velX = 0;
		velY = 2;
	}

	public void tick() {
		x += velX;
		y += velY;
		
		if (timer <= 0) velY = 0;
		else timer--;
		
		if(timer <= 0) timer2--;
		if(timer2 <= 0) {
			if(velX == 0) velX = 5;
			int spawn = r.nextInt(10);
			if(spawn == 0) handler.addObject(new EnemyBossBullet((int)x+48, (int)y+48, ID.Bullet, handler));
		}
		
		//if (y <= 0 || y >= Game.HEIGHT - 54) velY *= -1;
		if (x <= 0 || x >= Game.WIDTH - 80)	 velX *= -1;
		
		handler.addObject(new Trail((int)x, (int)y, ID.Trail, col, enemySize, enemySize, 0.07f, handler));
	}

	public void render(Graphics g) {
		g.setColor(col);
		g.fillRect((int)x, (int)y, enemySize, enemySize);
	}

	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, enemySize, enemySize);
	}
}
