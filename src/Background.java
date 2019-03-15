
public class Background extends Sprite {
	
	private int dy = 3;
	
	public Background(int x, int y, int backgroundNum) {
		super(x, y);
		initBackground(backgroundNum);
	}
	
	private void initBackground(int num) {
		loadImage("sprites/Background/background" + num + ".jpg");
		setImageDimensions();
	}
	
	public void move() {
		y += dy;
	}
	
	public void setSpeed(int speed) {
		dy = speed;
	}
}
