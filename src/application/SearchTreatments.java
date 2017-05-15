package application;

import java.util.ArrayList;

import application.model.Sign;
import application.view.ResultsController;

public class SearchTreatments extends Thread{
	ArrayList<Sign> allSynonyms = new ArrayList<Sign>();
	public SearchTreatments(ArrayList<Sign> liste){
		allSynonyms = liste;
	}
	public void run(){
		try {
			ResultsController.heal.clear();
			for(String d : Launcher.searchTreatment(allSynonyms)){
				ResultsController.heal.add(d);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
