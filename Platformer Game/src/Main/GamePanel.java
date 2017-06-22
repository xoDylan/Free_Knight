package Main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import GameState.GameStateManager;
import Graphics.Assets;


public class GamePanel extends JPanel implements Runnable, KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5277261652065391019L;
	
	// Game Dimension
	public static final int Width = 1024;
	public static final int Height = 768;
	
	// Game Thread
	private Thread thread;
	private boolean running;
	
	// Game Image
	private BufferedImage image;
	private Graphics2D g;
	
	// Game State Manager
	private GameStateManager gsm;
	
	public GamePanel() {
		super();
		setPreferredSize(new Dimension(Width, Height));
		setFocusable(true);
		requestFocus();
	}


	private void init() {
		image = new BufferedImage(Width, Height, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		Assets.init();
		
		running = true;
		
		gsm = new GameStateManager();
	}
	
	public void run() {

		init();
		
		int fps = 60;
		double targetTime = 1000000000 / fps;
		double accTime = 0;
		long now;
		long lastTime = System.nanoTime();
		
		while (running) {
			
			now = System.nanoTime();
			accTime += (now - lastTime) / targetTime;
			lastTime = now;
			
			if (accTime >= 1) {
				update();
				draw();
				drawToScreen();
				accTime--;
			}
			
		}
	}
	
	private void update() {
		gsm.update();
		
	}
	
	private void draw() {
		gsm.draw(g);
	}
	
	private void drawToScreen() {
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0, null);
		g2.dispose();
	}
	
	public void addNotify() {
		super.addNotify();
		if(thread == null) {
			thread = new Thread(this);
			addKeyListener(this);
			thread.start();
		}
	}


	public void keyPressed(KeyEvent key) {
		gsm.keyPressed(key.getKeyCode());
		
	}


	public void keyReleased(KeyEvent key) {
		gsm.keyReleased(key.getKeyCode());
		
	}


	public void keyTyped(KeyEvent key) {
		
	}
}

	


