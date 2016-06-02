package Client;

import processing.core.PImage;

public class PlusScore {
	
	private MainApplet parent;
	private PImage img;
	int chroma, initChroma;
	int photo;
	
	public PlusScore(MainApplet parent, int photo, int initChroma) {
		this.parent = parent;
		this.photo = photo;
		this.chroma = initChroma;
		this.initChroma = initChroma;//
	}
	
	public void display(){
		if (photo == 1) {
			img = parent.loadImage("+10.png");
		}
		else if (photo == 2) {
			img = parent.loadImage("+3.png");
		}
		else if (photo == 3) {
			img = parent.loadImage("-3.png");
		}
		parent.tint(chroma,127);
		parent.image(img, 600, 300, 500, 500);
	}
	
	// initial chroma
	public void initChroma() {
		chroma = initChroma;
	}

}
