package Client;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

import processing.core.PImage;

public class Food {
	
	private File[] foodList;
	private boolean[] isSelected;
	boolean isPassed;
	private int choose;
	Random rand = new Random();
	MainApplet parent;
	String strFood, strMaterial;
	PImage image;
	int x, y;
	int width, height;
	boolean isLocked;
	
	String name;
	int bread;
	int meat;
	int bacon, cheese, cucumber, egg, ham, lettuce, onion, tamato;
	PImage[] allFood;
	
	public Food(MainApplet parent, String country, boolean isLocked) {
		foodList = new File(country + "/food").listFiles();
		isSelected = new boolean[foodList.length];
		Arrays.fill(isSelected, false);
		isPassed = false;
		this.parent = parent;
		this.width = 200;
		this.height = 150;
		this.x = 500;
		this.y = -50;
		this.isLocked = isLocked;
		//allFood = new PImage[foodList.length];
		//for(int i=0; i<foodList.length; i++) allFood[i] = parent.loadImage(country + "/food" + foodList[i]);
	}
	
	public String getFood() {
		while(isSelected[choose = rand.nextInt(foodList.length)]);
		try {
			strFood = foodList[choose].getCanonicalPath();
		} catch (IOException e) {}
		return strFood;
	}

	public String getFood(int index) {
		//TODO return what is in the burger
		try {
			strMaterial = foodList[index].getCanonicalPath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return strMaterial;
	}
	
	public int getchoose() {
		//TODO return what is in the burger
		return choose;
	}
	
	public void foodRect() {
		parent.stroke(0);
		parent.strokeWeight(5);
		parent.fill(0, 255, 0);
		parent.rect(30, 40, 150, 100, 20);
		parent.fill(0);
		parent.text("Food", 60, 105);
	}
	public void setImage(PImage img) {
		this.image = img;
	}
	public void display() {
		parent.noTint();
		parent.image(image, x, y, width, height);
	}

	public int getSize(){
		return foodList.length;
	}
}
