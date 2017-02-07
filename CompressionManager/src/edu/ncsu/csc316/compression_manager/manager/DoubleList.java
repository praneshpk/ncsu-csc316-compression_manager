package edu.ncsu.csc316.compression_manager.manager;

import java.util.*;
/**
 * The DoubleList class acts as the main data structure for maintaining Nodes in
 * a similar fashion as a DoublyLinkedList, allowing for traversal of both sides
 * @author Pranesh Kamalakanthan
 *
 * @param <E> the type of data stored in the List
 */
public class DoubleList<E> {
	
	/**
	 * The nested Node class acts as the data structure contained within DoubleList
	 * with left and right Nodes to maintain the DoubleList structure
	 * @author Pranesh Kamalakanthan
	 *
	 * @param <E> the type of data stored in the Node
	 */
	private static class Node<E> {
		/** Holds Node data */
		private E data;
		
		/** References previous node */
		private Node<E> prev;
		
		/** References next node */
		private Node<E> next;
		
		/**
		 * Creates new Node object
		 * @param d Node data
		 * @param p previous Node
		 * @param n next Node
		 */
		public Node( E d, Node<E> p, Node<E> n ) {
			data = d;
			prev = p;
			next = n;
		}
		
		/**
		 * Get the previous Node.
		 * @return previous Node
		 */
		public Node<E> getPrev() { return prev; }
		
		/**
		 * Get the next Node.
		 * @return next Node
		 */
		public Node<E> getNext() { return next; }
		
		/**
		 * Sets the previous Node.
		 * @param p previous Node
		 */
		public void setPrev( Node<E> p ) { prev = p; }
		
		/**
		 * Sets the next Node
		 * @param n next Node
		 */
		public void setNext( Node<E> n ) { next = n; }
	}
	/** End nested Node class */
	
	/**
	 * The nested Iterator class maintains the functions and variables
	 * to traverse the DoubleList
	 * @author Pranesh Kamalakanthan
	 *
	 */
	private class DoubleListIterator implements Iterator<E> {
		/** Current Node of iterator */
		private Node<E> curr;
		
		/** Current iterator position */
		private int i;
		
		/**
		 * Creates a new DoubleListIterator object
		 */
		public DoubleListIterator() {
			curr = header.next;
			i = 0;
		}
		
		/**
		 * Checks if there is another Node in the List
		 * @return true if there is another Node
		 * 		   false if there is not
		 */
		public boolean hasNext() { return i < size; }
		
		/**
		 * Traverses the list by one and returns the current Node data
		 * @return curr.data
		 */
		public E next() {
			if( !hasNext() ) throw new NoSuchElementException();
			E e = curr.data;
			curr = curr.next;
			i++;
			return e;
		}
		
		/**
		 * Not supported. Included due to implementation of Iterator
		 */
		public void remove() { throw new UnsupportedOperationException(); }
	}
	/** End nested Iterator class */
	
	/** Header sentinel Node */
	private Node<E> header;
	
	/** Trailer sentinel Node */
	private Node<E> trailer;
	
	/** Size of DoubleList */
	private int size = 0;
	
	/**
	 * Creates a new DoubleList object
	 */
	public DoubleList() {
		header = new Node<>( null, null, null );
		trailer = new Node<>( null, header, null );
		header.setNext(trailer);
	}
	
	/**
	 * Gets a new DoubleListIterator object
	 * @return new DoubleListIterator object
	 */
	public DoubleListIterator iterator() { return new DoubleListIterator(); }
	
	/**
	 * Determines if the list is empty
	 * @return true if list is empty
	 * 		   false if it is not
	 */
	public boolean isEmpty() { return size == 0 ;}
	
	/**
	 * Gets the size of the list
	 * @return size of the list
	 */
	public int size() { return size; }
	
	/**
	 * Gets the first Node in the list
	 * @return first Node of list
	 */
	public Node<E> getFirst() { return header.getNext(); }
	
	/**
	 * Gets the last Node in the list
	 * @return last Node of list
	 */
	public Node<E> getLast() { return trailer.getPrev(); }
	
	/**
	 * Moves the current Node given by the Iterator object
	 * to the front
	 * @param it an Iterator object
	 */
	public void moveToFront( Iterator<E> it ) {
		Node<E> e = ((DoubleListIterator) it).curr.prev;
		
		e.getPrev().setNext( e.getNext() );
		e.getNext().setPrev( e.getPrev() );
		
		e.setPrev(header);
		e.setNext(header.getNext());
		
		header.getNext().setPrev( e );
		header.setNext( e );
	}
	
	/**
	 * Creates a new Node with the given data and
	 * adds it to the front of the list
	 * @param d Node data
	 */
	public void add( E d ) {
		Node<E> e = new Node<>(d, header, header.getNext());
		header.getNext().setPrev( e );
		header.setNext( e );
		size ++;
	}
	
	/** 
	 * Lists all words in the list separated by spaces. Used for debugging.
	 * @return words in list separated by spaces 
	 */
	public String toString() {
		DoubleListIterator e = new DoubleListIterator();
		String res = "";
		while( e.hasNext() )
			res += e.next() + " ";
		return res;
	}
	
}
