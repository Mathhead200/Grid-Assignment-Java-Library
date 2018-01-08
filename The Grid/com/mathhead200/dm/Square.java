package com.mathhead200.dm;

import java.util.ArrayList;


class Square
{
	Area area;
	Turf turf;
	ArrayList<Obj> objs = new ArrayList<Obj>(0);
	ArrayList<Mob> mobs = new ArrayList<Mob>(1);

	Square(Area area, Turf turf) {
		this.area = area;
		this.turf = turf;
	}
}
