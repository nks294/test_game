package com.nks.testgame.core;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;

import com.nks.testgame.core.Game.STATE;
import com.nks.testgame.game1.Player;

public class Menu extends MouseAdapter {
	
	private Handler handler;
	private HUD hud;
	private Game game;
	private SpriteSheet ss;
	private SkinData skin = new SkinData();
	private ArrayList<ScoreData> normal_record;
	private ArrayList<ScoreData> hard_record;
	
	private int skinCol = 0;
	private int skinRow = 0;
	private int size = 32;
	//private boolean saved = false;
	
	public Menu(Game game, Handler handler, HUD hud, Save save) {
		this.handler = handler;
		this.game = game;
		this.hud = hud;
		ss = new SpriteSheet(game.getSprite());
	}
	
	private void skinRowCol(int skinNum) {
		if (skinNum > 4 && skinNum < 9) { skinRow = 2; skinCol = skinNum - 4; }
		else if (skinNum > 8) { skinRow = 3; skinCol = skinNum - 8; }
		else { skinRow = 1; skinCol = skinNum; }
	}
	
	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
			
		// 메인 메뉴 버튼들의 이벤트 처리 블록
		if (Game.gameState == STATE.Menu) {
			// 게임 시작 버튼
			if (hud.checkButton(mx, my, -49, 240+(0*110), 290, 64, "center", "none")) {
				// play menu click sound
				//AudioPlayer.getSound("menu_sound").play();
				Game.gameState = STATE.Select;
				return;
			}
			// 설명 버튼
			if (hud.checkButton(mx, my, 0, 240+(1*110), 290, 64, "center", "none")) {
				// play menu click sound
				//AudioPlayer.getSound("menu_sound").play();
				Game.gameState = STATE.Help;
			}
			// 스킨 버튼
			if (hud.checkButton(mx, my, 235+(0*100), 18, 80, 54, "center", "none")) {
				Game.gameState = STATE.Shop;
				AudioPlayer.getMusic("Help_BGM").loop();
			}
			// 랭킹 버튼
			if (hud.checkButton(mx, my, 235+(1*100), 18, 80, 54, "center", "none")) {
				normal_record = game.loadScore(0);
				hard_record = game.loadScore(1);
				Game.gameState = STATE.Record;
				return;
			}
		}
		
		// 도움 화면 버튼들의 이벤트 처리 블록
		if(Game.gameState == STATE.Help) {
			// back button in help screen
			if (hud.checkButton(mx, my, 0, 450, 200, 64, "center", "none"))  {
				// play menu click sound
				//AudioPlayer.getSound("menu_sound").play();
				Game.gameState = STATE.Menu;
				return;
			}
		}
		
		// 게임 오버화면 버튼들의 이벤트 처리 블록
		if(Game.gameState==STATE.End) {
			// 저장 안할시 그냥 바로 메인메뉴로
			if (hud.checkButton(mx, my, 120, 420, 160, 64, "center", "none")) {
				// play menu click sound
				//AudioPlayer.getSound("menu_sound").play();
				Game.gameState = STATE.Menu;
				AudioPlayer.getMusic("Menu_BGM").loop();
				hud.setScore(0);
				return;
			}
			// 저장할시 닉네임 입력창으로
			if (hud.checkButton(mx, my, -120, 420, 160, 64, "center", "none")) {
				// play menu click sound
				//AudioPlayer.getSound("menu_sound").play();
				Game.gameState = STATE.Save;
				
				AudioPlayer.getMusic("Menu_BGM").loop();
				Window.changeLayout("save");
				return;
			}
		}

		// 랭킹 화면 버튼들의 이벤트 처리 블록
		if(Game.gameState==STATE.Record) {
			if (hud.checkButton(mx, my, -30, 15, 100, 43, "right", "none")) {
				// play menu click sound
				//AudioPlayer.getSound("menu_sound").play();
				hud.setScore(0);
				Game.gameState = STATE.Menu;
				return;
			}
		}
		
		// 난이도 선택 화면 버튼들의 이벤트 처리 블록
		if (Game.gameState == STATE.Select) {
			// easy mode button
			if (hud.checkButton(mx, my, 0, 170+(0*120), 290, 64, "center", "none")) {
				Game.gameState = STATE.Ready;
				AudioPlayer.getMusic("Game_BGM").loop();
				
				// easy mode code
				game.setDifficulty(0);
				
				handler.addObject(new Player(200, 200, ID.Player, game, handler));
				handler.clearObjects();
				
				// play menu click sound
				//AudioPlayer.getSound("menu_sound").play();
			}
			
			// hard mode button
			if (hud.checkButton(mx, my, 0, 170+(1*120), 290, 64, "center", "none")) {
				Game.gameState = STATE.Ready;
				AudioPlayer.getMusic("Game_BGM").loop();
				
				// hard mode code
				game.setDifficulty(1);
				
				handler.addObject(new Player(200, 200, ID.Player, game, handler));
				handler.clearObjects();
				// play menu click sound
				//AudioPlayer.getSound("menu_sound").play();
			}
			
			// back button
			if (hud.checkButton(mx, my, 0, 170+(2*120), 290, 64, "center", "none")) {
				// play menu click sound
				//AudioPlayer.getSound("menu_sound").play();
				Game.gameState = STATE.Menu;
				return;
			}
		}
		
		// 스킨 변경 화면 버튼들의 이벤트 처리 블록
		if (Game.gameState == STATE.Shop) {
			// back button
			if(hud.checkButton(mx, my, 0, 450, 200, 64, "center", "none")) {
				// play menu click sound
				//AudioPlayer.getSound("menu_sound").play();
				Game.gameState = STATE.Menu;
				AudioPlayer.getMusic("Menu_BGM").loop();
			}
			
			// 스킨 이미지 나열
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 4; j++) {
					if(hud.checkButton(mx, my, 80+(j*90), 153+(i*80), 64, 64, "none", "none")) {
						if (((4*i)+(j+1)) >= 10) return; 
						Player.SKIN = (4*i)+(j+1);
					}
				}
			}
		}
	}
	
	public void render(Graphics g) {
		
		// 메인 메뉴 화면 그래픽 블록
		if (Game.gameState == STATE.Menu) {
			hud.placeText(g, "테스트 게임", 50, 0, 64, "center", "none", Color.white);
			hud.placeText(g, "ver "+Game.VER, 18, 10, -12, "none", "bottom", Color.gray);

			for (int i = 0; i < 2; i++) {
				String[] text1 = {"시작", "설명"}; String[] text2 = {"스킨", "랭킹"};
				hud.placeButton(g, text1[i], 30, 0, 240+(i*110), 290, 64, "center", "none", Color.white);
				hud.placeButton(g, text2[i], 25, 235+(i*100), 20, 80, 54, "center", "none", Color.white);
			}
		
		// 게임 설명 화면
		} else if (Game.gameState == STATE.Help) {
			String dec1 = "방향키를 사용해 날아드는 사각형들을 피하세요!";
			String dec2 = "하드모드는 더 어려워요!";
			hud.placeText(g, "설명", 50, 0, 80, "center", "none", Color.white);
			hud.placeText(g, dec1, 20, 0, -40, "center", "center", Color.white);
			hud.placeText(g, dec2, 20, 0, 0, "center", "center", Color.red);
			hud.placeButton(g, "뒤로", 30, 0, 450, 200, 64, "center", "none", Color.white);
			
		// 게임 오버 화면
		} else if (Game.gameState == STATE.End) {
			hud.placeText(g, "게임 오버", 50, 0, 70, "center", "none", Color.white);
			if (game.getDifficulty() == 1) hud.placeText(g, "하드 모드", 25, 0, 110, "center", "none", Color.white);
			
			hud.placeText(g, "안타깝게도, 죽어버렸습니다.", 30, 0, 240, "center", "none", Color.white);
			hud.placeText(g, "내 기록: " + hud.getScore() + " 포인트", 35, 0, 300, "center", "none", Color.white);
			if (hud.getScore() > game.getHighScore())
				hud.placeText(g, "최고기록 갱신!", 30, 0, 340, "center", "none", Color.white);
				
			hud.placeButton(g, "점수기록", 30, -100, 420, 160, 64, "center", "none", Color.white);
			hud.placeButton(g, "돌아가다", 30, 100, 420, 160, 64, "center", "none", Color.white);
			
		// 난이도 선택 화면
		} else if (Game.gameState == STATE.Select) {
			hud.placeText(g, "난이도 선택", 50, 0, 110, "center", "none", Color.white);
			for (int i = 0; i < 3; i++) {
				String[] text = {"보통", "어려운", "뒤로"};
				Color[] col = {Color.green, Color.red, Color.white};
				hud.placeButton(g, text[i], 30, 0, 170+(i*120), 290, 64, "center", "none", col[i]);
			}
			
		// 점수 랭킹 화면
		} else if (Game.gameState == STATE.Record) {
			// 랭킹화면 타이틀		
			hud.placeText(g, "게임 랭킹", 40, 0, 50, "center", "none", Color.white);
			
			// 보통 난이도 랭킹 박스
			hud.placeText(g, "보통", 25, -190, 100, "center", "none", Color.white);
			hud.placeButton(g, "", 0, 30, 120, 345, 410, "none", "none", Color.white);
			
			// 보통 난이도에 대한 점수 데이터를 불러와서 출력,
			// 만약 데이터가 없으면 기록이 없다고 출력함
			if (normal_record.size() <= 0) hud.placeText(g, "기록이 없습니다.", 16, -190, 340, "center", "none", Color.white);
			else {
				// 불러온 데이터 ArrayList 개수만큼 출력, 상위 15위까지만 불러오도록 설정됨
				for (int i = 0; i < normal_record.size(); i++) {
					// +"    "+ normal_record.get(i).getDate() << 날짜 데이터 출력
					// 저장된 정보 출력 부분
					hud.placeText(g, i+1 + "      " + normal_record.get(i).getName() +"      "+ new DecimalFormat("#,###").format(normal_record.get(i).getScore()) + "점", 15, 70, 148+(i*26), "none", "none", Color.white);
					
					// 플레이했던 스킨 출력부분
					skinRowCol(normal_record.get(i).getSkin());
					g.drawImage(ss.grabImage(skinRow, skinCol, size, size).getScaledInstance(16, 16, Image.SCALE_FAST), 45, 136+(i*26), null);
				}
			}
			
			// 어려움 난이도 랭킹 박스
			hud.placeText(g, "어려움", 15, 200, 100, "center", "none", Color.white);
			hud.placeButton(g, "", 24, -30, 120, 345, 410, "right", "none", Color.white);
			
			// 어려움 난이도에 대한 점수 데이터를 불러와서 출력,
			// 만약 데이터가 없으면 기록이 없다고 출력함
			if (hard_record.size() <= 0) hud.placeText(g, "기록이 없습니다.", 16, 200, 340, "center", "none", Color.white);
			else {
				// 불러온 데이터 ArrayList 개수만큼 출력, 상위 15위까지만 불러오도록 설정됨
				for (int i = 0; i < hard_record.size(); i++) {
					// 저장된 정보 출력 부분
					hud.placeText(g, i+1 + "      " + hard_record.get(i).getName() +"      "+ new DecimalFormat("#,###").format(hard_record.get(i).getScore()) + "점", 15, 450, 148+(i*26), "none", "none", Color.white);

					// 플레이했던 스킨 출력부분
					skinRowCol(hard_record.get(i).getSkin());
					g.drawImage(ss.grabImage(skinRow, skinCol, size, size).getScaledInstance(16, 16, Image.SCALE_FAST), 425, 136+(i*26), null);
				}
			}
			
			// 메인 메뉴로 돌아가기 버튼
			hud.placeButton(g, "돌아가다", 20, -30, 15, 100, 43, "right", "none", Color.white);
			
		// 스킨 선택 화면
		} else if (Game.gameState == STATE.Shop) {
			hud.placeText(g, "스킨 선택", 50, 0, 80, "center", "none", Color.white);

			// 스킨 목록 배경
			g.setColor(new Color(255, 255, 255, 70));
			g.fillRect(50, 123, Game.WIDTH - 405, 285);
			
			// 스킨 설명 테두리
			g.setColor(Color.white);
			g.drawRect(Game.WIDTH - 405 + 80, 123, 260, 285);
			
			// HUD Design setting.back button
			hud.placeButton(g, "뒤로", 30,  0, 450, 200, 64, "center", "none", Color.white);
			
			// HUD Design setting.skin array page1
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 4; j++) {
					g.drawImage(ss.grabImage(i+1, j+1, size, size).getScaledInstance(64, 64, Image.SCALE_DEFAULT), 80+(j*90), 153+(i*80), null);
					if (Player.SKIN == ((4*i)+(j+1))) {
						if (((4*i)+(j+1)) >= 10) return; 
						g.setColor(new Color(255, 255, 255, 100));
						g.fillRect(80+(j*90), 153+(i*80), 64, 64);
						
						hud.placeText(g, skin.getSkinName((4*i)+j), 25, 218, 155, "center", "none", Color.white);
						hud.placeText(g, skin.getSkinLabel((4*i)+j), 20, 218, 275, "center", "none", Color.white);
						//placeUI(g, "text", Game.WIDTH - 340, 230, 290, 64, "none", "none", "스킨에 대한 설명", 20, 10);
					}
				}
			}
		}
	}
	
	public void mouseReleased(MouseEvent e) { }
	
	public void tick() { }
	
}

//// 기록 저장
//} else if (Game.gameState == STATE.Save) {
//	g.setColor(Color.white);			
//	hud.placeText(g, "기록 저장", 0, 85, 213, 70, "center", "none", "기록 저장", 50, 10);
//	hud.placeText(g, "내 기록: " + hud.getScore() + " 포인트", 0, 200, 213, 70, "center", "none", "내 기록: " + hud.getScore() + " 포인트", 30, 10);
//	
//	//g.setColor(Color.green);			
//	//hud.placeUI(g, "text", 0, 125, 213, 70, "center", "none", "최고기록 갱신!!", 23, 10);
//	
//	g.setColor(Color.white);			
//	hud.placeText(g, "여기에 닉네임을 입력.", 0, 268, 213, 70, "center", "none", "여기에 닉네임을 입력.", 18, 10);
//	
//	// 닉네임 입력란
//	hud.placeButton(g, "플레이어", 0, 280, 360, 64, "center", "none", "플레이어", 30, 10);
//	
//	hud.placeButton(g, "", -100, 420, 160, 64, "center", "none", "기록하다", 30, 10);
//	hud.placeButton(g, "button", 100, 420, 160, 64, "center", "none", "돌아가다", 30, 10);
//	
//	if (saved) {
//		g.setColor(new Color(0,0,0,235));
//		g.fillRect((Game.WIDTH / 2) - (400/2) - 10, (Game.HEIGHT / 2) - (260/2), 400, 260);
//		g.setColor(new Color(255,255,255,100));
//		g.drawRect((Game.WIDTH / 2) - (400/2) - 10, (Game.HEIGHT / 2) - (260/2), 400, 260);
//		g.setColor(Color.white);
//		hud.placeText(g, "text", 0, -10, 160, 64, "center", "center", "저장완료!", 50, 10);
//		hud.placeButton(g, "button", 0, 50, 160, 64, "center", "center", "돌아가다", 30, 10);
//	}


//// 기록 저장
//if(Game.gameState==STATE.Save) {
//	String nickName = null;
//	if (nickName == null) nickName = "플레이어";
//	// save score to db
//	if (hud.checkButton(mx, my, -100, 420, 160, 64, "center", "none")) {
//		// play menu click sound
//		//AudioPlayer.getSound("menu_sound").play();
//		game.saveScore(nickName);
//		saved = true;
//	}
//	if (saved) {
//		if (hud.checkButton(mx, my, 0, 50, 160, 64, "center", "center")) {
//			// play menu click sound
//			//AudioPlayer.getSound("menu_sound").play();
//			normal_record = game.loadScore(0);
//			hard_record = game.loadScore(1);
//			Game.gameState = STATE.Record;
//			saved = false;
//			return;
//		}
//	}
//	// go to record screen
//	if (hud.checkButton(mx, my, 100, 420, 160, 64, "center", "none")) {
//		// play menu click sound
//		//AudioPlayer.getSound("menu_sound").play();
//		Game.gameState = STATE.Menu;
//		return;
//	}
//}

