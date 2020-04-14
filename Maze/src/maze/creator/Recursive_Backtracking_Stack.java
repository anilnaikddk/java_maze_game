package maze.creator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

import maze.game.Cell;
import maze.game.Configurations;
import maze.game.Screen;

public class Recursive_Backtracking_Stack {

//	  Algorithm
//    1. Choose the initial cell, mark it as visited and push it to the stack
//    2. While the stack is not empty
//        1. Pop a cell from the stack and make it a current cell
//        2. If the current cell has any neighbors which have not been visited
//            1. Push the current cell to the stack
//            2. Choose one of the unvisited neighbors
//            3. Remove the wall between the current cell and the chosen cell
//            4. Mark the chosen cell as visited and push it to the stack
	
	private final int W;
	private final int H;
	private final int S;
	
	private Cell maze[][];
	private Screen screen;
	private Stack<Cell> stack;
	
	public Recursive_Backtracking_Stack(Configurations conf) {
		this.W = conf.W;
		this.H = conf.H;
		this.S = conf.S;
		maze = new Cell[W / S][H / S];
		screen = null;
		stack = new Stack<>();
	}
	
	public void setScreen(Screen s) {
		this.screen = s;
	}
	
	public void startGeneration(Cell initial_cell) {
		// Choose the initial cell, mark it as visited and push it to the stack
		initial_cell.visited = true;
		maze[initial_cell.xcord][initial_cell.ycord] = initial_cell;
		stack.push(initial_cell);
		// While the stack is not empty
		while(!stack.isEmpty()) {
			// Pop a cell from the stack and make it a current cell
			Cell current_cell = stack.pop();
			screen.update_screen(maze,current_cell,false);
			// If the current cell has any neighbors which have not been visited
			if(current_cell.hasAnyUnvisitedNeighbourCell(maze)) {
				// Push the current cell to the stack
				stack.push(current_cell);
				// Choose one of the unvisited neighbors
				ArrayList<Cell> n = current_cell.getUnvisitedNeighbourCells(maze);
				Collections.shuffle(n);
				Cell neighbour =  n.get(0);
				// Remove the wall between the current cell and the chosen cell
				removeWall(current_cell, neighbour);
				// Mark the chosen cell as visited and push it to the stack
				makeCellVisited(neighbour);
				stack.push(neighbour);
			}
		}
	}
	
	private void removeWall(Cell current_cell, Cell neighbour) {
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
	}
	
	private void makeCellVisited(Cell cell) {
		if (maze[cell.xcord][cell.ycord] == null) {
			maze[cell.xcord][cell.ycord] = cell;
		}
		if (screen != null) {
//			cell.color = Configurations.visitingcell_color;
			//screen.update_screen(maze);
		}
		maze[cell.xcord][cell.ycord].visited = true;
	}
	
	public Cell[][] getMaze(){
		return maze;
	}
}
