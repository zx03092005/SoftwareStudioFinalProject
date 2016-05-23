package Client;

import processing.core.PImage;

public class Country {

	MainApplet parent;
	int x, initX, y, initY;
	String name;
	PImage img;
	int width, height;
	boolean inRect = false;
	
	public Country(MainApplet parent, int x, int y, String countryName) {
		this.parent = parent;
		this.x = x;
		this.initX = x; 
		this.y = y;
		this.initY = y;
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
