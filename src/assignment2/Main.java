/**
 * @author Alexander Naylor 100 696 151
 */

package assignment2;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Assignment 2");
        primaryStage.setScene(new Scene(root, 300, 275));

        //Get test and training directories from user
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("."));
        directoryChooser.setTitle("Choose the training folder.");
        File trainDirectory = directoryChooser.showDialog(primaryStage);
        directoryChooser.setTitle("Choose the testing folder.");
        File testDirectory = directoryChooser.showDialog(primaryStage);

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}