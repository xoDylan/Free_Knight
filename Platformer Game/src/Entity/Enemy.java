package Entity;

import java.awt.Graphics2D;

import WorldMap.WorldMap;

public class Enemy extends Entity {
	
	protected int health;
	protected boolean dead;
	protected int damage;
	protected boolean flinching;
	protected long flinchTime;

	public Enemy(WorldMap wm) {
		super(wm);
	}

	public boolean isDead() {
		return dead;
	}
	
	public int getDamage() {
		return damage;
	}
	
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
		
		// enemy changes direction when hit
		if (facingRight && !dead) {
			facingRight = false;
			right = false;
			left = true;
		}
		else if (!facingRight && !dead) {
			facingRight = true;
			left = false;
			right = true;
		}
		
	}
	
	public void update() {}
	
	public void draw(Graphics2D g) {}

	public void attackPlayer(Player player) {}
	
	
}
