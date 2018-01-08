package connect4;

import javax.swing.ImageIcon;
import com.mathhead200.grid.GridItem;


public class Player
{
	public Class<? extends GridItem> item;
	public ImageIcon ico;

	public Player(Class<? extends GridItem> item, ImageIcon ico) {
		this.item = item;
		this.ico = ico;
	}
}
