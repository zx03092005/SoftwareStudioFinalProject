package Client;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import de.looksgood.ani.Ani;
import processing.core.PApplet;
import processing.core.PImage;

@SuppressWarnings({ "serial", "unused" })
public class MainApplet extends PApplet {

	int state;
	String msg;
	PImage img;
	private Ani ani;
	BackGround bg;
	Country country;
	File[] countryList = new File("Country").listFiles();
	ArrayList<Country> countrys;
	int m = 0, n = 0, k;
	int[] countrysX = new int[countryList.length];
	int[] countrysY = new int[countryList.length];
	Random rand	= new Random();
	Country overCountryAndNotPress = null, countryLocked = null;
	boolean isOver = false;
	boolean isDisplayed = false;
	int xOffset = 0; 
	int yOffset = 0;
	Food food, usFood;
	Drink drink;
	Snack snack;
	PImage foodImg, snackImg, drinkImg;
	boolean foodSelected = false, snackSelected = false, drinkSelected = false;
	int playState = 1;
	private Minim minim;
	private AudioPlayer bgMusic;
	int usOrNot;
	
	public MainApplet(Socket socket) {
		state = 0;
		countrys = new ArrayList<Country>();
	}
	
	public void setup() {
		size(1000,650);
		Ani.init(this);
		bg = new BackGround(this, 1, 220);
		loadCountry();
		smooth();
	}

	public void draw() {
		if(state == 0) {
			if(bg.chroma > 5 && bg.photo == 1) ani = Ani.to(bg, (float)0.1, "chroma", 10);
			if(bg.chroma < 11 && bg.photo == 1) { 
				bg.initChroma(); 
				bg.photo = 2;
			}
			if(bg.chroma > 5 && bg.photo == 2) ani = Ani.to(bg, (float)0.1, "chroma", 10);
			if(bg.chroma < 11 && bg.photo == 2) { 
				bg.initChroma(); 
				bg.photo = 3; 
			}
			if(bg.chroma > 5 && bg.photo == 3) ani = Ani.to(bg, (float)0.1, "chroma", 10);
			if(bg.chroma < 11 && bg.photo == 3) { 
				bg.initChroma(); 
				this.state = 1; 
			}
			bg.display();
		}
		else if(state == 1) {
			background(255);
			textSize(32);
			fill(new Random().nextInt(255),new Random().nextInt(255),new Random().nextInt(255));
			text("Press!!", 450, 325);
		}
		else if(state == 2) {
			Country c;
			background(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255));
			stroke(4);
			fill(127);
			rect(370, 230, 220, 150, 20);
			for(k=0; k<countrys.size(); k++) {
				c = countrys.get(k);
				fill(127);
				stroke(0);
				strokeWeight(5);
				rect(countrysX[k]-10, countrysY[k]-10, c.width+20, c.height+20, 20);
			}
			if(!isDisplayed) {
				for(k=0; k<countrys.size(); k++) {
					c = countrys.get(k);
					ani = Ani.to(c, (float)1.0, "x", countrysX[k]);
					ani = Ani.to(c, (float)1.0, "y", countrysY[k]);
					//ani = Ani.to(theTarget, theDuration, theFieldName, theEnd, theEasing)
				}
				isDisplayed = true;
			}
			for(Country i : countrys) i.display();
			for(k=0; k<countrys.size(); k++) {
				c = countrys.get(k);
				if(isMouseInShape("RECT", c.x, c.y, c.img.width, c.img.height) && !mousePressed) {
					noStroke();
					fill(126, 32, 174);
					rect(mouseX+10, mouseY-15, c.name.length()*14, 30, 100);
					fill(255, 255, 0);
					textSize(16);
					text(c.name, mouseX+25, mouseY+5);
					c.width = 220;
					c.height = 143;
					overCountryAndNotPress = c;
				}
				else {
					c.width = 200;
					c.height = 130;
				}
			}
			for(Country i : countrys) {
				if(isMouseInShape("RECT", i.x, i.y, i.img.width, i.img.height)) {
					isOver = true;
					break;
				}
				else isOver = false;
			}
		}
		else if(state == 3) {
			background(220);
			fill(0);
			textSize(40);
			for(int i=0; i<2; i++) text("+", 90, 205+i*200);
			food.foodRect();
			snack.snackRect();
			drink.drinkRect();
			ellipse(450, 500, 60, 60);
			if(playState == 1) {
				if(!foodSelected) {
					usOrNot = rand.nextInt(100); // decide to get US food or not
					if (usOrNot%5 == 0 || usOrNot%5 == 1) { 
						foodImg = loadImage(usFood.getFood());
						ani = Ani.to(usFood, (float)1.0, "x", 50);
						ani = Ani.to(usFood, (float)1.0, "y", 50);
					}
					else {
						foodImg = loadImage(food.getFood());
						ani = Ani.to(food, (float)1.0, "x", 50);
						ani = Ani.to(food, (float)1.0, "y", 50);
					}
					foodSelected = true;
				}
				
				image(foodImg, 500, 50, 150, 100);
				if(dist(450, 500, mouseX, mouseY) < 30 && mousePressed) {
					food.isPassed = true;
					playState = 2;
				}
			}
			else if(playState == 2) {
				if(!snackSelected) {
					snackImg = loadImage(snack.getSnack());
					snackSelected = true;
				}
				image(snackImg, 500, 50, 150, 100);
				if(dist(450, 500, mouseX, mouseY) < 30 && mousePressed) {
					snack.isPassed = true;
					playState = 3;
					
				}
			}
			else if(playState == 3){
				if(!drinkSelected) {
					drinkImg = loadImage(drink.getDrink());
					drinkSelected = true;
				}
				image(drinkImg, 500, 50, 150, 100);
				if(dist(450, 500, mouseX, mouseY) < 30 && mousePressed) {
					drink.isPassed = true;
					state = 4;
				}
			}
		}
		//state 4 time's up animation
		else if(state == 4) {
			background(255);
			fill(0);
			textSize(50);
			text("State = 4", 500, 320);
		}
		// end and calculate the score
		else if(state == 5){
			
		}
		// choose the favorite food and locate them in the 1st~3rd
		else if(state == 6) {
			
		}
		//feedback
		// the end Animation and if you want to get more coin to unlocked 
		// the new country you can type something like "Andy is nice person"
		// And then Back to choose the next country you want to play
		else if(state == 7) {
			
		}
	}
	
	public void loadCountry() {
		for(int i=0; i<countryList.length; i++) {
			m = (i>2) ? i-3 : i;
			n = (i>2) ? 1 : 0;
			countrysX[i] = 80+m*300;
			countrysY[i] = 50+n*390;
			country = new Country(this, 400, 260, countryList[i].getName());
			countrys.add(country);
		}
	}
	
	public void mousePressed() {
		if(state == 1) state = 2;
		if (isOver) {
			countryLocked = overCountryAndNotPress;
			xOffset = mouseX-countryLocked.x; 
			yOffset = mouseY-countryLocked.y; 
		}	
	}
	
	public void mouseDragged() {
		if (countryLocked != null) {
			countryLocked.x = mouseX - xOffset;
			countryLocked.y = mouseY - yOffset;
		}
	}
	
	public void mouseReleased() {
		if(countryLocked != null) {
			if(isMouseInShape("RECT", 370, 230, 220, 150)) {
				countryLocked.inRect = true;
				countryLocked.x = 370;
				countryLocked.y = 230;
				food = new Food(this, countryLocked.name);
				usFood = new Food(this, "United_States"); //add US's food
				drink = new Drink(this, countryLocked.name);
				snack = new Snack(this, countryLocked.name);
				state = 3;
			}
		}
	}
	
	public boolean isMouseInShape(String shape, int posX, int posY, int width, int height) {
		if(shape.equals("RECT")) {
			if(mouseX < posX+width && mouseX > posX && mouseY < posY+height && mouseY > posY) 
				return true;
			else 
				return false;
		}
		else if(shape.equals("CIRCLE")) {
			if(dist(posX, posY, mouseX, mouseY) < width/2) return true;
			else return false;
		}
		return false;
	}
}
