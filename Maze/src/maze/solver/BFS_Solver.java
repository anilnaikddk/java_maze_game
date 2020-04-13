package maze.solver;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import maze.game.Cell;

public class BFS_Solver {
	
	/*
	 * Algorithm
		1  procedure BFS(G, start_v) is
		2      let Q be a queue
		3      label start_v as discovered
		4      Q.enqueue(start_v)
		5      while Q is not empty do
		6          v := Q.dequeue()
		7          if v is the goal then
		8              return v
		9          for all edges from v to w in G.adjacentEdges(v) do
		10             if w is not labeled as discovered then
		11                 label w as discovered
		12                 w.parent := v
		13                 Q.enqueue(w)
	 */
	
	// let Q be a queue
	private Queue<BFS_SC> queue;
	private Cell maze[][];
	private List<BFS_SC> solution;
	
	public BFS_Solver(Cell maze[][],Cell starting_cell) {
		this.maze= maze;
		initMaze();
		queue = new LinkedList<>();
		solution = new LinkedList<>();
	}
	
	private void initMaze() {
		for (Cell[] row_cells : maze) {
			for (Cell cell : row_cells) {
				cell.visited = false;
			}
		}
	}
	
	public BFS_SC startSolving(Cell starting_cell,Cell final_cell) {
		// label start_v as discovered
		starting_cell.visited = true;
		// Q.enqueue(start_v)
		queue.add(new BFS_SC(starting_cell));
		// while Q is not empty do
		while(!queue.isEmpty()) {
			// v := Q.dequeue()
			BFS_SC cell = queue.remove();
			// if v is the goal then
			if(cell.xcord == final_cell.xcord && cell.ycord == final_cell.ycord) {
				System.out.println("Solved");
				return cell;
			}
			// for all edges from v to w in G.adjacentEdges(v) do
			getAdjacentEdges(cell).forEach(neighbour -> {
				// if w is not labeled as discovered then
				if(!neighbour.visited) {
					// label w as discovered
					neighbour.visited = true;
					// w.parent := v
					neighbour.parent = cell;
					solution.add(neighbour);
					// Q.enqueue(w)
					queue.add(neighbour);
				}
			});
		}
		return null;
	}
	
	public List<Cell> getSolution(Cell starting_cell,Cell final_cell) {
		List<Cell> solution_path = new LinkedList<>();
		BFS_SC cell = startSolving(starting_cell,final_cell);
		while(cell.parent.xcord != starting_cell.xcord || cell.parent.ycord != starting_cell.ycord) {
			cell = cell.parent;
			solution_path.add(cell);
		}
		return solution_path;//.forEach(c -> {System.out.println(c.ycord + "," + c.xcord);});
	}
	
	private ArrayList<BFS_SC> getAdjacentEdges(Cell v){
		ArrayList<BFS_SC> nei = new ArrayList<>();
		int x = v.xcord;
		int y = v.ycord;
		if(v.LEFT_WALL == false) {
			nei.add(new BFS_SC(maze[x-1][y]));
		}
		if(v.RIGHT_WALL == false) {
			nei.add(new BFS_SC(maze[x+1][y]));
		}
		if(v.UP_WALL == false) {
			nei.add(new BFS_SC(maze[x][y-1]));
		}
		if(v.DOWN_WALL == false) {
			nei.add(new BFS_SC(maze[x][y+1]));
		}
		return nei;
	}

}
