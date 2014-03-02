<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="toplinkfull-holder">
	<!--toplinkfull-holder-->
	<div class="logo">
		<a href="#"></a>
	</div>
	<div class="toplinks">
		<!--toplinks-->
		<ul>
			<li class="home"><a href="${contextPath}/index.html">Home</a></li>
			<li class="about"><a href="http://www.aamaadmiparty.org/why-are-we-entering-politics">About Us</a></li>
			<li class="event"><a href="http://www.aamaadmiparty.org/events" target="_new">Events</a></li>
			<li class="livetv"><a href="http://www.youtube.com/liveaap" target="_new">Live TV</a></li>
			<li class="contact"><a href="http://www.aamaadmiparty.org/contact-us" target="_new">Contact Us</a></li>
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
