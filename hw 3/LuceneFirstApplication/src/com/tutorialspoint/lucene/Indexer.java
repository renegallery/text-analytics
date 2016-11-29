package com.tutorialspoint.lucene;

import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.charfilter.HTMLStripCharFilter;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class Indexer {

   private IndexWriter writer;

   public Indexer(String indexDirectoryPath) throws IOException{
      //this directory will contain the indexes
	  Path indexPath = Paths.get(indexDirectoryPath);
      Directory indexDirectory = 
         FSDirectory.open(indexPath);

      //create the indexer
      Analyzer analyzer = new EnglishAnalyzer();
      IndexWriterConfig config = new IndexWriterConfig(analyzer);
      writer = new IndexWriter(indexDirectory, config);
   }

   public void close() throws CorruptIndexException, IOException{
      writer.close();
   }

   private Document getDocument(File file) throws IOException, ParseException{
	  
      Document document = new Document();
      
      // read in json file and parse json into four parts
      JSONParser parser = new JSONParser();
      Object obj = parser.parse(new FileReader(file));
      JSONObject jsonObject = (JSONObject) obj;
      
      String city_name = (String) jsonObject.get("city_name");
      String country_name = (String) jsonObject.get("country_name");
      String city_text = (String) jsonObject.get("city_text");
      String country_text = (String) jsonObject.get("country_text");
      
      //System.out.println(city_text);
      
      Field citynameField = new TextField("city_name", city_name, Field.Store.YES);
      Field countrynameField = new TextField("country_name", country_name, Field.Store.YES);
      
      // field city and country text
      FieldType TermVector = new FieldType();
      TermVector.setStored(false);
      TermVector.setTokenized(true);
      TermVector.setStoreTermVectors(true);
      TermVector.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS);
      HTMLStripCharFilter city_cont_stripped = new HTMLStripCharFilter(new StringReader(city_text));
      HTMLStripCharFilter country_cont_stripped = new HTMLStripCharFilter(new StringReader(country_text));
      Field citytextField = new Field("city_text", city_cont_stripped, TermVector);
      Field countrytextField = new Field("country_text", country_cont_stripped, TermVector);
        // file.getCanonicalPath(),
         //Field.Store.YES);

      document.add(citynameField);
      document.add(countrynameField);
      document.add(citytextField);
      document.add(countrytextField);

      return document;
   }   

   private void indexFile(File file) throws IOException, ParseException{
      System.out.println("Indexing "+file.getCanonicalPath());
      Document document = getDocument(file);
      writer.addDocument(document);
   }

   public int createIndex(String dataDirPath, FileFilter filter) 
      throws IOException, ParseException{
      //get all files in the data directory
      File[] files = new File(dataDirPath).listFiles();
      //int i = 0;

      for (File file : files) {
         if(!file.isDirectory()
            && !file.isHidden()
            && file.exists()
            && file.canRead()
            && filter.accept(file)
            //&& i==0
         ){
            indexFile(file);
            //i++;
         }
      }
      return writer.numDocs();
   }
}