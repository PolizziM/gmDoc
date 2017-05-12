package hpo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import application.model.*;

public class SQLiteDisease{
	private static SQLiteDisease instance;

	public static SQLiteDisease getInstance() {
		if (instance == null) {
			instance = new SQLiteDisease();
		}
		return instance;
	}

	public static List<Disease> getDiseases() {

		HashMap<Integer,Disease> liste = new HashMap<Integer,Disease>();
		try {
			if(ConnexionSQLite.getInstance().getConnexion() != null){
				Connection laConnexion = ConnexionSQLite.getInstance().getConnexion();
//			      Statement stmt = laConnexion.createStatement();
//			      ResultSet res = stmt.executeQuery( "SELECT * FROM phenotype_annotation;" );
				PreparedStatement requete = laConnexion.prepareStatement("SELECT disease_id, disease_label, sign_id FROM phenotype_annotation;");
				ResultSet res = requete.executeQuery();
				while (res.next()) {
					int id = res.getInt(1);
					String name = res.getString(2);
					String sign = res.getString(3);
					if(liste.containsKey(id)){
						liste.get(id).addSigne(sign);
					}
					else{
					Disease d = new Disease(id, name, new ArrayList<String>(),new ArrayList<String>());
					d.addSigne(sign);
					
					liste.put(id,d);
					}
				}
				if (res != null)
					res.close();
				if (requete != null)
					requete.close();
			}

		} catch (SQLException sqle) {
			System.out.println("Pb dans select disease " + sqle.getMessage());
		}
		List<Disease> res = new ArrayList<Disease>();
		for(Disease d : liste.values()){
			res.add(d);
		}
		return res;
	}

	public static List<Disease> getDiseasesBySign(Sign s) {

		HashMap<Integer,Disease> liste = new HashMap<Integer,Disease>();
		try {
			if(ConnexionSQLite.getInstance().getConnexion() != null){
				Connection laConnexion = ConnexionSQLite.getInstance().getConnexion();
//			      Statement stmt = laConnexion.createStatement();
//			      ResultSet res = stmt.executeQuery( "SELECT * FROM phenotype_annotation;" );
				String identifiant = String.format("%08d", s.getId());
				PreparedStatement requete = laConnexion.prepareStatement("SELECT disease_id, disease_label, sign_id FROM phenotype_annotation WHERE sign_id=\"HP:"+String.format("%07d", s.getId())+"\"");
				ResultSet res = requete.executeQuery();
				while (res.next()) {
					int id = res.getInt(1);
					String name = res.getString(2);
					name = name.replaceAll("^.?[0-9]* ?", "");
					  String[] splitArray = null;
					  splitArray = name.split(";");
					  name=splitArray[0];

					String sign = res.getString(3);
					if(liste.containsKey(id)){
						liste.get(id).addSigne(sign);
						  for(int j=1;j<splitArray.length;j++){
							  liste.get(id).addSynonyme(splitArray[j]);
						  }
					}
					else{
					Disease d = new Disease(id, name, new ArrayList<String>(),new ArrayList<String>());
					  for(int j=1;j<splitArray.length;j++){
						  d.addSynonyme(splitArray[j]);
					  }
					d.addSigne(sign);
					
					liste.put(id,d);
					}
				}
				if (res != null)
					res.close();
				if (requete != null)
					requete.close();
			}

		} catch (SQLException sqle) {
			System.out.println("Pb dans select disease " + sqle.getMessage());
		}

		List<Disease> res = new ArrayList<Disease>();
		for(Disease d : liste.values()){
			res.add(d);
		}
		return res;
	}



}
