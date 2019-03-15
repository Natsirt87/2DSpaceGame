
public class LevelSequence1 extends LevelSequence{
	
	public boolean check1Start;
	public boolean check1Done;
	public boolean check2Start;
	public boolean check2Done;
	
	public boolean isPaused;
	
	private int alienFireRate;
	
	public LevelSequence1() {
		super();
		initSequence();
	}
	
	public void initSequence() {
		for (int i = 0; i < 20; i++) {
			sceneDone.add(false);
			sceneDoneTimes.add((long)0);
			sceneStart.add(false);
		}
		
		alienFireRate = 150;
	}
	
	@Override
	public void updateSequence() {
		if (!isPaused) {
			levelEventSequence();
			
			for (int i = 0; i < aliens.size(); i++) {
				if (aliens.get(i).health > 0) {
					shootProjectiles(aliens.get(i));
				} else {
					aliens.get(i).dx = 0;
					aliens.get(i).dy = 0;
				}
			}
			
			super.updateSequence();
		}
	}
	
	private void levelEventSequence() {
		if (!check1Done) {
			if (checkpoint1()) {
				check1Done = true;
			}
		}
		
		if (check1Done) {
			if (checkpoint2()) {
				check2Done = true;
			}
		}
		
	}
	
	private boolean checkpoint1() {
		//Spawning alien grunts for scene 1
				if (timeNum == 0) {
					check1Start = true;
					
					aliens.add(new AlienGrunt(256, -100, "Left1"));
					aliens.add(new AlienGrunt(512, -100, "Left2"));
					aliens.add(new AlienGrunt(768, -100, "Right2"));
					aliens.add(new AlienGrunt(1024, -100, "Right1"));
					
					sceneStart.set(0, true);
				}
				
				if (aliens.size() > 0 && sceneStart.get(0) && !sceneDone.get(0)) {
					sceneDone.set(0, true);
					
					for (int i = 0; i < aliens.size(); i++) {
						Alien alien = aliens.get(i);
						
						moveToVertical(alien, 50, 1);
						
						if (alien.getY() != 50) {
							sceneDone.set(0, false);
						}
					}
				} else if (aliens.size() == 0 && sceneStart.get(0) && !sceneDone.get(0)) {
					sceneDone.set(0, true);
				}
				
				//Moving them in square patterns until they are all
				//destroyed by the player
				if (aliens.size() > 0 && !sceneDone.get(1) && sceneDone.get(0)) {
					
					for (int i = 0; i < aliens.size(); i++) {
						if (aliens.get(i).name.contains("Left")) {
							moveInSquares(200, aliens.get(i), 1, 2);
						} else if (aliens.get(i).name.contains("Right")) {
							moveInSquares(200, aliens.get(i), -1, 2);
						}
						aliens.get(i).laserDelayMax = (int)(Math.random() * 20) + alienFireRate;
						
					}
				} else if (sceneDoneTimes.get(0) == 0 && aliens.size() == 0 && !sceneDone.get(1)){
					sceneDone.set(1, true);
					sceneDoneTimes.set(0, timeNum);
				}
				
				//Start scene 2, which is spawning moving aliens from the right and left
				//into position for scene 3
				if (sceneDone.get(1) && !sceneStart.get(2) && timeNum - sceneDoneTimes.get(0) > 200) {
					aliens.add(new AlienGrunt(-50, 50, "Left1"));
					aliens.add(new AlienGrunt(1330, 150, "Right1"));
					aliens.add(new AlienGrunt(-50, 250, "Left2"));
					aliens.add(new AlienGrunt(1330, 350, "Right2"));
					aliens.add(new AlienGrunt(-50, 450, "Left3"));
					aliens.add(new AlienGrunt(1330, 550, "Right3"));
					
					sceneStart.set(2, true);
				}
				
				//Moving the aliens for scene 2
				if (sceneStart.get(2) && !sceneDone.get(2) && aliens.size() > 0) {
					sceneDone.set(2, true);
					
					for (int i = 0; i < aliens.size(); i++) {
						Alien alien = aliens.get(i);
						
						aliens.get(i).laserDelayMax = (int)(Math.random() * 30) + alienFireRate;
						
						if (alien.name.contains("Left")) {
							moveToHorizontal(alien, 50, 1);
							
							if (alien.getX() != 50) {
								sceneDone.set(2, false);
							}
							
						} else if (alien.name.contains("Right")) {
							moveToHorizontal(alien, 1230, 1);
							
							
							if (alien.getX() != 1230) {
								sceneDone.set(2, false);
							}
						}
						
					}
					
					if (sceneDone.get(2) && sceneDoneTimes.get(1) == 0) {
						sceneDoneTimes.set(1, timeNum);
					}
					
				} else if (aliens.size() == 0 && sceneStart.get(2) && sceneDoneTimes.get(1) == 0) {
					sceneDone.set(2, true);
					sceneDoneTimes.set(1, timeNum);			
				}
				
				//Start scene 3, which is moving the aliens back and forth from 
				//each side of the screen
				if (sceneDone.get(2) && !sceneDone.get(3) && aliens.size() > 0) {
					sceneStart.set(3, true);
					
					for (int i = 0; i < aliens.size(); i++) {
						Alien alien = aliens.get(i);
						
						moveBetweenHorizontal(alien, 50, 1230, 2);
					}
					
					
				} else if (aliens.size() == 0 && sceneStart.get(3) && sceneDoneTimes.get(2) == 0) {
					sceneDone.set(3, true);
					sceneDoneTimes.set(2, timeNum);
				}
				
				//Start scene 4, which sets up for scene 5
				if (sceneDone.get(3) && !sceneStart.get(4) && timeNum - sceneDoneTimes.get(2) > 250) {
					aliens.add(new AlienGrunt(142, -100, "Left1"));
					aliens.add(new AlienGrunt(284, -200, "Left2"));
					aliens.add(new AlienGrunt(426, -300, "Left3"));
					aliens.add(new AlienGrunt(568, -400, "Left4"));
					
					aliens.add(new AlienGrunt(1137, -100, "Right1"));
					aliens.add(new AlienGrunt(995, -200, "Right2"));
					aliens.add(new AlienGrunt(853, -300, "Right3"));
					aliens.add(new AlienGrunt(711, -400, "Right4"));
					
					sceneStart.set(4, true);
				}
				
				//Move aliens into position for scene 5
				if (sceneStart.get(4) && !sceneDone.get(4) && aliens.size() > 0) {
					sceneDone.set(4, true);
					
					for (int i = 0; i < aliens.size(); i++) {
						Alien alien = aliens.get(i);
						
						aliens.get(i).laserDelayMax = (int)(Math.random() * 40) + alienFireRate;
						
						int offset = Integer.parseInt(alien.name.substring(alien.name.length() - 1));
						offset *= 100;
						
						boolean isDone = moveToVertical(alien, 500 - offset, 2);
						
						if (!isDone) {
							sceneDone.set(4, false);
						}
					}
					
					if (sceneDone.get(4) && sceneDoneTimes.get(3) == 0) {
						sceneDoneTimes.set(3, timeNum);
					}
				} else if (aliens.size() == 0 && sceneStart.get(4) && sceneDoneTimes.get(3) == 0) {
					sceneDone.set(4, true);
					sceneDoneTimes.set(3, timeNum);
				}
				
				//Scene 5, moving all the aliens in a diamond pattern
				if (sceneDone.get(4) && !sceneDone.get(5) && aliens.size() > 0) {
					sceneStart.set(5, true);
					
					for (int i = 0; i < aliens.size(); i++) {
						Alien alien = aliens.get(i);
						aliens.get(i).laserDelayMax = (int)(Math.random() * 40) + alienFireRate;
						
						if (timeNum - sceneDoneTimes.get(3) > 0) {
							if (alien.name.contains("Left")) {
								moveInDiamond(alien, 120, 1, 1, timeNum + i * 400);
							} else if (alien.name.contains("Right")) {
								moveInDiamond(alien, 120, 1, -1, timeNum + i * 400);
							}
						}
					}
				} else if (aliens.size() == 0 && sceneStart.get(5) && sceneDoneTimes.get(4) == 0) {
					sceneDone.set(5, true);
					sceneDoneTimes.set(4, timeNum);
					timeNum = 0;
					sceneDone.removeAll(sceneDone);
					sceneStart.removeAll(sceneStart);
					sceneDoneTimes.removeAll(sceneDoneTimes);
					initSequence();
					return true;
				}
				
				return false;
	}
	
	private boolean checkpoint2() {
		
		//Spawn aliens for the beginning of checkpoint 2 (scene 0)
		if (timeNum == 1000) {
			check2Start = true;
			aliens.add(new AlienGrunt(-100, 100, "Left1"));
			aliens.add(new AlienGrunt(-100, 200, "Left2"));
			aliens.add(new AlienGrunt(-100, 300, "Left3"));
			
			aliens.add(new AlienGrunt(1380, 150, "Right3"));
			aliens.add(new AlienGrunt(1380, 250, "Right2"));
			aliens.add(new AlienGrunt(1380, 350, "Right1"));
			
			sceneStart.set(0, true);
		}
		
		//Move them horizontally into position for movement (still scene 0)
		if (sceneStart.get(0) && aliens.size() > 0 && !sceneDone.get(0)) {
			sceneDone.set(0, true);
			for (int i = 0; i < aliens.size(); i++) {
				Alien alien = aliens.get(i);
				
				int offset = Integer.parseInt(alien.name.substring(alien.name.length() - 1));
				offset *= 100;
				if (alien.name.contains("Left")) {
					moveToHorizontal(alien, 400 - offset, 2);
					if (alien.getX() != 400 - offset) {
						sceneDone.set(0, false);
					}
				} else {
					moveToHorizontal(alien, 1180 - offset, 2);
					if (alien.getX() != 1180 - offset) {
						sceneDone.set(0, false);
					}
				}
			}
			if (sceneDone.get(0) && sceneDoneTimes.get(0) == 0) {
				sceneDoneTimes.set(0, timeNum);
			}
		} else if (aliens.size() == 0 && sceneStart.get(0) && sceneDoneTimes.get(0) == 0) {
			sceneDoneTimes.set(0, timeNum);
			sceneDone.set(0, true);
		}
		
		//Move the aliens on the screen in a diamond pattern and back and forth
		//horizontally but they bounce off the top off the window for scene 1
		if (sceneDone.get(0) && aliens.size() > 0 && !sceneDone.get(1)) {
			for (int i = 0; i < aliens.size(); i++) {
				Alien alien = aliens.get(i);
				alienFireRate = 50;
				aliens.get(i).laserDelayMax = (int)(Math.random() * 40) + alienFireRate;
				
				int offset = Integer.parseInt(alien.name.substring(alien.name.length() - 1));
				offset *= 100;
				
				if (alien.name.contains("Right")) {
					
					if (alien.name.contains("1")) {
						offset = 300;
					} else if (offset == 300) {
						offset = 100;
					}
					
					offset += 50;
				}
				
				moveBetweenHorizontal(alien, 200, 1080, 2);
				moveBetweenVertical(alien, 0, offset, 2);
			}
			sceneStart.set(1, true);
		} else if (sceneDone.get(0) && aliens.size() == 0 && !sceneDone.get(1)) {
			sceneDone.set(1, true);
			sceneDoneTimes.set(1, timeNum);
		}
		
		//Spawn aliens for scene 2
		if (sceneDone.get(1) && timeNum - sceneDoneTimes.get(1) > 200 && !sceneStart.get(2)) {
			aliens.add(new AlienGrunt(142, -100, "Left1"));
			aliens.add(new AlienGrunt(284, -100, "Left2"));
			aliens.add(new AlienGrunt(426, -100, "Left3"));
			aliens.add(new AlienGrunt(568, -100, "Left4"));
			
			aliens.add(new AlienGrunt(1137, -100, "Right1"));
			aliens.add(new AlienGrunt(995, -100, "Right2"));
			aliens.add(new AlienGrunt(853, -100, "Right3"));
			aliens.add(new AlienGrunt(711, -100, "Right4"));
			
			sceneStart.set(2, true);
		}
		
		//Move aliens in large diamond patterns with direction dependent
		//on horizontal location for scene 2
		if (sceneStart.get(2) && aliens.size() > 0) {
			for (int i = 0; i < aliens.size(); i++) {
				Alien alien = aliens.get(i);
				alienFireRate = 120;
				aliens.get(i).laserDelayMax = (int)(Math.random() * 20) + alienFireRate;
				
				if (alien.name.contains("Right")) {
					moveInDiamond(alien, 250, 2, -1, timeNum + i * 50);
				} else {
					moveInDiamond(alien, 250, 2, 1, timeNum + i * 50);
				}
			}
		} else if (sceneStart.get(2) && aliens.size() == 0 && !sceneDone.get(2)) {
			sceneDone.set(2, true);
			sceneDoneTimes.set(1, timeNum);
			System.out.println("Check 2, Scene 2 is done");
		}
		
		return false;
	}
	
	private void shootProjectiles(Alien alien) {
		
		if (alien instanceof AlienGrunt) {
			AlienGrunt alienGrunt = (AlienGrunt) alien;
			
			if (alienGrunt.laserDelay == 0) {
				alienProjectiles.add(new AlienGruntLaser(alienGrunt.getX(), alienGrunt.getY()));
				alienGrunt.laserDelay = alienGrunt.laserDelayMax;
			} else {
				alienGrunt.laserDelay--;
			}
		}
		
	}
}