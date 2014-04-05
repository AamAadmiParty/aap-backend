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
<!-- 
	<jsp:include page="topscroller.jsp" />
 -->
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
							<table width="100%">
								<tr valign="top">
									<td><c:if test="${empty rippleUser.profilePic }">
											<img src='http://kanpuria.com/wp-content/uploads/2013/12/AAP_Kanpur.jpg' style="max-width: 300px; max-height: 300px;" />
										</c:if> <c:if test="${!empty rippleUser.profilePic }">
											<img src='${rippleUser.profilePic}?type=large' style="max-width: 300px; max-height: 300px;" />
										</c:if></td>
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
														<td>${rippleCampaign.myAapShortUrl}</td>
													</tr>
												</tbody>
											</table>
											<input name="globalDonationButton" id="globalDonationButton" value="" type="button" class="donatebtnbig" />
										</div>
									</td>
								</tr>
								<tr>
								<td>
								</td>
								<td>
									<c:out value="${rippleCampaign.description}" escapeXml="false" />
								</td>
								</tr>
								<tr>
									<td></td>
									<td>
									<div class="divarticle">
										<table id="successTable" class="languagetab">
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