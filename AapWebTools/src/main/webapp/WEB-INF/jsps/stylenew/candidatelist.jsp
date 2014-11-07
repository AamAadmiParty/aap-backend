<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${PageTitle}</title>
<jsp:include page="includes.jsp" />

<meta name="description" content="Meet Aam Aadmi Party candidate for Loksabha election 2014" />
<meta name="og:image" property="og:image" content="https://s3.amazonaws.com/myaap/test/candidate/profile/leaders.jpg" />

<c:forEach items="${candidates}" var="oneCandidate">
	<c:if test="${!empty oneCandidate.imageUrl }">
		<meta name="og:image" property="og:image" content="${oneCandidate.imageUrl}" />
	</c:if>
</c:forEach>
<meta name="og:title" property="og:title" content="Aam Aadmi party candidates for Loksabha Eletions 2014" />
<meta name="og:url" property="og:url" content="http://my.aamaadmiparty.org/candidates.html" />
<meta name="og:site_name" property="og:site_name" content="my.aamaadmiparty.org" />
<meta name="og:type" property="og:type" content="blog" />
<meta name="og:description" property="og:description" content="Meet Aam Aadmi Party candidates for loksabha elections 2014" />


<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<style type="text/css">
html {
	height: 100%
}

body {
	height: 100%;
	margin: 0;
	padding: 0
}

#map-canvas {
	height: 100%
}
</style>

</head>
<body>

	<jsp:include page="header.jsp" />
	<%--
	<jsp:include page="topscroller.jsp" />
 --%>


	<div class="contentarea">
		<!--contentarea-->
		<div class="loginwithholder">
			<!--loginwithholder-->
			<div class="loginwithinnerholder">
				<h1>${PageTitle}</h1>
				<!--loginwithinnerholder-->
				<h2><a href="${contextPath}/candidates.html?type=map">Map View</a></h2>
				<div id="list" class="languagetab">
					List of AAP candidates for the LokSabha is given below. Locate your constituency, click on the candidate or constituency to view the full details
					<% int count = 1; %>
					<table>
						<tr>
							<th style="border: 1px solid;">S.No.</th>
							<th style="border: 1px solid;">State</th>
							<th style="border: 1px solid;">Loksabha/Vidhansabha</th>
							<th style="border: 1px solid;">Name</th>
							<th style="border: 1px solid;">Total Donors</th>
							<th style="border: 1px solid;">Total Amount</th>
						</tr>
						<c:forEach items="${candidates}" var="oneCandidate">
							<tr>
								<td style="border: 1px solid;"><% out.println(count); count++; %> </td>
								<td style="border: 1px solid;"><a href="${contextPath}/candidate/${oneCandidate.stateName}.html"><c:out value="${oneCandidate.stateName}" /></a></td>
								<td style="border: 1px solid;"><a href="${oneCandidate.landingPageFullUrl}"><c:out value="${oneCandidate.pcName}" /><c:out value="${oneCandidate.acName}" /></a></td>
								<td style="border: 1px solid;"><a href="${oneCandidate.landingPageFullUrl}"><c:out value="${oneCandidate.name}" /></a></td>
								<td style="border: 1px solid;"><c:out value="${oneCandidate.totalTransactions}" /></td>
								<td style="border: 1px solid;"><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2"  value="${oneCandidate.totalAmount}" /></td>

							</tr>
						</c:forEach>
					</table>
				</div>
			</div>
		</div>
		<!--loginwithinnerholder-->
	</div>
	<!--loginwithholder-->
	<!--contentarea-->



	<jsp:include page="footer.jsp" />
	<jsp:include page="addthis.jsp" />
</body>
</html>