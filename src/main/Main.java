package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;

import model.Disease;
import model.Sign;
import sqlite.SQLiteDisease;

public class Main {

	public static void main(String[] args) {

		HashMap<Integer,Disease> liste = SQLiteDisease.getDiseases();
		HashMap<Integer,Disease> liste2 = SQLiteDisease.getDiseasesBySign(new Sign(28,""));
		System.out.println(liste2);
		
	}

}
