package graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Random;

import main.Game;

public class GameGraphics {

	public LinkedList<Clouds> clouds = new LinkedList<Clouds>();
	public LinkedList<Grass> grass = new LinkedList<Grass>();
	public LinkedList<Mountain> mountain = new LinkedList<Mountain>();
	public Rock rock;
	public Moon moon;
	public ShopPlace shopPlace;
	
	private int holdGrassTime = 25, holdCloudTime = 3;
	private int[] xStar = new int[200], yStar = new int[200];
	
	private boolean grassAnimation = false;
	
	public GameGraphics() {
		
		randomStars();
		
		initClouds();
		initGrass();
		initMountains();
		initObjects();
		
	}
	
	public void tick() {
		
		animateClouds();
		animateGrass();
		
		for(int i = 0; i < mountain.size(); i++)
			mountain.get(i).delay--;
		
	}
	
	public void render(Graphics g) {
		
		drawBackground(g);
		drawStars(g);
		drawMoon(g);
		drawMountains(g);
		drawBackgroundGrass(g);
		drawClouds(g);
		drawGrass(g);
		drawObjects(g);
		 
	}
	
	private void randomStars() {
		
		Random rand = new Random();
		for(int i = 0; i < xStar.length; i++) {
			
			xStar[i] = rand.nextInt(Game.WIDTH) + 1;
			yStar[i] = (rand.nextInt(Game.HEIGHT) + 1) - (Game.HEIGHT - 450);
				
		}
		
	}
	
	private void initClouds() {

		clouds.add(new Clouds(30, 50, 0.75f, 2, Game.clouds));
		clouds.add(new Clouds(220, 100, 0.5f, 2, Game.clouds));		
		clouds.add(new Clouds(142, 190, 1.10f, 1, Game.clouds));
		clouds.add(new Clouds(480, 230, 0.5f, 1, Game.clouds));
		clouds.add(new Clouds(450, 66, 1.5f, 2, Game.clouds));
		clouds.add(new Clouds(790, 90, 0.5f, 1, Game.clouds));
		clouds.add(new Clouds(800, 190, 1.10f, 1, Game.clouds));
		clouds.add(new Clouds(950, 45, 1.25f, 2, Game.clouds));
		clouds.add(new Clouds(1100, 200, 0.5f, 2, Game.clouds));		
		clouds.add(new Clouds(1200, 120, 0.75f, 2, Game.clouds));
			
	}
	
	private void initGrass() {
		
		grass.add(new Grass(75, 460, 1.5f, 2, Game.grass));
		grass.add(new Grass(50, 570, 1f, 2, Game.grass));
		grass.add(new Grass(80, 650, 2f, 2, Game.grass));
		grass.add(new Grass(200, 500, 1.5f, 1, Game.grass));
		grass.add(new Grass(350, 550, 1f, 1, Game.grass));
		grass.add(new Grass(250, 600, 1f, 3, Game.grass));
		grass.add(new Grass(350, 680, 1f, 3, Game.grass));
		grass.add(new Grass(650, 650, 2f, 1, Game.grass));
		grass.add(new Grass(400, 480, 1f, 2, Game.grass));
		grass.add(new Grass(480, 610, 2f, 2, Game.grass));
		grass.add(new Grass(550, 480, 1.5f, 3, Game.grass));
		grass.add(new Grass(640, 530, 1f, 1, Game.grass));
		grass.add(new Grass(800, 620, 1f, 2, Game.grass));
		grass.add(new Grass(850, 480, 1f, 1, Game.grass));
		
	}
	
	private void initMountains() {
		
		mountain.add(new Mountain(false, 200, 300, 2f, 2, Game.mountain));
		mountain.add(new Mountain(true, 50, 185, 3f, 2, Game.mountain));
		mountain.add(new Mountain(false, 250, 360, 1f, 2, Game.mountain));

		mountain.add(new Mountain(false, 500, 350, 1.75f, 2, Game.mountain));
		
		mountain.add(new Mountain(true, 900, 255, 2.25f, 2, Game.mountain));
		mountain.add(new Mountain(false, 850, 300, 2f, 2, Game.mountain));
		mountain.add(new Mountain(false, 980, 385, 0.75f, 2, Game.mountain));

		mountain.add(new Mountain(false, 1200, 365, 1f, 2, Game.mountain));

	}
	
	private void initObjects() {
		
		rock = new Rock(Game.WIDTH - 94 * 6, Game.HEIGHT - 105 * 6, 6f, 1, Game.rock);
		moon = new Moon(Game.WIDTH - 150, 50, 0.75f, 1, Game.moon);
		shopPlace = new ShopPlace(100, 510, 2f, 1, Game.shop_place);
		
	}
	
	private void drawBackground(Graphics g) {
		
		g.setColor(Color.GRAY.darker().darker().darker().darker().darker());
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		
	}
	
	private void drawStars(Graphics g) {	
		
			g.setColor(Color.WHITE);
			
			for(int i = 0; i < xStar.length; i++) {
				
				if(i % 10 == 0 || i % 10 == 5)
					g.fillOval(xStar[i], yStar[i], 3, 3);
				else
					g.fillRect(xStar[i], yStar[i], 1, 1);
					
			}
		
	}
	
	private void drawBackgroundGrass(Graphics g) {
		
		g.setColor(new Color(37, 214, 66).darker().darker());
		g.fillRect(0, 450, Game.WIDTH, Game.HEIGHT);
		
	}
	
	private void drawClouds(Graphics g) {
		
		for(int i = 0; i < clouds.size(); i++)
			clouds.get(i).drawObject(g);			

	}
	
	private void drawGrass(Graphics g) {
		
		for(int i = 0; i < grass.size(); i++)
			grass.get(i).drawObject(g);
		
	}
	
	private void drawMountains(Graphics g) {
		
		for(int i = 0; i < mountain.size(); i++)
			mountain.get(i).drawObject(g, 2);
		
	}
	
	private void drawObjects(Graphics g) {
		
		rock.drawObject(g);
		shopPlace.drawObject(g);
		
	}
	
	private void drawMoon(Graphics g) {
		moon.drawObject(g);
	}
	
	private void animateClouds() {
		
		holdCloudTime--;
		
		if(holdCloudTime == 0) {
			
			for(int i = 0; i < clouds.size(); i++) {
				
				Clouds tempObject = clouds.get(i);
				
				tempObject.setX(tempObject.getX() - 1);
				
				if(tempObject.getX() == 0)
					clouds.add(new Clouds(Game.WIDTH, tempObject.getY(), tempObject.getZ(), tempObject.getType(), Game.clouds));
				
				if(tempObject.getX() + tempObject.getWidth() <= 0)
					clouds.remove(i);
				
			}
				
			holdCloudTime = 3;
			
		}
		
	}
	
	private void animateGrass() {
		
		holdGrassTime--;
		
		if(holdGrassTime == 0 && !grassAnimation) {
			grassAnimation = true;
			
			for(int i = 0; i < grass.size(); i++)
				grass.get(i).setX(grass.get(i).getX() - 3);
		
			holdGrassTime = 25;
		}
	
		if(holdGrassTime == 0 && grassAnimation) {
			grassAnimation = false;
			
			for(int i = 0; i < grass.size(); i++)
				grass.get(i).setX(grass.get(i).getX() + 3);
			
			holdGrassTime = 25;
			
		}
		
	}
	
}
