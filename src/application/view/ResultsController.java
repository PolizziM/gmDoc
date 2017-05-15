package application.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import application.Launcher;
import application.MainApp;
import application.SearchDiseases;
import application.SearchDrugs;
import application.SearchTreatments;
import application.model.Disease;
import application.model.Sign;
import atc.SearchInATC;
import com.jfoenix.controls.JFXSpinner;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sider.MySqlSign;
import stitch.SearchInStitch;

public class ResultsController {

	private MainApp mainApp;
	private long time;
	public static ArrayList<Sign> synonyme;
	public static ObservableList<Disease> diseases = FXCollections.observableArrayList();
	public static ObservableList<String> drugs = FXCollections.observableArrayList();
	public static ObservableList<String> heal = FXCollections.observableArrayList();
	
	public ResultsController(){

	}

	@FXML private JFXSpinner resultsProgressSpinner;
	@FXML
	private Text timeText;

	@FXML
	private void initialize() {
		// Initialize
	}

    /*
    allows the user to perform a search by pressing the enter key and not clicking the search button.
    */
    public void searchEnter(KeyEvent e) throws IOException {
        // System.out.println("a key has been pressed it seems");
        resultsProgressSpinner.setVisible(true);
        if (e.getCode().equals(KeyCode.ENTER)) // check if the pressed key is the 'enter' key
        {
            search();
        }
    }

    public void initDiseases(){
		nameDisease.setCellValueFactory(cellData -> new SimpleStringProperty(writeProperly(cellData.getValue().getName())));
		Diseases.setItems(diseases);
//		diseases.clear();
	}

	public void initDrug(){
		nameDrug.setCellValueFactory(cellData -> new SimpleStringProperty(writeProperly(cellData.getValue())));
		Drugs.setItems(drugs);
//		drugs.clear();
	}
	public void initHeal(){
		treat.setCellValueFactory(cellData -> new SimpleStringProperty(writeProperly(cellData.getValue())));
		Treatment.setItems(heal);
//		heal.clear();
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

	@FXML private Button exitButton;
	@FXML private ImageView exitImgPressed;
	@FXML private ImageView exitImgNotPressed;


	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	/*
    this closes the app when user presses the 'x' button.
    */
	public void handleExitButton() throws IOException {
		Stage stage = (Stage) exitButton.getScene().getWindow();
		stage.close();

	}

	/*
    this shows the 'x' img when the mouse cursor enters the spot.
     */
	public void handleShowExitButton() throws IOException {
		// System.out.println("THE MOUSE IS INSIDE, I REPEAT THE MOUSE IS IN");
		exitImgPressed.setOpacity(1);
		exitImgNotPressed.setOpacity(0);
	}

	/*
    this hides the 'x' img when the mouse cursor exists the spot.
     */
	public void handleHideExitButton() throws IOException {
		// System.out.println("THE MOUSE IS INSIDE, I REPEAT THE MOUSE IS IN");
		exitImgPressed.setOpacity(0);
		exitImgNotPressed.setOpacity(1);
	}

	public Text getTimeText() {
		return timeText;
	}

	public void setTimeText(Text timeText) {
		this.timeText = timeText;
	}



	@FXML
	private void search(){
		Date    start   = new Date();
		String  sign    = query.getText();
		try {
			ArrayList<Sign> allSynonyms = Launcher.searchForSynonymSign(new Sign(0,sign));
			allSynonyms.remove(null);
			
			SearchDiseases sd = new SearchDiseases(allSynonyms);
			sd.start();
			
			SearchDrugs sdr = new SearchDrugs(allSynonyms);
			sdr.start();
			
			SearchTreatments st = new SearchTreatments(allSynonyms);
			st.start();
			
			sd.join();
			sdr.join();
			st.join();
			Date end = new Date();
			time = end.getTime()-start.getTime();
			initDiseases();
			initDrug();
			initHeal();
            resultsProgressSpinner.setVisible(false);
			timeText.setText(Long.toString(time)+" ms");
			System.out.println("The research took "+ time +" ms.");

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
}
