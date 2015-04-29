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
		$("#tabs").tabs();
	});
</script>

<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<style type="text/css">
html {
	height: 100%
}

body {
	height: 100%;
	margin: 0;
	padding: 0
}

#map-canvas {
	height: 100%
}
</style>
<script type="text/javascript"
	src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDz1Btzrgtb0YEtHFgjZLEGaw-ggjnWlrc&sensor=false&region=IN">
	
</script>
<script type="text/javascript">
	;
	function initialize() {
		var mapOptions = {
			center : new google.maps.LatLng(23.934102635197338, 78.310546875),
			zoom : 2
		};
		var map = new google.maps.Map(document.getElementById("map-canvas"),
				mapOptions);
		<c:forEach items="${events}" var="oneEvent">
		createMarker(
				map,
				<c:out value="${oneEvent.lattitude}" />,
				<c:out value="${oneEvent.longitude}" />,
				'<c:out value="${oneEvent.title}" />',
				'<fmt:formatDate value="${oneEvent.startDate}" pattern="dd-MMM-yyyy HH:mm" />',
				'<c:out value="${oneEvent.address}" escapeXml="false" />');
		</c:forEach>
	}
	google.maps.event.addDomListener(window, 'load', initialize);

	function createMarker(map, lattitude, longitude, title, startTime, address) {
		var marker = new google.maps.Marker({
			position : new google.maps.LatLng(lattitude, longitude),
			map : map,
			title : title
		});
		var infowindow = new google.maps.InfoWindow({
			content : "<div style='height:150px;'>" + title + "<br>Address : "
					+ address + "<br><br> Start on : " + startTime + "</div>"
		});
		google.maps.event.addListener(marker, 'click', function() {
			infowindow.open(map, marker);
		});
	}
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
				<!--loginwithinnerholder-->
				<div id="tabs">
					<ul>
						<li><a href="#map">Map</a></li>
						<li><a href="#listevent">List</a></li>
					</ul>
					<div id="map" class="languagetab">
						<div id="map-canvas" style="width: 900px; height: 600px; border: 1px solid;"></div>
					</div>
					<div id="listevent" class="languagetab">
						<div style="width: 900px; height: 600px; border: 1px solid;">

							<table>
								<thead>
									<tr>
										<th style="border: 1px solid;">Date</th>
										<th style="border: 1px solid;">Venue</th>
										<th style="border: 1px solid;">Title</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${events}" var="oneEvent">
										<tr>
											<td style="border: 1px solid;"><fmt:formatDate value="${oneEvent.startDate}"
													pattern="dd-MMM-yyyy HH:mm a" /></td>
											<td style="border: 1px solid;"><c:out value="${oneEvent.address}"></c:out></td>
											<td style="border: 1px solid;"><c:out value="${oneEvent.title}"></c:out></td>

										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
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