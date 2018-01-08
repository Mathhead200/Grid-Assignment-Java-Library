package com.mathhead200.grid.griditems;

import com.mathhead200.grid.*;

@SuppressWarnings("serial")
public class ActingBug extends Bug implements GridActor
{
	public ActingBug(String url, String n, Box b) throws NullParentGridException, ParentBoxIsSetException {
		super(url, n, b);
	}

	public ActingBug(String url, String n) {
		super(url, n);
	}

	public ActingBug(String url, Box b) throws NullParentGridException, ParentBoxIsSetException {
		super(url, b);
	}

	public ActingBug(String url) {
		super(url);
	}

	public ActingBug() {
		super();
	}

	public void keyDown(int k)
	{
		ActingBug a = this;
		try {
			switch(k) {
				case 37:
					a.moveWest();
					break;
				case 38:
					a.moveNorth();
					break;
				case 39:
					a.moveEast();
					break;
				case 40:
					a.moveSouth();
					break;
				default:
					System.out.println( "press an arrow key..." );
				}
			} catch(NullParentBoxException e) {
			} catch(NullParentGridException e) {
			} catch(GridItemIsDeadException e) {
			}
	}

	public void keyUp(int k)
	{
		System.out.println( "keyCode - " + k );
	}

	public void mouseDown(Box box, int b) throws GridException {
		if( this.getParentBox().equals(box) ) {
			this.say("Hey, that tickles!");
			return;
		}
		int[] tCords = this.getParentGrid().findBoxCord(this),
			bCords = this.getParentGrid().findBoxCord(box);
		int tx = tCords[0], ty = tCords[1],
			bx = bCords[0], by = bCords[1];
		if( bx - tx == -1 && by == ty )
			this.moveWest();
		else if( bx - tx == 1 && by == ty )
			this.moveEast();
		else if( bx == tx && by - ty == -1 )
			this.moveNorth();
		else if( bx == tx && by - ty == 1 )
			this.moveSouth();
	}

	public void mouseUp(Box box, int b) throws GridException {
		System.out.println( "button code - " + b );
	}

	public void mouseIn(Box box, Dir d) throws GridException {
		if( !this.getParentBox().equals(box) )
			return;
		if( d == null ) {
			this.say("Hi mouse.");
			return;
		}
		String message = "";
		if( d == Dir.NORTH || d == Dir.NORTHEAST || d == Dir.NORTHWEST )
			message += "What's up? ";
		else if( d == Dir.SOUTH || d == Dir.SOUTHEAST || d == Dir.SOUTHWEST )
			message += "Yo down there. ";
		if( d == Dir.EAST || d == Dir.NORTHEAST || d == Dir.SOUTHEAST )
			message += "Hey, over here. ";
		else if( d == Dir.WEST || d == Dir.NORTHWEST || d == Dir.SOUTHWEST )
			message += "Whoops... Blind sided. ";
		this.say(message);
	}

	public void mouseOut(Box box, Dir d) throws GridException {
		if( this.getParentBox().equals(box) )
			this.say("Bye mouse.");
	}


	public static ActingBug load(String save) {
		Bug b = Bug.load(save);
		return new ActingBug( b.getIcon().getDescription(), b.getName() );
	}
}
