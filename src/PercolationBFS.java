import java.util.*;

public class PercolationBFS extends PercolationDFSFast {

	// constructor that calls super's constructor to initialize a PercolationBFS object
	public PercolationBFS(int n) {
		super(n);
	}

	// making a helper method to see if a neighboring cell should be queued
	private boolean shouldDfs(int row, int col) {
		if (inBounds(row, col) && isOpen(row,col) && ! isFull(row,col)) return true;
		return false;
	}
	
	@Override
	protected void dfs(int row, int col) {
		int[] rowDelta = {-1,1,0,0};
        int[] colDelta = {0,0,-1,1};
		int cellRep = row*myGrid.length + col;
		Queue<Integer> pq = new LinkedList<>();
		myGrid[row][col] = FULL;
		pq.add(cellRep);
		while (pq.size() != 0) {
			int curr = pq.remove();
			int currRow = curr/myGrid.length;
			int currCol = curr%myGrid.length;
			for (int k = 0; k<4; k++) {
				if (shouldDfs(currRow + rowDelta[k], currCol + colDelta[k])) {
					myGrid[currRow+rowDelta[k]][currCol+colDelta[k]] = FULL;
					pq.add((currRow+rowDelta[k])*myGrid.length + (currCol+colDelta[k]));
				}
			}
		}
		
	}
}
