package Client;

import processing.core.PImage;

public class Country {

	MainApplet parent;
	int x, y;
	float initX, initY;
	String name;
	PImage img, background;
	int width = 200, height = 130;
	boolean inRect = false;
	boolean isLocked;
	String countryName;
	
	public Country(MainApplet parent, int x, int y, float initX, float initY, String countryName, boolean isLocked) {
		this.parent = parent;
		this.x = x;
		this.initX = initX; 
		this.y = y;
		this.initY = initY;
		this.name = countryName.substring(0, countryName.length()-4);
		this.isLocked = isLocked;
		this.countryName = countryName;
		background = parent.loadImage("WorldMap/" + name + ".png");
	}
	
	public void display() {
		parent.noTint();
		if(isLocked) img = parent.loadImage("CountryLocked/" + countryName);
		else img = parent.loadImage("Country/" + countryName);
		parent.image(img, x, y, width, height);
	}
	
}
