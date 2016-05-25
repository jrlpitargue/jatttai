import java.awt.Font;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class Tile extends JButton {

	public final int x;
	public final int y;
	
	public Tile(int x, int y) {
		super();
		this.x = x;
		this.y = y;
		setActionCommand("tile");
		setFocusable(false);
		setFont(new Font("Arial", Font.PLAIN, 60));
	}
	
}
