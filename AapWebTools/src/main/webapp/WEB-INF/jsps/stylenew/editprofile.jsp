<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Swaraj Abhiyan, India</title>
<jsp:include page="includes.jsp" />

<script>
	$(function() {
		$("select#stateLivingId")
				.change(
						function() {
							$('#districtLivingId').prop('disabled', true);
							$('#parliamentConstituencyLivingId').prop(
									'disabled', true);
							$('#assemblyConstituencyLivingId').prop('disabled',
									true);
							$
									.ajax(
											{
												type : "GET",
												url : "${pageContext.request.contextPath}/json/district/"
														+ $(this).val(),
												data : {
													name : "John",
													location : "Boston"
												}
											})
									.done(
											function(msg) {
												var options = '<option value="0">Select District</option>';
												for (var i = 0; i < msg.length; i++) {
													options += '<option value="' + msg[i].id + '">'
															+ msg[i].name
															+ '</option>';
												}
												$("select#districtLivingId")
														.html(options);
												$(
														"select#assemblyConstituencyLivingId")
														.html(
																'<option value="0">Select Assembly Constituency</option>');
												$('#districtLivingId').prop(
														'disabled', false);
											}).fail(
											function(jqXHR, textStatus,
													errorThrown) {
												alert("error " + errorThrown);
											});
							$
									.ajax(
											{
												type : "GET",
												url : "${pageContext.request.contextPath}/json/pc/"
														+ $(this).val(),
												data : {}
											})
									.done(
											function(msg) {
												var options = '<option value="0">Select Parliament Constituency</option>';
												for (var i = 0; i < msg.length; i++) {
													options += '<option value="' + msg[i].id + '">'
															+ msg[i].name
															+ '</option>';
												}
												$(
														"select#parliamentConstituencyLivingId")
														.html(options);
												$(
														'#parliamentConstituencyLivingId')
														.prop('disabled', false);
											}).fail(
											function(jqXHR, textStatus,
													errorThrown) {
												alert("error " + errorThrown);
											});
						});
		$("select#districtLivingId")
				.change(
						function() {
							$('#assemblyConstituencyLivingId').prop('disabled',
									true);
							$
									.ajax(
											{
												type : "GET",
												url : "${pageContext.request.contextPath}/json/ac/"
														+ $(this).val(),
												data : {
													name : "John",
													location : "Boston"
												}
											})
									.done(
											function(msg) {
												var options = '<option value="0">Select Assembly Constituency</option>';
												for (var i = 0; i < msg.length; i++) {
													options += '<option value="' + msg[i].id + '">'
															+ msg[i].name
															+ '</option>';
												}
												$(
														"select#assemblyConstituencyLivingId")
														.html(options);
												$(
														'#assemblyConstituencyLivingId')
														.prop('disabled', false);
											}).fail(
											function(jqXHR, textStatus,
													errorThrown) {
												alert("error " + errorThrown);
											});
						});
		$("select#stateVotingId")
				.change(
						function() {
							$('#districtVotingId').prop('disabled', true);
							$('#parliamentConstituencyVotingId').prop(
									'disabled', true);
							$('#assemblyConstituencyVotingId').prop('disabled',
									true);
							$
									.ajax(
											{
												type : "GET",
												url : "${pageContext.request.contextPath}/json/district/"
														+ $(this).val(),
												data : {
													name : "John",
													location : "Boston"
												}
											})
									.done(
											function(msg) {
												var options = '<option value="0">Select District</option>';
												for (var i = 0; i < msg.length; i++) {
													options += '<option value="' + msg[i].id + '">'
															+ msg[i].name
															+ '</option>';
												}
												$("select#districtVotingId")
														.html(options);
												$(
														"select#assemblyConstituencyVotingId")
														.html(
																'<option value="0">Select Assembly Constituency</option>');
												$('#districtVotingId').prop(
														'disabled', false);
											}).fail(
											function(jqXHR, textStatus,
													errorThrown) {
												alert("error " + errorThrown);
											});
							$
									.ajax(
											{
												type : "GET",
												url : "${pageContext.request.contextPath}/json/pc/"
														+ $(this).val(),
												data : {}
											})
									.done(
											function(msg) {
												var options = '<option value="0">Select Parliament Constituency</option>';
												for (var i = 0; i < msg.length; i++) {
													options += '<option value="' + msg[i].id + '">'
															+ msg[i].name
															+ '</option>';
												}
												$(
														"select#parliamentConstituencyVotingId")
														.html(options);
												$(
														'#parliamentConstituencyVotingId')
														.prop('disabled', false);
											}).fail(
											function(jqXHR, textStatus,
													errorThrown) {
												alert("error " + errorThrown);
											});
						});
		$("select#districtVotingId")
				.change(
						function() {
							$('#assemblyConstituencyVotingId').prop('disabled',
									true);
							$
									.ajax(
											{
												type : "GET",
												url : "${pageContext.request.contextPath}/json/ac/"
														+ $(this).val(),
												data : {
													name : "John",
													location : "Boston"
												}
											})
									.done(
											function(msg) {
												var options = '<option value="0">Select Assembly Constituency</option>';
												for (var i = 0; i < msg.length; i++) {
													options += '<option value="' + msg[i].id + '">'
															+ msg[i].name
															+ '</option>';
												}
												$(
														"select#assemblyConstituencyVotingId")
														.html(options);
												$(
														'#assemblyConstituencyVotingId')
														.prop('disabled', false);
											}).fail(
											function(jqXHR, textStatus,
													errorThrown) {
												alert("error " + errorThrown);
											});
						});
		$("select#nriCountryId")
				.change(
						function() {
							$('#nriCountryRegionId').prop('disabled', true);
							$('#nriCountryRegionAreaId').prop('disabled', true);
							$
									.ajax(
											{
												type : "GET",
												url : "${pageContext.request.contextPath}/json/region/"
														+ $(this).val(),
												data : {
													name : "John",
													location : "Boston"
												}
											})
									.done(
											function(msg) {
												var options = '<option value="0">Select Region</option>';
												for (var i = 0; i < msg.length; i++) {
													options += '<option value="' + msg[i].id + '">'
															+ msg[i].name
															+ '</option>';
												}
												$("select#nriCountryRegionId")
														.html(options);
												$(
														"select#nriCountryRegionAreaId")
														.html(
																'<option value="0">Select Area</option>');
												$('#nriCountryRegionId').prop(
														'disabled', false);
											}).fail(
											function(jqXHR, textStatus,
													errorThrown) {
												alert("error " + errorThrown);
											});
						});

		$("select#nriCountryRegionId")
				.change(
						function() {
							$('#nriCountryRegionAreaId').prop('disabled', true);
							$
									.ajax(
											{
												type : "GET",
												url : "${pageContext.request.contextPath}/json/regionarea/"
														+ $(this).val(),
												data : {}
											})
									.done(
											function(msg) {
												var options = '<option value="0">Select Area</option>';
												for (var i = 0; i < msg.length; i++) {
													options += '<option value="' + msg[i].id + '">'
															+ msg[i].name
															+ '</option>';
												}
												$(
														"select#nriCountryRegionAreaId")
														.html(options);
												$('#nriCountryRegionAreaId')
														.prop('disabled', false);
											}).fail(
											function(jqXHR, textStatus,
													errorThrown) {
												alert("error " + errorThrown);
											});
						});
	});
	$(function() {
		$("#language-tabs").tabs();
	});
	$(function() {
		$("#dateOfBirth").datepicker({
			changeMonth : true,
			changeYear : true,
			yearRange : '-99Y:-18Y'
		});
		$("#dateOfBirth").datepicker("option", "dateFormat", "dd/mm/yy");
		$("#dateOfBirth").datepicker("option", "autoSize", "true");
	});
	$(document).ready(function() {
		if ('${loggedInUser.nri}' == 'true') {
			$('#indianLocationDiv').hide();
			$('#nriDiv').show();
		} else {
			$('#nriDiv').hide();
			$('#indianLocationDiv').show();
		}
		if ('${loggedInUser.volunteer}' == 'true') {
            $('#volunteerDiv').show();
        } else {
            $('#volunteerDiv').hide();
        }

		$('#nri1').click(function() {
			if ($('#nri1').prop('checked')) {
				$('#nriDiv').show();
				$('#indianLocationDiv').hide();

			} else {
				$('#nriDiv').hide();
				$('#indianLocationDiv').show();
			}

		});
		$('#volunteer1').click(function() {
            if ($('#volunteer1').prop('checked')) {
                $('#volunteerDiv').show();
            } else {
                $('#volunteerDiv').hide();
            }

        });
        
		$('#member1').click(function() {
			if ($('#member1').prop('checked')) {
				$('#language-tabs').show();
			} else {
				$('#language-tabs').hide();
			}
		});
	});
	$(function() {
		$(document).tooltip({
			// place tooltip on the right edge
			position : {
				my : "left top+1",
				at : "right top"
			},
			//tooltipClass: "tooltip",
			// a little tweaking of the position
			//offset: [-2, 10],

			// use the built-in fadeIn/fadeOut effect
			effect : "fade",

			// custom opacity setting
			opacity : 0.7

		});
	});
</script>
<style type="text/css">
.tooltip {
	background-color: #000;
	border: 1px solid #fff;
	padding: 10px 15px;
	width: 200px;
	display: none;
	color: #fff;
	text-align: left;
	font-size: 12px;
	/* outline radius for mozilla/firefox only */
	-moz-box-shadow: 0 0 10px #000;
	-webkit-box-shadow: 0 0 10px #000;
}
</style>
</head>
<body>

	<jsp:include page="header.jsp" />

	<jsp:include page="topscroller.jsp" />

	<div class="contentarea">
		<div class="form-leftarea">
			<!--form-leftarea-->
			<div class="divarticle">
				<h1>Be a part of the change. Volunteer for the Swaraj Abhiyan</h1>
				<h3>${message}</h3>
			</div>
			<br />
			<div class="formwrapper">
				<!--formwrapper-->
				<div class="editprofile">Edit Profile</div>
				<jsp:include page="errors.jsp" />
				<form:form method="post" commandName="user" modelAttribute="user">
					<div class="blockdiv">
						<label>Name</label>
						<form:input path="name" class="textbox" title="Please Enter Your name" />
					</div>

					<div class="blockdiv">
						<label>Voter ID</label>
						<form:input path="voterId" class="textbox" title="Please Enter Your Voter ID" />
					</div>

					<div class="blockdiv">
						<label>Gender</label>
						<form:select path="gender" title="Choose Your Gender">
							<form:option value="select"> Select</form:option>
							<form:option value="Male">Male</form:option>
							<form:option value="Female">Female</form:option>
						</form:select>
					</div>

					<div class="blockdiv">
						<label>Passport Number</label>
						<form:input path="passportNumber" class="textbox" title="Enter your passport number, compulsory for NRIs" />
					</div>

					<div class="blockdiv">
						<label>Mobile</label>
						<form:input path="mobileNumber" class="textbox" title="Enter your indian mobile number, 10 digits only" />
					</div>

					<div class="blockdiv">
						<label>Date of Birth(dd/mm/yyyy)</label>
						<form:input path="dateOfBirth" class="textbox" title="Your date of Birth, i.e. 18/09/1981 dd/mm/yyyy" />
					</div>

					<div class="blockdiv">
						<label>Father's Name</label>
						<form:input path="fatherName" class="textbox" title="Enter your father's name" />
					</div>

					<div class="blockdiv">
						<label>Mother's Name</label>
						<form:input path="motherName" class="textbox" title="Enter Your mother's name" />
					</div>


					<div class="blockdiv">
						<label>I am NRI</label>
						<form:checkbox path="nri" class="textbox" title="Select if you are currently living outside India" />
					</div>
					<div id="nriDiv">
						<div class="editprofile">NRI Living Location</div>
						<div class="blockdiv">
							<label>Country </label>
							<form:select path="nriCountryId" title="Choose Your Country where you living currently(outside india)">
								<form:option value="0" label="Select Country" />
								<form:options items="${nriCountries}" itemValue="id" itemLabel="name" />
							</form:select>
						</div>

						<div class="blockdiv">
							<label>Region/State</label>
							<form:select path="nriCountryRegionId"
								disabled="${empty loggedInUser.nriCountryRegionId or loggedInUser.nriCountryRegionId le 0 }"
								title="Select your country region">
								<form:option value="0" label="Select Region" />
								<form:options items="${nriCountryRegions}" itemValue="id" itemLabel="name" />
							</form:select>
						</div>

						<div class="blockdiv">
							<label>Area/City</label>
							<form:select path="nriCountryRegionAreaId"
								disabled="${empty loggedInUser.nriCountryRegionAreaId or loggedInUser.nriCountryRegionAreaId le 0 }"
								title="Select your country region area">
								<form:option value="0" label="Select Area" />
								<form:options items="${nriCountryRegionAreas}" itemValue="id" itemLabel="name" />
							</form:select>
						</div>

						<div class="blockdiv">
							<label>Mobile</label>
							<form:input path="nriMobileNumber" class="textbox" title="Enter you outside india mobile number" />
						</div>
					</div>
					<div id="indianLocationDiv">
						<div class="editprofile">Select the place where you currently living in India</div>
						<div class="blockdiv">
							<label>State</label>
							<form:select path="stateLivingId" title="Choose State where you live currently">
								<form:option value="0" label="Select State" />
								<form:options items="${states}" itemValue="id" itemLabel="name" />
							</form:select>
						</div>
						<div class="blockdiv">
							<label>District</label>
							<form:select path="districtLivingId"
								disabled="${empty loggedInUser.districtLivingId or loggedInUser.districtLivingId le 0 }"
								title="Choose District where you live currently">
								<form:option value="0" label="Select District" />
								<form:options items="${livingDistricts}" itemValue="id" itemLabel="name" />
							</form:select>
						</div>
						<div class="blockdiv">
							<label>Parliament Constituency</label>
							<form:select path="parliamentConstituencyLivingId"
								disabled="${empty loggedInUser.parliamentConstituencyLivingId or loggedInUser.parliamentConstituencyLivingId le 0 }"
								title="Choose Parliament Constituency where you live currently">
								<form:option value="0" label="Select Parliament Constituency" />
								<form:options items="${livingPcs}" itemValue="id" itemLabel="name" />
							</form:select>
						</div>
						<div class="blockdiv">
							<label>Assembly Constituency</label>
							<form:select path="assemblyConstituencyLivingId"
								disabled="${empty loggedInUser.assemblyConstituencyLivingId or loggedInUser.assemblyConstituencyLivingId le 0 }"
								title="Choose Assembly Constituency where you live currently">
								<form:option value="0" label="Select Assembly Constituency" />
								<form:options items="${livingAcs}" itemValue="id" itemLabel="name" />
							</form:select>
						</div>
					</div>
					<div class="editprofile">Select the place where you registered as voter in India</div>
					<div class="blockdiv">
						<label>State</label>
						<form:select path="stateVotingId" title="Choose State where you registered as Voter or your indian address state">
							<form:option value="0" label="Select State" />
							<form:options items="${states}" itemValue="id" itemLabel="name" />
						</form:select>
					</div>
					<div class="blockdiv">
						<label>District</label>
						<form:select path="districtVotingId"
							disabled="${empty loggedInUser.districtVotingId or loggedInUser.districtVotingId le 0 }"
							title="Choose District where you registered as Voter or your indian address District">
							<form:option value="0" label="Select District" />
							<form:options items="${votingDistricts}" itemValue="id" itemLabel="name" />
						</form:select>
					</div>
					<div class="blockdiv">
						<label>Parliament Constituency</label>
						<form:select path="parliamentConstituencyVotingId"
							disabled="${empty loggedInUser.parliamentConstituencyVotingId or loggedInUser.parliamentConstituencyVotingId le 0 }"
							title="Choose Parliament Constituency where you registered as Voter or your indian address Parliament Constituency">
							<form:option value="0" label="Select Parliament Constituency" />
							<form:options items="${votingPcs}" itemValue="id" itemLabel="name" />
						</form:select>
					</div>
					<div class="blockdiv">
						<label>Assembly Constituency</label>
						<form:select path="assemblyConstituencyVotingId"
							disabled="${empty loggedInUser.assemblyConstituencyVotingId or loggedInUser.assemblyConstituencyVotingId le 0 }"
							title="Choose Assembly Constituency where you registered as Voter or your indian address Assembly Constituency">
							<form:option value="0" label="Select Assembly Constituency" />
							<form:options items="${votingAcs}" itemValue="id" itemLabel="name" />
						</form:select>
					</div>
					<!-- 
					<c:if test="${loggedInUser.member}">
						<div class="blockdiv">
							<label>Membership Number</label> <label><c:out value="${loggedInUser.membershipNumber}" /> <c:if test="${!empty loggedInUser.legacyMembershipNumber}">
							/ <c:out value="${loggedInUser.legacyMembershipNumber}" />
								</c:if> </label>
						</div>
						<div class="blockdiv">
							<label>Membership Status</label> <label><c:out value="${loggedInUser.membershipStatus}" /> </label>
						</div>
					</c:if>
					-->
					<c:if test="${!loggedInUser.member}">
						<!-- 
						<div class="partymemberdiv">
							<form:checkbox path="member" />
							I want to be Party Member
						</div>
						 -->
						<!-- 
						<div id="language-tabs">
							<ul>
								<li><a href="#english">English</a></li>
								<li><a href="#hindi">Hindi</a></li>
							</ul>
							<div id="english">
								<p>
									<ul>
										<li><c:out value="STEP – 1 –	Complete Your Profile above." /></li>
										<li><c:out value="STEP – 2 –	You can pay your membership fees of Rs 10 in two ways." /></li>
										<li><c:out value="STEP - 2a -	Submit the form and pay Rs. 10. online through Credit/Debit Card Or Internet Banking As Membership Fees and become a member.The email confirmation that you receive on making the payment in option is your confirmation of membership.." /></li>
										<li><b><c:out value="or" /></b></li>
										<li><c:out value="STEP - 2a -	Purchase a Postal Order (PO) of Rs.10/- from your local Post Office (if a single PO is not available you can purchase two Rs.5/- POs or any other combination). In 'PAY TO' write 'Aam Aadmi Party'. Please leave 'AT THE POST OFFICE AT' column blank. You can keep the counterfoil with yourself as acknowledgement." /></li>
										<li><c:out value="STEP - 2b -	Submit this printout of the membership form along with a postal order of Rs.10/- at the party office of your city OR send the printout and postal order to our national office at -Ground floor, A-119, Kaushambi, Ghaziabad, UP, 201010." /></li>
									</ul>
								</p>
							</div>
							<div id="hindi">
								<p>
									मेम्बर बनने के लिए कृपया नीचे दी गयी चार चरणों की प्रक्रिया पूर्ण करें -
									<ul>
										<li><c:out value="चरण - 1 -	नीचे दिए गए फॉर्म में अपना विवरण भरें ।" /></li>
										<li><c:out value="चरण - 2 -	आप दो तरह से 10 रुपये की अपनी सदस्यता शुल्क का भुगतान कर सकते हैं." /></li>
										<li><c:out value="चरण - 2a -	फॉर्म को भरकर क्रेडिट कार्ड / डेबिट कार्ड या नेट बैंकिंग के माध्यम से भी ऑनलाइन सदस्यता शुल्क (Membership fee) रु . 10/- का भुगतान कर सकते हैं | पुष्टि ईमेल जो आपको प्राप्त होगी वही आपकी सदस्यता की पुष्टि है |" /></li>
										<li><b><c:out value="या" /></b></li>
										<li><c:out value="चरण - 2a -	अपने स्थानीय पोस्ट ऑफिस से दस रुपये का पोस्टल आर्डर लेकर (अगर दस रुपये का एक पोस्टल आर्डर न मिले तो आप पांच रुपये के दो या कोई अन्य मोल). अदा करें में 'आम आदमी पार्टी' लिखें. 'पोस्ट ऑफिस पर' की जगह खाली छोड़ दें. आधी जमा पर्ची अपने पास पावती के तौर पर रख लें." /></li>
										<li><c:out value="चरण - 2b -	इस प्रिंटआउट को रु . 10/- के पोस्टल आर्डर के साथ अपने शहर के पार्टी दफ्तर में जमा करें या पार्टी के केंद्रीय कार्यालय को इस पते पर डाक द्वारा भेजें - Ground floor, A-119, Kaushambi, Ghaziabad, UP, 201010." /></li>
									</ul>
								</p>
							</div>

						</div>
						-->
					</c:if>

					<!--partymembertab-->
					<!--partymembertab-->
					<div class="blockdiv">
                        <label>I Want to Volunteer</label>
                        <form:checkbox path="volunteer" class="textbox" title="Select if you are currently living outside India" />
                    </div>
					
                        
                    <div id="volunteerDiv">
                    <div class="blockdiv">
                        <label>Have you volunteered with any political/public interest organization in the past?</label>
                        <form:checkbox path="volunteerDto.pastVolunteer" />
                    </div>
                    <div class="blockdiv">
                        <label>If yes, Which organization</label>
                        <form:input path="volunteerDto.pastOrganisation" class="textbox" title="If yes, Which organization" />
                    </div>
                    <div class="blockdiv">
                        <label>Do you know someone associated with Swaraj Abhiyan?</label>
                        <form:checkbox path="volunteerDto.knowExistingMember" />
                    </div>
                    <div class="blockdiv">
                        <label>If yes, Provide Name & Mobile No.</label>
                        <form:input path="volunteerDto.existingMember" class="textbox" title="If yes, Provide Name & Mobile No." />
                    </div>
                    
                    <c:set var="count" scope="session" value="0"/>

                    <c:forEach items="${interestGroups}" var="oneInterestGroup">
                        <div class="editprofile">${oneInterestGroup.description}</div>
                        <div class="blockdiv">
                        <% int colCount = 0; %>
                        <table width="100%" >
                                    <tbody>
                            <c:forEach items="${oneInterestGroup.interestDtos}" varStatus="status" var="oneInterest">
                                            <% 
                                            if(colCount % 3 == 0){
                                                out.println("<tr>");
                                            }
                                            %>
                                            <td width="33%">
                                            <form:hidden path="userInterestDtos[${count}].description" />
                                            <form:hidden path="userInterestDtos[${count}].id" />
                                            <form:checkbox path="userInterestDtos[${count}].selected" />${oneInterest.description}       
                                        <c:set var="count" scope="session" value="${count + 1}"/>
                                            </td>
                                            <% colCount++;
                                            if(colCount % 3 == 0){
                                                out.println("</tr>");
                                            }
                                            %>
                                        
                            </c:forEach>
                            <% colCount++;
                                 if(colCount % 3 != 0){
                                     out.println("</tr>");
                                 }
                             %>
                                    </tbody>
                                </table>
                        </div>
                    </c:forEach>
                    <div class="blockdiv">
                        <label>Education</label>
                        <form:input path="volunteerDto.education" class="textbox" title="Please Enter Your maximum Education level" />
                    </div>
                    <div class="blockdiv">
                        <label>Professional Background</label>
                        <form:input path="volunteerDto.professionalBackground" class="textbox" title="Please Enter professional Background" />
                    </div>
                    <div class="blockdiv">
                        <label>Domain Expertise</label>
                        <form:input path="volunteerDto.domainExpertise" class="textbox" title="i.e. IT, Law etc" />
                    </div>
                    <div class="blockdiv">
                        <label>Offences</label>
                        <form:input path="volunteerDto.offences" class="textbox" title="Any offeces or court cases(pending + Past)" />
                    </div>
                    <div class="blockdiv">
                        <label>Emergency Contact Name</label>
                        <form:input path="volunteerDto.emergencyContactName" class="textbox" title="A person name who should be contacted in case you are in some accident" />
                    </div>
                    <div class="blockdiv">
                        <label>Emergency Contact Relation</label>
                        <form:input path="volunteerDto.emergencyContactRelation" class="textbox" title="What is the relation with above person" />
                    </div>
                    <div class="blockdiv">
                        <label>Emergency Contact Number</label>
                        <form:input path="volunteerDto.emergencyContactNo" class="textbox" title="Emergency Contact No" />
                    </div>
                    
                    </div>
                    
                    <input name="" type="submit" class="button" value="Save Profile" />
                    
				</form:form>
			</div>
			<!--formwrapper-->
		</div>
		<!--form-leftarea-->

	</div>
	<!--contentarea-->



	<jsp:include page="footer.jsp" />

</body>
</html>