package maze.game;

import java.util.Random;

import javax.swing.JFrame;
//import javax.swing.JOptionPane;

import maze.creator.MazeCreator_Recursive_Backtracking;

public class Main {

	private String title = "The Maze Game";
	private Configurations conf = new Configurations();
	private JFrame frame;
	private Controls ctrls;

	public Main() {
		frame = new JFrame(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setAlwaysOnTop(true);
	}

	private void startNewGame() {
		// conf = new Configurations();
		ctrls = new Controls(conf);
		Screen s = new Screen(conf);
		frame.add(s);
		frame.addKeyListener(ctrls);
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);

		MazeCreator_Recursive_Backtracking mz = new MazeCreator_Recursive_Backtracking(conf);
		mz.setScreen(s);
		Cell main_cell = getRandomCell();
		mz.Traverse(main_cell);
		Cell final_cell = final_setup(main_cell, s);
		conf.mazeIsGenerated();

		Player p = new Player(main_cell, final_cell, s, conf, mz.getMaze());
		ctrls.setPlayer(p);
		conf.newGameStarted();
	}

	private Cell final_setup(Cell main_cell, Screen s) {
		s.setCurrent(main_cell);
		Cell fc;
		do {
			fc = getRandomCell();
		} while (fc.xcord == main_cell.xcord && fc.ycord == main_cell.ycord);
		s.setFinalCell(fc);
		s.update_screen();
		return fc;
	}

	private Cell getRandomCell() {
		Random rand = new Random();
		int x = rand.nextInt(conf.W / conf.S);
		int y = rand.nextInt(conf.H / conf.S);
		return new Cell(x, y);
	}

	private void gameLoop() {
		while (conf.run_game) {
			if (conf.canStartNewGame()) {
				startNewGame();
			} else if (conf.win_game) {
				System.out.println("WIN");
			}
		}
	}

	public static void main(String[] args) {
		Main m = new Main();
		m.gameLoop();
	}

}
