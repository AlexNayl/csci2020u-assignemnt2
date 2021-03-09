/**
 * @author Alexander Naylor 100 696 151
 */

package main.java.assignment2;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {

    private static File trainDirectory;
    private static File testDirectory;
    private ObservableList<TestFile> results;

    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.stage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource( "fileChooser.fxml" ));
        primaryStage.setTitle("Assignment 2");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    /**
     * Changes the current scene
     * @param root fxml file parent for the desired scene
     */
    public static void setScene(Parent root){
        try {
            Main.stage.setScene( new Scene(root) );
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static Stage getStage(){
        return stage;
    }

    public static File getTrainDirectory() {
        return trainDirectory;
    }

    public static File getTestDirectory() {
        return testDirectory;
    }

    public static void setTrainDirectory( File trainDirectory ) {
        Main.trainDirectory = trainDirectory;
    }

    public static void setTestDirectory( File testDirectory ) {
        Main.testDirectory = testDirectory;
    }

    public static void main( String[] args) {
        launch(args);
    }
}
