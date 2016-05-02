package connectFourAdvanced;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseHandler implements MouseListener {

	/**
	 * This class responds to mouse clicks on the board, and calls the game
	 * controller to make moves for the player.
	 */

	private GameControl gameController;

	public MouseHandler(GameControl gameController) {

		this.gameController = gameController;

	}

	@Override
	public void mousePressed(MouseEvent e) {
		/**
		 * Handler for player clicks on the squares.
		 */

		GameBoardSquare square = (GameBoardSquare) e.getSource();
		this.gameController.makeMove(square);
	}

	// Required and unused override methods for the abstract class.

	@Override
	public void mouseClicked(MouseEvent e) {
		// Pass
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// Pass
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// Pass
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// Pass
	}
}
