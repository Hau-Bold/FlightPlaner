package listeners;

import java.util.ArrayList;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.itextpdf.layout.element.List;

public class ListenerForAutoComplete
//implements DocumentListener
{

//	private JTextField textField;
//	private CompletedFrame completedFrame;
//
//	public ListenerForAutoComplete(JTextField textField, CompletedFrame completedFrame) {
//		this.textField = textField;
//		this.completedFrame=completedFrame;
//	}
//
//	@Override
//	public void changedUpdate(DocumentEvent arg0) {
//	}
//
//	@Override
//	public void insertUpdate(DocumentEvent arg0) {
//		String text;
//
//		text = textField.getText();
//		if (text != "") {
//			String[] textSplitted = text.split(",");
//			String toComplete = ".*" + textSplitted[textSplitted.length - 1] + ".*";
//
//			if (toComplete.length() > 1) {
//				doAutoComplete(textSplitted, toComplete.toLowerCase());
//			}
//		}
//
//	}
//
//	private void doAutoComplete(String[] textSplitted, String toComplete) {
//		Runnable AutoComplete = new Runnable() {
//			String completed = null;
//			ArrayList<String> completedList = new ArrayList<String>();
//
//			@Override
//			public void run() {
//				if ("ba".matches(toComplete)) {
//					completedList.add("Bamberg");
//					completed = "Bamberg";
//				}
//				if ("co".matches(toComplete)) {
//					completed = "Coburg";
//					completedList.add("Coburg");
//				}
//				if ("lic".matches(toComplete)) {
//					completed = "Lichtenfels";
//					completedList.add("Lichtenfels");
//				}
//				 if ("lich".matches(toComplete)) {
//					completed = "Lichtenstein";
//					completedList.add("Lichtenstein");
//				}
//				 if ("licht".matches(toComplete)) {
//						completed = "Lichteneiche";
//						completedList.add("Lichteneiche");
//					}
//				 if ("wü".matches(toComplete)) {
//					completed = "Würzbug";
//					completedList.add("Würzbug");
//				}
//
//				if (completed != null) {
//					String response = null;
//
//					if (textSplitted.length == 1) {
//						response = completed;
//					} else {
//						response = textSplitted[0];
//						for (int i = 1; i < textSplitted.length - 1; i++) {
//							response += ",";
//							response += textSplitted[i];
//						}
//						response += ",";
//						response += completed;
//					}
//
//					//textField.setText(response);
//					if(completedFrame != null)
//					{
//						completedFrame.dispose();
//					}
//					completedFrame=new CompletedFrame(completedList,textField);
//					
//				}
//
//			}
//		};
//		SwingUtilities.invokeLater(AutoComplete);
//	}
//
//	@Override
//	public void removeUpdate(DocumentEvent arg0) {
//	}

}
