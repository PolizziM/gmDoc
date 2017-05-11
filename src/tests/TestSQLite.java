package tests;

import java.util.HashMap;

import model.Disease;
import model.Sign;
import sqlite.SQLiteDisease;

public class TestSQLite {

	public static void main(String[] args) {

		HashMap<Integer,Disease> liste = SQLiteDisease.getDiseases();
		HashMap<Integer,Disease> liste2 = SQLiteDisease.getDiseasesBySign(new Sign(28,""));
		System.out.println(liste2);
		
	}

}
