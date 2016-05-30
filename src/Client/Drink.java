package Client;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

import processing.core.PImage;

public class Drink {

	private File[] drinkList;
	private boolean[] isSelected;
	boolean isPassed;
	Random rand = new Random();
	MainApplet parent;
	String strDrink;
	private PImage image;
	int x, y;
	int width, height;
	
	String name;
	
	public Drink(MainApplet parent, String country) {
		drinkList = new File(country + "/drink").listFiles();
		isSelected = new boolean[drinkList.length];
		Arrays.fill(isSelected, false);
		isPassed = false;
		this.parent = parent;
		this.width = 100;
		this.height = 75;
		this.x = 500;
		this.y = 100;
	}
	
	public String getDrink(int index) {
		try {
			strDrink = drinkList[index].getCanonicalPath();
		} catch (IOException e) {} 
		return strDrink;
	}
	
	public void setImage(PImage img) {
		this.image = img;
	}
	public void display() {
		parent.noTint();
		parent.image(image, x, y, width, height);
	}
	
	public void drinkRect() {
		parent.stroke(0);
		parent.strokeWeight(5);
		if(isPassed) parent.fill(0, 255, 0);
		else parent.fill(255, 0, 0);
		parent.rect(30, 440, 150, 100, 20);
		parent.fill(0);
		parent.text("Drink", 50, 505);
	}
}
