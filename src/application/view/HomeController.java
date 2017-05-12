package application.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import application.Launcher;
import application.MainApp;
import application.model.Disease;
import application.model.Sign;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class HomeController {
    @FXML
    private TextField query;

    // Reference to the main application.
    private MainApp mainApp;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public HomeController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize
    }

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    
    @FXML
    private void search(){
    	Date start = new Date();
    	if(!query.getText().contains(";")){
    		
    	String sign=query.getText();
		ObservableList<Disease> diseases = FXCollections.observableArrayList();
		ObservableList<String> drugs = FXCollections.observableArrayList();
		ObservableList<String> heal = FXCollections.observableArrayList();
		try {
			ArrayList<Sign> allSynonyms = Launcher.searchForSynonymSign(new Sign(0,sign));
			
			allSynonyms.remove(null);
			ResultsController.synonyme=allSynonyms;
			for(Disease d : Launcher.searchDiseases(allSynonyms)){
				diseases.add(d);
			}
			
			for(String s : Launcher.searchDrug(allSynonyms)){
				drugs.add(s);
			}
			for(String s : Launcher.searchTreatment(allSynonyms)){
				heal.add(s);
			}
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    		try {
    			FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("view/ResultsOverview.fxml"));
    			AnchorPane ResultsOverview =loader.load();
    			this.mainApp.getRootLayout().setCenter(ResultsOverview);
    			ResultsController controller = loader.getController(); 
    			controller.setMainApp(this.mainApp);
    			controller.init(diseases);
    			controller.initDrug(drugs);
    			controller.initHeal(heal);
    			Date end = new Date();
    			long time = end.getTime()-start.getTime();
    			controller.getTimeText().setText(Long.toString(time)+" ms");
    			System.out.println("The research took "+ time +" ms.");
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    		Date end = new Date();
			System.out.println("The research took "+ (end.getTime()-start.getTime()) +" ms.");
    	}else{
    		String[] signs=query.getText().split(";");
			ObservableList<Disease> diseases = FXCollections.observableArrayList();
			ObservableList<String> drugs = FXCollections.observableArrayList();
    		for(int k=0;k<signs.length;k++){
    	    	String sign=signs[k];

    			try {
    				ArrayList<Sign> allSynonyms = Launcher.searchForSynonymSign(new Sign(0,sign));
    				allSynonyms.remove(null);

    				for(Disease d : Launcher.searchDiseases(allSynonyms)){
    					diseases.add(d);
    				}
    				
    				for(String s : Launcher.searchDrug(allSynonyms)){
    					drugs.add(s);
    				}
    				
    				
    			} catch (Exception e1) {
    				// TODO Auto-generated catch block
    				e1.printStackTrace();
    			}
    		}    		try {
    			FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("view/ResultsOverview.fxml"));
    			AnchorPane ResultsOverview =loader.load();
    			this.mainApp.getRootLayout().setCenter(ResultsOverview);
    			ResultsController controller = loader.getController(); 
    			controller.setMainApp(this.mainApp);
    			controller.init(diseases);
    			controller.initDrug(drugs);
    			Date end = new Date();
    			long time = end.getTime()-start.getTime();
    			controller.getTimeText().setText(Long.toString(time)+" ms");
    			System.out.println("The research took "+ time +" ms.");
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    	}
    	
    }
}
