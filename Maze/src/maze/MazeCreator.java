package maze;

import java.util.ArrayList;
import java.util.Collections;

public class MazeCreator {

	private Cell[][] maze;
	private Screen screen;
	private int W, H, S;

	public MazeCreator(Configurations conf) {
		this.W = conf.W;
		this.H = conf.H;
		this.S = conf.S;
		maze = new Cell[W / S][H / S];
		screen = null;
	}

	public void setScreen(Screen s) {
		this.screen = s;
	}
	

	public void Traverse(Cell current_cell) {
		makeCellVisited(current_cell);
		if (current_cell.hasAnyUnvisitedNeighbourCell(maze)) {
			visitNeighbour(current_cell);
		}
	}

	private void makeCellVisited(Cell cell) {
		if (screen != null) {
			screen.setCurrent(cell);
			screen.update_screen(maze);
		}
		if (maze[cell.xcord][cell.ycord] == null) {
			maze[cell.xcord][cell.ycord] = cell;
		}
		if (maze[cell.xcord][cell.ycord].visited) {
			return;
		}
		maze[cell.xcord][cell.ycord].visited = true;
	}

	private void visitNeighbour(Cell current_cell) {
		ArrayList<Cell> neighbours = current_cell.getUnvisitedNeighbourCells(maze);
		Collections.shuffle(neighbours);
		for (Cell neighbour : neighbours) {
			if (maze[neighbour.xcord][neighbour.ycord] == null) {
				maze[neighbour.xcord][neighbour.ycord] = neighbour;
			}
			if (maze[neighbour.xcord][neighbour.ycord].visited) {
				continue;
			}
			neighbour = removeWall(current_cell, neighbour);
			Traverse(neighbour);
		}
	}

	private Cell removeWall(Cell current_cell, Cell neighbour) {
		if (current_cell.ycord - 1 == neighbour.ycord) {
			current_cell.UP_WALL = false;
			neighbour.DOWN_WALL = false;
		} else if (current_cell.ycord + 1 == neighbour.ycord) {
			current_cell.DOWN_WALL = false;
			neighbour.UP_WALL = false;
		} else if (current_cell.xcord - 1 == neighbour.xcord) {
			current_cell.LEFT_WALL = false;
			neighbour.RIGHT_WALL = false;
		} else if (current_cell.xcord + 1 == neighbour.xcord) {
			current_cell.RIGHT_WALL = false;
			neighbour.LEFT_WALL = false;
		}
		maze[current_cell.xcord][current_cell.ycord] = current_cell;

		return neighbour;
	}

	public Cell[][] getMaze() {
		return maze;
	}

}
