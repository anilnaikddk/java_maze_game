package maze;

import java.util.Random;

public class Player {
	private Cell main_cell;
	private Cell final_cell;
	private Screen screen;
	private Configurations conf;
	private Cell Maze[][];

	public Player(Cell main_cell, Cell final_cell, Screen screen, Configurations conf, Cell maze[][]) {
		this.main_cell = main_cell;
		this.final_cell = final_cell;
		this.screen = screen;
		this.conf = conf;
		this.Maze = maze;
	}

	public void move(int pos) {
		if (canMove(pos)) {
			if (pos == KeyBoard.LEFT) {
				main_cell = Maze[main_cell.xcord - 1][main_cell.ycord];
			} else if (pos == KeyBoard.RIGHT) {
				main_cell = Maze[main_cell.xcord + 1][main_cell.ycord];
			} else if (pos == KeyBoard.UP) {
				main_cell = Maze[main_cell.xcord][main_cell.ycord - 1];
			} else if (pos == KeyBoard.DOWN) {
				main_cell = Maze[main_cell.xcord][main_cell.ycord + 1];
			}
			screen.update_screen(Maze, main_cell);
			pause(80);

			// WIN - Reached Desired Cell
			if (main_cell.xcord == final_cell.xcord && main_cell.ycord == final_cell.ycord) {
				gameWon();
			}
			if (hasSingleWay()) {
				move(pos);
			}
		}
	}

	private void pause(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException ex) {
			// Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private void gameWon() {
		conf.gameWon();
//		System.out.println("WIN WIN");
		conf.increaseScore();
		main_cell = getRandomCell();
		final_cell = getRandomCell(main_cell);
		//screen.setCurrent(main_cell);
		screen.setFinalCell(final_cell);
		screen.update_screen(Maze,main_cell);
		conf.continueGame();
	}

	private Cell getRandomCell(Cell main_cell) {
		//s.setCurrent(main_cell);
		Cell fc;
		do {
			fc = getRandomCell();
		} while (fc.xcord == main_cell.xcord && fc.ycord == main_cell.ycord);
		return fc;
	}
	
	private Cell getRandomCell() {
		Random rand = new Random();
		int x = rand.nextInt(conf.W / conf.S);
		int y = rand.nextInt(conf.H / conf.S);
		
		return Maze[x][y];
	}

	private boolean hasSingleWay() {
		int count = 0;
		if (main_cell.LEFT_WALL) {
			count++;
		}
		if (main_cell.RIGHT_WALL) {
			count++;
		}
		if (main_cell.UP_WALL) {
			count++;
		}
		if (main_cell.DOWN_WALL) {
			count++;
		}
		return count == 2;
	}

	private boolean canMove(int pos) {
		if ((pos == KeyBoard.LEFT || pos == KeyBoard.A) && main_cell.LEFT_WALL == false) {
			return true;
		} else if ((pos == KeyBoard.RIGHT || pos == KeyBoard.D) && main_cell.RIGHT_WALL == false) {
			return true;
		} else if ((pos == KeyBoard.UP || pos == KeyBoard.W) && main_cell.UP_WALL == false) {
			return true;
		} else if ((pos == KeyBoard.DOWN || pos == KeyBoard.S) && main_cell.DOWN_WALL == false) {
			return true;
		}
		return false;
	}

}
