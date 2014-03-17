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

	$('#globalDonationButton').click(function() {
	    window.location.href = '${candidate.donationPageFullUrl}';
	    return false;
	});
});
</script>
</head>
<body>

	<jsp:include page="header.jsp" />

	<jsp:include page="topscroller.jsp" />



	<div class="contentarea">
		<!--contentarea-->

		<div class="article-leftarea">
			<!--article-leftarea-->
			<div class="articleCategory-tablist">
				<!--articleCategory-tablist-->
				<ul>
					<li><a href=""><img src="<c:out value='${staticDirectory}'/>/images/news-icon.png" border="0" align="absmiddle" /> Candidate Profile</a></li>
				</ul>
			</div>
			<div class="blockdiv">
				<input name="globalDonationButton" id="globalDonationButton" value="" type="button" class="donatebtnbig" />
			</div>
			<!--articleCategory-tablist-->

			<div class="divarticle">
				<!--divarticle-->
				<h1>
					<c:out value="${candidate.name}" />
				</h1>
			</div>
			<div>
				<c:out value="${candidate.content}" escapeXml="false" />
			</div>


		</div>
		<!--article-leftarea-->



		<div class="rhsarea">
			<!--rhsarea-->
			<div class="facebookWidget">
				<!--facebookWidget-->
				<iframe
					src="//www.facebook.com/plugins/likebox.php?href=https%3A%2F%2Fwww.facebook.com%2F${candidate.candidateFbPageId}&amp;width=728&amp;height=590&amp;show_faces=true&amp;colorscheme=light&amp;stream=true&amp;show_border=true&amp;header=true"
					scrolling="no" frameborder="0" style="border: none; overflow: hidden; width: 322px; height: 530px;" allowTransparency="true"></iframe>
			</div>
			<!--facebookWidget-->
			<div class="twitterWidget">
				<!--twitterWidget-->
				<a class="twitter-timeline" href="https://twitter.com/${candidate.twitterId}" data-widget-id="339326037013958656">Tweets by @AamAadmiParty</a>
				<script>
					!function(d, s, id) {
						var js, fjs = d.getElementsByTagName(s)[0], p = /^http:/
								.test(d.location) ? 'http' : 'https';
						if (!d.getElementById(id)) {
							js = d.createElement(s);
							js.id = id;
							js.src = p + "://platform.twitter.com/widgets.js";
							fjs.parentNode.insertBefore(js, fjs);
						}
					}(document, "script", "twitter-wjs");
				</script>


			</div>
			<!--twitterWidget-->

			<div class="clear"></div>
		</div>
		<!--rhsarea-->


	</div>
	<!--contentarea-->

	<jsp:include page="footer.jsp" />

</body>
</html>