package graphics;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import imageLoader.SpriteSheet;
import main.Game;

public class Rock extends GraphicsObject {

	public String[] name = new String[10];
	
	BufferedImage[] rocks = new BufferedImage[10];
	
	public Rock(int x, int y, float z, int type, BufferedImage sprite_var) {
		super(x, y, z, type, sprite_var);
		
		sprite_var = resize(sprite_var, (int) (sprite_var.getWidth() * z), (int) (sprite_var.getHeight() * z));
		
		this.ss = new SpriteSheet(sprite_var);
		
		int temp = 0;
		
		for(int i = 0; i < rocks.length; i++) {
			
			rocks[i] = ss.grabImage((int) (temp * z), 0, (int) (94 * z), (int) (105 * z));
			temp += 94;
			
		}
		
		name[0] = "Dirt";
		name[1] = "Hard Rock";
		name[2]	= "Silver";
		name[3] = "Gold";
		name[4] = "Platinum";
		name[5] = "Emerald";
		name[6] = "Diamond";
		name[7] = "Plutonium";
		name[8] = "Painite";
		name[9] = "Antimatter";
		
		sprite = resize(sprite_var, (int) (sprite_var.getWidth() * z), (int) (sprite_var.getHeight() * z));
		
	}
	
	public void drawObject(Graphics g) {
		
		g.drawImage(rocks[Game.player.getLevel()], x, y, null);
		
	}

}
