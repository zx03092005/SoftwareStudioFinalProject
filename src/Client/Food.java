package Client;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class Food {
	
	private File[] foodList;
	private boolean[] isSelected;
	boolean isPassed;
	private int choose;
	Random rand = new Random();
	MainApplet parent;
	String strFood, strMaterial;
	
	public Food(MainApplet parent, String country) {
		foodList = new File(country + "/food").listFiles();
		isSelected = new boolean[foodList.length];
		Arrays.fill(isSelected, false);
		isPassed = false;
		this.parent = parent;
	}
	
	public String getFood() {
		while(isSelected[choose = rand.nextInt(foodList.length)]);
		try {
			strFood = foodList[choose].getCanonicalPath();
		} catch (IOException e) {}
		return strFood;
	}
	
	public String getMaterial() {
		//TODO return what is in the burger
		
		
		return strMaterial;
	}
	
	public void foodRect() {
		parent.stroke(0);
		parent.strokeWeight(5);
		if(isPassed) parent.fill(0, 255, 0);
		else parent.fill(255, 0, 0);
		parent.rect(30, 40, 150, 100, 20);
		parent.fill(0);
		parent.text("Food", 60, 105);
	}
	
}