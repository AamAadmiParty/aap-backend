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
				<div id="tabs">
					<div id="success" class="languagetab">
						<form method="post">
							<div class="blockdiv">
								<label>Transaction Id</label> <input id="txnid" name="txnid" type="text" class="textbox" value="${txnid}" title="Please Enter Transaction Id i.e NI1234" />
							</div>
							<div class="blockdiv">
								<label>Email Id</label> <input id="emailid" name="emailid" type="text" class="textbox" value="${emailid}" title="Please Enter Email Id i.e xyz@gmail.com" />
							</div>
							<input name="buttonClicked" type="submit" class="button" value="View Donation Info" id="buttonClicked" /> <input name="buttonClicked" type="submit" class="button"
								value="Refresh Donation Info From .net" id="buttonClicked" />
						</form>
						
						<h3><c:out value="${message}" /></h3>
						
						<c:if test="${view eq 'donations' }">
							<table id="successTable" style="border: 1px solid;">
								<thead>
									<tr>
										<th style="border: 1px solid;">Transaction Id</th>
										<th style="border: 1px solid;">Date</th>
										<th style="border: 1px solid;">Amount</th>
										<th style="border: 1px solid;">Status</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${donations}" var="oneDonation">

										<tr>
											<td style="border: 1px solid;"><c:out value="${oneDonation.transactionId}"></c:out></td>
											<td style="border: 1px solid;"><fmt:formatDate value="${oneDonation.donationDate}" pattern="dd-MMM-yyyy HH:mm:ss" /></td>
											<td style="border: 1px solid;"><c:out value="${oneDonation.amount}"></c:out></td>
											<td style="border: 1px solid;"><c:out value="${oneDonation.pgErrorDetail}"></c:out></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</c:if>
						

					</div>
					
				</div>

			</div>
			<!--loginwithinnerholder-->
		</div>
		<!--loginwithholder-->
	</div>
	<!--contentarea-->



	<jsp:include page="footer.jsp" />

</body>
</html>