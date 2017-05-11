package tests;

import java.io.IOException;
import java.util.Scanner;
import atc.SearchInATC;
import stitch.SearchInStitch;
import model.*;

public class TestStitch {
	
	public static void main (String [] args) throws IOException {

	
		String ch = "CIDm00025959";
		String al = "CIDs00025959";
		Stitch s = new Stitch(ch,al);
		
		SearchInStitch.parsingStitch();
		String code = SearchInStitch.searchCodeAtcInStitch(s);
		System.out.println("Code corresponding: "+code);
		
	}	
}
