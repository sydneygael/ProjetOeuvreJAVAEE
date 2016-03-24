function teste()
{
   var  ok=true;
    if(document.identification.nom.value == "")
        {
            alert("Veuillez entrer votre nom");
            ok=false;
        }
      else
          if(document.identification.prenom.value == "")
            {
            alert("Veuillez entrer votre prenom");
            ok=false;
        }
          else
              if(document.identification.ville.value == "")
                {
                alert("Veuillez entrer la ville");
                ok=false;
            }

return ok;
}

function testeOeuvre()
{
    var  ok=true;
    document.getElementById("alertTitre").style.display="none";
    document.getElementById("alertPrix").style.display="none";
    document.getElementById("alertProprietaire").style.display="none";
    if($('#titre').val() == "")
    {
        document.getElementById("alertTitre").style.display="block";
        ok=false;
    }
    if ($('#prix').val() == "")
    {
        document.getElementById("alertPrix").style.display="block";
        ok=false;
    }
    if($('#proprietaire').val() == "-- Selectionnez un proprietaire --")
    {
        document.getElementById("alertProprietaire").style.display="block";
        ok=false;
    }

    return ok;
}

function testeReservation()
{
    var  ok=true;
    document.getElementById("alertAdherent").style.display="none";
    document.getElementById("alertDate").style.display="none";
    if ($('#date').val() == "")
    {
        document.getElementById("alertDate").style.display="block";
        ok=false;
    }
    if($('#adherent').val() == "-- Selectionnez un adherent --")
    {
        document.getElementById("alertAdherent").style.display="block";
        ok=false;
    }

    return ok;
}

