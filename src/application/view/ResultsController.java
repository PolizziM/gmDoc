package application.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import application.Launcher;
import application.MainApp;
import application.model.Disease;
import application.model.Sign;
import atc.SearchInATC;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import sider.MySqlSign;
import stitch.SearchInStitch;

public class ResultsController {

	private MainApp mainApp;
	private long time;
	public static ArrayList<Sign> synonyme;
	ObservableList<Disease> diseases = FXCollections.observableArrayList();
	ObservableList<String> drugs = FXCollections.observableArrayList();
	ObservableList<String> heal = FXCollections.observableArrayList();
	public ResultsController(){

	}

	@FXML
	private Text timeText;

	@FXML
	private void initialize() {
		// Initialize
	}

	public void init(ObservableList<Disease> liste){
		nameDisease.setCellValueFactory(cellData -> new SimpleStringProperty(writeProperly(cellData.getValue().getName())));
		Diseases.setItems(liste);
	}

	public void initDrug(ObservableList<String> liste){
		nameDrug.setCellValueFactory(cellData -> new SimpleStringProperty(writeProperly(cellData.getValue())));
		Drugs.setItems(liste);
	}
	public void initHeal(ObservableList<String> liste){
		treat.setCellValueFactory(cellData -> new SimpleStringProperty(writeProperly(cellData.getValue())));

		Treatment.setItems(liste);
	}
	public String writeProperly(String s) {
		return Character.toUpperCase(s.charAt(0)) + s.substring(1).toLowerCase();
		//    	return s = Character.toUpperCase(s.charAt(0)) + s.substring(1).toLowerCase();
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

	public Text getTimeText() {
		return timeText;
	}

	public void setTimeText(Text timeText) {
		this.timeText = timeText;
	}

	@FXML
	private void getDrugs(){
		int i=0;
		int selectedIndex = Treatment.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			i=selectedIndex;
		}
//		ArrayList<Sign> s = new ArrayList<Sign>();
//		s.add(new Sign(0,ResultsController.synonyme));
		List<String> cui = Launcher.searchCui(ResultsController.synonyme);
		ObservableList<String> cc = FXCollections.observableArrayList();
//		ObservableList<String> l = new ArrayList<String>();
		for(String ss : cui){
			List<String> ls = MySqlSign.getStitchByCui(ss);
			for(String sa : ls){
				cc.add(sa);
			}
		}
		
		
		treatse.setCellValueFactory(cellData -> new SimpleStringProperty(writeProperly(cellData.getValue())));
		TreatmentSE.setItems(cc);
	}

	@FXML
	private void search(){
		Date start = new Date();
		String sign=query.getText();
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
			init(diseases);
			initDrug(drugs);
			initHeal(heal);
			Date end = new Date();
			time = end.getTime()-start.getTime();
			timeText.setText(Long.toString(time)+" ms");
			System.out.println("The research took "+ time +" ms.");

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
}
