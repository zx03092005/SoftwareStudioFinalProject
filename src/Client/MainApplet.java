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
	Drink drink, usdrink;
	Snack snack,ussnack;
	PImage foodImg, snackImg, drinkImg;
	boolean foodSelected = false;
	int playState = 1;
	private Minim minim;
	private AudioPlayer bgMusic;
	
	int usOrNot;
	int choosefoodState = 0;
	int accept;
	int deny;
	boolean isAmerica = false;
	int orderState = 0;
	int score = 0;
	JSONObject data;
	JSONArray data_food, data_dessert, data_drink;
	JSONObject usdata;
	JSONArray usdata_food;
	
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
	
	OthersMaterial other;
	ArrayList<OthersMaterial> others;
	int[] othersX = new int[8];
	int[] othersY = new int[8];
	boolean otherisDisplayed = false;
	PImage otherImg;
	int selectedbacon = -1, selectedcheese = -1, selectedcucumber = -1, selectedegg = -1, selectedham = -1, selectedlettuce = -1, selectedonion = -1, selectedtomato = -1;
	int focusbacon = -1, focuscheese = -1, focuscucumber = -1, focusegg = -1, focusham = -1, focuslettuce = -1, focusonion = -1, focustomato = -1;
	int prevbacon = -1, prevcheese = -1, prevcucumber = -1, prevegg = -1, prevham = -1, prevlettuce = -1, prevonion = -1, prevtomato = -1;
	int otherOK = -1;
	
	//Snack snack;
	ArrayList<Snack> snacks;
	ArrayList<Snack> ussnacks;
	int[] snacksX = new int[5];
	int[] snacksY = new int[5];
	int[] ussnacksX = new int[5];
	int[] ussnacksY = new int[5];
	boolean snackisDisplayed = false;
	//PImage snackImg;
	PImage ussnackImg;
	int selectedsnack = -1;
	int focussnack = -1;
	int prevsnack = -1;
	int snackOK = 0;
	
	ArrayList<Drink> drinks;
	ArrayList<Drink> usdrinks;
	int[] drinksX = new int[5];
	int[] drinksY = new int[5];
	int[] usdrinksX = new int[5];
	int[] usdrinksY = new int[5];
	boolean drinkisDisplayed = false;
	PImage usdrinkImg;
	int selecteddrink = -1;
	int focusdrink = -1;
	int prevdrink = -1;
	int drinkOK = 0;
	PImage chooseimg;
	
	public MainApplet(Socket socket) {
		state = 0;
		countrys = new ArrayList<Country>();
		breads = new ArrayList<Bread>();
		meats = new ArrayList<Meat>();
		others = new ArrayList<OthersMaterial>();
		snacks = new ArrayList<Snack>();
		ussnacks = new ArrayList<Snack>();
		drinks = new ArrayList<Drink>();
		usdrinks = new ArrayList<Drink>();
	}
	
	public void setup() {
		size(1000,650);
		Ani.init(this);
		bg = new BackGround(this, 1, 220);
		loadCountry();
		loadBread();
		loadMeat();
		loadOther();
		chooseimg = loadImage("okay.png");
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
			snacks.get(0).snackRect();
			drinks.get(0).drinkRect();
			//ellipse(450, 500, 60, 60);
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
					}
					foodSelected = true;
				}
				loadfooddata();
				if (isAmerica){
					usFood.display();
					fill(0);
					textSize(25);
					text(usFood.name, 500, 250);
					textSize(40);
				}
				else {
					food.display(); 
					fill(0);
					textSize(25);
					text(food.name, 500, 250);
					textSize(40);
				}
				if (breadOK == -1){
					Bread b;
					b = breads.get(selectedbread);
					b.x = 220;
					b.y = 50;
					b.display();
				}
				if (meatOK == -1){
					Meat m;
					m = meats.get(selectedmeat);
					m.x = 350;
					m.y = 50;
					m.display();
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
							image(chooseimg,b.x,b.y-30,50,50);
							/*fill(0);
							text("choosed",b.x,b.y);*/
						}
					}
					noStroke();
					if (isMouseInShape("RECT",500,450,150,80) == true) {
						fill(51, 51, 255);
						rect(500, 450, 150, 80, 20);
						fill(255, 255, 255);
						if (mousePressed&&selectedbread>=0) {
							breadOK = 1;
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
							ani = Ani.to(m, (float)1.0, "x", meatsX[k],Ani.BOUNCE_OUT);
							ani = Ani.to(m, (float)1.0, "y", meatsY[k],Ani.BOUNCE_OUT);
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
							image(chooseimg,m.x,m.y-30,50,50);
							/*fill(0);
							text("choosed",m.x,m.y);*/
						}
					}
					noStroke();
					if (isMouseInShape("RECT",500,450,150,80) == true) {
						fill(51, 51, 255);
						rect(500, 450, 150, 80, 20);
						fill(255, 255, 255);
						if (mousePressed&&selectedmeat>=0) {
							meatOK = 1;
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
				else if (choosefoodState == 3){
					OthersMaterial o, ot;
					if(!otherisDisplayed) {
						for(k=0; k<others.size(); k++) {
							o = others.get(k);
							ani = Ani.to(o, (float)1.0, "x", othersX[k],Ani.BOUNCE_OUT);
							ani = Ani.to(o, (float)1.0, "y", othersY[k],Ani.BOUNCE_OUT);
							//ani = Ani.to(theTarget, theDuration, theFieldName, theEnd, theEasing)
						}
						otherisDisplayed = true;
					}
					for(OthersMaterial i : others) i.display();
					for (k=0; k<others.size(); k++){
						o = others.get(k);
						if(isMouseInShape("RECT", o.x, o.y, o.width, o.height)) {
							if (mousePressed){
								if (k == 0){
									focusbacon = 1;
									focuscheese = -1;
									focuscucumber = -1;
									focusegg = -1;
									focusham = -1;
									focuslettuce = -1;
									focusonion = -1;
									focustomato = -1;
								}
								if (k == 1){
									focusbacon = -1;
									focuscheese = 1;
									focuscucumber = -1;
									focusegg = -1;
									focusham = -1;
									focuslettuce = -1;
									focusonion = -1;
									focustomato = -1;
								}
								if (k == 2){
									focusbacon = -1;
									focuscheese = -1;
									focuscucumber = 1;
									focusegg = -1;
									focusham = -1;
									focuslettuce = -1;
									focusonion = -1;
									focustomato = -1;
								}
								if (k == 3){
									focusbacon = -1;
									focuscheese = -1;
									focuscucumber = -1;
									focusegg = 1;
									focusham = -1;
									focuslettuce = -1;
									focusonion = -1;
									focustomato = -1;
								}
								if (k == 4){
									focusbacon = -1;
									focuscheese = -1;
									focuscucumber = -1;
									focusegg = -1;
									focusham = 1;
									focuslettuce = -1;
									focusonion = -1;
									focustomato = -1;
								}
								if (k == 5){
									focusbacon = -1;
									focuscheese = -1;
									focuscucumber = -1;
									focusegg = -1;
									focusham = -1;
									focuslettuce = 1;
									focusonion = -1;
									focustomato = -1;
								}
								if (k == 6){
									focusbacon = -1;
									focuscheese = -1;
									focuscucumber = -1;
									focusegg = -1;
									focusham = -1;
									focuslettuce = -1;
									focusonion = 1;
									focustomato = -1;
								}
								if (k == 7){
									focusbacon = -1;
									focuscheese = -1;
									focuscucumber = -1;
									focusegg = -1;
									focusham = -1;
									focuslettuce = -1;
									focusonion = -1;
									focustomato = 1;
								}
							}
							o.width = 110;
							o.height = 80;
						}
						else {
							int j, outside=1;
							
							if (mousePressed) {
								for (j=0; j<others.size(); j++) {
									ot = others.get(j);
									if (isMouseInShape("RECT", ot.x, ot.y, ot.width, ot.height)) {
										outside = 0;
										break;
									}
								}
								if (outside == 1) {
									focusbacon = -1;
									focuscheese = -1;
									focuscucumber = -1;
									focusegg = -1;
									focusham = -1;
									focuslettuce = -1;
									focusonion = -1;
									focustomato = -1;
								}
							}
							o.width = 100;
							o.height = 75;
						}
					}
					if (selectedbacon == 1){
						o = others.get(0);
						image(chooseimg,o.x,o.y-30,50,50);
						/*fill(0);
						text("choosed",o.x,o.y);*/
					}
					if (selectedcheese == 1){
						o = others.get(1);
						image(chooseimg,o.x,o.y-30,50,50);
						/*fill(0);
						text("choosed",o.x,o.y);*/
					}
					if (selectedcucumber == 1){
						o = others.get(2);
						image(chooseimg,o.x,o.y-30,50,50);
						/*fill(0);
						text("choosed",o.x,o.y);*/
					}
					if (selectedegg == 1){
						o = others.get(3);
						image(chooseimg,o.x,o.y-30,50,50);
						/*fill(0);
						text("choosed",o.x,o.y);*/
					}
					if (selectedham == 1){
						o = others.get(4);
						image(chooseimg,o.x,o.y-30,50,50);
						/*fill(0);
						text("choosed",o.x,o.y);*/
					}
					if (selectedlettuce == 1){
						o = others.get(5);
						image(chooseimg,o.x,o.y-30,50,50);
						/*fill(0);
						text("choosed",o.x,o.y);*/
					}
					if (selectedonion == 1){
						o = others.get(6);
						image(chooseimg,o.x,o.y-30,50,50);
						/*fill(0);
						text("choosed",o.x,o.y);*/
					}
					if (selectedtomato == 1){
						o = others.get(7);
						image(chooseimg,o.x,o.y-30,50,50);
						/*fill(0);
						text("choosed",o.x,o.y);*/
					}
					noStroke();
					if (isMouseInShape("RECT",500,500,150,80) == true) {
						fill(51, 51, 255);
						rect(500, 500, 150, 80, 20);
						fill(255, 255, 255);
						if (mousePressed) {
							otherOK = 1;
						}
					}
					else {
						fill(255, 153, 51);
						rect(500, 500, 150, 80, 20);
						fill(255, 255, 255);
					}
					textSize(50);
					text("OK",540,560);
				}
				/*if(dist(450, 500, mouseX, mouseY) < 30 && mousePressed) {
					food.isPassed = true;
					playState = 2;
				}*/
			}
			else if(playState == 2) {
				Snack s, sn,uss,ussn;
				if(!snackisDisplayed) {
					for(k=0; k<snacks.size(); k++) {
						s = snacks.get(k);
						uss = ussnacks.get(k);
						ani = Ani.to(s, (float)1.0, "x", snacksX[k],Ani.BOUNCE_OUT);
						ani = Ani.to(s, (float)1.0, "y", snacksY[k],Ani.BOUNCE_OUT);
						ani = Ani.to(uss, (float)1.0, "x", ussnacksX[k],Ani.BOUNCE_OUT);
						ani = Ani.to(uss, (float)1.0, "y", ussnacksY[k],Ani.BOUNCE_OUT);
						//ani = Ani.to(theTarget, theDuration, theFieldName, theEnd, theEasing)
					}
					snackisDisplayed = true;
				}
				for(Snack i : snacks) i.display();
				for(Snack i : ussnacks) i.display();
				for(k=0; k<snacks.size(); k++) {
					s = snacks.get(k);
					uss = ussnacks.get(k);
					if(isMouseInShape("RECT", s.x, s.y, s.width, s.height)) {
						if (mousePressed){
							focussnack = k;
							prevsnack = k;
						}
						s.width = 110;
						s.height = 80;
					}
					else if (isMouseInShape("RECT", uss.x, uss.y, uss.width, uss.height)){
						if (mousePressed){
							focussnack = k+5;
							prevsnack = k+5;
						}
						uss.width = 110;
						uss.height = 80;
					}
					else {
						int j, outside=1;
							
						if (mousePressed) {
							for (j=0; j<snacks.size(); j++) {
								sn = snacks.get(j);
								ussn = snacks.get(j);
								if (isMouseInShape("RECT", sn.x, sn.y, sn.width, sn.height)) {
									outside = 0;
									break;
								}
								if (isMouseInShape("RECT", ussn.x, ussn.y, ussn.width, ussn.height)) {
									outside = 0;
									break;
								}
							}
							if (outside == 1) {
								focussnack = -1;
							}
						}
						s.width = 100;
						s.height = 75;
					}
					if (selectedsnack == k){
						image(chooseimg,s.x,s.y-30,50,50);
						/*fill(0);
						text("choosed",s.x,s.y);*/
					}
					if ((selectedsnack-5) == k){
						image(chooseimg,uss.x,uss.y-30,50,50);
						/*fill(0);
						text("choosed",uss.x,uss.y);*/
					}
				}
				noStroke();
				if (isMouseInShape("RECT",500,450,150,80) == true) {
					fill(51, 51, 255);
					rect(500, 450, 150, 80, 20);
					fill(255, 255, 255);
					if (mousePressed&&selectedsnack>=0) {
						snackOK = 1;
					}
				}
				else {
					fill(255, 153, 51);
					rect(500, 450, 150, 80, 20);
					fill(255, 255, 255);
				}
				textSize(50);
				text("OK",540,510);
				
				/*if(dist(450, 500, mouseX, mouseY) < 30 && mousePressed) {
					snack.isPassed = true;
					playState = 2;
				}*/
			}
			else if(playState == 3) {
				Drink d, dr,usd,usdr;
				if(!drinkisDisplayed) {
					for(k=0; k<drinks.size(); k++) {
						d = drinks.get(k);
						usd = usdrinks.get(k);
						ani = Ani.to(d, (float)1.0, "x", drinksX[k],Ani.BOUNCE_OUT);
						ani = Ani.to(d, (float)1.0, "y", drinksY[k],Ani.BOUNCE_OUT);
						ani = Ani.to(usd, (float)1.0, "x", usdrinksX[k],Ani.BOUNCE_OUT);
						ani = Ani.to(usd, (float)1.0, "y", usdrinksY[k],Ani.BOUNCE_OUT);
						//ani = Ani.to(theTarget, theDuration, theFieldName, theEnd, theEasing)
					}
					drinkisDisplayed = true;
				}
				for(Drink i : drinks) i.display();
				for(Drink i : usdrinks) i.display();
				for(k=0; k<drinks.size(); k++) {
					d = drinks.get(k);
					usd = usdrinks.get(k);
					if(isMouseInShape("RECT", d.x, d.y, d.width, d.height)) {
						if (mousePressed){
							focusdrink = k;
							prevdrink = k;
						}
						d.width = 110;
						d.height = 80;
					}
					else if (isMouseInShape("RECT", usd.x, usd.y, usd.width, usd.height)){
						if (mousePressed){
							focusdrink = k+5;
							prevdrink = k+5;
						}
						usd.width = 110;
						usd.height = 80;
					}
					else {
						int j, outside=1;
							
						if (mousePressed) {
							for (j=0; j<drinks.size(); j++) {
								dr = drinks.get(j);
								usdr = drinks.get(j);
								if (isMouseInShape("RECT", dr.x, dr.y, dr.width, dr.height)) {
									outside = 0;
									break;
								}
								if (isMouseInShape("RECT", usdr.x, usdr.y, usdr.width, usdr.height)) {
									outside = 0;
									break;
								}
							}
							if (outside == 1) {
								focusdrink = -1;
							}
						}
						d.width = 100;
						d.height = 75;
					}
					if (selecteddrink == k){
						image(chooseimg,d.x,d.y-30,50,50);
						/*fill(0);
						text("choosed",d.x,d.y);*/
					}
					if ((selecteddrink-5) == k){
						image(chooseimg,usd.x,usd.y-30,50,50);
						/*fill(0);
						text("choosed",usd.x,usd.y);*/
					}
				}
				noStroke();
				if (isMouseInShape("RECT",500,450,150,80) == true) {
					fill(51, 51, 255);
					rect(500, 450, 150, 80, 20);
					fill(255, 255, 255);
					if (mousePressed&&selecteddrink>=0) {
						drinkOK = 1;
					}
				}
				else {
					fill(255, 153, 51);
					rect(500, 450, 150, 80, 20);
					fill(255, 255, 255);
				}
				textSize(50);
				text("OK",540,510);
					
				/*if(dist(450, 500, mouseX, mouseY) < 30 && mousePressed) {
					drink.isPassed = true;
					state = 4;
				}*/
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
	
	public void loadOther() {
		for(int i=0; i<8; i++) {
			if (i<=3){
				othersX[i] = 250+i*200;
				othersY[i] = 300;
			}
			else {
				othersX[i] = 250+(i-4)*200;
				othersY[i] = 400;
			}
			other = new OthersMaterial(this);
			other.y = othersY[i];
			otherImg = loadImage(other.getOthers(i));
			other.setImage(otherImg);
			others.add(other);
		}
	}
	
	public void loadSnack() {
		for(int i=0; i<5; i++) {
			snacksX[i] = 250+i*150;
			if (i%3==1){
				snacksY[i] = 100;
			}
			else {
				snacksY[i] = 250;
			}
			snack = new Snack(this, countryLocked.name);
			snack.y = snacksY[i];
			snackImg = loadImage(snack.getSnack(i));
			snack.setImage(snackImg);
			snacks.add(snack);//
		}
	}
	public void loadUssnack(){
		for (int i=0;i<5;i++){
			ussnacksX[i] = 250+i*150;
			if (i%3==1){
				ussnacksY[i] = 250;
			}
			else {
				ussnacksY[i] = 100;
			}
			ussnack = new Snack(this,"United_States");
			ussnack.y = ussnacksY[i];
			ussnackImg = loadImage(ussnack.getSnack(i));
			ussnack.setImage(ussnackImg);
			ussnacks.add(ussnack);
		}
	}
	
	public void loadDrink() {
		for(int i=0; i<5; i++) {
			drinksX[i] = 250+i*150;
			if (i%3==1){
				drinksY[i] = 100;
			}
			else {
				drinksY[i] = 250;
			}
			drink = new Drink(this, countryLocked.name);
			drink.y = drinksY[i];
			drinkImg = loadImage(drink.getDrink(i));
			drink.setImage(drinkImg);
			drinks.add(drink);//
		}
	}
	public void loadUsdrink(){
		for (int i=0;i<5;i++){
			usdrinksX[i] = 250+i*150;
			if (i%3==1){
				usdrinksY[i] = 250;
			}
			else {
				usdrinksY[i] = 100;
			}
			usdrink = new Drink(this,"United_States");
			usdrink.y = usdrinksY[i];
			usdrinkImg = loadImage(usdrink.getDrink(i));
			usdrink.setImage(usdrinkImg);
			usdrinks.add(usdrink);
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
					loadSnack();
					loadUssnack();
					loadDrink();
					loadUsdrink();
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
				if (breadOK == 1){
					breadOK = -1;
					choosefoodState = 2;
					if (selectedbread == food.bread){
						score += 10;
					}
				}
			}
			if (choosefoodState == 2){
				if (selectedmeat == focusmeat){
					selectedmeat = -1;
				}
				else {
					if (focusmeat == -1) {
						selectedmeat = prevmeat;
					}
					else {
						selectedmeat = focusmeat;
					}
				}
				if (meatOK == 1){
					meatOK = -1;
					choosefoodState = 3;
					if (selectedmeat == food.meat){
						score += 10;
					}
				}
			}
			if (choosefoodState == 3){
				if (selectedbacon == 1 && focusbacon == 1){
					selectedbacon = -1;
				}
				else {
					if (focusbacon == -1 && selectedbacon == 1) {
						selectedbacon = 1;
					}
					else {
						selectedbacon = focusbacon;
					}
				}
				if (selectedcheese == 1 && focuscheese == 1){
					selectedcheese = -1;
				}
				else {
					if (focuscheese == -1 && selectedcheese == 1) {
						selectedcheese = 1;
					}
					else {
						selectedcheese = focuscheese;
					}
				}
				if (selectedcucumber == 1 && focuscucumber == 1){
					selectedcucumber = -1;
				}
				else {
					if (focuscucumber == -1 && selectedcucumber == 1) {
						selectedcucumber = 1;
					}
					else {
						selectedcucumber = focuscucumber;
					}
				}
				if (selectedegg == 1 && focusegg == 1){
					selectedegg = -1;
				}
				else {
					if (focusegg == -1 && selectedegg == 1) {
						selectedegg = 1;
					}
					else {
						selectedegg = focusegg;
					}
				}
				if (selectedham == 1 && focusham == 1){
					selectedham = -1;
				}
				else {
					if (focusham == -1 && selectedham == 1) {
						selectedham = 1;
					}
					else {
						selectedham = focusham;
					}
				}
				if (selectedlettuce == 1 && focuslettuce == 1){
					selectedlettuce = -1;
				}
				else {
					if (focuslettuce == -1 && selectedlettuce == 1) {
						selectedlettuce = 1;
					}
					else {
						selectedlettuce = focuslettuce;
					}
				}
				if (selectedonion == 1 && focusonion == 1){
					selectedonion = -1;
				}
				else {
					if (focusonion == -1 && selectedonion == 1) {
						selectedonion = 1;
					}
					else {
						selectedonion = focusonion;
					}
				}
				if (selectedtomato == 1 && focustomato == 1){
					selectedtomato = -1;
				}
				else {
					if (focustomato == -1 && selectedtomato == 1) {
						selectedtomato = 1;
					}
					else {
						selectedtomato = focustomato;
					}
				}
				if (otherOK == 1){
					otherOK = -1;
					playState = 2;
					food.isPassed = true;
					if (selectedbacon == food.bacon){
						score += 3;
					}
					if (selectedcheese == food.cheese){
						score += 3;
					}
					if (selectedcucumber == food.cucumber){
						score += 3;
					}
					if (selectedegg == food.egg){
						score += 3;
					}
					if (selectedham == food.ham){
						score += 3;
					}
					if (selectedlettuce == food.lettuce){
						score += 3;
					}
					if (selectedonion == food.onion){
						score += 3;
					}
					if (selectedtomato == food.tamato){
						score += 3;
					}
				}
			}
			if (playState == 2) {
				if (selectedsnack == focussnack){
					selectedsnack = -1;
				}
				else {
					if (focussnack == -1) {
						selectedsnack = prevsnack;
					}
					else {
						selectedsnack = focussnack;
					}
				}
				if (snackOK == 1){
					snackOK = -1;
					playState = 3;
					snacks.get(0).isPassed = true;
					if (selectedsnack<5){
						score += 20;
					}
					else {
						score += 10;
					}
				}
			}
			if (playState == 3) {
				if (selecteddrink == focusdrink){
					selecteddrink = -1;
				}
				else {
					if (focusdrink == -1) {
						selecteddrink = prevdrink;
					}
					else {
						selecteddrink = focusdrink;
					}
				}
				if (drinkOK == 1){
					drinkOK = -1;
					state = 4;
					drinks.get(0).isPassed = true;
					if (selecteddrink<5){
						score += 20;
					}
					else {
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
		usdata = loadJSONObject("United_States/United_States.json");
		usdata_food = usdata.getJSONArray("food");
		data_food = data.getJSONArray("food");
		data_dessert = data.getJSONArray("dessert");
		data_drink = data.getJSONArray("drink");
	}
	public void loadfooddata(){
		JSONObject temp = data_food.getJSONObject(food.getchoose());
		JSONObject temp1 = usdata_food.getJSONObject(usFood.getchoose());
		String name = temp.getString("name");
		food.name = name;
		String name1 = temp1.getString("name");
		usFood.name = name1;
		//bread
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
		//meat
		String meat = temp.getString("meat");
		if (meat.equals("false")){
			food.meat = 0;
		}
		else if (meat.equals("beef")){
			food.meat = 1;
		}
		else if (bread.equals("chicken")){
			food.meat = 2;
		}
		else if (bread.equals("fish")){
			food.meat = 3;
		}
		else if (bread.equals("pork")){
			food.meat = 4;
		}
		else if (bread.equals("shrimp")){
			food.meat = 5;
		}
		//others
		String bacon = temp.getString("bacon");
		if (bacon.equals("false")){
			food.bacon = -1;
		}
		else if (bacon.equals("true")){
			food.bacon = 1;
		}
		String cheese = temp.getString("cheese");
		if (cheese.equals("false")){
			food.cheese = -1;
		}
		else if (cheese.equals("true")){
			food.cheese = 1;
		}
		String cucumber = temp.getString("cucumber");
		if (cucumber.equals("false")){
			food.cucumber = -1;
		}
		else if (cucumber.equals("true")){
			food.cucumber = 1;
		}
		String egg = temp.getString("egg");
		if (egg.equals("false")){
			food.egg = -1;
		}
		else if (egg.equals("true")){
			food.egg = 1;
		}
		String ham = temp.getString("ham");
		if (ham.equals("false")){
			food.ham = -1;
		}
		else if (ham.equals("true")){
			food.ham = 1;
		}
		String lettuce = temp.getString("vegetable");
		if (lettuce.equals("false")){
			food.lettuce = -1;
		}
		else if (lettuce.equals("true")){
			food.lettuce = 1;
		}
		String onion = temp.getString("onion");
		if (onion.equals("false")){
			food.onion = -1;
		}
		else if (onion.equals("true")){
			food.onion = 1;
		}
		String tamato = temp.getString("tomato");
		if (tamato.equals("false")){
			food.tamato = -1;
		}
		else if (tamato.equals("true")){
			food.tamato = 1;
		}
	}
}
