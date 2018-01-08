package com.mathhead200.grid13;


public class GridItem
{
	//the following package private fields
	// are to be modified only by the parent grid.
	// (They may be read by this object.)
	Parent parent = null;
	Coordinate loc = Coordinate.ORIGIN;
	String tag = null;

	void setParentInfo(Parent parent, Coordinate loc, String tag) {
		this.parent = parent;
		this.loc = loc;
		this.tag = tag;
	}

	void resetParentInfo() {
		setParentInfo(null, null, null);
	}


	public Parent getParent() {
		return parent;
	}

	public Coordinate getLoc() {
		return loc;
	}

	public int getX() {
		if( loc == null )
			throw new OrphanException("this item has no location");
		return loc.x;
	}

	public int getY() {
		if( loc == null )
			throw new OrphanException("this item has no location");
		return loc.y;
	}

	public String getTag() {
		return tag;
	}

	/** Removes this item from its parent. */
	public void die() {
		if( parent == null )
			throw new OrphanException();
		try {
			parent.remove(this);
		} catch(StrangerException e) {
			throw new Error(e);
		}
	}

	public void moveTo(Coordinate dest) {
		if( parent == null )
			throw new OrphanException();
		try {
			parent.move(this, dest);
		} catch(StrangerException e) {
			throw new Error(e);
		}
	}

	public void moveTo(int x, int y) {
		moveTo( new Coordinate(x, y) );
	}

	public void moveBy(int x, int y) {
		moveTo( x + loc.x, y + loc.y );
	}

	/**
	 * Invoked when another {@link GridItem} tries to move,
	 * 	but this item is in the way. Note that if this method is invoked,
	 * 	the normal movement of <code>item</code> was not carried out.
	 *  <code>item</code> is still in the same location it was before
	 *  it tried to move. If this method is overridden to simply
	 *  <code>return true</code>, then the movement will simply fail.
	 *  If you wish to override this method to provide some alternative movement,
	 *  remember to remove <code>item</code> or <code>this</code> from
	 *  its parent by invoking {@link #die()}.
	 *
	 * @param bumper - The other (moving) item
	 * @return <code>true</code> if the situation has been resolved;
	 * 	no exception should be thrown. <code>false</code> if
	 * 	the situation has not been resolved; throw an {@link OccupedException}.
	 */
	public boolean bumped(GridItem item) {
		return false;
	}
}
