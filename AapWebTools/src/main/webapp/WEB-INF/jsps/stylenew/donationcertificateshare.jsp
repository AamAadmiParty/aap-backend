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
</head>
<body>
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
	  /*
	  FB.ui(
			  {
			    method: 'me/photos',
			    name: 'The Facebook SDK for Javascript',
			    caption: 'Bringing Facebook to the desktop and mobile web',
			    description: (
			       'A small JavaScript library that allows you to harness ' +
			       'the power of Facebook, bringing the user\'s identity, ' +
			       'social graph and distribution power to your site.'
			    ),
			    picture: 'https://lh6.googleusercontent.com/-Izqer-qmsz8/UcIKSuEUnbI/AAAAAAAAL1Q/OH1MOf7fuHI/w850-h315-no/covertemplate02.png',
			  },
			  function(response) {
			    if (response && response.post_id) {
			      alert('Post was published.');
			    } else {
			      alert('Post was not published.');
			    }
			  }
			);
	  */
	  
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
		                     alert('Posted on facebook, Now you can sign in and view your all donations.');
		                 }
		               }
		        );
		    }
		}, {scope: 'publish_stream'});
  }
  
</script>
	<jsp:include page="header.jsp" />

	<jsp:include page="topscroller.jsp" />



	<div class="contentarea">
		<!--contentarea-->
		<div class="loginwithholder">
			<!--loginwithholder-->
			<div class="loginwithinnerholder">
				<img src="${contextPath}/dc/images/<c:out value='${ImageSource}' />"
								style="max-width: 800px; max-height: 800px;" />
								<br></br>
								<a id="shareonfb" href="#"><img
							src="https://s3.amazonaws.com/myaap/images/facebookshare.png" /> </a>

			</div>
			<!--loginwithinnerholder-->
		</div>
		<!--loginwithholder-->
	</div>
	<!--contentarea-->

<script type="text/javascript">
  document.getElementById("shareonfb").onclick = callFbMethod;
</script>


	<jsp:include page="footer.jsp" />

</body>
</html>