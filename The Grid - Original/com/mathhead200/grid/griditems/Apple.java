package com.mathhead200.grid.griditems;

import com.mathhead200.grid.*;


@SuppressWarnings("serial")
public class Apple extends Food
{
	private static final boolean EDIBLE = true;

	public Apple(String url, String n, Box b) throws ParentBoxIsSetException {
		super(EDIBLE, url, n, b);
	}

	public Apple(String url, String n) {
		super(EDIBLE, url, n);
	}

	public Apple(String url, Box b) throws ParentBoxIsSetException {
		super(EDIBLE, url, b);
	}

	public Apple(String url) {
		super(EDIBLE, url);
	}

	public Apple() {
		super(EDIBLE);
	}
}
