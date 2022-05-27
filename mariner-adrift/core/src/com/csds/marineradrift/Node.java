package com.csds.marineradrift;

import java.util.ArrayList;

public class Node<E> {
	private E element;
	private Node parent;
	private ArrayList<Node<E>> children;
	public Node(E element, Node parent, ArrayList<Node<E>> children) {
		this.element = element;
		this.parent = parent;
		this.children = children;
	}
	public E get() {
		return element;
	}
	public void set(E element) {
		this.element = element;
	}
	
	public void addChild(E element) {
		if(children == null)
			children = new ArrayList<Node<E>>();
		children.add(new Node(element, this, null));
	}
	public Node getChild (int index) {
		return children.get(index);
	}
	public Node getChild(E element) {
		for(Node n: children) {
			if(n.element.equals(element))
				return n;
		}
		return null;
	}
	public ArrayList<Node<E>> children() {
		return children;
	}
	public int numChildren() {
		if(children == null)
			return 0;
		return children.size();
	}
	public Node parent() {
		return parent;
	}
	public void setParent(Node parent) {
		this.parent = parent;
	}
}
