package main.java.com.nks.testgame.game1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import main.java.com.nks.testgame.core.Game;
import main.java.com.nks.testgame.core.Handler;
import main.java.com.nks.testgame.core.ID;

public class EnemyBossBullet extends GameObject {
	
	private Handler handler;
	private Random r = new Random();

	// enemy color setting
	private final Color col = Color.green;
	// enemy size setting
	private final int enemySize = 8;

	public EnemyBossBullet(int x, int y, ID id, Handler handler) {
		super(x, y, id);
		
		this.handler = handler;
		
		velX = (r.nextInt(5 - -5) + -5);
		velY = 5;
	}

	public void tick() {
		x += velX;
		y += velY;
		
		//if (y <= 0 || y >= Game.HEIGHT - 54) velY *= -1;
		//if (x <= 0 || x >= Game.WIDTH - 80)	 velX *= -1;
		
		if(y >= Game.HEIGHT) handler.removeObject(this);
		
		handler.addObject(new Trail((int)x, (int)y, ID.Trail, col, enemySize, enemySize, 0.04f, handler));
	}

	public void render(Graphics g) {
		g.setColor(col);
		g.fillRect((int)x, (int)y, enemySize, enemySize);
	}

	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, enemySize, enemySize);
	}
}
