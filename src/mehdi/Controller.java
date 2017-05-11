package mehdi;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import static mehdi.resultsController.labelColumn;
import static mehdi.resultsController.resultsTableView;

import java.io.IOException;

public class Controller {

    /*
    TO DO:
    - use jfeonix package to show results in a drawer actually, seems smarter than opening a new stage like a pleb and more beautiful
    - swap search bar with text field from jfeonix package
    - look up jfeonix desktop notifications for team credits
    - add jfeonix loading animation while requests are being processed

     */


    // FXML Variables Declaration
    @FXML
    private Button exitButton;
    @FXML
    private ImageView exitImgPressed;
    @FXML
    private ImageView exitImgNotPressed;


    private final ObservableList<String> data =
            FXCollections.observableArrayList(
                    "jacob",
                    "bite",
                    "trou du cul"

            );

    /*
    the name speaks for itself.
     */
    public void handleSearchButton() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("ResultsWindow.fxml"));
        Parent root = loader.load();
        resultsController controller = loader.getController();
        Stage resultsStage = new Stage();
        resultsStage.setTitle("Here comes the drop!");
        resultsStage.setScene(new Scene(root));
        resultsStage.setResizable(false);
        resultsStage.initStyle(StageStyle.UNDECORATED);
        resultsStage.show();


        resultsTableView.setEditable(true);
        labelColumn.setCellValueFactory(new PropertyValueFactory<Integer, String>("cc"));



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
}
