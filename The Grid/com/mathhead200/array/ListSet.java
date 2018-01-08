package com.mathhead200.array;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;


public class ListSet<T> extends ArrayList<T> implements Set<T>
{
	private static final long serialVersionUID = -6441635357912509652L;

	ListSet(int initialCapacity) {
		super(initialCapacity);
	}

	ListSet() {
		super();
	}

	public ListSet(Collection<? extends T> c) {
		super(c);
	}
}
