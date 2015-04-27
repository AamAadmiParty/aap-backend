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

</head>
<body>
<div id="fb-root"></div>
<script src="https://apis.google.com/js/platform.js"></script>
<script>
  function onYtEvent(payload) {
    if (payload.eventType == 'subscribe') {
    	ga('send', 'event', 'youtube', 'subscribe',
				'${video.channelId}', 1);
    } else if (payload.eventType == 'unsubscribe') {
    	ga('send', 'event', 'youtube', 'unsubscribe',
				'${video.channelId}', 1);
    }
  }
</script>
<script>(function(d, s, id) {
  var js, fjs = d.getElementsByTagName(s)[0];
  if (d.getElementById(id)) return;
  js = d.createElement(s); js.id = id;
  js.src = "//connect.facebook.net/en_US/all.js#xfbml=1&amp;appId=1383280855238363";
  fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));</script>

	<jsp:include page="header.jsp" />

	<jsp:include page="topscroller.jsp" />


	<div class="contentarea">
		<!--contentarea-->

		<div class="readmorewrapper">
			<!--readmorewrapper-->
			
			<h1><c:out value="${video.title}"></c:out></h1>
			<br />
			<div class="formwrapper">
				<!--formwrapper-->
				<iframe width="610" height="425" src="http://www.youtube.com/embed/${video.youtubeVideoId}" frameborder="0" ></iframe>
				
				<br></br>
				<div class="g-ytsubscribe" data-channelid="UCYm-AJyEXXAWOrw_qp0XQDw" data-layout="default" data-count="default"></div>
				<div class="g-ytsubscribe" data-channel="${video.channelId}" data-layout="full" data-count="default" ></div>
				<br></br>
				<div class="fb-comments" data-href="http://my.aamaadmiparty.org/content/video/${video.id}" data-width="640" data-numposts="5" data-colorscheme="light"></div>
				
			</div>
			
		</div>
		<jsp:include page="rightside.jsp" />

	</div>




	<jsp:include page="footer.jsp" />

</body>
</html>