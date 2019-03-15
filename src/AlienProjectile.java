
public class AlienProjectile extends Sprite{
	
	protected int dy;
	
	public AlienProjectile(int x, int y) {
		super(x, y);
	}
	
	
	public void move() {
		y += dy;
	}

	
}
