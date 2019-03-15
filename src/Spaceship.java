
import java.awt.Rectangle;
import java.awt.event.*;
import java.util.ArrayList;

public class Spaceship extends Sprite{
	
	public int dx;
	public int dy;
	
	public boolean isDead;
	
	private int health;
	
	private boolean canMoveUp;
	private boolean canMoveDown;
	private boolean canMoveRight;
	private boolean canMoveLeft;
	
	private boolean movingUp;
	private boolean movingDown;
	private boolean movingLeft;
	private boolean movingRight;
	
	private boolean canFireLaser;
	private int laserPos;
	
	private ArrayList<String> moveLeftAnim;
	private ArrayList<String> moveRightAnim;
	
	public ArrayList<Laser> lasers;
	
	public Spaceship(int x, int y) {
		super(x, y);
		initSpaceship();
	}
	
	//Sets all of the default values necessary
	private void initSpaceship() {
		name = "Player";
		
		loadImage("sprites/Player/player.png");
		setImageDimensions();
		
		canMoveUp = true;
		canMoveDown = true;
		canMoveRight = true;
		canMoveLeft = true;
		
		health = 100;
		
		moveLeftAnim = new ArrayList<String>();
		moveRightAnim = new ArrayList<String>();
		
		lasers = new ArrayList<Laser>();
		laserPos = 1;
		
		canFireLaser = true;
		
		for (int i = 0; i < 5; i++) {
			moveLeftAnim.add("sprites/Player/Animation/playerLeft" + (i+1) + ".png");
		}
		
		for (int i = 0; i < 5; i++) {
			moveRightAnim.add("sprites/Player/Animation/playerRight" + (i+1) + ".png");
		}
		
	}
	
	public void doDamage (int damage) {
		health -= damage;
		if (health <= 0) {
			isDead = true;
		}
	}
	
	public void resetHealth() {
		health = 100;
	}
	
	public void setMoveUp(boolean b) {
		canMoveUp = b;
	}
	
	public void setMoveDown(boolean b) {
		canMoveDown = b;
	}
	
	public void setMoveRight(boolean b) {
		canMoveRight = b;
	}
	
	public void setMoveLeft(boolean b) {
		canMoveLeft = b;
	}
	
	//Moves the player by a certain number of pixels
	public void move() {
		
		if (dx > 0 && canMoveRight) {
			x += dx * 4;
		}
		
		if (dx < 0 && canMoveLeft) {
			x += dx * 4;
		}
		
		if (dy > 0 && canMoveDown) {
			y += dy * 4;
		}
		
		if (dy < 0 && canMoveUp) {
			y += dy * 5;
		}
	}
	
	//Fires when a key is press, handles basic movement and
	//more complex animation cases
	public void keyPressed(KeyEvent e) {
		
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
        	if (!movingLeft && movingRight) {
        		cancelAnim();
        	} else if(!movingLeft && animTimer.isRunning()) {
        		cancelAnim();
        	}
        	
        	playAnim(moveLeftAnim, false, 30);
            dx = -1;
        	movingLeft = true;
        }

        if (key == KeyEvent.VK_RIGHT) {
        	if (movingLeft && !movingRight) {
        		cancelAnim();
        	} else if (!movingRight && animTimer.isRunning()) {
        		cancelAnim();
        	}
        	
        	playAnim(moveRightAnim, false, 30);
            dx = 1;
            movingRight = true;
        }

        if (key == KeyEvent.VK_UP) {
        	movingUp = true;
            dy = -1;
        }

        if (key == KeyEvent.VK_DOWN) {
        	movingDown = true;
            dy = 1;
        }
        
        if (key == KeyEvent.VK_SPACE) {
        	if (canFireLaser) {
        		if (laserPos == 1) {
        			lasers.add(new Laser(x + w/2 - 3, y + 45));
        			laserPos = 2;
        		} else {
        			lasers.add(new Laser(x + w/2 - 24, y + 45));
        			laserPos = 1;
        		}
        	}
        	
        	canFireLaser = false;
        }
    }
	
	//Fires when a key is released, handles some movement
	//and animation cases to make things more smooth
	public void keyReleased(KeyEvent e) {
		
		int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_LEFT) {
			movingLeft = false;
			
			if (!movingRight) {
				playAnim(moveLeftAnim, true, 30);
				dx = 0;
			} else {
				
				if (dx == -1) {
					cancelAnim();
					playAnim(moveRightAnim, false, 30);
				}
				
				dx = 1;
			}
		}
		
		if (key == KeyEvent.VK_RIGHT) {
			movingRight = false;
			
			if (!movingLeft) {
				playAnim(moveRightAnim, true, 30);
				dx = 0;
			} else {
				
				if (dx == 1) {
					cancelAnim();
					playAnim(moveLeftAnim, false, 30);
				}
				
				dx = -1;
			}
		}
		
		if (key == KeyEvent.VK_UP) {
			movingUp = false;
			
			if (!movingDown) {
				dy = 0;
			} else {
				dy = 1;
			}
		}
		
		if (key == KeyEvent.VK_DOWN) {
			movingDown = false;
			
			if (!movingUp) {
				dy = 0;
			} else {
				dy = -1;
			}
		}
		
		if (key == KeyEvent.VK_SPACE) {
			canFireLaser = true;
		}
		
	}
	
	//Makes sure the player can't go off the edges of the window
	public void checkEdgeCollision(int windowX, int windowY) {
		 
		Rectangle playerCollision = getBounds();
		 
		if (playerCollision.contains(x, windowY)) {
			setMoveDown(false);
		} else {
			setMoveDown(true); 
		}
		 
		if (playerCollision.contains(x, 0)) {
			setMoveUp(false);
		} else {
			setMoveUp(true);
		}
		 
		if (playerCollision.contains(0, y)) {
			setMoveLeft(false);
		} else {
			setMoveLeft(true);
		}
		 
		if (playerCollision.contains(windowX, y)) {
			setMoveRight(false);
		} else {
			setMoveRight(true);
		}
	}
	
	public void cancelAnim() {
		loadImage("sprites/Player/player.png");
		animTimer.stop();
		animNum = 0;
	}
	
//	@Override
//	public Rectangle getBounds() {
//		Rectangle rect = new Rectangle(x + 11, y + 5, w-24, h - 5);
//		return rect;
//	}
	
	public Rectangle getProjectileBounds() {
		Rectangle rect = new Rectangle(x + 11, y + 5, w-24, h - 5);
		return rect;
	}
	
	@Override
	public void onCollide(Sprite other) {
		super.onCollide(other);
	}
}
