
import com.mathhead200.scene.*;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

public class SceneTest
{
	public static void main(String[] args) {
		JFrame frame = new JFrame("Scene Test");
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		frame.setBackground( Color.GRAY );
		Scene scene = new Scene( new Ellipse2D.Double(0, 0, 500, 500) );
		frame.getContentPane().add(scene);
		frame.pack();
		frame.setVisible(true);
	}
}
