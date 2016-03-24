package controle;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.OeuvreService;
import dao.ReservationService;
import meserreurs.MonException;
import metier.Oeuvrevente;

@WebServlet("/ReservationControleur")
public class ReservationControleur extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String ACTION_TYPE = "action";
	private static final String ERROR_PAGE = "/erreur.jsp";
	private static final String LISTE_RESERVATION = "listerReservations";
	private static final String SUPPRIMER_RESERVATION = "supprimerReservation";
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ReservationControleur() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			processusTraiteRequete(request, response);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			processusTraiteRequete(request, response);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void processusTraiteRequete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, ParseException {
		String actionName = request.getParameter(ACTION_TYPE);
		String destinationPage = ERROR_PAGE;

		ReservationService unService = new ReservationService();
		if (LISTE_RESERVATION.equals(actionName)) {
			try {
				request.setAttribute("listeReservations", unService.consulterListeReservation());
			} catch (MonException e) {
				e.printStackTrace();
			}

			destinationPage = "/listeReservations.jsp";
		} else if (SUPPRIMER_RESERVATION.equals(actionName)) {
			int idOeuvre = Integer.parseInt(request.getParameter("txtid"));

			try {
				unService.supprimerReservation(idOeuvre);

				OeuvreService oeuvreService = new OeuvreService();
				Oeuvrevente oeuvre = oeuvreService.getOeuvre(idOeuvre);
				oeuvre.setEtatOeuvrevente("L");
				oeuvreService.mettreAJourOeuvreVente(oeuvre);

			} catch (MonException e) {
				e.printStackTrace();
			}
			destinationPage = "/OeuvreControleur?action=listerOeuvres";
		}

		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(destinationPage);
		dispatcher.forward(request, response);
	}
}
