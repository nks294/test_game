package com.nks.testgame.core;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import com.nks.testgame.game1.Counter;
import com.nks.testgame.game1.Player;
import com.nks.testgame.game1.Spawner;

public class Game extends Canvas implements Runnable {
	
	private static final long serialVersionUID = 1433844287058382955L;
	
	// 게임의 화면 크기를 위한 값을 final로 선언
	// 만약 게임 내 설정에서 해상도 조정 옵션을 만들고자 한다면 final을 빼야 함
	public static final int WIDTH = 800, HEIGHT = WIDTH / 12 * 9;
	// 다른 클래스에도 상태 변수에 접근할 수 있도록 static으로 생성하고 시작시 게임의 상태를 Menu 로 변경
	public static STATE gameState = STATE.Menu;
	// 버전 변수, 현재는 final로 선언하지만 이후 변경 가능성이 있음
	public static final String VER = "0.1.5";
	
	// 체력 변수
	private int health = 5;
	// 없애야 하는 오브젝트의 개수
	private int stack = 0;
	// 난이도 변수 0은 보통, 1은 어려움
	private int difficulty = 0;
	
	// 다른 클래스들 불러오기
	private Thread thread;
	private Handler handler;
	private HUD hud;
	private Spawner spawner;
	private Menu menu;
	private Color col;
	private Counter counter;
	private BufferedImage bf;
	private GameDAO db;
	private ScoreData records;
	private Save save;
	
	// 게임이 실행중인지 확인하는 변수
	private boolean running = false;
	
	// 게임의 상태표기를 위한 enum 변수
	public enum STATE {
		Menu, Select, Help, Shop,
		Game, End, Pause, Ready, Save,
		Record
	};
	
	public Game() throws SQLException {

		
		// 플레이어 스킨을 위한 버퍼 이미지 로더 객체 생성
		BufferedImageLoader loader = new BufferedImageLoader();
		// 플레이어 스킨 이미지파일 불러오기
		bf = loader.loadPlayer("/sheet_player.png");

		handler = new Handler(this);
		hud = new HUD(this);
		save = new Save(this, hud);
		counter = new Counter(this, hud);
		menu = new Menu(this, handler, hud, save);
		spawner = new Spawner(handler, this);
		db = new GameDAO();
		
		// 쿼리 연결 테스트문
		// db.testSQL();
		
		// 게임 화면, 메뉴 화면에 키보드 리스너와 마우스 리스너 호출
		this.addKeyListener(new KeyInput(handler, this));
		this.addMouseListener(menu);
		
		col = Color.black;
		
		// 오디오플레이어 클래스 호출
		AudioPlayer.load();
		// 시작시 메뉴 BGM 재생
		AudioPlayer.getMusic("Menu_BGM").loop();
		
		// 새로운 창 만들기
		new Window(WIDTH, HEIGHT, "테스트 게임", this, save);

		//Window.changeLayout("save");
		// 게임이 진행중이 아니면 메소드를 호출하여 배경에 파티클 생성
		if(gameState != STATE.Game) menuParticle(25);
	}

	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 스레드 run 메소드
	public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		//int frames = 0;
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			
			while(delta >= 1) {
				tick();
				delta--;
			}
			if (running) {
				render ();
			}
			//frames ++;
			
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				//frames = 0;
			}
		}
		stop();
	}
	
	private void tick() {
		// 준비 상태라면 카운트다운만 실행
		if(gameState == STATE.Ready) {
			hud.tick();
		}

		// 게임이 시작상태라면 게임 실행
		if(gameState == STATE.Game) {
			hud.tick();
			counter.tick();
			spawner.tick();
			handler.tick();
			
			// 체력이 0이 되어 죽거나 없애야 할 몬스터의 수가 10마리를 넘었을 경우 게임 오버
			if(health <= 0 || stack >= 6) {
				gameOver(0);
			}
			
		// 게임을 하고 있지 않으면 메뉴 실행
		} else if (gameState != STATE.Game && gameState != STATE.Pause && gameState != STATE.Ready) {
			menu.tick();
			handler.tick();
		}
	}
	
	// 그래픽 출력 메소드
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) { this.createBufferStrategy(3); return; }
		Graphics g = bs.getDrawGraphics();
		
		// 배경색 변경
		g.setColor(col);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		// 만약 게임 상태일 때, 스택 카운터랑 게임 hud 렌더링 활성화
		if(gameState == STATE.Game || gameState == STATE.Pause || gameState == STATE.Ready) {
			counter.render(g);
			handler.render(g);
			hud.render(g);
		// 게임 상태가 아닐때 메뉴 화면 렌더링 활성화
		} else if (gameState == STATE.Menu || gameState == STATE.Help || gameState == STATE.End || gameState == STATE.Select || gameState == STATE.Shop || gameState == STATE.Record || gameState == STATE.Save) {
			handler.render(g);
			// 반투명한 검정 배경
			g.setColor(new Color(0, 0, 0, 140));
			g.fillRect(0, 0, WIDTH, HEIGHT);
			menu.render(g);
		}

		g.dispose();
		bs.show();
	}

	// 배경색을 변경하는 set 메소드
	public void setBackgroundColor (Color col) {
		this.col = col;
	}

	// 메뉴용 파티클을 생성하는 메소드
	public void menuParticle(int cnt) {
		for (int i = 0; i < cnt; i++) {
			handler.addObject(new MenuParticle(WIDTH / 2, HEIGHT /2, ID.Particle, handler));
		}
	}
	
	// 게임 화면 밖으로 나가지 않도록 하는 메소드
	public static float clamp(float var, float min, float max) {
		if (var >= max) {
			return var = max;
		} else if (var <= min) {
			return var = min;
		} else {
			return var;
		}
	}
	
	// 점수 데이터 저장 메소드
	public void saveScore(String name) {
		// 노말 모드 기록 저장
		if (difficulty == 0) {
			records = new ScoreData(hud.getScore(), Player.SKIN, name, Date.valueOf(LocalDate.now()));
			db.savePlayerRecord(records, 0);
		// 하드 모드 기록 저장
		} else {
			records = new ScoreData(hud.getScore(), Player.SKIN, name, Date.valueOf(LocalDate.now()));
			db.savePlayerRecord(records, 1);
		}
	}

	// 점수 데이터 불러오기 메소드
	public ArrayList<ScoreData> loadScore(int difficulty) {
		return db.loadPlayerRecord(difficulty);
	}
	
//	// 최고기록 비교 메소드
//	public void loadScore() throws IOException {
//		String diffScore;
//		
//		if (difficulty == 0) {
//			diffScore = "res/nscore.txt";
//		} else {
//			diffScore = "res/hscore.txt";
//		}
//		
//		BufferedReader saveFile = new BufferedReader(new FileReader(diffScore));
//		int highscore = Integer.parseInt((saveFile.readLine()));
//		saveFile.close();
//		
//		if(hud.getScore() > highscore) {
//			saveScore();
//		} else {
//			this.highscore = highscore;
//		}
//	}
	
	// 게임 오버 메소드
	public void gameOver (int state) {
		// 음악 정지
		AudioPlayer.getMusic("Game_BGM").stop();

		// 게임이랑 스택 초기화
		health = 5; stack = 0;
		
		// 오브젝트 스폰용 타이머 리셋
		spawner.resetTimer();
		
		// 남아있는 오브젝트들 삭제
		handler.clearObjects();
		handler.clearPlayer();
		
		// 게임 오버 화면에 파티클 출력
		menuParticle(25);

		if (state == 0) {
			// 게임 상태를 게임 오버 상태로 변경하고
			// 점수를 DB에 저장하는 메소드 호출
			gameState = STATE.End;
		} else {
			// 게임 상태를 메뉴 상태로 변경하고
			// 메인메뉴 BGM 재생
			gameState = STATE.Menu;
			AudioPlayer.getMusic("Menu_BGM").loop();
		}
	}

	// 각 값들에 대한 getter setter 메소드들
	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
	
	public int getStack() {
		return stack;
	}

	public void setStack(int stack) {
		this.stack = stack;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	public int getHighScore() {
		int highScore;
		try {
			highScore = db.loadPlayerRecord(difficulty).get(0).getScore();
		} catch (IndexOutOfBoundsException e) {
			highScore = 0;
		}
		return highScore;
	}

	public BufferedImage getSprite() {
		return bf;
	}

	public void setSprite(BufferedImage bf) {
		this.bf = bf;
	}
	
	// 메인 메소드
	public static void main(String[] args) throws SQLException {
		System.setProperty("sun.java2d.opengl", "true");
		new Game();
	}
}