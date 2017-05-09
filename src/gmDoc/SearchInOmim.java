package gmDoc;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SearchInOmim {

	public final static String OMIM_TXT = "ressources/omim/omim.txt";
	public final static String OMIM_CSV = "ressources/omim/omim_onto.csv";
	public String[] disease_omim_txt = new String[4];
	public String[] disease_omim_csv = new String[4];
	
	public SearchInOmim() throws IOException {
		System.out.println("Searching ...");
	}
	
	/* Function to read omim.txt*/
	public void ReadOmimTxt() throws IOException {

		InputStream ips = new FileInputStream(OMIM_TXT);
		InputStreamReader ipsr = new InputStreamReader(ips);
		BufferedReader br = new BufferedReader(ipsr);
		String line;
		int cpt=0;

		while ((line = br.readLine()) != null) {
			if(line.equals("*RECORD*")) {
				cpt++;
			}
			
			if(line.equals("*FIELD* NO")) { // Number of the disease
				String id = br.readLine();
				disease_omim_txt[0]=id;
				System.out.println("ID: " + id);
			}
			
			if(line.equals("*FIELD* TI")) { // Name and synonyms of the disease
				String name = br.readLine().substring(8);
				disease_omim_txt[1]=name;
				System.out.println("Name: " + name);
			}
			
			if(line.equals("*FIELD* TX")) {
				// Description of the disease
			}
			
			if(line.equals("*FIELD* CS")) {
				// Description of the signs 
			}
		}
		br.close();
		
		System.out.println("Diseases recorded in omim_txt: " + cpt);
		
	}
	
	/* Function to read omim_onto.csv*/
	public void ReadOmimCsv() throws IOException {

		InputStream ips = new FileInputStream(OMIM_CSV);
		InputStreamReader ipsr = new InputStreamReader(ips);
		BufferedReader br = new BufferedReader(ipsr);
		String line;
		int cpt=0;
		System.out.println(line = br.readLine()); // Header displayed
		
		while ((line = br.readLine()) != null) {
			/*values contents Class ID, Preferred Label, Synonyms, Definitions, Obsolete, CUI, Semantuc Types, Parents  fields*/
			String values[] = line.split(",");
			cpt++;
			/**
			 * ETUDIER LE CAS DE LA LIGNE 38 710 avec les "" qui empêche une bonne séparation
			 * */
			System.out.println("ID: " + values[0].substring(42));
			System.out.println("Name: " + values[1]);
			System.out.println("Synonyms: " + values[2]);
			System.out.println("CUI: " + values[5]);
		}
		br.close();
		
		System.out.println("Diseases recorded in omim_onto_csv: " + cpt);
		
	}

}
