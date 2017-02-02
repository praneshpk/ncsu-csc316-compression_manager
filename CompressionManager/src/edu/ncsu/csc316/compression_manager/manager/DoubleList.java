package edu.ncsu.csc316.compression_manager.manager;

import java.util.*;

public class DoubleList<E> {
	/** Nested Node class **/
	private static class Node<E> {
		private E data;
		private Node<E> prev;
		private Node<E> next;
		public Node( E d, Node<E> p, Node<E> n ) {
			data = d;
			prev = p;
			next = n;
		}
		public Node<E> getPrev() { return prev; }
		public Node<E> getNext() { return next; }
		public void setPrev( Node<E> p ) { prev = p; }
		public void setNext( Node<E> n ) { next = n; }
	}
	/** End nested Node class**/
	
	/** Nested Iterator class **/
	private class DoubleListIterator implements Iterator<E> {
		private Node<E> curr = header.next;
		private int i = 0;
		
		public boolean hasNext() { return i < size; }
		public E next() {
			if( !hasNext() ) throw new NoSuchElementException();
			E e = curr.data;
			curr = curr.next;
			i++;
			return e;
		}
		public void remove() { throw new UnsupportedOperationException(); }
	}
	/** End nested Iterator class **/
	
	// Instance variables of DoublyLinkedList
	private Node<E> header;
	private Node<E> trailer;
	private int size = 0;
	
	// Default constructor
	public DoubleList() {
		header = new Node<>( null, null, null );
		trailer = new Node<>( null, header, null );
		header.setNext(trailer);
	}
	
	// Returns iterator object
	public DoubleListIterator iterator() { return new DoubleListIterator(); }
	
	// Returns if the list empty
	public boolean isEmpty() { return size == 0 ;}
	
	// Returns size of the list
	public int size() { return size; }
	
	// Gets first node
	public Node<E> getFirst() { return header.getNext(); }
	
	// Gets last node
	public Node<E> getLast() { return trailer.getPrev(); }
	
	// Moves the given Node to the front
	public void moveToFront( Iterator<E> it ) {
		Node<E> e = ((DoubleListIterator) it).curr.prev;
		
		
		e.getPrev().setNext( e.getNext() );
		e.getNext().setPrev( e.getPrev() );
		
		e.setPrev(header);
		e.setNext(header.getNext());
		
		header.getNext().setPrev( e );
		header.setNext( e );
	}
	
	// Adds the element to the front
	public void add( E d ) {
		Node<E> e = new Node<>(d, header, header.getNext());
		header.getNext().setPrev( e );
		header.setNext( e );
		size ++;
	}
	
}
