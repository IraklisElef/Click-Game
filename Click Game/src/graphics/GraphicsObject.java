package graphics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import imageLoader.SpriteSheet;

public abstract class GraphicsObject {

	public int x, y, type, width, height;
	public int delay = 50;
	public float z;
	public boolean animate;
	
	public BufferedImage sprite;
	public BufferedImage[] spriteAnimation;
	public SpriteSheet ss;

	public GraphicsObject(boolean animate, int x, int y, float z, int frames, BufferedImage sprite_var) {
		
		this.animate = animate;
		this.x = x;
		this.y = y;
		this.z = z;
		this.spriteAnimation = new BufferedImage[frames];
		this.ss = new SpriteSheet(sprite_var);
			
		int entityWidth = sprite_var.getWidth() / 2;
		int entityHeight = sprite_var.getHeight();
		
		int x1 = 0;
			for(int i = 0; i < this.spriteAnimation.length; i++) {
						
				this.spriteAnimation[i] = this.ss.grabImage(x1, 0, entityWidth, entityHeight);
				this.spriteAnimation[i] = resize(this.spriteAnimation[i], (int) (entityWidth * z), (int) (entityHeight * z));
				x1 += entityWidth;
					
			}
		
		}
	
	public GraphicsObject(int x, int y, float z, int type, BufferedImage sprite_var) {
		
		this.x = x;
		this.y = y;
		this.z = z;
		this.type = type;
		this.ss = new SpriteSheet(sprite_var);
		
	}
	
	public static BufferedImage resize(BufferedImage image, int newW, int newH) { 
		
		int type = 0;
        type = image.getType() == 0? BufferedImage.TYPE_INT_ARGB : image.getType();
        BufferedImage resizedImage = new BufferedImage(newW, newH, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(image, 0, 0, newW, newH, null);
        g.dispose();
        
        return resizedImage;
	    
	}
	
	public void drawObject(Graphics g) {
		g.drawImage(sprite, x, y, null);
	}
	
	public void drawObject(Graphics g, int frames) {
		
		if(frames == 2) {
			
			if(animate) {
				
				if(delay > 25)
					g.drawImage(spriteAnimation[0], x, y, null);
				else if(delay > 0)
					g.drawImage(spriteAnimation[1], x, y, null);
				
				if(delay <= 1)
					delay = 50;
				
			}
			else
				g.drawImage(spriteAnimation[0], x, y, null);
			
		}
		
		
	}
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}
	
	public int getHeight() {
		return height;
	}

	public void setHeighth(int height) {
		this.height = height;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}
	
}
