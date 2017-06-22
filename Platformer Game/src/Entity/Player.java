package Entity;

import java.awt.Graphics2D;
import java.util.ArrayList;

import Graphics.Animation;
import Graphics.Assets;
import WorldMap.Tile;
import WorldMap.WorldMap;

public class Player extends Entity {
	
	private static Player instance;
	
	private int deadWidth;
	private int deadHeight;
	private int attackWidth;
	private int attackHeight;

	private int health;
	private int coinsCollected;
	private boolean dead;
	private boolean flinching;
	private long flinchTime;
	private boolean levelCleared;
	
	// attack
	private boolean attacking;
	private int attackDamage;
	private int attackRange;
	
	// timer
	private long gameTimer;
	private long startTime;
	
	// animations
	private static final int ATTACK = 0;
	private static final int DEAD = 1;
	private static final int IDLE = 2;
	private static final int JUMP = 3;
	private static final int FALL = 4;
	private static final int RUN = 5;
	private static final int WALK = 6;
	
	private Player(WorldMap wm) {
		super(wm);

		x = 300;
		y = 500;
		width = 96;
		height = 128;
		deadWidth = 183;
		deadHeight = 138;
		attackWidth = 192;
		attackHeight = 128;
		cwidth = 80;
		cheight = 96;
		
		moveSpeed = 0.5;
		runSpeed = 0.8;
		maxSpeed = 3.6;
		maxRunSpeed = 6.0;
		stopSpeed = 0.4;
		fallSpeed = 0.56;
		maxFallSpeed = 9.24;
		jumpStart = -15.6;
		stopJumpSpeed = 0.3;
		
		facingRight = true;
		
		health = 3;
		coinsCollected = 0;
		attackDamage = 1;
		attackRange = 96;
		
		gameTimer = 0;
		startTime = System.nanoTime();
		
		animation = new Animation();
		currentAction = IDLE;
		animation.setFrames(Assets.player.get(IDLE));
		animation.setDelay(40);
		
	}
	
	public static Player getInstance(WorldMap wm) {
		if (instance == null) {
			instance = new Player(wm);
		}
		return instance;
	}
	
	public void init() {
		
		levelCleared = false;
		shouldKill = false;
		dead = false;
		
		x = 300;
		y = 500;
		width = 96;
		height = 128;
		deadWidth = 183;
		deadHeight = 138;
		attackWidth = 192;
		attackHeight = 128;
		cwidth = 80;
		cheight = 96;
		
		moveSpeed = 0.5;
		runSpeed = 0.8;
		maxSpeed = 3.6;
		maxRunSpeed = 6.0;
		stopSpeed = 0.4;
		fallSpeed = 0.56;
		maxFallSpeed = 9.24;
		jumpStart = -15.6;
		stopJumpSpeed = 0.3;
		
		facingRight = true;
		
		health = 3;
		coinsCollected = 0;
		attackDamage = 1;
		attackRange = 96;
		
		gameTimer = 0;
		startTime = System.nanoTime();
		
		animation = new Animation();
		currentAction = IDLE;
		animation.setFrames(Assets.player.get(IDLE));
		animation.setDelay(40);
	}
	
	public int getHealth() {
		return health;
	}
	
	public int getCoinsCollected() {
		return coinsCollected;
	}
	
	public int getFinishTime() {
		if (!levelCleared) {
			gameTimer = (System.nanoTime() - startTime) / 1000000000;
		}
		return (int)gameTimer;
	}
	
	public boolean isDead() {
		return dead;
	}
	
	public boolean levelCleared() {
		return levelCleared;
	}
	
	public void setLevelClear(boolean b) {
		levelCleared = b;
	}
	
	public void setAttacking() {
		attacking = true;
	}
	
	public void getNextPos() {
		if (dead) {
			return;
		}
		if (left) {
			if (running) {
				dx -= runSpeed;
				if (dx < -maxRunSpeed) {
					dx = -maxRunSpeed;
				}
			}
			else {
				dx -= moveSpeed;
				if (dx < -maxSpeed) {
					dx = -maxSpeed;
				}
			}
		}
		else if (right) {
			if (running) {
				dx += runSpeed;
				if (dx > maxRunSpeed) {
					dx = maxRunSpeed;
				}
			}
			else {
				dx += moveSpeed;
				if (dx > maxSpeed) {
					dx = maxSpeed;
				}
			}
		}
		else {
			if (dx > 0) {
				dx -= stopSpeed;
				if (dx < 0) {
					dx = 0;
				}
			}
			else if (dx < 0) {
				dx += stopSpeed;
				if (dx > 0) {
					dx = 0;
				}
			}
		}
		
		// cannot attack while moving
		if (currentAction == ATTACK && !(jumping || falling)) {
			dx = 0;
		}
		
		if (jumping && !falling) {
			dy = jumpStart;
			falling = true;
		}
		
		if (falling) {

			dy += fallSpeed;
			if (dy > 0) {
				dy += fallSpeed;
				jumping = false;
			}
			if (dy < 0 && !jumping) {
				dy += stopJumpSpeed;
			}
			if (dy > maxFallSpeed) {
				dy = maxFallSpeed;
			}

		}
	}
	
	// hit by enemy
	public void hit(int damage) {
		if (dead || flinching) {
			return;
		}
		health -= damage;
		if (health < 0) {
			health = 0;
		}
		if (health == 0) {
			right = false;
			left = false;
			dx = 0;
			dead = true;
		}
		if (!flinching) {
			flinching = true;
			flinchTime = System.nanoTime();
		}
	}
	
	// collect coin
	public void contactCoin(int value) {
		coinsCollected += value;
		
	}
	
	// collect health
	public void contactHealth(int value) {
		health += value;
	}
	
	public void attackEnemy(ArrayList<Enemy> enemies) {

		for (int i = 0; i < enemies.size(); i++) {
			if (attacking) {
				Enemy e = enemies.get(i);
				if (facingRight) {
					
					if (e.getx() > x &&
						e.getx() < x + attackRange &&
						e.gety() > y - height / 2 &&
						e.gety() < y + height / 2
						) {
						e.hit(attackDamage);
					}
				}
				else {
					if (e.getx() < x &&
						e.getx() > x - attackRange &&
						e.gety() > y - height / 2 &&
						e.gety() < y + height / 2
						) {
							e.hit(attackDamage);
					}
				}
			}
		}
		
	}
	
	public void checkLevelClear() {
		
		int colmin = (int)(x - cwidth / 2) / tilesize;
		int colmax = (int)(x + cwidth / 2 - 1) / tilesize;
		int rowmin = (int)(y - cheight / 2) / tilesize;
		int rowmax = (int)(y + cheight / 2 - 1) / tilesize;
		
		int tl = wMap.getType(rowmin, colmin);
		int tr = wMap.getType(rowmin, colmax);
		int bl = wMap.getType(rowmax, colmin);
		int br = wMap.getType(rowmax, colmax);
		
		// player clears the level if get to the finish board
		if (tl == Tile.FinishBoard || tr == Tile.FinishBoard || bl == Tile.FinishBoard || br == Tile.FinishBoard) {
			setLevelClear(true);
			gameTimer = (System.nanoTime() - startTime) / 1000000000;
		}
		
	}
	
	public void update() {
		
		// position
		getNextPos();
		checkWorldMapCollision();
		setPos(xnew, ynew);
		
		// check if player clears the level
		if (!levelCleared()) {
			checkLevelClear();
		}
		
		// check if player falls out of screen, if it does, munus one health
		if (y > 672) {
			hit(1);
			if (!dead) {
				setPos(1344, 576);
			}
			else {
				shouldKill = true;
			}
		}
		
		// set animation
		if (currentAction == DEAD) {
			if (animation.hasPlayedOnce()) {
				shouldKill = true;
			}
		}
		
		if (currentAction == ATTACK) {
			if (animation.hasPlayedOnce()) {
				attacking = false;
			}
		}
		
		if (dead) {
			if (currentAction != DEAD) {
				currentAction = DEAD;
				animation.setFrames(Assets.player.get(DEAD));
				animation.setDelay(150);
			}
		}
		else if (attacking) {
			if (currentAction != ATTACK) {
				currentAction = ATTACK;
				animation.setFrames(Assets.player.get(ATTACK));
				animation.setDelay(20);
				width = 96;
				height = 128;
			}
		}
		else if (dy > 0) {
			if (falling) {
				if (currentAction != FALL) {
					currentAction = FALL;
					animation.setFrames(Assets.player.get(FALL));
					animation.setDelay(100);
					width = 96;
					height = 128;
				}
			}
		}
		else if (dy < 0) {
			if (jumping) {
				if (currentAction != JUMP) {
					currentAction = JUMP;
					animation.setFrames(Assets.player.get(JUMP));
					animation.setDelay(150);
					width = 106;
					height = 128;
				}
			}
		}
		else if (left || right) {
			if (running) {
				if (currentAction != RUN) {
					currentAction = RUN;
					animation.setFrames(Assets.player.get(RUN));
					animation.setDelay(40);
					width = 96;
					height = 128;
				}
			}
			else if (currentAction != WALK) {
				currentAction = WALK;
				animation.setFrames(Assets.player.get(WALK));
				animation.setDelay(40);
				width = 96;
				height = 128;
			}
		}
		else {
			if (currentAction != IDLE) {
				currentAction = IDLE;
				animation.setFrames(Assets.player.get(IDLE));
				animation.setDelay(40);
				width = 96;
				height = 128;
			}
		}
		
		animation.update();
		
		if (currentAction != ATTACK && currentAction != DEAD) {
			if (right) {
				facingRight = true;
			}
			if (left) {
				facingRight = false;
			}
		}
		
	}
	
	public void draw(Graphics2D g) {
		
		setMapPos();
		
		if (flinching && !dead) {
			long elapsed = (System.nanoTime() - flinchTime) / 1000000;
			if ((System.nanoTime() - flinchTime) / 1000000000 >= 1) {
				flinching = false;
			}
			if (elapsed / 100 % 4 == 0) {
				return;
			}
		}
		
		if (facingRight) {
			if (currentAction == DEAD) {
				g.drawImage(animation.getImage(), (int) (x + xmap - deadWidth / 2), (int) (y + ymap - deadHeight / 2), null);
			}
			else if (currentAction == ATTACK) {
				g.drawImage(animation.getImage(), (int) (x + xmap - attackWidth / 2 + 48), (int) (y + ymap - attackHeight / 2 - 10), null);
			}
			else {
				g.drawImage(animation.getImage(), (int) (x + xmap - width / 2), (int) (y + ymap - height / 2 - 10), null);
			}
		}
		else {
			if (currentAction == DEAD) {
				g.drawImage(animation.getImage(), (int) (x + xmap + deadWidth / 2), (int) (y + ymap - deadHeight / 2), -deadWidth, deadHeight, null);
			}
			else if (currentAction == ATTACK) {
				g.drawImage(animation.getImage(), (int) (x + xmap + attackWidth / 2 - 48), (int) (y + ymap - attackHeight / 2 - 10), -attackWidth, attackHeight, null);
			}
			else {
				g.drawImage(animation.getImage(), (int) (x + xmap + width / 2), (int) (y + ymap - height / 2 - 10), -width, height, null);
			}
		}
		
	}

}
