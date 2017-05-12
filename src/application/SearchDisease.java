package application;

import java.util.ArrayList;

import application.model.Disease;
import application.model.Sign;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SearchDisease extends Thread {
	private String sign;
	public SearchDisease(String s){
		this.sign=s;
	}

	public void run(){
		ObservableList<Disease> diseases = FXCollections.observableArrayList();
		try {
			ArrayList<Sign> allSynonyms = Launcher.searchForSynonymSign(new Sign(0,sign));
			allSynonyms.remove(null);
			
			for(Disease d : Launcher.searchDiseases(allSynonyms)){
				diseases.add(d);
			}
			
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
