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

<!--Facebook Tags -->
<meta name="description"
	content="Donate to aam aadmi party candidate ${candidate.name} from ${candidate.pcName} - ${candidate.stateName} to help them fight election against corrupts  and now you can generate donation certificates online" />
<c:if test="${empty candidate.imageUrl }">
	<meta name="og:image" property="og:image" content="https://lh4.googleusercontent.com/-4kGDOBFxpMY/UfCwashxxkI/AAAAAAAANNw/DCk6O5jwX_E/w1064-h822-no/donations03.jpg" />
	<meta name="og:image" property="og:image" content="https://lh5.googleusercontent.com/-v9IxH4yKots/UeEjk0Xf-9I/AAAAAAAAMR0/xbaXkSqNHug/w987-h822-no/donations01.jpg" />
	<meta name="og:image" property="og:image" content="https://lh4.googleusercontent.com/-R8T_ggjcylM/UeF7XfPz7iI/AAAAAAAAMSQ/5sybsvhpiDA/w1064-h822-no/donations04.jpg" />
	<meta name="og:image" property="og:image" content="https://lh6.googleusercontent.com/-Nw1eaaraEP0/UeDXYRXvmvI/AAAAAAAAMRM/M0UjgS2M_ek/w658-h436-no/donation02.jpg" />
</c:if>
<c:if test="${!empty candidate.imageUrl }">
	<meta name="og:image" property="og:image" content="${candidate.imageUrl}" />
</c:if>
<meta name="og:title" property="og:title" content="Donate for ${candidate.name}" />
<meta name="og:url" property="og:url" content="${candidate.landingPageFullUrl}" />
<meta name="og:site_name" property="og:site_name" content="my.aamaadmiparty.org" />
<meta name="og:type" property="og:type" content="blog" />
<meta name="og:description" property="og:description"
	content="Donate to aam aadmi party candidates ${candidate.name} from ${candidate.pcName} - ${candidate.stateName} to help them fight election against corrupts  and now you can generate donation certificates online" />


<script>
	$(function() {

		$('#globalDonationButton2').click(function() {
			ga('send', 'event', 'donation', 'candidatewidgetwidetop','${candidate.name}', 1);
			return false;
		});
		$('#globalDonationButton3').click(function() {
			ga('send', 'event', 'donation', 'candidatewidgetwidebottom','${candidate.name}', 1);
			return false;
		});
		$("#embedDialog").dialog({
			autoOpen : false,
			modal : true,
			height : 300,
			width : 500,
			show : {
				effect : "blind",
				duration : 1000
			},
			hide : {
				effect : "explode",
				duration : 1000
			}
		});

		$("#embedButton").click(
				function() {
					ga('send', 'event', 'candidate', 'wideprofile-embed','${candidate.name}', 1);
					$("#embedDialog").dialog("open");
				});
		$("#accordion").accordion({
			heightStyle : "content"
		});
	});
</script>
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
			js.src = "//connect.facebook.net/en_US/all.js#xfbml=1&amp;appId=1383280855238363";
			fjs.parentNode.insertBefore(js, fjs);
		}(document, 'script', 'facebook-jssdk'));
	</script>


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
			<div class="divarticle">
				*You can donate for below candidate by clicking here and can show your support.
				<div class="blockdiv">
					<a name="globalDonationButton2" id="globalDonationButton2" href="${candidate.donationPageFullUrl}" target=_new ><input value="" type="button" class="donatebtnbig" /></a>
				</div>
			</div>
			<!--articleCategory-tablist-->

			<div class="divarticle">
				<!--divarticle-->
				<table width="100%">
					<tr valign="top">
						<td>
							<h1>
								<c:out value="${candidate.name}" />
							</h1>
							<h3>State : ${candidate.stateName}</h3>
							<h3>Loksabha : ${candidate.pcName}</h3>
							<h3>URL to this page : ${candidate.landingPageSmallUrl}</h3>
							<h3>Direct Donation URL : http://myaap.in/${candidate.donatePageUrlId}</h3>

						</td>
						<td>
							<img src='${candidate.imageUrl}' style="max-width: 400px; max-height: 400px;" />
						</td>
					</tr>
				</table>
				
			</div>
			<div>
				<c:out value="${candidate.content}" escapeXml="false" />
			</div>
			<br></br>
			<div id="success" class="languagetab">
				<table border="0" cellpadding="0" cellspacing="0">
					<tbody>
						<tr>
							<th width="200">Total Money Required</th>
							<td width="200">Rs 70,00,000</td>
						</tr>
						<tr>
							<th width="200">Total Transactions</th>
							<td width="200">${donationCampaignInfo.ttxn}</td>
						</tr>
						<tr>
							<th>Total Amount</th>
							<td>${donationCampaignInfo.tamt}</td>
						</tr>
					</tbody>
				</table>
				<table border="0" cellpadding="0" cellspacing="0">
					<tbody>
						<tr>
							<th width="140">Donor Name</th>
							<th width="89">Date</th>
							<th width="89">Transaction Id</th>
							<th width="91">Amount</th>
						</tr>
						<c:forEach var="oneDonation" items="${donationCampaignInfo.dns}">
							<tr>
								<td><c:out value="${oneDonation.name}" /></td>
								<td><fmt:formatDate value="${oneDonation.dt}" pattern="dd-MMM-yyyy HH:mm:ss" /></td>
								<td><c:out value="${oneDonation.did}" /></td>
								<td><c:out value="${oneDonation.amt}" /></td>
							</tr>
						</c:forEach>

					</tbody>
				</table>
			</div>
			<br></br>
			<div class="voiceapp-inner-div">
				<table width="100%">
					<tr valign="top">
						<td>
						<a name="globalDonationButton3" id="globalDonationButton3" href="${candidate.donationPageFullUrl}" target=_new ><input value="" type="button" class="donatebtnbig" /></a>
						</td>
						<td align="right"><input id="embedButton" name="embedButton" type="button" value="Embed Profile in your Website" class="button" /></td>
					</tr>
				</table>
			</div>

			<div class="fb-comments" data-href="${candidate.landingPageFullUrl}" data-width="640" data-numposts="5" data-colorscheme="light"></div>
		</div>
		<!--article-leftarea-->



	</div>
	<!--contentarea-->
	<div id="embedDialog" title="Embed Candidate Profile on your website">
		<div id="accordion">
			<h3>${candidate.name}</h3>
			<div>
				<p>
					<b>Copy paste code below HTMl code in your website</b>
				</p>
				<textarea rows="5" cols="50" readonly="readonly">
					<iframe src="http://my.aamaadmiparty.org/aapcandidates.html?pcId=${candidate.parliamentConstituencyId}" width="300" height="400" frameborder="0" />
				</textarea>
			</div>
			<h3>All Candidates</h3>
			<div>
				<p>
					<b>Copy paste code below HTMl code in your website</b>
				</p>
				<textarea rows="5" cols="50" readonly="readonly">
					<iframe src="http://my.aamaadmiparty.org/aapcandidates.html" width="300" height="400" frameborder="0" />
				</textarea>
			</div>
		</div>
	</div>


</body>
</html>