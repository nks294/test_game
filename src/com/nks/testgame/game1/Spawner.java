package com.nks.testgame.game1;

import java.util.Random;

import com.nks.testgame.core.Game;
import com.nks.testgame.core.Handler;
import com.nks.testgame.core.ID;

// TODO: 패턴 생성 부분을 좀 더 단순하게 할 수 없을까

public class Spawner {
	
	private Handler handler;
	private Random r = new Random();
	private Game game;

	private boolean hostile = false;
	private int hostileSpawnTime = 400;
	private int passiveSpawnTime = 150;
	private int passiveTimer = 0;
	private int hostileTimer = 0;
	private int patternTimer = 0;
	private int bulletTimer = 0;
	
	public Spawner(Handler handler, Game game) {
		this.handler = handler;
		this.game = game;
	}

	public void tick() {
		passiveTimer++; 
		hostileTimer++;
		patternTimer++;
		
		spawnPassive();
		spawnHostile();

		// 맵 탄환패턴 블록
		if(patternTimer >= 10) {
			patternTimer = 0;
			bulletTimer++;
			
			// 500틱, 적대적 오브젝트 출현 시작
			if (bulletTimer == 50) {
				hostileTimer = 0;
				hostile = true;
				
			// 1100틱, 가로 방향 다중 패턴
			} else if (bulletTimer == 110) {
				for (int i = 0; i < 10; i++) { handler.addObject(new EnemyBullet(30+(80*i),0 , ID.Bullet, handler, game, 0, 4)); } 
				
			// 1200틱, 세로 방향 다중 패턴
			} else if (bulletTimer == 120) {
				for (int i = 0; i < 16; i++) {
					handler.addObject(new EnemyBullet(0 ,10+(80*i) , ID.Bullet, handler, game, 4, 0));
				}

			// 1800틱, 적대적, 따라오는 오브젝트 추가
			} else if (bulletTimer == 180) {
				handler.addObject(new EnemySmartObject(r.nextInt(Game.WIDTH - 50), r.nextInt(Game.HEIGHT - 50), ID.Hostile, handler, game));
																				
			// 2000틱, 바둑판식 패턴
			} else if (bulletTimer == 200) {
				for (int i = 0; i < 10; i++) {
					handler.addObject(new EnemyBullet(30+(80*i),0 , ID.Bullet, handler, game, 0, 4));
				}
				for (int i = 0; i < 16; i++) {
					handler.addObject(new EnemyBullet(0 ,10+(80*i) , ID.Bullet, handler, game, 4, 0));
				}

			// 2150틱, 아이템 추가
			} else if (bulletTimer == 215) {
				spawnItem(ID.ItemHeal);
				
			// 2500틱, 없애야 하는 오브젝트 스폰율 증가
			} else if (bulletTimer == 250) {
				passiveSpawnTime = game.getDifficulty() == 0 ? 100 : 80;
				
			// 3000틱, 세로방향 연속발사 패턴
			} else if (bulletTimer == 300) {
				handler.addObject(new EnemyBullet(30+(80*0),0 , ID.Bullet, handler, game, 0, 8));
			} else if (bulletTimer == 301) {
				handler.addObject(new EnemyBullet(30+(80*1),0 , ID.Bullet, handler, game, 0, 8));
			} else if (bulletTimer == 302) {
				handler.addObject(new EnemyBullet(30+(80*2),0 , ID.Bullet, handler, game, 0, 8));
			} else if (bulletTimer == 303) {
				handler.addObject(new EnemyBullet(30+(80*3),0 , ID.Bullet, handler, game, 0, 8));
			} else if (bulletTimer == 304) {
				handler.addObject(new EnemyBullet(30+(80*4),0 , ID.Bullet, handler, game, 0, 8));
			} else if (bulletTimer == 305) {
				handler.addObject(new EnemyBullet(30+(80*5),0 , ID.Bullet, handler, game, 0, 8));
			} else if (bulletTimer == 306) {
				handler.addObject(new EnemyBullet(30+(80*6),0 , ID.Bullet, handler, game, 0, 8));
			} else if (bulletTimer == 307) {
				handler.addObject(new EnemyBullet(30+(80*7),0 , ID.Bullet, handler, game, 0, 8));
			} else if (bulletTimer == 308) {
				handler.addObject(new EnemyBullet(30+(80*8),0 , ID.Bullet, handler, game, 0, 8));
			} else if (bulletTimer == 309) {
				handler.addObject(new EnemyBullet(30+(80*9),0 , ID.Bullet, handler, game, 0, 8));
			} else if (bulletTimer == 310) {
				handler.addObject(new EnemyBullet(30+(80*10),0 , ID.Bullet, handler, game, 0, 8));
			} else if (bulletTimer == 311) {
				handler.addObject(new EnemyBullet(30+(80*11),0 , ID.Bullet, handler, game, 0, 8));
			} else if (bulletTimer == 312) {
				handler.addObject(new EnemyBullet(30+(80*12),0 , ID.Bullet, handler, game, 0, 8));
			}
				
			// 3500틱, 가로세로 연속발사 패턴
			else if (bulletTimer == 350) {
				handler.addObject(new EnemyBullet(30+(80*0),0 , ID.Bullet, handler, game, 0, 5));
				handler.addObject(new EnemyBullet(0 ,30+(80*0) , ID.Bullet, handler, game, 5, 0));
			} else if (bulletTimer == 351) {
				handler.addObject(new EnemyBullet(30+(80*1),0 , ID.Bullet, handler, game, 0, 5));
				handler.addObject(new EnemyBullet(0 ,30+(80*1) , ID.Bullet, handler, game, 5, 0));
			} else if (bulletTimer == 352) {
				handler.addObject(new EnemyBullet(30+(80*2),0 , ID.Bullet, handler, game, 0, 5));
				handler.addObject(new EnemyBullet(0 ,30+(80*2) , ID.Bullet, handler, game, 5, 0));
			} else if (bulletTimer == 353) {
				handler.addObject(new EnemyBullet(30+(80*3),0 , ID.Bullet, handler, game, 0, 5));
				handler.addObject(new EnemyBullet(0 ,30+(80*3) , ID.Bullet, handler, game, 5, 0));
			} else if (bulletTimer == 354) {
				handler.addObject(new EnemyBullet(30+(80*4),0 , ID.Bullet, handler, game, 0, 5));
				handler.addObject(new EnemyBullet(0 ,30+(80*4) , ID.Bullet, handler, game, 5, 0));
			} else if (bulletTimer == 355) {
				handler.addObject(new EnemyBullet(30+(80*5),0 , ID.Bullet, handler, game, 0, 5));
				handler.addObject(new EnemyBullet(0 ,30+(80*5) , ID.Bullet, handler, game, 5, 0));
			} else if (bulletTimer == 356) {
				handler.addObject(new EnemyBullet(30+(80*6),0 , ID.Bullet, handler, game, 0, 5));
				handler.addObject(new EnemyBullet(0 ,30+(80*6) , ID.Bullet, handler, game, 5, 0));
			} else if (bulletTimer == 357) {
				handler.addObject(new EnemyBullet(30+(80*7),0 , ID.Bullet, handler, game, 0, 5));
				handler.addObject(new EnemyBullet(0 ,30+(80*7) , ID.Bullet, handler, game, 5, 0));
			} else if (bulletTimer == 358) {
				handler.addObject(new EnemyBullet(30+(80*8),0 , ID.Bullet, handler, game, 0, 5));
				handler.addObject(new EnemyBullet(0 ,30+(80*8) , ID.Bullet, handler, game, 5, 0));
			} else if (bulletTimer == 359) {
				handler.addObject(new EnemyBullet(30+(80*9),0 , ID.Bullet, handler, game, 0, 5));
				handler.addObject(new EnemyBullet(0 ,30+(80*9) , ID.Bullet, handler, game, 5, 0));
			} else if (bulletTimer == 360) {
				handler.addObject(new EnemyBullet(30+(80*10),0 , ID.Bullet, handler, game, 0, 5));
				handler.addObject(new EnemyBullet(0 ,30+(80*10) , ID.Bullet, handler, game, 5, 0));
			} else if (bulletTimer == 361) {
				handler.addObject(new EnemyBullet(30+(80*11),0 , ID.Bullet, handler, game, 0, 5));
				handler.addObject(new EnemyBullet(0 ,30+(80*11) , ID.Bullet, handler, game, 5, 0));
			} else if (bulletTimer == 362) {
				handler.addObject(new EnemyBullet(30+(80*12),0 , ID.Bullet, handler, game, 0, 5));
				handler.addObject(new EnemyBullet(0 ,30+(80*12) , ID.Bullet, handler, game, 5, 0));
			
			
			// 4000틱, 소환 타이밍 증가, 부채꼴 발사 패턴
			} else if (bulletTimer == 400) {
				reduceTimer(20);

				for (int i = 0; i < 8; i++) {
					handler.addObject(new EnemyBullet(1, 1, ID.Bullet, handler, game, i, 6-i));
				}
			} else if (bulletTimer == 401 ) {
				for (int i = 0; i < 8; i++) {
					handler.addObject(new EnemyBullet(Game.WIDTH-1, 1, ID.Bullet, handler, game, -6+i, i));
				}
			} else if (bulletTimer == 403 ) {
				for (int i = 0; i < 8; i++) {
					handler.addObject(new EnemyBullet(1, 1, ID.Bullet, handler, game, i, 6-i));
				}
			} else if (bulletTimer == 404 ) {
				for (int i = 0; i < 8; i++) {
					handler.addObject(new EnemyBullet(Game.WIDTH-1, 1, ID.Bullet, handler, game, -6+i, i));
				}
			} 
			
			// 4500틱, 소환 타이밍 증가
			else if (bulletTimer == 450) {
				for (int i = 0; i < 8; i++) {
					handler.addObject(new EnemyBullet(1, 1, ID.Bullet, handler, game, i, 6-i));
				}
			} else if (bulletTimer == 455 ) {
				for (int i = 0; i < 8; i++) {
					handler.addObject(new EnemyBullet(Game.WIDTH-1, 1, ID.Bullet, handler, game, -6+i, i));
				}
			} else if (bulletTimer == 460 ) {
				for (int i = 0; i < 8; i++) {
					handler.addObject(new EnemyBullet(1, Game.HEIGHT-1, ID.Bullet, handler, game, i, -6+i));
				}
			} else if (bulletTimer == 465 ) {
				for (int i = 0; i < 8; i++) {
					handler.addObject(new EnemyBullet(Game.WIDTH-1, Game.HEIGHT-1, ID.Bullet, handler, game, -6+i, -i));
				}
				
			// 5000틱, 소환 타이밍 증가
			} else if (bulletTimer == 500) {
				reduceTimer(20);
			}	
		}
	}

	// 먹어야 하는 오브젝트 생성 메소드
	private void spawnPassive() {
		if(passiveTimer > passiveSpawnTime) {
			handler.addObject(new EnemyObject(r.nextInt(Game.WIDTH - 100) + 50, r.nextInt(Game.HEIGHT - 100) + 50, ID.Passive, handler, game));
			game.setStack(game.getStack()+1);
			passiveTimer = 0;
		}
	}

	// 피해야 하는 오브젝트 생성 메소드
	private void spawnHostile() {
		if (hostileTimer > ((r.nextInt(3)+1)*50)+hostileSpawnTime && hostile) {
			handler.addObject(new EnemyObject(r.nextInt(Game.WIDTH - 100) + 50, r.nextInt(Game.HEIGHT - 100) + 50, ID.Hostile, handler, game));
			hostileTimer = 0;
		}
	}
	
	private void reduceTimer(int time) {
		hostileSpawnTime = game.getDifficulty() == 0 ? hostileSpawnTime-time : hostileSpawnTime-(time+20);
		passiveSpawnTime = game.getDifficulty() == 0 ? passiveSpawnTime-time : passiveSpawnTime-(time+20);
	}
	
	private void spawnItem(ID id) {
		handler.addObject(new ItemObject(r.nextInt(Game.WIDTH - 50), r.nextInt(Game.HEIGHT - 50), id, handler));
	}

	public void resetTimer() {
		this.passiveTimer = 0;
		this.hostileTimer = 0;
		this.patternTimer = 0;
		this.bulletTimer = 0;
		this.passiveSpawnTime = 150;
	}
}
