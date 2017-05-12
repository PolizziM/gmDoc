package application.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import application.Launcher;
import application.MainApp;
import application.model.Disease;
import application.model.Sign;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ResultsController {
    private MainApp mainApp;
    
    public ResultsController(){
    	
    }
    
    @FXML
    private void initialize() {
        // Initialize
    }
    
    public void init(ObservableList<Disease> liste){
		nameDisease.setCellValueFactory(cellData -> new SimpleStringProperty(writeProperly(cellData.getValue().getName())));
		Diseases.setItems(liste);
    }
    
    public String writeProperly(String s) {
    	return s = Character.toUpperCase(s.charAt(0)) + s.substring(1).toLowerCase();
    }
    
    @FXML
    private TextField query;

    @FXML
    private TableView<Disease> Diseases;
    @FXML
    private TableColumn<Disease, String> nameDisease;

    
    @FXML
    private TableView<String> Drugs;
    @FXML
    private TableColumn<String, String> nameDrug;
    
    
    @FXML
    private TableView<String> Treatment;
    @FXML
    private TableColumn<String, String> treat;
    
    @FXML
    private TableView<String> TreatmentSE;
    @FXML
    private TableColumn<String, String> treatse;
    
    
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    
    @FXML
    private void search(){
    	Date start = new Date();
    	String sign=query.getText();
		ObservableList<Disease> diseases = FXCollections.observableArrayList();
		try {
			ArrayList<Sign> allSynonyms = Launcher.searchForSynonymSign(new Sign(0,sign));
			allSynonyms.remove(null);
			
			for(Disease d : Launcher.searchDiseases(allSynonyms)){
				diseases.add(d);
			}
			init(diseases);
			Date end = new Date();
			System.out.println("The research took "+ (end.getTime()-start.getTime()) +" ms.");
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
    }
}
