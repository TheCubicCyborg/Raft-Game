package com.csds.marineradrift;

import java.util.ArrayList;

public class Tree<E> {
	private Node root;
	public Tree() {
		root = null;
	}
	public void add(E element) {
		if(root == null)
			root = new Node(element, null, new ArrayList<Node<E>>());
		else
			root.addChild(element);
	}
	public void add(E element, Node parent) {
		parent.addChild(element);
	}
	public Node get(E element) {
		return get(root, element);
	}
	public Node get(Node n, E element) {
		if (element.equals(n.get()))
			return n;
		if (n.numChildren() < 1)
			return null;
		for (int i = 0; i < n.numChildren(); i++) {
			if(get(n.getChild(i), element) != null)
					return get(n.getChild(i), element);
		}
		return null;
	}
	public Node getRoot() {
		return root;
	}
}
