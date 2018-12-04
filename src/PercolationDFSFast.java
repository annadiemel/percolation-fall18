import java.util.*;

public class PercolationDFSFast extends PercolationDFS {

	// constructor for this subclass that calls the super class constructor, initializing the private variables.
	public PercolationDFSFast(int n) {
		super(n);
	}

	// helper method to see if on top row
	private boolean onTop(int row) {
		return row==0;
	}
	
	// helper method to see if adjacent
	private boolean adjacent(int row, int col) {
		int[] rowDelta = {-1,1,0,0};
		int[] colDelta = {0,0,-1,1};
		for (int k = 0; k<4; k++) {
			if (inBounds(row + rowDelta[k], col + colDelta[k]) && isOpen(row + rowDelta[k], col + colDelta[k]) && isFull(row + rowDelta[k], col + colDelta[k])) return true;
		}
		return false;
	}
	
	// more efficient version of super's updateOnOpen method.
	@Override
	protected void updateOnOpen(int row, int col) {
		// deciding if the cell should be marked as full using boolean variable
		if (onTop(row) || adjacent(row,col)) dfs(row,col);
	}
	
}
