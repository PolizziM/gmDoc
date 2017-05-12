package stitch;

import java.io.BufferedReader;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import application.model.*;

public class SearchInStitch {

	public final static String STITCH = "ressources/stitch/chemical.sources.v5.0.tsv";
	public static ArrayList<Stitch> stitch_list;

	public static void parsingStitch() throws IOException {

		InputStream ips = new FileInputStream(STITCH);
		InputStreamReader ipsr = new InputStreamReader(ips);
		BufferedReader br = new BufferedReader(ipsr);
		String line;
		String source="ATC";
		int cpt=0;
		stitch_list = new ArrayList<Stitch>();

		/*Remove unecessary information*/
		for (int i=0;i<9;i++) {
			br.readLine();
		}
		line=br.readLine();

		while(source.equals("ATC")) {
			String[] disease_stitch=line.split("	");
			String chemical = disease_stitch[0];
			String alias = disease_stitch[1];
	
			Stitch s = new Stitch(chemical,alias,disease_stitch[3]);
			//System.out.println(s);
			stitch_list.add(s);

			source=disease_stitch[2];
			line=br.readLine();
			cpt++;
		}
		br.close();
		//System.out.println(cpt+" drugs recorded in stitch.");
	}

	public static String searchCodeAtcInStitch(Stitch stitch) {
		
		String chemical = stitch.getId1();
		chemical=chemical.substring(0,3)+"m"+chemical.substring(4,12);
		String alias = stitch.getId2();
		alias=alias.substring(0,3)+"s"+alias.substring(4,12);
		String code = null;

		for(int i=0; i<stitch_list.size();i++) {
			if(stitch_list.get(i).getId1().equals(chemical) && stitch_list.get(i).getId2().equals(alias)) {
				code = stitch_list.get(i).getCodeATC();
			}
			//System.out.println(stitch_list.get(i));
		}
		return code;
	}
}
