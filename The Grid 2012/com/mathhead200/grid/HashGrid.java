package com.mathhead200.grid;

import java.util.HashMap;
import java.util.Map;


/**
 * @author Christopher D'Angelo
 * @version 0.0
 */
public class HashGrid<T> implements Grid<T>
{
	private Map<Coord, T> items = new HashMap<Coord, T>();

	public void add(Coord c, T item) {
		items.put(c, item);
	}

	public void add(int x, int y, T item) {
		add( new Coord(x, y), item );
	}

	public void remove(Coord c) {
		items.remove(c);
	}

	public void remove(int x, int y) {
		remove( new Coord(x, y) );
	}

	public T get(Coord c) {
		return items.get(c);
	}

	public T get(int x, int y) {
		return get( new Coord(x, y) );
	}
}
