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
	    $( "#accordion" ).accordion();
	  });
</script>
</head>
<body>

	<jsp:include page="header.jsp" />

	<jsp:include page="topscroller.jsp" />



	<div class="contentarea">
		<!--contentarea-->
		<div class="article-leftarea">
			<form:form method="POST" >
				<!--article-leftarea-->
				<div class="voiceappdiv">
					<!-- <input name="" type="checkbox" value="" /> --> I want to be voice of AAP
				</div>

				<div class="voiceapp-inner-div">
					<jsp:include page="errors.jsp" />
					<!--voiceapp-inner-div-->
					<div class="fbwrapper">
						<!--fbwrapper-->
						<img src="<c:out value='${staticDirectory}'/>/images/facebook.jpg" />
						<div class="fbgorups">
							<!--fbgorups-->
							<ul>
								<li>
								<c:if test="${postOnTimeLine}">
									<input name="postOnTimeLine" id="postOnTimeLine" value="true" type="checkbox" checked="checked" /> 
								</c:if>
								<c:if test="${!postOnTimeLine}">
									<input name="postOnTimeLine" id="postOnTimeLine" value="true" type="checkbox"/>
								</c:if>
								Post on My Timeline
								</li>
								
								<li class="noborder">
								<c:if test="${postOnGroup}">
									<input name="postOnGroup" id="postOnGroup" type="checkbox" value="true" checked="checked" />
								</c:if>
								<c:if test="${!postOnGroup}">
									<input name="postOnGroup" id="postOnGroup" type="checkbox" value="true" />
								</c:if>
								Post on My Facebook groups
								
								</li>
							</ul>
							<!-- 
							<div class="select">
								<select name="">
									<option value="select">Select</option>
									<option value="select facebook group">Select Facebook Group</option>
								</select>
							</div>
							 -->
						</div>
						<!--fbgorups-->
					</div>
					<!--fbwrapper-->
					<br />
					<div class="fbwrapper">
						<!--fbwrapper-->
						<img src="<c:out value='${staticDirectory}'/>/images/twitter.jpg" />
						<div class="fbgorups">
							<!--fbgorups-->
							<ul>
								<li>
								<c:if test="${postOnTwitter}">
									<input name="postOnTwitter" id="postOnTwitter" type="checkbox" value="true" checked="checked" />
								</c:if>
								<c:if test="${!postOnTwitter}">
									<input name="postOnTwitter" id="postOnTwitter" type="checkbox" value="true" />
								</c:if>
								 
								
								Tweet/Retweet from my account</li>
							</ul>
						</div>
						<!--fbgorups-->
					</div>
					<!--fbwrapper-->
					<input name="" type="submit" value="Save Settings" class="button" />
				</div>
			</form:form>
			<!--voiceapp-inner-div-->
			<br />
			<div class="voiceapp-inner-div">
				<!--voiceapp-inner-div-->
				<div class="faqdiv-tab">Frequently Asked Questions</div>
				<div class="faqwrapper">
					<div id="accordion">
						<h3>What is Voice of AAP?</h3>
						<div>
						Voice of AAp is a social app of aam aadmi party which will use your social account like facebook and twitter
									to spread AAP related messages, i.e. Daily Donation Update, important messages, tweets etc
							<ul>
								<li><b>Post on My Timeline : </b> if you select this option that means we will post various content like Daily Donation Update(Daily) and various other important
									updates once in a while(i.e. once per week but may be more then once per week at sometimes) on your timeline, so that your friend and any visitor of your profile can view
									it.</li>
								<li><b>Post on my Facebook groups : </b> if you select this option that means we will post various content like Daily Donation Update(Daily) and various other
									important updates once in a while(i.e. once per week but may be more then once per week at sometimes) on your various selected groups so that other members of those groups
									can view the content</li>
								<li><b>Tweet/Retweet from my account : </b> if you select this option that means we will tweet or retweet important tweets from your all connected twitter account</li>

							</ul>
						</div>
						<h3>Is my Information secure?</h3>
						<div>
						Yes absolutely, we will never share your inforamtion with any one.
						</div>
						<h3>Can I track activities of this app on my accounts?</h3>
						<div>
						Yes we want to keep it transparent, so we will show what, where and when we posted on your behalf. You can come back here and view all activity.
						</div>
						<h3>Can I Cancel this service?</h3>
						<div>
						Yes you can cancel it any time. Just come back here and uncheck the 'I want to be voice of AAP' checkbox.
						</div>
					</div>
				</div>
				<!--faqwrapper-->
			</div>



		</div>
		<!--article-leftarea-->

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