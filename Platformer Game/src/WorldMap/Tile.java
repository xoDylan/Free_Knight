package WorldMap;

import java.awt.image.BufferedImage;

public class Tile {

	private BufferedImage image;
	private int type;
	
	public static final int Normal = 0;
	public static final int Solid = 1;
	public static final int FinishBoard = 2;
	
	public Tile(BufferedImage image, int type) {
		this.image = image;
		this.type = type;
	}
	
	public BufferedImage getImage() {
		return image;
	}
	
	public int getType() {
		return type;
	}
}
