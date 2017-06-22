package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import Graphics.Background;

public class ControlState extends GameState {
	
	private Background bg;
	
	private String title;
	private Font titleFont;
	private Color titleColor;
	
	private Font fontOne;
	private Font fontTwo;
	private Color colorOne;
	private Color colorTwo;
	
	public ControlState(GameStateManager gsm) {
		
		this.gsm = gsm;
		
		try {
			
			bg = new Background("/Background/BG1.png", 1);
			bg.setVector(-0.1, 0);
			
			title = "Controls";
			titleFont = new Font("Georgia", Font.PLAIN, 95);
			titleColor = new Color(242, 225, 101);
			
			fontOne = new Font("Arial", Font.PLAIN, 50);
			colorOne = new Color(54, 55, 73);
			fontTwo = new Font("Arial", Font.PLAIN, 50);
			colorTwo = new Color(204, 46,46);
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}


	public void init() {
		
	}

	public void update() {
		
		bg.update();
		
	}

	@Override
	public void draw(Graphics2D g) {

		bg.draw(g);
		
		g.setColor(titleColor);
		g.setFont(titleFont);
		g.drawString(title, 320, 100);
		
		g.setColor(colorOne);
		g.setFont(fontOne);
		g.drawString("Move:", 100, 250);
		g.drawString("Run:", 100, 350);
		g.drawString("Jump:", 100, 450);
		g.drawString("Attack:", 100, 550);
		g.drawString("Press ", 270, 700);
		g.drawString("To Return", 520, 700);
		
		g.setColor(colorTwo);
		g.setFont(fontTwo);
		g.drawString("¡û ¡ú", 310, 250);
		g.drawString("(hold)Shift + ¡û ¡ú", 310, 350);
		g.drawString("Space", 310, 450);
		g.drawString("X", 310, 550);
		g.drawString("Esc ", 420, 700);
		
	}

	public void keyPressed(int k) {

		if (k == KeyEvent.VK_ESCAPE) {
			gsm.setState(GameStateManager.MENUSTATE);
		}
		
	}

	public void keyReleased(int k) {
		
	}

}
