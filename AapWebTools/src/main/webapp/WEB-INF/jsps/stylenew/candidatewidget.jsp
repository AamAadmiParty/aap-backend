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

		$('#globalDonationButtonOnRight').click(
				function() {
					ga('send', 'event', 'donation', 'candidatewidget',
							'${candidate.name}', 1);
					window.location.href = '${candidate.donationPageFullUrl}';
					return false;
				});
		$('#candidate1').click(
				function() {
					ga('send', 'event', 'candidate', 'candidatewidget',
							'${candidate.name}', 1);
					return true;
				});
		$('#candidate2').click(
				function() {
					ga('send', 'event', 'candidate', 'candidatewidget',
							'${candidate.name}', 1);
					return true;
				});

	});
</script>
</head>
<body>
	<div class="rhsarea">
		<!--rhsarea-->
		<c:if test="${!empty candidate}">
			<div class="aap-performance">
				*You can donate for below candidate by clicking here and can show your support.
				<div class="blockdiv">
					<input name="globalDonationButtonOnRight" id="globalDonationButtonOnRight" value="" type="button" class="donatebtnbig" />
				</div>
				<a href="${candidate.landingPageFullUrl}" id="candidate1"> <img src="<c:out value='${candidate.imageUrl}'/>" style="width: 280px; min-width: 280px;" border="0" />
				</a>
				<div id="success" class="languagetab">
					<table border="0" cellpadding="0" cellspacing="0">
						<tbody>
							<tr>
								<th width="200">Name</th>
								<td width="200"><c:out value='${candidate.name}' /></td>
							</tr>
							<tr>
								<th width="200">Loksabha</th>
								<td width="200"><c:out value='${candidate.pcName}' /> - <c:out value='${candidate.stateName}' /></td>
							</tr>
							<tr>
								<th width="200">Total Money Required</th>
								<td width="200">Rs 40,00,000</td>
							</tr>
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
					<a href="${candidate.landingPageFullUrl}" id="candidate2">Read more .....</a>
				</div>
			</div>
		</c:if>

		<div class="clear"></div>
	</div>
	<!--rhsarea-->
</body>