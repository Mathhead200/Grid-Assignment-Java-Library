package com.mathhead200.array;


public class Queue<T>
{
	private class Element
	{
		T value;
		Element next = null;
		Element before = null;

		Element(T value) { this.value = value; }

		void setNext(Element next) {
			this.next = next;
			if( next != null )
				next.before = this;
		}
		void setBefore(Element before) {
			this.before = before;
			if( before != null )
				before.next = this;
		}
	}


	private Element first = null, last = null;

	public Queue<T> add(T value) {
		Element before = last;
		last = new Element(value);
		last.setBefore(before);
		if( first == null )
			first = last;
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
