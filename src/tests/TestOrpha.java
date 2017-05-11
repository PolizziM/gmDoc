package tests;

import java.util.ArrayList;
import java.util.List;

import model.Disease;
import orphadata.SearchInOrpha;

public class TestOrpha {
	
	public static void main(String[] args) {
		
		List<Disease> l = new ArrayList<Disease>() ;
		List<Disease> l2 = new ArrayList<Disease>(); 
		SearchInOrpha c = new SearchInOrpha();
		try {
			l = c.getDiseaseBySign("Abnormal colour");
			//			System.out.println(l);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			for(Disease d : l){
				l2 = c.getDiseaseByName(d.getName());
				System.out.println(l2);
			}


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
