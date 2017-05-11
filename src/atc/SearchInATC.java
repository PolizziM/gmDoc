package atc;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import model.*;

public class SearchInATC {

	public final static String ATC = "ressources/atc/br08303.keg";
	public static ArrayList<ATC> drugs_atc;

	public SearchInATC() {
		System.out.println("Searching in ATC...");
	}
	
	/*******************************************************************************
	 * Searching function in ATC database
	 *******************************************************************************/
	
	public static void parsingATC() throws IOException {
		InputStream ips = new FileInputStream(ATC);
		InputStreamReader ipsr = new InputStreamReader(ips);
		BufferedReader br = new BufferedReader(ipsr);
		String line;
		int cpt=0;
		drugs_atc = new ArrayList<ATC>();

		/* Remove unecessary information at the beginning of the file */
		for (int i=0;i<13;i++) {
			br.readLine();
		}
		line=br.readLine();
		
		while ((line=br.readLine())!=null) {
			
			ATC atc = new ATC();

			if(line.startsWith("B ")) {
				atc = new ATC(line.substring(3,6),line.substring(7));
				drugs_atc.add(atc);
			}
			if(line.startsWith("C ")) {
				atc = new ATC(line.substring(5,9),line.substring(10));
				drugs_atc.add(atc);
			}
			if(line.startsWith("D ")) {
				atc = new ATC(line.substring(7,12),line.substring(13));
				drugs_atc.add(atc);
			}
			if(line.startsWith("E ")) {
				atc = new ATC(line.substring(9,16),line.substring(17));
				drugs_atc.add(atc);
			}
			if(line.startsWith("F ")) {
				atc = new ATC(line.substring(11,17),line.substring(18));
				drugs_atc.add(atc);
			}
			cpt++;
			//System.out.println(atc);
		}
		br.close();
		//System.out.println(cpt+" drugs found in ATC.");

	}

	public static String searchDrugById(String codeATC) {
		
		String drug = null;
		
		for(int i=0;i<drugs_atc.size();i++) {
			if(drugs_atc.get(i).getCodeATC().equals(codeATC)) {
				drug=drugs_atc.get(i).getDrug();
			}
		}
		return drug;
	}
	
}
