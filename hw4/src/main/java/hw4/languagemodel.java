/*
 * Rosario Rivera
 * CSC 483
 * Homework 4
 * 4c: without smoothing most of the results would be 0, so there would be no substantial ranking
 * Notes: Used Merge Sort from http://www.vogella.com/tutorials/JavaAlgorithmsMergesort/article.html, 
 * 			but made my own modifications
 */
package hw4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import edu.stanford.nlp.simple.*;

public class languagemodel {
	static Sentence doc1;
	static Sentence doc2;
	static Sentence doc3;
	static Sentence doc4;
	static Sentence user;
	
	
	static List<String> lemma1;
	static List<String> lemma2;
	static List<String> lemma3;
	static List<String> lemma4;
	static List<String> lemmauser;
	
	static double total1;
	static double total2;
	static double total3;
	static double total4;
	
	static double totalSize;
	
	static Doc[] ranked;
	
	private static Doc[] numbers;
    private static Doc[] helper;

    private static int number;
	
	public static void main(String[] args)
	{
		doc1 = new Sentence("information retrieval is the most awesome class I ever took");
		doc2 = new Sentence("the retrieval of private information from your emails is a job that the NSA loves");
		doc3 = new Sentence("in the school of information you can learn about data science");
		doc4 = new Sentence("the labrador retriever is a great dog");
		//Sentence user;
		
		lemma1 = doc1.lemmas();
		lemma2 = doc2.lemmas();
		lemma3 = doc3.lemmas();
		lemma4 = doc4.lemmas();
		totalSize = lemma1.size()+lemma2.size()+lemma3.size()+lemma4.size();
		//lemmauser;
		
		Scanner scan = new Scanner(System.in);
		
		System.out.println("provide your query");
		user = new Sentence(scan.nextLine());
		lemmauser = user.lemmas();
		//System.out.println(lemmauser);
		//System.out.println(Collections.frequency(lemma3, lemmauser.get(0)));
		
		if(lemmauser.isEmpty()) // if no query
		{
			System.out.println("No query given.");
		}
		else if(lemmauser.size() > 1) // if multiple terms in query
		{
			multQuery();
		}
		else // if only one term in query
		{
			oneQuery();
		}
		Doc d1 = new Doc("Document 1", total1);
		Doc d2 = new Doc("Document 2", total2);
		Doc d3 = new Doc("Document 3", total3);
		Doc d4 = new Doc("Document 4", total4);
		ranked = new Doc[]{d1,d2,d3,d4};
		sort(ranked);
		//Collections.sort(ranked);
		//Collections.reverse(ranked);
		//System.out.println(ranked[0].getDoc());
		System.out.println("Document 1: "+total1);
		System.out.println("Document 2: "+total2);
		System.out.println("Document 3: "+total3);
		System.out.println("Document 4: "+total4);
		
		System.out.println("Ranking:");
		System.out.println(ranked[3].getDoc());
		System.out.println(ranked[2].getDoc());
		System.out.println(ranked[1].getDoc());
		System.out.println(ranked[0].getDoc());
		
		
		
	}

	static void multQuery()
	{
		total1 = listProb(lemma1,0);
		total2 = listProb(lemma2,0);
		total3 = listProb(lemma3,0);
		total4 = listProb(lemma4,0);
	}
	
	static void oneQuery()
	{
		String term = lemmauser.get(0);
		total1 = termProb(lemma1,term);
		total2 = termProb(lemma2, term);
		total3 = termProb(lemma3, term);
		total4 = termProb(lemma4, term);
	}
	
	static double termProb(List<String> lemma, String term)
	{
		return .5*((Collections.frequency(lemma, term)/(lemma.size()*1.0)))+((totalTerm(term)/totalSize));
	}
	
	static int totalTerm(String term)
	{
		return Collections.frequency(lemma1, term)+Collections.frequency(lemma2, term)+
				Collections.frequency(lemma3, term)+Collections.frequency(lemma4, term);
	}
	
	static double listProb(List<String> lemma, int num)
	{
		if (num >= lemmauser.size()-1)
		{
			return termProb(lemma, lemmauser.get(num));
		}
		
		return termProb(lemma, lemmauser.get(num))*listProb(lemma, num+1);
	}
	
	

    public static void sort(Doc[] values) {
            numbers = values;
            number = values.length;
            helper = new Doc[number];
            mergesort(0, number - 1);
    }

    private static void mergesort(int low, int high) {
            // check if low is smaller than high, if not then the array is sorted
            if (low < high) {
                    // Get the index of the element which is in the middle
                    int middle = low + (high - low) / 2;
                    // Sort the left side of the array
                    mergesort(low, middle);
                    // Sort the right side of the array
                    mergesort(middle + 1, high);
                    // Combine them both
                    merge(low, middle, high);
            }
    }

    private static void merge(int low, int middle, int high) {

            // Copy both parts into the helper array
            for (int i = low; i <= high; i++) {
                    helper[i] = numbers[i];
            }

            int i = low;
            int j = middle + 1;
            int k = low;
            // Copy the smallest values from either the left or the right side back
            // to the original array
            while (i <= middle && j <= high) {
                    if (helper[i].getAmount() <= helper[j].getAmount()) {
                            numbers[k] = helper[i];
                            i++;
                    } else {
                            numbers[k] = helper[j];
                            j++;
                    }
                    k++;
            }
            // Copy the rest of the left side of the array into the target array
            while (i <= middle) {
                    numbers[k] = helper[i];
                    k++;
                    i++;
            }

    }
	
}
