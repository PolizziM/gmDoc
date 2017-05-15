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
import com.jfoenix.controls.JFXProgressBar;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class HomeController {

	// FXML Variables Declaration
	@FXML private Button exitButton;
	@FXML private ImageView exitImgPressed;
	@FXML private ImageView exitImgNotPressed;
	@FXML private JFXProgressBar homeProgressBar;


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

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	/*
	allows the user to perform a search by pressing the enter key and not clicking the search button.
	 */
	public void searchEnter(KeyEvent e) throws IOException {
		// System.out.println("a key has been pressed it seems");
		homeProgressBar.setVisible(true);
		if (e.getCode().equals(KeyCode.ENTER)) // check if the pressed key is the 'enter' key
		{
			search();
		}
	}
	   public <T> ArrayList<T> intersection(List<T> list1, List<T> list2) {
	        ArrayList<T> list = new ArrayList<T>();

	        for (T t : list1) {
	            if(list2.contains(t)) {
	                list.add(t);
	            }
	        }

	        return list;
	    }
	@FXML
	private void search(){


		Date start = new Date();
		if(!query.getText().contains(";")){

			String sign=query.getText();

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
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				Date end = new Date();
				long time = end.getTime()-start.getTime();
				FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("view/ResultsOverview.fxml"));
				AnchorPane ResultsOverview =loader.load();
				this.mainApp.getRootLayout().setCenter(ResultsOverview);
				ResultsController controller = loader.getController(); 
				controller.setMainApp(this.mainApp);
				controller.initDiseases();
				controller.initDrug();
				controller.initHeal();
				controller.getTimeText().setText(Long.toString(time)+" ms");
				System.out.println("The research took "+ time +" ms.");
			} catch (IOException e) {
				e.printStackTrace();
			}
			Date end = new Date();
			System.out.println("The research took "+ (end.getTime()-start.getTime()) +" ms.");
		}else{
			String[] signs=query.getText().split(",");
			ArrayList<ArrayList<Disease>> diseases = new ArrayList<ArrayList<Disease>>();
			ArrayList<ArrayList<String>> drugs = new ArrayList<ArrayList<String>>();
			for(int k=0;k<signs.length;k++){
				diseases.add(new ArrayList<Disease>());
				drugs.add( new ArrayList<String>());
			}


			for(int k=0;k<signs.length;k++){
				String sign=signs[k];

				try {
					ArrayList<Sign> allSynonyms = Launcher.searchForSynonymSign(new Sign(0,sign));
					allSynonyms.remove(null);

					for(Disease d : Launcher.searchDiseases(allSynonyms)){
						diseases.get(k).add(d);
					}

					for(String s : Launcher.searchDrug(allSynonyms)){
						drugs.get(k).add(s);
					}


				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			ArrayList<Disease> listeDiseases = diseases.get(0);
			ArrayList<String> listeDrugs = drugs.get(0);
					for(ArrayList<Disease> d : diseases){
						listeDiseases = intersection(listeDiseases, d);
					}

					for(ArrayList<String> d : drugs){
						listeDrugs = intersection(listeDrugs, d);
					}
					
					for(Disease d : listeDiseases){
						ResultsController.diseases.add(d);
					}
					for(String s : listeDrugs){
						ResultsController.drugs.add(s);
					}
			
			

			try {
				FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("view/ResultsOverview.fxml"));
				AnchorPane ResultsOverview =loader.load();
				this.mainApp.getRootLayout().setCenter(ResultsOverview);
				ResultsController controller = loader.getController(); 
				controller.setMainApp(this.mainApp);
				controller.initDiseases();
				controller.initDrug();
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
