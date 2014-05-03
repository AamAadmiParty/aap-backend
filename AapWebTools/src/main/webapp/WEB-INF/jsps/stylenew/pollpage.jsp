<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Tell Us what you think, Aam Aadmi Party, India</title>


<!--Facebook Tags -->
<meta name="description"
	content="Tell us what you think on '${poll.content}'" />
	<meta name="og:image" property="og:image" content="http://s3-eu-west-1.amazonaws.com/nusdigital/image/images/221/original/tell%20us%20what%20you%20think%20button.png" />
	<meta name="og:title" property="og:title" content="AAP Polls, raise your voice" />
	<meta name="og:url" property="og:url" content="${candidate.landingPageFullUrl}" />
	<meta name="og:site_name" property="og:site_name" content="my.aamaadmiparty.org" />
	<meta name="og:type" property="og:type" content="blog" />
	<meta name="og:description" property="og:description"
	content="Tell us what you think on '${poll.content}'" />


<jsp:include page="includes.jsp" />
<c:if test="${userAlreadyPolled}">
<script type="text/javascript" src="https://www.google.com/jsapi"></script>
    <script type="text/javascript">
      google.load("visualization", "1", {packages:["corechart"]});
      google.setOnLoadCallback(drawChart);
      function drawChart() {
        var data = google.visualization.arrayToDataTable(${chartData});

        var options = {
          title: '${poll.contentWithoutHtml}'
        };

        var chart = new google.visualization.PieChart(document.getElementById('piechart_3d'));
        chart.draw(data, options);
      }
    </script>
</c:if>
</head>
<body>
	<div id="fb-root"></div>
	<script>
		(function(d, s, id) {
			var js, fjs = d.getElementsByTagName(s)[0];
			if (d.getElementById(id))
				return;
			js = d.createElement(s);
			js.id = id;
			js.src = "//connect.facebook.net/en_US/all.js#xfbml=1&appId=1389845241275842";
			fjs.parentNode.insertBefore(js, fjs);
		}(document, 'script', 'facebook-jssdk'));
	</script>

	<jsp:include page="header.jsp" />

	<jsp:include page="topscroller.jsp" />

	<div class="contentarea">
		<!--contentarea-->
		<div class="loginwithholder">
			<!--loginwithholder-->
			<div class="loginwithinnerholder">
				<!--loginwithinnerholder-->
				<div class="editprofile">${poll.content}</div>
				<br />
				<div class="formwrapper">
					<!--formwrapper-->
					<c:if test="${!userAlreadyPolled}">
					<form method="post">
						<input type="hidden" name="question" id="${poll.id}" value="${poll.id}" />
						<jsp:include page="errors.jsp" />
						<c:forEach items="${poll.answers}" var="oneAnswer">
							<div>
							<input type="radio" name="answer" id="${oneAnswer.id}" value="${oneAnswer.id}" />
							<label for="${oneAnswer.id}">${oneAnswer.content}</label>
							</div>
						</c:forEach>
						<c:if test="${empty loggedInUser}">
						<input name="poll" type="submit" class="button" value="Poll" disabled="disabled" />
						<br />* please <a href="${contextPath}/login?aap_redirect_url=${contextPath}/content/poll/${poll.id}">Sign In</a> to vote
						</c:if>
						<c:if test="${!empty loggedInUser}">
						<input name="poll" type="submit" class="button" value="Poll" />
						</c:if>

					</form>
					</c:if>
					<c:if test="${userAlreadyPolled}">
					<h3>You have already polled</h3>
						<h4><c:out value="${userAnswer}" escapeXml="false"></c:out> </h4>
						<div id="piechart_3d" style="width: 900px; height: 500px;"></div>
					</c:if>
				</div>
						<div class="fb-comments" data-href="http://my.aamaadmiparty.org${currentUrl}" data-width="900" data-numposts="5" data-colorscheme="light"></div>
				
				<!--loginwithinnerholder-->
			</div>
			<!--loginwithholder-->
		</div>
				<!--facebookWidget-->
	</div>
	<!--contentarea-->



	<jsp:include page="footer.jsp" />

</body>
</html>