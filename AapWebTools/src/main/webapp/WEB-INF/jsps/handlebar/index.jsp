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
 <c:out value="${template}" escapeXml="false"></c:out>
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




