package toolbar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import client.Routeplaner;

public class ConfirmingAddress extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2023548002819243318L;
	private JToolBar toolbarAdress;
	private JPanel panelToolbar;
	private Routeplaner routenplaner;
	private JButton confirmAddress;

	public ConfirmingAddress(int x, int y, int width, int height, Routeplaner routenplaner) {
		initComponent(x, y, width, height);
		this.routenplaner = routenplaner;

	}

	private void initComponent(int x, int y, int width, int height) {
		this.setBounds(x, y, width, height);
		this.setLayout(new BorderLayout());
		this.setVisible(false);
		this.setResizable(false);

		panelToolbar = new JPanel();
		panelToolbar.setOpaque(true);
		panelToolbar.setLayout(new BorderLayout());
		panelToolbar.setBackground(Color.LIGHT_GRAY);

		confirmAddress = new JButton("Confirm");
		confirmAddress.setMnemonic(KeyEvent.VK_C);
		confirmAddress.setToolTipText("Press alt + C to confirm the address");
		confirmAddress.addActionListener(this);

		toolbarAdress = new JToolBar();
		toolbarAdress.setPreferredSize(new Dimension(width, height));
		toolbarAdress.add(confirmAddress);
		
		panelToolbar.add(toolbarAdress,BorderLayout.CENTER);
		this.getContentPane().add(panelToolbar);

		// toolbarAdress.add(btnConfirmAddress);
	}

	public void showFrame() {
		this.setVisible(true);
	}

	public void hideFrame() {
		this.setVisible(false);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		
		Object o= event.getSource();
		if(o.equals(confirmAddress))
		{
		   routenplaner.reactOnConfirmAdress();
		}
		
	}

}
