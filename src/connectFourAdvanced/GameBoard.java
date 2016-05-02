package connectFourAdvanced;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameBoard {

	/**
	 * This class contains the visual and values of the game board. Each square
	 * on the board is individually contained in a GameBoardSquare object.
	 */

	private Color[] playerColours = { Color.BLACK, Color.GREEN, Color.MAGENTA };
	private int boardHeight, boardWidth;
	private GameBoardSquare[] gameBoardSquares;
	private int[][] gameBoard;

	private GameControl gameController;

	public GameBoard(int boardHeight, int boardWidth, GameControl gameController) {

		this.boardHeight = boardHeight;
		this.boardWidth = boardWidth;
		this.gameController = gameController;
		this.gameBoard = new int[this.boardHeight][this.boardWidth];
		this.gameBoardSquares = new GameBoardSquare[this.boardHeight * this.boardWidth];

	}

	public JPanel makeEmptyBoardPanel(JPanel targetPanel) {
		/**
		 * Removes contents from targetPanel and builds an empty game board in
		 * the panel.
		 */

		targetPanel.removeAll();
		targetPanel.setLayout(new GridLayout(boardHeight, boardWidth, 0, 0));
		targetPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		targetPanel.setBackground(Color.WHITE);

		/**
		 * Because MouseEvent is triggered on per object basis, there's no need
		 * to arrange them in the same 2-D style of array.
		 */
		int squareCount = 0;

		for (int i = 0; i < boardHeight; i++) {
			for (int j = 0; j < boardWidth; j++) {

				this.setBoardValue(i, j, 0);
				this.gameBoardSquares[squareCount] = new GameBoardSquare(i, j);
				this.gameBoardSquares[squareCount].setOpaque(true);
				this.gameBoardSquares[squareCount].setHorizontalAlignment(JLabel.CENTER);
				this.gameBoardSquares[squareCount].setVerticalAlignment(JLabel.CENTER);
				this.gameBoardSquares[squareCount].addMouseListener(new MouseHandler(this.gameController));
				this.gameBoardSquares[squareCount].setBorder(BorderFactory.createLineBorder(Color.BLACK));
				targetPanel.add(this.gameBoardSquares[squareCount]);
				squareCount++;

			}
		}

		targetPanel.repaint();
		targetPanel.updateUI();

		return targetPanel;

	}

	public boolean scanBoardForFull() {
		/**
		 * Checks if the board is full. Returns true if full, false otherwise.
		 */

		boolean boardFull = true;

		for (int i = 0; i < this.boardHeight; i++) {
			for (int j = 0; j < this.boardWidth; j++) {
				if (this.getBoardValue(i, j) == 0) {
					boardFull = false;
				}
			}
		}

		if (boardFull) {
			return true;
		}

		return false;
	}

	public int scanBoardForWinner(int winningCondition) {
		/**
		 * Checks if a player has won by scanning the board. Return 0 if no one
		 * has won. Returns 1 or 2 if Player 1 or 2 has won respectively.
		 */

		int[] checkingList = new int[winningCondition];
		int i, j, k, n; // Loop control variables.

		// Check in blocks of winningCondition size, see if the whole block is
		// of an equal value of non-zero.
		// If it is, then we have a winner.

		// Check horizontally
		for (i = 0; i < this.boardHeight; i++) {
			for (k = 0; k < (this.boardWidth - winningCondition + 1); k++) {
				n = 0;
				for (j = k; j < (k + winningCondition); j++) {
					checkingList[n++] = this.getBoardValue(i, j);
				}
				if (checkingList[0] != 0 && Utilities.listEqualCheck(checkingList)) {
					return checkingList[0];
				}
			}
		}

		// Check vertically
		for (i = 0; i < this.boardWidth; i++) {
			for (k = 0; k < (this.boardHeight - winningCondition + 1); k++) {
				n = 0;
				for (j = k; j < (k + winningCondition); j++) {
					checkingList[n++] = this.getBoardValue(j, i);
				}
				if (checkingList[0] != 0 && Utilities.listEqualCheck(checkingList)) {
					return checkingList[0];
				}
			}
		}

		/*
		 * Check diagonals. (No need to check diagonals if winning condition is
		 * larger than the maximum possible diagonal length.)
		 */
		if (winningCondition <= Math.min(boardHeight, boardWidth)) {

			// Check for "\" direction diagonals.
			for (i = 0; i < (boardHeight - winningCondition + 1); i++) {
				for (j = 0; j < (boardWidth - winningCondition + 1); j++) {
					n = 0;
					for (k = 0; k < winningCondition; k++) {
						checkingList[n++] = this.getBoardValue(i + k, j + k);
					}
					if (checkingList[0] != 0 && Utilities.listEqualCheck(checkingList)) {
						return checkingList[0];
					}
				}
			}

			// Check for "/" direction diagonals.
			for (i = 0; i <= (boardHeight - winningCondition); i++) {
				for (j = boardWidth - 1; j >= (winningCondition - 1); j--) {
					n = 0;
					for (k = 0; k < winningCondition; k++) {
						checkingList[n++] = this.getBoardValue(i + k, j - k);
					}
					if (checkingList[0] != 0 && Utilities.listEqualCheck(checkingList)) {
						return checkingList[0];
					}
				}
			}
		}

		return 0;
	}

	public void setBoardColour(GameBoardSquare square, int player, int column, int row) {
		/**
		 * Draws player's colour at the required square.
		 */

		if (player != 1 && player != 2) {
			System.out.println("setBoardColour: invalid player ID.");
			throw new IllegalArgumentException();
		}

		square.setBackground(playerColours[player]);

	}

	public void setBoardValue(int row, int column, int value) {
		/**
		 * Sets board value of game board at row and column to value. Throws
		 * exception if trying to set a value outside the boundaries.
		 */

		if ((row < 0 || row >= this.boardHeight) || (column < 0 || column >= this.boardWidth)) {
			System.out.println("setBoardValue: index out of bound (row = " + row + ", column = " + column + ")");
			throw new IllegalArgumentException();
		}

		this.gameBoard[row][column] = value;

	}

	public int getBoardValue(int row, int column) {
		/**
		 * Retrieves the value from (column, row) on gameBoard. Returns value: 0
		 * -- empty, 1-- player1'piece , 2 -- player2's piece. Throws exception
		 * if trying to set a value outside the boundaries.
		 */

		if ((row < 0 || row >= this.boardHeight) || (column < 0 || column >= this.boardWidth)) {
			System.out.println("getBoardValue: index out of bound (row = " + row + ", column = " + column + ")");
			throw new IllegalArgumentException();
		}

		return this.gameBoard[row][column];
	}

	public GameBoardSquare getSquareObject(int row, int column) {
		/**
		 * Returns the object of the square JLabel at requested row and column.
		 */

		return this.gameBoardSquares[column + row * this.boardWidth];
	}

	public Color getPlayerColour(int playerID) {
		/**
		 * Retrieves the colour that represents the player in the board.
		 */

		return playerColours[playerID];
	}

	public int getBoardWidth() {
		/**
		 * Returns the width (number of columns) of the board;
		 */

		return this.boardWidth;
	}

	public int getBoardHeight() {
		/**
		 * Returns the height (number of rows) of the board;
		 */

		return this.boardHeight;
	}

}
