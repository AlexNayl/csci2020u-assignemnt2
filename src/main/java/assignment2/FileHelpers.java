package main.java.assignment2;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.TreeMap;

public class FileHelpers {

	/**
	 * Counts the frequency of each word in the file.
	 * @param file File to get word count from.
	 * @return Treemap of all words and how many times they appear
	 */
	public static TreeMap<String, Integer> getWordFreqCount( File file ) throws IOException {
		//I would've used a map anyways, so I decided to count words for little extra cost
		//In hindsight I could've used a set
		TreeMap<String, Integer> words = new TreeMap<>();
		Scanner scanner = new Scanner(file);
		scanner.useDelimiter( "[^a-zA-Z]+" );	//Delimiter = anything that is not a letter

		//For every word in the file
		while ( scanner.hasNext() ){
			String nextWord = scanner.next();
			nextWord = nextWord.toLowerCase();

			if( words.containsKey( nextWord ) ) {
				words.put(nextWord, words.get(nextWord) + 1); //Increment the word frequency by 1
			}else{
				words.put(nextWord, 1);	//initialize the word frequency to 1
			}
		}

		return words;
	}
}
