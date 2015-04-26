<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${poll.contentWithoutHtml} - Tell Us what you think, Aam Aadmi Party, India</title>


<!--Facebook Tags -->
<meta name="description"
	content="Tell us what you think on '${poll.contentWithoutHtml}'" />
	<meta name="og:image" property="og:image" content="http://s3-eu-west-1.amazonaws.com/nusdigital/image/images/221/original/tell%20us%20what%20you%20think%20button.png" />
	<meta name="og:title" property="og:title" content="Swaraj Abhiyan Polls, raise your voice" />
	<meta name="og:url" property="og:url" content="${candidate.landingPageFullUrl}" />
	<meta name="og:site_name" property="og:site_name" content="swarajabhiyan.org" />
	<meta name="og:type" property="og:type" content="blog" />
	<meta name="og:description" property="og:description"
	content="Tell us what you think on '${poll.contentWithoutHtml}'" />


<jsp:include page="includes.jsp" />
<c:if test="${userAlreadyPolled}">
<script type="text/javascript" src="https://www.google.com/jsapi"></script>
    <script type="text/javascript">
      google.load("visualization", "1", {packages:["corechart"]});
      google.setOnLoadCallback(drawChart);
      function drawChart() {
        var data = google.visualization.arrayToDataTable(${chartData});

        var options = {
          title: '${poll.contentWithoutHtml}'
        };

        var chart = new google.visualization.PieChart(document.getElementById('piechart_3d'));
        chart.draw(data, options);
      }
    </script>
</c:if>
<style>
/*polls css starts --> somnath nabajja*/

.for-polls .editprofile font{
    font-size:18px;
    text-align:left;
    display:block;
    padding:0 0 20px 15px
}
.for-polls .formwrapper,
.for-polls form{
    padding:0
;
    border: 0;
}
.for-polls .form-row{
background: -moz-linear-gradient(top, rgba(242,242,242,0.65) 0%, rgba(252,252,252,1) 100%); /* FF3.6+ */
background: -webkit-gradient(linear, left top, left bottom, color-stop(0%,rgba(242,242,242,0.65)), color-stop(100%,rgba(252,252,252,1))); /* Chrome,Safari4+ */
background: -webkit-linear-gradient(top, rgba(242,242,242,0.65) 0%,rgba(252,252,252,1) 100%); /* Chrome10+,Safari5.1+ */
background: -o-linear-gradient(top, rgba(242,242,242,0.65) 0%,rgba(252,252,252,1) 100%); /* Opera 11.10+ */
background: -ms-linear-gradient(top, rgba(242,242,242,0.65) 0%,rgba(252,252,252,1) 100%); /* IE10+ */
background: linear-gradient(to bottom, rgba(242,242,242,0.65) 0%,rgba(252,252,252,1) 100%); /* W3C */
filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#a6f2f2f2', endColorstr='#fcfcfc',GradientType=0 ); /* IE6-9 */
padding: 0 0 0 34px;
border-bottom: 1px solid #DFDDDD;
overflow:auto;
position: relative;
}
.for-polls .form-row:hover{
	background: #efefef;
}
.for-polls .form-row input{
    float:left;
    margin: 11px 8px 0 0;
    position: absolute;
    left: 15px;
}
.for-polls .form-row label{
    font-size:13px;
    width: 100%;
    padding: 10px 0;
}
.for-polls .poll-submit{
text-align: center;
display: block;
overflow: hidden;
padding: 10px;
}
.for-polls .poll-submit input{
    float: none;
    margin: 0;
}
.for-polls .poll-submit input.disabled{
	-moz-opacity: 0.50;
opacity: 0.50;
-ms-filter:"progid:DXImageTransform.Microsoft.Alpha"(Opacity=50);
filter: progid:DXImageTransform.Microsoft.Alpha(opacity=50);
filter:alpha(opacity=50);
margin-left: 10px;
}
.for-polls .poll-submit span i{
	color:#f00;
}
.for-polls .poll-submit span a{
	color:#86bf59;
	font-weight: bold;
}
.for-polls h4{
    margin:0;
    font-size:25px;
    color:#86bf59;
    text-transform:uppercase
}
/*polls css ends --> somnath nabajja*/
</style>
</head>
<body>
	<div id="fb-root"></div>
	<script>
		(function(d, s, id) {
			var js, fjs = d.getElementsByTagName(s)[0];
			if (d.getElementById(id))
				return;
			js = d.createElement(s);
			js.id = id;
			js.src = "//connect.facebook.net/en_US/all.js#xfbml=1&appId=1389845241275842";
			fjs.parentNode.insertBefore(js, fjs);
		}(document, 'script', 'facebook-jssdk'));
	</script>

	<jsp:include page="header.jsp" />

	<jsp:include page="topscroller.jsp" />

	<div class="contentarea for-polls">
        <!--contentarea-->
        <div class="loginwithholder">
            <!--loginwithholder-->
            <div class="loginwithinnerholder">
                <!--loginwithinnerholder-->
                <div class="editprofile">
                    <font face="Arial, Verdana" size="2">${poll.content}</font>
                </div>
                <div class="formwrapper">
                    <!--formwrapper-->
                    <c:if test="${!userAlreadyPolled}">
                    <form method="post">
                        <input type="hidden" name="question" id="${poll.id}" value="${poll.id}" />

                    	<c:forEach items="${poll.answers}" var="oneAnswer">
                        <div  class="form-row">
                            <input type="radio" name="answer" id="${oneAnswer.id}" value="${oneAnswer.id}" />
                            <label for="${oneAnswer.id}">${oneAnswer.content}</label>
                        </div>

						</c:forEach>
						
                        <div class="poll-submit">
                        	<c:if test="${empty loggedInUser}">
		                        <span><i>*</i> please <a href="/login?aap_redirect_url=${currentUrl}">Sign In</a> to vote</span>
		                        <input name="poll" type="submit" class="button disabled " value="Cast Your Vote" disabled="disabled" />
								</c:if>
								<c:if test="${!empty loggedInUser}">
								<input name="poll" type="submit" class="button" value="Cast Your Vote" />
								</c:if>
								
                        </div>
                    </form>
                    </c:if>
                    <c:if test="${userAlreadyPolled}">
					<h3>You have already polled</h3>
						<h4><c:out value="${userAnswer}" escapeXml="false"></c:out> </h4>
						<div id="piechart_3d" style="width: 900px; height: 500px;"></div>
					</c:if>
                </div>
                
                <div class="fb-comments" data-href="http://my.aamaadmiparty.org${currentUrl}" data-width="900" data-numposts="20" data-colorscheme="light"></div>
                <!--loginwithinnerholder-->
            </div>
            <!--loginwithholder-->
        </div>
        <!--facebookWidget-->
    </div>


	<jsp:include page="footer.jsp" />

</body>
</html>