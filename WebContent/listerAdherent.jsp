<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Affichage de tous les adhérents</title>
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
<script src="bootstrap/js/jquery.js"></script>
<script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
<!-- <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script> -->
</head>
<body>
	<P>
		<A href="index.jsp"><FONT face="Arial" color="#004080">Retour
				Accueil</FONT></A>
	</P>
	<P align="center">
		<FONT face="Arial" size="5" color="#004080"><U> <STRONG>Listing&nbsp;des
					Adhérents </STRONG></U></FONT>
	</P>
	<div class="container">
		<table class="table table-striped">
			<thead>
				<tr>
					<TH>Numero</TH>
					<TH>Nom</TH>
					<TH>Prénom</TH>
					<TH>Ville</TH>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${mesAdherents}" var="item">
					<tr>
						<td>${item.idAdherent}</td>
						<td>${item.nomAdherent}</td>
						<td>${item.prenomAdherent}</td>
						<td>${item.villeAdherent}</td>

						<td><button
								class="action glyphicon glyphicon-pencil btn btn-primary"
								data-toggle="modal" data-id="${item.idAdherent}"
								data-nom="${item.nomAdherent}"
								data-prenom="${item.prenomAdherent}" data-target="#editionModal"></button></td>

						<td><button
								class="action glyphicon glyphicon-trash btn btn-danger"
								data-toggle="modal" data-id="${item.idAdherent}"
								data-nom="${item.nomAdherent}"
								data-prenom="${item.prenomAdherent}"
								data-target="#suppressionModal"></button></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>

	<!-- Modal for suppression -->
	<div id="suppressionModal" class="modal fade" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Voulez vous Supprimer un adhérent ?</h4>
				</div>
				<div class="modal-body">

					<form id="formSup" class="form-horizontal" name='identification'
						method="post" action="Controleur?action=supprimerAdherent">
						<div class="form-group supprimer">
							<div class="col-sm-8">
								<input type="text" class="form-control" name="txtid" value=""
									id="id" readonly>
							</div>
						</div>
						<div class="form-group supprimer">
							<div class="col-sm-8">
								<label> nom </label> <input type="text" class="form-control"
									name="txtnom" value="" id="nom" readonly>
							</div>
						</div>
						<div class="form-group supprimer">
							<div class="col-sm-8">
								<label> prenom </label><input type="text" class="form-control"
									name="txtprenom" id="prenom" readonly>
							</div>
						</div>
						<div class="modal-footer">
							<button class="btn btn-danger" type="submit" name="bt">OK</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>


	<!-- Modal for edition -->
	<div id="editionModal" class="modal fade" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">MISE A JOUR ADHERENT</h4>
				</div>
				<div class="modal-body">

					<label> id adherent </label>
					<form id="formSup" class="form-horizontal" name='identification'
						method="post" action="Controleur?action=miseAJourAdherent">
						<div class="form-group supprimer">
							<div class="col-sm-8">
								<input type="text" class="form-control" name="txtid" value=""
									id="id" readonly>
							</div>
						</div>
						<div class="form-group supprimer">
							<div class="col-sm-8">
								<label> nom </label><input type="text" class="form-control"
									name="txtnom" value="" id="nom">
							</div>
						</div>
						<div class="form-group supprimer">
							<div class="col-sm-8">
								<label> prenom </label><input type="text" class="form-control"
									name="txtprenom" id="prenom">
							</div>
						</div>
						<div class="form-group supprimer">
							<div class="col-sm-8">
								<label> ville </label><input type="text" class="form-control"
									name="txtville" id="ville">
							</div>
						</div>
						<div class="modal-footer">
							<button class="btn btn-success" type="submit" name="editbt">Enregistrer</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>



	<script>
		//triggered when modal is about to be shown

		//triggered when modal is about to be shown
		$('#suppressionModal').on('show.bs.modal', function(e) {

			//get data-id attribute of the clicked element
			var id = $(e.relatedTarget).data('id');
			var nom = $(e.relatedTarget).data('nom');
			var prenom = $(e.relatedTarget).data('prenom');

			//populate the textbox
			$(e.currentTarget).find('input[name="txtid"]').val(id);
			$(e.currentTarget).find('input[name="txtnom"]').val(nom);
			$(e.currentTarget).find('input[name="txtprenom"]').val(prenom);
		});

		$('#editionModal').on('show.bs.modal', function(e) {

			//get data-id attribute of the clicked element
			var id = $(e.relatedTarget).data('id');
			var nom = $(e.relatedTarget).data('nom');
			var prenom = $(e.relatedTarget).data('prenom');

			//populate the textbox
			$(e.currentTarget).find('input[name="txtid"]').val(id);
			$(e.currentTarget).find('input[name="txtnom"]').val(nom);
			$(e.currentTarget).find('input[name="txtprenom"]').val(prenom);
		});
	</script>
</body>

</html>