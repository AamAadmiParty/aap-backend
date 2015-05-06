<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:if test="${not empty loggedInUser}">
	<div class="donatewrapper">
		<div class="donatewrappertab">
			<ul>
			<!-- 
				<li><a href="${contextPath}/voa.html">Swaraj ki Awaz</a></li>
				<li><a href="${contextPath}/mydonations.html">My Donations</a></li>
				<li><a href="${contextPath}/ripple.html">My Ripple</a></li>
				-->
                <li><a href="${contextPath}/profile.html">My Profile</a></li>
				<c:if test="${admin}">
					<li><a href="${contextPath}/admin/home">Admin</a></li>
				</c:if>
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
			     <!-- 
				<li><a href="#">Donate</a></li>
				<li><a href="${contextPath}/login?sa_redirect_url=/profile.html">Join US</a></li>
				 -->
				
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
	   <!-- 
		<li><img src="<c:out value='${staticDirectory}'/>/images/slider-green.jpg" alt="" border="0"></li>
		<li><img src="<c:out value='${staticDirectory}'/>/images/slider-kumar.jpg" alt="" border="0"></li>
		<li><img src="<c:out value='${staticDirectory}'/>/images/slider-pink.jpg" alt="" border="0"></li>
		 -->
		
		<!-- 
		<li> <img src="<c:out value='${staticDirectory}'/>/images/slider-red.jpg" alt="" border="0"></li>
		 -->
	</ul>

</div>
<!--slider-->
