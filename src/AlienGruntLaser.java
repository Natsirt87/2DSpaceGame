
import java.awt.Rectangle;

public class AlienGruntLaser extends AlienProjectile{
	
	public AlienGruntLaser(int x, int y) {
		super(x, y);
		initAlienLaser();
	}
	
	private void initAlienLaser() {
		name = "AlienProjectile";
		
		loadImage("sprites/Aliens/AlienGrunt/alienGruntLaser.png");
		setImageDimensions();
		
		dy = 8;
	}
	
	@Override
	public Rectangle getBounds() {
		Rectangle rect = new Rectangle(x + 8, y, w - 16, h);
		return rect;
	}

}
