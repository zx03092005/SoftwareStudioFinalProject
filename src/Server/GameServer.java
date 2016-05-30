package Server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.*;


@SuppressWarnings("serial")
public class GameServer extends JFrame {
	
	private ServerSocket serverSocket;
	private TableWrite userData = new TableWrite(this);
	JTextArea ta = new JTextArea();
	String userAccount, userPassword;
	
	public GameServer(int portNum) {
		userData.init();
		this.setResizable(false);
		this.setBounds(970, 430, 400, 300);
		try {
			serverSocket = new ServerSocket(portNum);
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			SwingUtilities.updateComponentTreeUI(this);
		} catch(Exception e) {}
		this.add(ta);
		this.setVisible(true);
	}
	
	public void runForever() {
		ta.append("Server is waiting ...\n");
		while (true) {
			try {
				Socket connectionToClient = this.serverSocket.accept();
				ta.append("Server is connect!\n");
				ta.append("Player's IP is"
						+ connectionToClient.getInetAddress().toString().replace("/", " ") + "\n");
				ta.append("Player's Port is "
						+ connectionToClient.getLocalPort() + "\n");
				ta.append("Server is waiting ...\n");
				ConnectionThread connThread = new ConnectionThread(connectionToClient);
				connThread.start();
			} catch (Exception e) {}
		}
	}
	
	class ConnectionThread extends Thread {
		private Socket socket;
		private BufferedReader reader;
		private PrintWriter writer;
		
		public ConnectionThread(Socket socket) {
			this.socket = socket;
			try {
				reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
				writer = new PrintWriter(new OutputStreamWriter(this.socket.getOutputStream()));
			} catch (Exception e) {
			}
		}

		public void run() {
			while(true) {
				try {
					GameServer.this.userAccount = this.reader.readLine();
					GameServer.this.userPassword = this.reader.readLine();
					//if(GameServer.this.isPasswordCorrect(GameServer.this.userAccount, GameServer.this.userPassword)) 
					if(GameServer.this.userData.findUser(GameServer.this.userAccount, GameServer.this.userPassword)) {
						sendMessage("LoginSuccess");
						ta.append("user : " + userAccount + " login successed\n");	
					}
					else {
						sendMessage("LoginFailed");
						ta.append("user : " + userAccount + " login failed\n");
					}
				} catch (Exception e) {}
			}
		}
		
		public void sendMessage(String string) {
			writer.println(string);
			writer.flush();
		}
		
	}
	
	public static void main(String[] args) {
		GameServer server = new GameServer(8000);
		server.runForever();
	}

}
