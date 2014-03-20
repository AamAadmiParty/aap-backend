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
			<li><a href="${contextPath}/videos.html"><img src="<c:out value='${staticDirectory}'/>/images/video-blue-icon.png" border="0" align="absmiddle" />
					Videos</a></li>
			<li><img src="<c:out value='${staticDirectory}'/>/images/blog-blue-icon.png" border="0" align="absmiddle" /> Blogs</li>
		</ul>
	</div>
	<!--articleCategory-tablist-->

	<c:forEach items="${blogItems.items}" var="oneBlog">

		<div class="divblogarticle">
			<!--divarticle-->
			<h1>
				<a href="${contextPath}/content/blog/${oneBlog.id}">
				<c:out value="${oneBlog.title}" />
				<!--  
					<c:if test="${fn:length(oneBlog.title) gt 47}">
						<c:out value="${fn:substring(oneBlog.title, 0, 47)}" />
					</c:if> <c:if test="${fn:length(oneBlog.title) le 47}">
						<c:out value="${oneBlog.title}" />
					</c:if>
				 -->
				</a>
			</h1>
			<div class="img">
				<!--img-->
				<img src='<c:out value="${oneBlog.imageUrl}" />' width="200" border="0" />
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
			<c:if test="${fn:length(oneBlog.contentSummary) gt 500}">
				<p>
					<c:out value="${fn:substring(oneBlog.contentSummary, 0, 500)}" escapeXml="false" />
					...<a href="${contextPath}/content/blog/${oneBlog.id}">Read More</a>
				</p>
			</c:if>
			<c:if test="${fn:length(oneBlog.contentSummary) le 500}">
				<p>
					<c:out value="${oneBlog.contentSummary}" escapeXml="false" />
					<a href="${contextPath}/content/blog/${oneBlog.id}">Read More</a>
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
	<jsp:include page="addthis.jsp" />
</body>
</html>