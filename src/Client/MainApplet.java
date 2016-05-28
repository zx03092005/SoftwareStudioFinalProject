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
import processing.data.JSONArray;
import processing.data.JSONObject;

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
	int choosefoodState = 0;
	int accept;
	int deny;
	boolean foodisDisplayed = false;
	boolean isAmerica = false;
	int orderState = 0;
	int score = 0;
	JSONObject data;
	JSONArray data_food, data_dessert, data_drink;
	
	Bread bread;
	ArrayList<Bread> breads;
	int[] breadsX = new int[6];
	int[] breadsY = new int[6];
	boolean breadisDisplayed = false;
	PImage breadImg;
	int selectedbread = -1;
	int focusbread = -1;
	int prevbread = -1;
	int breadOK = 0;
	
	Meat meat;
	ArrayList<Meat> meats;
	int[] meatsX = new int[6];
	int[] meatsY = new int[6];
	boolean meatisDisplayed = false;
	PImage meatImg;
	int selectedmeat = -1;
	int focusmeat = -1;
	int prevmeat = -1;
	int meatOK = 0;
	
	public MainApplet(Socket socket) {
		state = 0;
		countrys = new ArrayList<Country>();
		breads = new ArrayList<Bread>();
		meats = new ArrayList<Meat>();
	}
	
	public void setup() {
		size(1000,650);
		Ani.init(this);
		bg = new BackGround(this, 1, 220);
		loadCountry();
		loadBread();
		loadMeat();
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
			background(204, 230, 255);
			fill(0);
			textSize(40);
			for(int i=0; i<2; i++) text("+", 90, 205+i*200);
			food.foodRect();
			snack.snackRect();
			drink.drinkRect();
			ellipse(450, 500, 60, 60);
			fill(0);
			text(Integer.toString(score),900,70);
			if(playState == 1) {
				if(!foodSelected) {
					usOrNot = rand.nextInt(100); // decide to get US food or not
					if (usOrNot%5 == 0 || usOrNot%5 == 1) { 
						isAmerica = true;
						usFood.x = 500;
						usFood.y = -50;
						foodImg = loadImage(usFood.getFood());
						usFood.setImage(foodImg);
						//ani = Ani.to(usFood, (float)1.0, "x", 500);
						ani = Ani.to(usFood, (float)1.0, "y", 50,Ani.BOUNCE_OUT);
					}
					else {
						isAmerica = false;
						food.x = 500;
						food.y = -50;
						foodImg = loadImage(food.getFood());
						food.setImage(foodImg);
						//ani = Ani.to(food, (float)1.0, "x", 500);
						ani = Ani.to(food, (float)1.0, "y", 50,Ani.BOUNCE_OUT);
						loadfooddata();
					}
					foodSelected = true;
				}
				
				if (isAmerica){
					usFood.display();
				}
				else {
					food.display(); 
				}
				if (breadOK == -1){
					Bread b;
					b = breads.get(selectedbread);
					b.x = 220;
					b.y = 50;
					b.display();
				}
				if (choosefoodState == 0){ 
					if (orderState == 0){
						noStroke();
						
						if (isMouseInShape("RECT",270,300,200,100) == true) {
							fill(51, 51, 255);
							rect(270, 300, 200, 100, 20);
							fill(255, 255, 255);
							if (mousePressed) {
								accept = 1;
							}
						}
						else {
							fill(0, 255, 0);
							rect(270, 300, 200, 100, 20);
							fill(0);
						}
						text("Accepted", 285, 360);

						if (isMouseInShape("RECT",600,300,200,100) == true) {
							fill(51, 51, 255);
							rect(600, 300, 200, 100, 20);
							fill(255, 255, 255);
							if (mousePressed) {
								deny = 1;
							}
						}
						else {
							fill(255, 0, 102);
							rect(600, 300, 200, 100, 20);
							fill(0);
						}
						text("Denied", 630, 360);
					}
					else if (orderState == 1){
						if (accept == 1){
							if (isAmerica){
								foodSelected = false;
								accept = -1;
								score -= 10;
								orderState = 0;
							}
							else {
								score += 10;
								choosefoodState = 1;
							}
						}
						if (deny == 1){
							if (isAmerica){
								score += 10;
								foodSelected = false;
								deny = 0;
								orderState = 0;
							}
							else {
								score -= 10;
								foodSelected = false;
								deny = 0;
								orderState = 0;
							}
						}
					}
				}
				else if (choosefoodState == 1){
					Bread b, br;
					if(!breadisDisplayed) {
						for(k=0; k<breads.size(); k++) {
							b = breads.get(k);
							ani = Ani.to(b, (float)1.0, "x", breadsX[k]);
							ani = Ani.to(b, (float)1.0, "y", breadsY[k]);
							//ani = Ani.to(theTarget, theDuration, theFieldName, theEnd, theEasing)
						}
						breadisDisplayed = true;
					}
					for(Bread i : breads) i.display();
					for(k=0; k<breads.size(); k++) {
						b = breads.get(k);
						if(isMouseInShape("RECT", b.x, b.y, b.width, b.height)) {
							if (mousePressed){
								focusbread = k;
								prevbread = k;
							}
							b.width = 110;
							b.height = 80;
						}
						else {
							int j, outside=1;
							
							if (mousePressed) {
								for (j=0; j<breads.size(); j++) {
									br = breads.get(j);
									if (isMouseInShape("RECT", br.x, br.y, br.width, br.height)) {
										outside = 0;
										break;
									}
								}
								if (outside == 1) {
									focusbread = -1;
								}
							}
							b.width = 100;
							b.height = 75;
						}
						if (selectedbread == k){
							fill(0);
							text("choosed",b.x,b.y);
						}
					}
					noStroke();
					if (isMouseInShape("RECT",500,450,150,80) == true) {
						fill(51, 51, 255);
						rect(500, 450, 150, 80, 20);
						fill(255, 255, 255);
						if (mousePressed) {
							breadOK = 1;
							choosefoodState = 2;
						}
					}
					else {
						fill(255, 153, 51);
						rect(500, 450, 150, 80, 20);
						fill(255, 255, 255);
					}
					textSize(50);
					text("OK",540,510);
				}
				else if (choosefoodState == 2){
					Meat m, mr;
					if(!meatisDisplayed) {
						for(k=0; k<meats.size(); k++) {
							m = meats.get(k);
							ani = Ani.to(m, (float)1.0, "x", meatsX[k],ani.BOUNCE_OUT);
							ani = Ani.to(m, (float)1.0, "y", meatsY[k],ani.BOUNCE_OUT);
							//ani = Ani.to(theTarget, theDuration, theFieldName, theEnd, theEasing)
						}
						meatisDisplayed = true;
					}
					for(Meat i : meats) i.display();
					for(k=0; k<meats.size(); k++) {
						m = meats.get(k);
						if(isMouseInShape("RECT", m.x, m.y, m.width, m.height)) {
							if (mousePressed){
								focusmeat = k;
								prevmeat = k;
							}
							m.width = 110;
							m.height = 80;
						}
						else {
							int j, outside=1;
							
							if (mousePressed) {
								for (j=0; j<meats.size(); j++) {
									mr = meats.get(j);
									if (isMouseInShape("RECT", mr.x, mr.y, mr.width, mr.height)) {
										outside = 0;
										break;
									}
								}
								if (outside == 1) {
									focusmeat = -1;
								}
							}
							m.width = 100;
							m.height = 75;
						}
						if (selectedmeat == k){
							fill(0);
							text("choosed",m.x,m.y);
						}
					}
					noStroke();
					if (isMouseInShape("RECT",500,450,150,80) == true) {
						fill(51, 51, 255);
						rect(500, 450, 150, 80, 20);
						fill(255, 255, 255);
						if (mousePressed) {
							meatOK = 1;
							choosefoodState = 2;
						}
					}
					else {
						fill(255, 153, 51);
						rect(500, 450, 150, 80, 20);
						fill(255, 255, 255);
					}
					textSize(50);
					text("OK",540,510);
				}
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

			background(255);
			fill(0);
			int totalScore = 0; //add by qqhsuanwu
			String msgTotalScore = String.valueOf(totalScore);
			textSize(50);
			text("Total Score = "+msgTotalScore, 400, 220);

			int totalMoney = 0; //add by qqhsuanwu
			String msgTotalMoney = String.valueOf(totalMoney);
			textSize(50);
			text("Total Money = "+msgTotalMoney, 400, 420);
			
		}
		// choose the favorite food and locate them in the 1st~3rd
		else if(state == 6) {
			///123
			
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
	
	public void loadBread() {
		for(int i=0; i<6; i++) {
			breadsX[i] = 250+i*110;
			breadsY[i] = (int)(350-0.3*(i-2.5)*(i-2.5)*80);
			bread = new Bread(this);
			breadImg = loadImage(bread.getBread(i));
			bread.setImage(breadImg);
			breads.add(bread);
		}
	}
	
	public void loadMeat() {
		for(int i=0; i<6; i++) {
			meatsX[i] = 250+i*110;
			meatsY[i] = (int)(350-0.3*(i-2.5)*(i-2.5)*80);
			meat = new Meat(this);
			meat.y = meatsY[i];
			meatImg = loadImage(meat.getMeat(i));
			meat.setImage(meatImg);
			meats.add(meat);
		}
	}
	
	public void mousePressed() {
		if(state == 1) state = 2;

		if(state == 4) state = 5;
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
			if(isMouseInShape("RECT", 370, 230, 220, 150)&&state == 2) {
				countryLocked.inRect = true;
				countryLocked.x = 370;
				countryLocked.y = 230;
				if (accept == 0){
					food = new Food(this, countryLocked.name);
					usFood = new Food(this, "United_States"); //add US's food
					drink = new Drink(this, countryLocked.name);
					snack = new Snack(this, countryLocked.name);
					loaddata();
					state = 3;
				}
			}
			if (choosefoodState == 0){
				if (accept == 1){
					orderState = 1;
				}
				if (deny == 1){
					orderState = 1;
				}
			}
			if (choosefoodState == 1){
				//System.out.println("Release: "+selectedbread+" "+focusbread+" "+prevbread);
				if (selectedbread == focusbread){
					selectedbread = -1;
				}
				else {
					if (focusbread == -1) {
						selectedbread = prevbread;
					}
					else {
						selectedbread = focusbread;
					}
				}
			}
			if (choosefoodState == 2){
				if (breadOK == 1){
					breadOK = -1;
					if (selectedbread == food.bread){
						score += 10;
					}
				}
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
	
	public void loaddata(){
		data = loadJSONObject(countryLocked.name+"/"+countryLocked.name+".json");
		data_food = data.getJSONArray("food");
		data_dessert = data.getJSONArray("dessert");
		data_drink = data.getJSONArray("drink");
	}
	public void loadfooddata(){
		JSONObject temp = data_food.getJSONObject(food.getchoose());
		String name = temp.getString("name");
		food.name = name;
		String bread = temp.getString("bread");
		if (bread.equals("bagel")){
			food.bread = 0;
		}
		else if (bread.equals("hamburger")){
			food.bread = 1;
		}
		else if (bread.equals("croissants")){
			food.bread = 2;
		}
		else if (bread.equals("english_muffin")){
			food.bread = 3;
		}
		else if (bread.equals("toast")){
			food.bread = 4;
		}
		else if (bread.equals("wrap")){
			food.bread = 5;
		}
	}
}
