package edu.ncsu.csc316.compression_manager.manager;

import java.util.Scanner;

public class CompressionManagerUI {

	public static void main(String[] args) {
		// Creates a new CompressionManager object
		CompressionManager c = new CompressionManager();
		
		Scanner in = new Scanner(System.in);
		//System.out.print("Please enter the filename (without file extension) you want compressed/decompressed: ");
		//String file = in.next();
		String res = c.process( "DeclarationOfIndependence-compressed.txt" );
		in.close();
		if( res == "COMPRESS")
			System.out.print("File was successfully compressed");
		else if( res == "DECOMPRESS")
			System.out.print("File was successfully decompressed");
		else
			System.out.print("Error: The input file is empty");
		
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
		
	}

}
