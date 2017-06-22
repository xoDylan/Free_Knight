package Graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import Entity.Player;

public class PlayerUI {
	
	private Player player;
	
	public PlayerUI(Player p) {
		
		player = p;
		
	}
	
	public void draw(Graphics2D g) {
		
		for (int i = 0; i < player.getHealth(); i++) {
			g.drawImage(Assets.heart, i * 50, 0, null);
		}
		
		g.drawImage(Assets.coinUI, 0, 50, null);
		g.setColor(Color.BLACK);
		g.setFont(new Font("Verdana", Font.PLAIN, 20));
		g.drawString("X", 70, 85);
		g.drawString(Integer.toString(player.getFinishTime()) + " Secs", 925, 50);
		g.setFont(new Font("Verdana", Font.PLAIN, 40));
		g.setColor(Color.RED);
		g.drawString(Integer.toString(player.getCoinsCollected()), 106, 93);
		
	}

}
