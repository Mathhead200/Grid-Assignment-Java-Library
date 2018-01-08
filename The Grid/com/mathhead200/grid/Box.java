package com.mathhead200.grid;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;


/**
 * A container for a single GridItem. One Grid can be made up of multiple Box Objects
 * @author Christopher D'Angelo
 * @author JBD Computers &trade;
 * @version 2.0
 */
@SuppressWarnings("serial")
public class Box extends JLabel
{
	static final Color DEFAULT_TURF = new Color(0xffffff);
	static final Border DEFAULT_BORDER = new LineBorder(new Color(0x000000), 1, false);
	private Grid parentGrid;
	private Color turf;
	private GridItem gridItem;

	/**
	 * creates a box for adding grid items
	 * @param width
	 * @param height
	 * @param t - background color
	 * @param border
	 */
	public Box(int width, int height, Color t, Border border) {
		turf = t;

		this.setMinimumSize(new Dimension(width, height) );
		this.setPreferredSize( new Dimension(width, height) );
		this.setBorder(border);
		this.setBackground(t);
		this.setOpaque(true);

		BoxEventHandler boxHandler = new BoxEventHandler(this);
		this.addMouseListener(boxHandler);

		this.setVerticalAlignment( JLabel.CENTER );
		this.setHorizontalAlignment( JLabel.CENTER );
	}

	/**
	 * synonymous with <b>new Box( width, height, t, new LineBorder(new Color(0x000000), 1, false) );</b>
	 * @param width
	 * @param height
	 * @param t - background color
	 * @see #Box(int, int, Color, Border)
	 */
	public Box(int width, int height, Color t) {
		this(width, height, t, DEFAULT_BORDER);
	}

	/**
	 * synonymous with <b>new Box( size, size, t );</b>
	 * @param size - width and height
	 * @param t - background color
	 * @see #Box(int, int, Color)
	 */
	public Box(int size, Color t) {
		this(size, size, t);
	}

	/**
	 * synonymous with <b>new Box(32, t);</b>
	 * @param t
	 * @see #Box(int, Color)
	 */
	public Box(Color t) {
		this(32, t);
	}

	/**
	 * synonymous with - <b>{ <br />
	 * &nbsp; Box b = new Box( width, height, t ); <br />
	 * &nbsp; b.addGridItem( item ); <br />
	 * }</b>
	 * @param width
	 * @param height
	 * @param t - background color
	 * @param item
	 * @throws ParentBoxIsSetException
	 * @throws GridItemIsDeadException
	 * @see #Box(int, int, Color)
	 * @see Box#addGridItem(GridItem)
	 */
	public Box(int width, int height, Color t, GridItem item) throws ParentBoxIsSetException, GridItemIsDeadException {
		this(width, height, t);
		addGridItem(item);
	}

	/**
	 * synonymous with - <b>new Box( size, size, t, item );</b>
	 * @param size
	 * @param t - background color
	 * @param item
	 * @throws ParentBoxIsSetException
	 * @throws GridItemIsDeadException
	 */
	public Box(int size, Color t, GridItem item) throws ParentBoxIsSetException, GridItemIsDeadException {
		this(size, size, t, item);
	}

	/**
	 * synonymous with - <b>{ <br />
	 * &nbsp; Box b = new Box( t ); <br />
	 * &nbsp; b.addGridItem( item ); <br />
	 * }</b>
	 * @param t - background color
	 * @param item - item to be added to the box
	 * @throws ParentBoxIsSetException - see <b>addGridItem( GridItem )</b>
	 * @throws GridItemIsDeadException - see <b>addGridItem( GridItem )</b>
	 * @see #Box(Color)
	 * @see #addGridItem(GridItem)
	 */
	public Box(Color t, GridItem item) throws ParentBoxIsSetException, GridItemIsDeadException {
		this(32, t, item);
	}

	/**
	 * synonymous with - <b>new Box( new java.awt.Color(0xffffff), item );</b> -
	 * setting the new box's background color to white
	 * @param item - item to be added to the box
	 * @throws ParentBoxIsSetException - see <b>addGridItem( GridItem )</b>
	 * @throws GridItemIsDeadException - see <b>addGridItem( GridItem )</b>
	 * @see #Box(Color, GridItem)
	 */
	public Box(GridItem item) throws ParentBoxIsSetException, GridItemIsDeadException {
		this( DEFAULT_TURF, item );
	}


	/**
	 * synonymous with - <b>new Box( width, height, new java.awt.Color(0xffffff) );</b>
	 * @param width
	 * @param height
	 * @see #Box(int, int, Color)
	 */
	public Box(int width, int height) {
		this( width, height, DEFAULT_TURF );
	}

	/**
	 * synonymous with - <b>new Box( size, size );</b>
	 * @param size
	 * @see #Box(int, int)
	 */
	public Box(int size) {
		this(size, size);
	}

	/**
	 * synonymous with - <b>new Box( new java.awt.Color(0xffffff) )</b> -
	 * setting the new Box's background color to white
	 * @see #Box(Color)
	 */
	public Box() {
		this( DEFAULT_TURF );
	}


	public String toString() {
		return gridItem != null ? "[" + gridItem + "]" : "()";
	}


	/**
	 * @return the grid that this box belongs to
	 */
	public Grid getParentGrid()
	{
		return this.parentGrid;
	}

	/**
	 * @return the background color of this box
	 */
	public Color getTurf() {
		return this.turf;
	}

	/**
	 * @return the GridItem found in this box, or null if empty
	 */
	public GridItem getGridItem() {
		return this.gridItem;
	}

	/**
	 * @return the coordinates of this box in the form of {x, y} on its parent grid,
	 * or null if this Box dosn't belong to a grid
	 */
	public int[] getBoxCord() {
		if( this.getParentGrid() == null ) {
			return null; }
		return this.getParentGrid().findBoxCord( this );
	}


	void setParentGrid(Grid g) {
		this.parentGrid = g;
	}

	void setGridItem(GridItem item) throws GridItemIsDeadException
	{
		if( item == null ) {
			this.setIcon( new ImageIcon() );
			this.setToolTipText(null);
			this.gridItem = null;
			this.validate();
			return;
		}

		if( !item.isAlive() ) {
			throw new GridItemIsDeadException();
		}

		if( this.gridItem != null ) {
			this.gridItem.setParentBox(null);
		}

		//if()... Removes from old parentBox the item
		if( item.getParentBox() != null ) {
			item.getParentBox().setGridItem(null);
		}
		item.setParentBox(this);
		this.setIcon(item.getIcon());
		this.setToolTipText( item.getName() );
		this.gridItem = item;
		this.validate();
	}

	/**
	 * adds an item to the box
	 * @param item - item to add to this box
	 * @throws ParentBoxIsSetException - if item already belongs to a box
	 * @throws GridItemIsDeadException - if item has invoked the kill() method
	 */
	public void addGridItem(GridItem item) throws ParentBoxIsSetException, GridItemIsDeadException
	{
		if( item == null ) {
			setGridItem(null);
			return;
		}

		if( item.getParentBox() != null ) {
			throw new ParentBoxIsSetException(); }
		setGridItem(item);
	}

	/**
	 * sets a new background color for this box
	 * @param t - background color
	 */
	public void setTurf(Color t) {
		this.turf = t;
		this.setBackground(t);
		this.validate();
	}

	/**
	 * called automatically when a mouse button is presses down,
	 * causes all GridActors in the parent Grid to invoke - <b>mouseDown( box, b )</b>
	 * @param b - button code
	 * @throws GridException
	 */
	protected void mouseWasPressed(int b) throws GridException {
		if( this.parentGrid == null )
			return;
		GridActor[] actors = this.parentGrid.findGridActors();
		for( GridActor a : actors  ) {
			a.mouseDown(this, b);
		}
	}

	/**
	 * called automatically when a mouse button is released,
	 * causes all GridActors in the parent Grid to invoke - <b>mouseUp( box, b )</b>
	 * @param b - button code
	 * @throws GridException
	 */
	protected void mouseWasReleased(int b) throws GridException {
		if( this.parentGrid == null )
			return;
		GridActor[] actors = this.parentGrid.findGridActors();
		for( GridActor a : actors  ) {
			a.mouseUp(this, b);
		}
	}

	/**
	 * called automatically when the mouse enters the box,
	 * causes all GridActors in the parent Grid to invoke - <b>mouseIn( box, d )</b>
	 * @param b - button code
	 * @throws GridException
	 */
	protected void mouseWasEntered(Dir d) throws GridException {
		if( this.parentGrid == null )
			return;
		GridActor[] actors = this.parentGrid.findGridActors();
		for( GridActor a : actors  ) {
			a.mouseIn(this, d);
		}
	}

	/**
	 * called automatically when the mouse exits the box,
	 * causes all GridActors in the parent Grid to invoke - <b>mouseOut( box, d )</b>
	 * @param b - button code
	 * @throws GridException
	 */
	protected void mouseWasExited(Dir d) throws GridException {
		if( this.parentGrid == null )
			return;
		GridActor[] actors = this.parentGrid.findGridActors();
		for( GridActor a : actors  ) {
			a.mouseOut(this, d);
		}
	}
}
