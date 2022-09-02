package graphics;

import java.awt.image.BufferedImage;

public class ShopPlace extends GraphicsObject {

	public ShopPlace(int x, int y, float z, int type, BufferedImage sprite_var) {
		super(x, y, z, type, sprite_var);
		
		sprite = resize(sprite_var, (int) (sprite_var.getWidth() * z), (int) (sprite_var.getHeight() * z));
	}

}
