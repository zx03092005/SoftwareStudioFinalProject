package Client;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FilenameFilter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

import controlP5.ControlFont;
import controlP5.ControlP5;
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
	PImage img, bgState1,money,time_up,rankings;
	int totalMoney = 1000;
	private Ani ani;
	BackGround bg;
	Country country;
	File[] countryList = new File("Country").listFiles(new FilenameFilter() {
        @Override
        public boolean accept(File dir, String name) {
            return !name.equals(".DS_Store");
        }
    });
	ArrayList<Country> countrys;
	int k;
	float[] countrysX = new float[countryList.length];
	float[] countrysY = new float[countryList.length];
	Random rand	= new Random();
	Country overCountryAndNotPress = null, countryLocked = null;
	boolean isOver = false;
	boolean isDisplayed = false;
	int xOffset = 0; 
	int yOffset = 0;
	Food food, food2 ;
	Food overFoodAndNotPress = null, foodLocked=null;
	ArrayList<Food> foods;
	float[] foodsX ;
	float[] foodsY ;

	boolean foodisDisplayed = false;
	ControlP5 cp5;
	boolean addButton = false;
	
	Drink drink;
	Snack snack;
	PImage foodImg, snackImg, drinkImg;
	boolean foodSelected = false;
	int playState = 1;
	private Minim minim;
	private AudioPlayer bgMusic;
	
	int choosefoodState = 1;
	int accept = 0;
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
	
	OthersMaterial other;
	ArrayList<OthersMaterial> others;
	int[] othersX = new int[8];
	int[] othersY = new int[8];
	boolean otherisDisplayed = false;
	PImage otherImg;
	int selectedbacon = -1, selectedcheese = -1, selectedcucumber = -1, selectedegg = -1, selectedham = -1, selectedlettuce = -1, selectedonion = -1, selectedtomato = -1;
	int focusbacon = -1, focuscheese = -1, focuscucumber = -1, focusegg = -1, focusham = -1, focuslettuce = -1, focusonion = -1, focustomato = -1;
	int otherOK = -1;
	
	//Snack snack;
	ArrayList<Snack> snacks;
	int[] snacksX = new int[8];
	int[] snacksY = new int[8];
	boolean snackisDisplayed = false;
	//PImage snackImg;
	int selectedsnack = -1;
	int focussnack = -1;
	int prevsnack = -1;
	int snackOK = 0;
	
	ArrayList<Drink> drinks;
	int[] drinksX = new int[8];
	int[] drinksY = new int[8];
	boolean drinkisDisplayed = false;
	int selecteddrink = -1;
	int focusdrink = -1;
	int prevdrink = -1;
	int drinkOK = 0;
	PImage chooseimg;
	
	boolean materialisdisplayed_b = false;
	boolean materialisdisplayed_m = false;
	boolean materialisdisplayed_o = false;
	boolean mealisdisplayed = false;

	Animation animation;
	ArrayList<Animation> animations;
	PImage animationImg;
	int animationIndex = 0;
	int foodindex = 0;
	int plus10Flag = 0;
	int goodFlag = 0;
	int gogoFlag = 0;
	int wrongFlag = 0;
	PlusScore plus10, good, gogo, wrong;
	int thisScore = 0;
	int gooddisplay = 0, gogodisplay = 0, plus10display = 0;
	boolean wrongdisplay = false;
	
	int centSecond = 0, gameTime = 5,animationSec=0;	
	float timeRate = 0;
	Animation hellow_animation, crying_animation, eating_animation, waiting_animation, bigcrying_animation;
	public MainApplet(Socket socket) {
		
		state = 2;
		countrys = new ArrayList<Country>();
		breads = new ArrayList<Bread>();
		meats = new ArrayList<Meat>();
		others = new ArrayList<OthersMaterial>();
		snacks = new ArrayList<Snack>();
		drinks = new ArrayList<Drink>();
		foods = new ArrayList<Food>();
	
	}
	
	public void setup() {
		size(1000,650);
		
		bgState1 = loadImage("bgState1.jpg");
		money = loadImage("money.png");
		Ani.init(this);
		bg = new BackGround(this, 1, 255);
		loadCountry();
		loadBread();
		loadMeat();
		loadOther();
		loadAnimation();
		chooseimg = loadImage("okay.png");
		cp5 = new ControlP5(this);
		plus10 = new PlusScore(this, 1, 100);
		good = new PlusScore(this, 2, 100);
		gogo = new PlusScore(this, 3, 100);
		wrong = new PlusScore(this,4,100);
		smooth();			
		time_up=loadImage("time_up.gif");
		rankings=loadImage("rankings.png");
	}

	

	public void draw() {

		
		if(state == 0) {
			if(bg.chroma > 5 && bg.photo == 1) ani = Ani.to(bg, (float)10.0, "chroma", 5);
			if(bg.chroma < 6 && bg.photo == 1) { 
				bg.initChroma(); 
				bg.photo = 2;
			}
			if(bg.chroma > 5 && bg.photo == 2) ani = Ani.to(bg, (float)10.0, "chroma", 10);
			if(bg.chroma < 11 && bg.photo == 2) { 
				bg.initChroma(); 
				bg.photo = 3; 
			}
			if(bg.chroma > 5 && bg.photo == 3) ani = Ani.to(bg, (float)10.0, "chroma", 10);
			if(bg.chroma < 11 && bg.photo == 3) { 
				bg.initChroma(); 
				bg.photo = 4; 
			}
			if(bg.chroma > 5 && bg.photo == 4) ani = Ani.to(bg, (float)10.0, "chroma", 10);
			if(bg.chroma < 11 && bg.photo == 4) { 
				bg.chroma = 0;
				bg.photo = 5;
				this.state = 1; 
			}
			bg.display();
		}
		else if(state == 1) {
			noTint();
			image(bgState1, 0, 0);
			if(!addButton) {
				//create button
				cp5.addButton("buttonA").setLabel("Start").setPosition(300,550).setSize(200,50);
				cp5.addButton("buttonB").setLabel("How").setPosition(700,550).setSize(200,50);
				// set the Font of word in the button
				ControlFont font = new ControlFont(createFont("Consolas",20,true),241);
				cp5.getController("buttonA").getCaptionLabel().setFont(font).setSize(24);
				cp5.getController("buttonB").getCaptionLabel().setFont(font).setSize(24);
				addButton = true;
			}
			else cp5.show();
		}
		else if(state == 2) {
			cp5.hide();
			Country c;
			background(255,255,153);
			//image(money, 1150, 20);
			//fill(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));
			//textSize(45);
			//text("x" + totalMoney, 1220, 70);
			stroke(4);
			fill(127);
			rect(490, 290, 220, 150, 20);
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
					ani = Ani.to(c, (float)1.5, "x", countrysX[k]);
					ani = Ani.to(c, (float)1.5, "y", countrysY[k]);
				}
				isDisplayed = true;
			}
			for(Country i : countrys) i.display();
			for(Country i : countrys) {
				if(isMouseInShape("RECT", i.x, i.y, i.img.width, i.img.height) && !mousePressed) {
					noStroke();
					fill(126, 32, 174);
					rect(mouseX+10, mouseY-15, i.name.length()*14, 30, 100);
					fill(255, 255, 0);
					textSize(16);
					text(i.name, mouseX+25, mouseY+5);
					i.width = 220;
					i.height = 143;
					overCountryAndNotPress = i;
				}
				else {
					i.width = 200;
					i.height = 130;
				}
			}
			for(Country i : countrys) {
				if(isMouseInShape("RECT", i.x, i.y, i.img.width, i.img.height)) {
					isOver = true;
					break;
				}
				else isOver = false;
			}
			
			
			//display animation
			hellow_animation.display(animationIndex);
			animationIndex++;
			delay(50);	
			if (animationIndex==hellow_animation.getSize()){
				//
				animationIndex = hellow_animation.getSize()-10;
				// end and calculate the score
			}
		}
		else if(state == 3) {
			cp5.hide();
			if(bg.chroma < 255 && bg.photo == 5) ani = Ani.to(bg, (float)4.0, "chroma", 254);
			if(mousePressed) {
				state = 1;
				bg.chroma = 0;
			}
			bg.display();
		}
		else if(state == 4) {
			
			background(204, 230, 255);
			//background(countryLocked.background);
			image(countryLocked.background, 0, 0);
			fill(0);
			textSize(40);
			thisScore = 0;
			
			if (plus10Flag == 1) {
				if (plus10display == 0) {
					ani = Ani.to(plus10, (float)1.0, "chroma", 240);
					plus10display = 1;
				}
				if (plus10.chroma != 240) {
					plus10.display();
				}
				else {
					plus10Flag = 0;
					plus10.initChroma();
					plus10display = 0;
				}
			}
			if (goodFlag == 1) {
				if (gooddisplay == 0) {
					ani = Ani.to(good, (float)1.0, "chroma", 240);
					gooddisplay = 1;
				}
				if (good.chroma != 240) {
					good.display();
				}
				else {
					goodFlag = 0;
					good.initChroma();
					gooddisplay = 0;
				}
			}
			
			if (gogoFlag == 1) {
				if (gogodisplay == 0) {
					ani = Ani.to(gogo, (float)1.0, "chroma", 240);
					gogodisplay = 1;
				}
				if (gogo.chroma != 240) {
					gogo.display();
				}
				else {
					gogoFlag = 0;
					gogo.initChroma();
					gogodisplay = 0;
				}
			}
			
			if (wrongFlag == 1) {
				if (!wrongdisplay) {
					ani = Ani.to(wrong, (float)1.0, "chroma", 240);
					wrongdisplay = true;
				}
				if (wrong.chroma != 240) {
					wrong.display();
				}
				else {
					wrongFlag = 0;
					wrong.initChroma();
					wrongdisplay = false;
				}
			}
			if (playState == 1) {
				food.foodRect();
			}
			else if (playState == 2) {
				snacks.get(0).snackRect();
			}
			else if (playState == 3) {
				drinks.get(0).drinkRect();
			}
			//ellipse(450, 500, 60, 60);
			fill(0);
			text(Integer.toString(score),900,70);
			accept = 1;
			if(playState == 1) {
				if(!foodSelected) {
					food.x = 500;
					food.y = -50;
					food.width = 200;
					food.height = 150;
					foodImg = loadImage(food.getFood());
					food.setImage(foodImg);
					//ani = Ani.to(food, (float)1.0, "x", 500);
					ani = Ani.to(food, (float)1.0, "y", 80,Ani.BOUNCE_OUT);
					foodSelected = true;
				}
				loadfooddata();
				food.display(); 
				fill(0);
				textSize(25);
				text(food.name, 500, 280);
				textSize(40);
				if (breadOK == -1&&choosefoodState!=1){
					Bread b;
					b = breads.get(selectedbread);
					b.x = 60;
					b.y = 150;
					b.display();
				}
				if (meatOK == -1&&choosefoodState!=2){
					Meat m;
					m = meats.get(selectedmeat);
					m.x = 60;
					m.y = 230;
					m.display();
				}
				if (choosefoodState == 1){
					if (food.y == 80){
						fill(0);
						textSize(40);
						text("Please choose bread", 300, 50);
					}
					Bread b, br;
					if(!breadisDisplayed) {
						for(k=0; k<breads.size(); k++) {
							b = breads.get(k);
							b.width = 100;
							b.height = 75;
							b.x = 500;
							b.y = 100;
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
					if (isMouseInShape("RECT",500,590,150,80) == true) {
						fill(51, 51, 255);
						rect(500, 590, 150, 80, 20);
						fill(255, 255, 255);
						if (mousePressed&&selectedbread>=0) {
							breadOK = 1;
						}
					}
					else {
						fill(255, 153, 51);
						rect(500, 590, 150, 80, 20);
						fill(255, 255, 255);
					}
					textSize(50);
					text("OK",540,650);
					if (plus10Flag == 1){
						if (plus10.chroma==239){
							choosefoodState = 2;
						}
					}
					else if (wrongFlag == 1){
						if (wrong.chroma==239){
							choosefoodState = 2;
						}
					}
				}
				else if (choosefoodState == 2){
					fill(0);
					textSize(40);
					text("Please choose meat", 300, 50);
					Meat m, mr;
					if(!meatisDisplayed) {
						for(k=0; k<meats.size(); k++) {
							m = meats.get(k);
							m.width = 100;
							m.height = 75;
							m.x = 1000;
							m.y = 100;
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
					if (isMouseInShape("RECT",500,590,150,80) == true) {
						fill(51, 51, 255);
						rect(500, 590, 150, 80, 20);
						fill(255, 255, 255);
						if (mousePressed&&selectedmeat>=0) {
							meatOK = 1;
						}
					}
					else {
						fill(255, 153, 51);
						rect(500, 590, 150, 80, 20);
						fill(255, 255, 255);
					}
					textSize(50);
					text("OK",540,650);
					if (plus10Flag == 1){
						if (plus10.chroma==238){
							choosefoodState = 3;
						}
					}
					else if (wrongFlag == 1){
						if (wrong.chroma==238){
							choosefoodState = 3;
						}
					}
				}
				else if (choosefoodState == 3){
					fill(0);
					textSize(40);
					text("Please choose ingredients", 300, 50);
					OthersMaterial o, ot;
					if(!otherisDisplayed) {
						for(k=0; k<others.size(); k++) {
							o = others.get(k);
							o.width = 100;
							o.height = 75;
							o.x = 500;
							o.y = 100;
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
					if (isMouseInShape("RECT",500,600,150,80) == true) {
						fill(51, 51, 255);
						rect(500, 600, 150, 80, 20);
						fill(255, 255, 255);
						if (mousePressed) {
							otherOK = 1;
							materialisdisplayed_b = false;
							materialisdisplayed_m = false;
							materialisdisplayed_o = false;
							mealisdisplayed = false;
						}
					}
					else {
						fill(255, 153, 51);
						rect(500, 600, 150, 80, 20);
						fill(255, 255, 255);
					}
					textSize(50);
					text("OK",540,660);
				}
				/*if(dist(450, 500, mouseX, mouseY) < 30 && mousePressed) {
					food.isPassed = true;
					playState = 2;
				}*/
				if (goodFlag == 1){
					if (good.chroma==239){
						playState = 2;
					}
				}
				else if (gogoFlag == 1){
					if (gogo.chroma==239){
						playState = 2;
					}
				}
			}
			else if(playState == 2) {
				Bread b;
				Meat m;
				b = breads.get(selectedbread);
				if (!materialisdisplayed_b){
					ani = Ani.to(b, (float)1.0, "y", 600, Ani.BOUNCE_OUT);
					materialisdisplayed_b = true;
				}
				
				m = meats.get(selectedmeat);
				if (!materialisdisplayed_m){
					ani = Ani.to(m, (float)1.0, "y", 600, Ani.BOUNCE_OUT);
					materialisdisplayed_m = true;
				}
				
				ArrayList<OthersMaterial> o;
				o = new ArrayList<OthersMaterial>();
				if (selectedbacon == 1){
					o.add(others.get(0));
				}
				if (selectedcheese == 1){
					o.add(others.get(1));
				}
				if (selectedcucumber == 1){
					o.add(others.get(2));
				}
				if (selectedegg == 1){
					o.add(others.get(3));
				}
				if (selectedham == 1){
					o.add(others.get(4));
				}
				if (selectedlettuce == 1){
					o.add(others.get(5));
				}
				if (selectedonion == 1){
					o.add(others.get(6));
				}
				if (selectedtomato == 1){
					o.add(others.get(7));
				}
				if (!materialisdisplayed_o){
					for (OthersMaterial os : o){
						ani = Ani.to(os, (float)1.0, "y", 600, Ani.BOUNCE_OUT);
					}
					materialisdisplayed_o = true;
				}
				if (o.get(o.size()-1).y != 600){
					b.display();
					m.display();
					for (OthersMaterial k:o)k.display();
				}
				if (o.get(o.size()-1).y == 600){
					if (!mealisdisplayed){
						ani = Ani.to(food, (float)1.0, "x", 1000);
						mealisdisplayed = true;
					}
				}
				if (o.get(o.size()-1).y == 600&&food.x != 1000){
					food.display();
				}
				Snack s, sn;
				if(!snackisDisplayed) {
					for(k=0; k<snacks.size(); k++) {
						s = snacks.get(k);
						s.width = 100;
						s.height = 75;
						s.x = 500;
						s.y = 100;
						ani = Ani.to(s, (float)1.0, "x", snacksX[k],Ani.BOUNCE_OUT);
						ani = Ani.to(s, (float)1.0, "y", snacksY[k],Ani.BOUNCE_OUT);
						//ani = Ani.to(theTarget, theDuration, theFieldName, theEnd, theEasing)
					}
					snackisDisplayed = true;
				}
				int k=0;
				for(Snack i : snacks) {
					JSONObject temp = data_dessert.getJSONObject(k++);
					String name = temp.getString("name");
					i.name = name.replace("_", " ");
					i.display();
					fill(0);
					textSize(18);
					text(i.name, i.x, i.y);
				}
				for(k=0; k<snacks.size(); k++) {
					s = snacks.get(k);
					if(isMouseInShape("RECT", s.x, s.y, s.width, s.height)) {
						if (mousePressed){
							focussnack = k;
							prevsnack = k;
						}
						s.width = 110;
						s.height = 80;
					}
					else {
						int j, outside=1;
							
						if (mousePressed) {
							for (j=0; j<snacks.size(); j++) {
								sn = snacks.get(j);
								if (isMouseInShape("RECT", sn.x, sn.y, sn.width, sn.height)) {
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
				}
				noStroke();
				if (isMouseInShape("RECT",500,600,150,80) == true) {
					fill(51, 51, 255);
					rect(500, 600, 150, 80, 20);
					fill(255, 255, 255);
					if (mousePressed&&selectedsnack>=0) {
						snackOK = 1;
					}
				}
				else {
					fill(255, 153, 51);
					rect(500, 600, 150, 80, 20);
					fill(255, 255, 255);
				}
				textSize(50);
				text("OK",540,660);
				
				/*if(dist(450, 500, mouseX, mouseY) < 30 && mousePressed) {
					snack.isPassed = true;
					playState = 2;
				}*/
			}
			else if(playState == 3) {
				Drink d, dr;
				if(!drinkisDisplayed) {
					for(k=0; k<drinks.size(); k++) {
						d = drinks.get(k);
						d.width = 100;
						d.height = 75;
						d.x = 500;
						d.y = 100;
						ani = Ani.to(d, (float)1.0, "x", drinksX[k],Ani.BOUNCE_OUT);
						ani = Ani.to(d, (float)1.0, "y", drinksY[k],Ani.BOUNCE_OUT);
						//ani = Ani.to(theTarget, theDuration, theFieldName, theEnd, theEasing)
					}
					drinkisDisplayed = true;
				}
				int k=0;
				for(Drink i : drinks) {
					JSONObject temp = data_drink.getJSONObject(k++);
					String name = temp.getString("name");
					i.name = name.replace("_", " ");
					i.display();
					fill(0);
					textSize(18);
					text(i.name, i.x, i.y);
				}
				for(k=0; k<drinks.size(); k++) {
					d = drinks.get(k);
					if(isMouseInShape("RECT", d.x, d.y, d.width, d.height)) {
						if (mousePressed){
							focusdrink = k;
							prevdrink = k;
						}
						d.width = 110;
						d.height = 80;
					}
					else {
						int j, outside=1;
							
						if (mousePressed) {
							for (j=0; j<drinks.size(); j++) {
								dr = drinks.get(j);
								if (isMouseInShape("RECT", dr.x, dr.y, dr.width, dr.height)) {
									outside = 0;
									//break;
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
				}
				noStroke();
				if (isMouseInShape("RECT",500,600,150,80) == true) {
					fill(51, 51, 255);
					rect(500, 600, 150, 80, 20);
					fill(255, 255, 255);
					if (mousePressed&&selecteddrink>=0) {
						drinkOK = 1;
					}
				}
				else {
					fill(255, 153, 51);
					rect(500, 600, 150, 80, 20);
					fill(255, 255, 255);
				}
				textSize(50);
				text("OK",540,660);
					
				/*if(dist(450, 500, mouseX, mouseY) < 30 && mousePressed) {
					drink.isPassed = true;
					state = 4;
				}*/
			}
			animationSec=centSecond;
			if (animationSec<gameTime/3*17){

				waiting_animation.display(animationIndex);
				animationIndex++;
				if (animationIndex==waiting_animation.getSize()){
					//
					animationIndex = 0;
					// end and calculate the score
				}
			}
			else if (animationSec<gameTime*2/3*17){
				crying_animation.display(animationIndex);
				animationIndex++;
				if (animationIndex==crying_animation.getSize()){
					//
					animationIndex = crying_animation.getSize()-30;
					// end and calculate the score
				}
				if(animationSec==gameTime*2/3*17-1) animationIndex=0;
			}
			else {
				bigcrying_animation.display(animationIndex);
				animationIndex++;
				if (animationIndex==bigcrying_animation.getSize()){
					//
					animationIndex = 0;
					// end and calculate the score
				}
			}
			//display animation
			delay(50);	
			
			timeRate = (float)(100-(100*centSecond/(gameTime*17)))/100;
			if(timeRate > 0.5)
				fill(255, 255, 0);
			else if(timeRate > 0.25)
				fill(255, 128, 0);
			else 
				fill(255, 0, 0);
			
			rect(this.getWidth()-20, 0, 20, timeRate*this.getHeight());
			
			if(centSecond++ >= gameTime*17) {
				state = 5;
				animationIndex = 0;
				background(0);
			}
			
		}
		//state 4 time's up animation
		else if(state == 5) {			
			background(204, 230, 255);


			image(time_up,0, 0, 1366, 768);
			
		}
		//score animation
		else if(state == 6) {

			background(204, 230, 255);
			fill(0);
			Animation a;

			eating_animation.display(animationIndex);
			animationIndex++;
			delay(50);
			
			if (animationIndex==eating_animation.getSize()-30){
				//
				state = 7;
				// end and calculate the score
			}
		}
		// choose the favorite food and locate them in the 1st~3rd
		else if(state == 7) {
			background(204, 230, 255);
			fill(0);
			eating_animation.display(animationIndex);
			animationIndex++;
			delay(50);

			String msgTotalScore = String.valueOf(score);
			textSize(40);
			text("Total Score = "+msgTotalScore, 80, 100);

			//add by qqhsuanwu
			String msgTotalMoney = String.valueOf(score * 2 + 50);
			textSize(40);
			text("Total Money = "+msgTotalMoney, 520, 100);

			if (animationIndex==eating_animation.getSize()){
				loadFood();
				animationIndex=0;
				state = 8;
			}
			
		}
		else if (state == 8){
			background(204, 230, 255);
			fill(0);
			Food f;

			image(rankings ,355, 190,650,379);
			
			stroke(4);
			fill(127);
//			rect(490, 290, 220, 150, 20);
			for(k=0; k<foods.size(); k++) {
				f = foods.get(k);
				fill(127);
				stroke(0);
				strokeWeight(5);
//				rect(foodsX[k]-10, foodsY[k]-10, f.width+20, f.height+20, 20);
			}
			if(!foodisDisplayed) {
				for(k=0; k<foods.size(); k++) {
					f = foods.get(k);
					ani = Ani.to(f, (float)1.5, "x", foodsX[k]);
					ani = Ani.to(f, (float)1.5, "y", foodsY[k]);
				}
				foodisDisplayed = true;
			}
			for(Food i : foods) i.display();
			for(Food i : foods) {
				if(isMouseInShape("RECT", i.x, i.y, i.width, i.height) && !mousePressed) {
					noStroke();
					fill(126, 32, 174);
//					rect(mouseX+10, mouseY-15, 30, 100);
					i.width = 220;
					i.height = 143;
					overFoodAndNotPress = i;
//					System.out.println("in");
				}
				else {
					i.width = 200;
					i.height = 130;
				}
			}
			for(Food i : foods) {
				if(isMouseInShape("RECT", i.x, i.y, i.width, i.height)) {
					isOver = true;
					break;
				}
				else isOver = false;
			}
		}
		else if(state == 9) {
			background(255);
			int sum = 0, i = 0, num = 10, result = 0;
			for(i=0; i<food.allFood.length; i++) {
				sum += 136-food.allFood.length*10;
				image(food.allFood[i], sum, 550, 100, 80);
				sum += 100;
			}
			sum = 0;
			float theta = 0;
			for(i=0; i<food.allFood.length; i++) {
				fill(0, 255, (int)255/food.allFood.length);
				sum += 136-food.allFood.length*10+20;
				rect(sum, 490, 60, -((i+350)*abs(sin(theta))), 20);
				theta += (TWO_PI / 9);
				sum += 80;
			}
		}
	}
	
	public void loadCountry() {
		float theta = 0;
		for(int i=0; i<countryList.length; i++) {
			countrysX[i] = 500 + 380 * cos(theta);
			countrysY[i] = 300 + 300 * sin(theta);
			theta += (TWO_PI / 6);
			if(i%2 == 0)
				country = new Country(this, 400, 260, countrysX[i], countrysY[i], countryList[i].getName(), false);
			else
				country = new Country(this, 400, 260, countrysX[i], countrysY[i], countryList[i].getName(), true);
			countrys.add(country);
		}
	}
	
	public void loadBread() {
		for(int i=0; i<6; i++) {
			breadsX[i] = 250+i*110;
			breadsY[i] = (int)(450-0.3*(i-2.5)*(i-2.5)*80);
			bread = new Bread(this);
			breadImg = loadImage(bread.getBread(i));
			bread.setImage(breadImg);
			breads.add(bread);
		}
	}
	
	public void loadMeat() {
		for(int i=0; i<6; i++) {
			meatsX[i] = 250+i*110;
			meatsY[i] = (int)(450-0.3*(i-2.5)*(i-2.5)*80);
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
				othersY[i] = 350;
			}
			else {
				othersX[i] = 250+(i-4)*200;
				othersY[i] = 450;
			}
			other = new OthersMaterial(this);
			other.y = othersY[i];
			otherImg = loadImage(other.getOthers(i));
			other.setImage(otherImg);
			others.add(other);
		}
	}
	
	public void loadSnack() {
		for(int i=0; i<8; i++) {
			snacksX[i] = 250+(i%3)*300;
			if (i < 3){
				snacksY[i] = 100;
			}
			else if (i < 6){
				snacksY[i] = 250;
			}
			else {
				snacksY[i] = 400;
			}
			snack = new Snack(this, countryLocked.name);
			snack.y = snacksY[i];
			snackImg = loadImage(snack.getSnack(i));
			snack.setImage(snackImg);
			snacks.add(snack);
		}
	}

	public void loadFood() {

		food2 = new Food(this,countryLocked.name,false);
		foodsX = new float[food2.getSize()];
		foodsY = new float[food2.getSize()];
		for(int i=0; i<food2.getSize(); i++) {
			if (i< food2.getSize()/2){

				foodsX[i] = 100;
				foodsY[i] = 30 + 140*i;
				food2.initX=100;
				food2.initY=30 + 140*i;
			}
			else {
				foodsX[i] = 1100;
				foodsY[i] = 30 + 140*(i-food2.getSize()/2);
				food2.initX=1100;
				food2.initY=30 + 140*(i-food2.getSize()/2);
				
			}
			foodImg = loadImage(food2.getFood(i));
			food2.setImage(foodImg);
			foods.add(food2);
			food2 = new Food(this,countryLocked.name,false);
		}
	}
	
	public void loadDrink() {
		for(int i=0; i<8; i++) {
			drinksX[i] = 250+(i%3)*300;
			if (i < 3){
				drinksY[i] = 100;
			}
			else if (i < 6){
				drinksY[i] = 250;
			}
			else {
				drinksY[i] = 400;
			}
			drink = new Drink(this, countryLocked.name);
			drink.y = drinksY[i];
			drinkImg = loadImage(drink.getDrink(i));
			drink.setImage(drinkImg);
			drinks.add(drink);//
		}
	}
	
	public void mousePressed() {
		countryLocked = null;
		foodLocked = null;
		if (isOver) {
			if(!overCountryAndNotPress.isLocked) {
				countryLocked = overCountryAndNotPress;
				xOffset = mouseX-countryLocked.x; 
				yOffset = mouseY-countryLocked.y; 
			}
			if (state==8){

				if(!overFoodAndNotPress.isLocked) {
					foodLocked = overFoodAndNotPress;
					xOffset = mouseX-foodLocked.x; 
					yOffset = mouseY-foodLocked.y; 
				}
			}
		}
		if (state==6){
			state=7;
			animationIndex=eating_animation.getSize()-30;
		}
		if (state==5) state=6;
	}
	
	public void mouseDragged() {
		if (countryLocked != null) {
			countryLocked.x = mouseX - xOffset;
			countryLocked.y = mouseY - yOffset;
		}
		if (foodLocked != null) {
			foodLocked.x = mouseX - xOffset;
			foodLocked.y = mouseY - yOffset;
		}
	}
	
	public void mouseReleased() {
		if (state==8 && foodLocked!=null){
			if(isMouseInShape("RECT", 490, 290, 220, 150)) {
				foodLocked.inRect = true;
				foodLocked.x = 370;
				foodLocked.y = 230;
				if (accept == 0){
					food = new Food(this, countryLocked.name,false);
					//animations.clear();
					state = 6;
				}
			}else {
				ani = Ani.to(foodLocked, 1.0f, "x", foodLocked.initX);
				ani = Ani.to(foodLocked, 1.0f, "y", foodLocked.initY);
			}
		}
		if(countryLocked != null) {
			if(isMouseInShape("RECT", 490, 290, 220, 150)&&state == 2) {
				countryLocked.inRect = true;
				countryLocked.x = 370;
				countryLocked.y = 230;
				if (accept == 0){
					food = new Food(this, countryLocked.name,false);
					loadSnack();
					loadDrink();
					loaddata();
					animationIndex=0;
					//animations.clear();
					state = 4;
				}
			}
			else {
				ani = Ani.to(countryLocked, 1.0f, "x", countryLocked.initX);
				ani = Ani.to(countryLocked, 1.0f, "y", countryLocked.initY);
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
					//choosefoodState = 2;
					if (selectedbread == food.bread){
						score += 10;
						plus10Flag = 1;
					}
					else {
						wrongFlag = 1;
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
					//choosefoodState = 3;
					if (selectedmeat == food.meat){
						score += 10;
						plus10Flag = 1;
					}
					else {
						wrongFlag = 1;
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
					food.x = 50;
					food.y = 600;
					food.width = 150;
					food.height = 90;
					otherOK = -1;
					int pos = 0;
					OthersMaterial o;
					if (selectedbacon == 1){
						o = others.get(0);
						o.x = 60;
						o.y = 310+pos;
						pos += 80;
						o.display();
					}
					if (selectedcheese == 1){
						o = others.get(1);
						o.x = 60;
						o.y = 310+pos;
						pos += 80;
						o.display();
					}
					if (selectedcucumber == 1){
						o = others.get(2);
						o.x = 60;
						o.y = 310+pos;
						pos += 80;
						o.display();
					}
					if (selectedegg == 1){
						o = others.get(3);
						o.x = 60;
						o.y = 150+pos;
						pos += 50;
						o.display();
					}
					if (selectedham == 1){
						o = others.get(4);
						o.x = 60;
						o.y = 310+pos;
						pos += 80;
						o.display();
					}
					if (selectedlettuce == 1){
						o = others.get(5);
						o.x = 60;
						o.y = 310+pos;
						pos += 80;
						o.display();
					}
					if (selectedonion == 1){
						o = others.get(6);
						o.x = 60;
						o.y = 310+pos;
						pos += 80;
						o.display();
					}
					if (selectedtomato == 1){
						o = others.get(7);
						o.x = 60;
						o.y = 310+pos;
						pos += 80;
						o.display();
					}
					playState = 2;
					food.isPassed = true;
					if (selectedbacon == food.bacon){
						score += 3;
						thisScore += 3;
					}
					else {
						score -= 3;
						thisScore -= 3;
					}
					if (selectedcheese == food.cheese){
						score += 3;
						thisScore += 3;
					}
					else {
						score -= 3;
						thisScore -= 3;
					}
					if (selectedcucumber == food.cucumber){
						score += 3;
						thisScore += 3;
					}
					else {
						score -= 3;
						thisScore -= 3;
					}
					if (selectedegg == food.egg){
						score += 3;
						thisScore += 3;
					}
					else {
						score -= 3;
						thisScore -= 3;
					}
					if (selectedham == food.ham){
						score += 3;
						thisScore += 3;
					}
					else {
						score -= 3;
						thisScore -= 3;
					}
					if (selectedlettuce == food.lettuce){
						score += 3;
						thisScore += 3;
					}
					else {
						score -= 3;
						thisScore -= 3;
					}
					if (selectedonion == food.onion){
						score += 3;
						thisScore += 3;
					}
					else {
						score -= 3;
						thisScore -= 3;
					}
					if (selectedtomato == food.tamato){
						score += 3;
						thisScore += 3;
					}
					else {
						score -= 3;
						thisScore -= 3;
					}
					if (thisScore > 10) {
						goodFlag = 1;
					}
					else {
						gogoFlag = 1;
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
					foodSelected = false;
					playState = 1;
					choosefoodState = 1;
					accept = 0;
					breadisDisplayed = false;
					selectedbread = -1;
					focusbread = -1;
					prevbread = -1;
					breadOK = 0;
					meatisDisplayed = false;
					selectedmeat = -1;
					focusmeat = -1;
					prevmeat = -1;
					meatOK = 0;
					otherisDisplayed = false;
					selectedbacon = -1; selectedcheese = -1; selectedcucumber = -1; selectedegg = -1; selectedham = -1; selectedlettuce = -1; selectedonion = -1; selectedtomato = -1;
					focusbacon = -1; focuscheese = -1; focuscucumber = -1; focusegg = -1; focusham = -1; focuslettuce = -1; focusonion = -1; focustomato = -1;
					otherOK = 0;
					snackisDisplayed = false;
					selectedsnack = -1;
					focussnack = -1;
					prevsnack = -1;
					snackOK = 0;
					drinkisDisplayed = false;
					selecteddrink = -1;
					focusdrink = -1;
					prevdrink = -1;
					drinkOK = 0;
					
				}
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public void keyPressed() {
		if(keyEvent.getKeyCode() == KeyEvent.VK_F1) {
			totalMoney += 1000;
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
		else if (meat.equals("chicken")){
			food.meat = 2;
		}
		else if (meat.equals("fish")){
			food.meat = 3;
		}
		else if (meat.equals("pork")){
			food.meat = 4;
		}
		else if (meat.equals("shrimp")){
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
	
	private void loadAnimation() {
		hellow_animation = new Animation(this, "hellow_animation", 640, 320, 900, 500 ) ;
		waiting_animation = new Animation(this, "waiting_animation", 960, 480, 750, 400 ) ;
		crying_animation = new Animation(this, "crying_animation", 640, 320, 900, 500 ) ;
		bigcrying_animation = new Animation(this, "bigcrying_animation", 640, 320, 900, 500 ) ;
		eating_animation = new Animation(this, "eating_animation", 1366, 768,0,0 ) ;
		
	}
	
	public void buttonA() {
		this.state = 2;
	}
	// if press the buttonB(CLEAR) remove each node from the big circle
	public void buttonB() {
		this.state = 3;
	}
}
