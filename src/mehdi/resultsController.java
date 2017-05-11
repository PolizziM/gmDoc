package mehdi;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class resultsController {

    // FXML Variables Declaration
    @FXML
    private Button exitButton;
    @FXML
    private ImageView exitImgPressed;
    @FXML
    private ImageView exitImgNotPressed;

    @FXML
    static TableView resultsTableView;

    @FXML
    static TableColumn<Integer, String> labelColumn;

    /*
    this closes the app when user presses the 'x' button.
     */
    public void handleResultsExitButton() throws IOException {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();

    }

    /*
    this shows the 'x' img when the mouse cursor enters the spot.
     */
    public void handleResultsShowExitButton() throws IOException {
        // System.out.println("THE MOUSE IS INSIDE, I REPEAT THE MOUSE IS IN");
        exitImgPressed.setOpacity(1);
        exitImgNotPressed.setOpacity(0);
    }

    /*
    this hides the 'x' img when the mouse cursor exists the spot.
     */
    public void handleResultsHideExitButton() throws IOException {
        // System.out.println("THE MOUSE IS INSIDE, I REPEAT THE MOUSE IS IN");
        exitImgPressed.setOpacity(0);
        exitImgNotPressed.setOpacity(1);
    }
}
