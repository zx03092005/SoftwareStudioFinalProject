package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.*;

@SuppressWarnings("serial")
public class MyWindow extends JFrame {

	Socket socket;
	
	public MyWindow() {
		
		connect("127.0.0.1", 8000);
		
		MainApplet applet = new MainApplet(socket);
		applet.init();
		applet.start();
		applet.setFocusable(true);
		
		LoginFrame lf = new LoginFrame(socket);
		
		setLayout(null);
		setResizable(false);
		setTitle("Final Project");		
		setContentPane(applet);
		
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
			SwingUtilities.updateComponentTreeUI(this);
		} catch(Exception e) {}
		
	}
	
	public void connect(String ip, int port) {
		try {
			socket = new Socket(ip, port);
		} catch (Exception e) {}
	}
	
}
