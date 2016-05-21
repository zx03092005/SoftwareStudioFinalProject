package Client;

import javax.swing.JFrame;

public class GameClient {
	public static void main(String[] args) {
		MyWindow window = new MyWindow();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setBounds(0, 0, 1000, 650);
		window.setVisible(true);
	}
}
