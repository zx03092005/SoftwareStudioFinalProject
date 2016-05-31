package Client;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import processing.core.PImage;

public class EatingAnimation {
	/*
	 * EatingAnimation
	 */
	private File[] eatingAnimationList;
	MainApplet parent;
	String strMaterial;
	private PImage image;
	int x, y;
	int width, height;
	
	public EatingAnimation(MainApplet parent) {
		eatingAnimationList = new File("eating_animation").listFiles(new FilenameFilter() {
	        @Override
	        public boolean accept(File dir, String name) {
	            return !name.equals(".DS_Store");
	        }
	    });
		this.parent = parent;
		this.width = 1000;
		this.height = 600;
		this.x = 0;
		this.y = 0;
	}
	
	public String getEating(int index) {
		//TODO return what is in the burger
		try {
			strMaterial = eatingAnimationList[index].getCanonicalPath();
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
	public int getSize(){
		return eatingAnimationList.length;
	}

}
