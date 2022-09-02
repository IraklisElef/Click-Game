package main;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import entities.Player;
import graphics.GameGraphics;
import graphics.HUD;
import imageLoader.BufferedImageLoader;

public class Game extends Canvas implements Runnable {
	
	private static final long serialVersionUID = 1L;

	private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	private Thread thread;
	private boolean running = false;
	
	public static final int WIDTH = (int) screenSize.getWidth(), HEIGHT = (int) screenSize.getHeight();
	public static final int finalScreenSize = WIDTH + HEIGHT;
	
	public static GameGraphics gameGraphics;
	public static Player player;
	public static HUD hud;
	
	public static BufferedImage clouds, grass, mountain, rock, moon, particles, materials, pickaxes, shop_place;
	
	public static BufferedImage playerSprite;
	
	public static STATE gameState = STATE.Game;
	
	public static enum STATE {
		
		Game,
		Stats,
		Levels,
		Shop,
		Menu;
		
	};
	
	public Game() {
		
		loadSprites();
		gameGraphics = new GameGraphics();
		player = new Player(Game.WIDTH / 2 - 100, (int) (Game.HEIGHT / 1.5), 0.5f, 3f, 1, "Hercules", playerSprite, particles);
		hud = new HUD(1f, 0.5f, materials, pickaxes);
		
		new Window("Click Game", this);
		addKeyListener(player);
		addMouseListener(player);
		addMouseMotionListener(player);
		
	}
	
	public synchronized void start() {
		
		thread = new Thread(this);
		thread.start();
		running = true;
		
	}
	
	public synchronized void stop() {
		
		try{
			thread.join();
			running = false;
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	public void run() {
		
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		
		while(running) {
			
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now; 
			
			while(delta >= 1) {
				tick();
				delta--;
			}
			
			if(running)
				render();
			
			if(System.currentTimeMillis() - timer > 1000)
				timer += 1000;
			
		}
		
		stop();
		
	}
	
	private void tick() {
		
		gameGraphics.tick();
		player.tick();
		hud.tick();
		
	}
	
	private void render() {
		
		BufferStrategy bs = this.getBufferStrategy();
		
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		gameGraphics.render(g);
		player.render(g);
		hud.render(g);
		
		g.dispose();
		bs.show();
		
	}
	
	private void loadSprites() {
		
		BufferedImageLoader loader = new BufferedImageLoader();
		
		clouds = loader.loadImage("/graphics/clouds.png");
		grass = loader.loadImage("/graphics/grass.png");
		mountain = loader.loadImage("/graphics/mountain.png");
		rock = loader.loadImage("/graphics/rock.png");
		moon = loader.loadImage("/graphics/moon.png");
		
		particles = loader.loadImage("/graphics/mine_particles.png");
		materials = loader.loadImage("/graphics/materials.png");
		pickaxes = loader.loadImage("/graphics/pickaxes.png");
		shop_place = loader.loadImage("/graphics/shop_place.png");
		
		playerSprite = loader.loadImage("/entities/player.png");
		
	}
	
	public static void main(String[] args) {
		
		new Game();
		
	}
	
}
