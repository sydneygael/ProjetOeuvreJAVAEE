package dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.annotation.WebServlet;

import meserreurs.MonException;
import metier.Adherent;
import metier.Oeuvrevente;
import metier.Reservation;
import persistance.DialogueBd;

public class ReservationService {

	public void insererReservation(Reservation reservation)throws MonException {
		String mysql;

		DialogueBd unDialogueBd = DialogueBd.getInstance();
		try {
			mysql = "INSERT INTO reservation  (id_oeuvrevente, id_adherent, date_reservation, statut) values (" +
					"'" + reservation.getOeuvrevente().getIdOeuvrevente() +
					"','" + reservation.getAdherent().getIdAdherent() +
					"','" + new SimpleDateFormat("yyyy-MM-dd").format(reservation.getDate()) +
					"','" + reservation.getStatut() +
					"')";

			unDialogueBd.insertionBD(mysql);
		} catch (MonException e) {
			throw e;
		}
	}
	
	public void mettreAJourReservation(Reservation reservation, int ancinneOeuvre, int ancienAd) throws MonException {
		String mysql;

		DialogueBd unDialogueBd = DialogueBd.getInstance();
		try {
			mysql = "UPDATE reservation SET " +
					" date_reservation = '" + new SimpleDateFormat("yyyy-MM-dd").format(reservation.getDate()) + "', " +
					" id_oeuvrevente = '" + reservation.getOeuvrevente().getIdOeuvrevente() +  "', " +
					" id_adherent = '" + reservation.getAdherent().getIdAdherent() +  "' " +
					" WHERE id_oeuvrevente = " + ancinneOeuvre +
					" AND id_adherent = " + ancienAd;

			unDialogueBd.insertionBD(mysql);
		} catch (MonException e) {
			throw e;
		}
	}

	public List<Reservation> consulterListeReservation() throws MonException {
		String mysql = "select * from reservation";
		return consulterReservation(mysql);
	}

	private List<Reservation> consulterReservation(String mysql) throws MonException {
		List<Object> rs;
		List<Reservation> listeResa = new ArrayList<Reservation>();
		int index = 0;
		try {
			DialogueBd unDialogueBd = DialogueBd.getInstance();
			rs = DialogueBd.lecture(mysql);
			OeuvreService oeuvreService = new OeuvreService();
			Service adherentService = new Service();
			while (index < rs.size()) {
				// On cree un stage
				Reservation resaCible = new Reservation();
				Date date = null;
				try {
					date = new SimpleDateFormat("yyyy-MM-dd").parse(rs.get(index + 2).toString());
					String s = new SimpleDateFormat("yyyy-MM-dd").format(date);

				} catch (ParseException e) {
					e.printStackTrace();
				}
				// il faut redecouper la liste pour retrouver les lignes
				resaCible.setOeuvrevente(oeuvreService.getOeuvre(Integer.parseInt(rs.get(index + 0).toString())));
				resaCible.setAdherent(adherentService.getAdherent(Integer.parseInt(rs.get(index + 1).toString())));
				resaCible.setDate(date);

				index = index + 4;
				listeResa.add(resaCible);
			}

			return listeResa;
		} catch (Exception exc) {
			throw new MonException(exc.getMessage(), "systeme");
		}
	}

	public boolean supprimerReservation(int idOeuvre) throws MonException {
		String mysql;

		DialogueBd unDialogueBd = DialogueBd.getInstance();
		try {
			mysql = "DELETE FROM reservation WHERE id_oeuvrevente = " + idOeuvre ;

			unDialogueBd.insertionBD(mysql);
			return true;
		} catch (MonException e) {
			throw e;
		}
	}


}
