package maze.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.JPanel;

public class Screen extends JPanel {

	private final Color cell_color = Color.BLACK;
	private final Color final_cell_color = Color.red;
	private final Color solution_path_color = Color.green;
	private final Color score_color = Color.blue;
	private final Color border_color = Color.black;
	private final Color background_color = Color.white;
	private final int Scale;
	private Cell current;
	private Cell finalcell;
	private Cell cells[][];
	private Configurations conf;
	private List<Cell> solution_path;

	public Screen(Configurations conf) {
		this.conf = conf;
		this.setPreferredSize(new Dimension(conf.W + conf.padding + 2, conf.H + 1));
		this.Scale = conf.S;
		solution_path = null;
	}

	public void drawMaze(Graphics g) {
		// Cell cellss[][] = this.cells.clone();
		// g.setColor(cell_color);
		for (Cell[] ci : cells) {
			for (Cell cell : ci) {
				if (cell == null) {
					continue;
				}
				int x = cell.xcord * Scale;
				int y = cell.ycord * Scale;
				if (cell.color != null && !conf.isMazeGenerated()) {
					g.setColor(cell.color);
					g.fillRect(x, y, Scale, Scale);
				}
				g.setColor(cell_color);

				if (cell.UP_WALL) {
					g.drawLine(x, y, x + Scale, y);
				}
				if (cell.DOWN_WALL) {
					g.drawLine(x, y + Scale, x + Scale, y + Scale);
				}
				if (cell.LEFT_WALL) {
					g.drawLine(x, y, x, y + Scale);
				}
				if (cell.RIGHT_WALL) {
					g.drawLine(x + Scale, y, x + Scale, y + Scale);
				}
				if (conf.draw_coordinates)
					drawCoordinates(g, x, y);
			}
		}

		// Draw current cell
		if (current != null) {
			g.fillRect((current.xcord * Scale) + conf.small, (current.ycord * Scale) + conf.small,
					Scale - conf.small * 2, Scale - conf.small * 2);
		}

		// Draw final cell
		if (finalcell != null) {
			g.setColor(final_cell_color);
			g.fillRect((finalcell.xcord * Scale) + conf.small, (finalcell.ycord * Scale) + conf.small,
					Scale - conf.small * 2, Scale - conf.small * 2);
		}
	}

	private void writeScore(Graphics g) {
		int score = conf.score;
		g.setFont(new Font("Arial", Font.BOLD, 16));
		g.setColor(background_color);
		g.fillRect(conf.W, 0, conf.padding, 50);
		g.setColor(score_color);
		g.drawString("Score : " + score, conf.W + 10, 30);
		g.setColor(border_color);
		g.drawRect(conf.W, 0, conf.padding, 50);
	}

	private void drawOutLine(Graphics g) {
		g.setColor(border_color);
		g.drawRect(0, 0, conf.W, conf.H);
	}

	private void clearScreen(Graphics g) {
		g.setColor(background_color);
		g.fillRect(0, 0, conf.W, conf.H);
	}

	private void drawSolution(Graphics g) {
		if (solution_path == null || !conf.show_solution) {
			return;
		}
		g.setColor(solution_path_color);
//		int s = conf.small + conf.small;
//		Consumer<Cell> c1 = cell -> {
//			g.fillRect((cell.xcord * Scale) + s, (cell.ycord * Scale) + s, Scale - s * 2, Scale - s * 2);
//		};
		Consumer<Cell> c2 = cell -> {
			g.fillRect(cell.xcord * Scale, cell.ycord * Scale, Scale, Scale);
		};

		solution_path.forEach(c2);
	}

	private void drawCoordinates(Graphics g, int x, int y) {
		g.setFont(new Font("Arial", Font.BOLD, 10));
//		g.setColor(background_color);
//		g.fillRect(conf.W, 0, conf.padding, 50);
		// g.setColor(score_color);
		String co = y / Scale + "," + x / Scale;
		g.drawString(co, x + 6, y + 20);
		// g.setColor(border_color);
		// g.drawRect(conf.W, 0, conf.padding, 50);
	}

	@Override
	public void paint(Graphics g) {
		if (cells == null) {
			return;
		}
		// Clear screen every time
		clearScreen(g);
		// Draw Outer Line or Border
		drawOutLine(g);
		// Draw solution if allowed
		drawSolution(g);
		// Draw the current maze
		drawMaze(g);
		// Draws the current score
		writeScore(g);
	}

	public void update_screen() {
		pause();
		repaint();
	}

	public void update_screen(Cell cells[][]) {
		this.cells = cells;
		repaint();
		pause();
	}

	public void update_screen(Cell cells[][], Cell current) {
		this.cells = cells;
		this.current = current;
		paintImmediately(0, 0, conf.W + conf.padding, conf.H);
		pause();
	}

	public void update_screen(Cell cells[][], Cell current, List<Cell> soln) {
		this.cells = cells;
		this.current = current;
		this.solution_path = soln;
		paintImmediately(0, 0, conf.W + conf.padding, conf.H);
		pause();
	}

	public void update_screen(Cell cells[][], List<Cell> soln) {
		this.cells = cells;
		this.solution_path = soln;
		repaint();
		pause();
	}

	private void pause() {
		try {
			Thread.sleep(conf.pausespeed);
		} catch (InterruptedException ex) {
			// Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void setCurrent(Cell cell) {
		current = cell;
	}

	public void setFinalCell(Cell cell) {
		finalcell = cell;
	}

}
