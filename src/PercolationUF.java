
public class PercolationUF implements IPercolate {

	private boolean[][] myGrid;
	private int myOpenCount;
	private IUnionFind myFinder;
	private final int VTOP;
	private final int VBOTTOM;
	
	public PercolationUF(int size, IUnionFind finder) {
		myGrid = new boolean[size][size];
		VTOP = size*size;
		VBOTTOM = size*size + 1;
		myOpenCount = 0;
		for (int i = 0; i <size; i++) {
			for (int j = 0; j<size; j++) {
				myGrid[i][j] = false;
			}
		}
		myFinder = finder;
		myFinder.initialize(size*size +2);
	}
	
	// copied from PercolationDFS to check validity for required cell specifications
	protected boolean inBounds(int row, int col) {
		if (row < 0 || row >= myGrid.length) return false;
		if (col < 0 || col >= myGrid[0].length) return false;
		return true;
	}
	
	// makes sure the specified cell is in myGrid, then changes the value to true at that cell and updates myOpenCount
	// then checks the adjacent cells 
	@Override
	public void open(int row, int col) {
		int[] rowDelta = {-1,1,0,0};
		int[] colDelta = {0,0,-1,1};
		
		if (! inBounds(row,col)) {
			throw new IndexOutOfBoundsException(
					String.format("(%d,%d) not in bounds", row,col));
		}
		myGrid[row][col] = true;
		myOpenCount++;
		
		int curr = row*myGrid.length + col; // variable to make code clearer - cell number used in unionfind
		for (int k = 0; k<4; k++) {
			if (inBounds(row+rowDelta[k], col+colDelta[k]) && isOpen(row+rowDelta[k], col+colDelta[k])) {
				int adjacent = (row+rowDelta[k])*myGrid.length + col+colDelta[k];
				myFinder.union(curr, adjacent);
			}
		}
		if (row == 0) myFinder.union(curr, VTOP);
		if (row == myGrid.length-1) myFinder.union(curr, VBOTTOM);
	}

	// throws an exception if out of bounds, otherwise returns the value of the cell in myGrid
	@Override
	public boolean isOpen(int row, int col) {
		if (! inBounds(row,col)) {
			throw new IndexOutOfBoundsException(
					String.format("(%d,%d) not in bounds", row,col));
		}
		return myGrid[row][col];
	}

	@Override
	public boolean isFull(int row, int col) {
		if (! inBounds(row,col)) {
			throw new IndexOutOfBoundsException(
					String.format("(%d,%d) not in bounds", row,col));
		}
		return myFinder.connected(VTOP, (row*myGrid.length+col));
	}

	@Override
	public boolean percolates() {
		return myFinder.connected(VTOP, VBOTTOM);
	}

	// returns myOpenCount, the number of open sites
	@Override
	public int numberOfOpenSites() {
		return myOpenCount;
	}

}
