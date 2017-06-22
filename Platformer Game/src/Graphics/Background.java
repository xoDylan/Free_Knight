package Graphics;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Main.GamePanel;

public class Background {
	
	private BufferedImage image; 
	
	private double x, y, dx, dy, moveScale;
	
	public Background(String s, double ms) {
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream(s));
			moveScale = ms;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void setPos(double x, double y) {
		
		this.x = (x * moveScale) % GamePanel.Width;
		this.y = (y * moveScale) % GamePanel.Height;
		
	}
	
	public void setVector(double dx, double dy) {
		
		this.dx = dx;
		this.dy = dy;
		
	}
	
	public void update() {
		
		x += dx;
		y += dy;
		
	}
	
	public void draw(Graphics2D g) {
		
		g.drawImage(image, (int)x, (int)y, null);
		
		if (x < 0) {
			g.drawImage(image, (int)x + GamePanel.Width, (int)y, null);
		}
		
		if (x > 0) {
			g.drawImage(image, (int)x - GamePanel.Width, (int)y, null);
		}
		
	}

}
