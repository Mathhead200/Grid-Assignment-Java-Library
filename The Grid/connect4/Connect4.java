package connect4;

import java.awt.Cursor;
import java.awt.Point;
import javax.swing.ImageIcon;
import com.mathhead200.grid.Grid;
import com.mathhead200.grid.BoxTemplate;


public class Connect4 extends Grid
{
	private static final BoxTemplate TEMPLATE = new BoxTemplate(64, 64);
	public static Player[] players = {
		new Player( RedPiece.class, new ImageIcon(RedPiece.icoAddr) ),
		new Player( BlackPiece.class, new ImageIcon(BlackPiece.icoAddr)  )
	};
	private int turn = 0;

	public Connect4() {
		super( 7, 6, TEMPLATE );
		setTitle("Connect Four");
		drawCursor();
	}


	private void drawCursor() {
		setCursor( getToolkit().createCustomCursor(
			turn().ico.getImage(),
			new Point( turn().ico.getIconWidth() / 4/*2*/, turn().ico.getIconHeight() / 4/*2*/ ),
			"Connect Four"
		) );
	}

	public Player turn() {
		return players[turn];
	}

	public void nextTurn() {
		turn = (turn + 1) % players.length;
		drawCursor();
	}


	public static void main(String[] args) {
		new Connect4();
	}
}
