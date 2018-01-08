package com.mathhead200.grid13;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


/**
 * Holds a 2D Grid (i.e. matrix) of items, of virtually limitless size for
 * 	management and display. <br>
 * 	<br>
 * Is synchronized for access from multiple threads.
 *
 * @author Christopher D'Angelo
 * @version 2/25/2013
 */
@SuppressWarnings("serial")
public class Grid implements Parent
{
	private HashMap<Coordinate, GridItem> itemsAt = new HashMap<Coordinate, GridItem>(128);
	private HashMap<String, GridItem> itemsByTag = new HashMap<String, GridItem>();


	/**
	 * Adds an item to this {@link Grid} at the given location.
	 *
	 * @param item - the item being added to this grid
	 * @param loc - the (initial) location of the item
	 * @param tag - an optional tag by which to retrieve this item later;
	 * 	can be <code>null</code>, meaning no tag
	 *
	 * @throws NullPointerException - if <code>item == null</code>
	 * @throws GridException - if the item can't be added to the grid
	 */
	public void add(GridItem item, Coordinate loc, String tag) {
		//check that the item is non-null
		if( item == null )
			throw new NullPointerException();
		synchronized(this) {
			//make sure the item is not already on a grid
			if( item.parent != null )
				throw new BilocationException("the item already has a parent");
			//check that the destination is unoccupied
			if( itemsAt.containsKey(loc) ) {
				GridItem obstacle = itemsAt.get(loc);
				if( obstacle.bumped(item) )
					return;
				throw new OccupiedException("location " + loc + " is occupied");
			}
			//check that the tag is unique (if non-null)
			if( tag != null ) {
				if( itemsByTag.containsKey(tag) )
					throw new DuplicateTagException("the tag \"" + tag + "\" is already in use");
				itemsByTag.put(tag, item);
			}
			itemsAt.put(loc, item);
			item.setParentInfo(this, loc, tag);
		}
	}

	/**
	 * Adds an item to this {@link Grid} at the given location.
	 * @see #add(GridItem, Coordinate, String)
	 */
	public void add(GridItem item, int x, int y, String tag) {
		add( item, new Coordinate(x, y), tag );
	}

	/**
	 * Adds an item to this {@link Grid} at the given location.
	 * @see #add(GridItem, Coordinate, String)
	 */
	public void add(GridItem item, Coordinate loc) {
		add(item, loc, null);
	}

	/**
	 * Adds an item to this {@link Grid} at the given location.
	 * @see #add(GridItem, Coordinate)
	 */
	public void add(GridItem item, int x, int y) {
		add( item, new Coordinate(x, y) );
	}

	/**
	 * Retrieves the item found at the given location.
	 *
	 * @param loc - the location to check
	 *
	 * @return the item found at location <code>(x,y)</code>,
	 * 	or <code>null</code> if no item is found there
	 */
	public synchronized GridItem getAt(Coordinate loc) {
		return itemsAt.get(loc);
	}

	/**
	 * Retrieves the item found at the given location.
	 * @see #getAt(Coordinate)
	 */
	public GridItem getAt(int x, int y) {
		return getAt( new Coordinate(x, y) );
	}

	/**
	 * Retrieves the item with the given tag.
	 *
	 * @param tag - a string used to "tag" the item when it was added to the grid
	 *
	 * @return the item with the given tag,
	 * 	or <code>null</code> if no item was added with that tag
	 */
	public synchronized GridItem getByTag(String tag) {
		return itemsByTag.get(tag);
	}

	/**
	 * Remove the given item from the grid.
	 *
	 * @param item - the item to remove
	 *
	 * @throws GridException - if the removal failed,
	 * 	and the {@link Grid} was left unchanged
	 */
	public synchronized void remove(GridItem item) {
		if( item.parent != this )
			throw new StrangerException("the item is not contained on the grid");
		itemsAt.remove(item.loc);
		itemsByTag.remove(item.tag);
		item.resetParentInfo();
	}

	/**
	 * Remove the item at the given location from the grid.
	 *
	 * @param loc - the location to check
	 *
	 * @return the {@link GridItem} removed, or <code>null</code>
	 * 	if no item was found at that location
	 */
	public synchronized GridItem removeAt(Coordinate loc) {
		GridItem item = itemsAt.remove(loc);
		if( item != null ) {
			itemsByTag.remove(item.tag);
			item.resetParentInfo();
		}
		return item;
	}

	/**
	 * Remove the item at the given location from the grid.
	 * @see #removeAt(Coordinate)
	 */
	public GridItem removeAt(int x, int y) {
		return removeAt( new Coordinate(x, y) );
	}

	/**
	 * Removes the item with the given tag from the grid.
	 *
	 * @param tag - the tag the item was added to the grid with
	 *
	 * @return the {@link GridItem} removed, or <code>null</code>
	 * 	if no item has the given tag on this grid
	 */
	public synchronized GridItem removeByTag(String tag) {
		GridItem item = itemsByTag.remove(tag);
		if( item != null ) {
			itemsAt.remove(item.loc);
			item.resetParentInfo();
		}
		return item;
	}

	public void move(GridItem item, Coordinate dest) {
		if( item == null )
			throw new NullPointerException();
		synchronized(this) {
			if( item.parent != this )
				throw new StrangerException("the item is not contained on the grid");
			if( itemsAt.containsKey(dest) ) {
				GridItem obstacle = itemsAt.get(dest);
				if( obstacle.bumped(item) )
					return;
				throw new OccupiedException("destination " + dest + " is occupied");
			}
			itemsAt.remove(item.loc);
			itemsAt.put(dest, item);
		}
	}

	public synchronized void moveFrom(Coordinate src, Coordinate dest) {
		GridItem item = itemsAt.get(src);
		if( item == null )
			throw new AbsentException("no item was found at the source location " + src);
		itemsAt.remove(src);
		itemsAt.put(dest, item);
	}

	public Set<GridItem> children() {
		HashSet<GridItem> copy = new HashSet<GridItem>( (int)Math.ceil(itemsAt.size() / .75) );
		for( GridItem item : itemsAt.values() )
			copy.add(item);
		return copy;
	}
}
