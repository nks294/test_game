package com.nks.testgame.core;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import com.nks.testgame.game1.GameObject;
import com.nks.testgame.game1.Trail;

public class MenuParticle extends GameObject {
	
	private Handler handler;
	private Random r = new Random();

	private Color col;
	
	public MenuParticle(int x, int y, ID id, Handler handler) {
		super(x, y, id);
		
		this.handler = handler;

		velX = (r.nextInt(10 - -10) + -10);
		velY = (r.nextInt(10 - -10) + -10);
		
		if (velX == 0) velX = 2;
		if (velY == 0) velY = 2;

		col = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
	}

	public void tick() {
		x += velX;
		y += velY;
		
		if (y <= 0 || y >= Game.HEIGHT - 54) velY *= -1;
		if (x <= 0 || x >= Game.WIDTH - 32)	 velX *= -1;
		
		handler.addObject(new Trail((int)x, (int)y, ID.Trail, col, 16, 16, 0.04f, handler));
	}

	public void render(Graphics g) {
		g.setColor(col);
		g.fillRect((int)x, (int)y, 16, 16);
	}

	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 16, 16);
	}
}
