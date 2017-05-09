package gmDoc;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SearchInStitchATC {

	public final static String STITCH = "ressources/stitch/chemical.sources.v5.0.tsv";
	public final static String ATC = "ressources/atc/br08303.keg";
	public String[] disease_stitch = new String[3];
	public String[] disease_atc = new String[2];
	
	public SearchInStitchATC() throws IOException {
		System.out.println("Searching ...");
	}

	
	public void ReadStitchCsv() throws IOException {

		InputStream ips = new FileInputStream(STITCH);
		InputStreamReader ipsr = new InputStreamReader(ips);
		BufferedReader br = new BufferedReader(ipsr);
		String line;
		String source="ATC";
		int cpt=0;
		
		/*Remove unecessary information*/
		for (int i=0;i<9;i++) {
			br.readLine();
		}
		line=br.readLine();
		
		while(source.equals("ATC")) {
			disease_stitch=line.split("	");
			
			System.out.println("\nchemical: " + disease_stitch[0]);
			System.out.println("alias: " + disease_stitch[1]);
			System.out.println("source: " + disease_stitch[2]);
			
			source=disease_stitch[2];
			
			line=br.readLine();
			cpt++;
		}
		br.close();
		System.out.println("\nClinicalSigns recorded in stitch: " + cpt);
	}

	public void ReadATC() throws IOException {
		InputStream ips = new FileInputStream(ATC);
		InputStreamReader ipsr = new InputStreamReader(ips);
		BufferedReader br = new BufferedReader(ipsr);
		String line;
		int cpt=0;
		int n;
		
		while ((line=br.readLine())!=null) {
			if(line.startsWith("B ")) {
				System.out.println("\nID: "+line.substring(3,6));
				System.out.println("Clinical sign: "+line.substring(7));
			}
			if(line.startsWith("C ")) {
				System.out.println("\nID: "+line.substring(5,9));
				System.out.println("Clinical sign: "+line.substring(5));
			}
			if(line.startsWith("D ")) {
				System.out.println("\nID: "+line.substring(7,12));
				System.out.println("Clinical sign: "+line.substring(7));
			}
			if(line.startsWith("E ")) {
				System.out.println(line.substring(9));
				System.out.println("\nID: "+line.substring(9,16));
				System.out.println("Clinical sign: "+line.substring(9));
			}
			if(line.startsWith("F ")) {
				System.out.println("\nID: "+line.substring(11,17));
				System.out.println("Clinical sign: "+line.substring(11));
			}
			
			cpt++;
		}
		br.close();
		System.out.println("\nClinical signs recorded in ATC: " + cpt);
	}
	
}