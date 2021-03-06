package sider;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import application.model.*;

public class MySqlSign {

	private static MySqlSign instance;

	public static MySqlSign getInstance() {
		if (instance == null) {
			instance = new MySqlSign();
		}
		return instance;
	}

	public static List<Stitch> getStitches() {

		ArrayList<Stitch> liste = new ArrayList<Stitch>();
		try {
			if(ConnexionMySQL.getInstance().getConnexion() != null){
				Connection laConnexion = ConnexionMySQL.getInstance().getConnexion();
				PreparedStatement requete = laConnexion.prepareStatement("SELECT stitch_compound_id1, stitch_compound_id2 FROM meddra_all_se");
				ResultSet res = requete.executeQuery();
				while (res.next()) {
					Stitch s = new Stitch(res.getString(1),res.getString(2));
					liste.add(s);
				}
				if (res != null)
					res.close();
				if (requete != null)
					requete.close();
			}

		} catch (SQLException sqle) {
			System.out.println("Pb dans select Stitches " + sqle.getMessage());
		}

		return liste;
	}
	
	public static List<Stitch> getStitchesBySignsName(Sign s) {

		ArrayList<Stitch> liste = new ArrayList<Stitch>();
		try {
			if(ConnexionMySQL.getInstance().getConnexion() != null){
				Connection laConnexion = ConnexionMySQL.getInstance().getConnexion();
				PreparedStatement requete = laConnexion.prepareStatement("SELECT stitch_compound_id1, stitch_compound_id2 FROM meddra_all_se WHERE LOWER(side_effect_name)=\""+s.getName().trim().toLowerCase()+"\"");
				ResultSet res = requete.executeQuery();
				while (res.next()) {
					Stitch stitch = new Stitch(res.getString(1),res.getString(2));
					liste.add(stitch);
				}
				if (res != null)
					res.close();
				if (requete != null)
					requete.close();
			}

		} catch (SQLException sqle) {
			System.out.println("Pb dans select Stitches By Name " + sqle.getMessage());
		}

		return liste;
	}
	
	public static List<Stitch> getStitchesByCui(String s) {

		ArrayList<Stitch> liste = new ArrayList<Stitch>();
		try {
			if(ConnexionMySQL.getInstance().getConnexion() != null){
				Connection laConnexion = ConnexionMySQL.getInstance().getConnexion();
				PreparedStatement requete = laConnexion.prepareStatement("SELECT stitch_compound_id1, stitch_compound_id2 FROM meddra_all_se WHERE LOWER(cui)=\""+s.trim().toLowerCase()+"\"");
				ResultSet res = requete.executeQuery();
				while (res.next()) {
					Stitch stitch = new Stitch(res.getString(1),res.getString(2));
					liste.add(stitch);
				}
				if (res != null)
					res.close();
				if (requete != null)
					requete.close();
			}

		} catch (SQLException sqle) {
			System.out.println("Pb dans select Stitches By cui" + sqle.getMessage());
		}
		return liste;
	}
	
	public static List<String> getStitchByCui(String s) {

		ArrayList<String> liste = new ArrayList<String>();
		try {
			if(ConnexionMySQL.getInstance().getConnexion() != null){
				Connection laConnexion = ConnexionMySQL.getInstance().getConnexion();
				PreparedStatement requete = laConnexion.prepareStatement("SELECT stitch_compound_id FROM meddra_all_indications WHERE LOWER(cui)=\""+s.trim().toLowerCase()+"\"");
				ResultSet res = requete.executeQuery();
				while (res.next()) {
		
					liste.add(res.getString(1));
				}
				if (res != null)
					res.close();
				if (requete != null)
					requete.close();
			}

		} catch (SQLException sqle) {
			System.out.println("Pb dans select Stitch by cui " + sqle.getMessage());
		}
		return liste;
	}
	
	public static List<String> getCuiBySignsName(Sign s) {

		ArrayList<String> liste = new ArrayList<String>();
		try {
			if(ConnexionMySQL.getInstance().getConnexion() != null){
				Connection laConnexion = ConnexionMySQL.getInstance().getConnexion();
				PreparedStatement requete = laConnexion.prepareStatement("SELECT cui FROM meddra WHERE LOWER(label)=\""+s.getName().trim().toLowerCase()+"\"");
				ResultSet res = requete.executeQuery();
				while (res.next()) {

					liste.add(res.getString(1));
				}
				if (res != null)
					res.close();
				if (requete != null)
					requete.close();
			}

		} catch (SQLException sqle) {
			System.out.println("Pb dans select Cui By Name " + sqle.getMessage());
		}

		return liste;
	}
	

}
