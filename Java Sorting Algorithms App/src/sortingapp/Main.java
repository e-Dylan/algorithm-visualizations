package sortingapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sortingAppUI.fxml"));

        // Initializing the program scene such that a css stylesheet can be added.
        Scene scene = new Scene(root);
        scene.getStylesheets().add("sortingapp/styles.css");

        primaryStage.setTitle("Sorting App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
