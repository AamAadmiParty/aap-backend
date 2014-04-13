<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Ripple : ${rippleUser.name} - Aam Aadmi Party, India</title>
<jsp:include page="includes.jsp" />

<meta name="description"
	content="Hi Friends, I ${rippleUser.name} supporting AAM AADMI PARTY and inviting you to help me and party. Come and join me to help our country and donate for better future" />
<c:if test="${empty rippleUser.profilePic }">
	<meta name="og:image" property="og:image" content="http://kanpuria.com/wp-content/uploads/2013/12/AAP_Kanpur.jpg" />
	<meta name="og:image" property="og:image" content="https://lh5.ggpht.com/Agz_PiBlwdqdWE3ypjXo4stC-l8tiRlDJnnIZlKT_XapaKM_h5LPrdzNLtkbyW5n3JU=w300" />
	<meta name="og:image" property="og:image" content="http://shakilahmed.org/assets/images/aap.png" />
</c:if>
<c:if test="${!empty rippleUser.profilePic }">
	<meta name="og:image" property="og:image" content="${rippleUser.profilePic}" />
</c:if>
<meta name="og:title" property="og:title" content="Donate for ${rippleUser.name}" />
<meta name="og:url" property="og:url" content="${rippleCampaign.myAapShortUrl}" />
<meta name="og:site_name" property="og:site_name" content="my.aamaadmiparty.org" />
<meta name="og:type" property="og:type" content="blog" />
<meta name="og:description" property="og:description"
	content="Hi Friends, I ${rippleUser.name} supporting AAM AADMI PARTY and inviting you to help me and party. Come and join me to help our country and donate for better future" />


<script>
	$(function() {

		$('#globalDonationButton').click(function() {
			window.location.href = '${rippleCampaign.myAapShortUrl}';
			return false;
		});
	});
</script>

<script>
	var invite_referrals = window.invite_referrals || {};
	(function() {
		invite_referrals.auth = {
			bid_e : 'D22655DE90FB47CC49FB29B55B7C6E4A',
			bid : '659',
			t : '420',
			orderID : ''
		};
		var script = document.createElement('script');
		script.async = true;
		script.src = (document.location.protocol == 'https:' ? "//d11yp7khhhspcr.cloudfront.net"
				: "//cdn.invitereferrals.com")
				+ '/js/invite-referrals-1.0.js';
		var entry = document.getElementsByTagName('script')[0];
		entry.parentNode.insertBefore(script, entry);
	})();
</script>
<script type="text/javascript">
	function inviteFriendsToJoin() {
		var params = {
			bid : 659,
			cid : 833
		};
		invite_referrals.widget.inlineBtn(params);
	}
	function inviteFriendsToDonateUsingMyRipple(url) {
		var params = {
			bid : 659,
			cid : 836,
			userCustomParams : {
				'shareLink' : url
			}
		};
		invite_referrals.widget.inlineBtn(params);
	}
	function invitereferrals_donate4aap() {
		var params = {
			bid : 659,
			cid : 113
		};
		invite_referrals.widget.inlineBtn(params);
	}
	$(function() {
		$(document).tooltip({
			// place tooltip on the right edge
			position : {
				my : "left top+1",
				at : "right top"
			},
			//tooltipClass: "tooltip",
			// a little tweaking of the position
			//offset: [-2, 10],

			// use the built-in fadeIn/fadeOut effect
			effect : "fade",

			// custom opacity setting
			opacity : 0.7

		});
	});
</script>
</head>
<body>
<div id="fb-root"></div>
<script>(function(d, s, id) {
  var js, fjs = d.getElementsByTagName(s)[0];
  if (d.getElementById(id)) return;
  js = d.createElement(s); js.id = id;
  js.src = "//connect.facebook.net/en_US/all.js#xfbml=1&appId=1383280855238363";
  fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));</script>

	<jsp:include page="header.jsp" />
	
	<div class="contentarea">
		<!--contentarea-->
		<div class="loginwithholder">
			<!--loginwithholder-->
			<div class="loginwithinnerholder">
				<!--loginwithinnerholder-->
				<div id="rippleprofile">
					<c:if test="${!rippleCampaignExists}">
					No Such ripple Campaign Exists
					</c:if>
					<c:if test="${rippleCampaignExists}">
						<div class="divarticle">
							<!--divarticle-->
							<table width="100%" align="left">
								<tr valign="top" align="left">
									<td><c:if test="${empty rippleUser.profilePic }">
											<img src='http://kanpuria.com/wp-content/uploads/2013/12/AAP_Kanpur.jpg' style="max-width: 300px; max-height: 300px;" />
										</c:if> <c:if test="${!empty rippleUser.profilePic }">
											<img src='${rippleUser.profilePic}?type=large' style="min-width: 250px; max-width: 300px; max-height: 300px;" />
										</c:if> 
										<br></br>
										<c:if test="${!empty rippleUserFacebookAccount }">
										<div class="fb-follow" data-href="https://www.facebook.com/${rippleUserFacebookAccount}" data-width="250" data-colorscheme="dark" data-layout="standard" data-show-faces="true"></div>
										<br></br>
										</c:if>
										<c:if test="${!empty rippleUserTwitterAccount }">
											<a class="twitter-timeline" width="250" href="https://twitter.com/${rippleUserTwitterAccount}" data-widget-id="339326037013958656" data-screen-name="${rippleUserTwitterAccount}">Tweets by
											${rippleUserTwitterAccount}</a>
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
										<br></br>
										</c:if>
										
										</td>
									<td>
										<h1>
											<c:out value="${rippleUser.name}" />
										</h1>
										<div id="success" class="languagetab">
											<table border="0" cellpadding="0" cellspacing="0">
												<tbody>
													<tr>
														<th width="200">Total Transactions</th>
														<td width="200">${fn:length(successDonations)}</td>
													</tr>
													<tr>
														<th>Total Amount</th>
														<td>${total}</td>
													</tr>
													<tr>
														<th>URL to this page</th>
														<td>http://my.aamaadmiparty.org${contextPath}/ripple/${rippleCampaign.campaignId}.html</td>
													</tr>
													<tr>
														<th>Direct Donation URL</th>
														<td><a href="${rippleCampaign.myAapShortUrl}">${rippleCampaign.myAapShortUrl}</a></td>
													</tr>
												</tbody>
											</table>
											<input name="globalDonationButton" id="globalDonationButton" value="" type="button" class="donatebtnbig" />
										</div>
										<br></br><br></br>
										<div>
											<c:out value="${rippleCampaign.description}" escapeXml="false" />
										</div>
										<br></br>
										<br></br>	
										<div id="success" class="languagetab">
											<table >
												<thead>
													<tr>
														<th style="border: 1px solid;">Donor Name</th>
														<th style="border: 1px solid;">Transaction Id</th>
														<th style="border: 1px solid;">Date</th>
														<th style="border: 1px solid;">Amount</th>
														<th style="border: 1px solid;">Donation Certificate</th>
													</tr>
												</thead>
												<tbody>
													<c:forEach items="${successDonations}" var="oneDonation">

														<tr>
															<td style="border: 1px solid;"><c:out value="${oneDonation.donorName}"></c:out></td>
															<td style="border: 1px solid;"><c:out value="${oneDonation.transactionId}"></c:out></td>
															<td style="border: 1px solid;"><fmt:formatDate value="${oneDonation.donationDate}" pattern="dd-MMM-yyyy HH:mm:ss" /></td>
															<td style="border: 1px solid;"><c:out value="${oneDonation.amount}"></c:out></td>
															<td style="border: 1px solid;"><a href='${contextPath}/donationcertifcates.html?txnid=${oneDonation.transactionId}'> Generate</a></td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</div>
									</td>
								</tr>
							</table>


						</div>

					</c:if>


				</div>
				<!--loginwithinnerholder-->
			</div>
			<!--loginwithholder-->
		</div>
	</div>
	<!--contentarea-->



	<jsp:include page="footer.jsp" />

</body>
</html>