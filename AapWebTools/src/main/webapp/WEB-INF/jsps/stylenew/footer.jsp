<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
				<li><a href="#">Publicity Material</a></li>
				<li><a href="#">Official Spokespersons</a></li>
				<li><a href="#">Videos</a></li>
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
				<li><a href="https://www.facebook.com/swaraj.samwad"><img src="<c:out value='${staticDirectory}'/>/images/facebooklogo.png" border="0" align="absmiddle" /></a></li>
				<li><a href="https://twitter.com/swaraj_abhiyan"><img src="<c:out value='${staticDirectory}'/>/images/twitterlogo.png" border="0" align="absmiddle" /></a></li>
				<li><a href="#"><img src="<c:out value='${staticDirectory}'/>/images/youtubeicon.png" border="0" align="absmiddle" /></a></li>
			</ul>
		</div>
		<!--conectleft-->
		<div class="copyright">
			<!--copyright-->
			© Swaraj Abhiyan. All Rights Reserved, Developed by <a href="#">Ravi Sharma</a>
		</div>
		<!--copyright-->
	</div>
	<!--footercontent-->
</div>
<!--footerfull-->
<script>
  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

  ga('create', 'UA-38065713-7', 'aamaadmiparty.org');
  ga('send', 'pageview');

</script>