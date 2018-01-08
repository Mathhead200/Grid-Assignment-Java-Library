package cgol;

import com.mathhead200.MH;
import com.mathhead200.grid.*;
import com.mathhead200.image.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import java.util.*;
import java.io.*;

@SuppressWarnings("serial")
public class ConwaysGameOfLife extends Grid implements Runnable
{
	private static class Listener extends GridItem implements GridActor {
		private boolean mouseDown = false;
		private boolean isEraser = false;
		private boolean ctrlDown = false;
		public Listener() {
			super( new EquationImage(1, 1) );
		}
		public String save() {
			return "";
		}
		public static GridItem load(String loadStr) {
			return new Listener();
		}
		public void mouseIn(Box box, Dir d) throws GridException {
			if( !(getParentGrid() instanceof ConwaysGameOfLife) )
				return;
			ConwaysGameOfLife cgol = (ConwaysGameOfLife)getParentGrid();
			{
				int[] cords = box.getBoxCord();
				cgol.autoSetTitle( cords[0], cords[1] );
			}
			if( mouseDown && !cgol.game.isAlive() )
				box.setTurf( isEraser ? Color.WHITE : cgol.currentColor );
		}
		public void mouseDown(Box box, int b) throws GridException {
			mouseDown = true;
			if( b == 3 )
				isEraser = true;
			mouseIn(box, null);
		}
		public void mouseUp(Box box, int b) throws GridException {
			mouseDown = false;
			isEraser = false;
		}
		public void keyDown(int k) throws GridException {
			if( !(getParentGrid() instanceof ConwaysGameOfLife) )
				return;
			ConwaysGameOfLife cgol = (ConwaysGameOfLife)getParentGrid();
			switch(k) {
			case 17: //Ctrl key
				ctrlDown = true;
				break;
			case 37: //Left
				if( cgol.speed > 1 )
					cgol.speed--;
				break;
			case 38: //Up
				if( cgol.speed < Integer.MAX_VALUE - 100 )
					cgol.speed += 100;
					break;
			case 39: //Right
				if( cgol.speed < Integer.MAX_VALUE - 1 )
					cgol.speed++;
				break;
			case 40: //Down
			if( cgol.speed > 100 )
				cgol.speed -= 100;
				break;
			case (int)'L':
				if(ctrlDown)
					cgol.getMenuBar().getMenu(0).getItem(0).getActionListeners()[0].actionPerformed(null);
				break;
			case (int)'S':
				if(ctrlDown)
					cgol.getMenuBar().getMenu(0).getItem(1).getActionListeners()[0].actionPerformed(null);
				break;
			case (int)'R':
				if(ctrlDown)
					cgol.getMenuBar().getMenu(1).getItem(0).getActionListeners()[0].actionPerformed(null);
				break;
			case (int)'M':
				if(ctrlDown)
					cgol.getMenuBar().getMenu(1).getItem(1).getActionListeners()[0].actionPerformed(null);
				break;
			case (int)'P':
				if(ctrlDown)
					cgol.getMenuBar().getMenu(2).getItem(0).getActionListeners()[0].actionPerformed(null);
				break;
			case (int)'0':
				if(ctrlDown)
					cgol.getMenuBar().getMenu(4).getItem(0).getActionListeners()[0].actionPerformed(null);
				break;
			case (int)'1':
				if(ctrlDown)
					cgol.getMenuBar().getMenu(4).getItem(1).getActionListeners()[0].actionPerformed(null);
				break;
			case (int)'2':
				if(ctrlDown)
					cgol.getMenuBar().getMenu(4).getItem(2).getActionListeners()[0].actionPerformed(null);
				break;
			case (int)'3':
				if(ctrlDown)
					cgol.getMenuBar().getMenu(4).getItem(3).getActionListeners()[0].actionPerformed(null);
				break;
			case (int)'4':
				if(ctrlDown)
					cgol.getMenuBar().getMenu(4).getItem(4).getActionListeners()[0].actionPerformed(null);
				break;
			case (int)'5':
				if(ctrlDown)
					cgol.getMenuBar().getMenu(4).getItem(5).getActionListeners()[0].actionPerformed(null);
				break;
			case (int)'6':
				if(ctrlDown)
					cgol.getMenuBar().getMenu(4).getItem(6).getActionListeners()[0].actionPerformed(null);
				break;
			case (int)'7':
				if(ctrlDown)
					cgol.getMenuBar().getMenu(4).getItem(7).getActionListeners()[0].actionPerformed(null);
				break;
			}
			cgol.autoSetTitle();
		}
		public void keyUp(int k) throws GridException {
			if( !(getParentGrid() instanceof ConwaysGameOfLife) )
				return;
			switch(k) {
			case 17:
				ctrlDown = false;
				break;
			}
		}
		public void mouseOut(Box b, Dir d) throws GridException {}
	}

	private abstract static class AL implements ActionListener {
		protected ConwaysGameOfLife cgol;
		public AL(ConwaysGameOfLife cgol) {
			this.cgol = cgol;
		}
	}

	private static class SpeedItem extends MenuItem {
		public SpeedItem(String lbl, ConwaysGameOfLife cgol, final int trgSpd) {
			super(lbl);
			addActionListener( new AL(cgol) {
				public void actionPerformed(ActionEvent e) {
					cgol.speed = trgSpd;
					cgol.autoSetTitle();
				}
			});
		}
	}

	private static class RandomizeItem extends MenuItem {
		public RandomizeItem(String lbl, ConwaysGameOfLife cgol, final int lw, final int dw) {
			super(lbl);
			addActionListener( new AL(cgol) {
				public void actionPerformed(ActionEvent e) {
					if( !cgol.game.isAlive() )
						cgol.randomize(lw, dw);
				}
			});
		}
	}

	private static class ColorizeItem extends MenuItem {
		public ColorizeItem(String lbl, ConwaysGameOfLife cgol, final Color[] colors) {
			super(lbl);
			addActionListener( new AL(cgol) {
				public void actionPerformed(ActionEvent e) {
					if( !cgol.game.isAlive() )
						cgol.colorize(colors);
				}
			});
		}
	}

	private static class ColorItem extends MenuItem {
		public ColorItem(String lbl, ConwaysGameOfLife cgol, final Color color) {
			super(lbl);
			addActionListener( new AL(cgol) {
				public void actionPerformed(ActionEvent e) {
					cgol.currentColor = color;
					cgol.autoSetTitle();
				}
			});
		}
	}

	private static class Change {
		int x, y;
		Color color;
		Change(int x, int y, Color color) {
			this.x = x;
			this.y = y;
			this.color = color;
		}
	}

	private int[] liveNumbers, birthNumbers;
	private long speed;
	private Thread game;
	private Color currentColor = Color.BLACK;

	public ConwaysGameOfLife(int x, int y, int width, int height, int[] bNums, int[] lNums, long speed) {
		super(x, y, Grid.HIDDEN);
		if( bNums != null )
			birthNumbers = bNums;
		else
			birthNumbers = new int[] {3};
		if( lNums != null )
			liveNumbers = lNums;
		else
			liveNumbers = new int[] {2, 3};
		this.speed = speed;
		game = new Thread(this);
		try {
			getBoxAt(0, 0).addGridItem( new Listener() );
		} catch(GridException e) { e.printStackTrace(); }
		for( Box[] bs : getBoxes() )
			for( Box b : bs ) {
				b.setPreferredSize( new Dimension( width / x, height / y ) );
				b.setMinimumSize( b.getPreferredSize() );
				b.setBorder( new javax.swing.border.EmptyBorder(0, 0, 0, 0) );
			}
		setMenuBar( new MenuBar() );
		Menu startMenu = new Menu("Start"),
			stopMenu = new Menu("Stop"),
			speedMenu = new Menu("Set Speed"),
			fileMenu = new Menu("File"),
			colorMenu = new Menu("Set Color");
		PopupMenu randomizeMenu = new PopupMenu("Randomize Game Board"),
			colorizeMenu = new PopupMenu("Colorize Game Board");
		MenuItem startItem = new MenuItem("Start Game [ctrl-r]"),
			stopItem = new MenuItem("Stop Game [ctrl-p]"),
			clearItem = new MenuItem("Clear Game Board"),
			saveItem = new MenuItem("Save [ctrl-s]"),
			loadItem = new MenuItem("Load [ctrl-l]"),
			advanceItem = new MenuItem("Advance Game [ctrl-m]");

		startMenu.add(startItem);
		startMenu.add(advanceItem);
		stopMenu.add(stopItem);
		stopMenu.add(clearItem);
		stopMenu.add(randomizeMenu);
		stopMenu.add(colorizeMenu);
		speedMenu.add( new SpeedItem("Fastest (.001 s)", this, 1) );
		speedMenu.add( new SpeedItem("Faster (.05 s)", this, 50) );
		speedMenu.add( new SpeedItem("Fast (.1 s)", this, 100) );
		speedMenu.add( new SpeedItem("Default (.25 s)", this, 250) );
		speedMenu.add( new SpeedItem("Slow (.5 s)", this, 500) );
		speedMenu.add( new SpeedItem("Slower (2 s)", this, 2000) );
		speedMenu.add( new SpeedItem("Slower-er (15 s)", this, 15000) );
		fileMenu.add(loadItem);
		fileMenu.add(saveItem);
		colorMenu.add( new ColorItem("Black [ctrl-0]", this, Color.BLACK) );
		colorMenu.add( new ColorItem("Red [ctrl-1]", this, Color.RED) );
		colorMenu.add( new ColorItem("Blue [ctrl-2]", this, Color.BLUE) );
		colorMenu.add( new ColorItem("Green [ctrl-3", this, Color.GREEN) );
		colorMenu.add( new ColorItem("Magenta [ctrl-4]", this, Color.MAGENTA) );
		colorMenu.add( new ColorItem("Yellow [ctrl-5]", this, Color.YELLOW) );
		colorMenu.add( new ColorItem("Orange [ctrl-6]", this, Color.ORANGE) );
		colorMenu.add( new ColorItem("Cyan [ctrl-7]", this, Color.CYAN) );
		randomizeMenu.add( new RandomizeItem("Scarce Life", this, 1, 25) );
		randomizeMenu.add( new RandomizeItem("Less Life", this, 1, 10) );
		randomizeMenu.add( new RandomizeItem("Average Life", this, 1, 5) );
		randomizeMenu.add( new RandomizeItem("More Life", this, 1, 3) );
		randomizeMenu.add( new RandomizeItem("Crowded Life", this, 1, 1) );
		colorizeMenu.add( new ColorizeItem("2 w/o Black", this, new Color[]{Color.RED, Color.BLUE}) );
		colorizeMenu.add( new ColorizeItem("3 w/o Black", this, new Color[]{Color.RED, Color.BLUE, Color.GREEN}) );
		colorizeMenu.add( new ColorizeItem("4 w/ Black", this, new Color[]{Color.BLACK, Color.RED, Color.BLUE, Color.GREEN}) );
		colorizeMenu.add( new ColorizeItem("7 w/o Black", this, new Color[]{Color.RED, Color.BLUE, Color.GREEN, Color.MAGENTA, Color.ORANGE, Color.YELLOW, Color.CYAN}) );
		colorizeMenu.add( new ColorizeItem("8 w/ Black", this, new Color[]{Color.BLACK, Color.RED, Color.BLUE, Color.GREEN, Color.MAGENTA, Color.ORANGE, Color.YELLOW, Color.CYAN}) );
		colorizeMenu.add( new ColorizeItem("Only Black", this, new Color[]{Color.BLACK}) );
		getMenuBar().add(fileMenu);
		getMenuBar().add(startMenu);
		getMenuBar().add(stopMenu);
		getMenuBar().add(speedMenu);
		getMenuBar().add(colorMenu);

		startItem.addActionListener( new AL(this) {
			public void actionPerformed(ActionEvent e) {
				if( !cgol.game.isAlive() )
					game.start();
			}
		});
		stopItem.addActionListener( new AL(this) {
			public void actionPerformed(ActionEvent e) {
				if( !cgol.game.isAlive() )
					return;
				game.interrupt();
				game = new Thread(cgol);
			}
		});
		clearItem.addActionListener( new AL(this) {
			public void actionPerformed(ActionEvent e) {
				if( cgol.game.isAlive() )
					return;
				for( int x = 0; x < cgol.getBoxes().length; x++ )
					for( int y = 0; y < cgol.getBoxes()[0].length; y++ )
						cgol.getBoxAt(x, y).setTurf(Color.WHITE);
			}
		});
		saveItem.addActionListener( new AL(this) {
			public void actionPerformed(ActionEvent e) {
				if( cgol.game.isAlive() )
					return;
				File file = new File("default.jgs");
				for( int i = 0; file.isFile(); i++ ) {
					file = new File("default" + i + ".jgs");
				}
				try {
					cgol.save( file.getPath() );
					JOptionPane.showMessageDialog(cgol, "Saved!\n" + file.getName(), "Save", JOptionPane.INFORMATION_MESSAGE);
				} catch(Exception f) {
					f.printStackTrace();
					JOptionPane.showMessageDialog(cgol, "Save Failed!\n" + f, "Save", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		loadItem.addActionListener( new AL(this) {
			public void actionPerformed(ActionEvent e) {
				if( cgol.game.isAlive() )
					return;
				final JFrame popup = new JFrame("Load");
				final JFileChooser file = new JFileChooser("./");
				popup.getContentPane().add(file);
				file.addActionListener( new AL(cgol) {
					public void actionPerformed(ActionEvent e) {
						if( e.getActionCommand().equals("ApproveSelection") ) {
							cgol.dispose();
							try {
								ConwaysGameOfLife.load( file.getSelectedFile().getPath(),
									cgol.getBoxAt(0, 0).getWidth() * cgol.getBoxes().length,
									cgol.getBoxAt(0, 0).getHeight() * cgol.getBoxes()[0].length,
									cgol.birthNumbers, cgol.liveNumbers, cgol.speed );
							} catch(IOException f) {
								f.printStackTrace();
								JOptionPane.showMessageDialog(cgol, "Save Failed!\n" + f, "Save", JOptionPane.ERROR_MESSAGE);
							}
						}
						popup.dispose();
					}
				});
				popup.pack();
				popup.validate();
				popup.setVisible(true);
			}
		});
		advanceItem.addActionListener( new AL(this) {
			public void actionPerformed(ActionEvent e) {
				if( !cgol.game.isAlive() )
					cgol.move();
			}
		});

		autoSetTitle();
		pack();
		setVisible(true);
		validate();
	}

	public ConwaysGameOfLife(int x, int y) {
		this(x, y, 800, 600, null, null, 250);
	}

	public void run() {
		while(true) {
			move();
			try {
				Thread.sleep(speed);
			} catch(InterruptedException e) { break; }
		}
	}

	public static ConwaysGameOfLife load(String filename, int width, int height, int[] lNums, int[] bNums, long speed) throws IOException {
		Grid g = Grid.load(filename, Grid.HIDDEN);
		g.dispose();
		ConwaysGameOfLife cgol = new ConwaysGameOfLife(g.getBoxes().length, g.getBoxes()[0].length, width, height, lNums, bNums, speed);
		for( int x = 0; x < g.getBoxes().length; x++ )
			for( int y = 0; y < g.getBoxes()[0].length; y++ )
				cgol.getBoxAt(x, y).setTurf( g.getBoxAt(x, y).getTurf() );
		return cgol;
	}

	public void move() {
		ArrayList<Change> changes = new ArrayList<Change>();

		for( int x = 0; x < getBoxes().length; x++ )
			for( int y = 0; y < getBoxes()[0].length; y++ ) {
				ArrayList<Color> neighbors = new ArrayList<Color>(8);
				for( int _x = -1; _x <= 1; _x++ )
					for( int _y = -1; _y <= 1; _y += (_x != 0 || _y != -1 ? 1 : 2) ) {
						int xCord = (x + _x + getBoxes().length) % getBoxes().length,
							yCord = (y + _y + getBoxes()[0].length) % getBoxes()[0].length;
						if( isAlive(xCord, yCord) )
							neighbors.add( getBoxAt(xCord, yCord).getTurf() );
					}
				if( isAlive(x, y) && !MH.isIn(liveNumbers, neighbors.size()) )
					changes.add( new Change(x, y, Color.WHITE) );
				else if( !isAlive(x, y) && MH.isIn(birthNumbers, neighbors.size()) ) {
					Hashtable<Color, Integer> colors = new Hashtable<Color, Integer>(8);
					for( Color c : neighbors )
						if( colors.get(c) == null )
							colors.put(c, 1);
						else
							colors.put(c, colors.get(c) + 1);
					Color majorColor = Color.BLACK;
					int colorCount = 1;
					for( Color c : colors.keySet() )
						if( colors.get(c) > colorCount ) {
							majorColor = c;
							colorCount = colors.get(c);
						} else if( colors.get(c) == colorCount )
							majorColor = Color.BLACK;
					changes.add( new Change(x, y, majorColor) );
				}
			}

		for( Change c : changes )
			if( c.color == Color.WHITE )
				kill(c.x, c.y);
			else
				res(c.x, c.y, c.color);
		changes.clear();
	}

	public boolean isAlive(Box b) {
		return !b.getTurf().equals(Color.WHITE);
	}

	public boolean isAlive(int x, int y) {
		return isAlive( getBoxAt(x, y) );
	}

	public void res(int x, int y, Color c) {
		getBoxAt(x, y).setTurf(c);
	}

	public void res(int x, int y) {
		res(x, y, Color.BLACK);
	}

	public void kill(int x, int y) {
		getBoxAt(x, y).setTurf(Color.WHITE);
	}

	public void randomize(double liveWeight, double deadWeight) {
		Random rand = new Random();
		for( int x = 0; x < getBoxes().length; x++ )
			for( int y = 0; y < getBoxes()[0].length; y++ )
				if( rand.nextDouble() * (liveWeight + deadWeight) <= liveWeight )
					res(x, y);
				else
					kill(x, y);
	}

	public void colorize(Color[] colors) {
		Random rand = new Random();
		for( Box[] bs : getBoxes() )
			for( Box b : bs )
				if( isAlive(b) )
					b.setTurf( colors[rand.nextInt(colors.length)] );
	}

	private String a2s(int[] arr) {
		String s = "";
		for( int i : arr )
			s += i;
		return s;
	}

	public void autoSetTitle(int mouseX, int mouseY) {
		setTitle(
			"Conway's Game of Life B" + a2s(birthNumbers) + "/S" + a2s(liveNumbers) + " "
			+ (game.isAlive() ? "[Running]" : "[Paused]")
			+ " (lag_time=" + speed + ", color = " + currentColor.toString().substring(14)
			+ " mouse_x=" + mouseX + ", mouse_y=" + mouseY + ")"
		);
	}

	public void autoSetTitle() {
		autoSetTitle(0, 0);
	}


	public static void main(String[] args) {
		new ConwaysGameOfLife(80, 60, 800, 600, null, null, 250);
	}

}
