package Entity;

import java.awt.Graphics2D;

import WorldMap.WorldMap;

public class GameItem extends Entity {
	
	protected int value;
	protected boolean contacted;
	
	protected static final int ROTATE = 0;
	protected static final int CONTACT = 1;

	public GameItem(WorldMap wm) {
		super(wm);
	}
	
	public void contactPlayer(Player p) {
		
	}
	
	public void update() {
		
	}
	
	public void draw(Graphics2D g) {
		setMapPos();
		g.drawImage(animation.getImage(), (int) (x + xmap), (int) (y + ymap), null);
	}

}
