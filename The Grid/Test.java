
import com.mathhead200.grid.*;
import com.mathhead200.grid.griditems.*;

import java.awt.Color;
import java.awt.MenuItem;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Test
{
	public static void main(String[] args) throws GridException, java.io.IOException {
		final Grid g = new Grid(8, 6, new Color( 0xccddff ), Grid.CENTERED);
		g.setTitle("Savable Grid");
		Box startBox = new Box( new Color(0x002244) );
		Bug wormy = new ActingBug("grid/griditems/worm.dmi", "Wormy");
		startBox.addGridItem(wormy);
		Box endBox = new Box( new Color(0xffbb33) );
		g.setBoxAt(startBox, 2, 4);
		g.setBoxAt(endBox, 5, 1);

		MenuBar mb = new MenuBar();
		Menu m = new Menu("File");
		MenuItem mi = new MenuItem("Save"), mi2 = new MenuItem("Load");
		mi.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					System.out.println("Saving \""+g.getTitle()+"\"...");
					try {
						g.overwrite("save.jgs");
					} catch(java.io.IOException f) {
						f.printStackTrace();
					} catch(NonSavableGridException f) {
						f.printStackTrace();
					}
					System.out.println("Done.");
			}
		});
		mi2.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					System.out.println("Loading \""+g.getTitle()+"\"...");
					try {
						Grid tempGrid = Grid.load("save.jgs", Grid.HIDDEN);
						g.setBoxes( tempGrid.getBoxes() );
						tempGrid.dispose();
					} catch(java.io.IOException f) {
						f.printStackTrace();
					} catch(WrongGridSizeException f) {
						f.printStackTrace();
					}
					System.out.println("Done.");
			}
		});
		m.add(mi);
		m.add(mi2);
		mb.add(m);
		g.setMenuBar(mb);
		g.pack();
	}
}
