<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${not empty loggedInUser}">
	<div class="donatewrapper">
		<!--donatewrapper-->

		<div class="donatewrappertab">
			<!--donatewrappertab-->
			<ul>
				<li><a href="${contextPath}/voa.html">Voice of AAP</a></li>
				<li><a href="${contextPath}/mydonations.html">My Donations</a></li>
				<li><a href="${contextPath}/profile.html">My Profile</a></li>
				<li><a href="${contextPath}/ripple.html">My Ripple</a></li>
			</ul>
		</div>
	</div>
</c:if>

<!--donatewrappertab-->

<!--donatetabwrapper-->
<c:if test="${empty loggedInUser}">
	<div class="donatetabwrapper">
		<div class="donatetabe">
			<!--donatetabe-->
			<ul>
				<li><a href="http://donate.aamaadmiparty.org">Donate</a></li>
				<!-- <li><a href="#">Register</a></li> -->
			</ul>
		</div>
		<!--donatetabe-->
	</div>
	<!--donatetabwrapper-->
</c:if>

<div class="slider">
	<!--slider-->
	<ul class="rslides">
		<li><img src="<c:out value='${staticDirectory}'/>/images/slider-green.jpg" alt="" border="0"></li>
		<li><img src="<c:out value='${staticDirectory}'/>/images/slider-blue-green.jpg" alt="" border="0"></li>
		<li><img src="<c:out value='${staticDirectory}'/>/images/slider-pink.jpg" alt="" border="0"></li>
		<li> <img src="<c:out value='${staticDirectory}'/>/images/slider-red.jpg" alt="" border="0"></li>
	</ul>

</div>
<!--slider-->
