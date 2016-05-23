package Client;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class Drink {

	private File[] drinkList;
	private boolean[] isSelected;
	boolean isPassed;
	private int choose;
	Random rand = new Random();
	MainApplet parent;
	String strDrink;
	
	public Drink(MainApplet parent, String country) {
		drinkList = new File(country + "/drink").listFiles();
		isSelected = new boolean[drinkList.length];
		Arrays.fill(isSelected, false);
		isPassed = false;
		this.parent = parent;
	}
	
	public String getDrink() {
		while(isSelected[choose = rand.nextInt(drinkList.length)]);
		try {
			strDrink = drinkList[choose].getCanonicalPath();
		} catch (IOException e) {} 
		return strDrink;
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
