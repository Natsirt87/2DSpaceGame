
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Board extends JPanel implements ActionListener{

	private static final long serialVersionUID = -7293766305942042325L;

	private final int DELAY = 5;
	
	private final int WINDOWX = 1280;
	private final int WINDOWY = 960;
	
	private int backgroundNum = 4;
	
	private int playerX;;
	private int playerY;
	
	private Timer timer;
	private Spaceship player;
	private RocketFlame playerFlame;
	
	private LevelSequence1 level;
	
	private int timeNum;
	
	private boolean isPaused;
	
	private long deathTime;
	
	private Background background1;
	private Background background2;
	private Background background3;
	private Background background4;
	
	public Board() {
		initBoard();
	}
	
	private void initBoard() {
		addKeyListener(new TAdapter());
		setPreferredSize(new Dimension(WINDOWX, WINDOWY));
		setBackground(Color.BLACK);
		setFocusable(true);
		
		level = new LevelSequence1();
		
		player = new Spaceship(0, 0);
		
		playerX = (WINDOWX/2) - (player.getImage().getWidth(null)/2);
		playerY = WINDOWY/2;
		
		player.setX(playerX);
		player.setY(playerY);
		
		playerFlame = new RocketFlame(0, 0);
		
		background1 = new Background(0, 0, backgroundNum);
		background2 = new Background(background1.getImage().getWidth(null), 0, backgroundNum);
		background3 = new Background(0, -(background1.getImage().getHeight(this)), backgroundNum);
		background4 = new Background(background1.getImage().getWidth(null), -(background2.getImage().getHeight(this)), backgroundNum);
		
		timer = new Timer(DELAY, this);
		timer.start();
	}
	
	private void resetBackground() {
		background1.setX(0);
		background1.setY(0);
		
		background2.setX(background1.getImage().getWidth(null));
		background2.setY(0);
		
		background3.setX(0);
		background3.setY(-(background1.getImage().getHeight(this)));
		
		background4.setX(background1.getImage().getWidth(null));
		background4.setY(-(background2.getImage().getHeight(this)));
	}
	
	//Method that runs every frame, renders objects on the screen
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		doDrawing(g);
	}
	
	private void doDrawing(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		int flameX = player.getX() + (player.getImage().getWidth(null)/2) -(playerFlame.getImage().getWidth(null)/2);
		int flameY = player.getY() + player.getImage().getHeight(null) - 6;
		
		g2d.drawImage(background1.getImage(), background1.getX(), background1.getY(), this);
		g2d.drawImage(background2.getImage(), background2.getX(), background2.getY(), this);
		g2d.drawImage(background3.getImage(), background3.getX(), background3.getY(), this);
		g2d.drawImage(background4.getImage(), background4.getX(), background4.getY(), this);
		
		for (int i = 0; i < player.lasers.size(); i++) {
			Laser laser = player.lasers.get(i);
			
			g2d.drawImage(laser.getImage(), laser.getX(), laser.getY(), this);
			
		}
		
		for (int i = 0; i < level.alienProjectiles.size(); i++) {
			AlienProjectile shot = level.alienProjectiles.get(i);
			
			g2d.drawImage(shot.getImage(), shot.getX(), shot.getY(), this);
		}
		
		for (int i = 0; i < level.aliens.size(); i++) {
			Alien alien = level.aliens.get(i);
			g2d.drawImage(alien.getImage(), alien.getX(), alien.getY(), this);
		}
		
		if (player.getVisible()) {
			g2d.drawImage(playerFlame.getImage(), flameX, flameY, this);
			g2d.drawImage(player.getImage(), player.getX(), player.getY(), this);
		}
		
		if (level.check1Done && !level.check2Start) {
			g2d.setColor(Color.RED);
			
			Font font = new Font("TimesRoman", Font.PLAIN, 40);
			String text = "Checkpoint 1 reached";
			
			g2d.setFont(font);
			g2d.drawString(text, 455, 480);
		}
		

		Toolkit.getDefaultToolkit().sync();
	}
	
	 @Override
	 public void actionPerformed(ActionEvent e) {
		 if (!isPaused) {
			 level.updateSequence();
			 updateAlienHealth();
			 player.checkEdgeCollision(WINDOWX, WINDOWY);
			 updatePlayerHealth();
			 updateCheckpoint();
			 
			 if (timeNum % 2 == 0) {
				 scrollBackground();
				 updateProjectiles();
				 player.move();
				 playerFlame.updateAnim(player.dy);
			 }
			 
			 timeNum++;
			 repaint();
		 }
	 }
	 
	 private void updateCheckpoint() {
		
	 }
	 
	 private void scrollBackground() {
		 if (background4.getY() < 0) {
			 background1.move();
			 background2.move();
			 background3.move();
			 background4.move();
		 } else {
			 resetBackground();
		 }
	 }
	 
	 private void updateAlienHealth() {
		 for (int i = 0; i < level.aliens.size(); i++) {
			 Alien alien = level.aliens.get(i);
			 
			 if (alien.health <= 0) {
				 alien.destroyed();
			 }
			 
			 if (alien.isDestroyed) {
				 alien.setVisible(false);
			 }
		 }
	 }
	 
	 private void updateProjectiles() {
		 for (int i = player.lasers.size() - 1; i >= 0; i--) {
			 Laser laser = player.lasers.get(i);
			 laser.move();
			 
			 if ((laser.getY() + laser.getImage().getHeight(null)) < 0) {
				 player.lasers.remove(i);
			 }
			 
			 if(!laser.getVisible()) {
				 player.lasers.remove(i);
			 }
			 
			 for (int j = 0; j < level.aliens.size(); j++) {
				 if (laser.getBounds().intersects(level.aliens.get(j).getBounds())) {
					 level.aliens.get(j).doDamage(100);
					 laser.setVisible(false);
					 level.aliens.get(j).explosionScale = 65;
				 }
			 }
		 }
		 
		 for (int i = level.alienProjectiles.size() - 1; i >= 0; i--) {
			 AlienProjectile laser = level.alienProjectiles.get(i);
			 laser.move();
			 
			 if (laser.getY() > 1400) {
				 level.alienProjectiles.remove(i);
			 }
			 
			 if (!laser.getVisible()) {
				 level.alienProjectiles.remove(i);
			 }
			 
			 if (laser.getBounds().intersects(player.getProjectileBounds())) {
				 laser.setVisible(false);
				 level.alienProjectiles.remove(i);
				 player.doDamage(25);
				 
				 System.out.println("Player was hit!");
			 }
		 }
	 }
	 
	 private void updatePlayerHealth() {
		 for (int i = 0; i < level.aliens.size(); i++) {
			 if (player.getBounds().intersects(level.aliens.get(i).getBounds())){
				 player.onCollide(level.aliens.get(i));
			 }
		 }
		 
		 if (!player.isDead) {
			 deathTime = System.currentTimeMillis();
		 }
		 
		 if (player.isDead) {
			 resetCheckpoint();
		 }
		 
	 }
	 
	 private void resetCheckpoint() {
		 
		 level.aliens.removeAll(level.aliens);
		 level.alienProjectiles.removeAll(level.alienProjectiles);
		 
		 level.sceneDone.removeAll(level.sceneDone);
		 level.sceneStart.removeAll(level.sceneStart);
		 level.sceneDoneTimes.removeAll(level.sceneDoneTimes);
		 level.initSequence();
		 level.timeNum = 0;
		 
		 player.lasers.removeAll(player.lasers);
		 
		 if (!player.getVisible()) {
			 player.setX(playerX);
			 player.setY(playerY);
		 }
		 player.setVisible(false);
		 
		 level.isPaused = true;
		 
		 long timeDiff = System.currentTimeMillis() - deathTime;
		 
		 if (level.check1Start && !level.check1Done && timeDiff > 2000) {
			 player.resetHealth();
			 player.setVisible(true);
			 
			 if (timeDiff > 3500) {
				 player.isDead = false;
				 level.isPaused = false;
				 level.check1Start = false;
			 }
		 }
		 
		 if (level.check2Start && !level.check2Done && timeDiff > 2000) {
			 player.resetHealth();
			 player.setVisible(true);
			 
			 if (timeDiff > 3500) {
				 player.isDead = false;
				 level.isPaused = false;
				 level.check2Start = false;
			 }
		 }
	 }
	 
	 public void pause() {
		 
		 if (isPaused) {
			 isPaused = false;
		 } else {
			 isPaused = true;
		 }
		 
	 }
	 
	 private class TAdapter extends KeyAdapter{
		 
		 @Override
		 public void keyPressed(KeyEvent e) {
			 int key = e.getKeyCode();
			 
			 if (key == KeyEvent.VK_ESCAPE) {
				 pause();
			 } else {
				 player.keyPressed(e);
			 }
			 
			 
		 }
		 
		 @Override
		 public void keyReleased (KeyEvent e) {
			 player.keyReleased(e);
		 } 
	 } 
}