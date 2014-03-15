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
</head>
<body>

	<jsp:include page="header.jsp" />

	<jsp:include page="topscroller.jsp" />



<div class="contentarea">
	<!--contentarea-->

	<div class="article-leftarea">
		<!--article-leftarea-->
	<div class="articleCategory-tablist">
		<!--articleCategory-tablist-->
		<ul>
			<li><img src="<c:out value='${staticDirectory}'/>/images/news-icon.png" border="0" align="absmiddle" /> News</li>
			<li><a href="${contextPath}/videos.html"><img src="<c:out value='${staticDirectory}'/>/images/video-blue-icon.png" border="0" align="absmiddle" />
					Videos</a></li>
			<li><a href="${contextPath}/blogs.html"><img src="<c:out value='${staticDirectory}'/>/images/blog-blue-icon.png" border="0" align="absmiddle" />
					Blogs</a></li>
		</ul>
	</div>
	<!--articleCategory-tablist-->

	<c:forEach items="${newsItems.items}" var="oneNews">

		<div class="divarticle">
			<!--divarticle-->
			<h1>
				<a href="${contextPath}/content/news/${oneNews.id}">
				<c:out value="${oneNews.title}" />
				<!--  
					<c:if test="${fn:length(oneNews.title) gt 47}">
						<c:out value="${fn:substring(oneNews.title, 0, 47)}" />
					</c:if> 
					<c:if test="${fn:length(oneNews.title) le 47}">
						<c:out value="${oneNews.title}" />
					</c:if>
				--> 
				</a>
			</h1>
			<div class="img">
				<!--img-->
				<img src='<c:out value="${oneNews.imageUrl}" />' width="200" border="0" />
				<!-- 
				<div class="author">
					<p>10 Min Ago</p>
					<p>
						By <span>Arvind</span>
					</p>
					<p class="orange">NATIONAL</p>
				</div>
				 -->
			</div>
			<!--img-->
			<c:if test="${fn:length(oneNews.contentSummary) gt 500}">
				<p>
					<c:out value="${fn:substring(oneNews.contentSummary, 0, 500)}" escapeXml="false" />
					...<a href="${contextPath}/content/news/${oneNews.id}">Read More</a>
				</p>
			</c:if>
			<c:if test="${fn:length(oneNews.contentSummary) le 500}">
				<p>
					<c:out value="${oneNews.contentSummary}" escapeXml="false" />
					<a href="${contextPath}/content/news/${oneNews.id}">Read More</a>
				</p>
			</c:if>


		</div>
		<!--divarticle-->
	</c:forEach>


	</div>
	<!--article-leftarea-->



	<jsp:include page="rightside.jsp" />
	

	</div>
	<!--contentarea-->
	
	<jsp:include page="footer.jsp" />

</body>
</html>