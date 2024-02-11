<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Téléversement de l'avatar</title>
<jsp:include page="include/header.jsp"/>
</head>
<body>
<jsp:include page="include/nav.jsp"></jsp:include>
<h1>Téléversement de l'avatar pour l'utilisateur ${utilisateur.nom}</h1>
<form action="televersementAvatar" method="post" enctype="multipart/form-data">
    <input class="form-control" type="file" name="FICHIER"/>
    <input type="hidden" name="ID_UTILISATEUR" value="${utilisateur.id}">
    <input class="btn btn-primary" type="submit" value="Envoyer" />
</form>
<jsp:include page="include/piedDePage.jsp"></jsp:include>
</body>
</html>