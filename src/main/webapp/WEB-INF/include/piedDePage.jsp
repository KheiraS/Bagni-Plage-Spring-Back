<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<br><br>
<div>
<a href="/swagger-ui/index.html#/" target="swagger">Swagger</a><br>
<a href="/h2-console" target="h2_console">Console H2</a><br>
<a href="/api-autogeneree/files" target="restrepo">API auto-générée par Spring REST</a><br>
<a href="/beans" target="_beans">Liste des beans contenus dans le conteneur IoC de Spring</a><br>
<a href="/health/custom" target="_health">Actuator Health</a>
<jsp:useBean id="dateFin" class="java.util.Date"/>
<c:set var="msFin" value="${dateFin.getTime()}" scope="page" />
<p>Page générée en ${msFin - msDepart} ms</p>
</div>