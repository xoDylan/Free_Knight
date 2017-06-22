package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import Graphics.Background;
import Graphics.ImageLoader;

public class GameOverState extends GameState {
	
	private Background bg;
	
	private int currentchoice = 0;
	private String[] menu = {"Restart", "Exit"};
	
	private BufferedImage title;
	
	private Font normalFont;
	private Font selectedFont;
	private Color normalColor;
	private Color selectedColor;
	
	
	public GameOverState(GameStateManager gsm) {
		
		this.gsm = gsm;
		
		try {
			
			bg = new Background("/Background/BG1.png", 1);
			bg.setVector(-0.1, 0);
			
			title = ImageLoader.loadImage("/PlayerUI/gameover.png");
			
			normalFont = new Font("Arial", Font.PLAIN, 50);
			normalColor = new Color(54, 55, 73);
			selectedFont = new Font("Arial", Font.PLAIN, 70);
			selectedColor = new Color(204, 46,46);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	public void init() {
		
	}

	public void update() {

		bg.update();
		
	}

	public void draw(Graphics2D g) {

		bg.draw(g);
		
		g.drawImage(title, 0, 200, null);
		
		for (int i = 0; i < menu.length; i++) {
			if (i == currentchoice) {
				if (i == 0) {
					g.setFont(selectedFont);
					g.setColor(selectedColor);
					g.drawString(menu[i], 400, 400 + 80 * i);
				}
				else if (i == 1) {
					g.setFont(selectedFont);
					g.setColor(selectedColor);
					g.drawString(menu[i], 455, 400 + 80 * i);
				}
			}
			else {
				if (i == 0) {
					g.setFont(normalFont);
					g.setColor(normalColor);
					g.drawString(menu[i], 435, 400 + 80 * i);
				}
				else if (i == 1) {
					g.setFont(normalFont);
					g.setColor(normalColor);
					g.drawString(menu[i], 470, 400 + 80 * i);
				}
				
			}
		}
		
	}

	private void select() {
		if (currentchoice == 0) {
			gsm.setState(GameStateManager.LEVELONE);
		}
		if (currentchoice == 1) {
			System.exit(0);
		}
	}
	
	public void keyPressed(int k) {

		if (k == KeyEvent.VK_ENTER) {
			select();
		}
		if (k == KeyEvent.VK_UP) {
			currentchoice--;
			if (currentchoice < 0) {
				currentchoice = menu.length - 1;
			}
		}
		if (k == KeyEvent.VK_DOWN) {
			currentchoice++;
			if (currentchoice == menu.length) {
				currentchoice = 0;
			}
		}
		
	}

	public void keyReleased(int k) {
		
	}

}
