<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<jsp:include page="include/header.jsp"/>
</head>
<body>
<jsp:include page="include/nav.jsp"></jsp:include>
<h1>Liste des clients</h1>
<ul>
<!-- Le forEach ci-dessous parcourt le contenu de la page de clients -->
<c:forEach items="${pageDeClients.content}" var="client">
	<li><img src="images/${client.id}.jpg" height="200"> ${client.nom} ${client.prenom} ${client.pays.nom} ${client.dateHeureInscription} <a href="client?ID_CLIENT=${client.id}">Modifier</a> <a href="televerserAvatar?ID_UTILISATEUR=${client.id}">Téléverser Avatar</a></li>
</c:forEach>
</ul>
<c:if test="${!pageDeClients.isFirst()}">
<a href="clients?page=0&sort=${sort}">&#x23EE;</a>
<a href="clients?page=${pageDeClients.number-1}&sort=${sort}">&#x23EA;</a>
</c:if>
Page ${pageDeClients.getNumber()+1}
<c:forEach var="i" begin="${pageDeClients.getNumber()-2 > 0 ? pageDeClients.getNumber()-2 : 0}" end="${pageDeClients.getNumber()+2}" step="1">
    <c:if test="${i < pageDeClients.totalPages}">
        <a href="clients?page=${i}&sort=${sort}">${i+1}</a>
    </c:if>
</c:forEach>
<c:if test="${!pageDeClients.last}">
<a href="clients?page=${pageDeClients.number+1}&sort=${pageDeClients.sort.iterator().next().property},${pageDeClients.sort.iterator().next().direction}">&#x23E9;</a>
<a href="clients?page=${pageDeClients.totalPages - 1}&sort=${sort}">&#x23ED;</a>
</c:if>
<br><br>
Client de ${pageDeClients.totalElements == 0 ? 0 : pageDeClients.size * pageDeClients.number+1} à ${pageDeClients.numberOfElements + (pageDeClients.size * pageDeClients.number)} sur ${pageDeClients.getTotalElements()} Clients
<br>
<a href="clients?page=0&sort=dateHeureInscription,DESC">Trier sur date heure inscription décroissante</a><br>
<a href="clients?page=0&sort=nom">Trier sur nom</a><br>
<a href="client">Ajouter un client</a>
<jsp:include page="include/piedDePage.jsp"></jsp:include>
</body>
</html>
