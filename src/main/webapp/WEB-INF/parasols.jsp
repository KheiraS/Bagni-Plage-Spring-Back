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
<h1>Liste des parasols</h1>
<form>
<select name="ID_FILE">
	<option value="0">Toutes les files</option>
	<c:forEach items="${files}" var="file">
		<option value="${file.id}"<c:if test="${file.id eq idFile}"> SELECTED</c:if>>${file.numero}</option>
	</c:forEach>
</select>
<input type="submit" value="Filtrer">
</form>
<ul>
<!-- Le forEach ci-dessous parcourt le contenu de la page de parasols -->
<c:forEach items="${pageDeParasols.content}" var="parasol">
	<li>${parasol.numEmplacement} en file ${parasol.file.numero} (${parasol.file.prixJournalier} &euro;) <a href="parasol?ID_PARASOL=${parasol.id}">Modifier</a></li>
</c:forEach>
</ul>
<c:if test="${!pageDeParasols.isFirst()}">
<a href="parasols?page=0&sort=${sort}">&#x23EE;</a>
<a href="parasols?page=${pageDeParasols.number-1}&sort=${sort}">&#x23EA;</a>
</c:if>
Page ${pageDeParasols.getNumber()+1}
<c:if test="${!pageDeParasols.last}">
<a href="parasols?page=${pageDeParasols.number+1}&sort=${sort}">&#x23E9;</a>
<a href="parasols?page=${pageDeParasols.totalPages - 1}&sort=${sort}">&#x23ED;</a>
</c:if>
<br><br>
<c:forEach var="i" begin="${pageDeParasols.getNumber()-2 > 0 ? pageDeParasols.getNumber()-2 : 0}" end="${pageDeParasols.getNumber()+2}" step="1">
    <c:if test="${i < pageDeParasols.totalPages}">
        <a href="parasols?page=${i}&sort=${sort}">${i+1}</a>
    </c:if>
</c:forEach>
<br><br>
Parasols de ${pageDeParasols.totalElements == 0 ? 0 : pageDeParasols.size * pageDeParasols.number+1} à ${pageDeParasols.numberOfElements + (pageDeParasols.size * pageDeParasols.number)} sur ${pageDeParasols.getTotalElements()} parasols
<br><br>
<a href="parasol">Ajouter un parasol</a>
<jsp:include page="include/piedDePage.jsp"></jsp:include>
</body>
</html>