package connectFourAdvanced;

import java.util.ArrayList;

public class AIPlayer {

	/**
	 * A very simple AI class, selecting random moves around where the human
	 * player has placed theirs.
	 */

	private GameBoard gameBoardView;
	private GameControl gameController;
	private int opponentNumber;
	private int boardWidth, boardHeight;

	public AIPlayer(GameBoard gameBoard, GameControl gameController, int winningCondition) {
		this.gameBoardView = gameBoard;
		this.gameController = gameController;
		this.opponentNumber = 1;
		this.boardWidth = this.gameBoardView.getBoardWidth();
		this.boardHeight = this.gameBoardView.getBoardHeight();
	}

	public void requestMove() {
		/**
		 * The AI's simple random move selector. Calls
		 * this.gameController.makeMove just like a human player pressing mouse
		 * on a GameBoardSquare object.
		 */

		int[] AIMove = new int[2];
		boardCoordinate targetChoice;
		int targetRow, targetColumn;
		boolean targetMoveMade = false;

		// Build a list of all opponent moves.
		ArrayList<boardCoordinate> opponentPieces = new ArrayList<boardCoordinate>();

		for (int i = 0; i < this.boardHeight; i++) {
			for (int j = 0; j < this.boardWidth; j++) {

				if (this.gameBoardView.getBoardValue(i, j) == this.opponentNumber) {
					opponentPieces.add(new boardCoordinate(i, j));
				}

			}
		}

		// Choose a random opponent piece to work on.
		for (int a = 0; a < opponentPieces.size(); a++) {

			targetChoice = opponentPieces.get(a);
			targetRow = targetChoice.getRow();
			targetColumn = targetChoice.getColumn();

			// Find an available space around the target opponent piece.
			for (int m = (targetRow - 1); (m <= (targetRow + 1) && targetMoveMade == false); m++) {
				for (int n = (targetColumn - 1); (n <= (targetColumn + 1) && targetMoveMade == false); n++) {

					if (isValidMove(m, n)) {
						AIMove[0] = m;
						AIMove[1] = n;
						targetMoveMade = true;
					}

				}
			}
		}

		// If no available space around the target, randomly generate a move.
		while (!targetMoveMade) {

			targetRow = randomSelection(0, this.boardHeight);
			targetColumn = randomSelection(0, this.boardWidth);

			if (isValidMove(targetRow, targetColumn)) {
				AIMove[0] = targetRow;
				AIMove[1] = targetColumn;
				targetMoveMade = true;

			}
		}

		this.gameController.makeMove(this.gameBoardView.getSquareObject(AIMove[0], AIMove[1]));

	}

	private boolean isValidMove(int row, int column) {
		/**
		 * Validates if a potential move is valid (within board in an available
		 * square)
		 */

		// Check if the requested move is within board.
		if (row >= 0 && row < this.boardHeight && column >= 0 && column < this.boardWidth) {

			// Check if the requested move is available.
			if (this.gameBoardView.getBoardValue(row, column) == 0) {
				return true;
			}

		}
		return false;
	}

	private int randomSelection(int min, int max) {
		/**
		 * Selects a random number from range [min..max].
		 */

		return (min) + (int) (Math.random() * ((max - min) + 1));
	}

	private class boardCoordinate {

		/**
		 * Subclass storing the coordinates of a square on the board.
		 */

		private int row;
		private int column;

		public boardCoordinate(int row, int column) {
			this.row = row;
			this.column = column;
		}

		public int getRow() {
			/**
			 * Returns the row of the coordinate.
			 */

			return this.row;
		}

		public int getColumn() {
			/**
			 * Returns the column of the coordinate.
			 */

			return this.column;
		}
	}

}
