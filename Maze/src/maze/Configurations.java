package maze;

public class Configurations {

	public final int S = 30;
	public final int H = (600 / S) * S;
	public final int W = (600 / S) * S;
	public int pausespeed = 100;

	public int direction;
	public final int padding = 80;
	public int small = S / 5;
	
	public int score = 0;

	private boolean maze_geneated = false;
	public boolean run_game = true;
	private boolean start_new_game = true;
	public boolean win_game = false;
	
	public void mazeIsGenerated() {
		maze_geneated = true;
	}
	public boolean isMazeGenerated() {
		return maze_geneated;
	}
	public boolean canStartNewGame() {
		return start_new_game;
	}
	public void newGameStarted() {
		start_new_game = false;
		win_game = false;
	}

	public void stopGame() {
		run_game = false;
	}

	public void gameWon() {
		win_game = true;
	}
	
	public void increaseScore() {
		score++;
	}
}
