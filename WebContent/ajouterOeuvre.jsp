<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
<script src="bootstrap/js/jquery.js"></script>
<script src="bootstrap/js/bootstrap.min.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%@ include file="menu.jsp"%>
	<div id="titreGeneral">
		<h1>AJOUTER UNE OEUVRE</h1>
	</div>

	<div id="alertTitre" class="alert alert-danger" role="alert"
		style="display: none">Veuillez entrer un titre</div>
	<div id="alertPrix" class="alert alert-danger" role="alert"
		style="display: none">Veuillez entrer un prix</div>
	<div id="alertProprietaire" class="alert alert-danger" role="alert"
		style="display: none">Veuillez selectionner un proprietaire</div>

	<div class="col-sm-12">
		<form class="form-horizontal" name='ajoutOeuvre' method="post"
			action="OeuvreControleur?action=insererOeuvre" onsubmit="return testeOeuvre()">
			<div class="form-group">
				<label class="labelGeneral control-label col-sm-2" for="titre">Titre
					de l'oeuvre :</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" name="txtTitre" value=""
						id="titre" placeholder="Entrez le titre">
				</div>
			</div>
			<div class="form-group">
				<label class="labelGeneral control-label col-sm-2" for="prix">Prix
					:</label>
				<div class="col-sm-9">
					<input type="number" step=".1" min="0" class="form-control"
						name="txtPrix" value="" id="prix" placeholder="Entrez le prix">
				</div>
			</div>
			<div class="form-group">
				<label class="labelGeneral control-label col-sm-2"
					for="proprietaire">Propriétaire :</label>
				<div class="col-sm-9">
					<select class="form-control" name="ddlProp" id="proprietaire">
						<option>-- Selectionnez un proprietaire --</option>
						<c:forEach items="${mesProprietaires}" var="prop">
							<option>${prop.nomProprietaire}
								${prop.prenomProprietaire}</option>
						</c:forEach>
					</select>
				</div>
			</div>

			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<button class="btn btn-default" type="submit" name="bt">Ajouter</button>
					<button class="btn btn-default">Reset</button>
				</div>
			</div>

		</form>
	</div>
</body>
</html>