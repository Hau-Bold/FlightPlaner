package listeners;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class ListenerForEmptyFields implements DocumentListener {

	private JTextField textField;
	private JButton button;

	public ListenerForEmptyFields(JTextField textField, JButton button) {
		this.textField = textField;
		this.button = button;
	}

	@Override
	public void changedUpdate(DocumentEvent arg0) {
	}

	@Override
	public void insertUpdate(DocumentEvent arg0) {
		if (!textField.getText().equals("")) {
			if (button != null && !button.isVisible()) {
				button.setVisible(true);
			}

			textField.setBackground(Color.WHITE);
		}
		textField.requestFocus();

	}

	@Override
	public void removeUpdate(DocumentEvent arg0) {
		if (textField.getText().equals("")) {
			if (button != null && button.isVisible()) {
				button.setVisible(false);
			}
			textField.setBackground(Color.GREEN);
		}
		textField.requestFocus();
	}

}
