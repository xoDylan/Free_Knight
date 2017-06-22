package GameState;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import Entity.Coin;
import Entity.Enemy;
import Entity.Health;
import Entity.Player;
import Entity.Zombie;
import Graphics.Background;
import Graphics.PlayerUI;
import Main.GamePanel;
import WorldMap.WorldMap;

public class LevelOne extends GameState {
	
	private WorldMap wMap;
	private Background bg;
	private PlayerUI pui;
	
	private Player player;
	
	private ArrayList<Enemy> enemies;
	private ArrayList<Coin> coins;
	private ArrayList<Health> health;

	public LevelOne(GameStateManager gsm) {
		this.gsm = gsm;
		init();
	}
	
	public void init() {

		if (wMap == null) {
			wMap = new WorldMap(96);
		}
		wMap.loadTileset();
		wMap.loadMap("/Map/map.txt");
		wMap.setPos(0, 0);
		
		if (bg == null) {
			bg = new Background("/Background/BG1.png", 0.1);
		}
		
		player = Player.getInstance(wMap);
		player.init();
		
		pui = new PlayerUI(player);
		
		generateEnemies();
		
		generateCoins();
		
		generateHealth();
		
	}

	public void update() {
		
		player.update();
		
		wMap.setPos(GamePanel.Width / 2 - player.getx(), GamePanel.Height / 2 - player.gety());
		bg.setPos(wMap.getx(), wMap.gety());
		
		if (player.levelCleared()) {
			gsm.setState(GameStateManager.GAMEWONSTATE);
		}
		
		if (!player.isDead()) {
			player.attackEnemy(enemies);
		}
		if (player.shouldKill()) {
			gsm.setState(GameStateManager.GAMEOVERSTATE);
		}
		
		for (int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);
			e.update();
			if (!e.isDead()){
				e.attackPlayer(player);
			}
			if (e.shouldKill()) {
				enemies.remove(i);
				i--;
			}
		}
		
		for (int i = 0; i < coins.size(); i++) {
			Coin c = coins.get(i);
			c.update();
			c.contactPlayer(player);
			if (c.shouldKill()) {
				coins.remove(i);
				i--;
			}
		}
		
		for (int i = 0; i < health.size(); i++) {
			Health h = health.get(i);
			h.update();
			h.contactPlayer(player);
			if (h.shouldKill()) {
				health.remove(i);
				i--;
			}
		}
		
	}

	public void draw(Graphics2D g) {

		bg.draw(g);
		
		wMap.draw(g);
		
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).draw(g);
		}
		
		for (int i = 0; i < coins.size(); i++) {
			coins.get(i).draw(g);
		}
		
		for (int i = 0; i < health.size(); i++) {
			health.get(i).draw(g);
		}
		
		player.draw(g);
		
		pui.draw(g);

	}

	public void keyPressed(int k) {

		if (k == KeyEvent.VK_LEFT) {
			player.setLeft(true);
		}
		if (k == KeyEvent.VK_RIGHT) {
			player.setRight(true);
		}
		if (k == KeyEvent.VK_SPACE) {
			player.setJumping(true);
		}
		if (k == KeyEvent.VK_X) {
			player.setAttacking();
		}
		if (k == KeyEvent.VK_SHIFT) {
			player.setRunning(true);
		}
		
	}

	public void keyReleased(int k) {
		
		if (k == KeyEvent.VK_LEFT) {
			player.setLeft(false);
		}
		if (k == KeyEvent.VK_RIGHT) {
			player.setRight(false);
		}
		if (k == KeyEvent.VK_SPACE) {
			player.setJumping(false);
		}
		if (k == KeyEvent.VK_SHIFT) {
			player.setRunning(false);
		}
		
	}
	
	public void generateEnemies() {
		enemies = new ArrayList<Enemy>();
		
		Zombie z;
		Point[] ps = new Point[] {
				new Point(768, 576),
				new Point(864, 576),
				new Point(2112, 576),
				new Point(2304, 576),
				new Point(2496, 576),
				new Point(2688, 576),
				new Point(2880, 576),
				new Point(6720, 384),
				new Point(8640, 576),
				new Point(8928, 576),
				new Point(9216, 576),
				new Point(9504, 576),
				new Point(9600, 192),
				new Point(9792, 576),
				new Point(10080, 576)
				
		};
		for (int i = 0; i < ps.length; i++) {
			z = new Zombie(wMap);
			z.setPos(ps[i].x, ps[i].y);
			enemies.add(z);
		}
	}
	
	public void generateCoins() {
		coins = new ArrayList<Coin>();
		
		Coin c;
		Point[] ps = new Point[] {
				new Point(480, 288),
				new Point(576, 288),
				new Point(672, 288),
				new Point(1056, 96),
				new Point(1056, 192),
				new Point(1056, 288),
				new Point(2016, 192),
				new Point(2112, 192),
				new Point(2208, 96),
				new Point(2304, 96),
				new Point(2400, 96),
				new Point(2496, 192),
				new Point(2592, 192),
				new Point(3936, 576),
				new Point(4032, 576),
				new Point(4128, 576),
				new Point(4608, 384),
				new Point(4608, 480),
				new Point(4608, 576),
				new Point(6144, 96),
				new Point(6144, 192),
				new Point(6144, 288),
				new Point(7968, 288),
				new Point(8064, 288),
				new Point(10368, 288),
				new Point(10368, 384),
				new Point(10368, 480)
		};
		for (int i = 0; i < ps.length; i++) {
			c = new Coin(wMap);
			c.setPos(ps[i].x, ps[i].y);
			coins.add(c);
		}
	}
	
	public void generateHealth() {
		health = new ArrayList<Health>();
		
		Health h;
		Point[] ps = new Point[] {
				new Point(192, 192),
				new Point(3840, 576)
		};
		for (int i = 0; i < ps.length; i++) {
			h = new Health(wMap);
			h.setPos(ps[i].x, ps[i].y);
			health.add(h);
		}
	}
	

}
