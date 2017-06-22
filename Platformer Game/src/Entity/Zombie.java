package Entity;

import java.awt.Graphics2D;

import Graphics.Animation;
import Graphics.Assets;
import WorldMap.WorldMap;

public class Zombie extends Enemy {
	
	// animations
	private static final int DEAD = 0;
	private static final int WALK = 1;

	public Zombie(WorldMap wm) {
		super(wm);
		
		moveSpeed = 0.3;
		maxSpeed = 1.8;
		fallSpeed = 0.56;
		maxFallSpeed = 9.24;
		
		width = 128;
		height = 128;
		cwidth = 96;
		cheight = 96;
		
		health = 2;
		damage = 1;
		
		facingRight = true;
		
		animation = new Animation();
		currentAction = WALK;
		animation.setFrames(Assets.enemy.get(WALK));
		animation.setDelay(100);
		
		right = true;
		
	}
	
	private void getNextPos() {
		
		if (left) {
			dx -= moveSpeed;
			if (dx < -maxSpeed) {
				dx = -maxSpeed;
			}
		}
		else if (right) {
			dx += moveSpeed;
			if (dx > maxSpeed) {
				dx = maxSpeed;
			}
		}
		
		if (falling) {
			dy += fallSpeed;
		}
		
	}
	
	public void attackPlayer(Player p) {
		
		if (facingRight) {
			
			if (p.getx() > x - width / 2 &&
				p.getx() < x + width / 2 &&
				p.gety() > y - height / 2 &&
				p.gety() < y + height / 2
				) {
				p.hit(damage);
			}
		}
		else {
			if (p.getx() < x + width / 2 &&
				p.getx() > x - width / 2&&
				p.gety() > y - height / 2 &&
				p.gety() < y + height / 2
				) {
					p.hit(damage);
			}
		}
		
	}
	
	public void update() {
		
		getNextPos();
		checkWorldMapCollision();
		setPos(xnew, ynew);
		
		if (currentAction == DEAD) {
			if (animation.hasPlayedOnce()){
				shouldKill = true;
			}
		}
		
		if (dead) {
			if (currentAction != DEAD) {
				currentAction = DEAD;
				animation.setFrames(Assets.enemy.get(DEAD));
				animation.setDelay(150);
			}
		}
		else if (right && dx == 0) {
			right = false;
			left = true;
			facingRight = false;
		}
		else if (left && dx == 0) {
			left = false;
			right = true;
			facingRight = true;
		}
		
		if (!shouldKill) {
			animation.update();
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

			g.drawImage(animation.getImage(), (int) (x + xmap - width/2), (int) (y + ymap - height / 2 - 12), null);
		}
		else {
			g.drawImage(animation.getImage(), (int) (x + xmap + width/2), (int) (y + ymap - height / 2 - 12), -width, height, null);
		}
		
	}

}
