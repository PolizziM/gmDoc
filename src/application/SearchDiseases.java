package application;

import java.util.ArrayList;

import application.model.Disease;
import application.model.Sign;
import application.view.ResultsController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SearchDiseases extends Thread{

	ArrayList<Sign> allSynonyms = new ArrayList<Sign>();
	public SearchDiseases(ArrayList<Sign> liste){
		allSynonyms = liste;
	}
	public void run(){
		try {
			ResultsController.diseases.clear();
			for(Disease d : Launcher.searchDiseases(allSynonyms)){
				ResultsController.diseases.add(d);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
