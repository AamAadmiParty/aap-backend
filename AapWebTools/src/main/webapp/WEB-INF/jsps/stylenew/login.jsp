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
		<div class="loginwithholder">
			<!--loginwithholder-->
			<div class="loginwithinnerholder">
				<!--loginwithinnerholder-->
				<h3>Please Login Via Facebook to Register</h3>
				<ul>
					<c:if test="${empty loggedInUser}">
						<li><a href="${contextPath}/login/facebook?${loginParams}"><img src="<c:out value='${staticDirectory}'/>/images/loginFB.png" border="0" /></a></li>
						<!-- 
						<li><a href="${contextPath}/login/twitter?${loginParams}"><img src="<c:out value='${staticDirectory}'/>/images/login-twitter.png" border="0" /> </a></li>
						 -->
					</c:if>
					<c:if test="${!empty loggedInUser}">
						<c:if test="${empty loginAccounts.facebookAccounts}">
							<li><a href="${contextPath}/login/facebook"><img src="<c:out value='${staticDirectory}'/>/images/loginFB.png" border="0" /></a></li>
						</c:if>
						<c:if test="${!empty loginAccounts.facebookAccounts}">
							<li><c:forEach var="oneFacebookAccount" items="${loginAccounts.facebookAccounts}">
									<a href="#"> <img src="${oneFacebookAccount.imageUrl}" />
									</a>
								</c:forEach></li>
						</c:if>
						<!-- 
						<c:if test="${empty loginAccounts.twitterAccounts}">
							<li><a href="${contextPath}/login/twitter"><img src="<c:out value='${staticDirectory}'/>/images/login-twitter.png" border="0" /> </a></li>
						</c:if>
						<c:if test="${!empty loginAccounts.twitterAccounts}">
							<li><c:forEach var="oneTwitterAccount" items="${loginAccounts.twitterAccounts}">
									<a href="#"> <img src="${oneTwitterAccount.imageUrl}" />
									</a>
								</c:forEach></li>
						</c:if>
                        -->
						
					</c:if>


				</ul>
				<!-- 
						<ul>
							<li class="noborder"><a href="#"><img src="<c:out value='${staticDirectory}'/>/images/loginyahoo.png" border="0" /></a></li>
							<li class="noborder"><a href="#"><img src="<c:out value='${staticDirectory}'/>/images/logingoogle.png" border="0" /></a></li>
						</ul>
						 -->
			</div>
			<!--loginwithinnerholder-->
		</div>
		<!--loginwithholder-->
	</div>
	<!--contentarea-->



	<jsp:include page="footer.jsp" />
</body>
</html>