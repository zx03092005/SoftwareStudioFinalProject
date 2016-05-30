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

	PrintWriter writer;
	BufferedReader reader;
	
	JButton loginButton = new JButton("Sign in");
	JButton registerButton = new JButton("Sign up");
	JPasswordField password = new JPasswordField();
	JTextField account = new JTextField();
	JLabel accountLabel = new JLabel("Account : ");
	JLabel passwordLabel = new JLabel("Password : ");
	JDialog userLogin = new JDialog(this, "WELCOME!!!", true);
	
	JPasswordField regPassword = new JPasswordField();
	JTextField regAccount = new JTextField();
	JLabel regAccountLabel = new JLabel("Account : ");
	JLabel regPasswordLabel = new JLabel("Password : ");
	JButton confirmButton = new JButton("Verify");
	JButton cancelButton = new JButton("Cancel");
	JDialog regLogin = new JDialog(this, "Register", true);
	int loginState = 0;
	
	public LoginFrame(Socket socket) {
		try {
			writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (Exception e) {}
		dialogContent();
	}
	
	public void dialogContent() {
		try {
			accountLabel.setBounds(60, 30, 70, 50);
			account.setBounds(120, 40, 200, 30);
			passwordLabel.setBounds(50, 70, 70, 50);
			password.setBounds(120, 80, 200, 30);
			password.setEchoChar('*');
			loginButton.setBounds(120, 130, 70, 30);
			loginButton.addActionListener(e -> {
				writer.println(account.getText());
				writer.println(String.valueOf(password.getPassword()));
				writer.flush();
				check();
			});
			registerButton.setBounds(200, 130, 70, 30);
			registerButton.addActionListener(e -> {
				account.setText("");
				password.setText("");
				regAccount.setText("");
				regPassword.setText("");
				regLogin.setVisible(true);
			});
			userLogin.add(loginButton);
			userLogin.add(registerButton);
			userLogin.add(account);
			userLogin.add(password);
			userLogin.add(passwordLabel);
			userLogin.add(accountLabel);
			
			regAccountLabel.setBounds(60, 30, 70, 50);
			regAccount.setBounds(120, 40, 200, 30);
			regPasswordLabel.setBounds(50, 70, 70, 50);
			regPassword.setBounds(120, 80, 200, 30);
			regPassword.setEchoChar('*');
			confirmButton.setBounds(120, 130, 70, 30);
			confirmButton.addActionListener(e -> {
				loginState = 0;
			});
			cancelButton.setBounds(200, 130, 70, 30);
			cancelButton.addActionListener(e -> {
				regLogin.setVisible(false);
			});
			
			
			regLogin.add(confirmButton);
			regLogin.add(cancelButton);
			regLogin.add(regAccount);
			regLogin.add(regPassword);
			regLogin.add(regPasswordLabel);
			regLogin.add(regAccountLabel);
			
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
			SwingUtilities.updateComponentTreeUI(userLogin);
			SwingUtilities.updateComponentTreeUI(regLogin);

			regLogin.setLayout(null);
			regLogin.setBounds(483, 255, 400, 220);
			regLogin.setVisible(false);
			
			userLogin.setLayout(null);
			userLogin.setBounds(483, 255, 400, 220);
			userLogin.setVisible(true);
			
		} catch(Exception e) {}
	}

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
