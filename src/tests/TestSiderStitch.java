package tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.Sign;
import model.Stitch;
import mySql.MySqlSign;
import stitch.SearchInStitch;

public class TestSiderStitch {

	public static void main (String [] args) throws IOException {

		ArrayList<String> codesATC=new ArrayList<String>();
		List<Stitch> l = MySqlSign.getStitchesBySignsName(new Sign(0,"Abdominal cramps"));
		//System.out.println(l);
		SearchInStitch.parsingStitch();
		
		for (int i=0;i<l.size();i++) {
			String code = SearchInStitch.searchCodeAtcInStitch(l.get(i));
			if (code!=null) {
				codesATC.add(code);
			}
		}
		System.out.println(codesATC);
	}
	
}
