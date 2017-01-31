package edu.ncsu.csc316.compression_manager.manager;

import java.util.Scanner;

public class CompressionManagerUI {

	public static void main(String[] args) {
		// Creates a new CompressionManager object
		CompressionManager c = new CompressionManager();
		
		Scanner in = new Scanner(System.in);
		System.out.print("Please enter the filename you want compressed/decompressed: ");
		System.out.println(c.process( "input/" + in.next() ));
		in.close();
	}

}
