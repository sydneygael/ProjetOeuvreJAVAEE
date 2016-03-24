package dao;

import meserreurs.MonException;
import java.util.*;

import metier.*;
import persistance.*;

public class Service {

	// Mise à jour des caractéristiques d'un adhérent
	// Le booleen indique s'il s'agit d'un nouvel adhérent, auquel cas on fait
	// une création

	public void insertAdherent(Adherent unAdherent) throws MonException {
		String mysql;

		DialogueBd unDialogueBd = DialogueBd.getInstance();
		try {
			mysql = "insert into adherent  (nom_adherent,prenom_adherent,ville_adherent)  " + "values ('"
					+ unAdherent.getNomAdherent();
			mysql += "'" + ",'" + unAdherent.getPrenomAdherent() + "','" + unAdherent.getVilleAdherent() + "')";

			unDialogueBd.insertionBD(mysql);
		} catch (MonException e) {
			throw e;
		}
	}

	// gestion des adherents
	// Consultation d'un adhérent par son numéro
	// Fabrique et renvoie un objet adhérent contenant le résultat de la requête
	// BDD
	public Adherent consulterAdherent(int numero) throws MonException {
		String mysql = "select * from adherent where numero_adherent=" + numero;
		List<Adherent> mesAdh = consulterListeAdherents(mysql);
		if (mesAdh.isEmpty())
			return null;
		else {
			return mesAdh.get(0);
		}
	}

	// Consultation des adhérents
	// Fabrique et renvoie une liste d'objets adhérent contenant le résultat de
	// la requête BDD
	public List<Adherent> consulterListeAdherents() throws MonException {
		String mysql = "select * from adherent";
		return consulterListeAdherents(mysql);
	}

	private List<Adherent> consulterListeAdherents(String mysql) throws MonException {
		List<Object> rs;
		List<Adherent> mesAdherents = new ArrayList<Adherent>();
		int index = 0;
		try {
			DialogueBd unDialogueBd = DialogueBd.getInstance();
			rs = DialogueBd.lecture(mysql);
			while (index < rs.size()) {
				// On crée un stage
				Adherent unA = new Adherent();
				// il faut redecouper la liste pour retrouver les lignes
				unA.setIdAdherent(Integer.parseInt(rs.get(index + 0).toString()));
				unA.setNomAdherent(rs.get(index + 1).toString());
				unA.setPrenomAdherent(rs.get(index + 2).toString());
				unA.setVilleAdherent(rs.get(index + 3).toString());
				// On incrémente tous les 3 champs
				index = index + 4;
				mesAdherents.add(unA);
			}

			return mesAdherents;
		} catch (Exception exc) {
			throw new MonException(exc.getMessage(), "systeme");
		}
	}

	/**
	 * permet de mettre à jour un adhérent
	 * 
	 * @param adherent
	 * @throws MonException
	 */
	public void modifierAdherent(Adherent adherent) throws MonException {
		String mysql;

		DialogueBd unDialogueBd = DialogueBd.getInstance();
		try {
			mysql = "UPDATE adherent SET "+
					"nom_adherent = '"+adherent.getNomAdherent()+"', "+
					"prenom_adherent = '"+adherent.getPrenomAdherent()+"', "+
					"ville_adherent = '"+adherent.getVilleAdherent()+"' "+
					"WHERE id_adherent = "+adherent.getIdAdherent();

			unDialogueBd.insertionBD(mysql);
		} catch (MonException e) {
			throw e;
		}
	}

	/**
	 * permet de supprimer un adhérent avec son id
	 * 
	 * @param numero integer
	 * @throws MonException
	 */
	public boolean supprimerAdherent(int id) throws MonException {
		String mysql = "DELETE FROM adherent WHERE id_adherent = "+id;

		DialogueBd unDialogueBd = DialogueBd.getInstance();
		try {


			unDialogueBd.insertionBD(mysql);
			return true;
		} catch (MonException e) {
			throw e;
		}
	}

	/**
	 * Consulter les adhérents par paquet
	 * Fabrique et renvoie les objets adhérent contenant le résultat de la requète
	 * 
	 * @throws MonException
	 */
	public List<Adherent> consulterListeAdherents(int page, int nombreParPage) throws MonException {
		String mysql = "SELECT * FROM adherent "+
				"ORDER BY id_adherent "+
				"LIMIT "+(page*nombreParPage)+","+nombreParPage;
		return consulterListeAdherents(mysql);
	}

	public Adherent getAdherent(int id) {
		Adherent adherent = new Adherent();

		try{
			for(Adherent a : consulterListeAdherents() ){
				if(a.getIdAdherent() == id){
					adherent = a;
					break;
				}
			}
		} catch (MonException e)
		{
			e.printStackTrace();
		}
		return adherent;
	}

}
