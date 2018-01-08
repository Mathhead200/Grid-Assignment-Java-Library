package com.mathhead200.array;


public class Stack<T>
{
	private class Element
	{
		T value;
		Element next = null;

		Element(T value) { this.value = value; }
	}


	private Element first = null;

	public Stack<T> add(T value) {
		Element next = first;
		first = new Element(value);
		first.next = next;
		return this;
	}

	public T get() {
		return first.value;
	}

	public T remove() {
		if( isEmpty() )
			return null;
		T value = first.value;
		first = first.next;
		return value;
	}

	public boolean isEmpty() {
		return first == null;
	}
}
