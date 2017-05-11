package mehdi;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    // app dimensions
    private int res_x = 600;
    private int res_y = 400;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("main/MainWindow.fxml"));
        primaryStage.setTitle("gmDoc, a hypochondriac's best friend (lol)");
        primaryStage.setScene(new Scene(root, res_x, res_y));
        primaryStage.setResizable(false);


        primaryStage.initStyle(StageStyle.UNDECORATED); // allows the app to disable the ugly window controls ('x' and stuff)

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

}
