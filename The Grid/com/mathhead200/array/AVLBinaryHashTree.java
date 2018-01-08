package com.mathhead200.array;


public class AVLBinaryHashTree<K, T> extends BinaryHashTree<K, T>
{
	private void balanceAVL(Leaf leaf) {
		if( leaf.left != null && leaf.right != null )
			return;
		Leaf root, left, right;
		if( leaf.left != null ) {
			if( leaf.left.left != null ) {
				root = leaf.left;
				left = leaf.left.left;
			} else { //leaf.left.right != null
				root = leaf.left.right;
				left = leaf.left;
			}
			right = leaf;
		} else { //leaf.right != null
			if( leaf.right.left != null ) {
				root = leaf.right.left;
				right = leaf.right;
			} else { //leaf.right.right != null
				root = leaf.right;
				right = leaf.right.right;
			}
			left = leaf;
		}
		if( leaf.isLeft() )
			leaf.parent.left = root;
		else if( leaf.isRight() )
			leaf.parent.right = root;
		root.parent = leaf.parent;
		root.left = left;
		root.right = right;
		left.parent = root;
		right.parent = root;
		left.left = null;
		left.right = null;
		right.left = null;
		right.right = null;
	}

	/**
	 * AVL Trees are self-balancing,
	 *  calling this method will do nothing
	 * @deprecated
	 */
	@Deprecated
	public void balance() {}

	@SuppressWarnings("finally")
	public T put(K key, T value) {
		T val = super.put(key, value);
		try {
			balanceAVL( getLeaf(key).parent.parent );
		} catch(NullPointerException e) {
		} finally {
			return val;
		}
	}

	@SuppressWarnings({ "finally", "unchecked" })
	public T remove(Object key) throws ClassCastException {
		T val = super.remove(key);
		try {
			balanceAVL( getLeaf((K)key).parent.parent );
		} catch(NullPointerException e) {
		} finally {
			return val;
		}
	}
}
