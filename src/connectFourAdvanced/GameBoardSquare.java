package connectFourAdvanced;

import javax.swing.JLabel;

public class GameBoardSquare extends JLabel {

	private static final long serialVersionUID = 5206228983352549440L;
	/**
	 * This class extends JLabel to store additional information required by
	 * each square on the board, for the game board to function.
	 */

	private int squareRow;
	private int squareColumn;

	public GameBoardSquare(int squareRow, int squareColumn) {

		this.squareRow = squareRow;
		this.squareColumn = squareColumn;

	}

	public int getRow() {
		/**
		 * Returns the row number of this square on the board.
		 */

		return this.squareRow;
	}

	public int getColumn() {
		/**
		 * Returns the column number of this square on the board.
		 */

		return this.squareColumn;
	}

}
