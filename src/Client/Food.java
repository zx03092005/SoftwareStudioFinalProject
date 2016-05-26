package Client;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class Food {
	
	private File[] foodList;
	private File[] breadList;
	private File[] meatList;
	private File[] othersList;
	private boolean[] isSelected;
	boolean isPassed;
	private int choose;
	Random rand = new Random();
	MainApplet parent;
	String strFood, strMaterial;
	
	public Food(MainApplet parent, String country) {
		foodList = new File(country + "/food").listFiles();
		breadList = new File("material" + "/bread").listFiles();
		meatList = new File("material" + "/meat").listFiles();
		othersList = new File("material" + "others").listFiles();
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
	
	public String getMaterial(int kind, int index) {
		//TODO return what is in the burger
		if (kind == 1){
			try {
				strMaterial = breadList[index].getCanonicalPath();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (kind == 2){
			try {
				strMaterial = meatList[index].getCanonicalPath();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			try {
				strMaterial = othersList[index].getCanonicalPath();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
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
