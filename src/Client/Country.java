package Client;

import processing.core.PImage;

public class Country {

	MainApplet parent;
	int x, y;
	float initX, initY;
	String name;
	PImage img;
	int width, height;
	boolean inRect = false;
	
	public Country(MainApplet parent, int x, int y, float initX, float initY, String countryName) {
		this.parent = parent;
		this.x = x;
		this.initX = initX; 
		this.y = y;
		this.initY = initY;
		this.name = countryName.substring(0, countryName.length()-4);
		img = parent.loadImage("Country/" + countryName);
		width = img.width;
		height = img.height;
	}
	
	public void display() {
		parent.noTint();
		parent.image(img, x, y, width, height);
	}
	
}
