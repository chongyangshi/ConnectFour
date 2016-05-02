package connectFourAdvanced;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Utilities {

	/**
	 * This class of static methods contain the necessary features for UI
	 * layout, as well as to validate user input values.
	 */

	public static JPanel makePanel() {
		/**
		 * Creates and returns an empty JPanel.
		 */

		JPanel newPanel = new JPanel();
		return newPanel;
	}

	public static JPanel makeForm(String[] textLabels, String[] textDefaults, JButton[] formButtons, int fieldWidth) {
		/**
		 * Returns a new JPanel form constructed with field labels from
		 * textLabels, with their defaults from textDefaults, of fieldWidth in
		 * width and buttons from textButtons in the last rows.
		 */

		// Verify parameters.
		if (textLabels.length != textDefaults.length) {
			System.out.println("makeForm: lengths of textLabels and textDefaults do not match.");
			throw new IllegalArgumentException();
		}

		if (fieldWidth < 1) {
			System.out.println("makeForm: width of the textfields cannot be smaller than 1.");
			throw new IllegalArgumentException();
		}

		JPanel returnPanel = new JPanel(new GridBagLayout());
		returnPanel.setBackground(Color.white);

		// Set constraints for new form.
		GridBagConstraints PanelConstraints = new GridBagConstraints();
		PanelConstraints.anchor = GridBagConstraints.WEST;
		PanelConstraints.insets = new Insets(5, 5, 5, 5);
		PanelConstraints.gridx = 0;
		PanelConstraints.gridy = 0;

		for (int i = 0; i < textLabels.length; i++) {

			// Create new label and textfield objects.
			JLabel newLabel = new JLabel(textLabels[i]);
			JTextField newTextfield = new JTextField(fieldWidth);
			newTextfield.setText(textDefaults[i]);

			// And add them to the grid.
			PanelConstraints.gridx = 0;
			PanelConstraints.gridy = i;
			returnPanel.add(newLabel, PanelConstraints);
			PanelConstraints.gridx = 1;
			returnPanel.add(newTextfield, PanelConstraints);
		}

		// Add buttons on the last rows.
		PanelConstraints.gridx = 0;
		PanelConstraints.gridy = textLabels.length;
		for (int j = 0; j < formButtons.length; j++) {
			PanelConstraints.gridy += j;
			returnPanel.add(formButtons[j], PanelConstraints);
		}

		return returnPanel;
	}

	public static JPanel makeMessagePanel(String initialMessage) {
		/**
		 * Creates a new panel for displaying messages and returns it.
		 */

		JPanel newPanel = new JPanel(new FlowLayout());
		newPanel.setBackground(Color.white);

		// Create and format the new text area.
		JTextArea initialMessageArea = new JTextArea(initialMessage);
		initialMessageArea.setFont(new Font("Arial", Font.BOLD, 16));
		initialMessageArea.setBackground(Color.white);

		newPanel.add(initialMessageArea);

		return newPanel;
	}

	public static void updateMessagePanel(JPanel messagePanel, String newMessage) {
		/**
		 * Modifies the message displayed in a message panel.
		 */

		Component messagePanelComponent = messagePanel.getComponent(0);

		// Verify that we are modifying a JTextArea.
		if (!(messagePanelComponent instanceof JTextArea)) {
			System.out.println("updateMessagePanel: messagePanel does not have first component as a JTextArea.");
			throw new IllegalArgumentException();
		}

		// Modify the text in the JTextArea.
		((JTextArea) messagePanelComponent).setText(newMessage);

	}

	public static String readPanelTextfield(JPanel formPanel, int fieldNumber) {
		/**
		 * Reads the value of fieldNumber'th text field from formPanel. Returns
		 * an empty string if no text field found.
		 */

		int componentCount = formPanel.getComponentCount();

		int iterationCount = 0;
		Component formPanelComponent;

		if (componentCount == 0) {
			System.out.println("readPanelTextfield: formPanel appears to be empty.");
			throw new IllegalArgumentException();
		}

		for (int i = 0; i < componentCount; i++) {

			formPanelComponent = formPanel.getComponent(i);

			if (formPanelComponent instanceof JTextField) {
				if (iterationCount == fieldNumber) {
					String returnText = ((JTextField) formPanelComponent).getText();
					return returnText;
				} else {
					iterationCount++;
				}
			}

		}

		return "";

	}

	public static boolean intInputCheck(String input) {
		/**
		 * Utility function, checks if input string is a valid, positive
		 * integer.
		 */

		if (input.isEmpty()) {
			return false;
		} else {
			try {
				Integer.parseInt(input);
			} catch (NumberFormatException e) {
				return false;
			}
			return true;
		}

	}

	public static boolean boundaryCheck(int value, int lower, int upper) {
		/**
		 * Utility function, checks if value of input is within the lower and
		 * upper bounds. Returns true or false as appropriate.
		 */

		if (value >= lower && value <= upper) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean listEqualCheck(int[] inputList) {
		/**
		 * Utility function, checks if all elements in inputList are equal.
		 * Returns true if so, false if otherwise.
		 */

		// Verify that we have a non-empty list.
		if (inputList.length < 1) {
			System.out.println("listEqualCheck: input is an empty list.");
			throw new IllegalArgumentException();
		}

		int previous = inputList[0];

		for (int i = 0; i < inputList.length; i++) {
			if (inputList[i] != previous) {
				return false;
			}
		}

		return true;

	}

}
