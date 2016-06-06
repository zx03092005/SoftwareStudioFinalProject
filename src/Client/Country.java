package Client;

import processing.core.PImage;

public class Country {

	MainApplet parent;
	int x, y;
	float initX, initY;
	String name;
	PImage img, background;
	int width, height;
	boolean inRect = false;
	boolean isLocked;
	
	public Country(MainApplet parent, int x, int y, float initX, float initY, String countryName, boolean isLocked) {
		this.parent = parent;
		this.x = x;
		this.initX = initX; 
		this.y = y;
		this.initY = initY;
		this.name = countryName.substring(0, countryName.length()-4);
		this.isLocked = isLocked;
		if(isLocked) img = parent.loadImage("CountryLocked/" + countryName);
		else img = parent.loadImage("Country/" + countryName);
		background = parent.loadImage("WorldMap/" + name + ".png");
		width = img.width;
		height = img.height;
	}
	
	public void display() {
		parent.noTint();
		parent.image(img, x, y, width, height);
	}
	
}
