package com.evanhoffman.t9;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DigitNode {

	private List<Integer> prefixDigits = new LinkedList<Integer>();
	
	private int digit;

	private Map<Integer, DigitNode> subNodes = new HashMap<Integer, DigitNode>();

	private Map<String,Integer> wordList = new HashMap<String,Integer>();

	public DigitNode(int digit) {
		this.digit = digit;
	}
	
	public static String digitizeWord(String word) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < word.length(); i++) {
			sb.append(digitizeLetter(word.charAt(i)));
		}
		return sb.toString();
	}

	public void addWord(int index, String wordToAdd) {

//		if (index == 0) {
//			// Special case for the root node
//			Integer i = digitizeLetter(wordToAdd.charAt(index));
//			if (wordToAdd.length() > 1) {
//
//				if (subNodes.containsKey(i)) {
//					subNodes.get(i).addWord(index+1, wordToAdd);
//				} else {
//					DigitNode d = new DigitNode(i);
//					d.addWord(index+1, wordToAdd);
//					subNodes.put(i, d);
//				}
//			} else {
//				if (!wordList.contains(wordToAdd)) {
//					wordList.add(wordToAdd);
//				}			
//			}
//		} else
			if (index == (wordToAdd.length() - 1)) {
				if (!wordList.containsKey(wordToAdd)) {
					wordList.put(wordToAdd,1);

//					System.out.println("Index: "+index+", added word: "+wordToAdd);
					//				System.out.println("Added "+wordToAdd);
				} else {
					Integer cnt = wordList.get(wordToAdd);
					wordList.put(wordToAdd,cnt+1);
				}
			} 


			else {
				if (index < wordToAdd.length()) {
					Integer i = digitizeLetter(wordToAdd.charAt(index+1));
					//				System.out.println("Index: "+index+", word: "+wordToAdd+", digit: "+i);
					if (subNodes.containsKey(i)) {
						subNodes.get(i).addWord(index+1, wordToAdd);
					} else {
						DigitNode d = new DigitNode(i);
						d.prefixDigits.addAll(this.prefixDigits);
						d.prefixDigits.add(this.digit);
						
						d.addWord(index+1, wordToAdd);
						subNodes.put(i, d);
					}
				}
			}

	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Integer i : prefixDigits) {
			sb.append(i);
		}
		return 
		"{"+sb+digit+
		"=>"+wordList+
		", subNodes="+subNodes.values() +
		//		", hashCode="+hashCode()+
		"}";
	}

	private static Map<Character,Integer> letterMap = new HashMap<Character,Integer>();

	static {
		letterMap.put('a', 2);
		letterMap.put('b', 2);
		letterMap.put('c', 2);
		letterMap.put('d', 3);
		letterMap.put('e', 3);
		letterMap.put('f', 3);
		letterMap.put('g', 4);
		letterMap.put('h', 4);
		letterMap.put('i', 4);
		letterMap.put('j', 5);
		letterMap.put('k', 5);
		letterMap.put('l', 5);
		letterMap.put('m', 6);
		letterMap.put('n', 6);
		letterMap.put('o', 6);
		letterMap.put('p', 7);
		letterMap.put('q', 7);
		letterMap.put('r', 7);
		letterMap.put('s', 7);
		letterMap.put('t', 8);
		letterMap.put('u', 8);
		letterMap.put('v', 8);
		letterMap.put('w', 9);
		letterMap.put('x', 9);
		letterMap.put('y', 9);
		letterMap.put('z', 9);

	};

	public static int digitizeLetter(char c) {
		if (letterMap.containsKey(c)) {
			return letterMap.get(c);
		}
		throw new IllegalArgumentException("Invalid character: "+c);
	}

	public Map<String, Integer> lookupWord(Integer... digits) {
		if (digits.length == 1) {
			return wordList;
		} else {
			if (subNodes.containsKey(digits[0])) {
				Integer subArray[] = new Integer[digits.length - 1];
				for (Integer i = 1; i < digits.length; i++) {
					subArray[i] = digits[i+1];
				}
				DigitNode dn = subNodes.get(digits[0]);
				return dn.lookupWord(subArray);
//				dn.
			} else {
				return null;
			}
		}
	}


}
