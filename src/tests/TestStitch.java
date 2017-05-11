package tests;

import java.io.IOException;
import java.util.Scanner;
import atc.SearchInATC;
import stitch.SearchInStitch;
import model.*;

public class TestStitch {
	
	public static void main (String [] args) throws IOException {

		/* User enter disease, clinical sign and side effects */
		System.out.print("Enter chemical: ");
		Scanner sc = new Scanner(System.in);
		String ch = sc.nextLine();
		System.out.print("Enter alias: ");
		Scanner sc1 = new Scanner(System.in);
		String al = sc.nextLine();
		Stitch s = new Stitch(ch,al);
		
		SearchInStitch.parsingStitch();
		String code = SearchInStitch.searchCodeAtcInStitch(s);
		System.out.println("Code corresponding: "+code);
		
	}	
}
