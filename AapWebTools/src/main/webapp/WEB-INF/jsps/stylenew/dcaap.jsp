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

</head>
<body>
<div id='invtrflfloatbtn'></div>
<script>
var invite_referrals = window.invite_referrals || {}; (function() {
        invite_referrals.auth = { bid_e : 'D22655DE90FB47CC49FB29B55B7C6E4A', bid : '659', t : '420', email : '${donation.donorEmail}',userParams : {'fname': '${donation.donorName}'}, userCustomParams :{'shareImg':'http://my.aamaadmiparty.org${contextPath}/dc/images/<c:out value='${ImageSource}' />'}};
var script = document.createElement('script');script.async = true;
script.src = (document.location.protocol == 'https:' ? "//d11yp7khhhspcr.cloudfront.net" : "//cdn.invitereferrals.com") + '/js/invite-referrals-1.0.js';
var entry = document.getElementsByTagName('script')[0];entry.parentNode.insertBefore(script, entry); })();
</script>
<img style="position:absolute; visibility:hidden" src="http://www.ref-r.com/campaign/t1/settings?bid_e=D22655DE90FB47CC49FB29B55B7C6E4A&bid=659&t=420&orderID=${txnId}&email=${donation.donorEmail}&purchaseValue=${donation.amount}" />



	<div id="fb-root"></div>
	<script>
  window.fbAsyncInit = function() {
    // init the FB JS SDK
    FB.init({
    	appId      : '175121315982296',
      //channelUrl : './channel.html', // Channel file for x-domain comms
      status     : true,                                 // Check Facebook Login status
      xfbml      : true                                  // Look for social plugins on the page
    });

    // Additional initialization code such as adding Event Listeners goes here
  };

  // Load the SDK asynchronously
  (function(d, s, id){
     var js, fjs = d.getElementsByTagName(s)[0];
     if (d.getElementById(id)) {return;}
     js = d.createElement(s); js.id = id;
     js.src = "//connect.facebook.net/en_US/all.js";
     fjs.parentNode.insertBefore(js, fjs);
   }(document, 'script', 'facebook-jssdk'));
  
  function callFbMethod(){
	  ga('send', 'event', 'donation_certificate', 'facebook_share', '${template}', 1);
	  
	  FB.login(function(response) {
		    if (response.authResponse) {
		        // login success, then post a photo
		        FB.api('/me/photos',
		               'post',
		               {
		                   message: '',
		                   url: "<c:out value='${imageurl}' />"
		               },
		               function(response) {
		                 console.log(response);
		                 if (!response || response.error) {
		                     console.log('failed');
		                     alert('Unable to post on facebook '+JSON.stringify(response, null, 4));
		                 } else {
		                     console.log('success');
		                     alert('Posted on facebook, Now you can sign in at http://my.aamaadmiparty.org and view your all donations.');
		                 }
		               }
		        );
		    }
		}, {scope: 'publish_stream'});
  }
  
</script>

	<div class="contentarea">
		<!--contentarea-->
		<div class="loginwithholder">
			<!--loginwithholder-->
			<div class="loginwithinnerholder" style="min-height=300px;">
				<img src="${contextPath}/dc/images/<c:out value='${ImageSource}' />" style="max-width: 800px; max-height: 800px;" />
				<br></br> <a id="shareonfb" href="#"><img src="https://s3.amazonaws.com/myaap/images/facebookshare.png" /> </a>

			</div>
			<c:if test="${empty hideOtherTemplates }">
				<h3>Select Other Styles</h3>
				<table cellpadding="10">
					<tr>
						<td><a id="template01" href="${contextPath}/donationcertifcates.html?txnid=${txnId}" target=_new><img
								style="width: 150px; height: 100px;"
								src="https://lh5.googleusercontent.com/-v9IxH4yKots/UeEjk0Xf-9I/AAAAAAAAMR0/xbaXkSqNHug/w987-h822-no/donations01.jpg" /></a></td>
						<td><a href="${contextPath}/donationcertifcates.html?txnid=${txnId}" target=_new><img
								style="width: 150px; height: 100px;"
								src="https://lh6.googleusercontent.com/-Nw1eaaraEP0/UeDXYRXvmvI/AAAAAAAAMRM/M0UjgS2M_ek/w658-h436-no/donation02.jpg" /></a></td>
						<td><a href="${contextPath}/donationcertifcates.html?txnid=${txnId}" target=_new><img
								style="width: 150px; height: 100px;"
								src="https://lh4.googleusercontent.com/-4kGDOBFxpMY/UfCwashxxkI/AAAAAAAANNw/DCk6O5jwX_E/w1064-h822-no/donations03.jpg" /></a></td>
						<td><a href="${contextPath}/donationcertifcates.html?txnid=${txnId}" target=_new><img
								style="width: 150px; height: 100px;"
								src="https://lh4.googleusercontent.com/-R8T_ggjcylM/UeF7XfPz7iI/AAAAAAAAMSQ/5sybsvhpiDA/w1064-h822-no/donations04.jpg" /></a></td>
					</tr>
					<tr>
						<td><a href="${contextPath}/donationcertifcates.html?txnid=${txnId}" target=_new><img
								style="width: 150px; height: 100px;"
								src="https://lh6.googleusercontent.com/-zeL8afs-9Ek/Uu2ilbjDaAI/AAAAAAAANys/e4q5PbAPNFY/s720/donations05.jpg" /></a></td>
						<td><a href="${contextPath}/donationcertifcates.html?txnid=${txnId}" target=_new><img
								style="width: 150px; height: 100px;"
								src="https://lh4.googleusercontent.com/-MLVEUgPRceQ/Uu2ilGhmdCI/AAAAAAAANyo/yTBBY7zJ-5w/s720/donations06.jpg" /></a></td>
						<td><a href="${contextPath}/donationcertifcates.html?txnid=${txnId}" target=_new><img
								style="width: 150px; height: 100px;"
								src="https://lh6.googleusercontent.com/-rDFFI6sP8do/UvGz3Mtgb4I/AAAAAAAAPZ0/yZxsuNoAMw8/s720/donations07.jpg" /></a></td>
                        <td><a href="${contextPath}/donationcertifcates.html?txnid=${txnId}" target=_new><img
                                style="width: 150px; height: 100px;"
                                src="https://lh3.googleusercontent.com/-48BhfX_Gjfg/VIKjN6X13LI/AAAAAAAASWI/JfTcubcoxXM/s720/teamplate08.jpg" /></a></td>
					</tr>
					<tr>
					</tr>
				</table>
			</c:if>

			<!--loginwithinnerholder-->
		</div>
		<!--loginwithholder-->
	</div>
	<!--contentarea-->

	<script type="text/javascript">
  document.getElementById("shareonfb").onclick = callFbMethod;
</script>

	<script>
  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

  ga('create', 'UA-38065713-7', 'aamaadmiparty.org');
  ga('send', 'pageview');

</script>

</body>
</html>