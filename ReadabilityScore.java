package activities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReadabilityScore {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);

		String path = sc.nextLine();
		
		List<String> list = new ArrayList<> ();
		String[] arr;
		String read = "";
		int sentences = 0;
		int characters = 0; 
		int words = 0;
		int syllables = 0;
		int polysyllables = 0;
		String option = "";
		int ageOne;
		int ageTwo;
		int ageThree;
		int ageFour;
		double average;
		
		File file = new File(path);

		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line = null;
			
			while ((line = br.readLine()) != null) {
				list.add(line);
			}
			
		} catch (IOException e) {
			System.out.println("Error");
		}

		for (String s: list) {
			read += s;
		}

		arr = read.split("[?!\\.]");
		
		for (String s: arr) {
			sentences++;
		}
		
		arr = read
				.toLowerCase()
				.replaceAll("[!,\\.]", "")
				.replaceAll("you", "a")
				.replaceAll("[aeiouy]{2}", "a")
				.replaceAll("e\\b", "")
				.split("\\s+");

		for (String s: arr) {
			words++;
			syllables += syllables(s);
			if (syllables(s) > 2) {
				polysyllables++;
			}
		}
		
		arr = read.replaceAll("\\s","").split("");
		
		for (String s: arr) {
			characters++;
		}
		
		System.out.println("Words: "+ words);
		System.out.println("Sentences: "+ sentences);
		System.out.println("Characters: "+ characters);
		System.out.println("Syllables: "+ syllables);
		System.out.println("Polysyllables: " + polysyllables);
		System.out.print("Enter the score you want to calculate (ARI, FK, SMOG, CL, all):");
		option = sc.nextLine().toLowerCase();
		System.out.println();
		
		switch (option) {
			case "ari":
				ageOne = readabilityIndex(characters, words, sentences);
				break;	
			case "fk":
				ageOne = fleschKincaid(words, sentences, syllables);
				break;
			case "smog":
				ageOne = smog(polysyllables, sentences);
				break;
			case "cl":
				ageOne = colemanLiau(characters, words, sentences);
				break;
			case "all":
				ageOne = readabilityIndex(characters, words, sentences);
				ageTwo = fleschKincaid(words, sentences, syllables);
				ageThree= smog(polysyllables, sentences);
				ageFour = colemanLiau(characters, words, sentences);
				average = (ageOne + ageTwo + ageThree + ageFour) / 4;
				System.out.format("\nThis text should be understood in average by %.2f year olds.", average);
				break;
		}
		
	}

	public static int syllables(String word) {
			int a = 0;
			String[] arr = word.split("");
			for (String s: arr) {
				if (s.matches("[aeiouy]")) a++;
			}
			if (a == 0) {
				a++;
			}
			return a;
	}
	
	public static int colemanLiau(int characters, int words, int sentences) {
		double l = (double) characters / words * 100.00;
		double s = (double) sentences / words * 100.00;
		double sco = (0.0588 * l) - (0.296 * s) - 15.8;
		int age = range(sco);
		String result = String.format("%.2f", sco);
		if (age == 0) {
			System.out.println("Coleman–Liau index: "+ result +" (about year 24+ olds).");
			return age;
		}
		System.out.println("Coleman–Liau index: "+ result +" (about year "+ age + " olds).");
		return age;
	}
	
	public static int smog(int polysyllables, int sentences) {
		double sco = 1.043 * Math.sqrt(polysyllables * (30.0 / sentences)) + 3.1291;
		int age = range(sco);
		String result = String.format("%.2f", sco);
		if (age == 0) {
			System.out.println("Simple Measure of Gobbledygook: "+ result +" (about year 24+ olds).");
			return age;
		}
		System.out.println("Simple Measure of Gobbledygook: "+ result +" (about year "+ age + " olds).");
		return age;
	}
	
	public static int readabilityIndex(int characters, int words, int sentences) {
		double sco = 4.71 * characters / words + 0.5 * words / sentences - 21.43;
		int age = range(sco);
		String result = String.format("%.2f", sco);
		if (age == 0) {
			System.out.println("Automated Readability Index: "+ result +" (about year 24+ olds).");
			return age;
		}
		System.out.println("Automated Readability Index: "+ result +" (about year "+ age + " olds).");
		return age;
	}
	
	public static int fleschKincaid(int words, int sentences, int syllables) {
		double sco = 0.39 * words / sentences + 11.8 * syllables / words - 15.59;
		int age = range(sco);
		String result = String.format("%.2f", sco);
		if (age == 0) {
			System.out.println("Flesch–Kincaid readability tests: "+ result +" (about year 24+ olds).");
			return age;
		}
		System.out.println("Flesch–Kincaid readability tests: "+ result +" (about year "+ age + " olds).");
		return age;
	}
	
	public static int range(double sco) {
		if (Math.floor(sco) <= 1) {
			return 6;
		}
		else if (Math.floor(sco) == 2) {
			return 7;
		}
		else if (Math.floor(sco) == 3) {
			return 9;
		}
		else if (Math.floor(sco) == 4) {
			return 10;
		}
		else if (Math.floor(sco) == 5) {
			return 11;
		}
		else if (Math.floor(sco) == 6) {
			return 12;
		}
		else if (Math.floor(sco) == 7) {
			return 13;
		}
		else if (Math.floor(sco) == 8) {
			return 14;
		}
		else if (Math.floor(sco) == 9) {
			return 15;
		}
		else if (Math.floor(sco) == 10) {
			return 16;
		}
		else if (Math.floor(sco) == 11) {
			return 17;
		}
		else if (Math.floor(sco) == 12) {
			return 18;
		}
		else if (Math.floor(sco) == 13) {
			return 24;
		}
		else if (Math.floor(sco) == 14) {
			return 0;
		}
		return -1;
	}
}
