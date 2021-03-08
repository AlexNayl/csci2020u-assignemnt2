package assignment2;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.DirectoryChooser;

import java.io.File;


public class Controller {
	@FXML public TableView mainTableView;
	@FXML public Label accuracyLabel;
	@FXML public Label precisionLabel;

	public void initialize() {
		SpamDetector spamDetector = new SpamDetector();

		spamDetector.train(Main.getTrainDirectory());
		ObservableList<TestFile> results = spamDetector.test(Main.getTestDirectory());

		mainTableView.getItems().addAll(results);
	}

}
