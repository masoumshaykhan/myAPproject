package plantsVSzombies;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Server {

	public static Player player2, player3;
	public static int weight = 5;
	public static int[] board = new int[weight];
	public static Timer t = new Timer();

	public static void main(String[] args) {
		System.out.println("Server started.");
		ServerSocket server = null;
		
		putPlayer();
		putRandomMane();
		spawnLife();
		try {
			// wait for players to connect
			server = new ServerSocket(1337);
			System.out.println("Waiting for player 1");
			Socket socket1 = server.accept();
			System.out.println("Player 1 Connected");
			System.out.println("Waiting for player 2");
			Socket socket2 = server.accept();
			System.out.println("Player 2 Connected");
			MyThread t1 = new MyThread(socket1, 2);
			t1.start();
			MyThread t2 = new MyThread(socket2, 3);
			t2.start();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				server.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * put 2 players in random positions
	 * bayad bekaram mn
	 */
	public static void putPlayer() {
	}

	/**
	 * put some mane in random positions. the number of mane is 0.4*height*weight
	 * inm niaz nadarm ps
	 */
	public static void putRandomMane() {
		
	}

	/**
	 * spawn a extra life every 20 seconds in an empty cell
	 * va in
	 */
	public static void spawnLife() {
		
	}

	static class MyThread extends Thread {
		private int p;

		Socket socket;
		DataInputStream in;
		DataOutputStream out;
		ObjectOutputStream objOut;
		public long lastshoot2, reloadtime2, lastshoot3, reloadtime3;
		public boolean firstshoot2 = true, firstshoot3 = true;

		/**
		 * create a thread for each player
		 * 
		 * @param socket the client socket
		 * @param p      it could be 2 and 3. 2 for player 1 and 3 for player 2
		 */
		public MyThread(Socket socket, int p) {
			this.socket = socket;
			this.p = p;
		}

		/**
		 * method to send message to client through socket
		 * 
		 * @param str the message
		 */
		public void send(String str) {
			try {
				out.writeUTF(str);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		/**
		 * method to read a message from client through socket
		 * 
		 * @return the message that been sent by client
		 * @throws EOFException
		 */
		public String read() throws EOFException {
			String ans = "";
			try {
				ans = in.readUTF();
			} catch (IOException e) {
				try {
					socket.close();
				} catch (IOException e1) {
				}
			}
			return ans;
		}

		/**
		 * send board every 50 milliseconds (20 fps) to client
		 */
		public void sendBoard() {
			// Timer t = new Timer();
			t.schedule(new TimerTask() {
				@Override
				public void run(){
					try {
						objOut = new ObjectOutputStream(socket.getOutputStream());
						// create a BoardPlayer that contains data of board and 2 players and sent it to
						// socket. this is because of not writing multiple object once on socket
						Bord bp = new Bord(board, player2, player3);
						
						objOut.writeObject(bp);
						objOut.flush();
					} catch (IOException e) {
						try {
							socket.close();
						} catch (IOException e1) {
						}
					}

				}
			}, 0, 50);
		}

		/**
		 * if a player die. respawn it after 5 seconds in random position
		 * 
		 * @param p player 1 or player 2
		 */
		private void respawnPlayer(int p) {
			
		}

		/**
		 * if player 2 get shot its hp will be decreased by 1
		 */
		private void player2GetShot() {
			
		}

		/**
		 * if player 3 get shot its hp will be decreased by 1
		 */
		private void player3GetShot() {
			
		}

		/**
		 * player shooting method that check the direction player facing and shoot.
		 */
		private void shoot() {
			
		}

		public void run() {

			try {
				in = new DataInputStream(this.socket.getInputStream());
				out = new DataOutputStream(this.socket.getOutputStream());
				send("ready");
				sendBoard();

				// keep reading input from client
				while (!socket.isClosed()) {
					String[] inp = read().split("%%");
					if (inp[0].equals("Move")) {
						/*if (inp[1].equals("down"))
							moveDown();
						else if (inp[1].equals("up"))
							moveUp();
						else if (inp[1].equals("right"))
							moveRight();
						else if (inp[1].equals("left"))
							moveLeft();*/
					}
					if (inp[0].equals("Shoot")) {
						shoot();

					}
					if (inp[0].equals("PickLife")) {

					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
