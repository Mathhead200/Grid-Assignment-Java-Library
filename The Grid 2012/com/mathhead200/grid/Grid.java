package com.mathhead200.grid;


public interface Grid<T>
{
	public void add(Coord c, T item);

	public void remove(Coord c);

	public T get(Coord c);
}
