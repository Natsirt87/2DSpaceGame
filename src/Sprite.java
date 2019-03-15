
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

public class Sprite implements ActionListener{
	
	public String name;
	protected int x;
	protected int y;
	protected Image image;
	protected int h;
	protected int w;
	protected boolean visible;
	
	protected int animNum;
	protected Timer animTimer = new Timer(20, this);
	protected ArrayList<String> animImages;
	protected boolean reverseAnim;
	protected boolean scaleAnim;
	protected int scaledWidth;
	protected int scaledHeight;
	
	public Sprite(int x, int y) {
		visible = true;
		
		this.x = x;
		this.y = y;
		
		animImages = new ArrayList<String>();
	}
	
	protected void loadImage(String imageName) {
		ImageIcon ii = new ImageIcon(imageName);
		image = ii.getImage();
	}
	
	protected void setImageDimensions() {
		w = image.getWidth(null);
		h = image.getHeight(null);
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public Image getImage() {
		return image;
	}
	
	public boolean getVisible() {
		return visible;
	}
	
	public void setVisible(boolean b) {
		visible = b;
	}
	
	public Rectangle getBounds() {
		Rectangle rect = new Rectangle(x, y, w, h);
		return rect;
	}
	
	public void onCollide(Sprite other) {
		System.out.println(name + " collided with " + other.name);
	}
	
	//Method for playing animations, uses an array of image names
	//and a timer to iterate through and render all of them sequentially
	public void playAnim(ArrayList<String> imageNames, boolean reverse, int delay) {
		if (reverse) {
			animNum = imageNames.size() - 1;
		}
		
		reverseAnim = reverse;
		animImages.removeAll(animImages);		
		animImages.addAll(imageNames);
		animTimer.setDelay(delay);
		animTimer.setInitialDelay(0);
		animTimer.start();
	}

	public void setAnimImageScale(int width, int height) {
		scaledWidth = width;
		scaledHeight = height;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (!reverseAnim) {
			String imageName = animImages.get(animNum);
			loadImage(imageName);
			
			if (scaleAnim) {
				image = image.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_FAST);
			}
			
			if (animNum < animImages.size() - 1) {
				animNum++;
			} else {
				animTimer.stop();
			}
		} else {
			String imageName = animImages.get(animNum);
			loadImage(imageName);
			
			if (animNum > 0) {
				animNum--;
			} else {
				animTimer.stop();
			}
		}
	}
	
}
