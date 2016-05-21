package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

@SuppressWarnings("serial")
public class LoginFrame extends JFrame /*implements Runnable */{

	Socket socket;
	PrintWriter writer;
	BufferedReader reader;
	
	JButton loginButton = new JButton("Log in");
	JPasswordField password = new JPasswordField();
	JTextField account = new JTextField();
	JLabel accountLabel = new JLabel("Account : ");
	JLabel passwordLabel = new JLabel("Password : ");
	JDialog userLogin = new JDialog(this, "WELCOME!!!", true);
	
	int state = 0; // wait for server
	
	public LoginFrame() {
		connect("127.0.0.1", 8000);
		dialogContent();
	}
	
	public void dialogContent() {
		try {
			accountLabel.setBounds(60, 30, 70, 50);
			account.setBounds(120, 40, 200, 30);
			passwordLabel.setBounds(50, 70, 70, 50);
			password.setBounds(120, 80, 200, 30);
			password.setEchoChar('*');
			loginButton.setBounds(165, 130, 70, 30);
			loginButton.addActionListener(e -> {
				writer.println(account.getText());
				writer.println(String.valueOf(password.getPassword()));
				writer.flush();
				check();
			});
			
			userLogin.add(loginButton);
			userLogin.add(account);
			userLogin.add(password);
			userLogin.add(passwordLabel);
			userLogin.add(accountLabel);
			
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
			SwingUtilities.updateComponentTreeUI(userLogin);
			
			userLogin.setLayout(null);
			userLogin.setBounds(0, 0, 400, 220);
			userLogin.setVisible(true);
			
		} catch(Exception e) {}
	}
	
	public void connect(String ip, int port) {
		try {
			socket = new Socket(ip, port);
			writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (Exception e) {}
	}
	
	/*public void run() {
		//while(true) {
			try {
				String test = reader.readLine().replace("\n", "");
				//System.out.println(test);
				if(test.equals("LoginSuccess")) {
					userLogin.setVisible(false);
					//break;
				}
				else {
					account.setText("");
					password.setText("");
					JOptionPane.showMessageDialog(userLogin, "Please Try Again!", "ERROR", ERROR);
				}
			} catch (IOException e) {}
		//}
		//System.out.println("OK");
	}*/
	public void check() {
		while(true) {
			try {
				String test = reader.readLine().replace("\n", "");
				if(test.equals("LoginFailed")) {
					password.setText("");
					JOptionPane.showMessageDialog(userLogin, "Please Try Again", "ERROR", JOptionPane.ERROR_MESSAGE);
					break;
				}
				else if(test.equals("LoginSuccess")) {
					userLogin.setVisible(false);
					break;
				}
			} catch (IOException e) {}
		}
	}
}
