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
	$(function() {
		$("#tabs").tabs();
	});
</script>
</head>
<body>

	<jsp:include page="header.jsp" />

	<jsp:include page="topscroller.jsp" />



	<div class="contentarea">
		<!--contentarea-->
		<div class="loginwithholder">
			<!--loginwithholder-->
			<div class="loginwithinnerholder">
				<c:if test="${!empty loggedInUser.email }">
					${loggedInUser.name} - All donations made by you using ${loggedInUser.email} are listed here. If you dont find a specific donation made by you in this list, please email the txn # & date of donation to us at donation@aamaadmiparty.org
				</c:if>
				<!--loginwithinnerholder-->
				<div id="tabs">
					<ul>
						<li><a href="#success">Success</a></li>
						<li><a href="#failed">failed</a></li>
					</ul>
					<div id="success" class="languagetab">
						<table><tr>
                                                        <th> Total Donation Amount</th>
                                                        <td> ${total} </td>
                                                        </tr><tr>
                                                        <th> Total Donations </th>
                                                        <td> ${fn:length(successDonations)}</td>
                                                        </tr>
                                                        </table>
						<table id="successTable" style="border: 1px solid;">
							<thead>
								<tr>
									<th style="border: 1px solid;">Transaction Id</th>
									<th style="border: 1px solid;">Date</th>
									<th style="border: 1px solid;">Amount</th>
									<th style="border: 1px solid;">Donation Certificate</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${successDonations}" var="oneDonation">

									<tr>
										<td style="border: 1px solid;"><c:out value="${oneDonation.transactionId}"></c:out></td>
										<td style="border: 1px solid;"><fmt:formatDate value="${oneDonation.donationDate}" pattern="dd-MMM-yyyy HH:mm:ss" /></td>
										<td style="border: 1px solid;"><c:out value="${oneDonation.amount}"></c:out></td>
										<td style="border: 1px solid;"><a target=_new href='http://www.donate4india.org/web/prevbadger.html?txnid=${oneDonation.transactionId}'> Generate</a></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<div id="failed" class="languagetab">
						<table id="failedTable">
							<thead>
								<tr>
									<th style="border: 1px solid;">Transaction Id</th>
									<th style="border: 1px solid;">Date</th>
									<th style="border: 1px solid;">Amount</th>
									<th style="border: 1px solid;">Status/Error</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${failedDonations}" var="oneDonation">

									<tr>
										<td style="border: 1px solid;"><c:out value="${oneDonation.transactionId}"></c:out></td>
										<td style="border: 1px solid;"><fmt:formatDate value="${oneDonation.donationDate}" pattern="dd-MMM-yyyy HH:mm:ss" /></td>
										<td style="border: 1px solid;"><c:out value="${oneDonation.amount}"></c:out></td>
										<td style="border: 1px solid;"><c:out value="${oneDonation.pgErrorMessage}"></c:out></td>
										
									</tr>
								</c:forEach>
							</tbody>
						</table>
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