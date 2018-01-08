package com.mathhead200.grid.griditems;

import com.mathhead200.grid.*;


@SuppressWarnings("serial")
public class PoisonApple extends Food
{
	private static final boolean EDIBLE = false;

	public PoisonApple(String url, String n, Box b) throws ParentBoxIsSetException {
		super(EDIBLE, url, n, b);
	}

	public PoisonApple(String url, String n) {
		super(EDIBLE, url, n);
	}

	public PoisonApple(String url, Box b) throws ParentBoxIsSetException {
		super(EDIBLE, url, b);
	}

	public PoisonApple(String url) {
		super(EDIBLE, url);
	}

	public PoisonApple() {
		super(EDIBLE);
	}
}
