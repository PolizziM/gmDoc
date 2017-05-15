package application;

import java.util.ArrayList;

import application.model.Disease;
import application.model.Sign;
import application.view.ResultsController;

public class SearchDrugs extends Thread{
	ArrayList<Sign> allSynonyms = new ArrayList<Sign>();
	public SearchDrugs(ArrayList<Sign> liste){
		allSynonyms = liste;
	}
	public void run(){
		try {
			ResultsController.drugs.clear();
			for(String d : Launcher.searchDrug(allSynonyms)){
				ResultsController.drugs.add(d);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
