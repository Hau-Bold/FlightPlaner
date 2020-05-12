package Routenplaner;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import animation.AnimatePlane;
import animation.AnimateTarget;
import animation.Plane;
import client.FlightPlaner;
import gps_coordinates.GpsCoordinate;
import routePlanningService.Impl.RoutePlanningHelper;

public class FrameMap extends JFrame implements ActionListener, ChangeListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static FrameMap frameMap;
	private JButton btnShowTargets, btn_showRoute, btnStartFlight;
	private JToolBar toolbar;
	private static int counter1 = 0;
	private static int counter2 = 0;
	private ArrayList<Thread> threadlist;
	private final int xwidthPanelMap = 1000;
	private final int yheightPanelMap = 1000;
	public static JScrollPane scrollmap;
	private List<GpsCoordinate> gpsCoordinates;
	private Thread t1;
	private static ImagePanel imagepanel;
	private Plane plane;
	private Point pt;
	private Target labeltarget;
	private static ArrayList<Point> targetList;
	private ArrayList<Target> labellist = new ArrayList<Target>();

	public static ImagePanel getImagepanel() {
		return imagepanel;
	}

	private FrameMap(List<GpsCoordinate> computedRoute) {
		this.gpsCoordinates = computedRoute;
		targetList = new ArrayList<Point>();
		computedRoute.forEach(gps -> targetList.add(RoutePlanningHelper.gpsToMiller(gps)));
		initComponent();
		showFrame();
	}

	private void initComponent() {
		this.setBounds(0, 0, xwidthPanelMap, yheightPanelMap);
		this.setLayout(new BorderLayout());
		this.setResizable(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		BufferedImage bufferedimage = null;
		// load map into bufferedimage:
		try {
			// bufferedimage =
			// ImageIO.read(getClass().getResource("../Images/zeitzonen.png"));
			bufferedimage = ImageIO.read(new File(
					FlightPlaner.getInstance().getPathToImageFolder() + File.separator + "zeitzonen.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		imagepanel = new ImagePanel(bufferedimage);

		// Speichere Graphik im Jscrollpane
		scrollmap = new JScrollPane(imagepanel);
		// Vertikales Scrollen
		scrollmap.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		// Horizontales Scrollen
		scrollmap.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		this.add(scrollmap, BorderLayout.CENTER);
		scrollmap.getViewport().addChangeListener(this);

		// F�ge Flieger hinzu
		plane = new Plane(new Point(40, 50));
		imagepanel.add(plane);

		/*
		 * F�ge Labels f�r Targets hinzu: und Threads der threadlist:
		 */
		toolbar = new JToolBar();
		toolbar.setPreferredSize(new Dimension(xwidthPanelMap, 20));

		btnShowTargets = new JButton("Targets");
		btnShowTargets.setPreferredSize(new Dimension(80, 20));
		btnShowTargets.addActionListener(this);

		btn_showRoute = new JButton("Show Route");
		btn_showRoute.setPreferredSize(new Dimension(80, 20));
		btn_showRoute.addActionListener(this);

		btnStartFlight = new JButton("Start Flight");
		btnStartFlight.setPreferredSize(new Dimension(80, 20));
		btnStartFlight.addActionListener(this);

		toolbar.add(btnShowTargets);
		toolbar.add(btn_showRoute);
		toolbar.add(btnStartFlight);

		this.add(toolbar, BorderLayout.PAGE_END);
	}

	public void showFrame() {
		this.setVisible(true);
		threadlist = new ArrayList<Thread>();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnShowTargets)) {
			btn_showRoute.setEnabled(false);
			if (counter1 % 2 == 0) {
				btnShowTargets.setText("Hide");
				for (int i = 0; i < targetList.size(); i++) {
					pt = targetList.get(i);
					labeltarget = new Target(pt);
					labellist.add(labeltarget);
					imagepanel.add(labeltarget);
					t1 = new Thread(new AnimateTarget(labeltarget));
					threadlist.add(t1);
					t1.start();
				}
			}
			if (counter1 % 2 == 1) {
				btn_showRoute.setEnabled(true);
				btnShowTargets.setText("Targets");
				for (Thread t : threadlist) {
					t.stop();
				}

				threadlist.clear();

				for (Target l : labellist) {
					l.setIcon(null);
					l.repaint();
				}

				labellist.clear();
			}
			counter1++;
		}

		else if (o.equals(btn_showRoute)) {

			if (counter2 % 2 == 0) {
				btnShowTargets.setEnabled(false);
				RoutePlanningHelper.drawRoute(targetList, imagepanel);
				btn_showRoute.setText("Hide Route");

			}
			if (counter2 % 2 == 1) {
				btn_showRoute.setText("Show Route");
				FrameMap.scrollmap.repaint();
				btnShowTargets.setEnabled(true);
			}
			counter2++;
		}

		if (o.equals(btnStartFlight)) {
			Thread flight = new Thread(new AnimatePlane(plane, targetList));
			flight.start();
		}
	}

	// Targets sollen nicht verschoben werden:
	// L�sche alte Threads, und initialisiere Threads erneut
	// nach Wartezeit
	@Override
	public void stateChanged(ChangeEvent e) {
		if (btnShowTargets.getText().equals("Hide")) {
			for (Thread t : threadlist) {
				t.stop();
			}

			threadlist.clear();

			for (Target l : labellist) {
				l.setIcon(null);
				l.repaint();
			}

			labellist.clear();

			try {
				Thread.sleep(100);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			for (int i = 0; i < targetList.size(); i++) {
				pt = targetList.get(i);
				labeltarget = new Target(pt);
				labellist.add(labeltarget);
				imagepanel.add(labeltarget);
				t1 = new Thread(new AnimateTarget(labeltarget));
				threadlist.add(t1);
				t1.start();
			}

		}

		else if (btn_showRoute.getText().equals("Hide Route")) {
			RoutePlanningHelper.drawRoute(targetList, imagepanel);
		}

	}

	public static FrameMap getInstance(List<GpsCoordinate> computedRoute) {
		if (FrameMap.frameMap == null) {
			frameMap = new FrameMap(computedRoute);
			return frameMap;
		} else {
			return null;
		}
	}
}
