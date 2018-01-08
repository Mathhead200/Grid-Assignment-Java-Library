package com.mathhead200.grid13;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;




/**
 * New, unchecked...
 *
 * @author Christopher D'Angelo
 * @version Mar 14, 2013
 */
public class Box extends GridItem implements Parent
{
	private HashSet<GridItem> items = new HashSet<GridItem>(4);
	private HashMap<String, GridItem> itemsByTag = new HashMap<String, GridItem>(4);


	public Box() {
	}


	public void add(GridItem item, String tag) {
		if( item == null )
			throw new NullPointerException();
		synchronized(this) {
			if( item.parent != null )
				throw new BilocationException("the item already has a parent");
			if( tag != null ) {
				if( itemsByTag.containsKey(tag) )
					throw new DuplicateTagException("the tag \"" + tag + "\" is already in use");
				itemsByTag.put(tag, item);
			}
			items.add(item);
			item.setParentInfo(this, getLoc(), tag);
		}
	}

	public void add(GridItem item) {
		add(item, null);
	}

	public synchronized void add(GridItem... items) {
		for( GridItem item : items )
			add(item);
	}

	public synchronized GridItem getByTag(String tag) {
		return itemsByTag.get(tag);
	}

	public synchronized void remove(GridItem item) {
		if( parent != this )
			throw new StrangerException("the item is not contained in the box");
		items.remove(item);
		itemsByTag.remove(item.tag);
	}

	public synchronized GridItem removeByTag(String tag) {
		GridItem item = itemsByTag.remove(tag);
		if( item != null ) {
			items.remove(item);
			item.resetParentInfo();
		}
		return item;
	}

	public void move(GridItem item, Coordinate dest) {
		if( item.parent == null )
			throw new NullPointerException();
		synchronized(this) {
			if( item.parent != this )
				throw new StrangerException("the item is not contained int the box");
			if( getParent() == null )
				throw new OrphanException("the box does not have a parent; can't propagate the move");
			getParent().move(item, dest);
		}
	}

	public synchronized Set<GridItem> children() {
		HashSet<GridItem> copy = new HashSet<GridItem>( (int)Math.ceil(items.size() / .75) );
		for( GridItem item : items )
			copy.add(item);
		return copy;
	}
	
	public boolean bumped(GridItem item) {
		String tag = item.getTag();
		item.die();
		add(item);
	}
}
