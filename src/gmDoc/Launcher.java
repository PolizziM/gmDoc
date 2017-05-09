package gmDoc;

import java.io.IOException;

public class Launcher {
	
	public static void main (String [] args) throws IOException {
		
		/*SearchInOmim searchOmim = new SearchInOmim();
		//searchOmim.ReadOmimTxt();
		//searchOmim.ReadOmimCsv();*/
		
		SearchInStitchATC searchStitchATC = new SearchInStitchATC();
		//searchStitchATC.ReadStitchCsv();
		searchStitchATC.ReadATC();
	}

}
