
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import com.mathhead200.image.*;


public class MainClass
{
	public static final int WIDTH = 800, HEIGHT = 600;

	public static final ColorEquations SMILEY = new ColorEquations() {
		public Double z1, z2;
		public boolean onLine, onZ1, onZ2;
		public void initCalc(double x, double y) {
			double fx = x*x / 25 - 50,
				acc = Math.abs(y / 10);
			onLine = y - acc <= fx && fx <= y + acc;

			z1 = 100 * Math.sqrt( 1 - Math.pow((x + 25)/10, 2) - Math.pow((y - 25)/10, 2) );
			onZ1 = !z1.equals(Double.NaN);

			z2 = 100 * Math.sqrt( 1 - Math.pow((x - 25)/10, 2) - Math.pow((y - 25)/10, 2) );
			onZ2 = !z2.equals(Double.NaN);
		}
		public double blue(double x, double y) {
			if( onLine ) return Math.abs(x);
			if( onZ1 ) return 100 - z1;
			if( onZ2 ) return 100 - z2;
			return 100;
		}
		public double green(double x, double y) {
			if( onLine ) return 0;
			if( onZ1 || onZ2 ) return 50;
			return 100;
		}
		public double red(double x, double y) {
			if( onLine ) return Math.abs(y);
			if( onZ1 ) return z1;
			if( onZ2 ) return z2;
			return 100;
		}
		public double alpha(double x, double y) { return 100; }
	}, SPHERE = new ColorEquations() {
		double z;
		public void initCalc(double x, double y) { z = Math.sqrt( 10000 - x*x - y*y ); }
		public double alpha(double x, double y) { return new Double(z).equals(Double.NaN) ? 0 : 100; }
		public double blue(double x, double y) { return 100 - z; }
		public double green(double x, double y) { return 100 - z; }
		public double red(double x, double y) { return 100 - z; }
	}, BLURY = new ColorEquations() {
		public void initCalc(double x, double y) {}
		public double alpha(double x, double y) { return 100; }
		public double blue(double x, double y) { return 100 - x*x; }
		public double green(double x, double y) { return 100 - y*y; }
		public double red(double x, double y) { return 0; }
	}, BEN = new ColorEquations() {

		public void initCalc(double x, double y) {

		}

		public double alpha(double x, double y) {
			return Math.sqrt(10000 - x*x - y*y);
		}

		public double red(double x, double y) {
			if( y > -2*x )
				return 100;
			return 0;
		}

		public double blue(double x, double y) {
			if( y > 2*x )
				return 100;
			return 0;
		}

		public double green(double x, double y) {
			if( y < 0 )
				return 100;
			return 0;
		}
	}, SPIRAL = new ColorEquations() {
		private boolean onLine;
		public void initCalc(double x, double y) {
			double acc = 20;
			int minPer = 0,
				maxPer = 20;
			double theta = 180 * Math.atan2(y, x) / Math.PI;
			double r = Math.sqrt(x*x + y*y);
			double f = r * 50;
			onLine = false;
			for( int i = minPer; i <= maxPer; i++ ) {
				if( theta + i*360 - acc <= f && f <= theta + i*360 + acc ) {
					onLine = true;
					break;
				}
			}
		}
		public double alpha(double x, double y) { return 100; }
		public double blue(double x, double y) {
			if( onLine ) return 100;
			return 0;
		}
		public double green(double x, double y) {
			if( onLine ) return 100;
			return 0;
		}
		public double red(double x, double y) {
			if( onLine ) return 100;
			return 0;
		}
	}, BOOK = new ColorEquations() {
		private double z;
		public void initCalc(double x, double y) {
			z = 20 * Math.log10( Math.abs(x) );
		}
		public double alpha(double x, double y) { return 100; }
		public double red(double x, double y) { return z; }
		public double green(double x, double y) { return z; }
		public double blue(double x, double y) {return z; }
	};


	public static void main(String[] args) throws java.io.IOException {
		JFrame mainFrame = new JFrame("Java");
		mainFrame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		JPanel stuff = new JPanel( new GridBagLayout() );
		GridBagConstraints cr = new GridBagConstraints(),
			cl = new GridBagConstraints(),
			ct = new GridBagConstraints();
		cr.gridx = 0;
		cr.gridy = 1;
		cl.gridx = 1;
		cl.gridy = 1;
		ct.gridwidth = 2;
		Slideshow ssLabel = new Slideshow( null, null, 3000, 0, 0, true);
		final Thread ssThread = new Thread(ssLabel, "Slidshow Thread");
		ssLabel.setOpaque(false);
		ssLabel.getSlides().add( new EquationImage( WIDTH, HEIGHT, SMILEY ) );
		ssLabel.getSlides().add( new EquationImage( WIDTH, HEIGHT, SPHERE ) );
		ssLabel.getSlides().add( new EquationImage( WIDTH, HEIGHT, BLURY ) );
		ssLabel.getSlides().add( new EquationImage( WIDTH, HEIGHT, BEN ) );
		ssLabel.getSlides().add( new EquationImage( WIDTH, HEIGHT, SPIRAL ) );
		ssLabel.getSlides().add( new EquationImage( WIDTH, HEIGHT, -10000, 10000, 0, 1, BOOK ) );
		ssLabel.randomize();
		ssLabel.autoScale();
		ssLabel.setMinimumSize( ssLabel.getPreferredSize() );
		stuff.add(ssLabel, ct);
		JButton startButton = new JButton("Start Slideshow"),
			stopButton = new JButton("Stop Slideshow");
		startButton.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ssThread.start();
					System.out.println( ssThread.getName() + " started." );
				} catch(Exception f) {
					System.err.println( ssThread.getName() + " can't be started." );
				}
			}
		});
		stuff.add(startButton, cr);
		stopButton.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ssThread.interrupt();
					System.out.println( ssThread.getName() + " stoped." );
				} catch(Exception f) {
					System.err.println( ssThread.getName() + " can't be stoped." );
				}
			}
		});
		stuff.add(stopButton, cl);
		mainFrame.add(stuff);
		mainFrame.pack();
		Dimension screenSize = mainFrame.getToolkit().getScreenSize();
		mainFrame.setLocation(
			( screenSize.width - mainFrame.getWidth() ) / 2,
			( screenSize.height - mainFrame.getHeight() ) / 2
		);
		mainFrame.validate();
		mainFrame.setVisible(true);
	}
}
