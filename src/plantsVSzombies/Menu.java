package plantsVSzombies;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;;

public class Menu extends JFrame {

	private static final long serialVersionUID = 1L;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Menu frame = new Menu();
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
	public Menu() {
		// Game Menu
		JFrame frame = new JFrame("PVS MENU");
		frame.setBounds(0, 0, 690, 500);
		frame.setResizable(false);
		// panel
		JPanel Panel = new JPanel();
		Panel.setLayout(null);
		Panel.setBounds(0, 0, 690, 500);
		Panel.setBackground(Color.black);
		frame.add(Panel);
		// label
		JLabel label = new JLabel();
		label.setText("Plants VS Zombies");
		label.setForeground(Color.GREEN);
		label.setFont(new Font("CurlZ MT", Font.BOLD, 30));
		label.setBounds(50, 50, 250, 100);
		Panel.add(label);
		// button
		// Game class will run if Single player been clicked
		JButton oflinePart = new JButton();
		oflinePart.setText("Play Ofline");
		oflinePart.setBackground(Color.GREEN);
		oflinePart.setForeground(Color.GRAY);
		oflinePart.setFont(new Font("Algerian", Font.PLAIN, 20));
		oflinePart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(false);
				new Thread(() -> {
					MainGame.ofline();
				}).start();
			}
		});
		oflinePart.setBounds(50, 150, 250, 50);
		Panel.add(oflinePart);

		// button
		// connect player 2 to sever
		JButton onlinePart = new JButton();
		onlinePart.setText("Play Online");
		onlinePart.setBackground(Color.GREEN);
		onlinePart.setForeground(Color.GRAY);
		onlinePart.setFont(new Font("Algerian", Font.PLAIN, 20));
		onlinePart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(false);
				//JOptionPane.showMessageDialog(frame, "This part is not supported yet.");
				Runnable runnable2 = () -> {
					MainOnlineGame.main(null);
				};
				Thread t2 = new Thread(runnable2);
				t2.start();

			}
		});
		onlinePart.setBounds(50, 250, 250, 50);
		Panel.add(onlinePart);

		//button
		// Server runs and 1 client connect to it
		JButton btnServer = new JButton("Server");
		btnServer.setBackground(Color.GREEN);
		btnServer.setForeground(Color.GRAY);
		btnServer.setFont(new Font("Algerian", Font.PLAIN, 20));
		btnServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Runnable runnable = () -> {
					Server.main(null);
				};
				Thread t = new Thread(runnable);
				t.start();
				Runnable runnable2 = () -> {
					// OnlineGame.main(null);
				};
				Thread t2 = new Thread(runnable2);
				t2.start();
			}
		});
		btnServer.setBounds(50, 350, 250, 50);
		Panel.add(btnServer);
		
		// stick
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);// end frame

	}

}
