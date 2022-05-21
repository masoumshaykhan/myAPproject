package plantsVSzombies;

import java.awt.EventQueue;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MainOnlineGame extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Client c = new Client();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Server is not ready");
			return;
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainOnlineGame frame = new MainOnlineGame();
					frame.setExtendedState(MAXIMIZED_BOTH);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
		 * Create the frame.
		 */
		public void  Online() {
			this.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					Client.close();
					dispose();
				}
			});
		}

	/**
	 * if keys pressed a request to server will be sent and server updates its board
	 */
	//@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP)
			Client.send("Move%%up");

		if (e.getKeyCode() == KeyEvent.VK_DOWN)
			Client.send("Move%%down");

		if (e.getKeyCode() == KeyEvent.VK_LEFT)
			Client.send("Move%%left");

		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
			Client.send("Move%%right");
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			Client.send("Shoot%%");
		}

	}

}
