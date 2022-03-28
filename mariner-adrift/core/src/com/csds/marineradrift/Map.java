package com.csds.marineradrift;
import java.util.LinkedList;
import java.util.ListIterator;
public class Map<I, E> {
	LinkedList<Entry> map;
	public Map() {
		map = new LinkedList<Entry>();
	}
	public void add(I id, E element) {
		map.add(new Entry(id, element));
	}
	public E getElement(I id) {
		ListIterator<Entry> itr = map.listIterator();
		while(itr.hasNext()) {
			Entry e = itr.next();
			if(e.getID().equals(id))
				return (E)e.getElement();
		}
		return null;
	}
}
