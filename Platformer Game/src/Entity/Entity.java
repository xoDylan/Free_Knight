package Entity;

import Graphics.Animation;
import WorldMap.Tile;
import WorldMap.WorldMap;

public abstract class Entity {

	protected WorldMap wMap;
	protected int tilesize;
	protected double xmap;
	protected double ymap;
	
	protected double x;
	protected double y;
	protected double dx;
	protected double dy;
	
	protected int width;
	protected int height;
	
	// collision detection
	protected int cwidth;
	protected int cheight;
	protected int currRow;
	protected int currCol;
	protected double xdest;
	protected double ydest;
	protected double xnew;
	protected double ynew;
	protected boolean topLeft;
	protected boolean topRight;
	protected boolean botLeft;
	protected boolean botRight;
	
	// animation
	protected Animation animation;
	protected int currentAction;
	protected int previousAction;
	protected boolean facingRight;
	
	// action
	protected boolean left;
	protected boolean right;
	protected boolean up;
	protected boolean down;
	protected boolean jumping;
	protected boolean falling;
	protected boolean running;
	
	// living state
	protected boolean shouldKill;
	
	// movement
	protected double moveSpeed;
	protected double maxSpeed;
	protected double runSpeed;
	protected double maxRunSpeed;
	protected double stopSpeed;
	protected double fallSpeed;
	protected double maxFallSpeed;
	protected double jumpStart;
	protected double stopJumpSpeed;
	
	public Entity(WorldMap wm) {
		wMap = wm;
		tilesize = wm.getTileSize();
	}
	
	public void calculateCorners(double x, double y) {
		int colmin = (int)(x - cwidth / 2) / tilesize;
		int colmax = (int)(x + cwidth / 2 - 1) / tilesize;
		int rowmin = (int)(y - cheight / 2) / tilesize;
		int rowmax = (int)(y + cheight / 2 - 1) / tilesize;
		
		int tl = wMap.getType(rowmin, colmin);
		int tr = wMap.getType(rowmin, colmax);
		int bl = wMap.getType(rowmax, colmin);
		int br = wMap.getType(rowmax, colmax);
		
		topLeft = tl == Tile.Solid;
		topRight = tr == Tile.Solid;
		botLeft = bl == Tile.Solid;
		botRight = br == Tile.Solid;
		
	}
	
	public void checkWorldMapCollision() {
		
		currCol = (int)x / tilesize;
		currRow = (int)y / tilesize;
		xdest = x + dx;
		ydest = y + dy;
		xnew = x;
		ynew = y;
		
		calculateCorners(x, ydest);
		if (dy < 0 ) {
			if (topLeft || topRight) {
				dy = 0;
				ynew = currRow * tilesize + cheight / 2;
			}
			else {
				ynew += dy;
			}
		}
		if (dy > 0) {
			if (botLeft || botRight) {
				dy = 0;
				falling = false;
				ynew = (currRow + 1) * tilesize - cheight / 2;
			}
			else {
				ynew += dy;
			}
		}
		
		calculateCorners(xdest, y);
		if (dx < 0) {
			if (topLeft || botLeft) {
				dx = 0;
				xnew = currCol * tilesize + cwidth / 2;
			}
			else {
				xnew += dx;
			}
		}
		if (dx > 0) {
			if (topRight || botRight) {
				dx = 0;
				xnew = (currCol + 1) * tilesize - cwidth / 2;
			}
			else {
				xnew += dx;
			}
		}
		
		if (!falling) {
			calculateCorners(x, ydest + 1);
			if (!botLeft && !botRight) {
				falling = true;
			}
		}
		
	}
	
	public void setPos(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public void setVector(double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
	}
	
	public void setMapPos() {
		xmap = wMap.getx();
		ymap = wMap.gety();
	}
	
	public void setLeft(boolean b) {
		left = b;
	}
	
	public void setRight(boolean b) {
		right = b;
	}
	
	public void setJumping (boolean b) {
		jumping = b;
	}
	
	public void setRunning (boolean b) {
		running = b;
	}
	
	public int getx() {
		return (int)x;
	}
	
	public int gety() {
		return (int)y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getCWidth() {
		return cwidth;
	}
	
	public int getCHeight() {
		return cheight;
	}
	
	public boolean shouldKill() {
		return shouldKill;
	}
}
