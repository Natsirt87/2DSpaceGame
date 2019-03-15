
public class Alien extends Sprite{
	
	public int dy;
	public int dx;
	public int speed;
	public int health;
	
	public int laserDelay;
	public int laserDelayMax;
	
	public boolean movingLeft;
	public boolean movingRight;
	public boolean movingUp;
	public boolean movingDown;
	
	public boolean isDestroyed;
	public int explosionScale;
	
	public Alien(int x, int y) {
		super(x, y);
		name = "Alien";
		health = 100;
		speed = 1;
	}
	
	public void move() {
		x += dx * speed;
		y += dy * speed;
	}
	
	public void doDamage(int damage) {
		health -= damage;
	}
	
	public void destroyed() {
		System.out.println(name + " was destroyed");
		isDestroyed = true;
	}
}
