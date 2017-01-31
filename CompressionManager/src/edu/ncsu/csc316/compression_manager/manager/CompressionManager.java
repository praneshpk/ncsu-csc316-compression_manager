package edu.ncsu.csc316.compression_manager.manager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CompressionManager {
	/**
	* The process method accepts a file name as a parameter. The
	* method then compresses or decompresses the file, based
	* on whether the file begins with "0 " (if so, decompress the file;
	* if not, compress the file).
	* @param filename the name of the file to process
	* @return    "DECOMPRESS" if the file was decompressed
	*            "COMPRESS" if the file was compressed
	*            "EMPTY" if the file is empty (has no contents)
	*/
	public String process(String filename) {
	    String res = new String();
		try( Scanner in = new Scanner( new FileInputStream( filename ), "UTF8") )
	    {
	    	if( in.hasNextLine() ){
	    		String line = in.nextLine();
	    		in.close();
	    		
	    		if( line.length() > 1 &&
	    				line.substring(0, 2).equals("0 ") ) {
	    			decompress( filename );
	    			res = "DECOMPRESS";
	    		}
	    		else {
	    			compress( filename );
	    			res = "COMPRESS";
	    		}
	    	}
	    	else {
	    		res = "EMPTY";
	    	}
	    } catch (FileNotFoundException e) {
			System.out.println("Error: File not found!");
		}
	    return res;
	}
	public void compress( String filename ) {
		
	}
	public void decompress( String filename ) {
		
	}
}
