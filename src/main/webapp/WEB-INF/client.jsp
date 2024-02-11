<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<jsp:include page="include/header.jsp"/>
</head>
<body>
<jsp:include page="include/nav.jsp"></jsp:include>
<h1>Client</h1>
<form:form method="post" modelAttribute="client" action="client">
<form:label path="nom" class="col-sm-1 control-label">Nom</form:label><form:input path="nom"/><form:errors cssClass="erreur" path="nom"/><br>
<form:label path="prenom" class="col-sm-1 control-label">Prénom</form:label><form:input path="prenom"/><form:errors cssClass="erreur" path="prenom"/><br>
<form:label path="email" class="col-sm-1 control-label">Email</form:label><form:input path="email"/><form:errors cssClass="erreur" path="email"/><br>
<form:label path="motDePasse" class="col-sm-1 control-label">Mot de passe</form:label><form:input path="motDePasse"/><form:errors cssClass="erreur" path="motDePasse"/><br>
<form:label path="pays" class="col-sm-1 control-label">Pays</form:label><form:select path="pays">
<form:option value="0">Merci de choisir un pays</form:option>
<form:options items="${pays}" itemValue="code" itemLabel="nom"/>
</form:select><form:errors cssClass="erreur" path="pays"/>
<br>
<form:label path="lienDeParente" class="col-sm-1 control-label">Lien de parenté</form:label><form:select path="lienDeParente">
<form:option value="0">Merci de choisir un lien de parenté</form:option>
<form:options items="${liensDeParente}" itemValue="id" itemLabel="nom"/>
</form:select><form:errors cssClass="erreur" path="lienDeParente"/>
<div class="col-sm-1 control-label"></div><form:button class="btn btn-success">Enregistrer</form:button>
</form:form>
<jsp:include page="include/piedDePage.jsp"></jsp:include>
</body>
</html>