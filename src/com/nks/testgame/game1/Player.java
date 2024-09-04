package com.nks.testgame.game1;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.nks.testgame.core.AudioPlayer;
import com.nks.testgame.core.Game;
import com.nks.testgame.core.Handler;
import com.nks.testgame.core.ID;
import com.nks.testgame.core.SpriteSheet;

public class Player extends GameObject {
	
	// private Random r;
	private Handler handler;
	private Game game;

	public static int SKIN = 1;
	private BufferedImage player_image;
	
	public Player(int x, int y, ID id, Game game, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		this.game = game;
		SpriteSheet ss = new SpriteSheet(game.getSprite());

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 4; j++) {
				if (SKIN == ((4*i)+(j+1))) {
					player_image = ss.grabImage(i+1, j+1, 32, 32);
				}
			}
		}
	}

	public void tick( ) {
		x += velX;
		y += velY;
		
		x = Game.clamp((int)x, 0, Game.WIDTH - 48);
		y = Game.clamp((int)y, 0, Game.HEIGHT - 70);
		
		collision();
	}
	
	private void collision() {
		for(int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);

			
			if(tempObject.getId() == ID.Passive) {
				if(getBounds().intersects(tempObject.getBounds())) {
					handler.removeObject(tempObject);
					game.setStack(game.getStack()-1);
				}
			}
			
			if(tempObject.getId() == ID.Hostile || tempObject.getId() == ID.Bullet) {
				if(getBounds().intersects(tempObject.getBounds())) {
					AudioPlayer.getSound("Hit_SOUND").play();
					handler.removeObject(tempObject);
					game.setHealth(game.getHealth()-1);
				}
			}
			
			if(tempObject.getId() == ID.ItemHeal) {
				if(getBounds().intersects(tempObject.getBounds())) {
					handler.removeObject(tempObject);
					AudioPlayer.getSound("Item_HEAL").play();
					if (game.getHealth()+5 > 10) game.setHealth(10);
					else game.setHealth(game.getHealth()+5);
				}
			}
		}
	}
	
	public void render(Graphics g) {
		//g.setColor(Color.white);
		//g.fillRect((int)x, (int)y, 32, 32);
		g.drawImage(player_image, (int)x, (int)y, null);
	}

	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 32, 32);
	}
}
