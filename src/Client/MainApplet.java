package Client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import controlP5.ControlP5;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import de.looksgood.ani.Ani;
import processing.core.PApplet;

@SuppressWarnings({ "serial", "unused" })
public class MainApplet extends PApplet {

	PrintWriter writer;
	BufferedReader reader;
	int state = 0;
	String msg;
	private Minim minim;
	private AudioPlayer bgMusic;
	
	public MainApplet(Socket socket) {
		try {
			writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (Exception e) {}
		//System.out.println("MA : " + socket.getInetAddress() + "->" + socket.getPort());
	}
	
	public void setup() {
		size(1000, 650);
		msg = "state = " + String.valueOf(state);
		minim = new Minim(this);
		//load a music to set the background music
		bgMusic = minim.loadFile("BgMusic.mp3");
		bgMusic.play();
	}

	public void draw() {
		textSize(32);
		this.text(msg, 450, 325);
	}

}
