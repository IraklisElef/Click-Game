package graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import entities.Player;
import imageLoader.SpriteSheet;
import main.Game;
import main.Game.STATE;

public class HUD {
	
	private SpriteSheet ss;
	public BufferedImage[] materials = new BufferedImage[10];
	public BufferedImage[] pickaxes = new BufferedImage[10];
	
	public HUD(float materialSize, float pickaxeSize, BufferedImage materialsImage, BufferedImage pickaxesImage) {
		
		materialsImage = Player.resize(materialsImage, (int) (materialsImage.getWidth() * materialSize), (int) (materialsImage.getHeight() * materialSize));
		pickaxesImage = Player.resize(pickaxesImage, (int) (pickaxesImage.getWidth() * pickaxeSize), (int) (pickaxesImage.getHeight() * pickaxeSize));
		
		ss = new SpriteSheet(materialsImage);
		
		for(int i = 0, x = 0; i < materials.length; i++, x += 105)
			materials[i] = ss.grabImage((int) (x * materialSize), 0, (int) (105 * materialSize), (int) (95 * materialSize));
		
		ss = new SpriteSheet(pickaxesImage);
		
		for(int i = 0, x = 0; i < pickaxes.length; i++, x += 192)
			pickaxes[i] = ss.grabImage((int) (x * pickaxeSize), 0, (int) (192 * pickaxeSize), (int) (191 * pickaxeSize));
		
	}
	
	public void tick() {
		
		
	}
	
	public void render(Graphics g) {
		
		if(Game.gameState == STATE.Stats)
			drawStats(g);
		else if(Game.gameState == STATE.Levels)
			drawMaterials(g);
		else if(Game.gameState == STATE.Shop)
			drawShop(g);
		
	}
	
	private void drawStats(Graphics g) {
		
		g.setColor(new Color(0, 0, 0, 150));
		g.fillRect(Game.WIDTH / 2 - 300, Game.HEIGHT / 2 - 300, 600, 600);
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", 1, 50));
		g.drawString("Statistics", Game.WIDTH / 2 - g.getFontMetrics().stringWidth("Statistics") / 2, Game.HEIGHT / 2 - 250);
		
		g.setFont(new Font("Arial", 1, 35));
		g.drawString("General", Game.WIDTH / 2 - 280, Game.HEIGHT / 2 - 200);
		
		g.setFont(new Font("Arial", 1, 20));
		g.drawString("Player: " + Game.player.getName(), Game.WIDTH / 2 - 265, Game.HEIGHT / 2 - 170);
		g.drawString("Level: " + (Game.player.getLevel() + 1), Game.WIDTH / 2 - 265, Game.HEIGHT / 2 - 145);
		g.drawString("Total Pickaxes: " + Game.player.getPickaxes(true, 0), Game.WIDTH / 2 - 265, Game.HEIGHT / 2 - 120);
		g.drawString("Total Clicks: " + Game.player.getTotalClicks(), Game.WIDTH / 2 - 265, Game.HEIGHT / 2 - 95);
		g.drawString("Total Damage: " + Game.player.getTotalDamage(), Game.WIDTH / 2 - 265, Game.HEIGHT / 2 - 70);
		
		g.setFont(new Font("Arial", 1, 35));
		g.drawString("Materials Mined", Game.WIDTH / 2 - 280, Game.HEIGHT / 2 - 10);
		g.drawString("Pickaxes Damage", Game.WIDTH / 2, Game.HEIGHT / 2 - 10);
		
		g.setFont(new Font("Arial", 1, 20));
		g.drawString("Total Materials: " + Game.player.getMaterials(true, 0), Game.WIDTH / 2 - 267, Game.HEIGHT / 2 + 20);
		g.drawString("Total Pickaxes Damage: " + Game.player.getTotalPickaxesDamage(), Game.WIDTH / 2 + 13, Game.HEIGHT / 2 + 20);
		
		for(int i = 0, y = 45; i < Game.gameGraphics.rock.name.length; i++, y += 25)
			g.drawString(Game.gameGraphics.rock.name[i] + ": " + Game.player.getMaterials(false, i), Game.WIDTH / 2 - 265, Game.HEIGHT / 2 + y);
		
		for(int i = 0, y = 45; i < Game.gameGraphics.rock.name.length; i++, y += 25)
			g.drawString(Game.gameGraphics.rock.name[i] + ": " + Game.player.getPickaxeDamage(i) + " (" + Game.player.getPickaxes(false, i) + ")", Game.WIDTH / 2 + 15, Game.HEIGHT / 2 + y);
		
	}
	
	private void drawMaterials(Graphics g) {
		
		Color backgroundColor = new Color(0, 0, 0, 150);
		
		g.setColor(backgroundColor);
		g.fillRect(Game.WIDTH / 2 - 300, Game.HEIGHT / 2 - 300, 600, 600);
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", 1, 50));
		g.drawString("Materials", Game.WIDTH / 2 - g.getFontMetrics().stringWidth("Materials") / 2, Game.HEIGHT / 2 - 250);
	
		g.setFont(new Font("Arial", 1, 15));
		g.drawString("Select a material to mine", Game.WIDTH / 2 - g.getFontMetrics().stringWidth("Select a material to mine") / 2, Game.HEIGHT / 2 - 230);
		
		g.setFont(new Font("Arial", 1, 20));
		
		//Top material names
		for(int i = 0, x = 285; i < 5; i++, x -= 115)
			g.drawString(Game.gameGraphics.rock.name[i], ((Game.WIDTH / 2 - x) + (materials[1].getWidth() / 2 - g.getFontMetrics().stringWidth(Game.gameGraphics.rock.name[i]) / 2)), Game.HEIGHT / 2 - 185);
		
		//Bottom material names
		for(int i = 5, x = 285; i < 10; i++, x -= 115)
			g.drawString(Game.gameGraphics.rock.name[i], ((Game.WIDTH / 2 - x) + (materials[1].getWidth() / 2 - g.getFontMetrics().stringWidth(Game.gameGraphics.rock.name[i]) / 2)), Game.HEIGHT / 2 + 50);
			
		//Top material pictures
		for(int i = 0, x = 285; i < 5; i++, x -= 115)
			g.drawImage(materials[i], Game.WIDTH / 2 - x, Game.HEIGHT / 2 - 170, null);

		//Bottom material pictures
		for(int i = 5, x = 285; i < 10; i++, x -= 115)
			g.drawImage(materials[i], Game.WIDTH / 2 - x, Game.HEIGHT / 2 + 65, null);
		
		//Top if its locked material
		for(int i = 1, x = 170; i < 5; i++, x -= 115) {
			
			if(Game.player.isLocked(i)) {
					
				g.setColor(backgroundColor);
				g.fillRect(Game.WIDTH / 2 - x, Game.HEIGHT / 2 - 170, materials[1].getWidth(), materials[1].getHeight());
				g.setColor(Color.RED);
				g.drawString("Locked", Game.WIDTH / 2 - (x - 18), Game.HEIGHT / 2 - 115);
				g.setColor(Color.WHITE);
				g.drawString("Mine " + Game.player.rockHealth[i - 1], ((Game.WIDTH / 2 - x) + (materials[1].getWidth() / 2 - g.getFontMetrics().stringWidth("Mine" + Game.player.rockHealth[i - 1]) / 2)), Game.HEIGHT / 2 - 50);
				g.drawString(Game.gameGraphics.rock.name[i - 1], ((Game.WIDTH / 2 - x) + (materials[1].getWidth() / 2 - g.getFontMetrics().stringWidth(Game.gameGraphics.rock.name[i - 1]) / 2)), Game.HEIGHT / 2 - 27);
				
				g.fillRect(Game.WIDTH / 2 - (x - 4), Game.HEIGHT / 2 - 20, (int) (Game.player.rockHealth[i - 1] * 100 / Game.player.originalRockHealth[i - 1]), 10);
				
			}
			
		}
		
		//Bottom if its locked material
		for(int i = 5, x = 285; i < 10; i++, x -= 115) {
			
			if(Game.player.isLocked(i)) {
				
				int textWidth1 = g.getFontMetrics().stringWidth("Mine" + Game.player.rockHealth[i - 1]);
				int textWidth2 = g.getFontMetrics().stringWidth(Game.gameGraphics.rock.name[i - 1]);
				
				g.setColor(backgroundColor);
				g.fillRect(Game.WIDTH / 2 - x, Game.HEIGHT / 2 + 65, materials[1].getWidth(), materials[1].getHeight());
				g.setColor(Color.RED);
				g.drawString("Locked", Game.WIDTH / 2 - (x - 18), Game.HEIGHT / 2 + 120);
				g.setColor(Color.WHITE);
				g.drawString("Mine " + Game.player.rockHealth[i - 1], ((Game.WIDTH / 2 - x) + (materials[1].getWidth() / 2 - textWidth1 / 2)), Game.HEIGHT / 2 + 185);
				g.drawString(Game.gameGraphics.rock.name[i - 1], ((Game.WIDTH / 2 - x) + (materials[1].getWidth() / 2 - textWidth2 / 2)), Game.HEIGHT / 2 + 208);
				
				g.fillRect(Game.WIDTH / 2 - (x - 4), Game.HEIGHT / 2 + 215, (int) (Game.player.rockHealth[i - 1] * 100 / Game.player.originalRockHealth[i - 1]), 10);
				
			}
			
		}
		
		//Hover border top
		for(int i = 0, x = 285; i < 5; i++, x -= 115) {
			
			if(i == 0) {
				
				if(Game.player.isOnMaterial[i])
					g.drawRect(Game.WIDTH / 2 - x - 1, Game.HEIGHT / 2 - 170 - 1, materials[1].getWidth() + 1, materials[1].getHeight() + 1);
				
			}
			else if(i > 0 && !Game.player.isLocked(i)) {
				
				if(Game.player.isOnMaterial[i])
					g.drawRect(Game.WIDTH / 2 - x - 1, Game.HEIGHT / 2 - 170 - 1, materials[1].getWidth() + 1, materials[1].getHeight() + 1);
				
			}
			
		}
		
		//Hover border bottom
		for(int i = 5, x = 285; i < 10; i++, x -= 115) {
			
			if(i > 0 && !Game.player.isLocked(i)) {
				
				if(Game.player.isOnMaterial[i])
					g.drawRect(Game.WIDTH / 2 - x - 1, Game.HEIGHT / 2 + 65 - 1, materials[1].getWidth() + 1, materials[1].getHeight() + 1);
				
			}
			
		}	
		
	}
	
	private void drawShop(Graphics g) {
		
		Color backgroundColor = new Color(0, 0, 0, 150);
		
		g.setColor(backgroundColor);
		g.fillRect(Game.WIDTH / 2 - 300, Game.HEIGHT / 2 - 300, 600, 600);
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", 1, 50));
		g.drawString("Shop", Game.WIDTH / 2 - g.getFontMetrics().stringWidth("Shop") / 2, Game.HEIGHT / 2 - 250);
	
		g.setFont(new Font("Arial", 1, 15));
		g.drawString("Buy/Upgrade a pickaxe", Game.WIDTH / 2 - g.getFontMetrics().stringWidth("Buy/Upgrade a pickaxe") / 2, Game.HEIGHT / 2 - 225);
		
		g.setFont(new Font("Arial", 1, 20));
		
		//Top pickaxe pictures
		for(int i = 0, x = 275; i < 5; i++, x -= 115)
			g.drawImage(pickaxes[i], Game.WIDTH / 2 - x, Game.HEIGHT / 2 - 170, null);
		
		//Bottom pickaxe pictures
		for(int i = 5, x = 275; i < 10; i++, x -= 115) {
			g.drawImage(pickaxes[i], Game.WIDTH / 2 - x, Game.HEIGHT / 2 + 65, null);
			
		}
			
		for(int i = 0, x = 275; i < 5; i++, x -= 115) {
			
			g.drawString(Game.gameGraphics.rock.name[i], Game.WIDTH / 2 - x + (pickaxes[i].getWidth() / 2 - g.getFontMetrics().stringWidth(Game.gameGraphics.rock.name[i]) / 2), Game.HEIGHT / 2 - 185);
			g.drawRect(Game.WIDTH / 2 - x + (pickaxes[i].getWidth() / 2 - 45), Game.HEIGHT / 2 - 60, 90, 35);
			
			if(Game.player.getPickaxes(false, i) == "Owned") {
				
				g.setColor(Color.GREEN);
				g.drawString("Owned", (Game.WIDTH / 2 - x) + (pickaxes[i].getWidth() / 2 - g.getFontMetrics().stringWidth("Owned") / 2), Game.HEIGHT / 2 - 115);
				g.setColor(Color.WHITE);
				g.drawString("Upgrade", (Game.WIDTH / 2 - x) + (pickaxes[i].getWidth() / 2 - g.getFontMetrics().stringWidth("Upgrade") / 2), Game.HEIGHT / 2 - 37);
				
			}
				
			else {
				
				g.setColor(backgroundColor);
				g.fillRect(Game.WIDTH / 2 - x, Game.HEIGHT / 2 - 170, pickaxes[i].getWidth(), pickaxes[i].getHeight());
				
				g.setColor(Color.RED);
				g.drawString("Locked", (Game.WIDTH / 2 - x) + (pickaxes[i].getWidth() / 2 - g.getFontMetrics().stringWidth("Locked") / 2), Game.HEIGHT / 2 - 115);
				g.setColor(Color.WHITE);
				g.drawString("Buy",(Game.WIDTH / 2 - x) + (pickaxes[i].getWidth() / 2 - g.getFontMetrics().stringWidth("Buy") / 2), Game.HEIGHT / 2 - 37);

			}
			
		}
		
		for(int i = 5, x = 275; i < 10; i++, x -= 115) {
			
			g.drawString(Game.gameGraphics.rock.name[i], Game.WIDTH / 2 - x + (pickaxes[i].getWidth() / 2 - g.getFontMetrics().stringWidth(Game.gameGraphics.rock.name[i]) / 2), Game.HEIGHT / 2 + 50);
			g.drawRect(Game.WIDTH / 2 - x + (pickaxes[i].getWidth() / 2 - 45), Game.HEIGHT / 2 + 175, 90, 35);
			
			if(Game.player.getPickaxes(false, i) == "Owned") {
				
				g.setColor(Color.GREEN);
				g.drawString("Owned", (Game.WIDTH / 2 - x) + (pickaxes[i].getWidth() / 2 - g.getFontMetrics().stringWidth("Owned") / 2), Game.HEIGHT / 2 + 120);
				g.setColor(Color.WHITE);
				g.drawString("Upgrade", (Game.WIDTH / 2 - x) + (pickaxes[i].getWidth() / 2 - g.getFontMetrics().stringWidth("Upgrade") / 2), Game.HEIGHT / 2 + 198);
				
			}
				
			else {
				
				g.setColor(backgroundColor);
				g.fillRect(Game.WIDTH / 2 - x, Game.HEIGHT / 2 + 65, pickaxes[i].getWidth(), pickaxes[i].getHeight());
				
				g.setColor(Color.RED);
				g.drawString("Locked", (Game.WIDTH / 2 - x) + (pickaxes[i].getWidth() / 2 - g.getFontMetrics().stringWidth("Locked") / 2), Game.HEIGHT / 2 + 120);
				g.setColor(Color.WHITE);
				g.drawString("Buy",(Game.WIDTH / 2 - x) + (pickaxes[i].getWidth() / 2 - g.getFontMetrics().stringWidth("Buy") / 2), Game.HEIGHT / 2 + 198);

			}
			
		}
			
	}
	
}
