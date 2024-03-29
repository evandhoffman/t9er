package com.evanhoffman.t9;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class T9Dictionary {

	private Map<Integer, DigitNode> dictionaryMap = new HashMap<Integer,DigitNode>();

	public Map<String, Integer> lookupWord(Integer... digits) {
		if (dictionaryMap.containsKey(digits[0])) {
			return dictionaryMap.get(digits[0]).lookupWord(digits);
		} else {
			return null;
		}
	}

	public void addWord(String word) {
		if (word.length() == 0) {
			return;
		}
		Integer firstDigit = DigitNode.digitizeLetter(word.charAt(0));
		if (dictionaryMap.containsKey(firstDigit)) {
			dictionaryMap.get(firstDigit).addWord(0, word);
		} else {
			DigitNode dn = new DigitNode(firstDigit);
			dn.addWord(0, word);
			dictionaryMap.put(firstDigit, dn);
		}
	}

	public void loadWords(InputStream is) {
		BufferedReader br = null;
		br = new BufferedReader(new InputStreamReader(is));

		String line = null;
		try {
			while ((line = br.readLine()) != null) {
				String words[] = line.toLowerCase().replaceAll("[^a-zA-Z]+", " ").split("\\s+");
				for (String w : words) {
					System.out.printf("%-10s|%-10s\n",w,DigitNode.digitizeWord(w));
					addWord(w);					
				}
			}
		} catch (IOException ie) {
			throw new RuntimeException(ie);
		}

	}

	public void loadWords(String filename) throws FileNotFoundException, IOException {
		FileInputStream is = new FileInputStream(filename); 
		loadWords(is);

	}
	
	public void serializeToFile(Writer writer) {
		PrintWriter pw = new PrintWriter(new BufferedWriter(writer));
		for (Integer digit : dictionaryMap.keySet()) {
			pw.println(dictionaryMap.get(digit));
			pw.flush();
		}
	}
	
	public void printCollisions(Writer writer) {
		PrintWriter pw = new PrintWriter(new BufferedWriter(writer));
		for (Integer digit : dictionaryMap.keySet()) {
			dictionaryMap.get(digit).printCollisions(pw);
		}
		pw.flush();
	}
	
	@Override
	public String toString() {
		return dictionaryMap.toString();
	}

	public static void main(String args[]) {
		
		T9Dictionary dict = new T9Dictionary();

		FileWriter writer = null;
		try {
			dict.loadWords(args[0]);
			writer = new FileWriter("/Users/evan/dictionary.out.txt");
//			dict.serializeToFile(writer);
			dict.printCollisions(writer);
			writer.close();
		} catch (IOException ie) {
			throw new RuntimeException(ie);
		}

		
		
	}

}
