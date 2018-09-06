package Routenplaner;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;

public class MailDialogue extends JFrame implements ActionListener
{
	private JPanel pnlForAddress;
	private JLabel lblFrom,lblTo,lblPassword;
	private JTextField txtFrom,txtTo,txtPassword;
	private JButton btnSendMail;
	private JToolBar btnBar;
	
	public MailDialogue()
	{
		initComponent();
	}

	private void initComponent()
	{
		this.setTitle("MailTransaction");
		this.setSize(200,200);
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		pnlForAddress = new JPanel();
		pnlForAddress.setPreferredSize(new Dimension(180,200));
		pnlForAddress.setLayout(null);
		pnlForAddress.setBackground(Color.BLACK);
		this.add(pnlForAddress,BorderLayout.NORTH);
		
		btnBar = new JToolBar();
		btnBar.setPreferredSize(new Dimension(200,20));
		
		
		ImageIcon icon = new ImageIcon(getClass().getResource("../Images/Confirm_icon.png").getPath());
		icon.setImage(icon.getImage().getScaledInstance(20, 20,Image.SCALE_SMOOTH));
		btnSendMail = new JButton(icon);
		btnSendMail.setPreferredSize(new Dimension(80,20));
		btnSendMail.setLocation(200, 50);
		btnSendMail.addActionListener(this);
		btnBar.add(btnSendMail);
		
		this.add(btnBar,BorderLayout.SOUTH);
	}

	public void showFrame()
	{
    this.setVisible(true);		
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

}

