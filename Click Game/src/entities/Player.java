package entities;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.SwingUtilities;

import imageLoader.SpriteSheet;
import main.Game;
import main.Game.STATE;

public class Player extends KeyAdapter implements MouseListener, MouseMotionListener {

	//Statistics
	private int totalClicks = 0;
	private int totalDamage = 0;
	private int level = 0;
	private int pickaxeLevel = 0;
	
	private boolean[] totalPickaxes = new boolean[10];
	private int[] totalMaterials = new int[10];
	private int[] shopMaterials = new int[10];
	private int[] pickaxeDamage = new int[10];
	
	//0 = right, 1 = left, 2 = up, 3 = down
	private boolean[] direction = new boolean[4];
	private boolean[] walking = new boolean[4];
	private int[] walkDelay = new int[4];
	private int[] originalWalkDelay = new int[4];
	
	//Mining
	private boolean mine = false;
	private int mineDelay = 5;
	
	//Player position
	private int x, y;
	
	public boolean[] isOnMaterial = new boolean[10];

	private String name;
	private BufferedImage[][] player = new BufferedImage[10][11];
	private BufferedImage[] particles = new BufferedImage[10];
	private SpriteSheet ss;
	
	public int[] rockHealth = new int[10];
	public int[] originalRockHealth = new int[10];

	public Player(int x, int y, float zPlayer, float zParticles, int pickaxeDamage, String name, BufferedImage sprite, BufferedImage particles) {
		
		sprite = resize(sprite, (int) (sprite.getWidth() * zPlayer), (int) (sprite.getHeight() * zPlayer));
		particles = resize(particles, (int) (particles.getWidth() * zParticles), (int) (particles.getHeight() * zParticles));
		
		this.x = x;
		this.y = y;
		this.pickaxeDamage[0] = pickaxeDamage;
		this.name = name;
		this.ss = new SpriteSheet(particles);
		
		int tempX = 0;
		
		for(int i = 0; i < this.particles.length; i++) {
			
			this.particles[i] = this.ss.grabImage((int) (tempX * zParticles), 0, (int) (22 * zParticles), (int) (34 * zParticles));
			tempX += 22;
			
		}
		
		this.ss = new SpriteSheet(sprite);
		
		tempX = 0;
		int tempY = 0;
		
		for(int i = 0; i < 10; i++) {
			
			tempX = 0;
			
			for(int j = 0; j < 10; j++) {
				
				this.player[i][j] = this.ss.grabImage((int) (tempX * zPlayer), (int) (tempY * zPlayer), (int) (367 * zPlayer), (int) (385 * zPlayer));
				tempX += 367;
				
			}
			
			tempY += 385;
			
		}
		
		tempY = 0;
		for(int i = 0; i < 10; i++) {
			
			this.player[i][10] = this.ss.grabImage((int) (tempX * zPlayer), (int) (tempY * zPlayer), (int) (380 * zPlayer), (int) (385 * zPlayer));
			tempY += 385;
			
		}
			
		direction[0] = true;
		
		for(int i = 0; i < walkDelay.length; i++)
			walkDelay[i] = originalWalkDelay[i] = 20;
		
		rockHealth[0] = 100;
		rockHealth[1] = 500;
		rockHealth[2] = 1000;
		rockHealth[3] = 2000;
		rockHealth[4] = 5000;
		rockHealth[5] = 10000;
		rockHealth[6] = 20000;
		rockHealth[7] = 40000;
		rockHealth[8] = 80000;
		rockHealth[9] = 100000;
		
		for(int i = 0; i < rockHealth.length; i++)
			originalRockHealth[i] = rockHealth[i];
		
		totalPickaxes[0] = true;
		
	}
	
	public boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
		
		if(mx > x && mx < x + width) {
			 if(my > y && my < y + height)
				 return true;
			 
			 return false;
		}
		
		return false;
		
	}
	
	//Clamping method
	public static int clamp(int var, int min, int max) {
		
		if(var <= min)
			return var = min;
		else if(var >= max)
			return var = max;
		else
			return var;
		
	}
	
	//Resize images
	public static BufferedImage resize(BufferedImage image, int newW, int newH) { 
		
		int type = 0;
        type = image.getType() == 0? BufferedImage.TYPE_INT_ARGB : image.getType();
        BufferedImage resizedImage = new BufferedImage(newW, newH, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(image, 0, 0, newW, newH, null);
        g.dispose();
        
        return resizedImage;
	    
	}
	
	//Change the direction variable that player faces
	private void changeDirection(int direction) {
		
		for(int i = 0; i < this.direction.length; i++) {
			
			if(i == direction)
				this.direction[i] = true;
			else
				this.direction[i] = false;
			
		}
		
	}
	
	private boolean mineReady() {
		
		if((!walking[0] && !walking[1] && !walking[2] && !walking[3]) && direction[0] && x == Game.WIDTH - 94 * 6)
			return true;
		
		return false;
			
	}
	
	private boolean shopReady() {
		
		if((x + player[0][0].getWidth() / 2 >= 100 && x + player[0][0].getWidth() / 2 <= 300) && (y + player[0][0].getHeight() >= 510 && y + player[0][0].getHeight() <= 710))
			return true;
		
		return false;
		
	}
	
	//General variable changing when player moves
	private void movingSettings(int direction) {
		
		walking[direction] = true;
		mine = false;
		mineDelay = 5;
		changeDirection(direction);
		
	}
	
	//Tick player
	public void tick() {
	
		x = clamp(x, 0, Game.WIDTH - 94 *  6);
		y = clamp(y, 450, Game.HEIGHT - player[0][0].getHeight());

		if(mine)
			mineDelay--;
		
		for(int i = 0; i < walking.length; i++) {
			
			if(walking[i]) {
				
				walkDelay[i]--;
				
				switch(i) {
				
					case 0: //Right
						x += 4;
						break;
					case 1: //Left
						x -= 4;
						break;
					case 2: //Up
						y -= 4;
						break;
					case 3: //Down
						y += 4;
						break;
					
				}
				
			}
				
		}
		
	}
	
	//Render player
	public void render(Graphics g) {
		
		if(mineReady()) {
			
			g.setFont(new Font("Arial", 1, 30));
			g.setColor(Color.WHITE);
			g.drawString("MINE!", x + player[0][10].getWidth() / 8, y - 20);
			
		}
		
		if(shopReady()) {
			
			g.setFont(new Font("Arial", 1, 30));
			g.setColor(Color.WHITE);
			g.drawString("SHOP!",  150, 430);
			
		}
		
		//Going left
		if(direction[0]) {
			
			if(!mine) {
				
				if(walkDelay[0] > 10)
					g.drawImage(this.player[pickaxeLevel][0], x, y, null);
				else if(walkDelay[0] > 0)
					g.drawImage(this.player[pickaxeLevel][1], x, y, null);
					
				if(walkDelay[0] <= 1)
					walkDelay[0] = originalWalkDelay[0];
				
			}
			else if((!walking[0] && !walking[1] && !walking[2] && !walking[3]) && mine) {
				
				if(mineDelay > 0) {
					
					g.drawImage(this.particles[level], x + 150, y + 80 , null); //Draw particles
					g.drawImage(this.player[pickaxeLevel][10], x, y, null); //Draw mining
					
				}
				
				if(mineDelay <= 1) {
					
					mineDelay = 5;
					mine = false;
					
				}
					
				
			}
				
			
		}
		//Going right
		else if(direction[1]) {
			
			if(walkDelay[1] > 10)
				g.drawImage(this.player[pickaxeLevel][2], x, y, null);
			else if(walkDelay[1] > 0)
				g.drawImage(this.player[pickaxeLevel][3], x, y, null);
				
			if(walkDelay[1] <= 1)
				walkDelay[1] = originalWalkDelay[1];
			
		}
		//Going up
		else if(direction[2]) {
			
			if(walking[2]) {
				
				if(walkDelay[2] > 10)
					g.drawImage(this.player[pickaxeLevel][5], x, y, null);
				else if(walkDelay[2] > 0)
					g.drawImage(this.player[pickaxeLevel][6], x, y, null);
					
				if(walkDelay[2] <= 1)
					walkDelay[2] = originalWalkDelay[2];
				
			}
			else
				g.drawImage(this.player[pickaxeLevel][4], x, y, null);
				
			
		}
		//Going down
		else if(direction[3]) {
			
			if(walking[3]) {
				
				if(walkDelay[3] > 10)
					g.drawImage(this.player[pickaxeLevel][8], x, y, null);
				else if(walkDelay[3] > 0)
					g.drawImage(this.player[pickaxeLevel][9], x, y, null);
					
				if(walkDelay[3] <= 1)
					walkDelay[3] = originalWalkDelay[3];
				
			}
			else
				g.drawImage(this.player[pickaxeLevel][7], x, y, null);
				
			
		}
		
			
	}
	
	public void keyPressed(KeyEvent e) {
		
		int key = e.getKeyCode();
		
		if(Game.gameState == STATE.Game || Game.gameState == STATE.Stats || Game.gameState == STATE.Levels || Game.gameState == STATE.Shop) {
			
			switch(key) {
			
				case KeyEvent.VK_ESCAPE:
					System.exit(1);
					break;
				
				case KeyEvent.VK_SPACE:
					if(shopReady()) {
						
						if(Game.gameState == STATE.Game) {
							for(int i = 0; i < walking.length; i++) {
								walking[i] = false;
								walkDelay[i] = originalWalkDelay[i];
							}
							Game.gameState = STATE.Shop;
						}
						else if(Game.gameState == STATE.Shop)
							Game.gameState = STATE.Game;
						
					}
					break;
				
				case KeyEvent.VK_D:
					if(!(Game.gameState == STATE.Shop))
						movingSettings(0);
					break;
	
				case KeyEvent.VK_A:
					if(!(Game.gameState == STATE.Shop))
						movingSettings(1);
					break;
					
				case KeyEvent.VK_W:
					if(!(Game.gameState == STATE.Shop))
						movingSettings(2);
					break;
					
				case KeyEvent.VK_S:
					if(!(Game.gameState == STATE.Shop))
						movingSettings(3);
					break;
					
			}
			
		}
		
		
	}
	
	public void keyReleased(KeyEvent e) {
		
		int key = e.getKeyCode();
		
		if(Game.gameState == STATE.Game || Game.gameState == STATE.Stats || Game.gameState == STATE.Levels) {
		
			switch(key) {
		
				case KeyEvent.VK_D:
					if(!(Game.gameState == STATE.Shop)) {
						
						walking[0] = false;
						walkDelay[0] = originalWalkDelay[0];
						
					}
					
					break;
					
				case KeyEvent.VK_A:
					if(!(Game.gameState == STATE.Shop)) {
						
						walking[1] = false;
						walkDelay[1] = originalWalkDelay[1];
						
					}
					
					break;
					
				case KeyEvent.VK_W:
					if(!(Game.gameState == STATE.Shop)) {
						
						walking[2] = false;
						walkDelay[2] = originalWalkDelay[2];
						
					}
					
					break;
					
				case KeyEvent.VK_S:
					if(!(Game.gameState == STATE.Shop)) {
						
						walking[3] = false;
						walkDelay[3] = originalWalkDelay[3];
						
					}
					
					break;
				
			}
		
		}
		
	}

	public void mouseClicked(MouseEvent e) {
		
		int mx = e.getX();
		int my = e.getY();
		
		if(SwingUtilities.isMiddleMouseButton(e)) {
			
			if(Game.gameState == STATE.Game || Game.gameState == STATE.Levels) {
				
				if(Game.gameState == STATE.Game)
					Game.gameState = STATE.Levels;
				else if(Game.gameState == STATE.Levels)
					Game.gameState = STATE.Game;
				
			}
				
			
		}
		
		if(SwingUtilities.isLeftMouseButton(e)) {
			
			if(mineReady()) {
				
				for(int i = 0; i < totalMaterials.length; i++) {
					
					if(level == i) {
						
						totalMaterials[i]++;
						shopMaterials[i]++;
						
					}
						
					
				}
				
				mine = true;
				totalClicks++;
				
				totalDamage += pickaxeDamage[pickaxeLevel];
				rockHealth[level] -= pickaxeDamage[pickaxeLevel];
				
			}	
			
			if(Game.gameState == STATE.Levels) {
				
				//Enable Dirt
				if(mouseOver(mx, my, Game.WIDTH / 2 - 285, Game.HEIGHT / 2 - 170, Game.hud.materials[1].getWidth(), Game.hud.materials[1].getHeight()))
					level = 0;
				
				//Enable Hard Rock
				else if(mouseOver(mx, my, Game.WIDTH / 2 - 170, Game.HEIGHT / 2 - 170, Game.hud.materials[1].getWidth(), Game.hud.materials[1].getHeight())) {
					
					if(!isLocked(1))
						level = 1;
					
				}
				
				//Enable Silver
				else if(mouseOver(mx, my, Game.WIDTH / 2 - 55, Game.HEIGHT / 2 - 170, Game.hud.materials[1].getWidth(), Game.hud.materials[1].getHeight())) {
					
					if(!isLocked(2))
						level = 2;
					
				}
				
				//Enable Gold
				else if(mouseOver(mx, my, Game.WIDTH / 2 + 60, Game.HEIGHT / 2 - 170, Game.hud.materials[1].getWidth(), Game.hud.materials[1].getHeight())) {
					
					if(!isLocked(3))
						level = 3;
					
				}
				
				//Enable Platinum
				else if(mouseOver(mx, my, Game.WIDTH / 2 + 175, Game.HEIGHT / 2 - 170, Game.hud.materials[1].getWidth(), Game.hud.materials[1].getHeight())) {
					
					if(!isLocked(4))
						level = 4;
					
				}

				//Enable Emerald
				else if(mouseOver(mx, my, Game.WIDTH / 2 - 285, Game.HEIGHT / 2 + 65, Game.hud.materials[1].getWidth(), Game.hud.materials[1].getHeight())) {
					
					if(!isLocked(5))
						level = 5;
				
				}	
					
				//Enable Diamond
				else if(mouseOver(mx, my, Game.WIDTH / 2 - 170, Game.HEIGHT / 2 + 65, Game.hud.materials[1].getWidth(), Game.hud.materials[1].getHeight())) {
					
					if(!isLocked(6))
						level = 6;
					
				}
				
				//Enable Plutonium
				else if(mouseOver(mx, my, Game.WIDTH / 2 - 55, Game.HEIGHT / 2 + 65, Game.hud.materials[1].getWidth(), Game.hud.materials[1].getHeight())) {
					
					if(!isLocked(7))
						level = 7;
					
				}
				
				//Enable Painite
				else if(mouseOver(mx, my, Game.WIDTH / 2 + 60, Game.HEIGHT / 2 + 65, Game.hud.materials[1].getWidth(), Game.hud.materials[1].getHeight())) {
					
					if(!isLocked(8))
						level = 8;
					
				}
				
				//Enable Antimatter
				else if(mouseOver(mx, my, Game.WIDTH / 2 + 175, Game.HEIGHT / 2 + 65, Game.hud.materials[1].getWidth(), Game.hud.materials[1].getHeight())) {
					
					if(!isLocked(9))
						level = 9;
					
				}
			}
				 
		}
		else if(SwingUtilities.isRightMouseButton(e) && (Game.gameState == STATE.Game || Game.gameState == STATE.Stats)) {
			
			if(Game.gameState == STATE.Game)
				Game.gameState = STATE.Stats;
			else if(Game.gameState == STATE.Stats)
				Game.gameState = STATE.Game;
			
		}
		
	}
	
	public void mouseReleased(MouseEvent e) {

		mine = false;
		
	}
	
	public void mouseMoved(MouseEvent e) {

		int mx = e.getX();
	    int my = e.getY();
	    
	    for(int i = 0, x = 285; i < 5; i++, x -= 115)
	    	isOnMaterial[i] = mouseOver(mx, my, Game.WIDTH / 2 - x, Game.HEIGHT / 2 - 170, Game.hud.materials[1].getWidth(), Game.hud.materials[1].getHeight());
	    
	    for(int i = 5, x = 285; i < 10; i++, x -= 115)
	    	isOnMaterial[i] = mouseOver(mx, my, Game.WIDTH / 2 - x, Game.HEIGHT / 2 + 65, Game.hud.materials[1].getWidth(), Game.hud.materials[1].getHeight());
		
	}
	
	public void mouseDragged(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPickaxeDamage(int pickaxe) {
		return pickaxeDamage[pickaxe];
	}

	public void setPickaxeDamage(int pickaxeDamage, int pickaxe) {
		this.pickaxeDamage[pickaxe] = pickaxeDamage;
	}
	
	public int getTotalPickaxesDamage() {
		
		int sum = 0;
		
		for(int i = 0; i < pickaxeDamage.length; i++)
			sum += pickaxeDamage[i];
		
		return sum;
		
	}

	public int getTotalClicks() {
		return totalClicks;
	}

	public void setTotalClicks(int totalClicks) {
		this.totalClicks = totalClicks;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getTotalDamage() {
		return totalDamage;
	}

	public void setTotalDamage(int totalDamage) {
		this.totalDamage = totalDamage;
	}
	
	public int getMaterials(boolean all, int material) {
		
		int sum = 0;
		
		if(all) {
			
			for(int i = 0; i < totalMaterials.length; i++)
				sum += totalMaterials[i];
			
		}
		else
			sum = totalMaterials[material];
		
		return sum;
		
	}
	
	public String getPickaxes(boolean all, int pickaxe) {
		
		int sum = 0;
		
		if(all) {
			
			for(int i = 0; i < totalPickaxes.length; i++) {
				
				if(totalPickaxes[i])
					sum++;
				
			}
			
			return Integer.toString(sum);
			
		}
		else {
			
			if(totalPickaxes[pickaxe])
				return "Owned";
			else
				return "Locked";
			
		}
			
	}
	
	public boolean isLocked(int material) {
		
		if(rockHealth[material - 1] > 0)
			return true;
		
		return false;
		
	}
	
}
