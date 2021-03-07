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

    private static File trainDirectory;
    private static File testDirectory;

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Get test and training directories from user
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("."));
        directoryChooser.setTitle("Choose the training folder.");
        trainDirectory = directoryChooser.showDialog(primaryStage);
        directoryChooser.setTitle("Choose the testing folder.");
        testDirectory = directoryChooser.showDialog(primaryStage);

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Assignment 2");
        primaryStage.setScene(new Scene(root, 600, 600));
        primaryStage.show();
    }

    public static File getTrainDirectory() {
        return trainDirectory;
    }

    public static File getTestDirectory() {
        return testDirectory;
    }

    public static void main( String[] args) {
        launch(args);
    }
}
