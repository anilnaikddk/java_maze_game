package maze.game;

import java.awt.Color;
import java.util.ArrayList;

public class Cell {
	public int xcord;
	public int ycord;

	public boolean UP_WALL = true;
	public boolean DOWN_WALL = true;
	public boolean LEFT_WALL = true;
	public boolean RIGHT_WALL = true;

	public boolean visited = false;
	
	public Color color = null;

	public Cell(int xcord, int ycord) {
		this.xcord = xcord;
		this.ycord = ycord;
	}

	public boolean hasAnyUnvisitedNeighbourCell(Cell mazegrid[][]) {
		return isUnVisited(xcord + 1, ycord, mazegrid) || isUnVisited(xcord, ycord + 1, mazegrid)
				|| isUnVisited(xcord - 1, ycord, mazegrid) || isUnVisited(xcord, ycord - 1, mazegrid);
	}

	private boolean isUnVisited(int x, int y, Cell grid[][]) {
		try {
			return grid[x][y].visited == false;
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
			// System.out.println("E-AIOB");
		} catch (NullPointerException e) {
			return true;
		}
	}

	public ArrayList<Cell> getUnvisitedNeighbourCells(Cell grid[][]) {
		ArrayList<Cell> n = new ArrayList<>();
		if (isUnVisited(xcord + 1, ycord, grid)) {
			n.add(new Cell(xcord + 1, ycord));
		}
		if (isUnVisited(xcord, ycord + 1, grid)) {
			n.add(new Cell(xcord, ycord + 1));
		}
		if (isUnVisited(xcord - 1, ycord, grid)) {
			n.add(new Cell(xcord - 1, ycord));
		}
		if (isUnVisited(xcord, ycord - 1, grid)) {
			n.add(new Cell(xcord, ycord - 1));
		}
		return n;
	}

}
