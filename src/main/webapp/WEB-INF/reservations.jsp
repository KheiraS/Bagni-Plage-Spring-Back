<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Liste des réservations</title>
<jsp:include page="include/header.jsp"/>
</head>
<body>
<jsp:include page="include/nav.jsp"></jsp:include>
<h1>Liste des réservations</h1>
<ul>
<!-- Le forEach ci-dessous parcourt le contenu de la page de reservations -->
<c:forEach items="${pageDeReservations.content}" var="reservation">
	<li>${reservation.client.nom} ${reservation.client.prenom} ${reservation.client.pays.nom} du ${reservation.dateDebut} au ${reservation.dateFin} <a href="reservation?ID_RESERVATION=${reservation.id}">Modifier</a> <a href="reservationPDF?ID_RESERVATION=${reservation.id}">PDF</a></li>
</c:forEach>
</ul>
<c:if test="${!pageDeReservations.isFirst()}">
<a href="reservations?page=0&sort=${sort}">&#x23EE;</a>
<a href="reservations?page=${pageDeReservations.number-1}&sort=${sort}">&#x23EA;</a>
</c:if>
Page ${pageDeReservations.getNumber()+1}
<c:forEach var="i" begin="${pageDeReservations.getNumber()-2 > 0 ? pageDeReservations.getNumber()-2 : 0}" end="${pageDeReservations.getNumber()+2}" step="1">
    <c:if test="${i < pageDeReservations.totalPages}">
        <a href="reservations?page=${i}&sort=${sort}">${i+1}</a>
    </c:if>
</c:forEach>
<c:if test="${!pageDeReservations.last}">
<a href="reservations?page=${pageDeReservations.number+1}&sort=${pageDeReservations.sort.iterator().next().property},${pageDeReservations.sort.iterator().next().direction}">&#x23E9;</a>
<a href="reservations?page=${pageDeReservations.totalPages - 1}&sort=${sort}">&#x23ED;</a>
</c:if>
<br><br>
Réservations de ${pageDeReservations.totalElements == 0 ? 0 : pageDeReservations.size * pageDeReservations.number+1} à ${pageDeReservations.numberOfElements + (pageDeReservations.size * pageDeReservations.number)} sur ${pageDeReservations.getTotalElements()} reservations
<br>
<a href="reservations?page=0&sort=dateDebut,DESC">Trier sur date de début décroissante</a><br>
<a href="reservation">Ajouter une réservation</a>
<br>
<a href="reservationsExcel">Export Excel des réservations de la semaine en cours</a>
<jsp:include page="include/piedDePage.jsp"></jsp:include>
</body>
</html>