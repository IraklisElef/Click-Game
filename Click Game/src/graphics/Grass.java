package graphics;
import java.awt.image.BufferedImage;

public class Grass extends GraphicsObject {
	
	public Grass(int x, int y, float z, int type, BufferedImage sprite_var) {
		
		super(x, y, z, type, sprite_var);
		
		switch(type) {
		
			case 1:
				sprite = ss.grabImage(0, 0, 32, 64);
				sprite = resize(sprite, (int) (32 * z), (int) (64 * z));
				break;
			case 2:
				sprite = ss.grabImage(40, 0, 24, 64);
				sprite = resize(sprite, (int) (24 * z), (int) (64 * z));
				break;
			case 3:
				sprite = ss.grabImage(72, 0, 32, 64);
				sprite = resize(sprite, (int) (32 * z), (int) (64 * z));
				break;
				
		}
		
		this.width = sprite.getWidth();
		
	}
	
}