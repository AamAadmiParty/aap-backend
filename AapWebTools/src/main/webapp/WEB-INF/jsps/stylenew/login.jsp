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

<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.3/jquery.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
<script src="<c:out value='${staticDirectory}'/>/js/responsiveslides.min.js"></script>
<script>
	$(function() {
		$(".rslides").responsiveSlides();
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
						<ul>
							<li><a href="/login/facebook"><img src="<c:out value='${staticDirectory}'/>/images/loginFB.png" border="0" /></a></li>
							<li><a href="/login/twitter"><img src="<c:out value='${staticDirectory}'/>/images/login-twitter.png" border="0" /> </a></li>
						</ul>
						<ul>
							<li class="noborder"><a href="#"><img src="<c:out value='${staticDirectory}'/>/images/loginyahoo.png" border="0" /></a></li>
							<li class="noborder"><a href="#"><img src="<c:out value='${staticDirectory}'/>/images/logingoogle.png" border="0" /></a></li>
						</ul>
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