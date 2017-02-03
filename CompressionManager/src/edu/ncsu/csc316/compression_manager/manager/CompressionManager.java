package edu.ncsu.csc316.compression_manager.manager;

import java.io.*;
import java.util.Iterator;
import java.util.Scanner;

public class CompressionManager {
	private DoubleList<String> wordlist;
	
	public CompressionManager() {
		wordlist = new DoubleList<>();
	}
	
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
		try( Scanner in = new Scanner( new FileInputStream( "input/"+filename+".txt" ), "UTF8") )
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
			e.printStackTrace();
		}
	    return res;
	}
	
	private String lookUp( String word ) {
		Iterator<String> it = wordlist.iterator();
		int index = 1;
		while( it.hasNext() ) {
			if(it.next().equals(word)) {
				wordlist.moveToFront(it);
				return index + "";
			}
			index ++;
		}
		wordlist.add( word );
		return word;
	}
	
	private void compress( String filename ) throws FileNotFoundException {
		try( Scanner in = new Scanner( new FileInputStream( "input/"+filename+".txt" ), "UTF8") )
		{
			try( FileOutputStream fos = new FileOutputStream( "output/compressed/"+ filename + "-compressed.txt" );
					Writer w = new OutputStreamWriter( fos, "UTF8" ))
			{
				String line, word;
				w.write( "0 " );
				while( in.hasNextLine() ) {
					line = in.nextLine();
					word = "";
					for( int i = 0; i < line.length(); i++ ) {
						if( Character.isLetter(line.charAt(i)) )
							word += line.charAt(i);
						else if( word != "" ) {
							w.write( lookUp( word ) + line.charAt(i) );
							word = "";
						}
						else
							w.write( line.charAt(i) );
					}
					w.write( "\n" );
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	private void decompress( String filename ) throws FileNotFoundException {
		try( Scanner in = new Scanner( new FileInputStream( "input/"+filename+".txt" ), "UTF8") )
		{
			try( FileOutputStream fos = new FileOutputStream( "output/decompressed/"+ filename + "-decompressed.txt" );
					Writer w = new OutputStreamWriter( fos, "UTF8" ))
			{
				String line, word;
				Iterator<String> it;
				int index;
				while( in.hasNextLine() ) {
					line = in.nextLine();
					word = "";
					for( int i = 2; i < line.length(); i++ ) {
						if( Character.isLetter(line.charAt(i)) )
							word += line.charAt(i);
						else if( Character.isDigit(line.charAt(i)) ){
							index = Integer.parseInt(line.charAt(i)+"");
							
							if( index > wordlist.size() ||
								index < 1 ) {
								System.out.println("Error: Compressed file is corrupt!");
								System.exit(0);
							}
							
							it = wordlist.iterator();
							for( int j = 1; j < index; j++ )
								it.next();
							w.write( it.next() );
							wordlist.moveToFront(it);
							
						}
						else if( word != "" ) {
							w.write( lookUp( word ) + line.charAt(i) );
							word = "";
						}
						else
							w.write( line.charAt(i) );
					}
					w.write( "\n" );
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
