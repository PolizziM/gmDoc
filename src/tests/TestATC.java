package tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import atc.SearchInATC;

public class TestATC {

	public static void main (String [] args) throws IOException {

		/* Query */
		String query = "A01AB03";

		SearchInATC.parsingATC();
		String drug = SearchInATC.searchDrugById(query);
		System.out.println("Drug corresponding to the id "+query+": "+drug);
	}

}
