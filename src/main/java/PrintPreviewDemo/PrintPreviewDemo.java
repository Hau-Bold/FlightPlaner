package PrintPreviewDemo;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.PrinterJob;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.OrientationRequested;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;

public class PrintPreviewDemo extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextPane textPane;
	private JPanel panel;
	private JButton previewButton;
	private JTable table;

	/**
	 * Constructor.
	 * @param jTable 
	 */
	public PrintPreviewDemo(JTable table) {
		this.table=table;
		initComponent();
	}

	private void initComponent() {
		this.setTitle("Printer preview demo");
		this.setSize(600, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panel = new JPanel(new BorderLayout());
		textPane = new JTextPane();
		textPane.setContentType("text/html");

		/** generates the tabel: */
		StringBuilder builder = new StringBuilder();
		builder.append("<h1>Header</h1><table width=\"100%\">");
		for(int row=0;row<table.getRowCount();row++)
		{
			builder.append("<tr>");
			for(int col=0;col<table.getColumnCount();col++)
			{
				builder.append("<td>" + table.getModel().getValueAt(row, col) + "</td>");
				
			}
			builder.append("</tr>");

		}
		builder.append("</table>");

		textPane.setText(builder.toString());

		previewButton = new JButton("Preview");
		previewButton.addActionListener(this);

		panel.add(new JScrollPane(textPane), BorderLayout.CENTER);
		panel.add(previewButton, BorderLayout.SOUTH);
		this.getContentPane().add(panel);

	}

	public void showFrame() {
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent event) {

		Object o = event.getSource();

		if (o.equals(previewButton)) {
			JDialog dialog = new JDialog();
			dialog.setModal(true);
			dialog.setSize(700, 400);
			dialog.setLayout(new BorderLayout());

			HashPrintRequestAttributeSet set = new HashPrintRequestAttributeSet();
			set.add(MediaSizeName.ISO_A4);
			set.add(OrientationRequested.PORTRAIT);
			PageFormat pf = PrinterJob.getPrinterJob().getPageFormat(set);
			// PageFormat can be also prompted from user with
			// PrinterJob.pageDialog()
			final PrintPreview preview = new PrintPreview(textPane.getPrintable(null, null), pf);

			JButton printButton = new JButton("Print!");
			printButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					preview.print();
				}
			});

			JPanel buttonsPanel = new JPanel();
			buttonsPanel.add(printButton);
			buttonsPanel.add(preview.getControls());

			dialog.getContentPane().add(preview, BorderLayout.CENTER);
			dialog.getContentPane().add(buttonsPanel, BorderLayout.SOUTH);

			dialog.setVisible(true);
		}
	}
}
