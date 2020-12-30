package view.Impl;

import java.util.Locale;

import javax.swing.JOptionPane;

public class InformationProvider implements Runnable {

	private String myInformation;
	private int myCapability;
	private String myMessage;
	private Object[] myValuesToFormat;
	private static int counter = 0;

	/// ctor.
	public InformationProvider(String information, int capability, String message, String... VARARGS) {

		myMessage = message;
		myInformation = information;
		myCapability = capability;
		myValuesToFormat = new Object[VARARGS.length];

		for (String string : VARARGS) {
			myValuesToFormat[counter] = string;
			counter++;
		}
		counter = 0;
	}

	@Override
	public void run() {
		JOptionPane.showMessageDialog(null, String.format(Locale.GERMAN, myMessage, myValuesToFormat), myInformation,
				myCapability);
	}
}
