package maze.creator;

import java.util.ArrayList;
import java.util.Collections;

import maze.game.Cell;
import maze.game.Configurations;
import maze.game.Screen;

public class Recursive_Backtracking {
	
//    1.Given a current cell as a parameter,
//    2.Mark the current cell as visited
//    3.While the current cell has any unvisited neighbour cells
//        1.Chose one of the unvisited neighbours
//        2.Remove the wall between the current cell and the chosen cell
//        3.Invoke the routine recursively for a chosen cell

	private Cell[][] maze;
	private Screen screen;
	private int W, H, S;

	public Recursive_Backtracking(Configurations conf) {
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
		current_cell.color = Configurations.visitedcell_color;
		screen.update_screen(maze,current_cell,true);
	}

	private void makeCellVisited(Cell cell) {
		if (screen != null) {
			//screen.setCurrent(cell);
			cell.color = Configurations.visitingcell_color;
			screen.update_screen(maze,cell,true);
		}
		if (maze[cell.xcord][cell.ycord] == null) {
			maze[cell.xcord][cell.ycord] = cell;
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
