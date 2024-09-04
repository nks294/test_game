package com.nks.testgame.core;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.nks.testgame.core.Game.STATE;

public class HUD {
	
	private Game game;
	private Color[] healthCol = new Color[5];
	private int score = 0;
	
	// 카운트 다운 딜레이를 위한 변수
	public int counter = 3;
	// 카운트다운 기능을 위한 타이머 변수
	private int startTimer = 0;
	
	public HUD (Game game) {
		this.game = game;
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
		if(Game.gameState == STATE.Game) score++;
	}
	
	public void render(Graphics g) {
//		g.setColor(Color.darkGray);
//		g.fillRect(0, 0, Game.WIDTH, 80);	

		// 체력 안내문구 표시
		placeText(g, "LIFE: ", 15, 15, 32, "none", "none", Color.white);
		
		// 아이템으로 인해 기본 체력량을 초과했을 때
		if (game.getHealth() > 5) {
			// 체력 5칸은 유지
			for (int i = 1; i <= 5; i++) {
				healthCol[i-1] = Color.green;
				// 초과된 체력은 파란색으로 표시
				for (int j = 1; j <= game.getHealth()-5; j++)
					healthCol[j-1] = Color.cyan;
				g.setColor(healthCol[i-1]);
				g.fillOval(52+((i-1)*30), 15, 23, 23);
			}
		// 체력량이 5 이하일때
		} else {
			for (int i = 1; i <= game.getHealth(); i++) {
				// 3개 이하일경우 노란색, 1개일 경우 빨간색으로 체력 표시
				healthCol[i-1] = game.getHealth() > 3 ? Color.green : game.getHealth() > 1 ? Color.yellow : Color.red;
				g.setColor(healthCol[i-1]);
				g.fillOval(52+((i-1)*30), 15, 23, 23);
			}
		}
		
		// 점수 표시
		placeText(g, "SCORE: "+ score, 15, 15, 60, "none", "none", Color.white);
		
		// 일시정지 안내문 표시
		if(Game.gameState != STATE.Pause) {
			placeText(g, "P키로 일시정지", 15, 670, 30, "none", "none", Color.gray);
		}
		
		// 일시정지 상태 표시
		if(Game.gameState == STATE.Pause) {
			placeText(g, "일시정지", 80, 0, 270, "center", "none", Color.white);
			placeText(g, "[ P ] 게임 계속 진행      [ ESC ] 메인 메뉴", 32, 0, 340, "center", "none", Color.white);
		}
		
		// 준비 카운트다운 표시
		if(Game.gameState == STATE.Ready) {
			placeText(g, Integer.toString(counter), 120, 0, 90, "center", "center", Color.white);
		}
	}
	
	// 화면에 텍스트를 출력하는 메소드
	public void placeText(Graphics g, String text, int fontSize, int x, int y, String alignH, String alignV, Color color) {
		g.setFont(new Font("nanumgothic",1 ,fontSize));
		g.setColor(color);
		
		x = alignH.equals("center") ? (Game.WIDTH / 2 - 10) - (g.getFontMetrics().stringWidth(text) / 2) + x : 
			alignH.equals("right") ? Game.WIDTH - 15 - g.getFontMetrics().stringWidth(text) + x : x;
		
		y = alignV.equals("center") ? (Game.HEIGHT / 2) - (fontSize / 2) + y : 
			alignV.equals("bottom") ? Game.HEIGHT - 20 - fontSize + y : y;
		
		g.drawString(text, x, y);
	}
	
	// 화면에 버튼을 출력하는 메소드
	public void placeButton(Graphics g, String text, int fontSize, int x, int y, int width, int height, String alignH, String alignV, Color color) {
		g.setFont(new Font("nanumgothic", 1, fontSize));
		g.setColor(color);
		
		x = alignH.equals("center") ? (Game.WIDTH / 2 - 10) - (width / 2) + x : alignH.equals("right") ? Game.WIDTH - 15 - width + x : x;
		y = alignV.equals("center") ? (Game.HEIGHT / 2) - (height / 2) + y : alignH.equals("bottom") ? Game.HEIGHT - height + y : y;

		g.drawRect(x, y, width, height);
		g.drawString(text, x + (width / 2) - (g.getFontMetrics().stringWidth(text) / 2), y + (height / 2) + (fontSize / 2) - (fontSize / 6));
		
	}
	
	// 버튼 클릭 처리 메소드
	public boolean checkButton(int mx, int my, int x, int y, int width, int height, String alignH, String alignV) {	
		x = alignH.equals("center") ? (Game.WIDTH / 2 - 10) - (width / 2) + x : alignH.equals("right") ? Game.WIDTH - 15 - width + x : x;
		y = alignV.equals("center") ? (Game.HEIGHT / 2) - (height / 2) + y : alignH.equals("bottom") ? Game.HEIGHT - height + y : y;

		if(mx > x && mx < x + width) {
			if(my > y && my < y + height) {
				return true;
			} else return false;
		} else return false;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
}
