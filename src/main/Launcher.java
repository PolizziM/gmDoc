package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import atc.SearchInATC;
import model.Disease;
import model.Sign;
import model.Stitch;
import mySql.MySqlSign;
import omim.SearchInOmim;
import orphadata.SearchInOrpha;
import stitch.SearchInStitch;

public class Launcher  {

	private static String myQuery;
	private static Sign mySign;

	/************************************************************
	 * Main function
	 * @throws Exception 
	 ************************************************************/

	public static void main (String [] args) throws Exception {

		/* User enter disease, clinical sign and side effects */
		System.out.println("Examples of typing: 'Abnormality of coordination', 'Abnormality of digestive system', 'Iris neovascularization', etc...");
		System.out.print("Enter your query: ");
		Scanner sc = new Scanner(System.in);
		myQuery = sc.nextLine();
		mySign=new Sign(0,myQuery);

		/* Parsing the documents and create the indexes */
		initDocuments();

		/* Search for the synonyms of the sign */
		ArrayList<Sign> allSynonyms = searchForSynonymSign(mySign);
		//System.out.println(allSynonyms); //OK

		/* Search for the drug responsible for the side effects (signs) */
		ArrayList<String> drugsSE= searchDrug(allSynonyms);
		//System.out.println(drugsSE); 
		
		
		/* Search for the diseases responsible for the sign */
		ArrayList<Disease> diseases = searchDiseases(allSynonyms);
		System.out.println(diseases);

	}

	/**************************************************************
	 * Function to initialize the parsing of documents and indexes
	 * @throws IOException 
	 **************************************************************/

	public static void initDocuments() throws IOException {
		SearchInOmim.parsingOmimCsv();
		SearchInATC.parsingATC();
		SearchInStitch.parsingStitch();
	}

	/***************************************************************
	 * Functions to search the synonyms of the sign in the databases
	 ***************************************************************/

	public static ArrayList<Sign> searchForSynonymSign(Sign s) {
		ArrayList<Sign> results = new ArrayList<Sign>();
		results.add(mySign);

		ArrayList<Sign> synonymsOmim = SearchInOmim.searchSynonymsInOmim(s.getName());
		//ArrayList<Sign> synonymsHpo = SearchInHpo.;

		results = concateSign(results,synonymsOmim);
		//results = concateSign(results,synonymsHpo);
		return results; 
	}
	
	/******************************************************************
	 * Functions to search the synonyms of the disease in the databases
	 ******************************************************************/

	public static ArrayList<Disease> searchForSynonymDisease(Disease d) {
		ArrayList<Disease> results = new ArrayList<Disease>();
		
		//SEARCH SYNONYMS IN ORPHADATA
		
		return results; 
	}

	/******************************************************************
	 * Functions to search the diseases by sign
	 ******************************************************************/

	public static ArrayList<Disease> searchDiseases(ArrayList<Sign> synonyms) throws Exception {
		ArrayList<Disease> results = new ArrayList<Disease>();
		
		SearchInOrpha sio = new SearchInOrpha();
		List<Disease> tmp = new ArrayList<Disease>();
		List<Disease> diseasesOrpha = new ArrayList<Disease>();
		for(Sign s : synonyms) {
			tmp = sio.getDiseaseBySign(s.getName());
		}
		for(Disease d : tmp) {
			diseasesOrpha = sio.getDiseaseByName(d.getName());
		}
		
		results = concateDisease(results,diseasesOrpha);
		return results;
	}
	
	/*********************************************************************
	 * Functions to search the drug responsible for the side effect (sign)
	 * @return 
	 * @throws Exception 
	 *********************************************************************/
	
	public static ArrayList<String> searchDrug(ArrayList<Sign> synonyms) {
		ArrayList<String> codesATC = new ArrayList<String>();
		ArrayList<String> drugs = new ArrayList<String>();
		for(int i=0; i<synonyms.size(); i++) {
			List<Stitch> stitches = MySqlSign.getStitchesBySignsName(synonyms.get(i));
			for(int j=0;j<stitches.size();j++) {
				//System.out.println("Stitch: "+stitches.get(j));
				String code = SearchInStitch.searchCodeAtcInStitch(stitches.get(j));
				//System.out.println("Code ATC: "+code);
				if (code!=null) {
					codesATC.add(code);
				}
			}
		}
		
		for(int k=0; k<codesATC.size(); k++) {
			String drug = SearchInATC.searchDrugById(codesATC.get(k));
			drugs.add(drug);
		}
		
		return drugs;
	}

	/************************************************************
	 * Function to merge two array list
	 ************************************************************/

	public static ArrayList<Sign> concateSign (ArrayList<Sign> AL1, ArrayList<Sign> AL2) {
		for(int i=0;i<AL2.size();i++) {
			AL1.add(AL2.get(i));
		}
		return AL1;
	}
	
	public static ArrayList<Disease> concateDisease (ArrayList<Disease> AL1, List<Disease> AL2) {
		for(int i=0;i<AL2.size();i++) {
			AL1.add(AL2.get(i));
		}
		return AL1;
	}

}
