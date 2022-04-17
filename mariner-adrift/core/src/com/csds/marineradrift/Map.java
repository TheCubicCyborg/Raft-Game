package com.csds.marineradrift;
import java.util.AbstractList;
import java.util.LinkedList;
import java.util.ListIterator;
public class Map<I, E> extends AbstractList<Entry<I,E>>{
	LinkedList<Entry<I,E>> map;
	public Map() {
		map = new LinkedList<Entry<I,E>>();
	}
	
	public E add(I id, E element) {
		for(Entry<I,E> e : map)
		{
			if(e.getID().equals( id))
			{
				map.remove(e);
				map.add(new Entry<I,E>(id,element));
				return e.getElement();
			}
		}
		map.add(new Entry<I,E>(id, element));
		return null;
		
	}
	public E getElement(I id) {
		ListIterator<Entry<I,E>> itr = map.listIterator();
		while(itr.hasNext()) {
			Entry<I,E> e = itr.next();
			if(e.getID().equals(id))
				return (E)e.getElement();
		}
		return null;
	}
	
	public Entry<I,E> get(int i)
	{
		return map.get(i);
	}
	
	public Entry<I,E> set(int i, Entry<I,E> e)
	{
		Entry<I,E> old = (Entry<I,E>)map.get(i);
		map.remove(i);
		map.add(e);
		return old;
	}
	
	public int size()
	{
		return map.size();
	}
	
}
