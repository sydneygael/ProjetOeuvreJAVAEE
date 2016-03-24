<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
<script src="bootstrap/js/jquery.js"></script>
<script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Liste des reservations</title>
</head>
<body>
<%@ include file="menu.jsp"%>
	<div id="titreGeneral">
		<h1>Liste des Réservations</h1>
	</div>

	<div id="tableOeuvre">
		<table class="table table-striped">
			<thead>
				<tr>
					<th>Titre</th>
					<th>Adhérent</th>
					<th>Date de Réservation</th>
					<th>Annuler</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${listeReservations}" var="res">
					<tr>
						<td>${res.oeuvrevente.titreOeuvrevente}</td>
						<td>${res.adherent.nomAdherent}
							${res.adherent.prenomAdherent}</td>
						<td><fmt:formatDate value="${res.date}" pattern="dd/MM/yyyy" /></td>
						<td><button data-id="${res.oeuvrevente.idOeuvrevente}"
								data-titre="${res.oeuvrevente.titreOeuvrevente}"
								data-proprietaire="${res.oeuvrevente.proprietaire.nomProprietaire}"
								class="action glyphicon glyphicon-trash" data-toggle="modal"
								data-target="#myModal"></button></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>

	<!-- Modal -->
	<div id="myModal" class="modal fade" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Annulation de la reservation</h4>
				</div>
				<div class="modal-body">

					<div id="numAdherent">Vous allez annuler la réservation de
						l'oeuvre :</div>
					<form id="formSup" class="form-horizontal" name='identification'
						method="post" action="ReservationControleur?action=supprimerReservation">
						<div class="form-group supprimer">
							<div class="col-sm-8">
								<input type="text" class="form-control" name="txtid" value=""
									id="id" readonly>
							</div>
						</div>
						<div class="form-group supprimer">
							<div class="col-sm-8">
								<input type="text" class="form-control" name="txttitre" value=""
									id="titre" readonly>
							</div>
						</div>
						<div class="form-group supprimer">
							<div class="col-sm-8">
								<input type="text" class="form-control" name="txtproprietaire"
									id="proprietaire" readonly>
							</div>
						</div>
						<div class="modal-footer">
							<button class="btn btn-success" type="submit" name="bt">confirmer</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
            $('#myModal').on('show.bs.modal', function(event) {
                var button = $(event.relatedTarget) // Button that triggered the modal
                var id = button.data('id')
                var titre = button.data('titre')
                var proprietaire = button.data('proprietaire')
                // Extract info from data-* attributes
                // If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
                // Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
                var modal = $(this)
                modal.find('.modal-body #id').val(id)
                modal.find('.modal-body #titre').val(titre)
                modal.find('.modal-body #proprietaire').val(proprietaire)
            })
        </script>
</body>
</html>