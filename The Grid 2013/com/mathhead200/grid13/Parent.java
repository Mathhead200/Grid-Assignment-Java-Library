package com.mathhead200.grid13;

import java.util.Set;


/**
 * Anything that can hold a {@link GridItem}.
 * Acts as a facilitator for its children's movement.
 *
 * @author Christopher D'Angelo
 * @version Mar 14, 2013
 */
public interface Parent
{
	/**
	 * Relinquishes control of the child.
	 * @param item - The child to remove
	 * @throws GridException - If <code>item</code> is not a child of this parent,
	 * 	or if <code>item</code> can not be removed.
	 */
	public abstract void remove(GridItem item);

	/**
	 * Used to change a child's location.
	 * @param dest - The child's destination
	 * @throws GridException - If <code>item</code> is not a child of this parent,
	 * 	or if <code>item</code> can not be moved to <code>dest</code>.
	 */
	public abstract void move(GridItem item, Coordinate dest);

	/**
	 * Used to iterate or search through this {@link Parent} container.
	 * 	The returned {@link Set} will contain references to the items,
	 * 	so attempting to remove from or add to it will not change the contents of this {@link Parent},
	 *  and may or may not throw an {@link UnsupportedOperationException}.
	 * @return The set of all {@link GridItem} objects, such that this is their parent.
	 */
	public abstract Set<GridItem> children();
}
