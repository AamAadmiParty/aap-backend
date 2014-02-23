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
					<div id="success">
						<table id="successTable" style="border: 1px solid;">
							<thead>
								<tr>
									<th style="border: 1px solid;">Transaction Id</th>
									<th style="border: 1px solid;">Date</th>
									<th style="border: 1px solid;">Amount</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${successDonations}" var="oneDonation">

									<tr>
										<td style="border: 1px solid;"><c:out value="${oneDonation.transactionId}"></c:out></td>
										<td style="border: 1px solid;"><fmt:formatDate value="${oneDonation.donationDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
										<td style="border: 1px solid;"><c:out value="${oneDonation.amount}"></c:out></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<div id="failed">
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
										<td style="border: 1px solid;"><fmt:formatDate value="${oneDonation.donationDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
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



	<div class="footerfullbg">
		<!--footerfullbg-->
		<div class="footercontent">
			<!--footercontent-->
			<div class="footerrow">
				<!--footerrow-->
				<h2>Donation Links</h2>
				<ul>
					<li><a href="#">Donate Online</a></li>
					<li><a href="#">Donate - By Cheque/Demand Draft</a></li>
					<li><a href="#">Donation Policies</a></li>
					<li><a href="#">List of Donors</a></li>
					<li><a href="#">Donation FAQs</a></li>
					<li><a href="#">Income & Expenditure Statements</a></li>
				</ul>
			</div>
			<!--footerrow-->

			<div class="footerrow">
				<!--footerrow-->
				<h2>Media</h2>
				<ul>
					<li><a href="#">Cicero Opinion Poll</a></li>
					<li><a href="#">Aap Ki Kranti</a></li>
				</ul>
			</div>
			<!--footerrow-->

			<div class="footerrow">
				<!--footerrow-->
				<h2>Contact Us</h2>
				<ul>
					<li><a href="#">Party Offices</a></li>
					<li><a href="#">NRI Site</a></li>
					<li><a href="#">Privacy Policy</a></li>
				</ul>
			</div>
			<!--footerrow-->
		</div>
		<!--footercontent-->
	</div>
	<!--footerfullbg-->
	<div class="footerfull">
		<!--footerfull-->
		<div class="footercontent">
			<!--footercontent-->
			<div class="conectleft">
				<!--conectleft-->
				<ul>
					<li class="h3">Connect with us on</li>
					<li><a href="#"><img src="<c:out value='${staticDirectory}'/>/images/facebooklogo.png" border="0" align="absmiddle" /></a></li>
					<li><a href="#"><img src="<c:out value='${staticDirectory}'/>/images/twitterlogo.png" border="0" align="absmiddle" /></a></li>
					<li><a href="#"><img src="<c:out value='${staticDirectory}'/>/images/youtubeicon.png" border="0" align="absmiddle" /></a></li>
				</ul>
			</div>
			<!--conectleft-->
			<div class="copyright">
				<!--copyright-->
				© Aam Aadmi Party. All Rights Reserved
			</div>
			<!--copyright-->
		</div>
		<!--footerfull-->
	</div>
	<!--footercontent-->
</body>
</html>