package omim;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

import model.Disease;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class QueriesOMIM {
	public static ArrayList<Disease> query(String search) {
		ArrayList<Disease> Diseasees=new ArrayList<Disease>();

		try {

            StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_40);
			
			Query q = new MultiFieldQueryParser(new String[]{"TI"}, analyzer).parse("\""+search+"\"");

            System.out.println(q);
            int hitsPerPage = 500;
			IndexReader reader = DirectoryReader.open(FSDirectory.open(new File("gmDoc/indexOMIM")));
			IndexSearcher searcher = new IndexSearcher(reader);
			TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage, true);
			searcher.search(q, collector);
			ScoreDoc[] hits = collector.topDocs().scoreDocs;

            for(int i=0;i<hits.length;++i) {
                System.out.println("This query held at least one answer inside the indexed files. \n");
                int docId = hits[i].doc;
				Document d = searcher.doc(docId);

                System.out.println(d);
               
			}
			reader.close();

		} catch (IOException e) {

		} catch (ParseException e) {

		}
		return Diseasees;
	}
}