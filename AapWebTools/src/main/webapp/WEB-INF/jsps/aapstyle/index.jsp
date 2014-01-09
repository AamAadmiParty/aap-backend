<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="/aap/<c:out value='${design}'/>/css/style.css">
</head>
<body>
	<div class="body">
		<jsp:include page="header.jsp" />
		
		<div class="profile-name">
			<p>Manmohan Singh</p>

		</div>
		<div class="bottom-row">
			<div class="widgets">
				<div class="left-widget">
					<p>Ongoing Poll App</p>
				</div>

				<div class="left-widget">
					<p>Training Modules</p>
				</div>

				<div class="left-widget">
					<p>My Political Hierarchy</p>
				</div>

				<div class="left-widget">
					<p>Ask a Question</p>
				</div>
			</div>
			<div class="social-area">
				<div class="social-fb">
					<img src="fb.png">
				</div>
				<div class="social-twtr">
					<img src="twtr.png">
				</div>
			</div>
		</div>
	</div>
	<div class="footer">
		<div class="footer-center">

			<div class="panel-pane pane-block pane-menu-menu-footer-1">

				<h2 class="pane-title">Donation Links</h2>


				<div class="pane-content">
					<ul class="menu">
						<li class="first leaf"><a href="https://donate.aamaadmiparty.org" title="AAP Party Website">Donate Online</a></li>
						<li class="leaf"><a href="http://www.aamaadmiparty.org/page/donate-by-chequedemand-draft">Donate - By Cheque/Demand Draft</a></li>
						<li class="leaf"><a href="http://www.aamaadmiparty.org/page/donation-policies">Donation Policies</a></li>
						<li class="leaf"><a href="http://www.aamaadmiparty.org/donation-list">List of Donors</a></li>
						<li class="leaf"><a href="http://www.aamaadmiparty.org/page/donation-faq" title="Donation FAQs">Donation FAQs</a></li>
						<li class="last leaf"><a href="http://aamaadmiparty.org/income%20expenditure%20details" title="Income &amp; Expenditure Statements">Income &amp; Expenditure
								Statements</a></li>
					</ul>
				</div>
			</div>

			<div class="panel-pane pane-block pane-menu-menu-footer-3">

				<h2 class="pane-title">Media</h2>


				<div class="pane-content">
					<ul class="menu">
						<li class="first leaf"><a href="http://www.aamaadmiparty.org/delhi-elections-opinion-poll">Cicero Opinion Poll</a></li>
						<li class="last leaf"><a href="http://aapkikranti.com" title="Aap Ki Kranti - eNewspaper">Aap Ki Kranti</a></li>
					</ul>
				</div>


			</div>
		</div>
		<div class="inside panels-flexible-region-inside panels-flexible-region-56-center-inside">
			<div class="panel-pane pane-block pane-menu-menu-contact-us-footer">

				<h2 class="pane-title">Contact Us</h2>


				<div class="pane-content">
					<ul class="menu">
						<li class="first leaf"><a href="http://www.aamaadmiparty.org/page/contact-us">Party Offices</a></li>
						<li class="last leaf"><a href="http://www.aamaadmiparty.org/NRI-Support-Aam-Aadmi-Party" title="NRI support for Aam Aadmi Party">NRI Support</a></li>
					</ul>
				</div>


			</div>
		</div>

	</div>

	</div>
	<div class="footer-bottom">


		<div class="footer-bottom-content">

			<div class="social-networks">
				<h2>Connect with us on</h2>
				<div id="fb">
					<a href="https://www.facebook.com/AamAadmiParty"> <img src="http://www.aamaadmiparty.org/sites/all/themes/aap_theme/images/fb.png"></a>
				</div>
				<div id="twtr">
					<a href="https://twitter.com/AamAadmiParty"> <img src="http://www.aamaadmiparty.org/sites/all/themes/aap_theme/images/twitter.png"></a>
				</div>
				<div id="youtube">
					<a href="https://www.youtube.com/user/indiACor2010"> <img src="http://www.aamaadmiparty.org/sites/all/themes/aap_theme/images/youtube.png"></a>
				</div>
			</div>
			<div class="copyright">
				<p>&#169; Aam Aadmi Party. All Rights Reserved</p>
			</div>

		</div>
	</div>
</body>
</html>