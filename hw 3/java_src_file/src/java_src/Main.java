package java_src;

import java.io.File;
import java.io.IOException;

import java_src.NormalizeBio;

public class Main {

	public static void main(String[] args) throws IOException {
		
		final File folder = new File("/Users/Rene/Documents/msia/16 Fall/text/hw 3/q0/split/");
		String writerfolder = "/Users/Rene/Documents/msia/16 Fall/text/hw 3/q0/norm/";
		listFilesForFolder(folder, writerfolder);

	}
	
	public static void listFilesForFolder(final File folder, String writerfolder) throws IOException {
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            listFilesForFolder(fileEntry,"");
	        } else {
	        	NormalizeBio normbio = new NormalizeBio();
	        	String writerfile = new StringBuilder(writerfolder).append(fileEntry.getName()).toString();
	    		normbio.norm(fileEntry, writerfile);
	            System.out.println(fileEntry.getName());
	        }
	    }
	}

}
