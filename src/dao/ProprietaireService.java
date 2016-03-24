package dao;

import meserreurs.MonException;
import metier.Proprietaire;
import persistance.DialogueBd;

import java.util.ArrayList;
import java.util.List;

public class ProprietaireService {
	
    public List<Proprietaire> consulterListeProprietaires() throws MonException {
        String mysql = "select * from proprietaire";
        return consulterListeProprietaires(mysql);
    }

    private List<Proprietaire> consulterListeProprietaires(String mysql) throws MonException {
        List<Object> rs;
        List<Proprietaire> mesProprietaires = new ArrayList<Proprietaire>();
        int index = 0;
        try {
            DialogueBd unDialogueBd = DialogueBd.getInstance();
            rs = DialogueBd.lecture(mysql);
            
            while (index < rs.size()) {
            	
                Proprietaire unP = new Proprietaire();
                // il faut redecouper la liste pour retrouver les lignes
                unP.setIdProprietaire(Integer.parseInt(rs.get(index + 0).toString()));
                unP.setNomProprietaire(rs.get(index + 1).toString());
                unP.setPrenomProprietaire(rs.get(index + 2).toString());
                // incrémenter tous les 3 champs
                index = index + 3;
                mesProprietaires.add(unP);
            }

            return mesProprietaires;
        } catch (Exception exc) {
            throw new MonException(exc.getMessage(), "systeme");
        }
    }

    public Proprietaire getProprietaire(int idProprietaire) throws MonException{
        Proprietaire proprietaire = new Proprietaire();

        try{
            for(Proprietaire p : consulterListeProprietaires()){
                if(p.getIdProprietaire() == idProprietaire){
                	proprietaire = p;
                    break;
                }
            }
        } catch (MonException e)
        {
            e.printStackTrace();
        }
        return proprietaire;
    }
}