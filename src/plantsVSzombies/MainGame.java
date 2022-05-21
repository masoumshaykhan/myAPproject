package plantsVSzombies;

import java.util.ArrayList;
import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.*;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

class MainGame {
	public static Player player, player2;
	public static int secondTimerClock = 0;
	public static double lastSunCollectTime = 0;
	public static int choosedFlower = 0;
	public static boolean choosedFlowerUsed = false;
	public static boolean cooledDown[] = new boolean[4];
	public static double lastCooledDown[] = new double[4];
	public static double clock = 0; // by second
	public static int zombieCount = 0;
	public static int finalZombieCount = 5; // change with menu
	public static boolean run = true;
	public static boolean gameOver = false;

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ofline();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void menu() {
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
					ofline();
				}).start();
			}
		});
		oflinePart.setBounds(50, 150, 250, 50);
		Panel.add(oflinePart);

		// button
		JButton onlinePart = new JButton();
		onlinePart.setText("Play Online");
		onlinePart.setBackground(Color.GREEN);
		onlinePart.setForeground(Color.GRAY);
		onlinePart.setFont(new Font("Algerian", Font.PLAIN, 20));
		onlinePart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// frame.setVisible(false);
				JOptionPane.showMessageDialog(frame, "This part is not supported yet.");
				online();

			}
		});
		onlinePart.setBounds(50, 250, 250, 50);
		Panel.add(onlinePart);

		// stick
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);// end frame

	}

	public static void online() {
	}

	public static void ofline() {

		Player player = new Player(50);
		// sun
		for (int i = 0; i < 4; i++) {
			cooledDown[i] = true;
			lastCooledDown[i] = 0;
		}
		// Game front preparation
		JFrame frame1 = new JFrame("PVS GAME");
		frame1.setBounds(0, 0, 690, 500);
		frame1.setResizable(false);

		// This panel is for the top bar
		JPanel topTabPanel = new JPanel();
		topTabPanel.setLayout(null);
		topTabPanel.setBounds(0, 0, 675, 90);
		topTabPanel.setBackground(Color.decode("#993300"));
		JButton flower[] = new JButton[4];
		int tempButtonLocation1 = 0;
		int flowerCost[] = new int[4];
		for (int i = 0; i < 4; i++) {
			flowerCost[i] = new Plant(i + 1, -1, -1).getCost();
			flower[i] = new JButton();
			flower[i].setLayout(null);
			tempButtonLocation1 = 100 * i + 15;
			flower[i].setBounds(tempButtonLocation1, 3, 84, 84);
			flower[i].setBackground(Color.orange);
			flower[i].setText(flowerCost[i] + "");
			flower[i].setFont(new Font("Arial", Font.PLAIN, 20));
			flower[i].setForeground(Color.cyan);
			flower[i].setHorizontalTextPosition(JButton.CENTER);
			flower[i].setVerticalTextPosition(JButton.BOTTOM);
			topTabPanel.add(flower[i]);
		}
		flower[0].setIcon(new ImageIcon("D:\\\\eclips2021\\\\LastOne\\\\src\\\\f1.png"));
		flower[1].setIcon(new ImageIcon("D:\\\\eclips2021\\\\LastOne\\\\src\\\\f2.png"));
		flower[2].setIcon(new ImageIcon("D:\\\\eclips2021\\\\LastOne\\\\src\\\\f3.png"));
		flower[3].setIcon(new ImageIcon("D:\\\\eclips2021\\\\LastOne\\\\src\\\\f4.png"));

		JLabel sunLabel = new JLabel();
		sunLabel.setLayout(new GridLayout(1, 0, 0, 0));
		sunLabel.setBounds(410, 10, 140, 70);
		// sunLabel.setBackground(Color.green);
		// sunLabel.setOpaque(true);

		JLabel sunL = new JLabel();
		sunL.setIcon(new ImageIcon("D:\\\\eclips2021\\\\LastOne\\\\src\\\\sun.png"));
		JLabel sunR = new JLabel(player.getSun() + "");
		sunR.setFont(new Font("Arial", Font.PLAIN, 25));
		sunR.setForeground(Color.WHITE);
		sunLabel.add(sunL);
		sunLabel.add(sunR);
		topTabPanel.add(sunLabel);

		JLabel timerLabel = new JLabel();
		timerLabel.setLayout(new GridLayout(1, 0, 0, 0));
		timerLabel.setBounds(575, 50, 100, 35);
		// timerLabel.setBackground(Color.yellow);
		// timerLabel.setOpaque(true);

		JLabel timerL = new JLabel();
		timerL.setIcon(new ImageIcon("D:\\\\eclips2021\\\\LastOne\\\\src\\\\timer.png"));
		JLabel timerR = new JLabel(secondTimerClock + "");
		timerR.setFont(new Font("Arial", Font.PLAIN, 20));
		timerR.setForeground(Color.WHITE);

		timerLabel.add(timerL);
		timerLabel.add(timerR);
		topTabPanel.add(timerLabel);

		// This panel is for the main game panel
		JPanel gamePanel = new JPanel();
		gamePanel.setLayout(null);
		gamePanel.setBackground(Color.green);
		gamePanel.setBounds(0, 90, 700, 375);
		JLabel gameLabel = new JLabel();
		gameLabel.setLayout(null);
		gameLabel.setBounds(0, 90, 675, 375);
		gameLabel.setLayout(new GridLayout(5, 9, 0, 0));

		int n = 5 * 9;
		JButton[] bordButton = new JButton[n];
		Bord[] bord = new Bord[n];
		for (int i = 0; i < n; i++) {
			bordButton[i] = new JButton();
			bord[i] = new Bord(i, bordButton[i]);
			bordButton[i].setText("");
			bordButton[i].setFont(new Font("Arial", Font.PLAIN, 30));
			bordButton[i].setHorizontalTextPosition(JButton.CENTER);
			bordButton[i].setVerticalTextPosition(JButton.BOTTOM);
			if (i % 2 == 0)
				bordButton[i].setBackground(Color.decode("#5EAB60"));
			else
				bordButton[i].setBackground(Color.decode("#74DF76"));
			gameLabel.add(bordButton[i]);
		}
		gamePanel.add(gameLabel);

		// Stick them together
		frame1.add(topTabPanel);
		frame1.add(gamePanel);
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame1.setVisible(true);// end frame

		// Ring and time arrangements
		long lastTime, time;
		int deltaTime; // by milli second
		double lastTimeZombie = player.getLastTimeZombie();
		// Call Listener Action for Buttons
		actionListenerT(flower, player, flowerCost);

		// Game Main Loop
		// I want a list of bullets
		ArrayList<Bullet> bullets = new ArrayList<Bullet>();
		while (run) {
			// Play the chronometer
			lastTime = System.nanoTime();
			actionListenerB(bord, flowerCost, player, clock);
			// Check Cool down for the top buttons of the game
			updateCoolDownFlowers(flower, clock);
			// Highlight the background color of the buttons above
			updateTopFlowers(flower, flowerCost, player);
			// 25 new suns for the player every five seconds
			updateSunByTime(clock, sunR, player);
			// Update the sun panel above the game
			updateSunLabel(sunR, player);
			// Game clock update
			updateSecondTimerClock(clock, timerR);
			// Bullets update
			updateBullets(bullets, bord, clock);
			// The birth of zombies
			if (Math.abs(lastTimeZombie - clock) >= Zombie._coolDown) {
				newZombie(bord, clock);
				lastTimeZombie = clock;
			}
			// Search all board game houses
			for (int i = 0; i < 45; i++) {
				// If you saw a plant
				if (bord[i].getPlantType() != 0) {
					// If you find a plant, check their specific action
					bord[i].getPlant().work(bord, bullets, player, clock);
				}
				// If you saw zombies
				if (bord[i].getZombieType() != 0) {
					// End tile, the game is over
					if (i == ((int) (i / 9) * 9) && bord[i].getZombie().getHeal() > 0) {
						run = false;
						gameOver = true;
					}
					// Zombies walk and destroy
					bord[i].getZombie().move(clock);
					bord[i].getZombie().work(bord, clock);
				}
				// Update the appearance of the game board
				bord[i].updateView(bord, clock);
			}

			// Now calculate the time interval of the first loop to the end of the loop
			time = System.nanoTime();
			deltaTime = (int) ((time - lastTime));
			// Print time to help debug
			// println(clock+"");

			// Now write down all the elapsed time in seconds
			clock += (((double) (deltaTime)) / 1000000000);
		}
		endGame();
		if (gameOver) {
			JOptionPane.showMessageDialog(frame1, " GAME OVER ");
			frame1.setVisible(false);
			// menu();
		} else {
			JOptionPane.showMessageDialog(frame1, " YOU WIN ");
			frame1.setVisible(false);
			// menu();
		}
	}

	public static void endGame() {
		if (gameOver) {
			// JOptionPane.showMessageDialog(frame1, " GAME OVER ");
			println("\t GAME OVER . . .");
		} else {
			// JOptionPane.showMessageDialog(frame1, " YOU WIN ");
			println("\t YOU WIN . . .");
		}
	}

	public static void newZombie(Bord[] bord, double clock) {
		int randLocation;
		if (zombieCount < finalZombieCount) {
			do {
				randLocation = ((int) (Math.random() * 5) + 1) * 9 - 1;
			} while (bord[randLocation].getZombieType() != 0 || bord[randLocation].getPlantType() != 0);
			double randDoNum = Math.random();
			boolean randDo = randDoNum >= 0.5 ? true : false;
			int randType = (int) (Math.random() * 2) + 1;
			if (randDo) {
				bord[randLocation].setZombieType(randType, clock);
				zombieCount++;
			}
		} else if (Bord.allZombieDead(bord)) {
			run = false;
		}
	}

	public static void updateBullets(ArrayList<Bullet> bullets, Bord[] bord, double clock) {
		if (bullets.isEmpty()) {
			return;
		}
		try {
			for (Bullet bullet : bullets) {
				if (!bullet.getHeal()) {
					bullets.remove(bullet);
				} else {
					bullet.updateView(bord, clock);
				}
			}
		} catch (Exception e) {
		}

	}

	public static void updateSecondTimerClock(double clock, JLabel timerR) {
		secondTimerClock = (int) (clock);
		timerR.setText(secondTimerClock + "");
	}

	public static void updateSunByTime(double clock, JLabel sunR, Player player) {
		if (Math.abs(lastSunCollectTime - clock) >= 5) {
			player.addSun(25);
			lastSunCollectTime = clock;
		}
	}

	public static void updateSunLabel(JLabel sunR, Player player) {
		sunR.setText(player.getSun() + "");
	}

	public static void updateCoolDownFlowers(JButton[] flowerButton, double clock) {
		double coolDown[] = new double[4];
		for (int i = 0; i < 4; i++) {
			coolDown[i] = (new Plant(i + 1, -1, clock)).getCoolDown();
			if (choosedFlower == i + 1) {
				lastCooledDown[i] = clock;
			} else if (Math.abs(clock - lastCooledDown[i]) >= coolDown[i]) {
				cooledDown[i] = true;
				lastCooledDown[i] = clock;
			}
		}
	}

	public static void updateTopFlowers(JButton[] flower, int[] cost, Player player) {
		for (int i = 0; i < 4; i++) {
			if (choosedFlower == i + 1) {
				flower[i].setBackground(Color.red);
			} else if (!cooledDown[i] || player.getSun() < cost[i]) {
				flower[i].setBackground(Color.LIGHT_GRAY); // gray
			} else if (cooledDown[i]) {
				flower[i].setBackground(Color.orange);
			}
		}
	}

	public static void actionListenerT(JButton[] topButton, Player player, int[] cost) {
		topButton[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (choosedFlower != 0 && choosedFlower != 1) {
					cooledDown[choosedFlower - 1] = true;
					// choosedFlower = 1;
					// cooledDown[0] = false;
					// choosedFlowerUsed = true;
				}
				if (cooledDown[0] && player.getSun() >= cost[0]) {
					choosedFlower = 1;
					choosedFlowerUsed = true;
					cooledDown[0] = false;
				} else if (choosedFlower == 1) {
					choosedFlower = 0;
					choosedFlowerUsed = true;
					cooledDown[0] = true;
				}
			}
		});
		topButton[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (choosedFlower != 0 && choosedFlower != 2) {
					cooledDown[choosedFlower - 1] = true;
					// choosedFlower = 2;
					// cooledDown[1] = false;
					// choosedFlowerUsed = true;
				}
				if (cooledDown[1] && player.getSun() >= cost[1]) {
					choosedFlower = 2;
					choosedFlowerUsed = true;
					cooledDown[1] = false;
				} else if (choosedFlower == 2) {
					choosedFlower = 0;
					choosedFlowerUsed = true;
					cooledDown[1] = true;
				}
			}
		});
		topButton[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (choosedFlower != 0 && choosedFlower != 3) {
					cooledDown[choosedFlower - 1] = true;
					// choosedFlower = 3;
					// cooledDown[2] = false;
					// choosedFlowerUsed = true;
				}
				if (cooledDown[2] && player.getSun() >= cost[2]) {
					choosedFlower = 3;
					choosedFlowerUsed = true;
					cooledDown[2] = false;
				} else if (choosedFlower == 3) {
					choosedFlower = 0;
					choosedFlowerUsed = true;
					cooledDown[2] = true;
				}
			}
		});
		topButton[3].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (choosedFlower != 0 && choosedFlower != 4) {
					cooledDown[choosedFlower - 1] = true;
					// choosedFlower = 4;
					// cooledDown[3] = false;
					// choosedFlowerUsed = true;
				}
				if (cooledDown[3] && player.getSun() >= cost[3]) {
					choosedFlower = 4;
					choosedFlowerUsed = true;
					cooledDown[3] = false;
				} else if (choosedFlower == 4) {
					choosedFlower = 0;
					choosedFlowerUsed = true;
					cooledDown[3] = true;
				}
			}
		});
	}

	public static void actionListenerB(Bord[] bord, int[] cost, Player player, double clock) {
		bord[0].getButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (bord[0].getPlantType() == 0 && bord[0].getZombieType() == 0 && choosedFlower != 0) {
					bord[0].setPlantType(choosedFlower, clock);
					player.pay(cost, choosedFlower - 1);
					choosedFlower = 0;
				}
			}
		});
		bord[1].getButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (bord[1].getPlantType() == 0 && bord[1].getZombieType() == 0 && choosedFlower != 0) {
					bord[1].setPlantType(choosedFlower, clock);
					player.pay(cost, choosedFlower - 1);
					choosedFlower = 0;
				}
			}
		});
		bord[2].getButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (bord[2].getPlantType() == 0 && bord[2].getZombieType() == 0 && choosedFlower != 0) {
					bord[2].setPlantType(choosedFlower, clock);
					player.pay(cost, choosedFlower - 1);
					choosedFlower = 0;
				}
			}
		});
		bord[3].getButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (bord[3].getPlantType() == 0 && bord[3].getZombieType() == 0 && choosedFlower != 0) {
					bord[3].setPlantType(choosedFlower, clock);
					player.pay(cost, choosedFlower - 1);
					choosedFlower = 0;
				}
			}
		});
		bord[4].getButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (bord[4].getPlantType() == 0 && bord[4].getZombieType() == 0 && choosedFlower != 0) {
					bord[4].setPlantType(choosedFlower, clock);
					player.pay(cost, choosedFlower - 1);
					choosedFlower = 0;
				}
			}
		});
		bord[5].getButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (bord[5].getPlantType() == 0 && bord[5].getZombieType() == 0 && choosedFlower != 0) {
					bord[5].setPlantType(choosedFlower, clock);
					player.pay(cost, choosedFlower != 0 ? choosedFlower - 1 : 0);
					choosedFlower = 0;
				}
			}
		});
		bord[6].getButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (bord[6].getPlantType() == 0 && bord[6].getZombieType() == 0 && choosedFlower != 0) {
					bord[6].setPlantType(choosedFlower, clock);
					player.pay(cost, choosedFlower != 0 ? choosedFlower - 1 : 0);
					choosedFlower = 0;
				}
			}
		});
		bord[7].getButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (bord[7].getPlantType() == 0 && bord[7].getZombieType() == 0 && choosedFlower != 0) {
					bord[7].setPlantType(choosedFlower, clock);
					player.pay(cost, choosedFlower != 0 ? choosedFlower - 1 : 0);
					choosedFlower = 0;
				}
			}
		});
		bord[8].getButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (bord[8].getPlantType() == 0 && bord[8].getZombieType() == 0 && choosedFlower != 0) {
					bord[8].setPlantType(choosedFlower, clock);
					player.pay(cost, choosedFlower != 0 ? choosedFlower - 1 : 0);
					choosedFlower = 0;
				}
			}
		});
		bord[9].getButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (bord[9].getPlantType() == 0 && bord[9].getZombieType() == 0 && choosedFlower != 0) {
					bord[9].setPlantType(choosedFlower, clock);
					player.pay(cost, choosedFlower != 0 ? choosedFlower - 1 : 0);
					choosedFlower = 0;
				}
			}
		});
		bord[10].getButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (bord[10].getPlantType() == 0 && bord[10].getZombieType() == 0 && choosedFlower != 0) {
					bord[10].setPlantType(choosedFlower, clock);
					player.pay(cost, choosedFlower != 0 ? choosedFlower - 1 : 0);
					choosedFlower = 0;
				}
			}
		});
		bord[11].getButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (bord[11].getPlantType() == 0 && bord[11].getZombieType() == 0 && choosedFlower != 0) {
					bord[11].setPlantType(choosedFlower, clock);
					player.pay(cost, choosedFlower != 0 ? choosedFlower - 1 : 0);
					choosedFlower = 0;
				}
			}
		});
		bord[12].getButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (bord[12].getPlantType() == 0 && bord[12].getZombieType() == 0 && choosedFlower != 0) {
					bord[12].setPlantType(choosedFlower, clock);
					player.pay(cost, choosedFlower != 0 ? choosedFlower - 1 : 0);
					choosedFlower = 0;
				}
			}
		});
		bord[13].getButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (bord[13].getPlantType() == 0 && bord[13].getZombieType() == 0 && choosedFlower != 0) {
					bord[13].setPlantType(choosedFlower, clock);
					player.pay(cost, choosedFlower != 0 ? choosedFlower - 1 : 0);
					choosedFlower = 0;
				}
			}
		});
		bord[14].getButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (bord[14].getPlantType() == 0 && bord[14].getZombieType() == 0 && choosedFlower != 0) {
					bord[14].setPlantType(choosedFlower, clock);
					player.pay(cost, choosedFlower != 0 ? choosedFlower - 1 : 0);
					choosedFlower = 0;
				}
			}
		});
		bord[15].getButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (bord[15].getPlantType() == 0 && bord[15].getZombieType() == 0 && choosedFlower != 0) {
					bord[15].setPlantType(choosedFlower, clock);
					player.pay(cost, choosedFlower != 0 ? choosedFlower - 1 : 0);
					choosedFlower = 0;
				}
			}
		});
		bord[16].getButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (bord[16].getPlantType() == 0 && bord[16].getZombieType() == 0 && choosedFlower != 0) {
					bord[16].setPlantType(choosedFlower, clock);
					player.pay(cost, choosedFlower != 0 ? choosedFlower - 1 : 0);
					choosedFlower = 0;
				}
			}
		});
		bord[17].getButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (bord[17].getPlantType() == 0 && bord[17].getZombieType() == 0 && choosedFlower != 0) {
					bord[17].setPlantType(choosedFlower, clock);
					player.pay(cost, choosedFlower != 0 ? choosedFlower - 1 : 0);
					choosedFlower = 0;
				}
			}
		});
		bord[18].getButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (bord[18].getPlantType() == 0 && bord[18].getZombieType() == 0 && choosedFlower != 0) {
					bord[18].setPlantType(choosedFlower, clock);
					player.pay(cost, choosedFlower != 0 ? choosedFlower - 1 : 0);
					choosedFlower = 0;
				}
			}
		});
		bord[19].getButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (bord[19].getPlantType() == 0 && bord[19].getZombieType() == 0 && choosedFlower != 0) {
					bord[19].setPlantType(choosedFlower, clock);
					player.pay(cost, choosedFlower != 0 ? choosedFlower - 1 : 0);
					choosedFlower = 0;
				}
			}
		});
		bord[20].getButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (bord[20].getPlantType() == 0 && bord[20].getZombieType() == 0 && choosedFlower != 0) {
					bord[20].setPlantType(choosedFlower, clock);
					player.pay(cost, choosedFlower != 0 ? choosedFlower - 1 : 0);
					choosedFlower = 0;
				}
			}
		});
		bord[21].getButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (bord[21].getPlantType() == 0 && bord[21].getZombieType() == 0 && choosedFlower != 0) {
					bord[21].setPlantType(choosedFlower, clock);
					player.pay(cost, choosedFlower != 0 ? choosedFlower - 1 : 0);
					choosedFlower = 0;
				}
			}
		});
		bord[22].getButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (bord[22].getPlantType() == 0 && bord[22].getZombieType() == 0 && choosedFlower != 0) {
					bord[22].setPlantType(choosedFlower, clock);
					player.pay(cost, choosedFlower != 0 ? choosedFlower - 1 : 0);
					choosedFlower = 0;
				}
			}
		});
		bord[23].getButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (bord[23].getPlantType() == 0 && bord[23].getZombieType() == 0 && choosedFlower != 0) {
					bord[23].setPlantType(choosedFlower, clock);
					player.pay(cost, choosedFlower != 0 ? choosedFlower - 1 : 0);
					choosedFlower = 0;
				}
			}
		});
		bord[24].getButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (bord[24].getPlantType() == 0 && bord[24].getZombieType() == 0 && choosedFlower != 0) {
					bord[24].setPlantType(choosedFlower, clock);
					player.pay(cost, choosedFlower != 0 ? choosedFlower - 1 : 0);
					choosedFlower = 0;
				}
			}
		});
		bord[25].getButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (bord[25].getPlantType() == 0 && bord[25].getZombieType() == 0 && choosedFlower != 0) {
					bord[25].setPlantType(choosedFlower, clock);
					player.pay(cost, choosedFlower != 0 ? choosedFlower - 1 : 0);
					choosedFlower = 0;
				}
			}
		});
		bord[26].getButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (bord[26].getPlantType() == 0 && bord[26].getZombieType() == 0 && choosedFlower != 0) {
					bord[26].setPlantType(choosedFlower, clock);
					player.pay(cost, choosedFlower != 0 ? choosedFlower - 1 : 0);
					choosedFlower = 0;
				}
			}
		});
		bord[27].getButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (bord[27].getPlantType() == 0 && bord[27].getZombieType() == 0 && choosedFlower != 0) {
					bord[27].setPlantType(choosedFlower, clock);
					player.pay(cost, choosedFlower != 0 ? choosedFlower - 1 : 0);
					choosedFlower = 0;
				}
			}
		});
		bord[28].getButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (bord[28].getPlantType() == 0 && bord[28].getZombieType() == 0 && choosedFlower != 0) {
					bord[28].setPlantType(choosedFlower, clock);
					player.pay(cost, choosedFlower != 0 ? choosedFlower - 1 : 0);
					choosedFlower = 0;
				}
			}
		});
		bord[29].getButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (bord[29].getPlantType() == 0 && bord[29].getZombieType() == 0 && choosedFlower != 0) {
					bord[29].setPlantType(choosedFlower, clock);
					player.pay(cost, choosedFlower != 0 ? choosedFlower - 1 : 0);
					choosedFlower = 0;
				}
			}
		});
		bord[30].getButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (bord[30].getPlantType() == 0 && bord[30].getZombieType() == 0 && choosedFlower != 0) {
					bord[30].setPlantType(choosedFlower, clock);
					player.pay(cost, choosedFlower != 0 ? choosedFlower - 1 : 0);
					choosedFlower = 0;
				}
			}
		});
		bord[31].getButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (bord[31].getPlantType() == 0 && bord[31].getZombieType() == 0 && choosedFlower != 0) {
					bord[31].setPlantType(choosedFlower, clock);
					player.pay(cost, choosedFlower != 0 ? choosedFlower - 1 : 0);
					choosedFlower = 0;
				}
			}
		});
		bord[32].getButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (bord[32].getPlantType() == 0 && bord[32].getZombieType() == 0 && choosedFlower != 0) {
					bord[32].setPlantType(choosedFlower, clock);
					player.pay(cost, choosedFlower != 0 ? choosedFlower - 1 : 0);
					choosedFlower = 0;
				}
			}
		});
		bord[33].getButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (bord[33].getPlantType() == 0 && bord[33].getZombieType() == 0 && choosedFlower != 0) {
					bord[33].setPlantType(choosedFlower, clock);
					player.pay(cost, choosedFlower != 0 ? choosedFlower - 1 : 0);
					choosedFlower = 0;
				}
			}
		});
		bord[34].getButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (bord[34].getPlantType() == 0 && bord[34].getZombieType() == 0 && choosedFlower != 0) {
					bord[34].setPlantType(choosedFlower, clock);
					player.pay(cost, choosedFlower != 0 ? choosedFlower - 1 : 0);
					choosedFlower = 0;
				}
			}
		});
		bord[35].getButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (bord[35].getPlantType() == 0 && bord[35].getZombieType() == 0 && choosedFlower != 0) {
					bord[35].setPlantType(choosedFlower, clock);
					player.pay(cost, choosedFlower != 0 ? choosedFlower - 1 : 0);
					choosedFlower = 0;
				}
			}
		});
		bord[36].getButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (bord[36].getPlantType() == 0 && bord[36].getZombieType() == 0 && choosedFlower != 0) {
					bord[36].setPlantType(choosedFlower, clock);
					player.pay(cost, choosedFlower != 0 ? choosedFlower - 1 : 0);
					choosedFlower = 0;
				}
			}
		});
		bord[37].getButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (bord[37].getPlantType() == 0 && bord[37].getZombieType() == 0 && choosedFlower != 0) {
					bord[37].setPlantType(choosedFlower, clock);
					player.pay(cost, choosedFlower != 0 ? choosedFlower - 1 : 0);
					choosedFlower = 0;
				}
			}
		});
		bord[38].getButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (bord[38].getPlantType() == 0 && bord[38].getZombieType() == 0 && choosedFlower != 0) {
					bord[38].setPlantType(choosedFlower, clock);
					player.pay(cost, choosedFlower != 0 ? choosedFlower - 1 : 0);
					choosedFlower = 0;
				}
			}
		});
		bord[39].getButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (bord[39].getPlantType() == 0 && bord[39].getZombieType() == 0 && choosedFlower != 0) {
					bord[39].setPlantType(choosedFlower, clock);
					player.pay(cost, choosedFlower != 0 ? choosedFlower - 1 : 0);
					choosedFlower = 0;
				}
			}
		});
		bord[40].getButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (bord[40].getPlantType() == 0 && bord[40].getZombieType() == 0 && choosedFlower != 0) {
					bord[40].setPlantType(choosedFlower, clock);
					player.pay(cost, choosedFlower != 0 ? choosedFlower - 1 : 0);
					choosedFlower = 0;
				}
			}
		});
		bord[41].getButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (bord[41].getPlantType() == 0 && bord[41].getZombieType() == 0 && choosedFlower != 0) {
					bord[41].setPlantType(choosedFlower, clock);
					player.pay(cost, choosedFlower != 0 ? choosedFlower - 1 : 0);
					choosedFlower = 0;
				}
			}
		});
		bord[42].getButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (bord[42].getPlantType() == 0 && bord[42].getZombieType() == 0 && choosedFlower != 0) {
					bord[42].setPlantType(choosedFlower, clock);
					player.pay(cost, choosedFlower != 0 ? choosedFlower - 1 : 0);
					choosedFlower = 0;
				}
			}
		});
		bord[43].getButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (bord[43].getPlantType() == 0 && bord[43].getZombieType() == 0 && choosedFlower != 0) {
					bord[43].setPlantType(choosedFlower, clock);
					player.pay(cost, choosedFlower != 0 ? choosedFlower - 1 : 0);
					choosedFlower = 0;
				}
			}
		});
		bord[44].getButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (bord[44].getPlantType() == 0 && bord[44].getZombieType() == 0 && choosedFlower != 0) {
					bord[44].setPlantType(choosedFlower, clock);
					player.pay(cost, choosedFlower != 0 ? choosedFlower - 1 : 0);
					choosedFlower = 0;
				}
			}
		});

	}

	public static void print(String str) {
		System.out.print(str);
	}

	public static void println(String str) {
		System.out.println(str);
	}
}
