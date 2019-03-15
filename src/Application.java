
import java.awt.*;
import javax.swing.*;

public class Application extends JFrame{

	private static final long serialVersionUID = 3144433787255316924L;

	public Application() {
		initUI();
	}
	
	public void initUI() {
		Board board = new Board();
		
		add(board);
		
		pack();
		
		setTitle("Spacey Boi");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
	        Application ex = new Application();
	        ex.setVisible(true);
	    });
	}
}
