package Client;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import processing.core.PImage;

public class Animation {
	/*
	 * Animation
	 */
	private File[] AnimationList;
	MainApplet parent;
	String strMaterial;
	private PImage image;
	int x, y;
	int width, height;
	
	public Animation(MainApplet parent, String fileName,int width, int height,int x,int y) {
		AnimationList = new File(fileName).listFiles(new FilenameFilter() {
	        @Override
	        public boolean accept(File dir, String name) {
	            return !name.equals(".DS_Store");
	        }
	    });
		this.parent = parent;
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
	}
	
	public String getEating(int index) {
		//TODO return what is in the burger
		try {
			strMaterial = AnimationList[index].getCanonicalPath();
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
		return AnimationList.length;
	}

}