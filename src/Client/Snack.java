package Client;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

import processing.core.PImage;

public class Snack {
	
	private File[] snackList;
	private boolean[] isSelected;
	boolean isPassed;
	Random rand = new Random();
	MainApplet parent;
	String strSnack;
	private PImage image;
	int x, y;
	int width, height;
	
	String name;
	
	public Snack(MainApplet parent, String country) {
		snackList = new File(country + "/dessert").listFiles();
		isSelected = new boolean[snackList.length];
		Arrays.fill(isSelected, false);
		isPassed = false;
		this.parent = parent;
		this.width = 100;
		this.height = 75;
		this.x = 500;
		this.y = 100;
	}
	
	public String getSnack(int index) {
		 try {
			strSnack = snackList[index].getCanonicalPath();
		} catch (IOException e) {}
		return strSnack;
	}
	
	public void setImage(PImage img) {
		this.image = img;
	}
	public void display() {
		parent.noTint();
		parent.image(image, x, y, width, height);
	}
	
	public void snackRect() {
		parent.stroke(0);
		parent.strokeWeight(5);
		parent.fill(0, 255, 0);
		parent.rect(30, 40, 150, 100, 20);
		parent.fill(0);
		parent.text("Snack", 50, 105);
	}
}
