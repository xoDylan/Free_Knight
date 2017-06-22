package Graphics;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Assets {
	
	public static BufferedImage[] tilesets;
	public static ArrayList<BufferedImage[]> player;
	public static ArrayList<BufferedImage[]> enemy;
	public static BufferedImage heart;
	public static BufferedImage coinUI;
	public static BufferedImage[] coins;
	public static BufferedImage[] health;
	public static BufferedImage[] coinEffect;
	public static BufferedImage[] healthEffect;
	
	public static void init() {
		
		try {
			
			// map tile image
			tilesets = new BufferedImage[30];
			for (int i = 0; i < 30; i++) {
				String p = "/Tilesets/" + Integer.toString(i + 1) + ".png";
				if (i >= 18) {
					p = "/Objects/" + Integer.toString(i - 18) + ".png";
				}
				tilesets[i] = ImageLoader.loadImage(p);
			}
			
			// player ui
			heart = ImageLoader.loadImage("/PlayerUI/heartui.png");
			coinUI = ImageLoader.loadImage("/PlayerUI/coinui.png");
			
			// player animation image
			BufferedImage[] attack, dead, idle, jump, fall, run, walk;
			jump = new BufferedImage[1];
			fall = new BufferedImage[1];
			jump[0] = ImageLoader.loadImage("/Sprites/Player/jump0.png");
			fall[0] = ImageLoader.loadImage("/Sprites/Player/jump1.png");
			
			attack = new BufferedImage[10];
			dead = new BufferedImage[10];
			idle = new BufferedImage[10];
			run = new BufferedImage[10];
			walk = new BufferedImage[10];
			for (int i = 0; i < 10; i++) {
				String p1 = "/Sprites/Player/attack" + Integer.toString(i) + ".png";
				String p2 = "/Sprites/Player/dead" + Integer.toString(i) + ".png";
				String p3 = "/Sprites/Player/idle" + Integer.toString(i) + ".png";
				String p4 = "/Sprites/Player/run" + Integer.toString(i) + ".png";
				String p5 = "/Sprites/Player/walk" + Integer.toString(i) + ".png";
				
				attack[i] = ImageLoader.loadImage(p1);
				dead[i] = ImageLoader.loadImage(p2);
				idle[i] = ImageLoader.loadImage(p3);
				run[i] = ImageLoader.loadImage(p4);
				walk[i] = ImageLoader.loadImage(p5);
			}
			
			player = new ArrayList<BufferedImage[]>();
			player.add(attack);
			player.add(dead);
			player.add(idle);
			player.add(jump);
			player.add(fall);
			player.add(run);
			player.add(walk);
			
			// enemy animation image
			BufferedImage[] eDead = new BufferedImage[10];
			BufferedImage[] eWalk = new BufferedImage[10];
			for (int i = 0; i < 10; i++) {
				String e1 = "/Sprites/Enemy/dead" + Integer.toString(i) + ".png";
				String e2 = "/Sprites/Enemy/walk" + Integer.toString(i) + ".png";
				
				eDead[i] = ImageLoader.loadImage(e1);
				eWalk[i] = ImageLoader.loadImage(e2);
			}
			
			enemy = new ArrayList<BufferedImage[]>();
			enemy.add(eDead);
			enemy.add(eWalk);
			
			// game items
			coins = new BufferedImage[10];
			coinEffect = new BufferedImage[4];
			for (int i = 0; i < 10; i++) {
				String c = "/PlayerUI/coin" + Integer.toString(i) + ".png";
				coins[i] = ImageLoader.loadImage(c);
			}
			for (int i = 0; i < 4; i++) {
				String ce = "/PlayerUI/contact_effect" + Integer.toString(i) + ".png";
				coinEffect[i] = ImageLoader.loadImage(ce);
			}
			
			health = new BufferedImage[1];
			healthEffect = new BufferedImage[29];
			health[0] = ImageLoader.loadImage("/PlayerUI/health0.png");
			for (int i = 0; i < 29; i++) {
				String h = "/PlayerUI/health" + Integer.toString(i) + ".png";
				healthEffect[i] = ImageLoader.loadImage(h);
			}

		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
