package tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import atc.SearchInATC;

public class TestATC {

	public static void main (String [] args) throws IOException {

		/* User enter disease, clinical sign and side effects */
		System.out.print("Enter your query: ");
		Scanner sc = new Scanner(System.in);
		String query = sc.nextLine();

		SearchInATC.parsingATC();
		String drug = SearchInATC.searchDrugById(query);
		System.out.println("Drug corresponding to the id "+query+": "+drug);
	}

}
