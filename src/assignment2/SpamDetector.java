package assignment2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.util.TreeMap;

public class SpamDetector {
	private static final String RELATIVE_HAM_PATH = "\\ham";
	private static final String RELATIVE_SPAM_PATH = "\\spam";

	private TreeMap<String, Integer> trainHamFreq;
	private TreeMap<String, Integer> trainSpamFreq;

	private int trainHamFileCount = 0;
	private int trainSpamFileCount = 0;
	private boolean trained = false;

	/**
	 * Trains the spam detector by creating a probability map of words and their probability of being spam
	 * @param trainingDirectory Location of training data (subfolders containing spam and ham data)
	 */
	public void train(File trainingDirectory)
	{
		//Testing and prepping training files
		if(!trainingDirectory.exists())
		{
			System.out.println("ERROR: Invalid training data directory.");
			return;
		}
		String absoluteMainPath = trainingDirectory.getAbsolutePath();
		File trainHamDirectory = new File(absoluteMainPath + RELATIVE_HAM_PATH);
		File trainSpamDirectory = new File(absoluteMainPath + RELATIVE_SPAM_PATH);
		if(!trainHamDirectory.exists() || !trainSpamDirectory.exists()){
			System.out.println("ERROR: Invalid training data format.");
			return;
		}
		File[] trainingHamFiles = trainHamDirectory.listFiles();
		File[] trainingSpamFiles = trainSpamDirectory.listFiles();

		//Loop through each file
		for(File currentFile : trainingHamFiles){
			if(!currentFile.isFile()){
				break;
			}
			//TODO: get word count
			trainHamFileCount++;
		}

		for(File currentFile : trainingSpamFiles){
			if(!currentFile.isFile()){
				break;
			}
			//TODO: get word count
			trainSpamFileCount++;
		}

		trained = true;
	}

	/**
	 * Caclulates the probability of each file being spam based on the words used in the file
	 * @param testDirectory Location of test data (subfolders containing spam and ham data)
	 * @return ObservableList of TestFiles, containing spam probability and actual class.
	 */
	public ObservableList<TestFile> test(File testDirectory)
	{
		if( !trained ) {
			System.out.println("ERROR: Cannot call test() on untrained SpamDetector object.");
			return null;
		}
		ObservableList<TestFile> results = FXCollections.observableArrayList();

		return results;
	}
}
