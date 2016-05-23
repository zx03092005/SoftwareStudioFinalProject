package Client;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class Snack {
	
	private File[] snackList;
	private boolean[] isSelected;
	boolean isPassed;
	private int choose;
	Random rand = new Random();
	MainApplet parent;
	String strSnack;
	
	public Snack(MainApplet parent, String country) {
		snackList = new File(country + "/snack").listFiles();
		isSelected = new boolean[snackList.length];
		Arrays.fill(isSelected, false);
		isPassed = false;
		this.parent = parent;
	}
	
	public String getSnack() {
		while(isSelected[choose = rand.nextInt(snackList.length)]);
		 try {
			strSnack = snackList[choose].getCanonicalPath();
		} catch (IOException e) {}
		return strSnack;
	}
	
	public void snackRect() {
		parent.stroke(0);
		parent.strokeWeight(5);
		if(isPassed) parent.fill(0, 255, 0);
		else parent.fill(255, 0, 0);
		parent.rect(30, 240, 150, 100, 20);
		parent.fill(0);
		parent.text("Snack", 50, 305);
	}
}
