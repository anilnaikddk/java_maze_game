package maze.solver;

import maze.game.Cell;

public class BFS_SC extends Cell{

	public BFS_SC parent;
	
	public BFS_SC(Cell cell) {
		super(cell.xcord,cell.ycord);
		super.UP_WALL = cell.UP_WALL;
		super.DOWN_WALL = cell.DOWN_WALL;
		super.LEFT_WALL = cell.LEFT_WALL;
		super.RIGHT_WALL = cell.RIGHT_WALL;
		super.visited = cell.visited;
	}
}
