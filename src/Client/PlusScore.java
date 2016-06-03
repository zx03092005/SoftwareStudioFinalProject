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
			img = parent.loadImage("good.png");
		}
		else if (photo == 3) {
			img = parent.loadImage("gogo.jpg");
		}
		else if (photo ==4){
			img = parent.loadImage("delete.png");
		}
		
		parent.tint(chroma,127);
		parent.image(img, 700, 200, 300, 300);
	}
	
	// initial chroma
	public void initChroma() {
		chroma = initChroma;
	}

}
