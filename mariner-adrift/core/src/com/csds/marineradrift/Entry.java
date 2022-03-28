package com.csds.marineradrift;

public class Entry<I, E> {
	private I id;
	private E element;
	public Entry(I id, E element) {
		this.id = id;
		this.element = element;
	}
	public I getID() {
		return id;
	}
	public E getElement() {
		return element;
	}
	public void setElement(E element) {
		this.element = element;
	}

}
