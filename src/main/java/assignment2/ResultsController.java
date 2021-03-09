package assignment2;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;


public class ResultsController {
	@FXML public TableView mainTableView;
	@FXML public Label accuracyLabel;
	@FXML public Label precisionLabel;

	public void initialize() {
		SpamDetector spamDetector = new SpamDetector();

		spamDetector.train(Main.getTrainDirectory());
		ObservableList<TestFile> results = spamDetector.test(Main.getTestDirectory());

		mainTableView.getItems().addAll(results);

		//Accuracy calculation
		int numGuesses = results.size();
		int numTruePositives = 0;
		int numFalsePositives = 0;
		int numTrueNegatives = 0;

		for(TestFile testFile : results){
			if (testFile.getSpamProbability() > 0.5){
				if(testFile.getActualClass().equals( "spam" )){
					numTruePositives++;
				}else{
					numFalsePositives++;
				}
			}else{
				if(testFile.getActualClass().equals( "ham" )){
					numTrueNegatives++;
				}
			}
		}

		double accuracy = (double)(numTruePositives + numTrueNegatives) / (double)numGuesses;
		double precision = (double)numTruePositives / (double)(numFalsePositives + numTrueNegatives);

		accuracyLabel.setText(Double.toString( accuracy ));
		precisionLabel.setText(Double.toString( precision ));

	}

}
