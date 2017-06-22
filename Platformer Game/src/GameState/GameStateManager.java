package GameState;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class GameStateManager {

	private ArrayList<GameState> gameStates;
	private int currentState;
	
	public static final int MENUSTATE = 0;
	public static final int CONTROLSTATE = 1;
	public static final int GAMEOVERSTATE = 2;
	public static final int GAMEWONSTATE = 3;
	public static final int LEVELONE = 4;
	
	public GameStateManager() {
		
		gameStates = new ArrayList<GameState>();
		
		currentState = MENUSTATE;
		gameStates.add(new MenuState(this));
		gameStates.add(new ControlState(this));
		gameStates.add(new GameOverState(this));
		gameStates.add(new GameWonState(this));
		gameStates.add(new LevelOne(this));
		
	}
	
	public void setState(int state) {
		
		currentState = state;
		gameStates.get(currentState).init();
		
	}
	
	public void update() {
		gameStates.get(currentState).update();
	}
	
	public void draw(Graphics2D g) {
		gameStates.get(currentState).draw(g);
	}
	
	public void keyReleased(int k) {
		gameStates.get(currentState).keyReleased(k);
	}
	
	public void keyPressed(int k) {
		gameStates.get(currentState).keyPressed(k);
	}
}
