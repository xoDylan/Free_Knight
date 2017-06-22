package Entity;

import java.awt.Graphics2D;

import Graphics.Animation;
import Graphics.Assets;
import WorldMap.WorldMap;

public class Health extends GameItem {

	public Health(WorldMap wm) {
		super(wm);

		width = 96;
		height = 96;
		cwidth = 96;
		cheight = 96;
		
		value = 1;
		contacted = false;
		
		animation = new Animation();
		currentAction = ROTATE;
		animation.setFrames(Assets.health);
		animation.setDelay(75);
	}
	
	public void contactPlayer(Player p) {
		if (!contacted) {
			if (p.getx() > x &&
					p.getx() < x + width &&
					p.gety() > y &&
					p.gety() < y + height
					) {
				contacted = true;
				p.contactHealth(value);
			}
		}
	}
	
	public void update() {
		if (currentAction == CONTACT) {
			if (animation.hasPlayedOnce()) {
				shouldKill = true;
			}
		}
		
		if (contacted) {
			if (currentAction != CONTACT) {
				currentAction = CONTACT;
				animation.setFrames(Assets.healthEffect);
				animation.setDelay(50);
			}
		}
		
		animation.update();
	}
	
	public void draw(Graphics2D g) {
		super.draw(g);
	}

}
