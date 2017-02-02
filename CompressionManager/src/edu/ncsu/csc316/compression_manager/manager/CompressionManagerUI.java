package edu.ncsu.csc316.compression_manager.manager;

import java.util.*;

public class CompressionManagerUI {

	public static void main(String[] args) {
		// Creates a new CompressionManager object
		CompressionManager c = new CompressionManager();
		
		Scanner in = new Scanner(System.in);
		System.out.print("Please enter the filename you want compressed/decompressed: ");
		System.out.println(c.process( "input/" + in.next() ));
		/*
		System.out.println("Testing linked list...");
		DoubleList<String> wordlist = new DoubleList<>();
		for(int i = 0; i < 100; i++ ) {
			wordlist.add(i+"");
		}
		Iterator<String> e = wordlist.iterator();
		while( e.hasNext() ) {
			String p = e.next();
			if(p.equals("68")) {
				wordlist.moveToFront(e);
				break;
			}
		}
		e = wordlist.iterator();
		while( e.hasNext() ) {
			System.out.print(e.next()+" ");
		}*/
		in.close();
	}

}
