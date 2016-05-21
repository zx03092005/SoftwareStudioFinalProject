package Client;

import processing.core.PApplet;

@SuppressWarnings("serial")
public class MainApplet extends PApplet {

	int state = 0;
	String msg;
	
	public void setup() {
		size(1000, 650);
		msg = "state = " + String.valueOf(state);
	}

	public void draw() {
		textSize(32);
		this.text(msg, 450, 325);
	}

}
