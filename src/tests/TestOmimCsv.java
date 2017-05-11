package tests;

import java.io.IOException;
import java.util.ArrayList;

import omim.SearchInOmim;

public class TestOmimCsv {
	
	public static void main (String [] args) throws IOException {
		
		SearchInOmim.parsingOmimCsv();
		ArrayList<String> synonyms = SearchInOmim.searchSynonymsInOmim("AMYOTROPHIC LATERAL SCLEROSIS 8");
		System.out.println(synonyms);
	}
	
}
