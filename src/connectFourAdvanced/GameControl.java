package connectFourAdvanced;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class GameControl implements ActionListener {

	/**
	 * This class controls the configuration of the game, as well as the game
	 * board by creating and interacting with a GameBoard object.
	 */

	private final int MAXIMUM_GAME_BOARD_WIDTH = 15;
	private final int MAXIMUM_GAME_BOARD_HEIGHT = 15;

	private int winningCondition;
	private int currentPlayer;
	private boolean gameOver, gameWithAI;

	private JButton gameConfigurationHumanVersusHumanButton, gameConfigurationHumanVersusAIButton, gameRestartButton;
	private JPanel gamePanel, messagePanel, resetPanel;
	private JFrame parentFrame;

	private GameBoard gameBoard;
	private AIPlayer computerPlayer;

	public GameControl(JFrame parentFrame, JPanel gamePanel) {

		this.parentFrame = parentFrame;
		this.gamePanel = gamePanel;
		this.resetPanel = new JPanel();
		this.messagePanel = Utilities.makeMessagePanel("");
		this.currentPlayer = 1;
		this.gameOver = false;

	}

	public void initialiseGame() {

		// Update the top panel for displaying messages.
		Utilities.updateMessagePanel(this.messagePanel,
				"Welcome to Tic Tac Toe: Advanced." + "\nPlease select game mode, board size and winning conditions.");

		// Create the game configuration form.
		String[] gameConfigurationFormLabels = { "Board Width:", "Board Height:", "Winning Line Length:" };
		String[] gameConfigurationFormDefaults = { "7", "6", "4" };

		gameConfigurationHumanVersusHumanButton = new JButton("Play Human vs Human");
		gameConfigurationHumanVersusHumanButton.addActionListener(this);
		gameConfigurationHumanVersusAIButton = new JButton("Play Human vs Computer");
		gameConfigurationHumanVersusAIButton.addActionListener(this);

		JButton[] gameConfigurationFormButtons = { gameConfigurationHumanVersusHumanButton,
				gameConfigurationHumanVersusAIButton };
		this.gamePanel = Utilities.makeForm(gameConfigurationFormLabels, gameConfigurationFormDefaults,
				gameConfigurationFormButtons, 5);

		this.parentFrame.getContentPane().add(this.messagePanel, BorderLayout.NORTH);
		this.parentFrame.getContentPane().add(this.gamePanel, BorderLayout.CENTER);
		this.parentFrame.getContentPane().add(this.resetPanel, BorderLayout.SOUTH);
	}

	public void startGame() {
		/**
		 * Start a game.
		 */

		this.gamePanel = this.gameBoard.makeEmptyBoardPanel(this.gamePanel);

		// Add game description text and a restart button to the bottom panel.
		gameRestartButton = new JButton("Restart");
		gameRestartButton.addActionListener(this);
		this.resetPanel.add(new JLabel("Connect " + String.valueOf(this.winningCondition) + " in a line to win."));
		this.resetPanel.add(gameRestartButton);

	}

	public void restartGame() {
		/**
		 * Called when Restart button is pressed during a game.
		 */

		// Reset game control variables and repaint board.
		this.currentPlayer = 1;
		this.gameOver = false;
		this.gamePanel = this.gameBoard.makeEmptyBoardPanel(this.gamePanel);
		Utilities.updateMessagePanel(this.messagePanel, "Player " + this.currentPlayer + ", make move.");

	}

	public void makeMove(GameBoardSquare square) {
		int squareRow = square.getRow();
		int squareColumn = square.getColumn();
		if (this.gameBoard.getBoardValue(squareRow, squareColumn) == 0) {
			if (this.gameOver == false) {
				this.gameBoard.setBoardValue(squareRow, squareColumn, this.currentPlayer);
				this.gameBoard.setBoardColour(square, this.currentPlayer, squareRow, squareColumn);
				if (this.currentPlayer == 1) {
					this.currentPlayer = 2;
				} else {
					this.currentPlayer = 1;
				}
				Utilities.updateMessagePanel(this.messagePanel, "Player " + this.currentPlayer + ", make move.");
			}

			this.checkForWinner();
			this.checkForTie();
		}

		// If human versus AI enabled, request AI to make a move.
		if (this.gameOver == false && this.currentPlayer == 2 && this.gameWithAI) {
			this.computerPlayer.requestMove();
		}
	}

	public void checkForWinner() {
		/**
		 * Calls gameboard's scanBoardForFull() to check if a player wins and
		 * the game ends.
		 */

		int winner = this.gameBoard.scanBoardForWinner(this.winningCondition);

		if (winner != 0) {
			this.gameOver = true;
			Utilities.updateMessagePanel(this.messagePanel, "Player " + winner + " has won!");
		}

	}

	public void checkForTie() {
		/**
		 * Calls gameboard's scanBoardForFull() to check if no further move is
		 * possible, and game ends in a tie.
		 */

		if (this.gameBoard.scanBoardForFull()) {
			this.gameOver = true;
			Utilities.updateMessagePanel(this.messagePanel, "Board is full. Game ends in tie.");
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		/**
		 * Performs checks on the game configuration values, and starts the game
		 * if a valid game can be created from the values.
		 */

		JButton buttonSource = (JButton) e.getSource();
		int boardHeight = 0;
		int boardWidth = 0;
		boolean gameReady = true;

		// Handle form submission.

		if (buttonSource == gameConfigurationHumanVersusAIButton
				|| buttonSource == gameConfigurationHumanVersusHumanButton) {

			String gameConfigurationBoardWidthString = Utilities.readPanelTextfield(this.gamePanel, 0);
			String gameConfigurationBoardHeightString = Utilities.readPanelTextfield(this.gamePanel, 1);
			String gameConfigurationWinningStreakString = Utilities.readPanelTextfield(this.gamePanel, 2);

			if (Utilities.intInputCheck(gameConfigurationBoardHeightString)) {

				if (!(Utilities.boundaryCheck(Integer.parseInt(gameConfigurationBoardHeightString), 2,
						MAXIMUM_GAME_BOARD_HEIGHT))) {
					Utilities.updateMessagePanel(this.messagePanel,
							"Board height must be between 2 and " + String.valueOf(MAXIMUM_GAME_BOARD_HEIGHT) + "!");
					gameReady = false;
				} else {
					boardHeight = Integer.parseInt(gameConfigurationBoardHeightString);
				}

			} else {
				Utilities.updateMessagePanel(this.messagePanel, "You have not entered a valid board height.");
				gameReady = false;
			}

			if (Utilities.intInputCheck(gameConfigurationBoardWidthString)) {

				if (!(Utilities.boundaryCheck(Integer.parseInt(gameConfigurationBoardWidthString), 2,
						MAXIMUM_GAME_BOARD_WIDTH))) {
					Utilities.updateMessagePanel(this.messagePanel,
							"Board width must be between 2 and " + String.valueOf(MAXIMUM_GAME_BOARD_HEIGHT) + "!");
					gameReady = false;
				} else {
					boardWidth = Integer.parseInt(gameConfigurationBoardWidthString);
				}

			} else {
				Utilities.updateMessagePanel(this.messagePanel, "You have not entered a valid board width.");
				gameReady = false;
			}

			if (Utilities.intInputCheck(gameConfigurationWinningStreakString)) {

				this.winningCondition = Integer.parseInt(gameConfigurationWinningStreakString);

				// Length of the winning steak cannot be larger than the maximum
				// diagonal.
				if (this.winningCondition < 2 || this.winningCondition > Math.max(boardWidth, boardHeight)) {
					Utilities.updateMessagePanel(this.messagePanel,
							"Your winning condition is not appropriate for this board!");
					gameReady = false;
				} else {
					this.winningCondition = Integer.parseInt(gameConfigurationWinningStreakString);
				}

			} else {
				Utilities.updateMessagePanel(this.messagePanel, "You have not entered a valid winning condition.");
				gameReady = false;
			}

			if (gameReady) {

				this.gameBoard = new GameBoard(boardHeight, boardWidth, this);

				if (buttonSource == gameConfigurationHumanVersusAIButton) {
					this.gameWithAI = true;
					this.computerPlayer = new AIPlayer(this.gameBoard, this, this.winningCondition);
				}

				this.startGame();
				Utilities.updateMessagePanel(this.messagePanel, "Player 1, make move.");

			}
		} else if (buttonSource == gameRestartButton) {
			// Reset the game.
			this.restartGame();

		}
	}
}
