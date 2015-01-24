<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${PageTitle}</title>
<jsp:include page="includes.jsp" />

<c:if test="${electionId eq 1 }">
<meta name="description" content="Meet Aam Aadmi Party candidates for loksabha elections 2014" />
<meta name="og:title" property="og:title" content="Aam Aadmi party candidates for Loksabha Eletions 2014" />
<meta name="og:description" property="og:description" content="Meet Aam Aadmi Party candidates for loksabha elections 2014" />
</c:if>
<c:if test="${electionId eq 2 }">
<meta name="description" content="Meet Aam Aadmi Party candidates for Delhi Vidhansbah elections 2015" />
<meta name="og:title" property="og:title" content="Aam Aadmi party candidates for Delhi Vidhansabha Eletions 2015" />
<meta name="og:description" property="og:description" content="Aam Aadmi party candidates for Delhi Vidhansabha Eletions 2015" />

</c:if>
<meta name="og:image" property="og:image" content="https://s3.amazonaws.com/myaap/test/candidate/profile/leaders.jpg" />

<c:forEach items="${candidates}" var="oneCandidate">
	<c:if test="${!empty oneCandidate.imageUrl }">
		<meta name="og:image" property="og:image" content="${oneCandidate.imageUrl}" />
	</c:if>
</c:forEach>
<meta name="og:url" property="og:url" content="http://my.aamaadmiparty.org/candidate/election/${electionId}.html" />
<meta name="og:site_name" property="og:site_name" content="my.aamaadmiparty.org" />
<meta name="og:type" property="og:type" content="blog" />


<link rel="stylesheet" type="text/css" href="<c:out value='${staticDirectory}'/>/assets/css/style.css" />
<link rel="stylesheet" type="text/css" href="<c:out value='${staticDirectory}'/>/assets/css/style2.css" />

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

</head>
<body>

	<jsp:include page="header.jsp" />
	<%--
	<jsp:include page="topscroller.jsp" />
 --%>


	<div class="contentarea">
		<!--contentarea-->
		<div class="loginwithholder">
		<!--loginwithholder-->
            <div class="loginwithinnerholder1">
                <div class="body_cnt">
                    <div style="color: #FF0000">
                    </div>
                    <div id="container">
                        <!-- Template: static/hom.tpl.html Start 21/01/2015 01:18:46 -->
                        <div id="user_getCMSuser">
                            <!-- Template: static/homlist.tpl.html Start 21/01/2015 01:18:46 -->
                            <div class="clear"></div>
                            <div class="res_found" id="prnt_no_of_result" style="display: none; padding-top: 0px;">
                                <span class="match_profile">We found <span id="no_of_result">435</span> matching profiles
                                </span>
                            </div>
                            <ul class="leaders_list">
                                <div class="clear"></div>
                                <c:forEach items="${candidates}" var="oneCandidate">
                                <li class="">
                                        <div class="ldr_img" onclick="details('a-g-syed-mohindeen', 'a g syed mohindeen', 'en');">
                                        <div class="flow_hidden">
                                        <c:if test="${empty oneCandidate.imageUrl }">
			                                <img src='http://kanpuria.com/wp-content/uploads/2013/12/AAP_Kanpur.jpg' style="max-width: 300px; max-height: 200px;" />
			                            </c:if> <c:if test="${!empty oneCandidate.imageUrl }">
			                                <img src='${oneCandidate.imageUrl}' style="max-width: 300px; max-height: 200px;"/>
			                            </c:if>
                                        </div>
                                        <!--<div class="flow_hidden"><img src="http://candidates.aamaadmiparty.org/image/thumb/profile/_templates/images/no-photo.png" alt="" /></div>-->
                                        <div class="mask_theme"></div>
                                    </div>
                                    <div class="ldr_dtl">
                                        <a class="name" href="${oneCandidate.landingPageFullUrl}" >${oneCandidate.name}</a> 
                                        <a class="loc" href="${oneCandidate.landingPageFullUrl}" >Donation : Rs <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2"  value="${oneCandidate.totalAmount}" /></a> 
                                        <a class="consticy" href="${oneCandidate.landingPageFullUrl}" ><c:out value="${oneCandidate.pcName}" /><c:out value="${oneCandidate.acName}" /></a>
                                        <hr>
                                            <div>
                                                <p><c:out value="${oneCandidate.contentSummary}" escapeXml="true"/></p>
                                            </div>
                                            <a class="fltrht detail" href="${oneCandidate.landingPageFullUrl}" target="_blank">Details...</a>
                                            <div class="clear"></div>
                                    </div>
                                    </li>
		                        </c:forEach>
                            </ul>
                            <div class="clear"></div>
                            <div class="srch_form_bg_parnt" style="padding: 2px 0px;">
                                <div class="dont_bg fltlft" style="margin-bottom: 0px;">
                                    <a href="https://donate.aamaadmiparty.org" target="_blank" class="fltlft donate-n-sp" id="donate-n-sp">Donate
                                        &amp; Support</a>
                                    <div class="clear"></div>

                                </div>
                                <!-- 
                                <div class="fltrht pagination_bg mob_page hm_fot_page">
                                    <div class="pagination_box">
                                        <a class="previous_disabl"></a><a title="Next Page" class="page_other next"
                                            href="javascript:get_next_page('http://candidates.aamaadmiparty.org/user%2FgetCMSuser%2Fce%2F0%2Fsort_by%2FNAME%2Fpgn%2F1%2F','8','8','user_getCMSuser')">Next
                                            &gt;</a>
                                        <div class="clear"></div>
                                    </div>
                                    <div class="clear"></div>
                                </div>
                                 -->
                                <div class="clear"></div>
                            </div>

                            <div style="display: none;" id="index-state-2">
                                <p>The candidates bring their varied experience and have the will to serve the citizens of this country by
                                    delivering results as the common theme and ideology. The party's nominees include senior journalists, doctors,
                                    lawyers, artists and professionals.Each candidate has tirelessly served their community and attempted to bring
                                    social reform and justice in the lives of others.</p>
                            </div>



                            <!-- Template: static/homlist.tpl.html End -->
                        </div>




                        <!-- Template: static/hom.tpl.html End -->

                        <!-- Template: static/about.tpl.html End -->
                    </div>
                    <div class="clear"></div>

                    <!-- Template: cms/otherleaders.tpl.html Start 21/01/2015 01:18:46 -->
                    <!-- Template: cms/otherleaders.tpl.html End -->

                </div>
            </div>
            <!--loginwithinnerholder-->
			<!--loginwithholder-->
		</div>
		<!--loginwithinnerholder-->
	</div>
	<!--loginwithholder-->
	<!--contentarea-->



	<jsp:include page="footer.jsp" />
	<jsp:include page="addthis.jsp" />
</body>
</html>