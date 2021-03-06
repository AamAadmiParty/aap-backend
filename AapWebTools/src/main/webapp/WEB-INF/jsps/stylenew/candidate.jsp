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

<!--Facebook Tags -->
<meta name="description"
	content="Donate to aam aadmi party candidate ${candidate.name} from ${candidate.pcName} ${candidate.acName} - ${candidate.stateName} to help them fight election against corrupts  and now you can generate donation certificates online" />
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
	content="Donate to aam aadmi party candidate ${candidate.name} from ${candidate.pcName} ${candidate.acName} - ${candidate.stateName} to help them fight election against corrupts  and now you can generate donation certificates online" />

<script>
	$(function() {

		$('#globalDonationButton').click(function() {
			window.location.href = '${candidate.donationPageFullUrl}';
			return false;
		});
		$('#globalDonationButton2').click(function() {
			window.location.href = '${candidate.donationPageFullUrl}';
			return false;
		});
		$('#globalDonationButton3').click(function() {
			window.location.href = '${candidate.donationPageFullUrl}';
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
					ga('send', 'event', 'candidate', 'myaap-embed',
							'${candidate.name}', 1);
					$("#embedDialog").dialog("open");
				});
		$("#accordion").accordion({
			heightStyle : "content"
		});
	});
</script>


<link type="text/css" href="<c:out value='${staticDirectory}'/>/pika/bottom.css" rel="stylesheet" />


        <script type="text/javascript" src="<c:out value='${staticDirectory}'/>/pika/jquery.jcarousel.min.js"></script>
        <script type="text/javascript" src="<c:out value='${staticDirectory}'/>/pika/jquery.pikachoose.min.js"></script>
        <script type="text/javascript" src="<c:out value='${staticDirectory}'/>/pika/jquery.touchwipe.min.js"></script>
        <script language="javascript">
            $(document).ready(
                function (){
                if($("#pikame").length > 0){
                    $("#pikame").PikaChoose({carousel:true,carouselOptions:{wrap:'circular'}});
					}
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


	<jsp:include page="header.jsp" />


	<!--donatetabwrapper-->
	
	<!-- 
	<div class="donatetabwrapper">
		<input name="globalDonationButton" id="globalDonationButton" value="" type="button" class="donatebtnbig" />
	</div>

	<div class="slider">
		<ul class="rslides">
			<li><img src="<c:out value='${staticDirectory}'/>/images/slider-green.jpg" alt="" border="0" /></li>
			<li><img src="<c:out value='${staticDirectory}'/>/images/slider-blue-green.jpg" alt="" border="0" /></li>
			<li><img src="<c:out value='${staticDirectory}'/>/images/slider-pink.jpg" alt="" border="0" /></li>
			<li><img src="<c:out value='${staticDirectory}'/>/images/slider-red.jpg" alt="" border="0" /></li>
		</ul>

	</div>
	 -->
	<!--slider-->


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
			<!--
                        <div class="divarticle">
                                <input name="globalDonationButton2" id="globalDonationButton2" value="" type="button" class="donatebtnbig" />
                        </div>-->
			<!--articleCategory-tablist-->

			<div class="divarticle">
				<!--divarticle-->
				<table width="100%">
					<tr valign="top">
						<td><c:if test="${empty candidate.imageUrl }">
								<img src='http://kanpuria.com/wp-content/uploads/2013/12/AAP_Kanpur.jpg' style="max-width: 300px; max-height: 300px;" />
							</c:if> <c:if test="${!empty candidate.imageUrl }">
								<img src='${candidate.imageUrl}' style="max-width: 300px; max-height: 300px;" />
							</c:if></td>
						<td>
							<h1>
								<c:out value="${candidate.name}" />
							</h1>
							<h3>State : ${candidate.stateName}</h3>
							<h3>Loksabha/Vidhansabha : ${candidate.pcName} ${candidate.acName}</h3>
							<div id="success" class="languagetab">
								<table border="0" cellpadding="0" cellspacing="0">
									<tbody>
									   <%-- 
										<tr>
											<th width="200">Total Money Required</th>
											<td width="200">Rs 70,00,000(EC Limit)</td>
										</tr>
										--%>
										<tr>
											<th width="200">Total Transactions</th>
											<td width="200">${donationCampaignInfo.ttxn}</td>
										</tr>
										<tr>
											<th>Total Amount</th>
											<td><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2"  value="${donationCampaignInfo.tamt}" /></td>
										</tr>
										<tr>
											<th>URL to this page</th>
											<td>${candidate.landingPageSmallUrl}</td>
										</tr>
										<tr>
											<th>Direct Donation URL</th>
											<td>http://myaap.in/${candidate.donatePageUrlId}</td>
										</tr>
									</tbody>
								</table>
								<input name="globalDonationButton2" id="globalDonationButton2" value="" type="button" class="donatebtnbig" />
							</div>
						</td>
					</tr>
				</table>

			</div>

			<div>
				<c:out value="${candidate.content}" escapeXml="false" />
			</div>
			<br></br>
			<div id="success" class="languagetab">
				<h2>Recent Online Donations</h2>
				<em>*Max Last 500 donations listed here</em>
				<% int i=1; %>
				<table border="0" cellpadding="0" cellspacing="0">
					<tbody>
						<tr>
							<th width="20">S.No. </th>
							<th width="80">Transaction Id</th>
							<th width="140">Donor Name</th>
							<th width="89">Date</th>
							<th width="91">Amount</th>
						</tr>
						<c:forEach var="oneDonation" items="${donationCampaignInfo.dns}">
							<tr>
								<td><% out.println(i++); %></td>
								<td><a href="http://my.aamaadmiparty.org/donationcertifcates.html?txnid=${oneDonation.did}"><c:out value="${oneDonation.did}" /></a></td>
								<td><c:out value="${oneDonation.name}" /></td>
								<td><fmt:formatDate value="${oneDonation.dt}" pattern="dd-MMM-yyyy HH:mm:ss" /></td>
								<td><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2"  value="${oneDonation.amt}" /></td>
							</tr>
						</c:forEach>

					</tbody>
				</table>
			</div>
			<br></br>
			<div class="voiceapp-inner-div">
				<table width="100%">
					<tr valign="top">
						<td><input name="globalDonationButton3" id="globalDonationButton3" value="" type="button" class="donatebtnbig" /></td>
						<td align="right"><input id="embedButton" name="embedButton" type="button" value="Embed Profile in your Website" class="button" /></td>
					</tr>
				</table>
			</div>

			<div class="fb-comments" data-href="${candidate.landingPageFullUrl}" data-width="640" data-numposts="5" data-colorscheme="light"></div>
		</div>
		<!--article-leftarea-->



		<div class="rhsarea">
			<!--rhsarea-->
			<c:if test="${empty loggedInUser}">
                <div class="joincommunity">
                        <!--joincommunity-->
                        <a href="${contextPath}/login"><img src="<c:out value='${staticDirectory}'/>/images/joincommunity.jpg" border="0" /></a>
                        <ul>
                                <li>To participate in polls</li>
                                <li>To track your donation network</li>
                                <li>To help spread the word</li>
                                <li>And lots more...</li>
                        </ul>
                </div>
                </c:if>
			<div class="facebookWidget">
				<!--facebookWidget-->
				<iframe
					src="//www.facebook.com/plugins/likebox.php?href=https%3A%2F%2Fwww.facebook.com%2F${candidate.candidateFbPageId}&amp;width=728&amp;height=590&amp;show_faces=true&amp;colorscheme=light&amp;stream=true&amp;show_border=true&amp;header=true"
					scrolling="no" frameborder="0" style="border: none; overflow: hidden; width: 322px; height: 530px;" allowTransparency="true"></iframe>
			</div>
			<!--facebookWidget-->
			<div class="twitterWidget">
				<!--twitterWidget-->
				<a class="twitter-timeline" href="https://twitter.com/${candidate.twitterId}" data-widget-id="339326037013958656" data-screen-name="${candidate.twitterId}">Tweets by
					@${candidate.twitterId}</a>
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
	<div id="embedDialog" title="Embed Candidate Profile on your website">
		<div id="accordion">
			<h3>${candidate.name}</h3>
			<div>
				<p>
					<b>Copy paste code below HTMl code in your website</b>
				</p>
				<textarea rows="5" cols="50" readonly="readonly">
					<iframe src="http://my.aamaadmiparty.org/aapcandidates.html?pcId=${candidate.parliamentConstituencyId}" width="300" scrolling="no" frameborder="0" height="500" />
				</textarea>
			</div>
			<h3>All Candidates</h3>
			<div>
				<p>
					<b>Copy paste code below HTMl code in your website</b>
				</p>
				<textarea rows="5" cols="50" readonly="readonly">
					<iframe src="http://my.aamaadmiparty.org/aapcandidates.html" width="300" scrolling="no" frameborder="0" height="500" />
				</textarea>
			</div>
		</div>
	</div>


	<jsp:include page="footer.jsp" />
	<jsp:include page="addthis.jsp" />

</body>
</html>