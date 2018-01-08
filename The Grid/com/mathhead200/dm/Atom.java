package com.mathhead200.dm;

import java.awt.Image;
import java.util.ArrayList;
import com.mathhead200.image.ColorEquations;
import com.mathhead200.image.EquationImage;

public abstract class Atom
{
 //Member Variables
	/**
	 * This turns the object's density on or off.
	 * Two dense objects may not occupy the same space in the standard movement system.
	 */
	public boolean density = false;

	/**
	 * This is the description of the Atom.
	 */
	public String desc = "";

	/**
	 * This is the direction that the object is facing.
	 */
	private int dir = Dir.NORTH;

	/**
	 * This sets the object's gender.
	 */
	public String gender = Gender.NEUTER;

	/**
	 * This is the Image that will be used to represent the Atom.
	 */
	public Image icon = new EquationImage( 1, 1, new ColorEquations() {
		public void initCalc(double x, double y) {}
		public double red(double x, double y) { return 0; }
		public double green(double x, double y) { return 0; }
		public double blue(double x, double y) { return 0; }
		public double alpha(double x, double y) { return 0; }
	});

	/**
	 * This displaces the object's icon on the x-axis by the specified number of pixels.
	 * This effect is purely visual and does not influence such things as
	 * movement bumping or view() range calculations.
	 */
	public int pixelX = 0;

	/**
	 * This displaces the object's icon on the y-axis by the specified number of pixels.
	 * This effect is purely visual and does not influence such things as
	 * movement bumping or view() range calculations.
	 */
	public int pixelY = 0;

	World world = null;
	int x = -1;
	int y = -1;
	int z = -1;

 //Accessor Methods
	public int getDir() {
		return dir;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getZ() {
		return z;
	}

 //Mutator Mathods
	public void setDir(int dir) {
		this.dir = dir;
	}

 //Methods
	public ArrayList<Atom> getContents() {
		Square s = world.squares[x][y][z];
		ArrayList<Atom> contents = new ArrayList<Atom>( s.objs.size() + s.mobs.size() );
		contents.addAll(s.objs);
		contents.addAll(s.mobs);
		return contents;
	}
}
