package dao;

import java.util.ArrayList;
import java.util.List;

import meserreurs.MonException;
import metier.Oeuvrepret;
import metier.Oeuvrevente;
import metier.Proprietaire;
import persistance.DialogueBd;

/**
 * permet de gérer tous les services d'une oeuvre
 * @author Sydney
 *
 */
public class OeuvreService {


	  private static final String SYSTEME_ERROR = "erreur systeme";

	///////////////////////////////////
	  //traitements pour les oeuvre pret
	 ////////////////////////////////////
	
	/*****
	 * permet d'inserer oeuvre pret
	 * @param oeuvrePret
	 * @throws MonException
	 */
	public void insererOeuvrePret(Oeuvrepret oeuvrePret)throws MonException {
		String mysql = "INSERT INTO oeuvrepret  (titre_oeuvrepret, id_proprietaire) values (" +
				"'" + oeuvrePret.getTitreOeuvrepret() +
				"','" + oeuvrePret.getProprietaire().getIdProprietaire() + 
				"')";

		DialogueBd unDialogueBd = DialogueBd.getInstance();
		try {
			unDialogueBd.insertionBD(mysql);
		} catch (MonException e) {
			throw e;
		}
	}

	/**
	 * mets à jour une oeuvre pret
	 * @param oeuvrePret
	 * @throws MonException
	 */
	public void mettreAJourOeuvrePret(Oeuvrepret oeuvrePret) throws MonException {
		String mysql = "UPDATE oeuvrepret SET " +
				"titre_oeuvrepret = '"+oeuvrePret.getTitreOeuvrepret()+"', " +
				"id_proprietaire = '" + oeuvrePret.getProprietaire().getIdProprietaire()+"' " +
				"WHERE id_oeuvrepret = "+oeuvrePret.getIdOeuvrepret();
		
		DialogueBd unDialogueBd = DialogueBd.getInstance();
		try {
			unDialogueBd.insertionBD(mysql);
		} catch (MonException e) {
			throw e;
		}
	}
	

	/**
	 * 
	 * @param numero
	 * @return
	 * @throws MonException
	 */
	public Oeuvrepret consulterOeuvrePret(int numero) throws MonException {
		String mysql = "SELECT * FROM oeuvrepret WHERE id_oeuvrepret = " + numero;
		List<Oeuvrepret> oeuvresList = consulterListeOeuvrePret(mysql);
		if (oeuvresList.isEmpty())
			return null;
		else {
			return oeuvresList.get(0);
		}
	}

	public List<Oeuvrepret> consulterListeOeuvresPret() throws MonException {
		String mysql = "SELECT * FROM oeuvrepret";
		return consulterListeOeuvrePret(mysql);
	}

	public List<Oeuvrepret> consulterListeOeuvresPret(int page, int nombreParPage) throws MonException {
		String mysql = "SELECT * FROM oeuvrepret "+
				"ORDER BY id_oeuvrepret "+
				"LIMIT "+(page*nombreParPage)+","+nombreParPage;
		return consulterListeOeuvrePret(mysql);
	}

	private List<Oeuvrepret> consulterListeOeuvrePret(String mysql) throws MonException {
		List<Object> rs;
		List<Object> rp;
		List<Oeuvrepret> mesOeuvres = new ArrayList<Oeuvrepret>();
		int index = 0;
		int proprio = 0;
		try {
			DialogueBd unDialogueBd = DialogueBd.getInstance();
			rs = DialogueBd.lecture(mysql);
			String mysqlproprio = "select * from proprietaire WHERE id_proprietaire=";
			while (index < rs.size()) {
				// On crée un stage
				Oeuvrepret uneO = new Oeuvrepret();
				// il faut redecouper la liste pour retrouver les lignes
				uneO.setIdOeuvrepret(Integer.parseInt(rs.get(index + 0).toString()));
				uneO.setTitreOeuvrepret(rs.get(index + 1).toString());
				rp = DialogueBd.lecture(mysqlproprio.concat(rs.get(index + 2).toString()));
				Proprietaire unAd = new Proprietaire();
				
				// il faut redecouper la liste pour retrouver les lignes
				unAd.setIdProprietaire(Integer.parseInt(rp.get(proprio + 0).toString()));
				unAd.setNomProprietaire(rp.get(proprio + 1).toString());
				unAd.setPrenomProprietaire(rp.get(proprio + 2).toString());
				uneO.setProprietaire(unAd);
				// On incrémente tous les 3 champs
				index = index + 3;
				mesOeuvres.add(uneO);
			}

			return mesOeuvres;
		} catch (Exception e) {
			throw new MonException(e.getMessage(), SYSTEME_ERROR);
		}
	}

	public boolean supprimerOeuvrePret(int id) throws MonException {
		String mysql;

		DialogueBd unDialogueBd = DialogueBd.getInstance();
		try {
			mysql = "DELETE FROM oeuvrepret WHERE id_oeuvrepret = "+id;

			unDialogueBd.insertionBD(mysql);
			return true;
		} catch (MonException e) {
			throw e;
		}
	}
	
	
	/////////////////////////////////////////////////////////////
	//	OEUVRE VENTE
	////////////////////////////////////////////////////////////
	
	/**
	 * permet d'insérer une oeuvre vente en base de données
	 * @param oeuvreVente
	 * @throws MonException
	 */
	public void insererOeuvreVente(Oeuvrevente oeuvreVente)throws MonException {
		String mysql = "INSERT INTO oeuvrevente  (titre_oeuvrevente, etat_oeuvrevente,prix_oeuvrevente, id_proprietaire) values (" +
				"'" + oeuvreVente.getTitreOeuvrevente() +
				"','" + oeuvreVente.getEtatOeuvrevente() + 
				"','" + oeuvreVente.getPrixOeuvrevente() +
				"','" + oeuvreVente.getProprietaire().getIdProprietaire() + 
				"')";

		DialogueBd unDialogueBd = DialogueBd.getInstance();
		try {
			unDialogueBd.insertionBD(mysql);
		} catch (MonException e) {
			throw e;
		}
	}
	
	/**
	 * 
	 * @param oeuvreVente
	 * @throws MonException
	 */
	public void mettreAJourOeuvreVente(Oeuvrevente oeuvreVente) throws MonException {
		String mysql = "UPDATE oeuvrevente SET "+
				"titre_oeuvrevente = '"+oeuvreVente.getTitreOeuvrevente()+"', "+
				"etat_oeuvrevente = '"+oeuvreVente.getEtatOeuvrevente()+"', "+
				"prix_oeuvrevente = '"+oeuvreVente.getPrixOeuvrevente()+"', "+
				"id_proprietaire = '" + oeuvreVente.getProprietaire().getIdProprietaire()+"' "+
				"WHERE id_oeuvrevente = "+oeuvreVente.getIdOeuvrevente();

		DialogueBd unDialogueBd = DialogueBd.getInstance();
		try {
			unDialogueBd.insertionBD(mysql);
		} catch (MonException e) {
			throw e;
		}
	}
	
	public Oeuvrevente consulterOeuvrevente(int numero) throws MonException {
		String mysql = "SELECT * FROM oeuvrevente WHERE id_oeuvrevente = " + numero;
		List<Oeuvrevente> mesOeuvresVentes = consulterListeOeuvreVendre(mysql);
		if (mesOeuvresVentes.isEmpty())
			return null;
		else {
			return mesOeuvresVentes.get(0);
		}
	}
	
	public List<Oeuvrevente> consulterListeOeuvresVentes() throws MonException {
		String mysql = "SELECT * FROM oeuvrevente";
		return consulterListeOeuvreVendre(mysql);
	}
	
	public List<Oeuvrevente> consulterListeOeuvresVentes(int page, int nombreParPage) throws MonException {
		String mysql = "SELECT * FROM oeuvrevente "+
					   "ORDER BY id_oeuvrevente "+
					   "LIMIT "+(page*nombreParPage)+","+nombreParPage;
		return consulterListeOeuvreVendre(mysql);
	}
	
	private List<Oeuvrevente> consulterListeOeuvreVendre(String mysql) throws MonException {
		List<Object> rs;
		List<Object> rp;
		List<Oeuvrevente> mesOeuvres = new ArrayList<Oeuvrevente>();
		int index = 0;
		int proprio = 0;
		try {
			DialogueBd unDialogueBd = DialogueBd.getInstance();
			rs = DialogueBd.lecture(mysql);
			String mysqlproprio = "select * from proprietaire WHERE id_proprietaire=";
			
			while (index < rs.size()) {
				// On crée un stage
				Oeuvrevente unA = new Oeuvrevente();
				
				// il faut redecouper la liste pour retrouver les lignes
				unA.setIdOeuvrevente(Integer.parseInt(rs.get(index + 0).toString()));
				unA.setTitreOeuvrevente(rs.get(index + 1).toString());
				unA.setEtatOeuvrevente(rs.get(index + 2).toString());
				unA.setPrixOeuvrevente(Float.parseFloat(rs.get(index + 3).toString()));
				rp = DialogueBd.lecture(mysqlproprio.concat(rs.get(index + 4).toString()));
				Proprietaire unAd = new Proprietaire();
				
				// il faut redecouper la liste pour retrouver les lignes
				unAd.setIdProprietaire(Integer.parseInt(rp.get(proprio + 0).toString()));
				unAd.setNomProprietaire(rp.get(proprio + 1).toString());
				unAd.setPrenomProprietaire(rp.get(proprio + 2).toString());
				unA.setProprietaire(unAd);
				
				// On incrémente tous les 3 champs
				index = index + 5;
				mesOeuvres.add(unA);
			}

			return mesOeuvres;
			
		} catch (Exception e) {
			throw new MonException(e.getMessage(), SYSTEME_ERROR);
		}
	}
	
	//permet de suppprimer une oeuvre vente
	public boolean supprimerOeuvreVente(int id) throws MonException {
		String mysql = "DELETE FROM oeuvrevente WHERE id_oeuvrevente = "+id;

		DialogueBd unDialogueBd = DialogueBd.getInstance();
		try {
			unDialogueBd.insertionBD(mysql);
			return true;
		} catch (MonException e) {
			throw e;
		}
	}

	public List<Oeuvrevente> consulterListOeuvresReservables() throws MonException {
		String mysql = "select * from oeuvrevente WHERE etat_oeuvrevente ='L' ";
		return consulterListeOeuvreVendre(mysql);
	}

	public Oeuvrevente getOeuvre(int id) {
		  Oeuvrevente oeuvre = new Oeuvrevente();

	        try{
	            for(Oeuvrevente o : consulterListeOeuvresVentes() ){
	                if(o.getIdOeuvrevente() == id){
	                    oeuvre = o;
	                    break;
	                }
	            }
	        } catch (MonException e)
	        {
	            e.printStackTrace();
	        }
	        return oeuvre;
	    }
}
