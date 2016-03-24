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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Affichage de toutes les oeuvres</title>
</head>
<body>
	<%@ include file="menu.jsp"%>
	<P align="center">
		<FONT face="Arial" size="5" color="#004080"><U> <STRONG>Listing
					des Oeuvres </STRONG></U></FONT>
	</P>
	<TABLE class="table table-bordered">
		<CAPTION>Tableau des Oeuvres prétées</CAPTION>
		<TR>
			<TH>Id</TH>
			<TH>Titre</TH>
			<TH>Etat Oeuvre</TH>
			<TH>Prix Oeuvre</TH>
			<TH>Id Propriétaire</TH>
			<TH>Nom Propriétaire</TH>
			<TH>Prenom Propriétaire</TH>
			<TH>réserver?</TH>
			<TH>modifier?</TH>
		</TR>
		<c:forEach items="${mesOeuvresPret}" var="item">
			<tr class="danger">
				<td>${item.idOeuvrepret}</td>
				<td>${item.titreOeuvrepret}</td>
				<td>Prêté</td>
				<td>Non vendable</td>
				<td>${item.proprietaire.idProprietaire}</td>
				<td>${item.proprietaire.nomProprietaire}</td>
				<td>${item.proprietaire.prenomProprietaire}</td>
				<td style="font-style: italic;">Oeuvre déjà reservée</td>
				<td><button data-whatever="${item.idOeuvrepret}"
						type="button" class="btn btn-success" data-toggle="modal"
						data-idoeuvre="${item.idOeuvrepret}"
						data-titreoeuvre="${item.titreOeuvrepret}"
						data-target="#myModal2">modifier</button></td>

			</tr>
		</c:forEach>
		<c:forEach items="${mesOeuvresVente}" var="item">
			<tr
				<c:if test="${item.etatOeuvrevente=='L'}"> class="success" 
			</c:if>
				class="warning">
				<td>${item.idOeuvrevente}</td>
				<td>${item.titreOeuvrevente}</td>
				<td>${item.etatOeuvrevente}</td>
				<td>${item.prixOeuvrevente}</td>
				<td>${item.proprietaire.idProprietaire}</td>
				<td>${item.proprietaire.nomProprietaire}</td>
				<td>${item.proprietaire.prenomProprietaire}</td>
				<td><button data-whatever="${oeuvre.idOeuvrevente}"
						type="button" class="btn btn-success" data-toggle="modal"
						data-idoeuvre="${item.idOeuvrevente}"
						data-titreoeuvre="${item.titreOeuvrevente}" data-target="#myModal">Réserver</button></td>
				<td><button data-whatever="${oeuvre.idOeuvrevente}"
						type="button" class="btn btn-success" data-toggle="modal"
						data-idoeuvre="${item.idOeuvrevente}"
						data-titreoeuvre="${item.titreOeuvrevente}"
						data-prixoeuvre="${item.prixOeuvrevente}"
						data-target="#myModal2">modifier</button></td>
			</tr>
		</c:forEach>
	</TABLE>

	<!-- Modal -->
	<div id="myModal" class="modal fade" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Reservation</h4>
				</div>
				<div class="modal-body">
					<div id="alertAdherent" class="alert alert-danger" role="alert"
						style="display: none">Veuillez Selectionner un adherent</div>
					<div id="alertDate" class="alert alert-danger" role="alert"
						style="display: none">Veuillez entrer une date</div>

					<form class="form-horizontal" name='identification' method="post"
						action="OeuvreControleur?action=reserverOeuvre"
						onsubmit="return testeReservation()">
						<div class="form-group" id="formIdOeuvre">
							<label class="control-label col-sm-2" for="oeuvre">id
								oeuvre:</label>
							<div class="col-sm-9">
								<input type="text" class="form-control" name="txtidoeuvre"
									id="idoeuvre" readonly>
							</div>
						</div>
						<div class="form-group" id="titreoeuvre">
							<label class="control-label col-sm-2" for="oeuvre">titre
								oeuvre:</label>
							<div class="col-sm-9">
								<input type="text" class="form-control" name="txtoeuvre"
									id="titreoeuvre" readonly>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="adherent">propriétaire</label>
							<div class="col-sm-9">
								<select class="form-control" name="selectAd" id="adherent">
									<option>-- Selectionnez un adherent --</option>
									<c:forEach items="${listeAdherents}" var="proprietaire">
										<option value="${proprietaire.idAdherent}">
											${proprietaire.nomAdherent} ${proprietaire.prenomAdherent}</option>
									</c:forEach>
								</select>
							</div>
						</div>

						<div class="form-group">
							<label class="control-label col-sm-2" for="date">Date de
								la réservation :</label>

							<div class="col-sm-9">
								<input type='date' id="date" class="form-control" name="maDate">
							</div>
						</div>

						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<button class="btn btn-default" type="submit" name="bt">Reserver</button>
							</div>
						</div>
					</form>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- Modal -->
	<div id="myModal2" class="modal fade" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Modification oeuvre</h4>
				</div>
				<div class="modal-body">
					<div id="alertAdherent" class="alert alert-danger" role="alert"
						style="display: none">Veuillez Selectionner un adherent</div>
					<div id="alertDate" class="alert alert-danger" role="alert"
						style="display: none">Veuillez entrer une date</div>

					<form class="form-horizontal" name='identification' method="post"
						action="OeuvreControleur?action=modifierOeuvre"
						onsubmit="return testeOeuvre()">
						<div class="form-group" id="formIdOeuvre">
							<label class="control-label col-sm-2" for="oeuvre">id
								oeuvre:</label>
							<div class="col-sm-9">
								<input type="text" class="form-control" name="txtidoeuvre"
									id="idoeuvre" readonly>
							</div>
						</div>
						<div class="form-group" id="titreoeuvre">
							<label class="control-label col-sm-2" for="oeuvre">titre
								oeuvre:</label>
							<div class="col-sm-9">
								<input type="text" class="form-control" name="txtoeuvre"
									id="titreoeuvre">
							</div>
						</div>
						<div class="form-group" id="titreoeuvre">
							<label class="control-label col-sm-2" for="oeuvre">prix</label>
							<div class="col-sm-9">
								<input type="text" class="form-control" name="txtprixoeuvre"
									id="prixoeuvre">
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="adherent">propriétaire</label>
							<div class="col-sm-9">
								<select class="form-control" name="txtpropoeuvre" id="propOeuvre">
									<option>-- Selectionnez un proprietaire --</option>
									<c:forEach items="${listeProprietaires}" var="prop">
										<option>${prop.nomProprietaire}
											${prop.prenomProprietaire}</option>
									</c:forEach>
								</select>
							</div>
						</div>

						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<button class="btn btn-default" type="submit" name="bt">confirmer</button>
							</div>
						</div>
					</form>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					</div>
				</div>
			</div>
		</div>
	</div>


	<script>
		$('#myModal').on('show.bs.modal', function(event) {
			var button = $(event.relatedTarget) // Button that triggered the modal
			var id = $(event.relatedTarget).data('idoeuvre');
			var nom = $(event.relatedTarget).data('titreoeuvre');
			// If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
			// Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
			var modal = $(this)
			$(event.currentTarget).find('input[name="txtidoeuvre"]').val(id);
			$(event.currentTarget).find('input[name="txtoeuvre"]').val(nom);
		})
		
		$('#myModal2').on('show.bs.modal', function(event) {
			var button = $(event.relatedTarget) // Button that triggered the modal
			var id = $(event.relatedTarget).data('idoeuvre');
			var nom = $(event.relatedTarget).data('titreoeuvre');
			var prix = $(event.relatedTarget).data('prixoeuvre');
			var prop = $(event.relatedTarget).data('propOeuvre');
			// If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
			// Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
			var modal = $(this)
			$(event.currentTarget).find('input[name="txtidoeuvre"]').val(id);
			$(event.currentTarget).find('input[name="txtoeuvre"]').val(nom);
			$(event.currentTarget).find('input[name="txtprixoeuvre"]').val(prix);
			$(event.currentTarget).find('input[name="txtpropoeuvre"]').val(prop);
		})
		
	</script>
</body>
</html>