package sider;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnexionMySQL {

	private static ConnexionMySQL instance = null;

	private Connection connexion;

	private Properties proprietes;

	/**
	 * Constructeur : création de la connexion grâce au chargement des données
	 * dans un fichier Properties
	 *
	 */
	private ConnexionMySQL() {

		this.chargeDonneesConnexion();
		this.connexion = null;
		this.demandeConnexion();
	}

	/**
	 * Accesseur
	 * 
	 * @return cette instance
	 */
	public static ConnexionMySQL getInstance() {

		if (instance == null) {
			instance = new ConnexionMySQL();
		}
		return instance;
	}

	/**
	 * Accesseur
	 * 
	 * @return l'objet de connexion
	 */
	public Connection getConnexion() {

			try {
				if (this.connexion.isClosed()) {
					this.demandeConnexion();
				}
			} catch (SQLException se) {
				System.out.println("getConnexion " + se.getMessage());
			}
		
		return this.connexion;
	}

	/**
	 * Fermeture de la connexion
	 *
	 */
	public void ferme() {

			try {
				this.connexion.close();
			} catch (SQLException e) {
				System.out.println("Pb fermeture bdd " + e.getMessage());
			}
		
	}

	/**
	 * Connexion à MySQL
	 * 
	 */
	private void demandeConnexion() {
		String url = "jdbc:mysql://";
		url += this.proprietes.getProperty("adresse_ip") + ":" + this.proprietes.getProperty("port");
		url += "/" + this.proprietes.getProperty("bdd");

		String login = this.proprietes.getProperty("login");
		String mdp = this.proprietes.getProperty("pass");

			try {

				this.connexion = DriverManager.getConnection(url, login, mdp);

			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println(e);
			}
		
	}

	/**
	 * Création d'un objet properties et remplissage à partir du fichier contenu
	 * dans le chemin donné
	 * 
	 */
	private void chargeDonneesConnexion() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}


		ClassLoader cl = this.getClass().getClassLoader();
		URL url = cl.getResource("mySql.properties");

		this.proprietes = new Properties();
		try {
			this.proprietes.loadFromXML(url.openStream());
		} catch (IOException ioe) {
			System.out
			.println("Probl�me � la lecture du fichier de config bdd");
			System.out.println(ioe.getMessage());
		}

	}

}
