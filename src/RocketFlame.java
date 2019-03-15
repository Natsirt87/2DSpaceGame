
import java.util.*;

public class RocketFlame extends Sprite {
	
	private ArrayList<String> normalAnim;
	private ArrayList<String> slowAnim;
	private ArrayList<String> fastAnim;
	
	private int dyLast;
	
	public RocketFlame(int x, int y) {
		super(x, y);
		initFlame();
	}
	
	private void initFlame() {
		loadImage("sprites/Player/Flame/Normal/flame1.png");
		setImageDimensions();
		
		normalAnim = new ArrayList<String>();
		slowAnim = new ArrayList<String>();
		fastAnim = new ArrayList<String>();
		
		for (int i = 0; i < 5; i++) {
			normalAnim.add("sprites/Player/Flame/Normal/flame" + (i+1) + ".png");
		}
		
		for (int i = 0; i < 5; i++) {
			slowAnim.add("sprites/Player/Flame/Slow/flame" + (i+1) + ".png");
		}
		
		for (int i = 0; i < 5; i++) {
			fastAnim.add("sprites/Player/Flame/Fast/flame" + (i+1) + ".png");
		}
		
	}
	
	public void updateAnim(int dy) {
		boolean dChanged = dyLast != dy;
		
		if (dy == 0) {
			if (dChanged) {
				cancelAnim();
				normalAnim();
			} else {
				if (!animTimer.isRunning()) {
					animNum = 0;
					normalAnim();
				}
			}
		}
		
		if (dy == 1) {
			if (dChanged) {
				cancelAnim();
				slowAnim();
			} else {
				if (!animTimer.isRunning()) {
					animNum = 0;
					slowAnim();
				}
			}
		}
		
		if (dy == -1) {
			if (dChanged) {
				cancelAnim();
				fastAnim();
			} else {
				if (!animTimer.isRunning()) {
					animNum = 0;
					fastAnim();
				}
			}
		}
		
		dyLast = dy;
	}
	
	public void normalAnim() {
		playAnim(normalAnim, false, 20);
	}
	
	public void slowAnim() {
		playAnim(slowAnim, false, 20);
	}
	
	public void fastAnim() {
		playAnim(fastAnim, false, 20);
	}
	
	public void cancelAnim() {
		animTimer.stop();
		animNum = 0;
	}
}
