package graphics;

import java.awt.image.BufferedImage;

public class Clouds extends GraphicsObject {

	public Clouds(int x, int y, float z, int type, BufferedImage sprite_var) {
		
		super(x, y, z, type, sprite_var);
		
		switch(type) {
		
			case 1:
				sprite = ss.grabImage(0, 0, 152, 80);
				break;
			case 2:
				sprite = ss.grabImage(160, 0, 152, 80);
				break;
				
		}
		
		sprite = resize(sprite, (int) (152 * z), (int) (80 * z));
		
		this.width = sprite.getWidth();
		
	}
	
}