
import java.awt.Rectangle;

public class Laser extends Sprite{
	
	private int dy = -12;
	
	public Laser(int x, int y) {
		super(x, y);
		initLaser();
	}
	
	private void initLaser() {
		name = "Laser";
		
		loadImage("sprites/Player/Projectiles/laser2.png");
		setImageDimensions();
	}
	
	public void move (){
		y += dy;
	}
	
	@Override
	public Rectangle getBounds() {
		Rectangle rect = new Rectangle(x + 7, y + 6, w - 14, h - 12);
		return rect;
	}
}
