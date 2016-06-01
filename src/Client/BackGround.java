package Client;

import processing.core.PImage;

public class BackGround {
	
	private MainApplet parent;
	private PImage img;
	int chroma, initChroma;
	int photo;
	
	public BackGround(MainApplet parent, int photo, int initChroma) {
		this.parent = parent;
		this.photo = photo;
		this.chroma = initChroma;
		this.initChroma = initChroma;
	}
	
	public void display(){
		if(photo == 1) {
			img = parent.loadImage("photo1.jpg");
			parent.tint(chroma,127);
			parent.image(img, 0, 0);
		}
		else if(photo == 2) {
			img = parent.loadImage("photo2.jpg");
			parent.tint(chroma,127);
			parent.image(img, 0, 0);
		}
		else if(photo == 3) {
			img = parent.loadImage("photo3.jpg");
			parent.tint(chroma,127);
			parent.image(img, 0, 0);
		}
		else if(photo == 4) {
			img = parent.loadImage("photo4.jpg");
			parent.tint(chroma,127);
			parent.image(img, 0, 0);
		}
		else if(photo == 5) {
			img = parent.loadImage("explain.jpg");
			parent.tint(chroma,127);
			parent.image(img, 0, 0);
		}
	}
	// initial chroma
	public void initChroma() {
		chroma = initChroma;
	}

}
