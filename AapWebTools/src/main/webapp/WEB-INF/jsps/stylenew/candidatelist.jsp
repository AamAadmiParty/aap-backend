<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Aam Aadmi Party Candidates for Loksabha Elections 2014</title>
<jsp:include page="includes.jsp" />

<meta name="description" content="Meet Aam Aadmi Party candidate for Loksabha election 2014" />
<meta name="og:image" property="og:image" content="https://s3.amazonaws.com/myaap/test/candidate/profile/leaders.jpg"/>

<c:forEach items="${candidates}" var="oneCandidate">
	<c:if test="${!empty oneCandidate.imageUrl }">
		<meta name="og:image" property="og:image" content="${oneCandidate.imageUrl}"/>
	</c:if>
</c:forEach>
<meta name="og:title"  property="og:title" content="Aam Aadmi party candidates for Loksabha Eletions 2014"/>
<meta name="og:url" property="og:url" content="http://my.aamaadmiparty.org/candidates.html"/>
<meta name="og:site_name" property="og:site_name" content="my.aamaadmiparty.org"/>
<meta name="og:type" property="og:type" content="blog"/>
<meta name="og:description" property="og:description" content="Meet Aam Aadmi Party candidates for loksabha elections 2014"/>


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
<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDz1Btzrgtb0YEtHFgjZLEGaw-ggjnWlrc&sensor=false&region=IN">
    </script>
<script type="text/javascript">
    ;
      function initialize() {
        var mapOptions = {
          center: new google.maps.LatLng(23.934102635197338, 78.310546875),
          zoom: 5
        };
        var map = new google.maps.Map(document.getElementById("map-canvas"), mapOptions);
        /*createMarker(map, <c:out value="${oneCandidate.lattitude}" />,<c:out value="${oneCandidate.longitude}" />, '<c:out value="${oneCandidate.name}" />', '<c:out value="${oneCandidate.content}" escapeXml="false" />');*/
        <c:forEach items="${candidates}" var="oneCandidate">
		
		createMarker(map, <c:out value="${oneCandidate.lattitude}" />,<c:out value="${oneCandidate.longitude}" />, '<c:out value="${oneCandidate.name}" />', '<c:out value="${oneCandidate.landingPageFullUrl}" />', '<c:out value="${oneCandidate.imageUrl32}" />');
    </c:forEach>
      }
      google.maps.event.addDomListener(window, 'load', initialize);

      function createMarker(map, lattitude, longitude,name,  pageUrl, iconUrl){
    	  var marker = new google.maps.Marker({
    		    position: new google.maps.LatLng(lattitude, longitude),
    		    map: map,
    		    title:name,
    		    icon :iconUrl
    		});
    	  var infowindow = new google.maps.InfoWindow({
    	      content: "<div><a href='"+pageUrl+"'>" + name +"</a></div>"
    	  });
    	  google.maps.event.addListener(marker, 'click', function() {
    		    infowindow.open(map,marker);
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
					<!-- 
					<ul>
						<li><a href="#map">Map</a></li>
						<li><a href="#list">List</a></li>
						
					</ul>
					 -->
					<div id="map" class="languagetab">
						<div id="map-canvas" style="width: 900px; height: 1100px; border: 1px solid;" />
					</div>

					<div id="list" class="languagetab">
						<table>
							<thead>
								<tr>
									<th style="border: 1px solid;">State</th>
									<th style="border: 1px solid;">Loksabha</th>
									<th style="border: 1px solid;">Name</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${candidates}" var="oneCandidate">
									<tr>
										<td style="border: 1px solid;"><a href="${oneCandidate.landingPageFullUrl}"><c:out value="${oneCandidate.stateName}" /></a></td>
										<td style="border: 1px solid;"><a href="${oneCandidate.landingPageFullUrl}"><c:out value="${oneCandidate.pcName}" /></a> </td>
										<td style="border: 1px solid;"><a href="${oneCandidate.landingPageFullUrl}"><c:out value="${oneCandidate.name}" /></a></td>

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
	<jsp:include page="addthis.jsp" />
</body>
</html>