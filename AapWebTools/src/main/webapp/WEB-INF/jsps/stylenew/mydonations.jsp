<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Aam Aadmi Party, India</title>
<link rel="stylesheet" type="text/css" href="<c:out value='${staticDirectory}'/>/styles/mainstyles.css" />


<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.4/jquery-ui.min.js"></script>
<link rel="stylesheet" href="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.4/themes/smoothness/jquery-ui.css" />


<!-- 
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.3/jquery.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
 -->
<script src="<c:out value='${staticDirectory}'/>/js/responsiveslides.min.js"></script>
<script>
	$(function() {
		$(".rslides").responsiveSlides();
	});
	$(function() {
		$("#tabs").tabs();
	});
</script>
</head>
<body>

	<jsp:include page="header.jsp" />

	<jsp:include page="topscroller.jsp" />



	<div class="contentarea">
		<!--contentarea-->
		<div class="loginwithholder">
			<!--loginwithholder-->
			<div class="loginwithinnerholder">
				<!--loginwithinnerholder-->
				<div id="tabs">
					<ul>
						<li><a href="#success">Success</a></li>
						<li><a href="#failed">failed</a></li>
					</ul>
					<div id="success" class="languagetab">
						<table id="successTable" style="border: 1px solid;">
							<thead>
								<tr>
									<th style="border: 1px solid;">Transaction Id</th>
									<th style="border: 1px solid;">Date</th>
									<th style="border: 1px solid;">Amount</th>
									<th style="border: 1px solid;">Donation Certificate</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${successDonations}" var="oneDonation">

									<tr>
										<td style="border: 1px solid;"><c:out value="${oneDonation.transactionId}"></c:out></td>
										<td style="border: 1px solid;"><fmt:formatDate value="${oneDonation.donationDate}" pattern="dd-MMM-yyyy HH:mm:ss" /></td>
										<td style="border: 1px solid;"><c:out value="${oneDonation.amount}"></c:out></td>
										<td style="border: 1px solid;"><a target=_new href='http://www.donate4india.org/web/prevbadger.html?txnid=${oneDonation.transactionId}'> Generate</a></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<div id="failed" class="languagetab">
						<table id="failedTable">
							<thead>
								<tr>
									<th style="border: 1px solid;">Transaction Id</th>
									<th style="border: 1px solid;">Date</th>
									<th style="border: 1px solid;">Amount</th>
									<th style="border: 1px solid;">Status/Error</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${failedDonations}" var="oneDonation">

									<tr>
										<td style="border: 1px solid;"><c:out value="${oneDonation.transactionId}"></c:out></td>
										<td style="border: 1px solid;"><fmt:formatDate value="${oneDonation.donationDate}" pattern="dd-MMM-yyyy HH:mm:ss" /></td>
										<td style="border: 1px solid;"><c:out value="${oneDonation.amount}"></c:out></td>
										<td style="border: 1px solid;"><c:out value="${oneDonation.pgErrorMessage}"></c:out></td>
										
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>

			</div>
			<!--loginwithinnerholder-->
		</div>
		<!--loginwithholder-->
	</div>
	<!--contentarea-->



		<jsp:include page="footer.jsp" />

</body>
</html>