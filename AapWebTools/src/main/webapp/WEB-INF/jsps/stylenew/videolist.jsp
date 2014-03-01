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
			<li><a href="${contextPath}/index.html"><img src="<c:out value='${staticDirectory}'/>/images/news-icon.png" border="0" align="absmiddle" /> News</a></li>
			<li><img src="<c:out value='${staticDirectory}'/>/images/video-blue-icon.png" border="0" align="absmiddle" />
					Videos</li>
			<li><a href="${contextPath}/blogs.html"><img src="<c:out value='${staticDirectory}'/>/images/blog-blue-icon.png" border="0" align="absmiddle" />
					Blogs</a></li>
		</ul>
	</div>
	<!--articleCategory-tablist-->

	<c:forEach items="${videoItems.items}" var="oneNews">

		<div class="divvideoarticle">
			<!--divarticle-->
			<h1>
				<a href="${contextPath}/content/video/${oneNews.id}"> <c:if test="${fn:length(oneNews.title) gt 47}">
						<c:out value="${fn:substring(oneNews.title, 0, 47)}" />
					</c:if> <c:if test="${fn:length(oneNews.title) le 47}">
						<c:out value="${oneNews.title}" />
					</c:if>

				</a>
			</h1>
			<div class="img">
				<!--img-->
				<img src='<c:out value="${oneNews.imageUrl}" />' width="245" height="145" border="0" />
				<div class="author">
					<p>10 Min Ago</p>
					<p>
						By <span>Arvind</span>
					</p>
					<p class="orange">NATIONAL</p>
				</div>
			</div>
			<!--img-->
			<c:if test="${fn:length(oneNews.description) gt 500}">
				<p>
					<c:out value="${fn:substring(oneNews.description, 0, 500)}" escapeXml="false" />
					... <a href="${contextPath}/content/video/${oneNews.id}">Watch Now</a>
				</p>
			</c:if>
			<c:if test="${fn:length(oneNews.description) le 500}">
				<p>
					<c:out value="${oneNews.description}" escapeXml="false" />
					<a href="${contextPath}/content/video/${oneNews.id}">Watch Now</a>
				</p>
			</c:if>


		</div>
		<!--divarticle-->
	</c:forEach>


	</div>
	<!--article-leftarea-->




	<jsp:include page="rightside.jsp" />

	</div>
		<jsp:include page="footer.jsp" />
	
</body>
</html>