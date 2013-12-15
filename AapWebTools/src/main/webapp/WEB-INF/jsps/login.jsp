<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style type="text/css">
.google {
	display: block;
	width: 150px;
	height: 50px;
	background-image: url(http://www.way2ghar.com/images/google.png);
	background-position: 0px -200px;
}

.yahoo {
	display: block;
	width: 150px;
	height: 50px;
	background-image: url(http://www.way2ghar.com/images/google.png);
	background-position: 0px 50px;
}

.facebook {
	display: block;
	width: 150px;
	height: 50px;
	background-image: url(http://www.way2ghar.com/images/google.png);
	background-position: 0px -100px;
}

.myspace {
	display: block;
	width: 150px;
	height: 50px;
	background-image: url(http://www.way2ghar.com/images/google.png);
	background-position: 0px 250px;
}

.linkedin {
	display: block;
	width: 150px;
	height: 50px;
	background-image: url(http://www.way2ghar.com/images/google.png);
	background-position: 0px 350px;
}

.aol {
	display: block;
	width: 150px;
	height: 50px;
	background-image: url(http://www.way2ghar.com/images/google.png);
	background-position: 0px 0px;
}

.twitter {
	display: block;
	width: 150px;
	height: 50px;
	background-image: url(http://www.way2ghar.com/images/google.png);
	background-position: 0px 150px;
}
</style>
</head>
<body>

	<table>
		<tr>
			<td width="300">
				<ul>
					<li style="list-style-type: none;"><a href="./login/twitter"
						class="twitter"><sup>working</sup></a></li>
					<li style="list-style-type: none;"><a href="./login/facebook"
						class="facebook"><sup>working</sup></a></li>
					<li style="list-style-type: none;"><a href="#" class="google"><sup>Under
								Construction</sup></a></li>
					<li style="list-style-type: none;"><a href="#"
						class="linkedin"><sup>Under Construction</sup></a></li>
					<li style="list-style-type: none;"><a href="#" class="yahoo"><sup>Under
								Construction</sup></a></li>
					<li style="list-style-type: none;"><a href="#" class="myspace"><sup>Under
								Construction</sup></a></li>
					<li style="list-style-type: none;"><a href="#" class="aol"><sup>Under
								Construction</sup></a></li>
				</ul>
			</td>
			<td>
				<c:if test="${!empty loggedInUser}">
                	User logged in : <c:out value="${loggedInUser.name}" />
                	<br>
                	User Id : <c:out value="${loggedInUser.externalId}" />
                	<br><br>
                	You are connected with following accounts
                	<br>
                	<c:forEach items="${loginAccounts.twitterAccounts}" var="oneTwitterAccount">
                	<img src="<c:out value='${oneTwitterAccount.imageUrl}' />" /> <c:out value="${oneTwitterAccount.screenName}" />
                	<br>
					</c:forEach>
                	<c:forEach items="${loginAccounts.facebookAccounts}" var="oneFacebookAccount">
                	<img src="<c:out value='${oneFacebookAccount.imageUrl}' />" /> <c:out value="${oneFacebookAccount.userName}" />
                	<br>
					</c:forEach>
                </c:if>
				<c:if test="${empty loggedInUser}">
                	No User logged in
                </c:if>
				
			</td>
		</tr>
	</table>


</body>
</html>