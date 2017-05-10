package mySql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import model.Disease;

public class MySqlDisease {

	private static MySqlDisease instance;

	public static MySqlDisease getInstance() {
		if (instance == null) {
			instance = new MySqlDisease();
		}
		return instance;
	}

	public List<Disease> getDiseases() {

		ArrayList<Disease> liste = new ArrayList<Disease>();
		try {
			if(ConnexionMySQL.getInstance().getConnexion() != null){
				Connection laConnexion = ConnexionMySQL.getInstance().getConnexion();
				PreparedStatement requete = laConnexion.prepareStatement("SELECT * FROM Disease");
				ResultSet res = requete.executeQuery();
				while (res.next()) {
					int id = res.getInt(1);
					String nom = res.getString(2);
					String prenom = res.getString(3);
					String email = res.getString(4);
					String photo = res.getString(5);
					Date date = res.getDate(6);
					String niveau = res.getString(7);
					String sexe = res.getString(8);
					Boolean ceten = res.getBoolean(9);
					String telephone = res.getString(10);
					int cipa = res.getInt(11);
					Boolean bde = res.getBoolean(12);
//					Disease e = new Disease(id,nom,prenom,email,photo,date,niveau,sexe,ceten,telephone,cipa,bde);
//					liste.add(e);
				}
				if (res != null)
					res.close();
				if (requete != null)
					requete.close();
			}

		} catch (SQLException sqle) {
			System.out.println("Pb dans select Disease " + sqle.getMessage());
		}

		return liste;
	}
	

}
