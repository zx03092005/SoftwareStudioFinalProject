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
	JPasswordField regCheckPassword = new JPasswordField();
	JTextField regAccount = new JTextField();
	JLabel regAccountLabel = new JLabel("Account : ");
	JLabel regPasswordLabel = new JLabel("Password : ");
	JLabel regCheckPasswordLabel = new JLabel("Repeat Password : ");
	JButton confirmButton = new JButton("Verify");
	JButton cancelButton = new JButton("Cancel");
	JDialog regLogin = new JDialog(this, "Register", true);
	
	public LoginFrame(Socket socket) {
		try {
			writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (Exception e) {}
		dialogContent();
	}
	
	@SuppressWarnings("deprecation")
	public void dialogContent() {
		try {
			accountLabel.setBounds(63, 30, 70, 50);
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
				regPassword.setText("");;
				regLogin.setVisible(true);
			});
			userLogin.add(loginButton);
			userLogin.add(registerButton);
			userLogin.add(account);
			userLogin.add(password);
			userLogin.add(passwordLabel);
			userLogin.add(accountLabel);
			
			regAccountLabel.setBounds(73, 30, 70, 50);
			regAccount.setBounds(130, 40, 200, 30);
			regPasswordLabel.setBounds(60, 70, 70, 50);
			regPassword.setBounds(130, 80, 200, 30);
			regPassword.setEchoChar('*');
			regCheckPasswordLabel.setBounds(17, 110, 150, 50);
			regCheckPassword.setBounds(130, 120, 200, 30);
			regCheckPassword.setEchoChar('*');
			confirmButton.setBounds(120, 170, 70, 30);
			confirmButton.addActionListener(e -> {
				if(regPassword.getText().equals(regCheckPassword.getText())) {
					writer.println("new@"+regAccount.getText());
					writer.println("new@"+String.valueOf(regPassword.getPassword()));
					writer.flush();
					check();
				}
				else {
					JOptionPane.showMessageDialog(regLogin, "Two passwords are not the same", "ERROR", JOptionPane.ERROR_MESSAGE);
					regPassword.setText("");
					regCheckPassword.setText("");
				}
			});
			cancelButton.setBounds(200, 170, 70, 30);
			cancelButton.addActionListener(e -> {
				regLogin.setVisible(false);
			});
			
			
			regLogin.add(confirmButton);
			regLogin.add(cancelButton);
			regLogin.add(regAccount);
			regLogin.add(regPassword);
			regLogin.add(regCheckPassword);
			regLogin.add(regPasswordLabel);
			regLogin.add(regAccountLabel);
			regLogin.add(regCheckPasswordLabel);
			
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
			SwingUtilities.updateComponentTreeUI(userLogin);
			SwingUtilities.updateComponentTreeUI(regLogin);

			regLogin.setLayout(null);
			regLogin.setBounds(483, 255, 430, 260);
			regLogin.setVisible(false);
			
			userLogin.setLayout(null);
			userLogin.setBounds(483, 255, 400, 220);
			userLogin.setVisible(true);
			
		} catch(Exception e) {}
	}

	public void check() {
		while(true) {
			try {
				String serverMessage = reader.readLine().replace("\n", "");
				if(serverMessage.equals("LoginFailed")) {
					password.setText("");
					JOptionPane.showMessageDialog(userLogin, "Please Try Again", "ERROR", JOptionPane.ERROR_MESSAGE);
					break;
				}
				else if(serverMessage.equals("LoginSuccess")) {
					userLogin.setVisible(false);
					break;
				}
				else if(serverMessage.equals("RegisterSuccess")) {
					JOptionPane.showMessageDialog(regLogin, "Your account create successfully", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
					regLogin.setVisible(false);
					break;
				}
				else if(serverMessage.equals("RegisterSpecial")) {
					JOptionPane.showMessageDialog(regLogin, "Your account has specail character(s)", "ERROR", JOptionPane.ERROR_MESSAGE);
					break;
				}
				else if(serverMessage.equals("RegisterRepeat")) {
					JOptionPane.showMessageDialog(regLogin, "Your account has been registed", "ERROR", JOptionPane.ERROR_MESSAGE);
					break;
				}
				
			} catch (IOException e) {}
		}
	}
}
