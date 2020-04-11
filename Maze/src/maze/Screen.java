package maze;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Screen extends JPanel {

	private final Color cell_color = Color.BLACK;
	private final int Scale;
	private Cell current;
	private Cell finalcell;
	private Cell cells[][];
	private Configurations conf;

	public Screen(Configurations conf) {
		this.conf = conf;
		this.setPreferredSize(new Dimension(conf.W + conf.padding + 2, conf.H + 1));
		this.Scale = conf.S;
	}

	public void drawMaze(Graphics g) {
		// Cell cellss[][] = this.cells.clone();
		g.setColor(cell_color);
		for (Cell[] ci : cells) {
			for (Cell cell : ci) {
				if (cell == null) {
					continue;
				}
				int x = cell.xcord * Scale;
				int y = cell.ycord * Scale;

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
			}
		}

		// Draw current cell
		if (current != null) {
			g.fillRect((current.xcord * Scale) + conf.small, (current.ycord * Scale) + conf.small,
					Scale - conf.small * 2, Scale - conf.small * 2);
		}

		// Draw final cell
		if (finalcell != null) {
			g.setColor(Color.GREEN);
			g.fillRect((finalcell.xcord * Scale) + conf.small, (finalcell.ycord * Scale) + conf.small,
					Scale - conf.small * 2, Scale - conf.small * 2);
		}
	}

	private void writeText(Graphics g) {
		g.setFont(new Font("Arial", Font.BOLD, 16));
		g.setColor(Color.WHITE);
		g.fillRect(conf.W, 0, conf.padding, 50);
		g.setColor(Color.BLUE);
		g.drawString("Score : " + conf.score, conf.W + 10, 30);
		g.setColor(Color.black);
		g.drawRect(conf.W, 0, conf.padding, 50);
	}

	private void drawOutLine(Graphics g) {
		g.setColor(Color.black);
		g.drawRect(0, 0, conf.W, conf.H);
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, conf.W, conf.H);
		drawOutLine(g);
		drawMaze(g);
		writeText(g);
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
		paintImmediately(0, 0, conf.W, conf.H);
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
