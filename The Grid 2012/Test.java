
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.mathhead200.grid.HashGrid;
import com.mathhead200.grid.gui.Sprite;
import com.mathhead200.grid.gui.SpriteBox;
import com.mathhead200.grid.gui.SpriteCanvas;


class Item implements Sprite, Comparable<Item> {
	static Random rand = new Random();
	static final int WIDTH = 32, HEIGHT = 32, R = Math.min(WIDTH, HEIGHT);
	BufferedImage img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);

	public Item() {
		int rgb = rand.nextInt(0xFFFFFF);
		for( int x = 0; x < WIDTH; x++ )
			for( int y = 0; y < HEIGHT; y++ ) {
				double r = Math.sqrt( x*x + y*y );
				img.setRGB( x, y, r <= R ? rgb & (0xFF << 24) : 0 );
			}
	}

	public Image getImage() {
		return img;
	}

	public int compareTo(Item s) {
		return hashCode() - s.hashCode();
	}
}


public class Test
{
	public static void main(String[] args) {
		JFrame frame = new JFrame("Sprite Test");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		HashGrid<SpriteBox<Item>> grid = new HashGrid<SpriteBox<Item>>();
		grid.add( 0, 0, new SpriteBox<Item>() );
		grid.get(0, 0).add( new Item() );
		grid.add( 4, 4, new SpriteBox<Item>() );
		grid.get(4, 4).add( new Item() );
		SpriteCanvas canvas = new SpriteCanvas(grid, 10, 10, 32, 32);
		frame.getContentPane().add(canvas);
		frame.getContentPane().add( new JLabel(new ImageIcon(grid.get(0, 0).getImage())) );
		frame.pack();
		frame.setVisible(true);
	}
}
