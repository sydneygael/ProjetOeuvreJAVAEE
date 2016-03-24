package controle;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.OeuvreService;
import dao.ProprietaireService;
import dao.ReservationService;
import dao.Service;
import meserreurs.MonException;
import metier.Adherent;
import metier.Oeuvrevente;
import metier.Proprietaire;
import metier.Reservation;

@WebServlet("/OeuvreControleur")
public class OeuvreControleur extends HttpServlet {

	private static final String ACTION_TYPE = "action";
	private static final String INSERER_OEUVRE = "insererOeuvre";
	private static final String AJOUTER_OEUVRE = "ajouterOeuvre";
	private static final String MODIFIER_OEUVRE = "modifierOeuvre";
	private static final String LISTER_OEUVRES = "listerOeuvres";
	private static final String RESERVER_OEUVRES ="reserverOeuvres";
	private static final String RESERVER_OEUVRE ="reserverOeuvre";
	private static final String ERROR_PAGE = "/erreur.jsp";

	public OeuvreControleur() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		processusTraiteRequete(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		processusTraiteRequete(request, response);
	}

	protected void processusTraiteRequete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String actionName = request.getParameter(ACTION_TYPE);
		String destinationPage = ERROR_PAGE;
		destinationPage = "/oeuvreIndex.jsp";
		// execute l'action
		if (LISTER_OEUVRES.equals(actionName)) {
			try {

				OeuvreService oService = new OeuvreService();
				Service unService = new Service();
				ProprietaireService pService = new ProprietaireService();

				request.setAttribute("mesOeuvresPret", oService.consulterListeOeuvresPret());
				request.setAttribute("mesOeuvresVente", oService.consulterListeOeuvresVentes());
				request.setAttribute("listeAdherents", unService.consulterListeAdherents());
				request.setAttribute("listeProprietaires", pService.consulterListeProprietaires());


			} catch (MonException e) {
				request.setAttribute("erreur", e.getMessage());
				destinationPage = ERROR_PAGE;
			}

			destinationPage = "/listerOeuvres.jsp";
		}

		else if (AJOUTER_OEUVRE.equals(actionName)) {

			try {

				OeuvreService oService = new OeuvreService();
				ProprietaireService pService = new ProprietaireService();
				request.setAttribute("mesOeuvresReservable", oService.consulterListOeuvresReservables());
				request.setAttribute("mesProprietaires", pService.consulterListeProprietaires());
			} catch (MonException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			destinationPage = "/ajouterOeuvre.jsp";
		}

		else if (RESERVER_OEUVRES.equals(actionName)) {
			try {

				OeuvreService unService = new OeuvreService();
				request.setAttribute("mesOeuvresReservable", unService.consulterListOeuvresReservables());

			} catch (MonException e) {
				request.setAttribute("erreur", e.getMessage());
				destinationPage = ERROR_PAGE;
			}

			destinationPage = "/reserverOeuvre.jsp";

		}

		else if (INSERER_OEUVRE.equals(actionName)) {
			try {
				//services dont on aura besoin
				ProprietaireService pService = new ProprietaireService();
				OeuvreService oService = new OeuvreService();
				Oeuvrevente ov = new Oeuvrevente();
				ov.setEtatOeuvrevente("L");

				String expressionR = "^([+]?\\d*\\.?\\d*)$";

				if (!request.getParameter("txtPrix").equals("") && request.getParameter("txtPrix").matches(expressionR)) {
					ov.setPrixOeuvrevente(Float.parseFloat(request.getParameter("txtPrix")));
				}

				Proprietaire proprietaire = new Proprietaire();
				for(Proprietaire p : pService.consulterListeProprietaires())
				{
					if(p.getNomProprietaire().equals(request.getParameter("ddlProp").split(" ")[0]))
					{
						proprietaire = p;
						break;
					}
				}


				ov.setProprietaire(proprietaire);
				ov.setTitreOeuvrevente(request.getParameter("txtTitre"));
				oService.insererOeuvreVente(ov);

			} catch (MonException e)
			{
				request.setAttribute("erreur", e.getMessage());
				destinationPage = ERROR_PAGE;
			}
			destinationPage = "/listerOeuvres.jsp";
		}

		else if (RESERVER_OEUVRE.equals(actionName)) {
			OeuvreService oService = new OeuvreService();
			ReservationService rService = new ReservationService();
			Service aService = new Service();

			Oeuvrevente oeuvre = oService.getOeuvre(Integer.parseInt(request.getParameter("txtidoeuvre")));
			Adherent adherent = aService.getAdherent(Integer.parseInt(request.getParameter("selectAd")));

			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date date = null;


			try {
				date = formatter.parse(request.getParameter("maDate"));
				oeuvre.setEtatOeuvrevente("R");
				oService.mettreAJourOeuvreVente(oeuvre);
				Reservation reservation = new Reservation(date,adherent,oeuvre);
				reservation.setStatut("confirmee");
				rService.insererReservation(reservation);
			} catch (MonException e) {
				request.setAttribute("erreur", e.getMessage());
				destinationPage = ERROR_PAGE;
			} catch (ParseException e) {
				request.setAttribute("erreur", e.getMessage());
				destinationPage = ERROR_PAGE;
			}

			try {
				request.setAttribute("mesOeuvresReservable", oService.consulterListOeuvresReservables());
			} catch (MonException e) {
				request.setAttribute("erreur", e.getMessage());
				destinationPage = ERROR_PAGE;
			}

			destinationPage = "/listerOeuvres.jsp";
		}

		else if (MODIFIER_OEUVRE.equals(actionName)) {
			OeuvreService oService = new OeuvreService();
			ProprietaireService pService = new ProprietaireService();

			Oeuvrevente oeuvre = oService.getOeuvre(Integer.parseInt(request.getParameter("txtidoeuvre")));

			oeuvre.setTitreOeuvrevente(request.getParameter("txtoeuvre"));

			String expressionR = "^([+]?\\d*\\.?\\d*)$";

			if (!request.getParameter("txtprixoeuvre").equals("") && 
					request.getParameter("txtprixoeuvre").matches(expressionR)) {
				oeuvre.setPrixOeuvrevente(Float.parseFloat(request.getParameter("txtprixoeuvre")));
			}

			Proprietaire proprietaire = new Proprietaire();
			try {
				for(Proprietaire p : pService.consulterListeProprietaires())
				{
					if(p.getNomProprietaire().equals(request.getParameter("txtpropoeuvre").split(" ")[0]))
					{
						proprietaire = p;
						oeuvre.setProprietaire(proprietaire);
						oService.mettreAJourOeuvreVente(oeuvre);
					}
				}
			} catch (MonException e1) {
				e1.printStackTrace();
			}

			try {
				request.setAttribute("mesOeuvresReservable", oService.consulterListOeuvresReservables());
			} catch (MonException e) {
				request.setAttribute("erreur", e.getMessage());
				destinationPage = ERROR_PAGE;
			}
			destinationPage = "/listerOeuvres.jsp";

		}

		// Redirection vers la page jsp appropriee
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(destinationPage);
		dispatcher.forward(request, response);

	}

}
