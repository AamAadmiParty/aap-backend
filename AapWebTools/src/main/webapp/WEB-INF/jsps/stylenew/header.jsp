<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script>
  window.fbAsyncInit = function() {
    FB.init({
      appId      : '705165199595773',
      xfbml      : true,
      version    : 'v2.3'
    });
  };

  (function(d, s, id){
     var js, fjs = d.getElementsByTagName(s)[0];
     if (d.getElementById(id)) {return;}
     js = d.createElement(s); js.id = id;
     js.src = "//connect.facebook.net/en_US/sdk.js";
     fjs.parentNode.insertBefore(js, fjs);
   }(document, 'script', 'facebook-jssdk'));
</script>
<div class="toplinkfull-holder">
	<!--toplinkfull-holder-->
	<!-- 
	<div class="logo">
		<a href="#"></a>
	</div>
	 -->
	<div class="toplinks">
		<!--toplinks-->
		<ul>
			<li><a class="home" href="${contextPath}/index.html">Home</a></li>
			<!-- 
			<li><a class="contact" href="${contextPath}/candidate/election/2.html">Candidates</a></li>
			 -->
			<li><a class="about" href="#" target="_new">About Us</a></li>
			<li><a class="event" href="#" target="_new">Events</a></li>
			<li><a class="livetv" href="#" target="_new">Live TV</a></li>
			<!-- 
			<li><a class="contact" href="#" target="_new">Contact Us</a></li>
			 -->
			 
		</ul>
	</div>



	<c:if test="${empty loggedInUser}">
		<!--toplinks-->
		<div class="toplinks-sign">
			<!--toplinks-sign-->
			<ul>
				<li class="signin"><a href="${contextPath}/login?v4d_redirect_url=/index.html">Sign In</a></li>
				<li class="guest">Guest</li>
			</ul>
		</div>
		<!--toplinks-sign-->
		<div class="clear"></div>

	</c:if>
	<c:if test="${!empty loggedInUser}">
		<div class="toplinks-sign">
			<!--toplinks-sign-->
			<ul>
				<li class="signin"><a href="${contextPath}/logout">Sign out</a></li>
			</ul>
		</div>
		<!--toplinks-sign-->
		<div class="userprofile">
			<img src="${loggedInUser.profilePic}" width="40" height="45" border="0" /><br />${loggedInUser.name}
		</div>
		<!--userprofile-->
		<div class="clear"></div>

	</c:if>
</div>
<!--toplinkfull-holder-->

