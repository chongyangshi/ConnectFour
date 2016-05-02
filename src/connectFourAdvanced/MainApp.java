package connectFourAdvanced;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainApp extends JFrame {

	private static final long serialVersionUID = 4699673712295953303L;
	/**
	 * This class is the entry point for the game. Creates a game controller to
	 * launch the game.
	 */

	public final int FRAME_HEIGHT = 500;
	public final int FRAME_WIDTH = 500;
	public final String GAME_TITLE = "Connect Four: Advanced";

	public MainApp() {

		// Initialize the application.
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout(4, 4));
		this.getContentPane().setBackground(Color.white);
		this.setTitle(GAME_TITLE);
		this.setSize(FRAME_HEIGHT, FRAME_WIDTH);

		// Create a game controller and let it start the game.
		JPanel gamePanel = Utilities.makePanel();
		GameControl gameController = new GameControl(this, gamePanel);
		gameController.initialiseGame();

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		MainApp mainWindow = new MainApp();
		mainWindow.setVisible(true);

	}

}
