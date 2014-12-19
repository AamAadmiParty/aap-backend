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
<script>

</script>
</head>
<body>

	<jsp:include page="header.jsp" />

	<jsp:include page="topscroller.jsp" />
 
  
</div>
 

	<div class="contentarea">
		<!--contentarea-->
		<div class="loginwithholder">
			<!--loginwithholder-->
			<div class="loginwithinnerholder">
				<c:out value="${Error}"></c:out>
				<c:if test="${empty donation }">
					<form>
						<div class="blockdiv">
							<label>Transaction Id</label> <input id="txnid" name="txnid" type="text" class="textbox" title="Please Enter Transaction Id i.e NI1234" />
						</div>
						<input name="" type="submit" class="button" value="Generate Certificate" />
					</form>
				</c:if>
				<c:if test="${!empty donation }">
					<table cellpadding="10">
						<tr>
							<td><a id="template01" href="${contextPath}/dc/template01.html?txnid=${donation.transactionId}"><img style="width: 300px; height: 200px;"
									src="https://lh5.googleusercontent.com/-v9IxH4yKots/UeEjk0Xf-9I/AAAAAAAAMR0/xbaXkSqNHug/w987-h822-no/donations01.jpg" /></a></td>
							<td><a href="${contextPath}/dc/template02.html?txnid=${donation.transactionId}"><img style="width: 300px; height: 200px;"
									src="https://lh6.googleusercontent.com/-Nw1eaaraEP0/UeDXYRXvmvI/AAAAAAAAMRM/M0UjgS2M_ek/w658-h436-no/donation02.jpg" /></a></td>
							<td><a href="${contextPath}/dc/template03.html?txnid=${donation.transactionId}"><img style="width: 300px; height: 200px;"
									src="https://lh4.googleusercontent.com/-4kGDOBFxpMY/UfCwashxxkI/AAAAAAAANNw/DCk6O5jwX_E/w1064-h822-no/donations03.jpg" /></a></td>
						</tr>
						<tr>
							<td><a href="${contextPath}/dc/template04.html?txnid=${donation.transactionId}"><img style="width: 300px; height: 200px;"
									src="https://lh4.googleusercontent.com/-R8T_ggjcylM/UeF7XfPz7iI/AAAAAAAAMSQ/5sybsvhpiDA/w1064-h822-no/donations04.jpg" /></a></td>
							<td><a href="${contextPath}/dc/template05.html?txnid=${donation.transactionId}"><img style="width: 300px; height: 200px;"
									src="https://lh6.googleusercontent.com/-zeL8afs-9Ek/Uu2ilbjDaAI/AAAAAAAANys/e4q5PbAPNFY/s720/donations05.jpg" /></a></td>
							<td><a href="${contextPath}/dc/template06.html?txnid=${donation.transactionId}"><img style="width: 300px; height: 200px;"
									src="https://lh4.googleusercontent.com/-MLVEUgPRceQ/Uu2ilGhmdCI/AAAAAAAANyo/yTBBY7zJ-5w/s720/donations06.jpg" /></a></td>
						</tr>
						<tr>
							<td><a href="${contextPath}/dc/template07.html?txnid=${donation.transactionId}"><img style="width: 300px; height: 200px;"
									src="https://lh6.googleusercontent.com/-rDFFI6sP8do/UvGz3Mtgb4I/AAAAAAAAPZ0/yZxsuNoAMw8/s720/donations07.jpg" /></a></td>
									
                            <td><a href="${contextPath}/dc/template08.html?txnid=${donation.transactionId}"><img style="width: 300px; height: 200px;"
                                    src="https://lh3.googleusercontent.com/-48BhfX_Gjfg/VIKjN6X13LI/AAAAAAAASWI/JfTcubcoxXM/w483-h604-no/teamplate08.jpg" /></a></td>
									
						</tr>
					</table>
				</c:if>

			</div>
			<!--loginwithinnerholder-->
		</div>
		<!--loginwithholder-->
	</div>
	<!--contentarea-->



	<jsp:include page="footer.jsp" />

</body>
</html>