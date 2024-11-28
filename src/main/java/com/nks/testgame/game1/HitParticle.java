package main.java.com.nks.testgame.game1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import main.java.com.nks.testgame.core.Game;
import main.java.com.nks.testgame.core.Handler;
import main.java.com.nks.testgame.core.ID;

public class HitParticle extends GameObject {

	//private Random r = new Random();
	private Handler handler;
	int etwoTimer = 0;
	private Color col;
	int size = 6;
	
	
	public HitParticle(int x, int y, ID id, Handler handler, int velX, int velY) {
		super(x, y, id);
		
		this.handler = handler;
		
		this.velX = velX;
		this.velY = velY;
	}
	
	public void tick() {
		x += velX;
		y += velY;
		etwoTimer ++;
		col = new Color(255, 255, 200, 100-(etwoTimer*4));
		System.out.println(etwoTimer);
		
		//if(y <= 0 || y >= Game.HEIGHT - 40) velY *= -1;
		//if(x <= 0 || x >= Game.WIDTH - 25) velX *= -1;

		if(y >= Game.HEIGHT) handler.removeObject(this);
		if(x >= Game.WIDTH) handler.removeObject(this);
		
		handler.addObject(new Trail((int)x, (int)y, ID.Trail, col, size, size, 0.1f, handler));

		for (int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ID.Particle) {
				if(etwoTimer == 25) {
					etwoTimer = 0;
					handler.removeObject(tempObject);
				}
			}
		}
	}

	public void render(Graphics g) {

		g.setColor(col);
		g.fillRect((int)x, (int)y, size, size);
	}

	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, size, size);
	}

}
