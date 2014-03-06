<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Donate - Aam Aadmi Party</title>
<jsp:include page="includes.jsp" />

<script>
	$(function() {
		$('#customDiv').hide();
		$('#custom').click(function() {
			if ($('#custom').prop('checked')) {
				$('#customDiv').show();
			} else {
				$('#customDiv').hide();
			}

		});

		$("select#stateId").change(function() {
					$('#districtId').prop('disabled', true);
					$('#parliamentConstituencyId').prop('disabled',
							true);
					$('#assemblyConstituencyId').prop('disabled', true);
					
					$.ajax({type : "GET",url : "${pageContext.request.contextPath}/json/district/"+ $(this).val(),
									data : {
									}
								})
						.done(function(msg) {
									var options = '<option value="0">Select District</option>';
									for ( var i = 0; i < msg.length; i++) {
										options += '<option id="ldistrict'+msg[i].id +'" value="' + msg[i].id + '" title="'+msg[i].name+'">'
												+ msg[i].name
												+ '</option>';
									}
									$("select#districtId").html(
											options);
									$("select#assemblyConstituencyId")
											.html(
													'<option value="0">Select Assembly Constituency</option>');
									$('#districtId').prop(
											'disabled', false);
								}).fail(
								function(jqXHR, textStatus,
										errorThrown) {
									alert("error " + errorThrown);
								});
					$.ajax({
										type : "GET",
										url : "${pageContext.request.contextPath}/json/pc/"
												+ $(this).val(),
										data : {}
									})
							.done(
									function(msg) {
										var options = '<option value="0">Select Parliament Constituency</option>';
										for ( var i = 0; i < msg.length; i++) {
											options += '<option id="lpc'+msg[i].id +'" value="' + msg[i].id + '" title="'+msg[i].name+'">'
													+ msg[i].name
													+ '</option>';
										}
										$(
												"select#parliamentConstituencyId")
												.html(options);
										$('#parliamentConstituencyId')
												.prop('disabled', false);
									}).fail(
									function(jqXHR, textStatus,
											errorThrown) {
										alert("error " + errorThrown);
									});
					getDonations($("option:selected", this).attr("id"), $("option:selected", this).attr("title"), 'State');
					
			});
		function getDonations(lcid, locationname, locationType)
		{
			$("#donationHeader").html('Donation in '+locationType +" : "+ locationname);
			$.ajax({type : "GET",url : "${pageContext.request.contextPath}/donationsforlocation.html?lcid="+ lcid,
				data : {
				}
			}).done(function(msg) {
				$("#donationTable").html(msg);
			}).fail(
			function(jqXHR, textStatus,
					errorThrown) {
				alert("error " + errorThrown);
			});
		}
		$("select#districtId").change(function() {
				$('#assemblyConstituencyId').prop('disabled', true);
				$.ajax({
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
							for ( var i = 0; i < msg.length; i++) {
								options += '<option id="lac'+msg[i].id +'" value="' + msg[i].id + '" title="'+msg[i].name+'">'
										+ msg[i].name
										+ '</option>';
							}
							$(
									"select#assemblyConstituencyId")
									.html(options);
							$('#assemblyConstituencyId')
									.prop('disabled', false);
						}).fail(
						function(jqXHR, textStatus,
								errorThrown) {
							alert("error " + errorThrown);
						});
				getDonations($("option:selected", this).attr("id"), $("option:selected", this).attr("title"), 'District');
				//getDonations($("option:selected", this).attr("id"));
			});

		$("select#assemblyConstituencyId").change(function() {
			getDonations($("option:selected", this).attr("id"), $("option:selected", this).attr("title"), 'Assembly Constituency');
		});
		$("select#parliamentConstituencyId").change(function() {
			getDonations($("option:selected", this).attr("id"), $("option:selected", this).attr("title"), 'Parliament Constituency');
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

	$(function() {
		
		$('#globalDonationButton').click(function() {
		    window.location.href = 'https://donate.aamaadmiparty.org/';
		    return false;
		});

		$('#districtDonationButton').click(function() {
			forwardClick('districtId','District');
		});
		
		$('#stateDonationButton').click(function() {
			forwardClick('stateId','State');
		});

		$('#acDonationButton').click(function() {
			forwardClick('assemblyConstituencyId','Assembly Constituency');
		});

		$('#pcDonationButton').click(function() {
			forwardClick('parliamentConstituencyId','Parliament Constituency');
		});

		function forwardClick(selectId, locationType){
			var index = $('#'+selectId+' :selected').index();
			var lcid = $('#'+ selectId+' :selected').attr("id");
			if(index <= 0)
			{
				ga('send', 'event', 'button', 'click', 'donation-clickerror-' +locationType);
				alert('Please select a ' + locationType);
			} 
			else
			{
				ga('send', 'event', 'button', 'click', 'donation-' +locationType);
				window.location.href = 'https://donate.aamaadmiparty.org?cid=lcid='+lcid;		
			}
		    return false;
		}
		
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
		<!--contentarea-->

		<div class="form-leftarea">
			<!--form-leftarea-->
			<h1>Online Donations</h1>
			<div class="formwrapper">
				<!--formwrapper-->

				<div class="partymembertab">
					<!--partymembertab-->

					<div class="languagetab">
						<!--languagetab-->
						<jsp:include page="errors.jsp" />
						<div class="blockdiv">
							<input name="globalDonationButton" id="globalDonationButton" value="" type="button" class="donatebtnbig" />
						</div>


						<div class="blockdiv">
							<label>Custom Location</label> <input type="checkbox" id="custom" name="custom" class="textbox" title="Select if you want your donation to go a custom location" />
						</div>
						<br />
						<div id="customDiv">
							<div class="formsmallheading">Select the place where you want your donation to go*</div>

							<div class="blockdiv">
								<label>State</label> <select class="selectbox" id="stateId" name="stateId" title="Choose State where you want your donation to go">
									<option value="0" label="Select State">Select State</option>
									<c:forEach items="${states}" var="oneState">
										<option title="${oneState.name}" id="lstate${oneState.id}" value="${oneState.id}" label="${oneState.name}">${oneState.name}</option>
									</c:forEach>

								</select> <input id="stateDonationButton" name="stateDonationButton" type="button" class="donatebtnsmall" value="Donate to State" />
							</div>



							<div class="blockdiv">
								<label>District</label> <select disabled="disabled" class="selectbox" id="districtId" name="districtId" title="Choose District where you want your donation to go">
									<option value="0" label="Select District">Select District</option>
									<c:forEach items="${livingDistricts}" var="oneDistrict">
										<option title="${oneDistrict.name}" id="ldistrict${oneDistrict.id}" value="${oneDistrict.id}" label="${oneDistrict.name}">${oneDistrict.name}</option>
									</c:forEach>

								</select> <input id="districtDonationButton" name="districtDonationButton" type="button" class="donatebtnsmall" value="Donate to District" />
							</div>

							<div class="blockdiv">
								<label>Parliament Constituency</label> <select disabled="disabled" class="selectbox" id="parliamentConstituencyId" name="parliamentConstituencyId"
									title="Choose Parliament Constituency where you want your donation to go">
									<option value="0" label="Select Parliament Constituency" />
									<c:forEach items="${livingPcs}" var="onePc">
										<option id="lpc${onePc.id}" value="${onePc.id}" label="${onePc.name}">${onePc.name}</option>
									</c:forEach>

								</select> <input id="pcDonationButton" name="pcDonationButton" type="button" class="donatebtnsmall" value="Donate to PC" />

							</div>

							<div class="blockdiv">
								<label>Assembly Constituency</label> <select disabled="disabled" class="selectbox" id="assemblyConstituencyId" name="assemblyConstituencyId"
									title="Choose Assembly Constituency where you want your donation to go">
									<option value="0" label="Select Assembly Constituncy" />
									<c:forEach items="${livingAcs}" var="oneAc">
										<option id="lac${oneAc.id}" value="${oneAc.id}" label="${oneAc.name}" />
									</c:forEach>

								</select> <input id="acDonationButton" name="acDonationButton" type="button" class="donatebtnsmall" value="Donate to AC" />


							</div>
							<p>* This is an indication that you wish your donation to go this location, but party can decide where your donation will be used across India</p>
						</div>
						<br></br>
						<h2 id="donationHeader"></h2>
						<br></br>
						<div id="donationTable">
						<table border="0" cellpadding="0" cellspacing="0">
							<tbody>
								<tr>
									<th width="140">Donor Name</th>
									<th width="89">Date</th>
									<th width="89">Transaction Id</th>
									<th width="91">Amount</th>
								</tr>
							</tbody>
						</table>
						</div>
					</div>
					<!--languagetab-->
				</div>
				<!--partymembertab-->


			</div>
			<!--formwrapper-->
		</div>
		<!--form-leftarea-->
	</div>
	<!--contentarea-->


	<jsp:include page="footer.jsp" />

</body>
</html>





