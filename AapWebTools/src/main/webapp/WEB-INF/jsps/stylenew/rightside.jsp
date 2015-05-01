<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script>
	$(function() {

		$('#globalDonationButtonOnRight').click(
				function() {
					ga('send', 'event', 'donation', 'myaap-rightpanel',
							'${candidate.name}', 1);
					window.location.href = '${candidate.donationPageFullUrl}';
					return false;
				});
		$('#candidate1').click(
				function() {
					ga('send', 'event', 'candidate', 'myaap-rightpanel',
							'${candidate.name}', 1);
					return true;
				});
		$('#candidate2').click(
				function() {
					ga('send', 'event', 'candidate', 'myaap-rightpanel',
							'${candidate.name}', 1);
					return true;
				});

	});
</script>
<div class="rhsarea">
	<!--rhsarea-->
	<c:if test="${empty loggedInUser}">
		<div class="joincommunity">
			<!--joincommunity-->
			<a href="${contextPath}/profile.html"><img src="<c:out value='${staticDirectory}'/>/images/joincommunity.jpg" border="0" /></a>
			<ul>
				<li>To participate in polls</li>
				<li>To track your donation network</li>
				<li>To help spread the word</li>
				<li>And lots more...</li>
			</ul>
		</div>
	</c:if>
	<c:if test="${!empty candidate}">
		<div class="aap-performance">
			<a href="${candidate.landingPageFullUrl}" id="candidate1"> <img src="<c:out value='${candidate.imageUrl}'/>"
				style="width: 280px; min-width: 280px;" border="0" />
			</a>
			<div id="success" class="languagetab">
				<table border="0" cellpadding="0" cellspacing="0">
					<tbody>
						<tr>
							<th width="200">Name</th>
							<td width="200"><c:out value='${candidate.name}' /></td>
						</tr>
						<tr>
							<th width="200">Constituency</th>
							<td width="200"><c:out value='${candidate.acName}' />
								<c:out value='${candidate.pcName}' /> - <c:out value='${candidate.stateName}' /></td>
						</tr>
						
						<div class="facebookWidget">
							<!--facebookWidget-->
							<iframe
								src="//www.facebook.com/plugins/likebox.php?href=https%3A%2F%2Fwww.facebook.com%2FSwaraj.Samwad&amp;width=728&amp;height=590&amp;show_faces=true&amp;colorscheme=light&amp;stream=true&amp;show_border=true&amp;header=true"
								scrolling="no" frameborder="0" style="border: none; overflow: hidden; width: 322px; height: 530px;"
								allowTransparency="true"></iframe>
						</div>
						<!--facebookWidget-->
						<div class="twitterWidget">
							<!--twitterWidget-->
							<a class="twitter-timeline" href="https://twitter.com/swaraj_abhiyan" data-widget-id="591375821736120321">Tweets
								by @swaraj_abhiyan</a>
							<script>
								!function(d, s, id) {
									var js, fjs = d.getElementsByTagName(s)[0], p = /^http:/
											.test(d.location) ? 'http'
											: 'https';
									if (!d.getElementById(id)) {
										js = d.createElement(s);
										js.id = id;
										js.src = p
												+ "://platform.twitter.com/widgets.js";
										fjs.parentNode.insertBefore(js, fjs);
									}
								}(document, "script", "twitter-wjs");
							</script>


						</div>
						<!--twitterWidget-->

						<div class="clear"></div>
						<%--
		<div class="trending">
			<!--trending-->
			<img src="<c:out value='${staticDirectory}'/>/images/trending.jpg" border="0" />
			<div class="trendingfull">
				<!--trendingfull-->
				<img src="<c:out value='${staticDirectory}'/>/images/aapLogoArticle-nail-Small.jpg" border="0" />
				<p>Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical..</p>
			</div>
			<!--trendingfull-->

			<div class="trendingfull">
				<!--trendingfull-->
				<img src="<c:out value='${staticDirectory}'/>/images/aapLogoArticle-nail-Small.jpg" border="0" />
				<p>Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical..</p>
			</div>
			<!--trendingfull-->

			<div class="trendingfull">
				<!--trendingfull-->
				<img src="<c:out value='${staticDirectory}'/>/images/aapLogoArticle-nail-Small.jpg" border="0" />
				<p>Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical..</p>
			</div>
			<!--trendingfull-->

			<div class="trendingfull">
				<!--trendingfull-->
				<img src="<c:out value='${staticDirectory}'/>/images/aapLogoArticle-nail-Small.jpg" border="0" />
				<p>Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical..</p>
			</div>
			<!--trendingfull-->
			<div class="trendingfull">
				<!--trendingfull-->
				<img src="<c:out value='${staticDirectory}'/>/images/aapLogoArticle-nail-Small.jpg" border="0" />
				<p>Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical..</p>
			</div>
			<!--trendingfull-->
			<div class="trendingfull">
				<!--trendingfull-->
				<img src="<c:out value='${staticDirectory}'/>/images/aapLogoArticle-nail-Small.jpg" border="0" />
				<p>Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical..</p>
			</div>
			<!--trendingfull-->

		</div>
		 --%>
						<!--trending-->
			</div>
			<!--rhsarea-->