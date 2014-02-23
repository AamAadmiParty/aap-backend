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
		$("#tabs").tabs();
	});
</script>
<script>	
var invite_referrals = window.invite_referrals || {}; (function() { 
	invite_referrals.auth = { bid_e : 'D22655DE90FB47CC49FB29B55B7C6E4A', bid : '659', t : '420', orderID : '' };	
var script = document.createElement('script');script.async = true;
script.src = (document.location.protocol == 'https:' ? "//d11yp7khhhspcr.cloudfront.net" : "//cdn.invitereferrals.com") + '/js/invite-referrals-1.0.js';
var entry = document.getElementsByTagName('script')[0];entry.parentNode.insertBefore(script, entry); })();
</script>
<script type="text/javascript">
	function inviteFriendsToJoin(){ 
		var params = { bid: 659, cid: 833 };
		invite_referrals.widget.inlineBtn(params);
    }
	function inviteFriendsToDonateUsingMyRipple(url){ 
		var params = { bid: 659, cid: 836, userCustomParams : {'shareLink': url}  };
		invite_referrals.widget.inlineBtn(params);
    }
	function invitereferrals_donate4aap(){ 
		var params = { bid: 659, cid: 113};
		invite_referrals.widget.inlineBtn(params);
    }
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
				<div id="tabs">
					<ul>
						<c:if test="${rippleCampaignExists}">
							<li><a href="#success">Success</a></li>
							<li><a href="#failed">failed</a></li>
						</c:if>
						<li><a href="#setting">Ripple Setup</a></li>
					</ul>
					<c:if test="${rippleCampaignExists}">
						<div id="success">
							<table id="successTable" style="border: 1px solid;">
								<thead>
									<tr>
										<th style="border: 1px solid;">Transaction Id</th>
										<th style="border: 1px solid;">Date</th>
										<th style="border: 1px solid;">Amount</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${successDonations}" var="oneDonation">

										<tr>
											<td style="border: 1px solid;"><c:out value="${oneDonation.transactionId}"></c:out></td>
											<td style="border: 1px solid;"><fmt:formatDate value="${oneDonation.donationDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
											<td style="border: 1px solid;"><c:out value="${oneDonation.amount}"></c:out></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
						<div id="failed">
							<table id="failedTable">
								<thead>
									<tr>
										<th style="border: 1px solid;">Transaction Id</th>
										<th style="border: 1px solid;">Date</th>
										<th style="border: 1px solid;">Amount</th>
										<th style="border: 1px solid;">Status/Error</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${failedDonations}" var="oneDonation">

										<tr>
											<td style="border: 1px solid;"><c:out value="${oneDonation.transactionId}"></c:out></td>
											<td style="border: 1px solid;"><fmt:formatDate value="${oneDonation.donationDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
											<td style="border: 1px solid;"><c:out value="${oneDonation.amount}"></c:out></td>
											<td style="border: 1px solid;"><c:out value="${oneDonation.pgErrorMessage}"></c:out></td>

										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</c:if>
					<div id="setting">
						<div class="editprofilehead">Spread the word, build your own donation network</div>
						<br />
						<div class="formwrapper">
							<!--formwrapper-->
							<div class="editprofile">Edit Ripple Campaign</div>
							<form method="post">
								<jsp:include page="errors.jsp" />
								<div class="blockdiv">
									<label>Campaign Id</label>
									<c:if test="${rippleCampaignExists}">
									<input disabled=true type="text" name="campaignId" id="campaignId" value="<c:out value='${rippleCampaign.campaignId}'/>"
										class="textbox" />
									</c:if> 
									<c:if test="${!rippleCampaignExists}">
									<input type="text" name="campaignId" id="campaignId" value="<c:out value='${rippleCampaign.campaignId}'/>"
										class="textbox" />
									</c:if> 
									
								</div>
								<div class="blockdiv">
									<label>Description</label>
									<textarea rows="" cols="" name="description" id="description" class="textarea"><c:out value='${rippleCampaign.description}'/></textarea>
								</div>
								<c:if test="${rippleCampaignExists}">
								<div class="blockdiv">
									<label>Personal URL</label>
									<input disabled=true type="text" name="campaignId" id="campaignId" value="<c:out value='${rippleCampaign.myAapShortUrl}'/>"
										class="textbox" />
								</div>
								
								<div class="blockdiv">
									<label>Share</label>
									<input name="save" type="button" class="button" value="Share Personal URL" 
									onclick="inviteFriendsToDonateUsingMyRipple('${rippleCampaign.myAapShortUrl}')" />
								</div>
								</c:if>
								<input name="save" type="submit" class="button" value="Save Ripple Campaign" />
								  
							</form>
						</div>
					</div>

				</div>
				<!--loginwithinnerholder-->
			</div>
			<!--loginwithholder-->
		</div>
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