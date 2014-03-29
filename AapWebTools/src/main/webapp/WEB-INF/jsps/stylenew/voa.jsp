<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Aam Aadmi Party, India</title>
<jsp:include page="includes.jsp" />

<script>
	$(function() {
		$("#accordion").accordion();
	});
</script>
<c:if test="${empty loginAccounts.twitterAccounts}">
	<script>
		$(function() {
			$("#postOnTwitter")
					.click(
							function() {
								alert("You have NOT given twitter access, now you will be forwarded to twitter login page and you can give access and come back");
								window.location.href = "${contextPath}/login/twitter";
							});
		});
	</script>
</c:if>
</head>
<body>

	<jsp:include page="header.jsp" />

	<jsp:include page="topscroller.jsp" />


	<div class="contentarea">
		<!--contentarea-->
		<div class="article-leftarea">
			<form:form method="POST">
				<!--article-leftarea-->
				<div class="voiceappdiv">
					<!-- <input name="" type="checkbox" value="" /> -->
					Voice of AAP
				</div>

							<div class="voiceapp-inner-div" style="width: 1000px;">
				<table style="width: 997px;">
					<tr valign="top">
						<td style="width: 350px;">
								<jsp:include page="errors.jsp" />
								<jsp:include page="successmessage.jsp" />
								
								<!--voiceapp-inner-div-->
								<div class="fbwrapper">
									<!--fbwrapper-->
									<img src="<c:out value='${staticDirectory}'/>/images/facebook.jpg" />
									<div class="fbgorups">
										<!--fbgorups-->
										<ul>
											<li><c:if test="${postOnTimeLine}">
													<input name="postOnTimeLine" id="postOnTimeLine" value="true" type="checkbox" checked="checked" title="Yes I would like to spread AAP messages on Facebook to my friends, family and others" />
												</c:if> <c:if test="${!postOnTimeLine}">
													<input name="postOnTimeLine" id="postOnTimeLine" value="true" type="checkbox" title="Yes I would like to spread AAP messages on Facebook to my friends, family and others" />
												</c:if> Post on My Facebook</li>
											<!-- 
								<li class="noborder"><c:if test="${postOnGroup}">
										<input name="postOnGroup" id="postOnGroup" type="checkbox" value="true" checked="checked" />
									</c:if> <c:if test="${!postOnGroup}">
										<input name="postOnGroup" id="postOnGroup" type="checkbox" value="true" />
									</c:if> Post on My Facebook groups</li>
									 -->
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
											<li><c:if test="${postOnTwitter}">
													<input name="postOnTwitter" id="postOnTwitter" type="checkbox" value="true" checked="checked" />
												</c:if> <c:if test="${!postOnTwitter}">
													<input name="postOnTwitter" id="postOnTwitter" type="checkbox" value="true" />
												</c:if> Tweet/Retweet from my account</li>
										</ul>
									</div>
									<!--fbgorups-->
								</div>
								<!--fbwrapper-->
								<input name="" type="submit" value="Save Settings" class="button" />
						</td>
						<td style="width: 645px;">
							<!--voiceapp-inner-div-->
							<div class="faqdiv-tab">Frequently Asked Questions</div>
							<div class="faqwrapper">
								<div id="accordion">
									<h3>What is Voice of AAP?</h3>
									<div>
										Voice of AAp is a social app of aam aadmi party which will use your social account like facebook and twitter to spread AAP related messages, i.e. Daily Donation Update,
										important messages, tweets etc
										<ul>
											<li><b>Post on my Facebook : </b> if you select this option that means we will post various content like Daily Donation Update(Daily) and various other important
												updates once in a while(i.e. once per week but may be more then once per week at sometimes) on your timeline, so that your friend and any visitor of your profile can
												view it.</li>
											<!-- 
								<li><b>Post on my Facebook groups : </b> if you select this option that means we will post various content like Daily Donation Update(Daily) and various other
									important updates once in a while(i.e. once per week but may be more then once per week at sometimes) on your various selected groups so that other members of those groups
									can view the content</li>
									 -->
											<li><b>Tweet/Retweet from my account : </b> if you select this option that means we will tweet or retweet important tweets from your all connected twitter account</li>

										</ul>
									</div>
									<h3>Is my Information secure?</h3>
									<div>Yes absolutely, we will never share your information with any one outside AAP.</div>
									<!-- 
									<h3>Can I track activities of this app on my accounts?</h3>
									<div>Yes we want to keep it transparent, so we will show what, where and when we posted on your behalf. You can come back here and view all activity.</div>
									 -->
									<h3>Can I Cancel this service?</h3>
									<div>Yes you can cancel it any time. Just come back here and uncheck the checkboxes.</div>
								</div>
							</div> <!--faqwrapper-->
						</td>
					</tr>
				</table>
		</div>
		</form:form>
		



	</div>
	<!--article-leftarea-->

	</div>
	<!--contentarea-->


	<jsp:include page="footer.jsp" />


</body>
</html>