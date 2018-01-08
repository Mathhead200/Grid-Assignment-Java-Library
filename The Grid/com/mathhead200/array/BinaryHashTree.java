package com.mathhead200.array;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BinaryHashTree<K, T> implements Map<K, T>
{
	protected class Pair implements Map.Entry<K, T> {
		private final K key;
		private T value;

		public Pair(K key, T value) {
			this.key = key;
			this.value = value;
		}
		public Pair(K key) {
			this(key, null);
		}
		public boolean equals(Object o) {
			return o instanceof BinaryHashTree.Pair && ((Pair)o).key.equals(key);
		}
		public int hashCode() {
			return key.hashCode();
		}
		public String toString() {
			return key + "=>" + value;
		}
		public K getKey() {
			return key;
		}
		public T getValue() {
			return value;
		}
		public T setValue(T value) {
			T oldValue = this.value;
			this.value = value;
			return oldValue;
		}
	}

	/**
	 * a node in the tree
	 */
	protected class Leaf {
		public int code;
		public List<Pair> bucket = new ArrayList<Pair>(1);
		public Leaf left = null,
			right = null,
			parent = null;

		public Leaf(Leaf parent) {
			this.parent = parent;
		}
		public int size() {
			int size = bucket.size();
			if( left != null )
				size += left.size();
			if( right != null )
				size += right.size();
			return size;
		}
		public int height() {
			return 1 + Math.max( left != null ? left.height() : 0, right != null ? right.height() : 0 );
		}
		public boolean isLeft() {
			return parent == null || parent.left == this;
		}
		public boolean isRight() {
			return parent == null || parent.right == this;
		}
	}

	protected Leaf root = null;

	protected Leaf getLeaf(int code) {
		Leaf leaf = root;
		while( leaf != null ) {
			if( code == leaf.code )
				break;
			else if( code < leaf.code )
				leaf = leaf.left;
			else
				leaf = leaf.right;
		}
		return leaf;
	}

	protected Leaf getLeaf(K key) {
		return getLeaf( key.hashCode() );
	}

	protected Set<Map.Entry<K, T>> entrySet(Leaf leaf) {
		ListSet<Map.Entry<K, T>> set = new ListSet<Map.Entry<K, T>>( leaf.size() );
		set.addAll(leaf.bucket);
		if( leaf.left != null )
			set.addAll( entrySet(leaf.left) );
		if( leaf.right != null )
			set.addAll( entrySet(leaf.right) );
		return set;
	}

	protected Set<K> keySet(Leaf leaf) {
		ListSet<K> set = new ListSet<K>( leaf.size() );
		for( Pair p : leaf.bucket )
			set.add( p.getKey() );
		if( leaf.left != null )
			set.addAll( keySet(leaf.left) );
		if( leaf.right != null )
			set.addAll( keySet(leaf.right) );
		return set;
	}


	/**
	 * Optimizes search time by
	 * first removing all elements in the set, then sort them, and finally repopulates the set
	 */
	public void balance() {
		ListSet<Map.Entry<K, T>> set = new ListSet<Map.Entry<K, T>>( entrySet() );
		clear();
		//sort set
		for( int pos = 1; pos < set.size(); pos++ ) {
			int start = -1, end = pos;
			while( end - start > 1 ) {
				int mid = (start + end) / 2;
				Map.Entry<K, T> ele = set.get(pos),
					check = set.get(mid);
				if( ele.getKey().hashCode() == check.getKey().hashCode() ) {
					start = mid;
					break;
				} else if( ele.getKey().hashCode() < check.getKey().hashCode() )
					end = mid;
				else
					start = mid;
			}
			set.add( start + 1, set.remove(pos) );
		}
		//repopulate (a balanced) set
		for( int d = 2; d < set.size(); d *= 2 ) {
			for( int i = 0; i < d / 2; i++ ) {
				Map.Entry<K, T> ele = set.remove( (2 * i + 1) * set.size() / d - i );
				put( ele.getKey(), ele.getValue() );
			}
		}
		while( !set.isEmpty() ) {
			Map.Entry<K, T> ele = set.remove(0);
			put( ele.getKey(), ele.getValue() );
		}
	}

	public String toString() {
		return entrySet().toString();
	}

	public void clear() {
		root = null;
	}

	@SuppressWarnings("unchecked")
	public boolean containsKey(Object key) throws ClassCastException {
		K k = (K)key;
		Leaf leaf = getLeaf(k);
		return leaf.bucket.indexOf(k) >= 0;
	}

	public boolean containsValue(Object value) {
		for( Entry<K, T> entry : entrySet() )
			if( entry.getValue().equals(value) )
				return true;
		return false;
	}

	@SuppressWarnings("unchecked")
	public Set<Map.Entry<K, T>> entrySet() {
		return root == null ? new ListSet(0) : entrySet(root);
	}

	@SuppressWarnings("unchecked")
	public T get(Object key) throws ClassCastException {
		K k = (K)key;
		Leaf leaf = getLeaf(k);
		if( leaf == null )
			return null;
		return leaf.bucket.get( leaf.bucket.indexOf( new Pair(k) ) ).getValue();
	}

	public boolean isEmpty() {
		return root == null;
	}

	public Set<K> keySet() {
		return keySet(root);
	}

	public T put(K key, T value) {
		Leaf leaf = getLeaf(key);
		if( leaf != null ) {
			int index = leaf.bucket.indexOf( new Pair(key) );
			if( index >= 0 ) {
				Pair element = leaf.bucket.get(index);
				T oldValue = element.getValue();
				element.setValue(value);
				return oldValue;
			} else { //collision (bucket will contain more then 1 value)
				Pair element = new Pair(key, value);
				leaf.bucket.add(element);
				return null;
			}
		} else if( root == null ) {
			root = new Leaf(null);
			root.code = key.hashCode();
			root.bucket.add( new Pair(key, value) );
		} else {
			leaf = root;
			int code = key.hashCode();
			boolean left = code < leaf.code;
			Leaf next = left ? leaf.left : leaf.right;
			while( next != null ) {
				leaf = next;
				left = code < leaf.code;
				next = left ? leaf.left : leaf.right;
			}
			if( left ) {
				leaf.left = new Leaf(leaf);
				leaf.left.code = code;
				leaf.left.bucket.add( new Pair(key, value) );
			} else {
				leaf.right = new Leaf(leaf);
				leaf.right.code = code;
				leaf.right.bucket.add( new Pair(key, value) );
			}
		}
		return null;
	}

	public void putAll(Map<? extends K, ? extends T> t) {
		for( K key : t.keySet() )
			put( key, t.get(key) );
	}

	@SuppressWarnings("unchecked")
	public T remove(Object key) throws ClassCastException {
		K k = (K)key;
		Leaf leaf = getLeaf(k);
		if( leaf == null )
			return null;
		int index = leaf.bucket.indexOf( new Pair(k) );
		if( index < 0 )
			return null;
		T value = leaf.bucket.remove(index).getValue();
		if( leaf.bucket.size() == 0 )
			do { //remove the Leaf
				if( leaf.parent == null )
					if( leaf.right == null && leaf.left == null ) {
						root = null;
						break;
					} else if( leaf.right == null ) {
						leaf.bucket = leaf.left.bucket;
						leaf.code = leaf.left.code;
						leaf = leaf.left;
					} else {
						leaf.bucket = leaf.right.bucket;
						leaf.code = leaf.right.code;
						leaf = leaf.right;
					}
				else if( leaf == leaf.parent.left )
					if( leaf.right == null && leaf.left == null ) {
						leaf.parent.left = null;
						break;
					} else if( leaf.right == null ) {
						leaf.bucket = leaf.left.bucket;
						leaf.code = leaf.left.code;
						leaf = leaf.left;
					} else {
						leaf.bucket = leaf.right.bucket;
						leaf.code = leaf.right.code;
						leaf = leaf.right;
					}
				else
					if( leaf.right == null && leaf.left == null ) {
						leaf.parent.right = null;
						break;
					} else if( leaf.left == null ) {
						leaf.bucket = leaf.right.bucket;
						leaf.code = leaf.right.code;
						leaf = leaf.right;
					} else {
						leaf.bucket = leaf.left.bucket;
						leaf.code = leaf.left.code;
						leaf = leaf.left;
					}
			} while( leaf != null );
		return value;
	}

	public int size() {
		return root == null ? 0 : root.size();
	}

	public Collection<T> values() {
		Collection<T> values = new ArrayList<T>( size() );
		for( K key : keySet() )
			values.add( get(key) );
		return values;
	}
}
