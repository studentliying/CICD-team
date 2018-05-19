package com.example.demo.controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Null;
import java.util.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@RestController
public class WordLadderController{
	private static File dictFile = new File("src\\main\\java\\com\\example\\demo\\dictionary.txt");
	private static Set<String> bigDict = new HashSet<String>();
	private static Set<String> smallDict = new HashSet<String>();
	private static Set<String> popDict = new HashSet<String>();
	private static Queue<Stack<String>> ladders = new LinkedList<Stack<String>>();
	
	private static String temp;

	private static String replaceOneChar(String str, int i, char ch)
	{
		String first = str.substring(0, i);
		String second = str.substring(i,i+1);
		String third = str.substring(i+1);
		return first + ch + third;
	}

	private static void createBigDict()
	{
		String word = null;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(dictFile));
			int index=0;
			while ((word = reader.readLine()) != null) {
				word = word.toLowerCase();
				bigDict.add(word);
				
				if (index == 0) temp=word;
				index++;
			}
		}
		catch (IOException e){e.printStackTrace();}
	}
	

	private static void createSmallDict(int len)
	{
		for(String word : bigDict)		
			if (word.length() == len)
				smallDict.add(word);	
	}
	
	

	private static String judgeTwoWords(String word1, String word2)
	{
		if (word1.length() == 0)
		{
			return "Have a nice day.";
			
		}
		if (word2.length() == 0)
		{
			return "Have a nice day.";
		}
		if (word1.length() != word2.length())
		{
			return "The two words must be the same length.";
		}
		word1 = word1.toLowerCase();
		word2 = word2.toLowerCase();
		if (word1.equals(word2))
		{
			return "The two words must be different.";
		}
		createSmallDict(word1.length());
		
		return null;
	}
	
	

	private static void createQueue(String word1)
	{
		word1 = word1.toLowerCase();
		popDict.clear();
		ladders.clear();
		Stack<String> ladder = new Stack<String>();
		ladder.push(word1);
		ladders.offer(ladder);
		popDict.add(word1);		
	}

	

	private static Set<String> findNeighbours(String word)
	{
		word = word.toLowerCase();
		Set<String> neighbourWords = new HashSet<String>();
		int size = word.length();
		for (int i = 0; i < size; i++)
		{			
			for (char j = 97; j <= 122; j++)
			{
				String temp = word;
				char ch = temp.charAt(i);
				if (ch != j )
				{
					temp = replaceOneChar(temp,i,j);				
					if (smallDict.contains(temp))
						neighbourWords.add(temp);
				}
			}
		}
		return neighbourWords;
	}
	

	private static Stack<String> findWord2(String word1,String word2)
	{
		word1 = word1.toLowerCase();
		word2 = word2.toLowerCase();
		while (!ladders.isEmpty())
		{
			Stack<String> ladder = ladders.poll();
			String topWord = ladder.peek();
			Set<String> neighbours = findNeighbours(topWord);
			if (neighbours.contains(word2))
			{
				ladder.push(word2);
				return ladder;
				
			}
			for (String word : neighbours)
			{				
				if (!(popDict.contains(word)))
				{
					Stack<String> copyLadder = (Stack<String>) ladder.clone();
					copyLadder.push(word);
					popDict.add(word);
					ladders.offer(copyLadder);
				}
			}
		}
		return null;
	}
	
	
	//@RequestParam("word1") String word1,@RequestParam("word2") String word2
	
	@RequestMapping("/wordladder")
	public String wordladderMain(@RequestParam("word1") String word1,@RequestParam("word2") String word2)
	{
		String result1 = judgeTwoWords(word1, word2);
		if(result1 != null)
			return result1;
		else {
		createBigDict();
		createQueue(word1);	
		Stack<String> result2 = findWord2(word1,word2);
		
		if (result2 == null)
			return "No wordladder exists from " + word2 + " to " + word1 + ".";
		else {
			String result3 = "A ladder from " + word2 + " to " + word1 + ":\n";
			while (!result2.isEmpty()) {
				result3 = result3 + result2.pop()+" ";
			}
			return result3;
		}
		
		}
	}
}
