
import java.util.ArrayList;

public class LevelSequence {
	
	public ArrayList<Alien> aliens;
	public ArrayList<AlienProjectile> alienProjectiles;
	
	public ArrayList<Boolean> sceneDone;
	public ArrayList<Boolean> sceneStart;
	public ArrayList<Long> sceneDoneTimes;
	
	protected long timeNum;
	protected int timeNumSec;
	
	public LevelSequence() {
		aliens = new ArrayList<Alien>();
		alienProjectiles= new ArrayList<AlienProjectile>();
		
		sceneDone = new ArrayList<Boolean>();
		sceneStart = new ArrayList<Boolean>();
		sceneDoneTimes = new ArrayList<Long>();
		
		timeNum = 0;
		timeNumSec = 0;
	}
	
	public void updateSequence() {
		updateAlienMovement();
		
		timeNum++;
		
		if (timeNum % 200 == 0) {
			timeNumSec++;
		}
		
		
	}
	
	private void updateAlienMovement () {
		for (int i = aliens.size() - 1; i >= 0; i--) {
			aliens.get(i).move();
			
			if (!aliens.get(i).getVisible()) {
				aliens.remove(i);
			}
		}
	}
	
	protected void moveInSquares(int timeInterval, Alien alien, int direction, int speed) {
		int dyLast = alien.dy;
		int dxLast = alien.dx;
		alien.speed = speed;
		
		if (timeNum % timeInterval == 0) {
			
			if ((dyLast == 0 && dxLast == 0) || dyLast == 0 && dxLast == -1 * direction) {
				alien.dy = 1;
				alien.dx = 0;
			}
			
			if (dyLast == 1 && dxLast == 0) {
				alien.dy = 0;
				alien.dx = 1 * direction;
			}
			
			if (dyLast == 0 && dxLast == 1 * direction) {
				alien.dy = -1;
				alien.dx = 0;
			}
			
			if (dyLast == -1 && dxLast == 0) {
				alien.dy = 0;
				alien.dx = -1 * direction;
			}
			
		} 	
	}
	
	protected void moveToHorizontal(Alien alien, int xVal, int speed) {
		alien.speed = speed;
		
		if (alien.getX() != xVal) {
			
			if (xVal > alien.getX()) {
				alien.dx = 1;
			} else {
				alien.dx = -1;
			}
			
		} else {
			alien.dx = 0;
		}
	}
	
	protected boolean moveToVertical(Alien alien, int yVal, int speed) {
		alien.speed = speed;
		boolean b = false;
		
		if (alien.getY() != yVal) {
			if (yVal > alien.getY()) {
				alien.dy = 1;
			} else {
				alien.dy = -1;
			}
			
			b = false;
		} else {
			alien.dy = 0;
			b = true;
		}
		
		return b;
	}
	
	protected void moveBetweenHorizontal(Alien alien, int xL, int xR, int speed) {
		alien.speed = speed;
		
		
		if (alien.getX() == xL) {
			alien.dx = 1;
		} else if (alien.getX() == xR) {
			alien.dx = -1;
		} else if (alien.dx == 0) {
			if (Math.abs(alien.getX() - xL) > Math.abs(alien.getX() - xR)) {
				alien.dx = -1;
			} else {
				alien.dx = 1;
			}
		}
	}
	
	protected void moveBetweenVertical(Alien alien, int yU, int yD, int speed) {
		alien.speed = speed;
		
		if (alien.getY() == yU) {
			alien.dy = 1;
		} else if (alien.getY() == yD) {
			alien.dy = -1;
		}
	}
	
	protected void moveInDiamond(Alien alien, int timeInterval, int speed, int direction, long timeNume) {
		alien.speed = speed;
		int dyLast = alien.dy;
		int dxLast = alien.dx;
		
		if (timeNume % timeInterval == 0) {
			
			if ((dyLast == 0 && dxLast == 0) || dyLast == -1 && dxLast == 1 * direction) {
				alien.dy = 1;
				alien.dx = 1 * direction;
			}
			
			if (dyLast == 1 && dxLast == 1 * direction) {
				alien.dy = 1;
				alien.dx = -1 * direction;
			}
			
			if (dyLast == 1 && dxLast == -1 * direction) {
				alien.dy = -1;
				alien.dx = -1 * direction;
			}
			
			if (dyLast == -1 && dxLast == -1 * direction) {
				alien.dy = -1;
				alien.dx = 1 * direction;
			}
			
		} 	
	}
	
}
