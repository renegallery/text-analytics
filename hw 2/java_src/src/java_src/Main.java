package java_src;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.process.PTBTokenizer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.apache.lucene.util.Version;

public class Main {
	
	public static void main(String[] args) throws IOException{
		/*Tokenization tokenization = new Tokenization();
		tokenization.token();
		Normalization normalization = new Normalization();
		normalization.norm();
		NormalizeBio normbio = new NormalizeBio();
		normbio.normbio();
		StopFilterBio normbio = new StopFilterBio();
		normbio.normbio();*/
		/*
		final File folder = new File("/Users/Rene/Documents/msia/16 Fall/text/hw 2/review_polarity/txt_sentoken/pos");
		String writerfolder = "/Users/Rene/Documents/msia/16 Fall/text/hw 2/review_polarity/txt_norm/pos/";
		listFilesForFolder(folder, writerfolder);*/
		final File folder = new File("/Users/Rene/Documents/msia/16 Fall/text/hw 2/review_polarity/txt_sentoken/neg");
		String writerfolder = "/Users/Rene/Documents/msia/16 Fall/text/hw 2/review_polarity/txt_norm/neg/";
		listFilesForFolder(folder, writerfolder);
	}
	
	public static void listFilesForFolder(final File folder, String writerfolder) throws IOException {
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            listFilesForFolder(fileEntry,"");
	        } else {
	        	NormalizeReview normbio = new NormalizeReview();
	        	String writerfile = new StringBuilder(writerfolder).append(fileEntry.getName()).toString();
	    		normbio.norm(fileEntry, writerfile);
	            System.out.println(fileEntry.getName());
	        }
	    }
	}


}



