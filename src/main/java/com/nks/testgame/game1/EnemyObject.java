package main.java.com.nks.testgame.game1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import main.java.com.nks.testgame.core.Game;
import main.java.com.nks.testgame.core.Handler;
import main.java.com.nks.testgame.core.ID;

public class EnemyObject extends GameObject {
	
	Game game;
	private Handler handler;
	//private BufferedImage enemy_image;
	private int timer = 0;
	// enemy color setting
	private Color col;
	// enemy size setting
	private final int enemySize = 16;

	private Random r = new Random();
	
	public EnemyObject(int x, int y, ID id, Handler handler, Game game) {
		super(x, y, id);
		
		this.handler = handler;
		
		if (id == ID.Passive) {
			if (game.getDifficulty() == 0) {
				col = Color.red;
				velX = r.nextInt(2)+4;
				velY = r.nextInt(2)+4;
			} else {
				col = Color.red;
				velX = r.nextInt(2)+8;
				velY = r.nextInt(2)+8;
			}
		} else if (id == ID.Hostile) {
			if (game.getDifficulty() == 0) {
				col = Color.green;
				velX = r.nextInt(2)+4;
				velY = r.nextInt(2)+4;
			} else {
				col = Color.blue;
				velX = r.nextInt(2)+6;
				velY = r.nextInt(2)+6;
			}
		}
		
		timer = 0;
	}

	public void tick() {
		x += velX;
		y += velY;
		timer++;
		
		if (y <= 0 || y >= Game.HEIGHT - 54) velY *= -1;
		if (x <= 0 || x >= Game.WIDTH - 32) velX *= -1;

		for (int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			if(tempObject.getId() == ID.Hostile && timer > r.nextInt(350)+500) {
				handler.removeObject(tempObject);
				timer = 0;
			}
		}
		handler.addObject(new Trail((int)x, (int)y, ID.Trail, col, enemySize, enemySize, 0.05f, handler));
	}

	public void render(Graphics g) {
		g.setColor(col);
		g.fillRect((int)x, (int)y, enemySize, enemySize);
	}

	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, enemySize, enemySize);
	}
}
