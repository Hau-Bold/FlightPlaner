package listeners;

import java.awt.Frame;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import contextmenu.CommonContextMenu;
import contextmenu.TargetsContextMenu;
import toolbar.ConfirmingAddress;

/**
 * 
 * the class RoutePlanerWindowListener;
 *
 */
public class RoutePlanerWindowListener implements WindowListener {

	private ConfirmingAddress confirmingAddress;
	private CommonContextMenu contextMenu;

	

	/**
	 * Constructor.
	 * @param confirmingAddress - the confiming Address dialog
	 * @param contextMenu 
	 */
	public RoutePlanerWindowListener(ConfirmingAddress confirmingAddress) {
		this.confirmingAddress=confirmingAddress;
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		confirmingAddress.setState(Frame.NORMAL);
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		confirmingAddress.setState(Frame.ICONIFIED);
		
		if(contextMenu != null)
		{
			contextMenu.dispose();
		}

	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}
	
	public void setContextMenu(CommonContextMenu contextMenu) {
		this.contextMenu = contextMenu;
	}

}
