package main.java.assignment2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;

import java.io.File;

public class FileChooserController {

	@FXML private Label errorLabel;
	//Files are stored as the text contents of these fields so they can be edited and not ignored
	@FXML private TextField trainingTextField;
	@FXML private TextField testingTextField;

	public void initialize(){
		File initialDirectory = new File(".");
		trainingTextField.setText( initialDirectory.getAbsolutePath() );
		testingTextField.setText( initialDirectory.getAbsolutePath() );
	}

	public void handleTrainingButton( ActionEvent e ) {
		//Get what was typed in
		File currentDirectory = new File( trainingTextField.getText() );
		if ( !currentDirectory.isDirectory() ) {
			currentDirectory = new File( "." );
		}

		//Prompt user
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setInitialDirectory( currentDirectory );
		File newDirectory = directoryChooser.showDialog( Main.getStage() );

		//Check and save result
		if ( newDirectory != null ){
			trainingTextField.setText( newDirectory.getAbsolutePath() );
		}
	}

	public void handleTestingButton( ActionEvent e ){
		//Get what was typed in
		File currentDirectory = new File( testingTextField.getText() );
		if ( !currentDirectory.isDirectory() ) {
			currentDirectory = new File( "." );
		}

		//Prompt user
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setInitialDirectory( currentDirectory );
		File newDirectory = directoryChooser.showDialog( Main.getStage() );

		//Check and save result
		if ( newDirectory != null ){
			testingTextField.setText( newDirectory.getAbsolutePath() );
		}
	}

	public void handleNextButton( ActionEvent e ){
		//Check files
		File trainingDirectory = new File(trainingTextField.getText());
		File testingDirectory = new File(testingTextField.getText());
		if(trainingDirectory.isDirectory() && testingDirectory.isDirectory()){
			errorLabel.setText( "RUNNING, please wait." );
			//Files are valid, save and switch scenes
			Main.setTrainDirectory( trainingDirectory );
			Main.setTestDirectory( testingDirectory );
			try {
				Parent root = FXMLLoader.load( getClass().getResource( "results.fxml" ) );
				Main.setScene(root);
			}catch(Exception exception){
				exception.printStackTrace();
			}
		}else{
			errorLabel.setText("ERROR: Invalid path.");
		}

	}
}
