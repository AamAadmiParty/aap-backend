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
<div id='invtrflfloatbtn'></div>
<script>
var invite_referrals = window.invite_referrals || {}; (function() {
        invite_referrals.auth = { bid_e : 'D22655DE90FB47CC49FB29B55B7C6E4A', bid : '659', t : '420', email : '${donation.donorEmail}',userParams : {'fname': '${donation.donorName}'}, userCustomParams :{'shareImg':'http://my.aamaadmiparty.org${contextPath}/dc/images/<c:out value='${ImageSource}' />'}};
var script = document.createElement('script');script.async = true;
script.src = (document.location.protocol == 'https:' ? "//d11yp7khhhspcr.cloudfront.net" : "//cdn.invitereferrals.com") + '/js/invite-referrals-1.0.js';
var entry = document.getElementsByTagName('script')[0];entry.parentNode.insertBefore(script, entry); })();

function invitereferrals_113(){
    var params = { bid: 659, cid: 113 };
    invite_referrals.widget.inlineBtn(params);
}
</script>

	<jsp:include page="header.jsp" />

	<jsp:include page="topscroller.jsp" />



	<div class="contentarea">
		<!--contentarea-->
		<div class="loginwithholder">
			<!--loginwithholder-->
			<div class="loginwithinnerholder">
				<img src="${contextPath}/dc/images/<c:out value='${ImageSource}' />"
								style="max-width: 800px; max-height: 800px;" />
								<br></br>
<a id="shareonfb" onclick="invitereferrals_113()" href="#"><img src="https://s3.amazonaws.com/myaap/images/facebookshare.png" />

			</div>
			<!--loginwithinnerholder-->
		</div>
		<!--loginwithholder-->
	</div>
	<!--contentarea-->

	<jsp:include page="footer.jsp" />

</body>
</html>