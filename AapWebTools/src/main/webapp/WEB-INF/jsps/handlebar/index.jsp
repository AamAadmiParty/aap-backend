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

<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.2/handlebars.min.js"></script>

<jsp:include page="includes.jsp" />

<meta property="og:title" content="Swaraj Abhiyan" />
<meta property="og:type" content="website" />
<meta property="og:url" content="{{currentUrl}}" />
<meta property="og:image" content="https://s3-us-west-2.amazonaws.com/swaraj/prod/images/missing.png" />
<meta property="og:site_name" content="Swaraj Abhiyan, India" />

</head>
<body>

	<jsp:include page="header.jsp" />

	<jsp:include page="topscroller.jsp" />


	<script id="entry-template" type="text/x-handlebars-template">
<div class="contentarea">
    <!--contentarea-->

    <div class="article-leftarea">
        <!--article-leftarea-->
    <div class="articleCategory-tablist">
        <!--articleCategory-tablist-->
        <ul>
            <li><img src="<c:out value='{{staticDirectory}}'/>/images/news-icon.png" border="0" align="absmiddle" /> News</li>
            <li><a href="{{contextPath}}/videos.html"><img src="<c:out value='{{staticDirectory}}'/>/images/video-blue-icon.png" border="0" align="absmiddle" />
                    Videos</a></li>
            <li><a href="{{contextPath}}/blogs.html"><img src="<c:out value='{{staticDirectory}}'/>/images/blog-blue-icon.png" border="0" align="absmiddle" />
                    Blogs</a></li>
            <li><a href="{{contextPath}}/polls.html"><img src="<c:out value='{{staticDirectory}}'/>/images/blog-blue-icon.png" border="0" align="absmiddle" />
                    Polls</a></li>
        </ul>
    </div>
    <!--articleCategory-tablist-->

    {{#list newsItems.items}}
    
        <div class="divarticle">
            <!--divarticle-->
            <h1>
                <a href="{{contextPath}}/content/news/{{id}}">
                <c:out value="{{title}}" />
                </a>
            </h1>
            <div class="img">
            </div>
                <p>
                    {{contentSummary}}
                    ...<a href="{{contextPath}}/content/news/{{id}}">Read More</a>
                </p>
        </div>
    {{/list}}

    </div>
</script>
	<!--article-leftarea-->

	<div id="final"></div>












	<script>

    var source   = $("#entry-template").html();

    var template = Handlebars.compile(source);

    var context = ${context};

    var html    = template(context);



    $("#final").html(html);

</script>

	<jsp:include page="rightside.jsp" />


	</div>
	<!--contentarea-->

	<jsp:include page="footer.jsp" />

</body>
</html>




