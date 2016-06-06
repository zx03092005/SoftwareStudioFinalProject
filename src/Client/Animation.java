package Client;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;

import processing.core.PImage;

public class Animation {
	/*
	 * Animation
	 */
	private File[] AnimationList;
	MainApplet parent;
	String strMaterial;
	int x, y;
	int width, height;
	ArrayList<PImage> animationImg = new ArrayList<PImage>();
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
		
		for(int i = 0; i < AnimationList.length; i++) {
			try {
				animationImg.add(this.parent.loadImage(AnimationList[i].getCanonicalPath()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
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
	}
	
	public void display(int index) {
		parent.noTint();
		parent.image(animationImg.get(index), x, y, width, height);
		//parent.image(image, x, y, width, height);
	}
	public int getSize(){
		return AnimationList.length;
	}
	

}
