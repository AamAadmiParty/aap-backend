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
<meta charset="utf-8" />
<meta name="google-site-verification" content="szZXs81i9wwYfIVcZy4hDzCKHtxsnuOMoLwfGDhlzdss" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="shortcut icon" href="https://s3-us-west-2.amazonaws.com/swaraj/prod/favicon.ico" />
<link rel="stylesheet" type="text/css" href="https://s3-us-west-2.amazonaws.com/swaraj/prod/styles/mainstyles.css" />


<meta property="og:title" content="Swaraj Abhiyan" />
<meta property="og:type" content="website" />
<meta property="og:url" content="${currentUrl}" />
<meta property="og:image" content="https://s3-us-west-2.amazonaws.com/swaraj/prod/images/missing.png" />
<meta property="og:site_name" content="Swaraj Abhiyan, India" />

</head>
<body>
<script>
Handlebars.registerHelper('trimString', function(passedString) {
	   if(passedString.length <= 500){
	      return passedString;
	   }
	    var theString = passedString.substring(0,500);
	    return new Handlebars.SafeString(theString)
	});
</script>

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

    {{#each newsItems.items}}
    
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
                    {{trimString contentSummary}}
                    ...<a href="{{contextPath}}/content/news/{{id}}">Read More</a>
                </p>
        </div>
    {{/each}}

    </div>
    <div class="rhsarea">
        {{#if loggedInUser}}
        {{else}}
        <div class="joincommunity">
            <!--joincommunity-->
            <a href="${contextPath}/profile.html"><img src="<c:out value='${staticDirectory}'/>/images/joincommunity.jpg" border="0" /></a>
            <ul>
                <li>To participate in polls</li>
                <li>To track your donation network</li>
                <li>To help spread the word</li>
                <li>And lots more...</li>
            </ul>
        </div>
        {{/if}}
        <div class="facebookWidget">
                            <!--facebookWidget-->
                            <iframe
                                src="//www.facebook.com/plugins/likebox.php?href=https%3A%2F%2Fwww.facebook.com%2FSwaraj.Samwad&amp;width=728&amp;height=590&amp;show_faces=true&amp;colorscheme=light&amp;stream=true&amp;show_border=true&amp;header=true"
                                scrolling="no" frameborder="0" style="border: none; overflow: hidden; width: 322px; height: 530px;"
                                allowTransparency="true"></iframe>
                        </div>
                        <!--facebookWidget-->
                        <div class="twitterWidget">
                            <!--twitterWidget-->
                            <a class="twitter-timeline" href="https://twitter.com/swaraj_abhiyan" data-widget-id="591375821736120321">Tweets
                                by @swaraj_abhiyan</a>
                            <script>
                                !function(d, s, id) {
                                    var js, fjs = d.getElementsByTagName(s)[0], p = /^http:/
                                            .test(d.location) ? 'http'
                                            : 'https';
                                    if (!d.getElementById(id)) {
                                        js = d.createElement(s);
                                        js.id = id;
                                        js.src = p
                                                + "://platform.twitter.com/widgets.js";
                                        fjs.parentNode.insertBefore(js, fjs);
                                    }
                                }(document, "script", "twitter-wjs");
                            </script>


                        </div>
                        <!--twitterWidget-->

                        <div class="clear"></div>
    </div>
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

	<!--contentarea-->

	<jsp:include page="footer.jsp" />

</body>
</html>




