package com.csds.marineradrift;
import java.util.AbstractList;
import java.util.LinkedList;
import java.util.ListIterator;
public class Map<I, E> extends AbstractList<E>{
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
	
	public E get(int i)
	{
		return (E)map.get(i).getElement();
	}
	
	public E set(int i, E element)
	{
		Entry old = (Entry)map.get(i);
		map.remove(i);
		map.add(new Entry(old.getID(),element));
		return (E)old.getElement();
	}
	
	public int size()
	{
		return map.size();
	}
	
}
