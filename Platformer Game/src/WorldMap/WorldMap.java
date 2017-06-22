package WorldMap;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import Graphics.Assets;
import Main.GamePanel;

public class WorldMap {
	
	// position
	private double x;
	private double y;
	
	// bounds
	private int xmin;
	private int ymin;
	private int xmax;
	private int ymax;
	
	private double tween;
	
	// world map
	private int[][] wMap;
	private int tilesize;
	private int numRows;
	private int numCols;
	private int width;
	private int height;
	
	// tiles
	private Tile[] tiles;
	
	// draw
	private int rowOffset;
	private int colOffset;
	private int numRowsToDraw;
	private int numColsToDraw;
	
	public WorldMap(int tilesize) {
		this.tilesize = tilesize;
		numRowsToDraw = GamePanel.Height / tilesize;
		numColsToDraw = GamePanel.Width / tilesize + 2;
		tween = 0.07;
	}
	
	public void loadTileset(){
		tiles = new Tile[31];
		for (int i = 1; i < 31; i++) {
			if (i == 30) {
				tiles[i] = new Tile(Assets.tilesets[i - 1], Tile.FinishBoard);
			}
			else if (i >= 19) {
				tiles[i] = new Tile(Assets.tilesets[i - 1], Tile.Normal);
			}
			else {
				tiles[i] = new Tile(Assets.tilesets[i - 1], Tile.Solid);
			}
		}
	}
	
	public void loadMap(String s) {
		
		try {
			InputStream in = getClass().getResourceAsStream(s);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			numRows = Integer.parseInt(br.readLine());
			numCols = Integer.parseInt(br.readLine());
			wMap = new int[numRows][numCols];
			width = numCols * tilesize;
			height = numRows * tilesize;
			
			xmin = GamePanel.Width - width + 96;
			xmax = -96;
			ymin = GamePanel.Height - height;
			ymax = 0;
			
			String delims = "\\s+";
			for (int row = 0; row < numRows; row++) {
				String line = br.readLine();
				String[] tokens = line.split(delims);
				for (int col = 0; col < numCols; col++) {
					wMap[row][col] = Integer.parseInt(tokens[col]);
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int getTileSize() {
		return tilesize;
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

	public int getType(int row, int col) {
		if (wMap[row][col] == 0) {
			return 0;
		}
		else {
			return tiles[wMap[row][col]].getType();
		}
	}
	
	public void setPos(double x, double y) {
		this.x += (x - this.x) * tween;
		this.y += (y - this.y) * tween;
		
		checkBounds();
		
		colOffset = (int)-this.x / tilesize;
		rowOffset = (int)-this.y / tilesize;
	}
	
	private void checkBounds() {
		if (x < xmin) {
			x = xmin;
		}
		if (x > xmax) {
			x = xmax;
		}
		if (y < ymin) {
			y = ymin;
		}
		if (y > ymax) {
			y = ymax;
		}
	}
	
	public void draw(Graphics2D g) {
		
		for (int row = rowOffset; row < rowOffset + numRowsToDraw; row++) {
			
			if (row >= numRows) {
				break;
			}
			
			for (int col = colOffset; col < colOffset + numColsToDraw; col++) {
				
				if (col >= numCols) {
					break;
				}
				
				if (wMap[row][col] == 0) {
					continue;
				}
				
				g.drawImage(tiles[wMap[row][col]].getImage(), (int)x + col * tilesize, (int)y + row * tilesize, null);
				
			}
		}
		
	}
}
