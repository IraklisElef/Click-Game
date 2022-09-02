package graphics;

import java.awt.image.BufferedImage;

public class Mountain extends GraphicsObject {

	public Mountain(boolean animate, int x, int y, float z, int frames, BufferedImage sprite_var) {
		
		super(animate, x, y, z, frames, sprite_var);
		
		sprite = resize(sprite_var, (int) (sprite_var.getWidth() * z), (int) (sprite_var.getHeight() * z));
		
	}
	
}
