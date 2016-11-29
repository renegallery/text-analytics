package com.tutorialspoint.lucene;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.json.simple.parser.ParseException;

public class LuceneTester {
	
   String indexDir = "/Users/Rene/Documents/msia/16 Fall/text/hw 3/q3/Index/";
   String dataDir = "/Users/Rene/Documents/msia/16 Fall/text/hw 3/q3/Data/";
   Indexer indexer;
   Queryer queryer;
   LDA lda;
   
   public static void main(String[] args) throws ParseException, org.apache.lucene.queryparser.classic.ParseException, QueryNodeException, IOException {
      LuceneTester tester;
      /* create index*/
      try {
         tester = new LuceneTester();
         tester.createIndex();
      } catch (IOException e) {
         e.printStackTrace();
      } 
      
      /* 
       * create query */
       
      try {
    	  tester = new LuceneTester();
    	  tester.query();
      } catch (IOException e){
    	  e.printStackTrace();
      } 
      
      
      /* 
       * create LDA*/
      
      try {
    	  tester = new LuceneTester();
    	  tester.LDA();
      } catch (IOException e){
    	  e.printStackTrace();
      }
      
      
   }
   
   
   private void LDA() throws IOException {
	   lda = new LDA(indexDir);
	   lda.termDocMatrix1(indexDir);
   }
   
   private void query() throws IOException, org.apache.lucene.queryparser.classic.ParseException, QueryNodeException {
	   queryer = new Queryer(indexDir);
	   queryer.booleanQuery();
	   queryer.fuzzyQuery();
	   queryer.phraseQuery("\"located below sea level\"~10");
	   queryer.myQuery("china");
   }
   private void createIndex() throws IOException, ParseException{
      indexer = new Indexer(indexDir);
      int numIndexed;
      long startTime = System.currentTimeMillis();	
      numIndexed = indexer.createIndex(dataDir, new TextFileFilter());
      long endTime = System.currentTimeMillis();
      indexer.close();
      System.out.println(numIndexed+" File indexed, time taken: "
         +(endTime-startTime)+" ms");		
   }
}
