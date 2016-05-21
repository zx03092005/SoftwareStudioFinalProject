package Client;

import java.util.ArrayList;

import controlP5.ControlP5;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import de.looksgood.ani.Ani;
import main.java.Character;
import processing.core.PApplet;

@SuppressWarnings({ "serial", "unused" })
public class MainApplet extends PApplet {

	int state = 0;
	String msg;
	private ControlP5 cp5;
	private Ani ani;
	private Minim minim;
	private AudioPlayer bgMusic;
	
	public void setup() {
		size(1000, 650);
		msg = "state = " + String.valueOf(state);
		Ani.init(this);
		minim = new Minim(this);
		//load a music to set the background music
		bgMusic = minim.loadFile("BgMusic.mp3");
		bgMusic.play();
		cp5 = new ControlP5(this);
	}

	public void draw() {
		textSize(32);
		this.text(msg, 450, 325);
	}

}
