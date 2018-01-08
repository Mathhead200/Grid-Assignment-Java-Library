
import com.mathhead200.grid.*;
import com.mathhead200.image.*;

public class Test2
{
	public static void main(String[] args) throws GridException {
		ColorEquations r = new ColorEquations() {
			public Double z;

			public void initCalc(double x, double y) {
				z = Math.sqrt( 85*85 - (x+7)*(x+7) - y*y );
			}

			public double alpha(double x, double y) {
				if( z.equals(Double.NaN) )
					return 0;
				return 100;
			}

			public double blue(double x, double y) {
				return z/3;
			}

			public double green(double x, double y) {
				return z/3;
			}

			public double red(double x, double y) {
				return z;
			}
		};
		ColorEquations g = new ColorEquations() {
			public Double z;

			public void initCalc(double x, double y) {
				z = Math.sqrt( 85*85 - (x+7)*(x+7) - y*y );
			}

			public double alpha(double x, double y) {
				if( z.equals(Double.NaN) )
					return 0;
				return 100;
			}

			public double blue(double x, double y) {
				return z/3;
			}

			public double green(double x, double y) {
				return z;
			}

			public double red(double x, double y) {
				return z/3;
			}
		};
		ColorEquations b = new ColorEquations() {
			public Double z;

			public void initCalc(double x, double y) {
				z = Math.sqrt( 85*85 - (x+7)*(x+7) - y*y );
			}

			public double alpha(double x, double y) {
				if( z.equals(Double.NaN) )
					return 0;
				return 100;
			}

			public double blue(double x, double y) {
				return z;
			}

			public double green(double x, double y) {
				return z/3;
			}

			public double red(double x, double y) {
				return z/3;
			}
		};

		Grid grid = new Grid(10, 10, Grid.CENTERED);
		grid.getBoxAt(2, 3).addGridItem(
			new GridItem( new EquationImage(32, 32, r), "Red Marble" ) {}
		);
		grid.getBoxAt(7, 2).addGridItem(
			new GridItem( new EquationImage(32, 32, g), "Green Marble" ) {}
		);
		grid.getBoxAt(6, 7).addGridItem(
			new GridItem( new EquationImage(32, 32, b), "Blue Marble" ) {}
		);
	}
}
