package application;

import java.io.IOException;

import application.view.HomeController;
import application.view.ResultsController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainApp extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;
	private double xOffset = 0;
	private double yOffset = 0;


	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("GMDoc");
		primaryStage.setResizable(true);



		initRootLayout();
		showHomeOverview();
			Initialisation initialisation = new Initialisation();
			initialisation.start(); //THREAD 

	}

	/**
	 * Initializes the root layout.
	 */
	public void initRootLayout() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
			primaryStage.initStyle(StageStyle.UNDECORATED); // allows the app to disable the ugly window controls ('x' and stuff)
			rootLayout = (BorderPane) loader.load();
            rootLayout.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    xOffset = event.getSceneX();
                    yOffset = event.getSceneY();
                }
            });
            rootLayout.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    primaryStage.setX(event.getScreenX() - xOffset);
                    primaryStage.setY(event.getScreenY() - yOffset);
                }
            });
			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



	/**
	 * Shows the person overview inside the root layout.
	 */
	public void showHomeOverview() {
		try {
			FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("view/HomeOverview.fxml"));
			AnchorPane HomeOverview = loader.load();
			rootLayout.setCenter(HomeOverview);
			HomeController controller = loader.getController(); 
			controller.setMainApp(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void showResultsOverview() {
		try {
			FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("view/ResultsOverview.fxml"));
			AnchorPane ResultsOverview =loader.load();
			rootLayout.setCenter(ResultsOverview);
			ResultsController controller = loader.getController(); 
			controller.setMainApp(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	public BorderPane getRootLayout() {
		return rootLayout;
	}

	public void setRootLayout(BorderPane rootLayout) {
		this.rootLayout = rootLayout;
	}

	/**
	 * Returns the main stage.
	 * @return
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void main(String[] args) {
		launch(args);
	}
}