package edu.ncsu.csc316.compression_manager.manager;

import java.util.Scanner;

/**
 * The CompressionManagerUI class allows the user to interface with the CompressionManager
 * and input files to be compressed / decompressed
 * @author Pranesh Kamalakanthan
 */
public class CompressionManagerUI {

	/**
	 * Starts the program.
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		// Creates a new CompressionManager object
		CompressionManager c = new CompressionManager();
		
		// Asks for user input
		Scanner in = new Scanner(System.in);
		System.out.print("Please enter the filename you want compressed/decompressed: ");
		String res = c.process( in.next() );
		in.close();
		
		// Prints message according to which method was called 
		if( res.equals("COMPRESS") )
			System.out.print("File was successfully compressed");
		else if( res.equals("DECOMPRESS") )
			System.out.print("File was successfully decompressed");
		else if( res.equals("EMPTY") )
			System.out.print("Error: The input file is empty");
		
	}

}