import java.util.ArrayList;

public class AlienGrunt extends Alien{

	private ArrayList<String> explosionAnimSmall;
	private boolean shouldExplode;
	
	public AlienGrunt(int x, int y, String name) {
		super(x, y);
		this.name = name;
		initAlien();
	}
	
	private void initAlien() {
		loadImage("sprites/Aliens/AlienGrunt/alienGrunt.png");
		setImageDimensions();
		
		explosionAnimSmall = new ArrayList<String>();
		
		for (int i = 1; i <= 74; i++) {
			explosionAnimSmall.add("sprites/Effects/ExplosionFrames/Small/explosion1_" + i + ".png");
		}
		
		shouldExplode = true;
		
		laserDelay = 0;
		laserDelayMax = 180;
	}
	
	@Override
	public void destroyed() {
		if (shouldExplode) {
			setAnimImageScale(explosionScale, explosionScale);
			scaleAnim = false;
			playAnim(explosionAnimSmall, false, 7);
			shouldExplode = false;
			x -= explosionScale/2 - w/2;
			y -= explosionScale/2 - h/2;
		}
		
		dx = 0;
		dy = 0;
		
		if (!animTimer.isRunning()) {
			isDestroyed = true;
		}
	}
}
