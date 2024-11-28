package main.java.com.nks.testgame.game1;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import main.java.com.nks.testgame.core.Handler;
import main.java.com.nks.testgame.core.ID;

public class BulletTrail extends GameObject {

	private float alpha = 1;
	private float life;
	
	private Handler handler;
	private Color color;
	private int width, height;
	
	//life = 0.001 - 0.1
	
	public BulletTrail(float x, float y, ID id, Color color, int width, int height, float life, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		this.color = color;
		this.width = width;
		this.height = height;
		this.life = life;
		
	}

	public void tick() {
		if (alpha > life) {
			alpha -= (life - 0.0001f);
		} else handler.removeObject(this);
	}

	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setComposite(makeTransaprent(alpha));
		
		g.setColor(color);
		g.fillOval((int)x, (int)y, width, height);
		
		g2d.setComposite(makeTransaprent(1));
	}
	
	private AlphaComposite makeTransaprent(float alpha) {
		int type = AlphaComposite.SRC_OVER;
		return(AlphaComposite.getInstance(type, alpha));
	}

	public Rectangle getBounds() {
		return null;
	}

}
