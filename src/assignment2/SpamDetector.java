package assignment2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

public class SpamDetector {
	private static final String RELATIVE_HAM_PATH = "\\ham";
	private static final String RELATIVE_SPAM_PATH = "\\spam";

	private TreeMap<String, Double> spamProbabilityMap;
	private boolean trained = false;

	/**
	 * Trains the spam detector by creating a probability map of words and their probability of being spam
	 * @param trainingDirectory Location of training data (subfolders containing spam and ham data)
	 */
	public void train(File trainingDirectory) {
		//----Frequency Map Generation----
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

		TreeMap<String, Integer> trainHamFreqMap = new TreeMap<>();
		TreeMap<String, Integer> trainSpamFreqMap = new TreeMap<>();
		int trainHamFileCount = 0;
		int trainSpamFileCount = 0;

		//Loop through each ham file
		for(File currentFile : trainingHamFiles){
			try{
				TreeMap<String,Integer> wordsFrequency = FileHelpers.getWordFreqCount( currentFile );
				Set<String> words = wordsFrequency.keySet();

				//For every word that appears in the file
				for(String word : words){
					if( trainHamFreqMap.containsKey( word )){

						trainHamFreqMap.put(word, trainHamFreqMap.get(word) + 1);	// Increment word frequency by 1
					}else{
						trainHamFreqMap.put(word, 1);							// Initialize word frequency to 1
					}
				}
				trainHamFileCount++;

			}catch(Exception e){
				System.out.println("ERROR: Missing file " + currentFile.getPath());
				break;
			}
		}

		//Loop through each spam file
		for(File currentFile : trainingSpamFiles){
			try{
				TreeMap<String,Integer> wordsFrequency = FileHelpers.getWordFreqCount( currentFile );
				Set<String> words = wordsFrequency.keySet();

				//For every word that appears in the file
				for(String word : words){
					if( trainSpamFreqMap.containsKey( word )){

						trainSpamFreqMap.put(word, trainSpamFreqMap.get(word) + 1);	// Increment word frequency by 1
					}else{
						trainSpamFreqMap.put(word, 1);							// Initialize word frequency to 1
					}
				}
				trainSpamFileCount++;

			}catch(Exception e){
				System.out.println("ERROR: Missing file " + currentFile.getPath());
				break;
			}
		}

		//----Proabaility map calculation----
		TreeMap<String, Double> spamContainsWordProbabilityMap = new TreeMap<>();	//Probability a spam file contains each word
		TreeMap<String, Double> hamContainsWordProbabilityMap = new TreeMap<>();	//Probability a ham file contains each word
		Set<String> spamWords = trainSpamFreqMap.keySet();
		Set<String> hamWords = trainHamFreqMap.keySet();

		//Caclulate probability maps for spam and ham
		for(String word : spamWords){
			spamContainsWordProbabilityMap.put(word, Double.valueOf(trainSpamFreqMap.get(word)) / trainSpamFileCount);
		}
		for(String word : hamWords){
			hamContainsWordProbabilityMap.put(word, Double.valueOf(trainHamFreqMap.get(word)) / trainHamFileCount);
		}

		Set<String> allWords = new HashSet<>( spamWords );
		allWords.addAll( hamWords );
		spamProbabilityMap = new TreeMap<>();

		//Calculate final probability map
		for(String word: allWords){
			Double spamContainsWordProbability = 0.0;
			Double hamContainsWordProbability = 0.0;

			if( spamContainsWordProbabilityMap.containsKey( word ) ){
				spamContainsWordProbability = spamContainsWordProbabilityMap.get( word );
			}
			if( hamContainsWordProbabilityMap.containsKey( word ) ){
				hamContainsWordProbability = hamContainsWordProbabilityMap.get( word );
			}

			Double spamProbability = spamContainsWordProbability / (spamContainsWordProbability + hamContainsWordProbability);
			spamProbabilityMap.put(word, spamProbability);
		}

		//System.out.println(spamProbabilityMap);
		trained = true;
	}

	/**
	 * Caclulates the probability of each file being spam based on the words used in the file
	 * @param testDirectory Location of test data (subfolders containing spam and ham data)
	 * @return ObservableList of TestFiles, containing spam probability and actual class.
	 */
	public ObservableList<TestFile> test(File testDirectory)
	{
		//----state checking and file prep----
		if( !trained ) {
			System.out.println("ERROR: Cannot call test() on untrained SpamDetector object.");
			return null;
		}

		if(!testDirectory.exists())
		{
			System.out.println("ERROR: Invalid test data directory.");
			return null;
		}
		String absoluteMainPath = testDirectory.getAbsolutePath();
		File hamDirectory = new File(absoluteMainPath + RELATIVE_HAM_PATH);
		File spamDirectory = new File(absoluteMainPath + RELATIVE_SPAM_PATH);
		if(!hamDirectory.exists() || !spamDirectory.exists()){
			System.out.println("ERROR: Invalid test data format.");
			return null;
		}

		File[] hamFiles = hamDirectory.listFiles();
		File[] spamFiles = spamDirectory.listFiles();
		ObservableList<TestFile> results = FXCollections.observableArrayList();

		//----File spam detection----
		//Loop though ham files
		for(File currentFile : hamFiles){
			try{
				TreeMap<String, Integer> wordsFrequency = FileHelpers.getWordFreqCount( currentFile );
				Set<String> words = wordsFrequency.keySet();
				//Implimentation of 'n' formula in assignment 1 pdf
				double n = 0;
				for(String word : words){
					if(spamProbabilityMap.containsKey( word )) {
						double wordProbability = spamProbabilityMap.get( word );
						if(wordProbability != 0) {		//Zeroes break this
							n += Math.log( 1 - wordProbability ) - Math.log( wordProbability );
						}
					}
				}
				//Implimentation of Pr formula in assignment 1 pdf
				double finalProbability = 1 / (1 + Math.pow(Math.E , n));
				TestFile testFile = new TestFile( currentFile.getName(), finalProbability, "ham");
				results.add(testFile);

			}catch(Exception e){
				System.out.println("ERROR: File does not exists" + currentFile.getPath());
			}

		}
		//Loop through spam files
		for(File currentFile : spamFiles){
			try{
				TreeMap<String, Integer> wordsFrequency = FileHelpers.getWordFreqCount( currentFile );
				Set<String> words = wordsFrequency.keySet();
				//Implimentation of 'n' formula in assignment 1 pdf
				double n = 0;
				for(String word : words){
					if(spamProbabilityMap.containsKey( word )) {
						double wordProbability = spamProbabilityMap.get( word );
						if(wordProbability != 0) {		//Zeroes break this
							n += Math.log( 1 - wordProbability ) - Math.log( wordProbability );
						}
					}
				}
				//Implimentation of Pr formula in assignment 1 pdf
				double finalProbability = 1 / (1 + Math.pow(Math.E , n));
				TestFile testFile = new TestFile( currentFile.getName(), finalProbability, "spam");
				results.add(testFile);

			}catch(Exception e){
				System.out.println("ERROR: File does not exists" + currentFile.getPath());
			}

		}


		return results;
	}
}
