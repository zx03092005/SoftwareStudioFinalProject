package Client;

import javax.swing.*;

@SuppressWarnings("serial")
public class MyWindow extends JFrame {

	MainApplet applet = new MainApplet();
	
	public MyWindow() {
		
		MainApplet applet = new MainApplet();
		applet.init();
		applet.start();
		applet.setFocusable(true);
		
		//Thread lt = new Thread(new LoginFrame());
		//lt.start();
		@SuppressWarnings("unused")
		LoginFrame lf = new LoginFrame();
		
		setLayout(null);
		setResizable(false);
		setTitle("Final Project");		
		setContentPane(applet);
		
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
			SwingUtilities.updateComponentTreeUI(this);
		} catch(Exception e) {}
		
	}

}
