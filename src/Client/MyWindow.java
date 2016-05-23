package Client;

import java.net.Socket;

import javax.swing.*;

@SuppressWarnings("serial")
public class MyWindow extends JFrame {

	Socket socket;
	
	@SuppressWarnings("unused")
	public MyWindow() {
		
		connect("127.0.0.1", 8000);

		LoginFrame lf = new LoginFrame(socket);
		
		MainApplet applet = new MainApplet(socket);
		applet.init();
		applet.start();
		applet.setFocusable(true);

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
