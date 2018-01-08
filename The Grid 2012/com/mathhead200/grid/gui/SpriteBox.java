package com.mathhead200.grid.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class SpriteBox<T extends Sprite & Comparable<T>> implements Sprite, Collection<T>
{
	private List<T> sprites = new ArrayList<T>(1);
	private boolean ordered = true;

	public Image getImage() {
		int maxWidth = 0, maxHeight = 0;
		for( Sprite sprite : this ) {
			Image img = sprite.getImage();
			int width = img.getWidth(null),
			    height = img.getHeight(null);
			if( width > maxWidth )
				maxWidth = width;
			if( height > maxHeight )
				maxHeight = height;
		}
		BufferedImage img = new BufferedImage(maxWidth, maxHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = img.createGraphics();
		g.setColor( new Color(0, 0, 0, 0) );
		g.fillRect( 0, 0, img.getWidth(), img.getHeight() );
		for( Sprite sprite : this )
			g.drawImage(sprite.getImage(), 0, 0, null);
		return img;
	}

	public Iterator<T> iterator() {
		return new Iterator<T>() {
			int index = 0;
			boolean hasRemoved = false;

			public boolean hasNext() {
				return (index + 1) < sprites.size();
			}

			public T next() {
				if( !hasRemoved )
					index++;
				return sprites.get(index);
			}

			public void remove() {
				sprites.remove(index);
				hasRemoved = true;
			}
		};
	}

	/**
	 * Inserts the <code>item</code> in its "correct" place so that that the box remains sorted.
	 * @param item - The item to add to the box
	 * @return <code>true</code>, (unless something went horribly wrong!)
	 */
	public boolean add(T item) {
		for( int i = 0; i < sprites.size(); i++ )
			if( sprites.get(i).compareTo(item) >= 0 ) {
				sprites.add(i, item);
				return true;
			}
		return sprites.add(item);
	}

	/**
	 * Simply calls {@link #add(T)} for each element in <code>c</code>
	 * @param c - A collection of elements to add
	 * @return <code>true</code>, (unless something went horribly wrong!)
	 */
	public boolean addAll(Collection<? extends T> c) {
		boolean r = false;
		for( T item : c )
			r = add(item) || r;
		return r;
	}

	@SuppressWarnings("unchecked")
	public boolean remove(Object item) throws ClassCastException {
		if( !ordered )
			return sprites.remove(item);
		int index = Collections.binarySearch( sprites, (T)item );
		if( index < 0 )
			return false;
		sprites.remove(index);
		return true;
	}

	public boolean removeAll(Collection<?> c) {
		boolean r = false;
		for( Object item : c )
			r = remove(item) || r;
		return r;
	}

	public boolean retainAll(Collection<?> c) {
		return sprites.retainAll(c);
	}

	public void clear() {
		sprites.clear();
	}

	/**
	 * Removes the last item from the box.
	 * @see #push(T)
	 * @return The removed item, or <code>null</code> if the box was empty.
	 */
	public T pop() {
		if( sprites.isEmpty() )
			return null;
		return sprites.remove( sprites.size() - 1 );
	}

	/**
	 * Adds an item to the end of the box.
	 * 	<i>This invalidates the box's natural sorted order!</i>
	 * @see #pop()
	 * @param item - The new last item in the box
	 */
	public void push(T item) {
		sprites.add(item);
		ordered = false;
	}

	/**
	 * Removes the first item from the box.
	 * @see #unshift(T)
	 * @return The removed item, or <code>null</code> if the box was empty.
	 */
	public T shift() {
		if( sprites.isEmpty() )
			return null;
		return sprites.remove(0);
	}

	/**
	 * Adds an item to the beginning of the box.
	 *  <i>This invalidates the box's natural sorted order!</i>
	 * @see #shift()
	 * @param item - The new first item in the box
	 */
	public void unshift(T item) {
		sprites.add(0, item);
		ordered = false;
	}

	/**
	 * Reorders the items in the box so that they are (again) in their natural order.
	 * 	This allows many operations to run more efficiently.
	 */
	public void sort() {
		Collections.sort(sprites);
	}

	public boolean isEmpty() {
		return sprites.isEmpty();
	}

	@SuppressWarnings("unchecked")
	public boolean contains(Object item) throws ClassCastException {
		if( !ordered )
			return sprites.contains(item);
		return Collections.binarySearch(sprites, (T)item) >= 0;
	}

	public boolean containsAll(Collection<?> c) {
		for( Object item : c )
			if( !contains(item) )
				return false;
		return true;
	}

	public int size() {
		return sprites.size();
	}

	public Object[] toArray() {
		return sprites.toArray();
	}

	public <S> S[] toArray(S[] a) {
		return sprites.toArray(a);
	}
}
