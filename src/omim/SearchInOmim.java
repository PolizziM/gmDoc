package omim;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import model.Omim;

public class SearchInOmim {

	public final static String OMIM_CSV = "ressources/omim/omim_onto.csv";
	public static ArrayList<Omim> omim_list;

	public static void parsingOmimCsv() throws IOException {

		InputStream ips = new FileInputStream(OMIM_CSV);
		InputStreamReader ipsr = new InputStreamReader(ips);
		BufferedReader br = new BufferedReader(ipsr);
		String line;
		int cpt=0;
		line = br.readLine(); // Header
		omim_list = new ArrayList<Omim>();

		while ((line = br.readLine()) != null) {

			/*fields contents Class ID, Preferred Label, Synonyms, Definitions, Obsolete, CUI, Semantuc Types, Parents  fields*/
			String fields[] = line.split(",");
			Omim omim = new Omim();

			/*get the id*/
			if(fields[0].substring(37,41).equals("OMIM")) {
				if(fields[0].substring(42).startsWith("MTHU")) {
					omim.setId(fields[0].substring(46,51)); // ID
				} else {
					omim.setId(fields[0].substring(42,47)); // ID
				}

				String[] signs = fields[1].split(";");
				omim.setSign(signs); // Preferred Label

				String[] synonyms = fields[2].split(";");
				omim.setSynonym(synonyms); // Synonyms

				omim.setCui(fields[5]); // CUI
			}
			omim_list.add(omim);
			//System.out.println(omim);
			cpt++;
		}
		br.close();
		//System.out.println("Diseases recorded in omim_onto_csv: " + cpt);

	}

	public static ArrayList<String> searchSynonymsInOmim(String sign) {
		
		ArrayList<String> synonyms = new ArrayList<String>();
		for(int i=0;i<omim_list.size();i++) {
			for(int j=0;j<omim_list.get(i).getSign().length;j++) {
				if (omim_list.get(i).getSign()[j].equals(sign)) {
					String synonym = omim_list.get(i).getSynonym()[j];
					System.out.println(synonym);
					synonyms.add(synonym);
				}
			}
		}
		if(synonyms.isEmpty()) {
			System.out.println("Not any synonym");
		}
		return synonyms;
	}
	
}
