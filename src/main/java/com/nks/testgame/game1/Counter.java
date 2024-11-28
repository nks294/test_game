package main.java.com.nks.testgame.game1;

import java.awt.Color;
import java.awt.Graphics;

import main.java.com.nks.testgame.core.Game;
import main.java.com.nks.testgame.core.HUD;
import main.java.com.nks.testgame.core.Game.STATE;

public class Counter {
	
	// 카운트 다운 딜레이를 위한 변수
	public int counter = 3;
	// 카운트다운 기능을 위한 타이머 변수
	private int startTimer = 0;
	private Color col;
	private HUD hud;
	private Game game;
	
	public Counter (Game game, HUD hud) {
		this.game = game;
		this.hud = hud;
	}
	
	public void tick() {
		// 게임 시작시, 카운트다운 타이머 작동
		if(Game.gameState == STATE.Ready) {
			startTimer++;
			if (startTimer == 60) {
				counter--;
				startTimer = 0;
				if (counter == 0) {
					Game.gameState = STATE.Game;
					counter = 3;
				}
			}
		}
	}
	
	public void render(Graphics g) {		
		// 스택 카운터 표시
		if(Game.gameState == STATE.Game) {
			col = game.getStack() < 4 ? new Color(255, 255, 255, 100) : new Color(255, 0, 0, 100);
			hud.placeText(g, Integer.toString(game.getStack()), 120, 0, 90, "center", "center", col);
		}
	}
}
