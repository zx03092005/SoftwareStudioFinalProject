package Client;

import java.io.File;
import java.io.IOException;

import processing.core.PImage;

public class Bread {
	
	private File[] breadList;
	MainApplet parent;
	String strMaterial;
	private PImage image;
	int x, y;
	int width, height;
	
	public Bread(MainApplet parent) {
		breadList = new File("material" + "/bread").listFiles();
		this.parent = parent;
		this.width = 100;
		this.height = 75;
		this.x = 500;
		this.y = 100;
	}
	
	public String getBread(int index) {
		//TODO return what is in the burger
		try {
			strMaterial = breadList[index].getCanonicalPath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return strMaterial;
	}

	public void setImage(PImage img) {
		this.image = img;
	}
	
	public void display() {
		parent.noTint();
		parent.image(image, x, y, width, height);
	}
}
