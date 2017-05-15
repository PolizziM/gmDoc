package omim;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

import application.model.*;

/** Simple command-line based search demo. */
public class QueriesOMIM {

	private QueriesOMIM() {}


	/** Simple command-line based search demo. */
	public static List<Disease> getDiseaseBySign(Sign s) throws Exception {

		List<Disease> results = new ArrayList<Disease>();
		String index = "indexOmim";
		String field = "CS";
		String queries = null;
		int repeat = 0;
		boolean raw = false;
		String queryString = null;
		int hitsPerPage = 10;

		IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(index)));
		IndexSearcher searcher = new IndexSearcher(reader);
		Analyzer analyzer = new StandardAnalyzer();

		BufferedReader in = null;
		if (queries != null) {
			in = Files.newBufferedReader(Paths.get(queries), StandardCharsets.UTF_8);
		} else {
			in = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
		}
		QueryParser parser = new QueryParser(field, analyzer);

			if (queries == null && queryString == null) {                        // prompt the user
//				System.out.println("Enter query: ");
			}

//			String line = queryString != null ? queryString : in.readLine();
			String line = s.getName();
			if (line == null || line.length() == -1) {
//				break;
			}

			line = line.trim();
			if (line.length() == 0) {
//				break;
			}

			Query query = parser.parse(line);
			System.out.println("Searching for: " + query.toString(field));

			if (repeat > 0) {                           // repeat & time as benchmark
				Date start = new Date();
				for (int i = 0; i < repeat; i++) {
					searcher.search(query, 100);
				}
				Date end = new Date();
				System.out.println("Time: "+(end.getTime()-start.getTime())+"ms");
			}

			results=doPagingSearch(in, searcher, query, hitsPerPage, raw, queries == null && queryString == null);


		reader.close();
		return results;
	}

	/**
	 * This demonstrates a typical paging search scenario, where the search engine presents 
	 * pages of size n to the user. The user can then go to the next page if interested in
	 * the next hits.
	 * 
	 * When the query is executed for the first time, then only enough results are collected
	 * to fill 5 result pages. If the user wants to page beyond this limit, then the query
	 * is executed another time and all hits are collected.
	 * 
	 */
	public static List<Disease> doPagingSearch(BufferedReader in, IndexSearcher searcher, Query query, 
			int hitsPerPage, boolean raw, boolean interactive) throws IOException {
		
		List<Disease> resultsList = new ArrayList<Disease>();
		
		// Collect enough docs to show 5 pages
		TopDocs results = searcher.search(query, 5 * hitsPerPage);
		ScoreDoc[] hits = results.scoreDocs;

		int numTotalHits = results.totalHits;
		System.out.println(numTotalHits + " total matching documents");

		int start = 0;
		int end = Math.min(numTotalHits, hitsPerPage);


			if (end > hits.length) {
				System.out.println("Only results 1 - " + hits.length +" of " + numTotalHits + " total matching documents collected.");
				System.out.println("Collect more (y/n) ?");
				String line = in.readLine();
				if (line.length() == 0 || line.charAt(0) == 'n') {
//					break;
				}

				hits = searcher.search(query, numTotalHits).scoreDocs;
			}

			end = Math.min(hits.length, start + hitsPerPage);

			for (int i = start; i < end; i++) {
				if (raw) {                              // output raw format
					System.out.println("doc="+hits[i].doc+" score="+hits[i].score);
					continue;
				}

				Document doc = searcher.doc(hits[i].doc);
				String name = doc.get("CS");
				if (name != null) {
					Disease d = new Disease();
//					System.out.println((i+1) + ". " + name);
					String id = doc.get("NO");
					if (id != null) {
//						System.out.println("   Id: " + doc.get("NO"));
						d.setId(Integer.parseInt(id));
					}
					String disease = doc.get("TI");
					if (disease != null) {

//						System.out.println("   Disease: " + doc.get("TI"));
						d.setName(disease.replaceAll("^.?[0-9]+", "").trim());
					}
					String syno = doc.get("synonyms");
					if (syno != null) {
//						System.out.println("   Synonyms: " + doc.get("CS"));
					}
					resultsList.add(d);
				} else {
					System.out.println((i+1) + ". " + "No drug with this name");
				}

			}

		return resultsList;
	}
}




























































