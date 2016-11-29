package com.tutorialspoint.lucene;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.PostingsEnum;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.search.DocIdSetIterator;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;

public class LDA {
	public LDA(String indexDir) throws IOException{
	      //this directory will contain the indexes
		Path indexPath = Paths.get(indexDir);
  	    IndexReader rdr = DirectoryReader.open(FSDirectory.open(indexPath));
	}
	
	public void termDocMatrix1(String indexDir) throws IOException {
		Path indexPath = Paths.get(indexDir);
  	    IndexReader rdr = DirectoryReader.open(FSDirectory.open(indexPath));
  	    String writepath = "/Users/Rene/Documents/msia/16 Fall/text/hw 3/q5/termDoc.txt";
		PrintWriter writer = new PrintWriter(writepath , "UTF-8");
  	    
		// get terms vectors for one document and one field
	  	for (int i=0; i< rdr.maxDoc(); i++) {
	  	    Document doc = rdr.document(i);
	  	    String docId = doc.get("docId");
  	    // do something with docId here...
	  	Terms terms = rdr.getTermVector(i, "country_text");
	  	if (terms != null && terms.size() > 0) {
		    // access the terms for this field
		    TermsEnum termsEnum = terms.iterator(); 
		    BytesRef term = null;
		    
		 // explore the terms for this field
		    while ((term = termsEnum.next()) != null) {
		        // enumerate through documents, in this case only one
		    	/*
		        PostingsEnum docsEnum = termsEnum.postings(null); 
		        int docIdEnum;
		        while ((docIdEnum = docsEnum.nextDoc()) != DocIdSetIterator.NO_MORE_DOCS) {
		            // get the term frequency in the document 
		        	//String term_word = term.utf8ToString();
		        	//int freq = (int) docsEnum.freq();
		        	writer.println(doc.get("country_name")+ "|" + term.utf8ToString() + "|" + docsEnum.freq());
		            //System.out.println(term.utf8ToString()+ " " + docIdEnum + " " + docsEnum.freq()); */
		        }
		    }
	  	}
  	    }
		


}
