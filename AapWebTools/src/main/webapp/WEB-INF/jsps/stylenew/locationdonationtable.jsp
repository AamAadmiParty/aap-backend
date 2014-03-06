<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:if test="${empty donationCampaignInfo}">
	<table border="0" cellpadding="0" cellspacing="0">
		<tbody>
			<tr>
				<th width="200">Total Transactions</th>
				<td width="200">0</td>
			</tr>
			<tr>
				<th>Total Amount</th>
				<td>0</td>
			</tr>
		</tbody>
	</table>
	<table border="0" cellpadding="0" cellspacing="0">
		<tbody>
			<tr>
				<th width="140">Donor Name</th>
				<th width="89">Date</th>
				<th width="89">Transaction Id</th>
				<th width="91">Amount</th>
			</tr>
			<tr>
				<td colspan="4">No Donation Records</td>
			</tr>
		</tbody>
	</table>
</c:if>
<c:if test="${!empty donationCampaignInfo}">

	<table border="0" cellpadding="0" cellspacing="0">
		<tbody>
			<tr>
				<th width="200">Total Transactions</th>
				<td width="200">${donationCampaignInfo.ttxn}</td>
			</tr>
			<tr>
				<th>Total Amount</th>
				<td>${donationCampaignInfo.tamt}</td>
			</tr>
		</tbody>
	</table>
	<table border="0" cellpadding="0" cellspacing="0">
		<tbody>
			<tr>
				<th width="140">Donor Name</th>
				<th width="89">Date</th>
				<th width="89">Transaction Id</th>
				<th width="91">Amount</th>
			</tr>
			<c:forEach var="oneDonation" items="${donationCampaignInfo.dns}">
				<tr>
					<td><c:out value="${oneDonation.name}" /></td>
					<td><fmt:formatDate value="${oneDonation.dt}" pattern="dd-MMM-yyyy HH:mm:ss" /></td>
					<td><c:out value="${oneDonation.did}" /></td>
					<td><c:out value="${oneDonation.amt}" /></td>
				</tr>
			</c:forEach>

		</tbody>
	</table>
</c:if>
