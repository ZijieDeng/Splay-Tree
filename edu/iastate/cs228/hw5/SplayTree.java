package edu.iastate.cs228.hw5;

import java.util.AbstractSet;
import java.util.Iterator;

/**
 * 
 * @author Zijie Deng
 *
 */

/**
 * 
 * This class implements a splay tree. Add any helper methods or implementation
 * details you'd like to include.
 *
 */

public class SplayTree<E extends Comparable<? super E>> extends AbstractSet<E> {
	protected Node root;
	protected int size;

	private class Node {
		public E data;
		public Node left;
		public Node parent;
		public Node right;

		public Node(E data) {
			this.data = data;
		}

		@Override
		public Node clone() {
			return new Node(data);
		}
	}

	/**
	 * Default constructor constructs an empty tree.
	 */
	public SplayTree() {
		root = null;
		size = 0;
	}

	/**
	 * Needs to call addBST() later on to complete tree construction.
	 */
	public SplayTree(E data) {
		addBST(data);
	}

	/**
	 * Copies over an existing splay tree. The entire tree structure must be
	 * copied. No splaying.
	 * 
	 * @param tree
	 */
	public SplayTree(SplayTree<E> tree) {
		root = copy(tree.root);
		size = tree.size;
	}

	private Node copy(Node n) {
		if (n == null)
			return n;
		Node copy = n.clone();
		copy.left = copy(n.left);
		copy.right = copy(n.right);
		if (copy.left != null)
			copy.left.parent = copy;
		if (copy.right != null)
			copy.right.parent = copy;
		return copy;
	}

	/**
	 * This function is here for grading purpose. It is not a good programming
	 * practice. This method is fully implemented and should not be modified.
	 * 
	 * @return root of the splay tree
	 */
	public E getRoot() {
		return root == null ? null : root.data;
	}

	@Override
	public int size() {
		return size;
	}

	/**
	 * Clear the splay tree.
	 */
	@Override
	public void clear() {
		root = null;
		size = 0;
	}

	// ----------
	// BST method
	// ----------

	/**
	 * Adds an element to the tree without splaying. The method carries out a
	 * binary search tree addition. It is used for initializing a splay tree.
	 * 
	 * @param data
	 * @return true if addition takes place false otherwise
	 */
	public boolean addBST(E data) {
		if (root == null) {
			root = new Node(data);
			size++;
			return true;
		}
		Node current = root;
		while (true) {
			if (current.data.compareTo(data) == 0)
				return false;
			else if (current.data.compareTo(data) > 0) {
				if (current.left != null)
					current = current.left;
				else {
					Node n = new Node(data);
					current.left = n;
					n.parent = current;
					size++;
					return true;
				}
			} else if (current.data.compareTo(data) < 0) {
				if (current.right != null)
					current = current.right;
				else {
					Node n = new Node(data);
					current.right = n;
					n.parent = current;
					size++;
					return true;
				}
			}

		}
	}

	// ------------------
	// Splay tree methods
	// ------------------

	/**
	 * Inserts an element into the splay tree. In case the element was not
	 * contained, this creates a new node and splays the tree at the new node.
	 * If the element exists in the tree already, it splays at the node
	 * containing the element.
	 * 
	 * @param data
	 *            element to be inserted
	 * @return true if addition takes place false otherwise (i.e., data is in
	 *         the tree already)
	 */
	@Override
	public boolean add(E data) {
		if (root == null) {
			root = new Node(data);
			size++;
			return true;
		}
		Node current = root;
		while (true) {
			if (current.data.compareTo(data) == 0) {
				splay(current);
				return false;
			} else if (current.data.compareTo(data) > 0) {
				if (current.left != null)
					current = current.left;
				else {
					Node n = new Node(data);
					current.left = n;
					n.parent = current;
					size++;
					splay(n);
					return true;
				}
			} else if (current.data.compareTo(data) < 0) {
				if (current.right != null)
					current = current.right;
				else {
					Node n = new Node(data);
					current.right = n;
					n.parent = current;
					size++;
					splay(n);
					return true;
				}
			}

		}
	}

	/**
	 * Determines whether the tree contains an element. Splays at the node that
	 * stores the element. If the element is not found, splays at the last node
	 * on the search path.
	 * 
	 * @param data
	 *            element to be determined whether to exist in the tree
	 * @return true if the element is contained in the tree false otherwise
	 */
	public boolean contains(E data) {
		Node n = findEntry(data);
		if (n==null)
			return false;
		splay(n);
		if (n.data.compareTo(data) == 0)
			return true;
		return false;
	}

	/**
	 * Splays at a node containing data. Exists for coding convenience, code
	 * readability, and testing purpose.
	 * 
	 * @param data
	 */
	public void splay(E data) {
		contains(data);
	}

	/**
	 * Removes the node that stores an element. Splays at its parent node after
	 * removal (No splay if the removed node was the root.) If the node was not
	 * found, the last node encountered on the search path is splayed to the
	 * root.
	 * 
	 * @param data
	 *            element to be removed from the tree
	 * @return true if the object is removed false if it was not contained in
	 *         the tree
	 */
	public boolean remove(E data) {
		Node n = findEntry(data);
		if (n == null)
			return false;
		Node parent = n.parent;
		Node leftChild = n.left;
		Node rightChild = n.right;
		if (root == n && size == 1) {
			root = null;
			size--;
			return true;
		}
		Node connect = join(leftChild, rightChild);
		if (root == n) {
			root = connect;
			size--;
			return true;
		} else if (parent.left == n)
			parent.left = connect;
		else if (parent.right == n)
			parent.right = connect;
		if (connect != null)
			connect.parent = parent;
		if (parent != null)
			splay(parent);
		size--;
		return true;
	}

	/**
	 * This method finds an element stored in the splay tree that is equal to
	 * data as decided by the compareTo() method of the class E. This is useful
	 * for retrieving the value of a pair <key, value> stored at some node
	 * knowing the key, via a call with a pair <key, ?> where ? can be any
	 * object of E.
	 * 
	 * Splays at the node containing the element or the last node on the search
	 * path.
	 * 
	 * @param data
	 * @return element such that element.compareTo(data) == 0
	 */
	public E findElement(E data) {
		if (size == 0)
			return null;
		Node n = findEntry(data);
		splay(n);
		if (n.data.compareTo(data) == 0)
			return n.data;
		return null;
	}

	/**
	 * Finds the node that stores an element. It is called by several methods
	 * including contains(), add(), remove(), and findElement().
	 * 
	 * No splay at the found node.
	 *
	 * @param data
	 *            element to be searched for
	 * @return node if found or the last node on the search path otherwise null
	 *         if size == 0.
	 */
	protected Node findEntry(E data) {
		if (root == null)
			return null;
		Node current = root;
		while (true) {
			if (current.data.compareTo(data) == 0) {
				return current;
			} else if (current.data.compareTo(data) > 0) {
				if (current.left != null)
					current = current.left;
				else
					return current;
			} else if (current.data.compareTo(data) < 0) {
				if (current.right != null)
					current = current.right;
				else
					return current;
			}
		}
	}

	/**
	 * Join the two subtrees T1 and T2 rooted at root1 and root2 into one. It is
	 * called by remove().
	 * 
	 * Precondition: All elements in T1 are less than those in T2.
	 * 
	 * Access the largest element in T1, and splay at the node to make it the
	 * root of T1. Make T2 the right subtree of T1. The method is called by
	 * remove().
	 * 
	 * @param root1
	 *            root of the subtree T1
	 * @param root2
	 *            root of the subtree T2
	 * @return the root of the joined subtree
	 */
	protected Node join(Node root1, Node root2) {
		if (root1 == null && root2 == null)
			return null;
		if (root1 == null && root2 != null)
			return root2;
		if (root1 != null && root2 == null)
			return root1;
		Node n = root1;
		root1.parent = null;
		while (n.right != null)
			n = n.right;
		splay(n);
		n.right = root2;
		root2.parent = n;
		return n;
	}

	/**
	 * Splay at the current node. This consists of a sequence of zig, zigZig, or
	 * zigZag operations until the current node is moved to the root of the
	 * tree.
	 * 
	 * @param current
	 *            node to splay
	 */
	protected void splay(Node current) {
		while (current != null && current.parent != null) {
			Node parent = current.parent;
			Node grandparent = current.parent.parent;
			if (grandparent == null)
				zig(current);
			else if (grandparent.left == parent && parent.right == current
					|| (grandparent.right == parent && parent.left == current))
				zigZag(current);
			else if (grandparent.right == parent && parent.right == current
					|| (grandparent.left == parent && parent.left == current))
				zigZig(current);
		}
	}

	/**
	 * This method performs the zig operation on a node. Calls leftRotate() or
	 * rightRotate().
	 * 
	 * @param current
	 *            node to perform the zig operation on
	 */
	protected void zig(Node current) {
		if (current == null || current.parent == null)
			throw new IllegalStateException();
		if (current.parent.left == current)
			rightRotate(current);
		else if (current.parent.right == current)
			leftRotate(current);
	}

	/**
	 * This method performs the zig-zig operation on a node. Calls leftRotate()
	 * or rightRotate().
	 * 
	 * @param current
	 *            node to perform the zig-zig operation on
	 */
	protected void zigZig(Node current) {
		Node parent = current.parent;
		Node grandparent = current.parent.parent;
		if (current == null || parent == null || grandparent == null)
			throw new IllegalStateException();
		if (grandparent.right == parent && parent.left == current
				|| (grandparent.left == parent && parent.right == current))
			throw new IllegalStateException();
		zig(parent);
		zig(current);
	}

	/**
	 * This method performs the zig-zag operation on a node. Calls leftRotate()
	 * or rightRotate() or both.
	 * 
	 * @param current
	 *            node to perform the zig-zag operation on
	 */
	protected void zigZag(Node current) {
		Node parent = current.parent;
		Node grandparent = current.parent.parent;
		if (current == null || parent == null || grandparent == null)
			throw new IllegalStateException();
		if (grandparent.left == parent && parent.left == current
				|| (grandparent.right == parent && parent.right == current))
			throw new IllegalStateException();
		zig(current);
		zig(current);
	}

	/**
	 * Carries out a left rotation at a node such that after the rotation its
	 * former parent becomes its left child.
	 * 
	 * @param current
	 */
	private void leftRotate(Node current) {
		if (current == null || current.parent == null || current.parent.right != current)
			throw new IllegalStateException();
		Node grandparent = current.parent.parent;
		Node parent = current.parent;
		Node leftChild = current.left;
		if (parent == root)
			root = current;
		current.parent.parent = current;
		current.left = parent;
		parent.right = null;
		current.parent = grandparent;
		if (grandparent != null && grandparent.left == parent)
			grandparent.left = current;
		if (grandparent != null && grandparent.right == parent)
			grandparent.right = current;
		if (leftChild != null) {
			leftChild.parent = parent;
			parent.right = leftChild;
		}
	}

	/**
	 * Carries out a right rotation at a node such that after the rotation its
	 * former parent becomes its right child.
	 * 
	 * @param current
	 */
	private void rightRotate(Node current) {
		if (current == null || current.parent == null || current.parent.left != current)
			throw new IllegalStateException();
		Node grandparent = current.parent.parent;
		Node parent = current.parent;
		Node rightChild = current.right;
		if (parent == root)
			root = current;
		current.parent.parent = current;
		current.right = parent;
		parent.left = null;
		current.parent = grandparent;
		if (grandparent != null && grandparent.left == parent)
			grandparent.left = current;
		if (grandparent != null && grandparent.right == parent)
			grandparent.right = current;
		if (rightChild != null) {
			rightChild.parent = parent;
			parent.left = rightChild;
		}
	}

	@Override
	public Iterator<E> iterator() {
		return new SplayTreeIterator();
	}

	/**
	 * Write the splay tree according to the format specified in Section 2.2 of
	 * the project description.
	 * 
	 * Calls toStringRec().
	 *
	 */
	@Override
	public String toString() {
		return toStringRec(root, 0);
	}

	private String toStringRec(Node n, int depth) {
		String s = "";
		for (int i = 0; i < depth * 4; i++) {
			s += " ";
		}
		if (n == null) {
			s += null + "\n";
			return s;
		}
		if (n.left == null && n.right == null) {
			s += n.data + "\n";
			return s;
		}
		s += n.data + "\n" + toStringRec(n.left, depth + 1) + toStringRec(n.right, depth + 1);
		return s;
	}

	/**
	 *
	 * Iterator implementation for this splay tree. The elements are returned in
	 * ascending order according to their natural ordering. All iterator methods
	 * are exactly the same as those for a binary search tree --- no splaying at
	 * any node as the cursor moves.
	 *
	 */
	private class SplayTreeIterator implements Iterator<E> {
		Node cursor;
		Node pending;

		public SplayTreeIterator() {
			cursor = root;
			while (cursor.left != null)
				cursor = cursor.left;
		}

		@Override
		public boolean hasNext() {
			return cursor != null;
		}

		@Override
		public E next() {
			pending = cursor;
			cursor = successor(cursor);
			return pending.data;
		}

		@Override
		public void remove() {
			if (pending == null)
				throw new IllegalStateException();
			if (pending.left != null && pending.right != null) {
				Node next = successor(pending);
				pending.data = next.data;
				pending = next;
			}
			if (pending.left != null || pending.right != null) {
				if (pending.parent.left == pending && pending.left != null) {
					pending.parent.left = pending.left;
					pending.left.parent = pending.parent;
				}
				if (pending.parent.left == pending && pending.right != null) {
					pending.parent.left = pending.right;
					pending.right.parent = pending.parent;
				}
				if (pending.parent.right == pending && pending.left != null) {
					pending.parent.right = pending.left;
					pending.left.parent = pending.parent;
				}
				if (pending.parent.right == pending && pending.right != null) {
					pending.parent.right = pending.right;
					pending.right.parent = pending.parent;
				}
			} else {
				if (pending.parent.left == pending)
					pending.parent.left = null;
				if (pending.parent.right == pending)
					pending.parent.right = null;
			}
			size--;
		}
	}

	private Node successor(Node n) {
		if (n == null || root == null)
			return null;
		Node current = n;
		if (current.right != null) {
			current = current.right;
			while (current.left != null)
				current = current.left;
			return current;
		} else {
			while (current.parent != null && current.parent.right == current)
				current = current.parent;
			if (current.parent != null)
				return current.parent;
			return null;
		}
	}
}
