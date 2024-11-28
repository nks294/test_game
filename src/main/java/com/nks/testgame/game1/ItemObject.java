package main.java.com.nks.testgame.game1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import main.java.com.nks.testgame.core.Game;
import main.java.com.nks.testgame.core.Handler;
import main.java.com.nks.testgame.core.ID;

public class ItemObject extends GameObject {

	private Handler handler;
	
	private int timer = 0;
	// enemy color setting
	private Color col;
	// enemy size setting
	private final int enemySize = 32;

	private Random r = new Random();
	
	public ItemObject(int x, int y, ID id, Handler handler) {
		super(x, y, id);
		
		this.handler = handler;
		
		if (id == ID.ItemHeal) {
			col = Color.orange;
			velX = r.nextInt(4)+2;
			velY = r.nextInt(4)+2; 
		}
	}

	public void tick() {
		//Color TaleColor = new Color(255, 255, 255, 100);
		
		x += velX;
		y += velY;
		timer ++;

		// Wall Bounce
		if(y <= 0 || y >= Game.HEIGHT - 40) velY *= -1;
		if(x <= 0 || x >= Game.WIDTH - 25) velX *= -1;
		
		//handler.addObject(new Trail((int)x, (int)y, ID.Trail, TaleColor, 18, 18, 0.02f, handler));

		for (int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ID.ItemHeal) {
				if(timer == 800) {
					timer = 0;
					handler.removeObject(tempObject);
				}
			}
		}
	}

	public void render(Graphics g) {
		g.setColor(col);
		g.fillRect((int)x, (int)y, enemySize, enemySize);
	}

	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, enemySize, enemySize);
	}
}
